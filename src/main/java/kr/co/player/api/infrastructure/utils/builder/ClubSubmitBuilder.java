package kr.co.player.api.infrastructure.utils.builder;

import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubSubmitEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.jeasy.random.EasyRandom;

public class ClubSubmitBuilder {
    public static ClubSubmitEntity build(ClubEntity clubEntity, UserEntity userEntity) {
        return ClubSubmitEntity.builder()
                .message(new EasyRandom().nextObject(String.class))
                .joinStatus(new EasyRandom().nextObject(JoinStatus.class))
                .clubEntity(clubEntity)
                .userEntity(userEntity)
                .build();
    }

    public static ClubSubmitEntity waiting(ClubEntity clubEntity, UserEntity userEntity) {
        return ClubSubmitEntity.builder()
                .message(new EasyRandom().nextObject(String.class))
                .joinStatus(JoinStatus.WAITING)
                .clubEntity(clubEntity)
                .userEntity(userEntity)
                .build();
    }

    public static ClubSubmitEntity cancel(ClubEntity clubEntity, UserEntity userEntity) {
        return ClubSubmitEntity.builder()
                .message(new EasyRandom().nextObject(String.class))
                .joinStatus(JoinStatus.CANCEL)
                .clubEntity(clubEntity)
                .userEntity(userEntity)
                .build();
    }
}
