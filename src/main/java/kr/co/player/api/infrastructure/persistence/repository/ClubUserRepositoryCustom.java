package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ClubUserRepositoryCustom {
    Optional<ClubUserEntity> fetchByClubEntityAndClubUserRole(ClubEntity clubEntity);
    Long countByClubEntity(ClubEntity clubEntity);
    Optional<ClubUserEntity> fetchByClubEntityAndUserEntity(ClubEntity clubEntity, UserEntity userEntity);
    List<ClubUserEntity> fetchByUserEntity(UserEntity userEntity);
}
