package kr.co.player.api.domain.shared.test;

import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.model.common.Gender;
import kr.co.player.api.domain.user.model.common.UserPhone;
import kr.co.player.api.domain.user.model.common.UserRole;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;

public class UserBuilder {

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
