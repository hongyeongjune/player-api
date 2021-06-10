package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.RepositoryTest;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.domain.shared.PageUtil;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class ClubRepositoryCustomTest {

    @Autowired
    private ClubRepository clubRepository;

    @Test
    void fetchClubs() {
        //given
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        //when
        Page<ClubEntity> clubEntityPage = clubRepository.fetchClubs(PageUtil.applyPageConfig(1, 10));

        //then
        assertNotNull(clubEntityPage);
        assertEquals(clubEntityPage.getContent().get(0).getClubName(), clubEntity.getClubName());
    }

    @Test
    void fetchClubsByAddress() {
        //given
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        //when
        Page<ClubEntity> clubEntityPage = clubRepository.fetchClubsByAddress(
                PageUtil.applyPageConfig(1, 10),
                Arrays.asList(new Address(clubEntity.getAddress().getCity(), clubEntity.getAddress().getDistrict())));

        Page<ClubEntity> notSavedClubEntityPage = clubRepository.fetchClubsByAddress(
                PageUtil.applyPageConfig(1, 10),
                Arrays.asList(new Address("Not Exist city", "Not exist District")));

        //then
        assertEquals(notSavedClubEntityPage.getTotalPages(), 0);
        assertEquals(clubEntityPage.getTotalPages(), 1);
        assertEquals(clubEntityPage.getContent().get(0), clubEntity);
    }

    @Test
    void fetchClubsByKeywordContains() {
        //given
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        //when
        Page<ClubEntity> clubEntityPage = clubRepository.fetchClubsByKeywordContains(PageUtil.applyPageConfig(1, 10), clubEntity.getClubName());
        Page<ClubEntity> notSavedClubEntityPage = clubRepository.fetchClubsByKeywordContains(PageUtil.applyPageConfig(1, 10), "Not exist keyword");

        //then
        assertEquals(notSavedClubEntityPage.getTotalPages(), 0);
        assertEquals(clubEntityPage.getTotalPages(), 1);
        assertEquals(clubEntityPage.getContent().get(0), clubEntity);
    }

}