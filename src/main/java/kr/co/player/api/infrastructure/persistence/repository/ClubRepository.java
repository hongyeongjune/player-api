package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<ClubEntity, Long>, ClubRepositoryCustom {
    boolean existsByClubName(String clubName);
    Optional<ClubEntity> findByClubName(String clubName);
}
