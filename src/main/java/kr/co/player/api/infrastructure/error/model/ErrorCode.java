package kr.co.player.api.infrastructure.error.model;

import lombok.Getter;

@Getter
public enum  ErrorCode {

    //common
    UNAUTHORIZED_USER("해당 기능을 사용할 수 없습니다.", 403),

    //user
    DUPLICATED_ID("아이디가 중복되었습니다.", 400),

    //club
    DUPLICATED_CLUB("이름이 중복되었습니다.", 400);

    private String message;
    private int status;

    ErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
