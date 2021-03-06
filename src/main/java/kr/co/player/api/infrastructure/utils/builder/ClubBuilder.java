package kr.co.player.api.infrastructure.utils.builder;

import com.amazonaws.services.guardduty.model.City;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;

public class ClubBuilder {
    public static ClubEntity build() {
        return ClubEntity.builder()
                .clubName("FC 서울")
                .address(Address.builder()
                        .city("서울특별시")
                        .district("마포구")
                        .build())
                .description("서울의 축구 팀")
                .rating(0)
                .build();
    }

    public static ClubEntity create = ClubEntity.builder()
            .clubName("아름마을 FC")
            .address(Address.builder()
                    .city("경기도")
                    .district("구리시")
                    .build())
            .description("경기도 구리시 아름마을 축구단")
            .rating(0)
            .build();

    public static ClubEntity TottenhamHotspur = ClubEntity.builder()
                .clubName("토트넘 훗스퍼 FC")
                .address(Address.builder()
                        .city("런던")
                        .district("토트넘")
                        .build())
                .description("북런던의 축구 팀")
                .rating(0)
                .build();

    public static ClubEntity Liverpool = ClubEntity.builder()
            .clubName("리버풀 FC")
            .address(Address.builder()
                    .city("리버풀")
                    .district("머지사이드")
                    .build())
            .description("머지사이드 주의 축구 팀")
            .rating(0)
            .build();

    public static ClubEntity ManchesterCity = ClubEntity.builder()
            .clubName("맨체스터 시티 FC")
            .address(Address.builder()
                    .city("맨체스터")
                    .district("맨체스터")
                    .build())
            .description("맨체스터의 축구 팀")
            .rating(0)
            .build();

    public static ClubEntity  Arsenal = ClubEntity.builder()
            .clubName("아스날 FC")
            .address(Address.builder()
                    .city("런던")
                    .district("이슬링턴 구")
                    .build())
            .description("북런던의 축구 팀")
            .rating(0)
            .build();
}
