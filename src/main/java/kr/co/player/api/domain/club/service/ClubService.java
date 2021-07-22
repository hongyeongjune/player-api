package kr.co.player.api.domain.club.service;

import kr.co.player.api.domain.club.model.ClubDto;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClubService {

    //common - service
    boolean checkClubName(String clubName);

    //create - integrated
    ClubEntity createClub(ClubDto.CREATE create);

    //read - integrated
    Page<ClubEntity> getClubs(int pageNo);
    ClubEntity getClub(String clubName);
    Page<ClubEntity> getClubsByAddress(int pageNo, List<String> districtList, List<String> cityList);
    Page<ClubEntity> getClubsByKeywordContains(int pageNo, String keyword);

    //update - integrated
    void updateClub(ClubEntity clubEntity, ClubDto.UPDATE update);
}
