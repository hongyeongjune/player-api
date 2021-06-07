package kr.co.player.api.domain.match.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JoinType {
    INVITATION("초대"), SUBMIT("제출");

    private String joinType;
}
