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

    public static ClubIntegratedDto.UPDATE_SUBMIT updateSubmit = ClubIntegratedDto.UPDATE_SUBMIT.builder()
            .clubName(new EasyRandom().nextObject(String.class))
            .identity(new EasyRandom().nextObject(String.class))
            .joinStatus(JoinStatus.ACCEPT.name())
            .build();
}
