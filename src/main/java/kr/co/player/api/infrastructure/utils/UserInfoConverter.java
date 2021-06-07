package kr.co.player.api.infrastructure.utils;

import kr.co.player.api.infrastructure.error.exception.BadRequestException;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;

import java.time.LocalDate;

public class UserInfoConverter {

    public static Integer getUserAge(UserEntity userEntity) {
        try {
            String birthYear = userEntity.getBirth().substring(0, 4);

            return LocalDate.now().getYear() - Integer.parseInt(birthYear);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUserAddr(UserEntity userEntity) {
        try {
            return userEntity.getAddress().getDistrict();
        } catch (Exception e) {
            return null;
        }
    }

}
