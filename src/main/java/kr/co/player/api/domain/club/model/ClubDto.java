package kr.co.player.api.domain.club.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClubDto {

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

        @ApiModelProperty(example = "클럽 소개")
        @NotBlank(message = "클럽 소개를 해주세요")
        private String description;
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
}
