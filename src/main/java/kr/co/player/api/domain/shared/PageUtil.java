package kr.co.player.api.domain.shared;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    public static Pageable applyPageConfig(int pageNo, int size) {
        return PageRequest.of(--pageNo, size, Sort.Direction.DESC, "createAt");
    }
}
