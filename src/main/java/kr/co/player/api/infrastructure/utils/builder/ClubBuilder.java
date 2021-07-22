package kr.co.player.api.infrastructure.utils.builder;

import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;

public class ClubBuilder {
    public static ClubEntity build() {
        return ClubEntity.builder()
                .clubName("아름마을 FC")
                .address(Address.builder()
                        .city("경기도")
                        .district("구리시")
                        .build())
                .description("경기도 구리시 아름마을 축구단")
                .rating(0)
                .build();
    }
}
