package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.MatchUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchUserRepository extends JpaRepository<MatchUserEntity, Long>, MatchUserRepositoryCustom {
}
