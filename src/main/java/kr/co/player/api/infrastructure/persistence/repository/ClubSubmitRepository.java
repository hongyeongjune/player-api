package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.ClubSubmitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubSubmitRepository extends JpaRepository<ClubSubmitEntity, Long>, ClubSubmitRepositoryCustom {
}
