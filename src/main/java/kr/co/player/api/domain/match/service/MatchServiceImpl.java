package kr.co.player.api.domain.match.service;

import kr.co.player.api.domain.match.model.MatchDto;
import kr.co.player.api.domain.match.model.common.MatchStatus;
import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;
import kr.co.player.api.infrastructure.persistence.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    @Override
    public void createMatch(MatchDto.CREATE create) {
        matchRepository.save(MatchEntity.builder()
                .name(create.getName())
                .address(create.getAddress())
                .startTime(create.getStartTime())
                .endTime(create.getEndTime())
                .fieldName(create.getFieldName())
                .playerCount(1)
                .matchStatus(MatchStatus.RECRUITMENT)
                .homeClubEntity(create.getClubEntity())
                .homeUserEntity(create.getUserEntity())
                .homeScore(0)
                .build());
    }
}
