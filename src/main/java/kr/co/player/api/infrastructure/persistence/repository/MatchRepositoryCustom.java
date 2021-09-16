package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MatchRepositoryCustom {
    Optional<MatchEntity> fetchByAddressAndFieldNameAndStartTimeAndEndTime(Address address, String fieldName, LocalDateTime startTime, LocalDateTime endTime);
    Page<MatchEntity> fetchMatches(Pageable pageable);
    Page<MatchEntity> fetchByName(Pageable pageable, String name);
    Page<MatchEntity> fetchByClubName(Pageable pageable, String clubName);
    Page<MatchEntity> fetchByIdentity(Pageable pageable, String identity);
    Page<MatchEntity> fetchByFieldName(Pageable pageable, String fieldName);
    Page<MatchEntity> fetchByCity(Pageable pageable, String city);
    Page<MatchEntity> fetchByDistrict(Pageable pageable, String district);
    Page<MatchEntity> fetchByAddress(Pageable pageable, Address address);
    Page<MatchEntity> fetchByTime(Pageable pageable, LocalDateTime startTime, LocalDateTime endTime);
    Optional<MatchEntity> fetchById(Long id);
}
