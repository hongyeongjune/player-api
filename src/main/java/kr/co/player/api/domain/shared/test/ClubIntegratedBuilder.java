package kr.co.player.api.domain.shared.test;

import kr.co.player.api.domain.club.model.ClubIntegratedDto;
import kr.co.player.api.domain.user.model.common.PositionType;
import org.jeasy.random.EasyRandom;

public class ClubIntegratedBuilder {
    public static ClubIntegratedDto.CREATE create = ClubIntegratedDto.CREATE.builder()
            .name(new EasyRandom().nextObject(String.class))
            .city(new EasyRandom().nextObject(String.class))
            .district(new EasyRandom().nextObject(String.class))
            .positionType("MF")
            .description(new EasyRandom().nextObject(String.class))
            .build();
}
