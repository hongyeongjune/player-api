package kr.co.player.api.domain.image.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ImageType {

    MAIN("메인 이미지"), SUB("서브 이미지");

    private String imageType;

    public static ImageType of(String imageType) {
        return Arrays.stream(ImageType.values())
                .filter(type -> type.toString().equalsIgnoreCase(imageType))
                .findAny().orElseThrow(() -> new UserDefineException("ImageType 항목을 찾을 수 없습니다."));
    }
}
