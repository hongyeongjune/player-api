package kr.co.player.api.domain.image.model;

import io.swagger.annotations.ApiModelProperty;
import kr.co.player.api.domain.image.model.common.ImageType;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.*;

public class ImageDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Builder
    public static class CREATE_USER {
        private UserEntity userEntity;
        private String url;
        private ImageType imageType;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class READ_USER {
        @ApiModelProperty(example = "Main or SUB")
        private ImageType imageType;

        @ApiModelProperty(example = "이미지 url")
        private String imageUrl;
    }
}
