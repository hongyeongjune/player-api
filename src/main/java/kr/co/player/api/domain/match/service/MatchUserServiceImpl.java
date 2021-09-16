package kr.co.player.api.domain.match.service;

import kr.co.player.api.domain.match.model.MatchUserDto;
import kr.co.player.api.infrastructure.persistence.entity.MatchEntity;
import kr.co.player.api.infrastructure.persistence.entity.MatchUserEntity;
import kr.co.player.api.infrastructure.persistence.repository.MatchUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchUserServiceImpl implements MatchUserService {

    private final MatchUserRepository matchUserRepository;

    @Override
    public void createMatchUser(MatchUserDto.CREATE create) {
        matchUserRepository.save(MatchUserEntity.builder()
                .userEntity(create.getUserEntity())
                .matchType(create.getMatchType())
                .matchUserRole(create.getMatchUserRole())
                .build());
    }

    @Override
    public List<MatchUserEntity> getMatchUserByMatchEntity(MatchEntity matchEntity) {
        return matchUserRepository.fetchByMatchEntity(matchEntity);
    }
}
