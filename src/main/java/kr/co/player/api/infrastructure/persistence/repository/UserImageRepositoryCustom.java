package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;

import java.util.List;
import java.util.Optional;

public interface UserImageRepositoryCustom {
    Optional<UserImageEntity> fetchFirstOrderByIdDesc();
    List<UserImageEntity> fetchByUserEntity(UserEntity userEntity);
    Optional<UserImageEntity> fetchByUserEntityAndUrl(UserEntity userEntity, String url);
}
