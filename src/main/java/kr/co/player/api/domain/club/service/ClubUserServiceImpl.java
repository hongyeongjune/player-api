package kr.co.player.api.domain.club.service;

import kr.co.player.api.domain.club.model.ClubUserDto;
import kr.co.player.api.domain.club.model.common.ClubUserRole;
import kr.co.player.api.domain.user.model.common.PositionType;
import kr.co.player.api.infrastructure.error.exception.BadRequestException;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubUserServiceImpl implements ClubUserService {

    @Value("${club.limit}")
    private int CLUB_LIMIT;

    private final ClubUserRepository clubUserRepository;


    @Override
    public boolean isLeader(ClubEntity clubEntity) {
        ClubUserEntity clubUserEntity = clubUserRepository.fetchByClubEntityAndUserEntity(clubEntity, UserThreadLocal.get())
                .orElseThrow(() -> new NotFoundException("ClubUserEntity"));

        if(!clubUserEntity.getClubUserRole().equals(ClubUserRole.of("LEADER"))) {
            return false;
        }

        return true;
    }

    @Override
    public void createClubFromLeader(ClubUserDto.CREATE_LEADER create) {

        UserEntity userEntity = UserThreadLocal.get();

        List<ClubUserEntity> clubUserEntityList = clubUserRepository.fetchByUserEntity(userEntity);

        if(clubUserEntityList.size() >= CLUB_LIMIT) {
            throw new BadRequestException("생성 가능한 클럽 또는 현재 가입한 클럽의 수가 초과하였습니다.");
        }

        clubUserRepository.save(
                ClubUserEntity.builder()
                        .clubEntity(create.getClubEntity())
                        .userEntity(userEntity)
                        .uniformNumber(10)
                        .clubPositionType(PositionType.of(create.getPositionType()))
                        .clubUserRole(ClubUserRole.of("LEADER"))
                        .build()
        );
    }

    @Override
    public void createClubUser(ClubUserDto.CREATE create) {
        clubUserRepository.save(
                ClubUserEntity.builder()
                        .clubEntity(create.getClubEntity())
                        .userEntity(create.getUserEntity())
                        .clubUserRole(ClubUserRole.of("USER"))
                        .build());
    }

    @Override
    public ClubUserEntity getClubLeader(ClubEntity clubEntity) {
        return clubUserRepository.fetchByClubEntityAndClubUserRole(clubEntity)
                .orElseThrow(() -> new NotFoundException("ClubUserEntity"));
    }

    @Override
    public Long countClubUser(ClubEntity clubEntity) {
        return clubUserRepository.countByClubEntity(clubEntity);
    }

    @Override
    public ClubUserEntity getClubUserEntity(ClubEntity clubEntity, UserEntity userEntity) {
        return clubUserRepository.fetchByClubEntityAndUserEntity(clubEntity, userEntity)
                .orElseThrow(() -> new NotFoundException("ClubUserEntity"));
    }

    /**
     * 내 클럽 조회 : ClubUserService
     * @return : 페이징 dto
     */
    @Override
    public List<ClubUserDto.READ_MY_CLUB> getMyClubs() {
        UserEntity userEntity = UserThreadLocal.get();
        return clubUserRepository.fetchByUserEntity(userEntity).stream()
                .map(ClubUserEntity::toDomain)
                .collect(Collectors.toList());
    }

}
