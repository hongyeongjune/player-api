package kr.co.player.api.domain.integrated.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MatchIntegratedDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE {
        @ApiModelProperty(example = "매치 제목")
        @NotBlank(message = "매치 제목을 입력해주세요")
        @Size(min = 2, max = 30, message = "매치 제목은 2 ~ 30자로 입력해주세요.")
        private String name;

        @ApiModelProperty(example = "클럽 이름")
        @NotBlank(message = "클럽 이름을 입력해주세요.")
        private String clubName;

        @ApiModelProperty(example = "경기도")
        @NotBlank(message = "지역을 설정해주세요.")
        private String city;

        @ApiModelProperty(example = "구리시")
        @NotBlank(message = "지역을 설정해주세요.")
        private String district;

        @ApiModelProperty(example = "연도")
        @NotBlank(message = "연도를 입력해주세요")
        private int year;

        @ApiModelProperty(example = "월")
        @NotBlank(message = "월을 입력해주세요")
        private int month;

        @ApiModelProperty(example = "일")
        @NotBlank(message = "일을 입력해주세요")
        private int day;

        @ApiModelProperty(example = "시작 시간(시)")
        @NotBlank(message = "시를 입력해주세요")
        private int startHour;

        @ApiModelProperty(example = "시작 시간(분)")
        @NotBlank(message = "분을 입력해주세요")
        private int startMinutes;

        @ApiModelProperty(example = "끝나는 시간(시)")
        @NotBlank(message = "시를 입력해주세요")
        private int endHour;

        @ApiModelProperty(example = "끝나는 시간(분)")
        @NotBlank(message = "분을 입력해주세요")
        private int endMinutes;
    }
}
