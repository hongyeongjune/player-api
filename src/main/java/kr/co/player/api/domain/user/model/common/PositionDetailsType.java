package kr.co.player.api.domain.user.model.common;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PositionDetailsType {
    LW("왼쪽 윙어"), ST("스트라이커"), CF("센터 포워드"), RW("오른쪽 윙어"),
    LM("왼쪽 미드필더"), CM("중앙 미드필더"), CDM("수비형 미드필더"), RM("오른쪽 미드필더"),
    LWB("왼쪽 윙백"), LB("왼쪽 풀백"), CB("센터백"), RB("오른쪽 풀백"), RWB("오른쪽 윙백"),
    GK("골키퍼");

    private String positionDetailsType;

    public static PositionDetailsType of(String positionDetailsType) {
        return Arrays.stream(PositionDetailsType.values())
                .filter(positionDetails -> positionDetails.toString().equalsIgnoreCase(positionDetailsType))
                .findAny().orElseThrow(() -> new UserDefineException("PositionDetailsType 항목을 찾을 수 없습니다."));
    }
}
