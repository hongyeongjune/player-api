package kr.co.player.api.domain.submit.service;

import kr.co.player.api.ServiceTest;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.domain.submit.model.ClubSubmitDto;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubSubmitEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubSubmitRepository;
import kr.co.player.api.infrastructure.utils.builder.ClubSubmitBuilder;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ServiceTest
class ClubSubmitServiceImplTest {

    @InjectMocks
    private ClubSubmitServiceImpl clubSubmitService;

    @Mock
    private ClubSubmitRepository clubSubmitRepository;

    @Test
    void createSubmit() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
            ClubSubmitDto.CREATE create = ClubSubmitBuilder.create(clubEntity);

            //when then
            assertDoesNotThrow(() -> clubSubmitService.createSubmit(create));
        }
    }

    @Test
    void getClubSubmitsByClubEntity() {
        //given
        List<ClubSubmitEntity> clubSubmitEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubSubmitEntity.class));
        Page<ClubSubmitEntity> clubSubmitEntityPage = new PageImpl<>(clubSubmitEntityList);
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubSubmitRepository.fetchClubSubmitByClubEntity(any(), any())).willReturn(clubSubmitEntityPage);

        //when
        Page<ClubSubmitEntity> savedClubSubmitEntityPage = clubSubmitService.getClubSubmitsByClubEntity(1, clubEntity);

        //then
        assertNotNull(savedClubSubmitEntityPage);
        assertEquals(savedClubSubmitEntityPage.getContent().get(0).getUserEntity().getIdentity(), clubSubmitEntityPage.getContent().get(0).getUserEntity().getIdentity());
    }

    @Test
    void countClubSubmitByUserEntityNotWaiting() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            given(clubSubmitRepository.countClubSubmitByUserEntityAndNotWaiting(any())).willReturn(1L);

            //when
            Long count = clubSubmitService.countClubSubmitByUserEntityNotWaiting();

            //then
            assertEquals(count, 1L);
        }
    }

    @Test
    void getClubSubmitByWaiting() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
            ClubSubmitEntity clubSubmitEntity = ClubSubmitBuilder.build(clubEntity, userEntity);
            given(clubSubmitRepository.fetchClubSubmitByUserEntityAndClubEntityAndWaiting(userEntity, clubEntity)).willReturn(Optional.ofNullable(clubSubmitEntity));

            //when
            ClubSubmitEntity savedClubSubmitEntity = clubSubmitService.getClubSubmitByWaiting(clubEntity).orElse(null);

            //then
            assertNotNull(savedClubSubmitEntity);
            assertEquals(savedClubSubmitEntity.getUserEntity().getIdentity(), clubSubmitEntity.getUserEntity().getIdentity());
        }
    }

    @Test
    void getClubSubmits() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            List<ClubSubmitEntity> clubSubmitEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubSubmitEntity.class));
            Page<ClubSubmitEntity> clubSubmitEntityPage = new PageImpl<>(clubSubmitEntityList);
            given(clubSubmitRepository.fetchClubSubmitByUserEntity(any(), any())).willReturn(clubSubmitEntityPage);

            //when
            Page<ClubSubmitDto.READ> readPage = clubSubmitService.getClubSubmits(1);

            //then
            assertNotNull(readPage);
            assertEquals(readPage.getContent().get(0).getClubName(), clubSubmitEntityPage.getContent().get(0).getClubEntity().getClubName());
        }
    }

    @Test
    void getClubSubmitsByJoinStatus() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            List<ClubSubmitEntity> clubSubmitEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubSubmitEntity.class));
            Page<ClubSubmitEntity> clubSubmitEntityPage = new PageImpl<>(clubSubmitEntityList);
            given(clubSubmitRepository.fetchClubSubmitByUserEntityAndJoinStatus(any(), any(), any())).willReturn(clubSubmitEntityPage);
            List<String> joinStatusList = new ArrayList<>();
            joinStatusList.add(JoinStatus.WAITING.name());

            //when
            Page<ClubSubmitDto.READ> readPage = clubSubmitService.getClubSubmitsByJoinStatus(1, joinStatusList);

            //then
            assertNotNull(readPage);
            assertEquals(readPage.getContent().get(0).getClubName(), clubSubmitEntityPage.getContent().get(0).getClubEntity().getClubName());
        }
    }

    @Test
    void updateJoinStatus() {
        //given
        ClubSubmitEntity clubSubmitEntity = new EasyRandom().nextObject(ClubSubmitEntity.class);
        given(clubSubmitRepository.fetchClubSubmitByUserEntityAndClubEntityAndWaiting(any(), any())).willReturn(Optional.ofNullable(clubSubmitEntity));
        ClubSubmitDto.UPDATE update = new EasyRandom().nextObject(ClubSubmitDto.UPDATE.class);

        //when then
        assertDoesNotThrow(() -> clubSubmitService.updateJoinStatus(update));
    }
}