package kr.co.player.api.infrastructure.utils.builder;

import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.model.common.Gender;
import kr.co.player.api.domain.user.model.common.UserPhone;
import kr.co.player.api.domain.user.model.common.UserRole;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;

public class UserBuilder {

    public static UserEntity target = UserEntity.builder()
            .identity("targetUser")
            .password("password")
            .build();

    public static UserEntity build() {
        return UserEntity.builder()
                .identity("hongyeongjune")
                .password("password")
                .name("홍영준")
                .userPhone(new UserPhone("010-6479-4834"))
                .birth("19951217")
                .gender(Gender.MALE)
                .role(UserRole.USER)
                .build();
    }

    public static UserEntity HongYeongJune = UserEntity.builder()
            .identity("leaderHong")
            .password("password")
            .name("홍영준")
            .userPhone(new UserPhone("010-6479-4834"))
            .birth("19951217")
            .gender(Gender.MALE)
            .role(UserRole.USER)
            .build();

    public static UserEntity KimDongWook = UserEntity.builder()
                .identity("kimdongwook")
                .password("password")
                .name("김동욱")
                .userPhone(new UserPhone("010-6888-4650"))
                .birth("19950909")
                .gender(Gender.MALE)
                .role(UserRole.USER)
                .build();

    public static UserEntity LeeYeChan = UserEntity.builder()
            .identity("leeyechan")
            .password("password")
            .name("이예찬")
            .userPhone(new UserPhone("010-3632-8406"))
            .birth("19950401")
            .gender(Gender.MALE)
            .role(UserRole.USER)
            .build();

    public static UserEntity LeeJaeSung = UserEntity.builder()
            .identity("leejaesung")
            .password("password")
            .name("이재성")
            .userPhone(new UserPhone("010-3892-0299"))
            .birth("19950309")
            .gender(Gender.MALE)
            .role(UserRole.USER)
            .build();

    public static UserEntity SunJuHo = UserEntity.builder()
            .identity("sunjuho")
            .password("password")
            .name("선주호")
            .userPhone(new UserPhone("010-3316-7056"))
            .birth("19950404")
            .gender(Gender.MALE)
            .role(UserRole.USER)
            .build();

    public static UserEntity MinJaeHong = UserEntity.builder()
            .identity("minjaehong")
            .password("password")
            .name("민재홍")
            .userPhone(new UserPhone("010-7770-5106"))
            .birth("19950304")
            .gender(Gender.MALE)
            .role(UserRole.USER)
            .build();

    public static UserDto.CREATE create = UserDto.CREATE.builder()
            .identity("hongyeongjune")
            .password("password")
            .name("홍영준")
            .userPhone("010-6479-4834")
            .birth("19951217")
            .gender("MALE")
            .role("USER")
            .build();

    public static UserDto.UPDATE update = UserDto.UPDATE.builder()
            .name("홍영준")
            .city("경기도")
            .district("구리시")
            .positionType("MF")
            .mainPosition("CDM")
            .subPosition("RB")
            .userPhone("010-6479-4834")
            .height(70)
            .weight(186)
            .build();
}
