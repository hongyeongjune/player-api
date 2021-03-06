package kr.co.player.api.domain.integrated.service;

import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
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
    void updateClubInvitationStatusDirectly(ClubIntegratedDto.UPDATE_INVITATION_DIRECTLY update);
}
