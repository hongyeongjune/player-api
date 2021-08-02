package kr.co.player.api.application.club;

import kr.co.player.api.ServiceTest;
import kr.co.player.api.domain.club.model.ClubIntegratedDto;
import kr.co.player.api.domain.club.service.ClubServiceImpl;
import kr.co.player.api.domain.club.service.ClubUserServiceImpl;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.utils.builder.ClubIntegratedBuilder;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ServiceTest
class ClubIntegratedServiceImplTest {

    @InjectMocks
    private ClubIntegratedServiceImpl clubIntegratedService;

    @Mock
    private ClubServiceImpl clubService;

    @Mock
    private ClubUserServiceImpl clubUserService;

    @Test
    void isLeader() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubService.getClub(anyString())).willReturn(clubEntity);
        given(clubUserService.isLeader(clubEntity)).willReturn(true);

        //when
        boolean leader = clubIntegratedService.isLeader(clubEntity.getClubName());

        //then
        assertTrue(leader);
    }

    @Test
    void createClub() {
        //given
        ClubIntegratedDto.CREATE create = ClubIntegratedBuilder.create;
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubService.createClub(any())).willReturn(clubEntity);

        //when then
        assertDoesNotThrow(() -> clubIntegratedService.createClub(create));
    }

    @Test
    void getClubs() {
        //given
        List<ClubEntity> clubEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubEntity.class));
        Page<ClubEntity> clubEntityPage = new PageImpl<>(clubEntityList);
        given(clubService.getClubs(anyInt())).willReturn(clubEntityPage);

        ClubUserEntity clubUserEntity = new EasyRandom().nextObject(ClubUserEntity.class);
        given(clubUserService.getClubLeader(clubEntityPage.getContent().get(0))).willReturn(clubUserEntity);
        given(clubUserService.countClubUser(clubEntityPage.getContent().get(0))).willReturn(1L);

        //when
        Page<ClubIntegratedDto.READ> read = clubIntegratedService.getClubs(anyInt());

        //then
        assertEquals(read.getContent().get(0).getName(), clubEntityPage.getContent().get(0).getClubName());
    }

    @Test
    void getClubsByAddress() {
        //given
        List<ClubEntity> clubEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubEntity.class));
        Page<ClubEntity> clubEntityPage = new PageImpl<>(clubEntityList);
        given(clubService.getClubsByAddress(anyInt(), any(), any())).willReturn(clubEntityPage);

        ClubUserEntity clubUserEntity = new EasyRandom().nextObject(ClubUserEntity.class);
        given(clubUserService.getClubLeader(clubEntityPage.getContent().get(0))).willReturn(clubUserEntity);
        given(clubUserService.countClubUser(clubEntityPage.getContent().get(0))).willReturn(1L);

        //when
        Page<ClubIntegratedDto.READ> read = clubIntegratedService.getClubsByAddress(anyInt(), any(), any());

        //then
        assertEquals(read.getContent().get(0).getName(), clubEntityPage.getContent().get(0).getClubName());
    }

    @Test
    void getClubsByKeyword() {
        //given
        List<ClubEntity> clubEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubEntity.class));
        Page<ClubEntity> clubEntityPage = new PageImpl<>(clubEntityList);
        given(clubService.getClubsByKeywordContains(anyInt(), any())).willReturn(clubEntityPage);

        ClubUserEntity clubUserEntity = new EasyRandom().nextObject(ClubUserEntity.class);
        given(clubUserService.getClubLeader(clubEntityPage.getContent().get(0))).willReturn(clubUserEntity);
        given(clubUserService.countClubUser(clubEntityPage.getContent().get(0))).willReturn(1L);

        //when
        Page<ClubIntegratedDto.READ> read = clubIntegratedService.getClubsByKeyword(anyInt(), any());

        //then
        assertEquals(read.getContent().get(0).getName(), clubEntityPage.getContent().get(0).getClubName());
    }

    @Test
    void updateClub() {
        //given
        ClubIntegratedDto.UPDATE update = new EasyRandom().nextObject(ClubIntegratedDto.UPDATE.class);
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubService.getClub(update.getName())).willReturn(clubEntity);
        given(clubUserService.isLeader(clubEntity)).willReturn(true);

        //when then
        assertDoesNotThrow(() -> clubIntegratedService.updateClub(update));
    }

    @Test
    void updateClubName() {
        //given
        ClubIntegratedDto.UPDATE_CLUB_NAME update = new EasyRandom().nextObject(ClubIntegratedDto.UPDATE_CLUB_NAME.class);
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubService.getClub(update.getOldName())).willReturn(clubEntity);
        given(clubUserService.isLeader(clubEntity)).willReturn(true);
        given(clubService.checkClubName(update.getNewName())).willReturn(false);

        //when then
        assertDoesNotThrow(() -> clubIntegratedService.updateClubName(update));
    }
}