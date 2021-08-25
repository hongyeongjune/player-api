package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MatchRepositoryCustom {
    Optional<MatchEntity> fetchByAddressAndFieldNameAndStartTimeAndEndTime(Address address, String fieldName, LocalDateTime startTime, LocalDateTime endTime);
}
