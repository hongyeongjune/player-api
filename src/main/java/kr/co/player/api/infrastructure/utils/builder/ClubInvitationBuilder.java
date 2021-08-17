package kr.co.player.api.infrastructure.utils.builder;

import kr.co.player.api.domain.invitation.model.ClubInvitationDto;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.ClubInvitationEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.jeasy.random.EasyRandom;

public class ClubInvitationBuilder {
    public static ClubInvitationEntity build(ClubUserEntity clubUserEntity, UserEntity userEntity) {
        return ClubInvitationEntity.builder()
                .message(new EasyRandom().nextObject(String.class))
                .joinStatus(new EasyRandom().nextObject(JoinStatus.class))
                .clubUserEntity(clubUserEntity)
                .userEntity(userEntity)
                .build();
    }

    public static ClubInvitationEntity waiting(ClubUserEntity clubUserEntity, UserEntity userEntity) {
        return ClubInvitationEntity.builder()
                .message(new EasyRandom().nextObject(String.class))
                .joinStatus(JoinStatus.WAITING)
                .clubUserEntity(clubUserEntity)
                .userEntity(userEntity)
                .build();
    }

    public static ClubInvitationDto.CREATE create(ClubUserEntity clubUserEntity, UserEntity userEntity) {
        return ClubInvitationDto.CREATE.builder()
                .message(new EasyRandom().nextObject(String.class))
                .clubUserEntity(clubUserEntity)
                .userEntity(userEntity)
                .build();
    }
}
