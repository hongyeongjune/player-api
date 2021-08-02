package kr.co.player.api.domain.integrated;

import kr.co.player.api.domain.club.model.ClubIntegratedDto;
import kr.co.player.api.domain.shared.Address;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClubIntegratedService {

    //common
    boolean isLeader(String clubName);

    //create
    void createClub(ClubIntegratedDto.CREATE create);

    //read
    Page<ClubIntegratedDto.READ> getClubs(int pageNo);
    Page<ClubIntegratedDto.READ> getClubsByAddress(int pageNo, List<String> districtList, List<String> cityList);
    Page<ClubIntegratedDto.READ> getClubsByKeyword(int pageNo, String keyword);

    //update
    void updateClub(ClubIntegratedDto.UPDATE update);
    void updateClubName(ClubIntegratedDto.UPDATE_CLUB_NAME update);
}
