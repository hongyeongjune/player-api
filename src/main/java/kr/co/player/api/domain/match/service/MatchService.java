package kr.co.player.api.domain.match.service;

import kr.co.player.api.domain.match.model.MatchDto;
import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MatchService {

    //create - integrated
    void createMatch(MatchDto.CREATE create);
}
