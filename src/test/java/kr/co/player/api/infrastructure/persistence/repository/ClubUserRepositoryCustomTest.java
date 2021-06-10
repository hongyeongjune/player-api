package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.RepositoryTest;
import kr.co.player.api.domain.shared.test.ClubUserBuilder;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class ClubUserRepositoryCustomTest {

    @Autowired
    private ClubUserRepository clubUserRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void fetchByClubEntityAndClubUserRole() {
        //given
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubUserEntity clubUserEntity = clubUserRepository.save(ClubUserBuilder.build(clubEntity, userEntity));

        //when
        ClubUserEntity savedClubUserEntity = clubUserRepository.fetchByClubEntityAndClubUserRole(clubUserEntity.getClubEntity()).orElse(null);

        //then
        assertNotNull(savedClubUserEntity);
        assertEquals(savedClubUserEntity.getClubEntity(), clubUserEntity.getClubEntity());
        assertEquals(savedClubUserEntity.getUserEntity(), clubUserEntity.getUserEntity());
    }

    @Test
    void countByClubEntity() {
        //given
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubUserEntity clubUserEntity = clubUserRepository.save(ClubUserBuilder.build(clubEntity, userEntity));

        //when
        Long count = clubUserRepository.countByClubEntity(clubUserEntity.getClubEntity());

        //then
        assertNotNull(count);
        assertEquals(count, 1);
    }

    @Test
    void fetchByClubEntityAndUserEntity() {
        //given
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubUserEntity clubUserEntity = clubUserRepository.save(ClubUserBuilder.build(clubEntity, userEntity));

        //when
        ClubUserEntity savedClubUserEntity = clubUserRepository.fetchByClubEntityAndUserEntity(clubUserEntity.getClubEntity(), clubUserEntity.getUserEntity()).orElse(null);

        //then
        assertNotNull(savedClubUserEntity);
        assertEquals(savedClubUserEntity.getClubEntity(), clubUserEntity.getClubEntity());
        assertEquals(savedClubUserEntity.getUserEntity(), clubUserEntity.getUserEntity());
    }

    @Test
    void fetchByUserEntity() {
        //given
        ClubEntity clubEntity = clubRepository.save(new EasyRandom().nextObject(ClubEntity.class));
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        ClubUserEntity clubUserEntity = clubUserRepository.save(ClubUserBuilder.build(clubEntity, userEntity));

        //when
        List<ClubUserEntity> clubUserEntityList = clubUserRepository.fetchByUserEntity(clubUserEntity.getUserEntity());

        //then
        assertEquals(clubUserEntityList.size(), 1);
        assertEquals(clubUserEntityList.get(0).getClubEntity(), clubUserEntity.getClubEntity());
        assertEquals(clubUserEntityList.get(0).getUserEntity(), clubUserEntity.getUserEntity());
    }
}