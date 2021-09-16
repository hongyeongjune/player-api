package kr.co.player.api.domain.match.service;

import kr.co.player.api.domain.match.model.MatchUserDto;
import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;
import kr.co.player.api.infrastructure.persistence.entity.MatchUserEntity;

import java.util.List;

public interface MatchUserService {
    //create - integrated
    void createMatchUser(MatchUserDto.CREATE create);

    //read - integrated
    List<MatchUserEntity> getMatchUserByMatchEntity(MatchEntity matchEntity);
}
