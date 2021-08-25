package kr.co.player.api.domain.match.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MatchLevel {
    BAD("하"), AVERAGE("중"), GOOD("상");

    private String matchLevel;

    public static MatchLevel of(String matchLevel) {
        return Arrays.stream(MatchLevel.values())
                .filter(level -> level.toString().equalsIgnoreCase(matchLevel))
                .findAny().orElseThrow(() -> new UserDefineException("MatchLevel 항목을 찾을 수 없습니다."));
    }
}
