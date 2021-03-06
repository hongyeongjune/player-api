package kr.co.player.api.application.club;

import kr.co.player.api.domain.club.model.ClubDto;
import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.domain.club.model.ClubUserDto;
import kr.co.player.api.domain.club.service.ClubService;
import kr.co.player.api.domain.club.service.ClubUserService;
import kr.co.player.api.domain.integrated.service.ClubIntegratedService;
import kr.co.player.api.infrastructure.error.exception.BadRequestException;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ClubIntegratedServiceImpl implements ClubIntegratedService {

    private final ClubService clubService;
    private final ClubUserService clubUserService;

    @Override
    public boolean isLeader(String clubName) {
        ClubEntity clubEntity = clubService.getClub(clubName);

        boolean isLeader = clubUserService.isLeader(clubEntity);

        return isLeader;
    }

    /**
     * 클럽 생성 : ClubService + ClubUserService
     * @param create : 클럽 생성에 필요한 dto
     */
    @Override
    public void createClub(ClubIntegratedDto.CREATE create) {
        ClubEntity clubEntity = clubService.createClub(ClubDto.CREATE.builder()
                .name(create.getName())
                .city(create.getCity())
                .district(create.getDistrict())
                .description(create.getDescription())
                .build());

        clubUserService.createClubFromLeader(ClubUserDto.CREATE_LEADER.builder()
                .clubEntity(clubEntity)
                .positionType(create.getPositionType())
                .build());
    }

    /**
     * 모든 클럽 조회 : ClubService + ClubUserService
     * @param pageNo : 페이지 번호
     * @return : 페이징 dto
     */
    @Override
    public Page<ClubIntegratedDto.READ> getClubs(int pageNo) {
        Page<ClubEntity> clubEntityPage = clubService.getClubs(pageNo);

        return toDto(clubEntityPage);
    }

    /**
     * 주소 기준으로 클럽 조회 : ClubService + ClubUserService
     * @param pageNo : 페이지 번호
     * @return : 페이징 dto
     */
    @Override
    public Page<ClubIntegratedDto.READ> getClubsByAddress(int pageNo, List<String> districtList, List<String> cityList) {
        Page<ClubEntity> clubEntityPage = clubService.getClubsByAddress(pageNo, districtList, cityList);

        return toDto(clubEntityPage);
    }

    /**
     * 키워드로 클럽 조회 : ClubService + ClubUserService
     * @param pageNo : 페이지 번호
     * @return : 페이징 dto
     */
    @Override
    public Page<ClubIntegratedDto.READ> getClubsByKeyword(int pageNo, String keyword) {
        Page<ClubEntity> clubEntityPage = clubService.getClubsByKeywordContains(pageNo, keyword);

        return toDto(clubEntityPage);
    }

    /**
     * 클럽 장이 클럽 내용 수정 : ClubService + ClubUserService
     * @param update : 클럽 수정에 필요한 dto
     */
    @Override
    public void updateClub(ClubIntegratedDto.UPDATE update) {
        ClubEntity clubEntity = clubService.getClub(update.getName());

        if(!clubUserService.isLeader(clubEntity)) {
            throw new BadRequestException("수정 권한이 없습니다");
        }

        clubService.updateClub(clubEntity, ClubDto.UPDATE.builder()
                .city(update.getCity())
                .district(update.getDistrict())
                .description(update.getDescription())
                .build());
    }

    /**
     * 클럽 장이 클럽 이름 수정 : ClubService + ClubUserService
     * @param update : 클럽 이름 수정을 위한 dto
     */
    @Override
    public void updateClubName(ClubIntegratedDto.UPDATE_CLUB_NAME update) {
        ClubEntity clubEntity = clubService.getClub(update.getOldName());

        if(!clubUserService.isLeader(clubEntity)) {
            throw new BadRequestException("수정 권한이 없습니다.");
        }

        if(clubService.checkClubName(update.getNewName())) {
            throw new BadRequestException("이미 존재하는 클럽 이름입니다.");
        }

        clubEntity.update(update.getNewName());
    }

    private Page<ClubIntegratedDto.READ> toDto(Page<ClubEntity> clubEntityPage) {
        return clubEntityPage
                .map(clubEntity -> {
                    ClubUserEntity clubUserEntity = clubUserService.getClubLeader(clubEntity);
                    Long count = clubUserService.countClubUser(clubEntity);
                    return clubEntity.toDomain(clubUserEntity, count);
                });
    }
}
