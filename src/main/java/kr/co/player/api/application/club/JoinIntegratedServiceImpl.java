package kr.co.player.api.application.club;

import kr.co.player.api.domain.club.model.ClubIntegratedDto;
import kr.co.player.api.domain.club.model.ClubUserDto;
import kr.co.player.api.domain.club.model.common.ClubUserRole;
import kr.co.player.api.domain.club.service.ClubService;
import kr.co.player.api.domain.club.service.ClubUserService;
import kr.co.player.api.domain.integrated.JoinIntegratedService;
import kr.co.player.api.domain.invitation.model.ClubInvitationDto;
import kr.co.player.api.domain.invitation.service.ClubInvitationService;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.domain.submit.model.ClubSubmitDto;
import kr.co.player.api.domain.submit.service.ClubSubmitService;
import kr.co.player.api.domain.user.service.UserService;
import kr.co.player.api.infrastructure.error.exception.BadRequestException;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinIntegratedServiceImpl implements JoinIntegratedService {

    @Value("${club.submit.limit}")
    private int SUBMIT_LIMIT;

    private final ClubService clubService;
    private final ClubUserService clubUserService;
    private final ClubSubmitService clubSubmitService;
    private final ClubInvitationService clubInvitationService;
    private final UserService userService;

    /**
     * 클럽 가입 신청 : ClubService + ClubSubmitService
     * @param create : 클럽 가입을 위한 dto
     */
    @Override
    public void createClubSubmit(ClubIntegratedDto.CREATE_SUBMIT create) {
        ClubEntity clubEntity = clubService.getClub(create.getClubName());

        if(clubSubmitService.getClubSubmitByWaiting(clubEntity).isPresent()) {
            throw new BadRequestException("이미 신청을 한 내역이 있습니다.");
        }

        if(clubSubmitService.countClubSubmitByUserEntityNotWaiting() > SUBMIT_LIMIT) {
            throw new BadRequestException("신청 가능한 개수를 초과하였습니다.");
        }

        clubSubmitService.createSubmit(ClubSubmitDto.CREATE.builder()
                .clubEntity(clubEntity)
                .message(create.getMessage())
                .build());
    }

    /**
     * 클럽장이 클럽으로 초대 신청 : ClubService + ClubUserService + ClubInvitationService + UserService
     * @param create : 초대를 위한 dto
     */
    @Override
    public void createClubInvitation(ClubIntegratedDto.CREATE_INVITATION create) {
        ClubEntity clubEntity = clubService.getClub(create.getClubName());
        UserEntity clubLeaderEntity = UserThreadLocal.get();
        UserEntity userEntity = userService.getUserEntity(create.getIdentity());

        ClubUserEntity clubUserEntity = clubUserService.getClubUserEntity(clubEntity, clubLeaderEntity);

        if(clubInvitationService.getClubInvitationByWaiting(clubUserEntity, userEntity).isPresent()) {
            throw new BadRequestException("이미 초대를 하신 내역이 있습니다.");
        }

        if(!clubUserEntity.getClubUserRole().equals(ClubUserRole.LEADER)) {
            throw new BadRequestException("클럽의 리더가 아니기 때문에 초대 권한이 없습니다.");
        }

        clubInvitationService.createClubInvitation(ClubInvitationDto.CREATE.builder()
                .clubUserEntity(clubUserEntity)
                .message(create.getMessage())
                .userEntity(userEntity)
                .build());
    }

    /**
     * 해당 클럽에 가입 요청을 한 목록 확인 : ClubService + ClubUserService + ClubSubmitService
     * @param pageNo : 페이지 번호
     * @param clubName : 클럽 이름
     * @return 페이징 dto
     */
    @Override
    public Page<ClubIntegratedDto.READ_SUBMIT> getClubSubmits(int pageNo, String clubName) {

        ClubEntity clubEntity = clubService.getClub(clubName);

        if(!clubUserService.isLeader(clubEntity)) {
            throw new BadRequestException("클럽 신청 목록을 확인할 권한이 없습니다.");
        }

        return clubSubmitService.getClubSubmitsByClubEntity(pageNo, clubEntity)
                .map(ClubSubmitEntity::toIntegratedDomain);
    }

    /**
     * 클럽 장이 클럽으로 초대한 리스트 확인 : ClubService + ClubUserService + ClubInvitationService
     * @param pageNo : 페이지 번호
     * @param clubName : 클럽 이름
     * @return 페이징 dto
     */
    @Override
    public Page<ClubIntegratedDto.READ_INVITATION> getClubInvitations(int pageNo, String clubName) {
        ClubEntity clubEntity = clubService.getClub(clubName);

        if(!clubUserService.isLeader(clubEntity)) {
            throw new BadRequestException("클럽 신청 목록을 확인할 권한이 없습니다.");
        }

        return clubInvitationService.getClubInvitations(pageNo, clubEntity)
                .map(ClubInvitationEntity::toIntegratedDomain);
    }

    /**
     * 클럽 가입 신청 상태 변경 : ClubService + ClubUserService + ClubSubmitService + UserService
     * -> 수락 시 ClubUser 에 추가
     * -> 거절 시 상태만 변경
     * @param update 클럽 가입 신청 상태 변경 dto
     */
    @Override
    public void updateClubSubmitStatus(ClubIntegratedDto.UPDATE_SUBMIT update) {
        ClubEntity clubEntity = clubService.getClub(update.getClubName());
        UserEntity userEntity = userService.getUserEntity(update.getIdentity());

        JoinStatus joinStatus = JoinStatus.of(update.getJoinStatus());

        if(joinStatus.equals(JoinStatus.ACCEPT)) {
            clubUserService.createClubUser(ClubUserDto.CREATE.builder()
                    .clubEntity(clubEntity)
                    .userEntity(userEntity)
                    .build());
        }

        clubSubmitService.updateJoinStatus(ClubSubmitDto.UPDATE.builder()
                .clubEntity(clubEntity)
                .userEntity(userEntity)
                .joinStatus(joinStatus)
                .build());

    }

    /**
     * 본인이 직접 신청 후 취소 : ClubService + ClubSubmitService
     * @param clubName : 클럽 이름
     */
    @Override
    public void updateClubSubmitStatusDirectly(String clubName) {
        ClubEntity clubEntity = clubService.getClub(clubName);

        clubSubmitService.updateJoinStatus(ClubSubmitDto.UPDATE.builder()
                .clubEntity(clubEntity)
                .userEntity(UserThreadLocal.get())
                .joinStatus(JoinStatus.CANCEL)
                .build());
    }

    /**
     * 클럽 초대 상태 변경 : ClubService + UserService + ClubUserService + ClubInvitationService
     * -> 수락 시 ClubUser 에 추가
     * -> 거절 시 상태만 변경
     * @param update : 클럽 초대 상태 변경 dto
     */
    @Override
    public void updateClubInvitationStatus(ClubIntegratedDto.UPDATE_INVITATION update) {
        ClubEntity clubEntity = clubService.getClub(update.getClubName());
        UserEntity clubLeaderEntity = userService.getUserEntity(update.getIdentity());
        UserEntity userEntity = UserThreadLocal.get();

        JoinStatus joinStatus = JoinStatus.of(update.getJoinStatus());

        if(joinStatus.equals(JoinStatus.ACCEPT)) {
            clubUserService.createClubUser(ClubUserDto.CREATE.builder()
                    .clubEntity(clubEntity)
                    .userEntity(userEntity)
                    .build());
        }

        ClubUserEntity clubUserEntity = clubUserService.getClubUserEntity(clubEntity, clubLeaderEntity);

        clubInvitationService.updateJoinStatus(ClubInvitationDto.UPDATE.builder()
                .clubUserEntity(clubUserEntity)
                .userEntity(userEntity)
                .joinStatus(joinStatus)
                .build());
    }
}
