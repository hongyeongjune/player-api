package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.RepositoryTest;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdentity() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));

        //when
        UserEntity notSavedUserEntity = userRepository.findByIdentity("ID does not exist").orElse(null);
        UserEntity savedUserEntity = userRepository.findByIdentity(userEntity.getIdentity()).orElse(null);

        //then
        assertNull(notSavedUserEntity);
        assertNotNull(savedUserEntity);
        assertEquals(savedUserEntity.getIdentity(), userEntity.getIdentity());
    }

    @Test
    void findByNameAndBirth() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));

        //when
        List<UserEntity> notSavedUserEntityList = userRepository.findByNameAndBirth("Name does not exist", "Birth does not exist");
        List<UserEntity> savedUserEntityList = userRepository.findByNameAndBirth(userEntity.getName(), userEntity.getBirth());

        //then
        assertTrue(notSavedUserEntityList.isEmpty());
        assertNotNull(savedUserEntityList);
        assertEquals(savedUserEntityList.get(0), userEntity);
    }

    @Test
    void existsByIdentity() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));

        //when
        boolean isExist = userRepository.existsByIdentity(userEntity.getIdentity());
        boolean isNotExist = userRepository.existsByIdentity("ID does not exist");

        //then
        assertTrue(isExist);
        assertFalse(isNotExist);
    }

    @Test
    void existsByIdentityAndNameAndBirth() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));

        //when
        boolean isExist = userRepository.existsByIdentityAndNameAndBirth(userEntity.getIdentity(), userEntity.getName(), userEntity.getBirth());
        boolean isNotExist = userRepository.existsByIdentityAndNameAndBirth("ID does not exist", "Name does not exist", "Birth does not exist");

        //then
        assertTrue(isExist);
        assertFalse(isNotExist);
    }
}