package kr.co.player.api.domain.match.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchType {
    HOME("홈"), AWAY("어웨이");

    private String matchType;
}
