package kr.co.player.api.domain.shared.test;

import kr.co.player.api.domain.club.model.ClubUserDto;
import kr.co.player.api.domain.club.model.common.ClubUserRole;
import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.model.common.PositionType;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.jeasy.random.EasyRandom;

public class ClubUserBuilder {

    public static ClubUserEntity build(ClubEntity clubEntity, UserEntity userEntity) {
        return ClubUserEntity.builder()
                .clubEntity(clubEntity)
                .userEntity(userEntity)
                .clubUserRole(new EasyRandom().nextObject(ClubUserRole.class))
                .clubPositionType(new EasyRandom().nextObject(PositionType.class))
                .uniformNumber(new EasyRandom().nextInt())
                .build();
    }

    public static ClubUserDto.CREATE_LEADER create(ClubEntity clubEntity){
        return ClubUserDto.CREATE_LEADER.builder()
                .positionType("MF")
                .clubEntity(clubEntity)
                .build();
    }

}
