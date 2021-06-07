package kr.co.player.api.domain.match.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchUserRole {
    USER("클럽원"), MERCENARY("용병");

    private String matchUserRole;
}

