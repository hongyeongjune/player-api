package kr.co.player.api.domain.invitation.service;

import kr.co.player.api.domain.invitation.model.ClubInvitationDto;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.domain.shared.PageUtil;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubInvitationEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubInvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubInvitationServiceImpl implements ClubInvitationService {

    private final static int LIMIT = 10;

    private final ClubInvitationRepository clubInvitationRepository;

    @Override
    public void createClubInvitation(ClubInvitationDto.CREATE create) {
        clubInvitationRepository.save(ClubInvitationEntity.builder()
                .clubUserEntity(create.getClubUserEntity())
                .userEntity(create.getUserEntity())
                .joinStatus(JoinStatus.of("WAITING"))
                .message(create.getMessage())
                .build());
    }

    @Override
    public Page<ClubInvitationEntity> getClubInvitations(int pageNo, ClubEntity clubEntity) {
        return clubInvitationRepository.fetchClubInvitationByClubEntity(PageUtil.applyPageConfig(pageNo, LIMIT), clubEntity);
    }

    @Override
    public Optional<ClubInvitationEntity> getClubInvitationByWaiting(ClubUserEntity clubUserEntity, UserEntity userEntity) {
        return clubInvitationRepository.fetchClubInvitationByClubUserEntityAndUserEntityAndWaiting(clubUserEntity, userEntity);
    }

    /**
     * 초대받은 내역 조회 : ClubInvitationService
     * @param pageNo : 페이지 번호
     * @return 페이징 dto
     */
    @Override
    public Page<ClubInvitationDto.READ> getClubInvitationsByUserEntity(int pageNo) {
        return clubInvitationRepository.fetchClubInvitationByUserEntity(PageUtil.applyPageConfig(pageNo, LIMIT), UserThreadLocal.get())
                .map(ClubInvitationEntity::toDomain);
    }

    /**
     * 초대 상태에 따라 초대받은 내역 조회 : ClubInvitationService
     * @param pageNo : 페이지 번호
     * @param joinStatusList : 초대 상태
     * @return 페이징 dto
     */
    @Override
    public Page<ClubInvitationDto.READ> getClubInvitationsByJoinStatus(int pageNo, List<String> joinStatusList) {
        List<JoinStatus> statusList = joinStatusList.stream()
                .map(joinStatus -> JoinStatus.of(joinStatus))
                .collect(Collectors.toList());

        return clubInvitationRepository.fetchClubInvitationByUserEntityAndJoinStatus(PageUtil.applyPageConfig(pageNo, LIMIT), UserThreadLocal.get(), statusList)
                .map(ClubInvitationEntity::toDomain);
    }

    @Override
    public void updateJoinStatus(ClubInvitationDto.UPDATE update) {
        ClubInvitationEntity clubInvitationEntity = clubInvitationRepository.fetchClubInvitationByClubUserEntityAndUserEntityAndWaiting(update.getClubUserEntity(), update.getUserEntity())
                .orElseThrow(() -> new NotFoundException("ClubUserEntity"));

        clubInvitationEntity.updateStatus(update.getJoinStatus());
        clubInvitationRepository.save(clubInvitationEntity);
    }
}
