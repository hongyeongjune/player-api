package kr.co.player.api.domain.match.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MatchPlayerType {
    STARTING_PLAYERS("선발"), SUBSTITUTE("후보");

    private String matchPlayerType;

    public static MatchPlayerType of(String matchPlayerType) {
        return Arrays.stream(MatchPlayerType.values())
                .filter(type -> type.toString().equalsIgnoreCase(matchPlayerType))
                .findAny().orElseThrow(() -> new UserDefineException("MatchPlayerType 항목을 찾을 수 없습니다."));
    }
}
