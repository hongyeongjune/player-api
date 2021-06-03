package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;

import java.util.Optional;

public interface UserImageRepositoryCustom {
    Optional<UserImageEntity> fetchFirstOrderByIdDesc();
}
