package kr.co.player.api.domain.match.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MatchUserRole {
    USER("클럽원"), MERCENARY("용병");

    private String matchUserRole;

    public static MatchUserRole of(String matchUserRole) {
        return Arrays.stream(MatchUserRole.values())
                .filter(role -> role.toString().equalsIgnoreCase(matchUserRole))
                .findAny().orElseThrow(() -> new UserDefineException("MatchUserRole 항목을 찾을 수 없습니다."));
    }
}

