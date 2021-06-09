package kr.co.player.api.domain.shared;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum JoinStatus {
    ACCEPT("수락"), WAITING("대기"), REJECT("거절"), CANCEL("취소");

    private String joinStatus;

    public static JoinStatus of(String joinStatus) {
        return Arrays.stream(JoinStatus.values())
                .filter(status -> status.toString().equalsIgnoreCase(joinStatus))
                .findAny().orElseThrow(() -> new UserDefineException("JoinStatus 항목을 찾을 수 없습니다."));
    }
}
