package kr.co.player.api.application.club;

import kr.co.player.api.ServiceTest;
import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.domain.club.service.ClubServiceImpl;
import kr.co.player.api.domain.club.service.ClubUserServiceImpl;
import kr.co.player.api.domain.invitation.service.ClubInvitationServiceImpl;
import kr.co.player.api.domain.submit.service.ClubSubmitServiceImpl;
import kr.co.player.api.domain.user.service.UserServiceImpl;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.*;
import kr.co.player.api.infrastructure.utils.builder.ClubIntegratedBuilder;
import kr.co.player.api.infrastructure.utils.builder.ClubUserBuilder;
import kr.co.player.api.infrastructure.utils.builder.UserBuilder;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ServiceTest
class JoinIntegratedServiceImplTest {

    @InjectMocks
    private JoinIntegratedServiceImpl joinIntegratedService;

    @Mock
    private ClubServiceImpl clubService;
    @Mock
    private ClubUserServiceImpl clubUserService;
    @Mock
    private ClubSubmitServiceImpl clubSubmitService;
    @Mock
    private ClubInvitationServiceImpl clubInvitationService;
    @Mock
    private UserServiceImpl userService;

    @Test
    void createClubSubmit() {
        //given
        ClubIntegratedDto.CREATE_SUBMIT create = new EasyRandom().nextObject(ClubIntegratedDto.CREATE_SUBMIT.class);
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubService.getClub(create.getClubName())).willReturn(clubEntity);
        given(clubSubmitService.getClubSubmitByWaiting(any())).willReturn(Optional.empty());
        given(clubUserService.getClubUserEntity(any(), any())).willReturn(Optional.empty());
        given(clubSubmitService.countClubSubmitByUserEntityNotWaiting()).willReturn(1L);
        ReflectionTestUtils.setField(joinIntegratedService, "SUBMIT_LIMIT", 10);

        //when then
        assertDoesNotThrow(() -> joinIntegratedService.createClubSubmit(create));
    }

    @Test
    void createClubInvitation() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            ClubIntegratedDto.CREATE_INVITATION create = new EasyRandom().nextObject(ClubIntegratedDto.CREATE_INVITATION.class);

            ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
            given(clubService.getClub(create.getClubName())).willReturn(clubEntity);

            UserEntity clubLeaderEntity = UserBuilder.HongYeongJune;
            utl.when(UserThreadLocal::get).thenReturn(clubLeaderEntity);

            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            given(userService.getUserEntity(create.getIdentity())).willReturn(userEntity);

            ClubUserEntity clubUserEntity = ClubUserBuilder.leader(clubEntity, clubLeaderEntity);
            given(clubUserService.getClubUserEntity(clubEntity, clubLeaderEntity)).willReturn(Optional.ofNullable(clubUserEntity));

            given(clubUserService.getClubUserEntity(clubEntity, userEntity)).willReturn(Optional.empty());

            given(clubInvitationService.getClubInvitationByWaiting(clubUserEntity, userEntity)).willReturn(Optional.empty());

            assertDoesNotThrow(() -> joinIntegratedService.createClubInvitation(create));
        }
    }

    @Test
    void getClubSubmits() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubService.getClub(anyString())).willReturn(clubEntity);

        given(clubUserService.isLeader(clubEntity)).willReturn(true);

        List<ClubSubmitEntity> clubSubmitEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubSubmitEntity.class));
        Page<ClubSubmitEntity> clubSubmitEntityPage = new PageImpl<>(clubSubmitEntityList);
        given(clubSubmitService.getClubSubmitsByClubEntity(anyInt(), any())).willReturn(clubSubmitEntityPage);

        //when
        Page<ClubIntegratedDto.READ_SUBMIT> readPage = joinIntegratedService.getClubSubmits(1, "clubName");

        //then
        assertNotNull(readPage);
        assertEquals(readPage.getContent().get(0).getIdentity(), clubSubmitEntityPage.getContent().get(0).getUserEntity().getIdentity());
    }

    @Test
    void getClubInvitations() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubService.getClub(anyString())).willReturn(clubEntity);

        given(clubUserService.isLeader(clubEntity)).willReturn(true);

        List<ClubInvitationEntity> clubInvitationEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubInvitationEntity.class));
        Page<ClubInvitationEntity> clubInvitationEntityPage = new PageImpl<>(clubInvitationEntityList);
        given(clubInvitationService.getClubInvitations(anyInt(), any())).willReturn(clubInvitationEntityPage);

        //when
        Page<ClubIntegratedDto.READ_INVITATION> readPage = joinIntegratedService.getClubInvitations(1, "clubName");

        //then
        assertNotNull(readPage);
        assertEquals(readPage.getContent().get(0).getIdentity(), clubInvitationEntityPage.getContent().get(0).getUserEntity().getIdentity());
    }

    @Test
    void updateClubSubmitStatus() {
        //given
        ClubIntegratedDto.UPDATE_SUBMIT update = ClubIntegratedBuilder.updateSubmit;

        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubService.getClub(update.getClubName())).willReturn(clubEntity);

        UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
        given(userService.getUserEntity(update.getIdentity())).willReturn(userEntity);

        //when then
        assertDoesNotThrow(() -> joinIntegratedService.updateClubSubmitStatus(update));
    }

    @Test
    void updateClubSubmitStatusDirectly() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        given(clubService.getClub(anyString())).willReturn(clubEntity);

        //when then
        assertDoesNotThrow(() -> joinIntegratedService.updateClubSubmitStatusDirectly("clubName"));
    }

    @Test
    void updateClubInvitationStatus() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            ClubIntegratedDto.UPDATE_INVITATION update = ClubIntegratedBuilder.updateInvitation;

            ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
            given(clubService.getClub(update.getClubName())).willReturn(clubEntity);

            UserEntity clubLeaderEntity = UserBuilder.HongYeongJune;
            given(userService.getUserEntity(update.getIdentity())).willReturn(clubLeaderEntity);

            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);

            ClubUserEntity clubUserEntity = new EasyRandom().nextObject(ClubUserEntity.class);
            given(clubUserService.getClubUserEntity(clubEntity, clubLeaderEntity)).willReturn(Optional.ofNullable(clubUserEntity));

            //when then
            assertDoesNotThrow(() -> joinIntegratedService.updateClubInvitationStatus(update));
        }
    }

    @Test
    void updateClubInvitationStatusDirectly() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            ClubIntegratedDto.UPDATE_INVITATION_DIRECTLY update = new EasyRandom().nextObject(ClubIntegratedDto.UPDATE_INVITATION_DIRECTLY.class);

            ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
            given(clubService.getClub(update.getClubName())).willReturn(clubEntity);

            UserEntity clubLeaderEntity = UserBuilder.HongYeongJune;
            utl.when(UserThreadLocal::get).thenReturn(clubLeaderEntity);

            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            given(userService.getUserEntity(update.getIdentity())).willReturn(userEntity);

            ClubUserEntity clubUserEntity = ClubUserBuilder.build(clubEntity, clubLeaderEntity);
            given(clubUserService.getClubUserEntity(clubEntity, clubLeaderEntity)).willReturn(Optional.ofNullable(clubUserEntity));

            //when then
            assertDoesNotThrow(() -> joinIntegratedService.updateClubInvitationStatusDirectly(update));
        }
    }
}