package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubSubmitEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClubSubmitRepositoryCustom {
    Page<ClubSubmitEntity> fetchClubSubmitByUserEntity(Pageable pageable, UserEntity userEntity);
    Page<ClubSubmitEntity> fetchClubSubmitByUserEntityAndJoinStatus(Pageable pageable, UserEntity userEntity, List<JoinStatus> joinStatusList);
    Page<ClubSubmitEntity> fetchClubSubmitByClubEntity(Pageable pageable, ClubEntity clubEntity);
    Optional<ClubSubmitEntity> fetchClubSubmitByUserEntityAndClubEntityAndWaiting(UserEntity userEntity, ClubEntity clubEntity);

    long countClubSubmitByUserEntityAndNotWaiting(UserEntity userEntity);
}
