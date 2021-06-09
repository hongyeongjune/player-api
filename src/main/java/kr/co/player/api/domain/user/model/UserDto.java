package kr.co.player.api.domain.user.model;

import io.swagger.annotations.ApiModelProperty;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.domain.user.model.common.*;
import lombok.*;
import org.hibernate.usertype.UserType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LOGIN {
        @ApiModelProperty(example = "로그인 아이디")
        @NotBlank(message = "ID를 입력해주세요")
        @Size(min = 5, max = 16, message = "ID는 5 ~ 16자를 입력해주세요")
        private String identity;

        @ApiModelProperty(example = "로그인할 패스워드")
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;

        @ApiModelProperty(example = "FCM 토큰 정보")
        @NotBlank(message = "FCM 토큰 정보를 넣어주세요")
        private String fcmToken;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TOKEN {
        @ApiModelProperty(example = "사용자 인증을 위한 accessToken")
        private String accessToken;

        @ApiModelProperty(example = "자동 로그인을 위한 refreshToken")
        private String refreshToken;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE {
        @ApiModelProperty(example = "사용할 아이디")
        @NotBlank(message = "ID를 입력해주세요")
        @Size(min = 5, max = 16, message = "ID는 5 ~ 16자를 입력해주세요")
        private String identity;

        @ApiModelProperty(example = "사용할 패스워드")
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;

        @ApiModelProperty(example = "홍길동")
        @NotBlank(message = "이름을 입력해주세요")
        private String name;

        @ApiModelProperty(example = "010-xxxx-xxxx")
        @NotBlank(message = "전화번호를 입력해주세요")
        private String userPhone;

        @ApiModelProperty(example = "19951217")
        @NotBlank(message = "생년월일을 입력해주세요")
        private String birth;

        @ApiModelProperty(example = "MALE or FEMALE")
        @NotBlank(message = "성별을 선택해주세요")
        private String gender;

        @ApiModelProperty(example = "USER")
        private String role;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class READ {
        @ApiModelProperty(example = "아이디")
        private String identity;

        @ApiModelProperty(example = "홍길동")
        private String name;

        @ApiModelProperty(example = "19951217")
        private String birth;

        @ApiModelProperty(example = "MALE or FEMALE")
        private Gender gender;

        @ApiModelProperty(example = "좋아요")
        private int likeCnt;

        @ApiModelProperty(example = "싫어요")
        private int rudeCnt;

        @ApiModelProperty(example = "70kg")
        private int height;

        @ApiModelProperty(example = "186cm")
        private int weight;

        private Address address;
        private Position position;
        private UserPhone userPhone;

        @ApiModelProperty(example = "image-url")
        private List<String> imageUrl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UPDATE {
        @ApiModelProperty(example = "홍길동")
        private String name;

        @ApiModelProperty(example = "경기도")
        private String city;

        @ApiModelProperty(example = "구리시")
        private String district;

        @ApiModelProperty(example = "FW")
        private String positionType;

        @ApiModelProperty(example = "LW")
        private String mainPosition;

        @ApiModelProperty(example = "ST")
        private String subPosition;

        @ApiModelProperty(example = "010-xxxx-xxxx")
        private String userPhone;

        @ApiModelProperty(example = "70kg")
        private int height;

        @ApiModelProperty(example = "186cm")
        private int weight;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UPDATE_PASSWORD {
        @ApiModelProperty(example = "기존 비밀번호")
        private String password;

        @ApiModelProperty(example = "새 비밀번호")
        private String newPassword;

        @ApiModelProperty(example = "새 비밀번호 확인")
        private String reNewPassword;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RESET_CHECK {
        @ApiModelProperty(example = "아이디")
        private String identity;

        @ApiModelProperty(example = "홍길동")
        private String name;

        @ApiModelProperty(example = "19951217")
        private String birth;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RESET_PASSWORD {
        @ApiModelProperty(example = "아이디")
        private String identity;

        @ApiModelProperty(example = "새 비밀번호")
        private String newPassword;

        @ApiModelProperty(example = "새 비밀번호 확인")
        private String reNewPassword;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ID_READ {
        @ApiModelProperty(example = "홍길동")
        private String name;

        @ApiModelProperty(example = "19951217")
        private String birth;
    }

}
