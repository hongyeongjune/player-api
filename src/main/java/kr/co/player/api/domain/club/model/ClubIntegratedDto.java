package kr.co.player.api.domain.club.model;

import io.swagger.annotations.ApiModelProperty;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.domain.shared.JoinStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClubIntegratedDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE {
        @ApiModelProperty(example = "클럽 이름")
        @NotBlank(message = "클럽 이름을 입력해주세요")
        @Size(min = 2, max = 16, message = "클럽 이름은 2 ~ 16자로 입력해주세요.")
        private String name;

        @ApiModelProperty(example = "경기도")
        @NotBlank(message = "지역을 설정해주세요")
        private String city;

        @ApiModelProperty(example = "구리시")
        @NotBlank(message = "지역을 설정해주세요")
        private String district;

        @ApiModelProperty(example = "FW or MF or DF or GK")
        private String positionType;

        @ApiModelProperty(example = "클럽 소개")
        @NotBlank(message = "클럽 소개를 해주세요")
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE_SUBMIT {
        @ApiModelProperty(example = "클럽 이름")
        private String clubName;

        @ApiModelProperty(example = "가입 메시지")
        private String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE_INVITATION {
        @ApiModelProperty(example = "클럽 이름")
        private String clubName;

        @ApiModelProperty(example = "초대 메시지")
        private String message;

        @ApiModelProperty(example = "초대할 사람 아이디")
        private String identity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class READ {
        @ApiModelProperty(example = "클럽 이름")
        private String name;

        @ApiModelProperty(example = "클럽 소개")
        private String description;

        @ApiModelProperty(example = "회원 수")
        private long userCount;

        @ApiModelProperty(example = "아이디")
        private String identity;

        @ApiModelProperty(example = "이름")
        private String userName;

        @ApiModelProperty(example = "평점")
        private double rating;

        private Address address;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class READ_SUBMIT {
        @ApiModelProperty(example = "신청한 사람 아이디")
        private String identity;

        @ApiModelProperty(example = "신청한 사람 이름")
        private String name;

        @ApiModelProperty(example = "신청 메세지")
        private String message;

        @ApiModelProperty(example = "신청 상태")
        private JoinStatus joinStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class READ_INVITATION {
        @ApiModelProperty(example = "초대한 사람 아이디")
        private String identity;

        @ApiModelProperty(example = "초대한 사람 이름")
        private String name;

        @ApiModelProperty(example = "초대 메세지")
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

        @ApiModelProperty(example = "클럽 이름")
        @NotBlank(message = "클럽 이름을 입력해주세요")
        @Size(min = 2, max = 16, message = "클럽 이름은 2 ~ 16자로 입력해주세요.")
        private String name;

        @ApiModelProperty(example = "경기도")
        @NotBlank(message = "지역을 설정해주세요")
        private String city;

        @ApiModelProperty(example = "구리시")
        @NotBlank(message = "지역을 설정해주세요")
        private String district;

        @ApiModelProperty(example = "클럽 소개")
        @NotBlank(message = "클럽 소개를 해주세요")
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UPDATE_SUBMIT {
        @ApiModelProperty(example = "신청한 사람 아이디")
        private String identity;

        @ApiModelProperty(example = "클럽 이름")
        private String clubName;

        @ApiModelProperty(example = "신청 상태")
        private String joinStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UPDATE_INVITATION {
        @ApiModelProperty(example = "클럽장 아이디")
        private String identity;

        @ApiModelProperty(example = "클럽 이름")
        private String clubName;

        @ApiModelProperty(example = "신청 상태")
        private String joinStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UPDATE_SUBMIT_DIRECTLY {
        @ApiModelProperty(example = "클럽 이름")
        private String clubName;
    }

}
