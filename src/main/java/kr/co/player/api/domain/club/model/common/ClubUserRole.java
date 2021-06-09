package kr.co.player.api.domain.club.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ClubUserRole {
    USER("팀원"), LEADER("리더");

    private String role;

    public static ClubUserRole of(String role) {
        return Arrays.stream(ClubUserRole.values())
                .filter(userRole -> userRole.toString().equalsIgnoreCase(role))
                .findAny().orElseThrow(() -> new UserDefineException("ClubUserRole 항목을 찾을 수 없습니다."));
    }
}
