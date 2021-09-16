package kr.co.player.api.domain.match.model;

import kr.co.player.api.domain.match.model.common.MatchType;
import kr.co.player.api.domain.match.model.common.MatchUserRole;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.*;

public class MatchUserDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE {
        private UserEntity userEntity;
        private MatchType matchType;
        private MatchUserRole matchUserRole;
    }
}
