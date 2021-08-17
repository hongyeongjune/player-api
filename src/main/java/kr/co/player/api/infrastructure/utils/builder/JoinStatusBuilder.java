package kr.co.player.api.infrastructure.utils.builder;

import kr.co.player.api.domain.shared.JoinStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinStatusBuilder {
    public static List<JoinStatus> build() {
        return Arrays.asList(JoinStatus.values());
    }
}
