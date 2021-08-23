package kr.co.player.api.infrastructure.utils.builder;

import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.domain.shared.JoinStatus;
import org.jeasy.random.EasyRandom;

public class ClubIntegratedBuilder {
    public static ClubIntegratedDto.CREATE create = ClubIntegratedDto.CREATE.builder()
            .name("아름마을 FC")
            .city("경기도")
            .district("구리시")
            .positionType("MF")
            .description("경기도 구리시 아름마을 축구단")
            .build();

    public static ClubIntegratedDto.UPDATE update = ClubIntegratedDto.UPDATE.builder()
            .name("아름마을 FC")
            .city("경기도")
            .district("구리시")
            .description("경기도 구리시 아름마을 축구팀")
            .build();

    public static ClubIntegratedDto.UPDATE_CLUB_NAME updateClubName = ClubIntegratedDto.UPDATE_CLUB_NAME.builder()
            .oldName("아름마을 FC")
            .newName("Liverpool FC")
            .build();

    public static ClubIntegratedDto.UPDATE_INVITATION updateInvitation = ClubIntegratedDto.UPDATE_INVITATION.builder()
            .clubName(new EasyRandom().nextObject(String.class))
            .identity(new EasyRandom().nextObject(String.class))
            .joinStatus(JoinStatus.ACCEPT.name())
            .build();

    public static ClubIntegratedDto.UPDATE_INVITATION updateInvitation(String clubName, String identity, String joinStatus) {
        return ClubIntegratedDto.UPDATE_INVITATION.builder()
                .clubName(clubName)
                .identity(identity)
                .joinStatus(joinStatus)
                .build();
    }

    public static ClubIntegratedDto.UPDATE_INVITATION_DIRECTLY updateInvitation(String clubName, String identity) {
        return ClubIntegratedDto.UPDATE_INVITATION_DIRECTLY.builder()
                .clubName(clubName)
                .identity(identity)
                .build();
    }

    public static ClubIntegratedDto.UPDATE_SUBMIT updateSubmit = ClubIntegratedDto.UPDATE_SUBMIT.builder()
            .clubName(new EasyRandom().nextObject(String.class))
            .identity(new EasyRandom().nextObject(String.class))
            .joinStatus(JoinStatus.ACCEPT.name())
            .build();

    public static ClubIntegratedDto.UPDATE_SUBMIT updateSubmit(String clubName, String identity, String joinStatus) {
        return ClubIntegratedDto.UPDATE_SUBMIT.builder()
                .clubName(clubName)
                .identity(identity)
                .joinStatus(joinStatus)
                .build();
    }

    public static ClubIntegratedDto.CREATE_SUBMIT createSubmit = ClubIntegratedDto.CREATE_SUBMIT.builder()
            .clubName("아스날 FC")
            .message("가입 신청")
            .build();

    public static ClubIntegratedDto.CREATE_INVITATION createInvitation(String identity) {
        return ClubIntegratedDto.CREATE_INVITATION.builder()
                .clubName("아스날 FC")
                .identity(identity)
                .message("클럽 초대")
                .build();
    }
}
