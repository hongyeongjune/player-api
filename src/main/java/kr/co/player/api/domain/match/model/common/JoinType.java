package kr.co.player.api.domain.match.model.common;

import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum JoinType {
    INVITATION("초대"), SUBMIT("제출");

    private String joinType;

    public static JoinType of(String joinType) {
        return Arrays.stream(JoinType.values())
                .filter(type -> type.toString().equalsIgnoreCase(joinType))
                .findAny().orElseThrow(() -> new UserDefineException("JoinType 항목을 찾을 수 없습니다."));
    }
}
