package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.RepositoryTest;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.domain.shared.PageUtil;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubInvitationEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.utils.builder.ClubInvitationBuilder;
import kr.co.player.api.infrastructure.utils.builder.ClubUserBuilder;
import kr.co.player.api.infrastructure.utils.builder.JoinStatusBuilder;
import kr.co.player.api.infrastructure.utils.builder.UserBuilder;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class ClubInvitationRepositoryCustomTest {

    @Autowired
    private ClubInvitationRepository clubInvitationRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubUserRepository clubUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void fetchClubInvitationByClubEntity() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));
        ClubUserEntity clubUserEntity = clubUserRepository.save(ClubUserBuilder.build(clubEntity, userEntity));

        UserEntity targetUserEntity = userRepository.save(UserBuilder.target);
        ClubInvitationEntity clubInvitationEntity = clubInvitationRepository.save(ClubInvitationBuilder.build(clubUserEntity, targetUserEntity));

        //when
        Page<ClubInvitationEntity> clubInvitationEntityPage = clubInvitationRepository.fetchClubInvitationByClubEntity(PageUtil.applyPageConfig(1, 10), clubEntity);

        //then
        assertNotNull(clubInvitationEntityPage);
        assertEquals(clubInvitationEntityPage.getContent().get(0).getUserEntity().getIdentity(), clubInvitationEntity.getUserEntity().getIdentity());
    }

    @Test
    void fetchClubInvitationByUserEntity() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));
        ClubUserEntity clubUserEntity = clubUserRepository.save(ClubUserBuilder.build(clubEntity, userEntity));

        UserEntity targetUserEntity = userRepository.save(UserBuilder.target);
        clubInvitationRepository.save(ClubInvitationBuilder.build(clubUserEntity, targetUserEntity));

        //when
        Page<ClubInvitationEntity> clubInvitationEntityPage = clubInvitationRepository.fetchClubInvitationByUserEntity(PageUtil.applyPageConfig(1, 10), targetUserEntity);

        //then
        assertNotNull(clubInvitationEntityPage);
        assertEquals(clubInvitationEntityPage.getContent().get(0).getUserEntity().getIdentity(), targetUserEntity.getIdentity());
    }

    @Test
    void fetchClubInvitationByUserEntityAndJoinStatus() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));
        ClubUserEntity clubUserEntity = clubUserRepository.save(ClubUserBuilder.build(clubEntity, userEntity));

        UserEntity targetUserEntity = userRepository.save(UserBuilder.target);
        clubInvitationRepository.save(ClubInvitationBuilder.build(clubUserEntity, targetUserEntity));

        List<JoinStatus> joinStatusList = JoinStatusBuilder.build();

        //when
        Page<ClubInvitationEntity> clubInvitationEntityPage = clubInvitationRepository.fetchClubInvitationByUserEntityAndJoinStatus(
                PageUtil.applyPageConfig(1, 10),
                targetUserEntity,
                joinStatusList);

        //then
        assertNotNull(clubInvitationEntityPage);
        assertEquals(clubInvitationEntityPage.getContent().get(0).getUserEntity().getIdentity(), targetUserEntity.getIdentity());
    }

    @Test
    void fetchClubInvitationByClubUserEntityAndUserEntityAndWaiting() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));
        ClubUserEntity clubUserEntity = clubUserRepository.save(ClubUserBuilder.build(clubEntity, userEntity));

        UserEntity targetUserEntity = userRepository.save(UserBuilder.target);
        clubInvitationRepository.save(ClubInvitationBuilder.waiting(clubUserEntity, targetUserEntity));

        //when
        ClubInvitationEntity clubInvitationEntity = clubInvitationRepository.fetchClubInvitationByClubUserEntityAndUserEntityAndWaiting(clubUserEntity, targetUserEntity).orElse(null);

        //then
        assertNotNull(clubInvitationEntity);
        assertEquals(clubInvitationEntity.getUserEntity().getIdentity(), targetUserEntity.getIdentity());
    }
}