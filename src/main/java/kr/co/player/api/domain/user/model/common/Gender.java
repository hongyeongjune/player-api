package kr.co.player.api.domain.user.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("남자"), FEMALE("여자");

    String gender;

    public static Gender of(String gender) {
        return Arrays.stream(Gender.values())
                .filter(g -> g.toString().equalsIgnoreCase(gender))
                .findAny().orElseThrow(() -> new UserDefineException("Gender 항목을 찾을 수 없습니다."));
    }
}
