package kr.co.player.api.domain.invitation.model;

import io.swagger.annotations.ApiModelProperty;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.*;

public class ClubInvitationDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE {
        private ClubUserEntity clubUserEntity;
        private String message;
        private UserEntity userEntity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class READ {
        @ApiModelProperty(example = "클럽 이름")
        private String clubName;

        @ApiModelProperty(example = "클럽 리더 아이디")
        private String identity;

        @ApiModelProperty(example = "클럽 리더 이름")
        private String name;

        @ApiModelProperty(example = "초대 메시지")
        private String message;

        @ApiModelProperty(example = "초대 상태")
        private JoinStatus joinStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UPDATE {
        private ClubUserEntity clubUserEntity;
        private UserEntity userEntity;
        private JoinStatus joinStatus;
    }
}
