package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubRepositoryCustom {
    Page<ClubEntity> fetchClubs(Pageable pageable);
    Page<ClubEntity> fetchClubsByAddress(Pageable pageable, List<Address> addressList);
    Page<ClubEntity> fetchClubsByKeywordContains(Pageable pageable, String keyword);
}
