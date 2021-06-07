package kr.co.player.api.domain.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JoinStatus {
    ACCEPT("수락"), WAITING("대기"), REJECT("거절");

    private String joinStatus;
}
