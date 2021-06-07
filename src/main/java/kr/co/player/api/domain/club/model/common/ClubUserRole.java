package kr.co.player.api.domain.club.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClubUserRole {
    USER("팀원"), LEADER("리더");

    private String role;
}
