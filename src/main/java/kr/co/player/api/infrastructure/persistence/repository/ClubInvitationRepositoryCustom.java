package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubInvitationEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClubInvitationRepositoryCustom {
    Page<ClubInvitationEntity> fetchClubInvitationByClubEntity(Pageable pageable, ClubEntity clubEntity);
    Page<ClubInvitationEntity> fetchClubInvitationByUserEntity(Pageable pageable, UserEntity userEntity);
    Page<ClubInvitationEntity> fetchClubInvitationByUserEntityAndJoinStatus(Pageable pageable, UserEntity userEntity, List<JoinStatus> joinStatusList);

    Optional<ClubInvitationEntity> fetchClubInvitationByClubUserEntityAndUserEntityAndWaiting(ClubUserEntity clubUserEntity, UserEntity userEntity);
}
