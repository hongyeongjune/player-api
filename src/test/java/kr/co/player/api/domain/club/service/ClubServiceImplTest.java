package kr.co.player.api.domain.club.service;

import kr.co.player.api.ServiceTest;
import kr.co.player.api.domain.club.model.ClubDto;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.infrastructure.error.exception.DuplicatedException;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ServiceTest
class ClubServiceImplTest {

    @InjectMocks
    private ClubServiceImpl clubService;

    @Mock
    private ClubRepository clubRepository;

    @Test
    void checkClubName() {
        //given
        given(clubRepository.existsByClubName(anyString())).willReturn(false);

        boolean isSuccess = clubService.checkClubName(anyString());

        assertTrue(!isSuccess);
    }

    @Test
    void createClub() {
        //given
        ClubDto.CREATE create = new EasyRandom().nextObject(ClubDto.CREATE.class);
        given(clubRepository.existsByClubName(anyString())).willReturn(false);

        //when then
        assertDoesNotThrow(() -> clubService.createClub(create));
    }

    @Test
    void createClub_클럽이름중복() {
        //given
        ClubDto.CREATE create = new EasyRandom().nextObject(ClubDto.CREATE.class);
        given(clubRepository.existsByClubName(anyString())).willReturn(true);

        //when then
        assertThrows(DuplicatedException.class, () -> clubService.createClub(create));
    }

    @Test
    void getClubs() {
        //given
        List<ClubEntity> clubEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubEntity.class));
        Page<ClubEntity> clubEntityPage = new PageImpl<>(clubEntityList);
        given(clubRepository.fetchClubs(any())).willReturn(clubEntityPage);

        //when
        Page<ClubEntity> savedClubEntityPage = clubService.getClubs(1);

        //then
        assertNotNull(savedClubEntityPage);
        assertEquals(savedClubEntityPage.getContent().get(0).getClubName(), clubEntityPage.getContent().get(0).getClubName());
    }

    @Test
    void getClub() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubRepository.findByClubName(anyString())).willReturn(Optional.ofNullable(clubEntity));

        //when
        ClubEntity savedClubEntity = clubService.getClub(clubEntity.getClubName());

        //then
        assertNotNull(savedClubEntity);
        assertEquals(savedClubEntity.getClubName(), clubEntity.getClubName());
    }

    @Test
    void getClub_클럽이존재하지않음() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubRepository.findByClubName(anyString())).willThrow(NotFoundException.class);

        //when then
        assertThrows(NotFoundException.class, () -> clubService.getClub(clubEntity.getClubName()));
    }

    @Test
    void getClubsByAddress() {
        //given
        List<ClubEntity> clubEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubEntity.class));
        Page<ClubEntity> clubEntityPage = new PageImpl<>(clubEntityList);
        given(clubRepository.fetchClubsByAddress(any(), any(), any())).willReturn(clubEntityPage);

        List<String> districtList = Collections.singletonList(new EasyRandom().nextObject(String.class));
        List<String> cityList = Collections.singletonList(new EasyRandom().nextObject(String.class));

        //when
        Page<ClubEntity> savedClubEntityPage = clubService.getClubsByAddress(1, districtList, cityList);

        //then
        assertNotNull(savedClubEntityPage);
        assertEquals(savedClubEntityPage.getContent().get(0).getClubName(), clubEntityPage.getContent().get(0).getClubName());

    }

    @Test
    void getClubsByKeywordContains() {
        //given
        List<ClubEntity> clubEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubEntity.class));
        Page<ClubEntity> clubEntityPage = new PageImpl<>(clubEntityList);
        given(clubRepository.fetchClubsByKeywordContains(any(), any())).willReturn(clubEntityPage);
        String keyword = clubEntityList.get(0).getClubName();

        //when
        Page<ClubEntity> savedClubEntityPage = clubService.getClubsByKeywordContains(1, keyword);

        //then
        assertNotNull(savedClubEntityPage);
        assertEquals(savedClubEntityPage.getContent().get(0).getClubName(), clubEntityPage.getContent().get(0).getClubName());

    }

    @Test
    void updateClub() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        ClubDto.UPDATE update = new EasyRandom().nextObject(ClubDto.UPDATE.class);

        //when then
        assertDoesNotThrow(() -> clubService.updateClub(clubEntity, update));
    }
}