package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubUserRepository extends JpaRepository<ClubUserEntity, Long>, ClubUserRepositoryCustom {
}
