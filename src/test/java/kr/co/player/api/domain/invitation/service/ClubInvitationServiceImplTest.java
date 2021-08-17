package kr.co.player.api.domain.invitation.service;

import kr.co.player.api.ServiceTest;
import kr.co.player.api.domain.invitation.model.ClubInvitationDto;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubInvitationEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubInvitationRepository;
import kr.co.player.api.infrastructure.utils.builder.ClubInvitationBuilder;
import kr.co.player.api.infrastructure.utils.builder.ClubUserBuilder;
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
class ClubInvitationServiceImplTest {

    @InjectMocks
    private ClubInvitationServiceImpl clubInvitationService;

    @Mock
    private ClubInvitationRepository clubInvitationRepository;

    @Test
    void createClubInvitation() {
        //given
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
        ClubUserEntity clubUserEntity = ClubUserBuilder.build(clubEntity, userEntity);

        ClubInvitationDto.CREATE create = ClubInvitationBuilder.create(clubUserEntity, userEntity);

        //when then
        assertDoesNotThrow(() -> clubInvitationService.createClubInvitation(create));
    }

    @Test
    void getClubInvitations() {
        //given
        List<ClubInvitationEntity> clubInvitationEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubInvitationEntity.class));
        Page<ClubInvitationEntity> clubInvitationEntityPage = new PageImpl<>(clubInvitationEntityList);
        given(clubInvitationRepository.fetchClubInvitationByClubEntity(any(), any())).willReturn(clubInvitationEntityPage);
        ClubEntity clubEntity = new EasyRandom().nextObject(ClubEntity.class);
        //when
        Page<ClubInvitationEntity> savedClubInvitationPage = clubInvitationService.getClubInvitations(1, clubEntity);

        //then
        assertNotNull(savedClubInvitationPage);
        assertEquals(savedClubInvitationPage.getContent().get(0).getClubUserEntity().getClubEntity().getClubName(), clubInvitationEntityPage.getContent().get(0).getClubUserEntity().getClubEntity().getClubName());
        assertEquals(savedClubInvitationPage.getContent().get(0).getUserEntity().getIdentity(), clubInvitationEntityPage.getContent().get(0).getUserEntity().getIdentity());

    }

    @Test
    void getClubInvitationByWaiting() {
        UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
        ClubUserEntity clubUserEntity = new EasyRandom().nextObject(ClubUserEntity.class);
        ClubInvitationEntity clubInvitationEntity = ClubInvitationBuilder.build(clubUserEntity, userEntity);
        given(clubInvitationRepository.fetchClubInvitationByClubUserEntityAndUserEntityAndWaiting(clubUserEntity, userEntity)).willReturn(Optional.ofNullable(clubInvitationEntity));

        //when
        ClubInvitationEntity savedClubInvitationEntity = clubInvitationService.getClubInvitationByWaiting(clubUserEntity, userEntity).orElse(null);

        //then
        assertNotNull(savedClubInvitationEntity);
        assertEquals(savedClubInvitationEntity.getUserEntity().getIdentity(), userEntity.getIdentity());
        assertEquals(savedClubInvitationEntity.getClubUserEntity().getUserEntity().getIdentity(), clubUserEntity.getUserEntity().getIdentity());
    }

    @Test
    void getClubInvitationsByUserEntity() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            List<ClubInvitationEntity> clubInvitationEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubInvitationEntity.class));
            Page<ClubInvitationEntity> clubInvitationEntityPage = new PageImpl<>(clubInvitationEntityList);
            given(clubInvitationRepository.fetchClubInvitationByUserEntity(any(), any())).willReturn(clubInvitationEntityPage);

            //when
            Page<ClubInvitationDto.READ> savedClubInvitationsByUserEntityPage = clubInvitationService.getClubInvitationsByUserEntity(1);

            //then
            assertNotNull(savedClubInvitationsByUserEntityPage);
            assertEquals(savedClubInvitationsByUserEntityPage.getContent().get(0).getIdentity(), clubInvitationEntityPage.getContent().get(0).getClubUserEntity().getUserEntity().getIdentity());
        }
    }

    @Test
    void getClubInvitationsByJoinStatus() {
        //given
        List<ClubInvitationEntity> clubInvitationEntityList = Collections.singletonList(new EasyRandom().nextObject(ClubInvitationEntity.class));
        Page<ClubInvitationEntity> clubInvitationEntityPage = new PageImpl<>(clubInvitationEntityList);
        given(clubInvitationRepository.fetchClubInvitationByUserEntityAndJoinStatus(any(), any(), any())).willReturn(clubInvitationEntityPage);
        List<String> joinStatusList = new ArrayList<>();
        joinStatusList.add(JoinStatus.WAITING.name());

        //when
        Page<ClubInvitationDto.READ> savedClubInvitationsByJoinStatusPage = clubInvitationService.getClubInvitationsByJoinStatus(1, joinStatusList);

        //then
        assertNotNull(savedClubInvitationsByJoinStatusPage);
        assertEquals(savedClubInvitationsByJoinStatusPage.getContent().get(0).getIdentity(), clubInvitationEntityPage.getContent().get(0).getClubUserEntity().getUserEntity().getIdentity());
    }

    @Test
    void updateJoinStatus() {
        //given
        ClubInvitationEntity clubInvitationEntity = new EasyRandom().nextObject(ClubInvitationEntity.class);
        given(clubInvitationRepository.fetchClubInvitationByClubUserEntityAndUserEntityAndWaiting(any(), any())).willReturn(Optional.ofNullable(clubInvitationEntity));
        ClubInvitationDto.UPDATE update = new EasyRandom().nextObject(ClubInvitationDto.UPDATE.class);

        //when
        assertDoesNotThrow(() -> clubInvitationService.updateJoinStatus(update));
    }
}