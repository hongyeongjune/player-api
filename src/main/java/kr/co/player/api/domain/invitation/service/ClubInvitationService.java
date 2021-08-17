package kr.co.player.api.domain.invitation.service;

import kr.co.player.api.domain.invitation.model.ClubInvitationDto;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubInvitationEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ClubInvitationService {

    //create - integrated
    void createClubInvitation(ClubInvitationDto.CREATE create);

    //read - integrated
    Page<ClubInvitationEntity> getClubInvitations(int pageNo, ClubEntity clubEntity);
    Optional<ClubInvitationEntity> getClubInvitationByWaiting(ClubUserEntity clubUserEntity, UserEntity userEntity);

    //read - service
    Page<ClubInvitationDto.READ> getClubInvitationsByUserEntity(int pageNo);
    Page<ClubInvitationDto.READ> getClubInvitationsByJoinStatus(int pageNo, List<String> joinStatusList);

    //update - integrated
    void updateJoinStatus(ClubInvitationDto.UPDATE update);
}
