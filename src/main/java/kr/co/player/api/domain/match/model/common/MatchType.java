package kr.co.player.api.domain.match.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MatchType {
    HOME("홈"), AWAY("어웨이");

    private String matchType;

    public static MatchType of(String matchType) {
        return Arrays.stream(MatchType.values())
                .filter(type -> type.toString().equalsIgnoreCase(matchType))
                .findAny().orElseThrow(() -> new UserDefineException("MatchType 항목을 찾을 수 없습니다."));
    }
}
