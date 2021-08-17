package kr.co.player.api.domain.submit.model;

import io.swagger.annotations.ApiModelProperty;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.*;

public class ClubSubmitDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE {
        @ApiModelProperty(example = "클럽 가입 메시지")
        private String message;

        private ClubEntity clubEntity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class READ {
        @ApiModelProperty(example = "클럽 가입 메시지")
        private String message;

        @ApiModelProperty(example = "클럽 이름")
        private String clubName;

        @ApiModelProperty(example = "신청 상태")
        private JoinStatus joinStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UPDATE {
        private JoinStatus joinStatus;
        private UserEntity userEntity;
        private ClubEntity clubEntity;
    }
}
