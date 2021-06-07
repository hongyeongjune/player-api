package kr.co.player.api.domain.user.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserRole {

    USER("일반 사용자"), ADMIN("관리자");

    private String userRole;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(userRole -> userRole.toString().equalsIgnoreCase(role))
                .findAny().orElseThrow(() -> new UserDefineException("UserRole 항목을 찾을 수 없습니다."));
    }
}
