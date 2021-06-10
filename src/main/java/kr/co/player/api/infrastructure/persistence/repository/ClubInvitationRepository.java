package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.ClubInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubInvitationRepository extends JpaRepository<ClubInvitationEntity, Long>, ClubInvitationRepositoryCustom {
}
