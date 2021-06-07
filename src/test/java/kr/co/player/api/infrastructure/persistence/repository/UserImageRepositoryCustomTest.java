package kr.co.player.api.infrastructure.persistence.repository;

import kr.co.player.api.RepositoryTest;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class UserImageRepositoryCustomTest {

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void fetchFirstOrderByIdDesc() {
        //given
        UserEntity userEntity1 = userRepository.save(UserEntity.builder()
                .identity("identity")
                .build());
        UserImageEntity userImageEntity1 = userImageRepository.save(UserImageEntity.builder()
                .userEntity(userEntity1)
                .url(new EasyRandom().nextObject(String.class))
                .build());

        UserEntity userEntity2 = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        UserImageEntity userImageEntity2 = userImageRepository.save(UserImageEntity.builder()
                .userEntity(userEntity2)
                .url(new EasyRandom().nextObject(String.class))
                .build());
        //when
        UserImageEntity userImageEntity = userImageRepository.fetchFirstOrderByIdDesc().orElse(null);

        //then
        assertNotNull(userImageEntity);
        assertNotEquals(userImageEntity.getId(), userImageEntity1.getId());
        assertEquals(userImageEntity.getId(), userImageEntity2.getId());
    }

    @Test
    void fetchByUserEntity() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        UserImageEntity userImageEntity = userImageRepository.save(UserImageEntity.builder()
                .userEntity(userEntity)
                .url(new EasyRandom().nextObject(String.class))
                .build());

        //when
        List<UserImageEntity> userImageEntityList = userImageRepository.fetchByUserEntity(userEntity);

        //then
        assertNotNull(userImageEntityList);
        assertEquals(userImageEntityList.get(0).getId(), userImageEntity.getId());

    }

    @Test
    void fetchByUserEntityAndUrl() {
        //given
        UserEntity userEntity = userRepository.save(new EasyRandom().nextObject(UserEntity.class));
        UserImageEntity userImageEntity = userImageRepository.save(UserImageEntity.builder()
                .userEntity(userEntity)
                .url(new EasyRandom().nextObject(String.class))
                .build());

        //when
        UserImageEntity savedUserImageEntity = userImageRepository.fetchByUserEntityAndUrl(userEntity, userImageEntity.getUrl()).orElse(null);

        //then
        assertNotNull(savedUserImageEntity);
        assertEquals(savedUserImageEntity.getId(), userImageEntity.getId());
    }
}