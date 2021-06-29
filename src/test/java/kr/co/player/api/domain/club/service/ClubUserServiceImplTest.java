package kr.co.player.api.domain.club.service;

import kr.co.player.api.ServiceTest;
import kr.co.player.api.domain.club.model.ClubUserDto;
import kr.co.player.api.domain.club.model.common.ClubUserRole;
import kr.co.player.api.domain.shared.test.ClubUserBuilder;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubUserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ServiceTest
class ClubUserServiceImplTest {

    @InjectMocks
    private ClubUserServiceImpl clubUserService;

    @Mock
    private ClubUserRepository clubUserRepository;

    @Test
    void isLeader() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            ClubUserEntity clubUserEntity = ClubUserBuilder.build(clubEntity, userEntity);
            given(clubUserRepository.fetchByClubEntityAndUserEntity(clubEntity, userEntity)).willReturn(Optional.ofNullable(clubUserEntity));

            //when
            boolean isLeader = clubUserService.isLeader(clubEntity);

            //then
            if (clubUserEntity.getClubUserRole().equals(ClubUserRole.LEADER)) {
                assertTrue(isLeader);
            } else {
                assertFalse(isLeader);
            }
        }
    }

    @Test
    void createClubFromLeader() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            ClubUserDto.CREATE_LEADER create = ClubUserBuilder.create(new EasyRandom().nextObject(ClubEntity.class));
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            List<ClubUserEntity> clubUserEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubUserEntity.class));
            ReflectionTestUtils.setField(clubUserService, "CLUB_LIMIT", 4);
            given(clubUserRepository.fetchByUserEntity(userEntity)).willReturn(clubUserEntityList);

            //when then
            assertDoesNotThrow(()-> clubUserService.createClubFromLeader(create));
        }
    }

    @Test
    void createClubUser() {
        //given
        UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        ClubUserDto.CREATE create = ClubUserDto.CREATE.builder()
                .userEntity(userEntity)
                .clubEntity(clubEntity)
                .build();

        //when then
        assertDoesNotThrow(() -> clubUserService.createClubUser(create));
    }

    @Test
    void getClubLeader() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        ClubUserEntity clubUserEntity = new EasyRandom().nextObject(ClubUserEntity.class);
        given(clubUserRepository.fetchByClubEntityAndClubUserRole(any())).willReturn(Optional.ofNullable(clubUserEntity));

        //when
        ClubUserEntity leader = clubUserService.getClubLeader(clubEntity);

        //then
        assertEquals(clubUserEntity.getClubUserRole().getRole(), leader.getClubUserRole().getRole());
    }

    @Test
    void countClubUser() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        long count = new EasyRandom().nextLong();
        given(clubUserRepository.countByClubEntity(any())).willReturn(count);

        //when
        Long countClubUser = clubUserService.countClubUser(clubEntity);

        //then
        assertEquals(countClubUser, count);
    }

    @Test
    void getClubUserEntity() {
        //given
        UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);

        ClubUserEntity clubUserEntity = new EasyRandom().nextObject(ClubUserEntity.class);
        given(clubUserRepository.fetchByClubEntityAndUserEntity(any(), any())).willReturn(Optional.ofNullable(clubUserEntity));

        //when
        ClubUserEntity savedClubUserEntity = clubUserService.getClubUserEntity(clubEntity, userEntity);

        //then
        assertEquals(clubUserEntity.getUserEntity().getIdentity(), savedClubUserEntity.getUserEntity().getIdentity());

    }

    @Test
    void getMyClubs() {
        //given
        try(MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            List<ClubUserEntity> clubUserEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubUserEntity.class));
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            given(clubUserRepository.fetchByUserEntity(userEntity)).willReturn(clubUserEntityList);

            //when
            List<ClubUserDto.READ_MY_CLUB> read = clubUserService.getMyClubs();

            //then
            assertEquals(read.get(0).getName(), clubUserEntityList.get(0).getClubEntity().getClubName());
        }
    }
}