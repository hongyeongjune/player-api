package kr.co.player.api.domain.match.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchLevel {
    BAD("하"), AVERAGE("중"), GOOD("상");

    private String matchLevel;
}
