package kr.co.player.api.domain.user.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PositionType {
    FW("공격수"), MF("미드필더"), DF("수비수"), GK("골키퍼");

    private String positionType;

    public static PositionType of(String positionType) {
        return Arrays.stream(PositionType.values())
                .filter(position -> position.toString().equalsIgnoreCase(positionType))
                .findAny().orElseThrow(() -> new UserDefineException("PositionType 항목을 찾을 수 없습니다."));
    }
}
