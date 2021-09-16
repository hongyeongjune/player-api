package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;
import kr.co.player.api.infrastructure.persistence.entity.MatchUserEntity;

import java.util.List;

public interface MatchUserRepositoryCustom {
    List<MatchUserEntity> fetchByMatchEntity(MatchEntity matchEntity);
}
