package kr.co.player.api.domain.match.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchPlayerType {
    STARTING_PLAYERS("선발"), SUBSTITUTE("후보");

    private String matchPlayerType;
}
