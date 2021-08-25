package kr.co.player.api.domain.match.model;

import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

public class MatchDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CREATE {
        private ClubEntity clubEntity;
        private UserEntity userEntity;
        private String name;
        private Address address;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String fieldName;
    }
}
