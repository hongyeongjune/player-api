package kr.co.player.api.domain.club.model;

import io.swagger.annotations.ApiModelProperty;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.*;

public class ClubUserDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LEADER {
        @ApiModelProperty(example = "클럽 이름")
        private String clubName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE {
        private UserEntity userEntity;
        private ClubEntity clubEntity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE_LEADER {
        @ApiModelProperty(example = "FW or MF or DF or GK")
        private String positionType;

        private ClubEntity clubEntity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class READ_MY_CLUB {
        @ApiModelProperty(example = "클럽 이름")
        private String name;

        @ApiModelProperty(example = "클럽 소개")
        private String description;

        @ApiModelProperty(example = "평점")
        private double rating;

        private Address address;
    }
}
