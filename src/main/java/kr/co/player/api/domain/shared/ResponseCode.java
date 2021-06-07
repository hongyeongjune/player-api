package kr.co.player.api.domain.shared;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(1), FAIL(2), TOKEN_EXPIRED(3);

    int code;

    public static ResponseCode of(int num){
        return Arrays.stream(ResponseCode.values())
                .filter(responseCode -> responseCode.getCode() == num)
                .findFirst().orElseThrow(() -> new UserDefineException("응답 코드를 찾을 수 없습니다."));
    }
}
