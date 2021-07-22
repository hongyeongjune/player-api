package kr.co.player.api.infrastructure.utils.builder;

import kr.co.player.api.domain.club.model.ClubIntegratedDto;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import org.jeasy.random.EasyRandom;

public class ClubIntegratedBuilder {
    public static ClubIntegratedDto.CREATE create = ClubIntegratedDto.CREATE.builder()
            .name("아름마을 FC")
            .city("경기도")
            .district("구리시")
            .positionType("MF")
            .description("경기도 구리시 아름마을 축구단")
            .build();

    public static ClubIntegratedDto.UPDATE update = ClubIntegratedDto.UPDATE.builder()
            .name("아름마을 FC")
            .city("경기도")
            .district("구리시")
            .description("경기도 구리시 아름마을 축구팀")
            .build();
}
