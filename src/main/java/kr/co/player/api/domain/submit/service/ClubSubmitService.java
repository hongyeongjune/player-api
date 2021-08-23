package kr.co.player.api.domain.submit.service;

import kr.co.player.api.domain.submit.model.ClubSubmitDto;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubSubmitEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ClubSubmitService {

    //create - integrated
    void createSubmit(ClubSubmitDto.CREATE create);

    //read - integrated
    Page<ClubSubmitEntity> getClubSubmitsByClubEntity(int pageNo, ClubEntity clubEntity);
    Long countClubSubmitByUserEntityNotWaiting();
    Optional<ClubSubmitEntity> getClubSubmitByWaiting(ClubEntity clubEntity);
    Optional<ClubSubmitEntity> getClubSubmitByWaiting(ClubEntity clubEntity, UserEntity userEntity);

    //read - service
    Page<ClubSubmitDto.READ> getClubSubmits(int pageNo);
    Page<ClubSubmitDto.READ> getClubSubmitsByJoinStatus(int pageNo, List<String> joinStatusList);

    //update - integrated
    void updateJoinStatus(ClubSubmitDto.UPDATE update);
}
