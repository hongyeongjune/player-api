package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.RepositoryTest;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.domain.shared.PageUtil;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubSubmitEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.utils.builder.ClubSubmitBuilder;
import kr.co.player.api.infrastructure.utils.builder.ClubUserBuilder;
import kr.co.player.api.infrastructure.utils.builder.JoinStatusBuilder;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class ClubSubmitRepositoryCustomTest {

    @Autowired
    private ClubSubmitRepository clubSubmitRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void fetchClubSubmitByUserEntity() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        clubSubmitRepository.save(ClubSubmitBuilder.build(clubEntity, userEntity));

        //when
        Page<ClubSubmitEntity> clubSubmitEntityPage = clubSubmitRepository.fetchClubSubmitByUserEntity(PageUtil.applyPageConfig(1, 10), userEntity);

        //then
        assertNotNull(clubSubmitEntityPage);
        assertEquals(clubSubmitEntityPage.getContent().get(0).getClubEntity().getClubName(), clubEntity.getClubName());
        assertEquals(clubSubmitEntityPage.getContent().get(0).getUserEntity().getIdentity(), userEntity.getIdentity());

    }

    @Test
    void fetchClubSubmitByUserEntityAndJoinStatus() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        clubSubmitRepository.save(ClubSubmitBuilder.build(clubEntity, userEntity));

        List<JoinStatus> joinStatusList = JoinStatusBuilder.build();

        //when
        Page<ClubSubmitEntity> clubSubmitEntityPage = clubSubmitRepository.fetchClubSubmitByUserEntityAndJoinStatus(PageUtil.applyPageConfig(1, 10), userEntity, joinStatusList);

        //then
        assertNotNull(clubSubmitEntityPage);
        assertEquals(clubSubmitEntityPage.getContent().get(0).getClubEntity().getClubName(), clubEntity.getClubName());
        assertEquals(clubSubmitEntityPage.getContent().get(0).getUserEntity().getIdentity(), userEntity.getIdentity());

    }

    @Test
    void fetchClubSubmitByClubEntity() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        clubSubmitRepository.save(ClubSubmitBuilder.build(clubEntity, userEntity));

        //when
        Page<ClubSubmitEntity> clubSubmitEntityPage = clubSubmitRepository.fetchClubSubmitByClubEntity(PageUtil.applyPageConfig(1, 10), clubEntity);

        //then
        assertNotNull(clubSubmitEntityPage);
        assertEquals(clubSubmitEntityPage.getContent().get(0).getClubEntity().getClubName(), clubEntity.getClubName());
        assertEquals(clubSubmitEntityPage.getContent().get(0).getUserEntity().getIdentity(), userEntity.getIdentity());
    }

    @Test
    void fetchClubSubmitByUserEntityAndClubEntityAndWaiting() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        clubSubmitRepository.save(ClubSubmitBuilder.waiting(clubEntity, userEntity));

        //when
        ClubSubmitEntity clubSubmitEntity = clubSubmitRepository.fetchClubSubmitByUserEntityAndClubEntityAndWaiting(userEntity, clubEntity).orElse(null);

        //then
        assertNotNull(clubSubmitEntity);
        assertEquals(clubSubmitEntity.getUserEntity().getIdentity(), userEntity.getIdentity());
        assertEquals(clubSubmitEntity.getClubEntity().getClubName(), clubEntity.getClubName());
    }

    @Test
    void countClubSubmitByUserEntityAndNotWaiting() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));

        clubSubmitRepository.save(ClubSubmitBuilder.cancel(clubEntity, userEntity));

        //when
        long countClubSubmitEntity = clubSubmitRepository.countClubSubmitByUserEntityAndNotWaiting(userEntity);

        //then
        assertEquals(countClubSubmitEntity, 1);
    }
}