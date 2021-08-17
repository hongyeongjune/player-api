package kr.co.player.api.domain.integrated;

import kr.co.player.api.domain.club.model.ClubIntegratedDto;
import org.springframework.data.domain.Page;

public interface JoinIntegratedService {

    //create
    void createClubSubmit(ClubIntegratedDto.CREATE_SUBMIT create);
    void createClubInvitation(ClubIntegratedDto.CREATE_INVITATION create);

    //read
    Page<ClubIntegratedDto.READ_SUBMIT> getClubSubmits(int pageNo, String clubName);
    Page<ClubIntegratedDto.READ_INVITATION> getClubInvitations(int pageNo, String clubName);

    //update
    void updateClubSubmitStatus(ClubIntegratedDto.UPDATE_SUBMIT update);
    void updateClubSubmitStatusDirectly(String clubName);
    void updateClubInvitationStatus(ClubIntegratedDto.UPDATE_INVITATION update);
}
