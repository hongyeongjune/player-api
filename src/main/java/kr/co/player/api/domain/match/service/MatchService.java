package kr.co.player.api.domain.match.service;

import kr.co.player.api.domain.match.model.MatchDto;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MatchService {

    //create - integrated
    void createMatch(MatchDto.CREATE create);

    //read - integrated
    Optional<MatchEntity> getMatchEntityByAddressAndFieldNameAndStartTimeAndEndTime(Address address, String fieldName, LocalDateTime startTime, LocalDateTime endTime);
    Optional<MatchEntity> getMatchEntity(Long id);

    //read - service
    Page<MatchDto.READ> getMatches(int pageNo);
    Page<MatchDto.READ> getMatchesByName(int pageNo, String name);
    Page<MatchDto.READ> getMatchesByClubName(int pageNo, String clubName);
    Page<MatchDto.READ> getMatchesByIdentity(int pageNo, String identity);
    Page<MatchDto.READ> getMatchesByFieldName(int pageNo, String fieldName);
    Page<MatchDto.READ> getMatchesByCity(int pageNo, String city);
    Page<MatchDto.READ> getMatchesByDistrict(int pageNo, String district);
    Page<MatchDto.READ> getMatchesByAddress(int pageNo, Address address);
    Page<MatchDto.READ> getMatchesByTime(int pageNo, LocalDateTime startTime, LocalDateTime endTime);
}
