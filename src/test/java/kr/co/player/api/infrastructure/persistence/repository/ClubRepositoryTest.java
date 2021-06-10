package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.RepositoryTest;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class ClubRepositoryTest {

    @Autowired
    private ClubRepository clubRepository;

    @Test
    void existsByClubName() {
        //given
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        //when
        boolean isExist = clubRepository.existsByClubName(clubEntity.getClubName());
        boolean isNotExist = clubRepository.existsByClubName("ClubName does not exist");

        //then
        assertTrue(isExist);
        assertFalse(isNotExist);
    }

    @Test
    void findByClubName() {
        //given
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        //when
        ClubEntity savedClubEntity = clubRepository.findByClubName(clubEntity.getClubName()).orElse(null);
        ClubEntity notSavedClubEntity = clubRepository.findByClubName("ClubName does not exist").orElse(null);

        //then
        assertNull(notSavedClubEntity);
        assertNotNull(savedClubEntity);
        assertEquals(savedClubEntity.getClubName(), savedClubEntity.getClubName());
    }
}