package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImageEntity, Long> {
    List<UserImageEntity> findByUserEntity(Long UserEntity);
}

