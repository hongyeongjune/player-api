package kr.co.player.api.domain.match.model;

import io.swagger.annotations.ApiModelProperty;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

public class MatchDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE {
        private ClubEntity clubEntity;
        private UserEntity userEntity;
        private String name;
        private Address address;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String fieldName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class READ {
        @ApiModelProperty(example = "매칭 번호")
        private Long id;

        @ApiModelProperty(example = "매치 제목")
        private String name;

        @ApiModelProperty(example = "클럽 이름")
        private String clubName;

        @ApiModelProperty(example = "경기도")
        private String city;

        @ApiModelProperty(example = "구리시")
        private String district;

        @ApiModelProperty(example = "시작 시간")
        private LocalDateTime startTime;

        @ApiModelProperty(example = "종료 시간")
        private LocalDateTime endTime;

        @ApiModelProperty(example = "축구장 이름")
        private String fieldName;

        @ApiModelProperty(example = "매칭 인원")
        private int playerCount;

        @ApiModelProperty(example = "매칭 상태")
        private String matchStatus;
    }
}
