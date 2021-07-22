package kr.co.player.api.domain.club.service;

import kr.co.player.api.domain.club.model.ClubUserDto;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;

import java.util.List;

public interface ClubUserService {

    //common - integrated
    boolean isLeader(ClubEntity clubEntity);

    //create - integrated
    void createClubFromLeader(ClubUserDto.CREATE_LEADER create);
    void createClubUser(ClubUserDto.CREATE create);

    //read - integrated
    ClubUserEntity getClubLeader(ClubEntity clubEntity);
    Long countClubUser(ClubEntity clubEntity);
    ClubUserEntity getClubUserEntity(ClubEntity clubEntity, UserEntity userEntity);

    //read - service
    List<ClubUserDto.READ_MY_CLUB> getMyClubs();
}
