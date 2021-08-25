package kr.co.player.api.domain.match.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MatchStatus {
    RECRUITMENT("모집중"), PROCEEDING("진행중"), FINISH("종료");

    private String matchStatus;

    public static MatchStatus of(String matchStatus) {
        return Arrays.stream(MatchStatus.values())
                .filter(status -> status.toString().equalsIgnoreCase(matchStatus))
                .findAny().orElseThrow(() -> new UserDefineException("MatchStatus 항목을 찾을 수 없습니다."));
    }
}
