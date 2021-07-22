package kr.co.player.api.domain.club.service;

import kr.co.player.api.domain.club.model.ClubDto;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.domain.shared.PageUtil;
import kr.co.player.api.infrastructure.error.exception.DuplicatedException;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.error.model.ErrorCode;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    private final static int LIMIT = 10;

    @Override
    public boolean checkClubName(String clubName) {
        return clubRepository.existsByClubName(clubName);
    }

    @Override
    public ClubEntity createClub(ClubDto.CREATE create) {

        if(clubRepository.existsByClubName(create.getName())) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
        }

        return clubRepository.save(ClubEntity.builder()
                .clubName(create.getName())
                .address(new Address(create.getCity(), create.getDistrict()))
                .description(create.getDescription())
                .rating(0)
                .build());
    }

    @Override
    public Page<ClubEntity> getClubs(int pageNo) {
        return clubRepository.fetchClubs(PageUtil.applyPageConfig(pageNo, LIMIT));
    }

    @Override
    public ClubEntity getClub(String clubName) {
        return clubRepository.findByClubName(clubName).orElseThrow(() -> new NotFoundException("ClubEntity"));
    }

    @Override
    public Page<ClubEntity> getClubsByAddress(int pageNo, List<String> districtList, List<String> cityList) {
        return clubRepository.fetchClubsByAddress(PageUtil.applyPageConfig(pageNo, LIMIT), districtList, cityList);
    }

    @Override
    public Page<ClubEntity> getClubsByKeywordContains(int pageNo, String keyword) {
        return clubRepository.fetchClubsByKeywordContains(PageUtil.applyPageConfig(pageNo, LIMIT), keyword);
    }

    @Override
    public void updateClub(ClubEntity clubEntity, ClubDto.UPDATE update) {
        clubEntity.update(update);
        clubRepository.save(clubEntity);
    }
}
