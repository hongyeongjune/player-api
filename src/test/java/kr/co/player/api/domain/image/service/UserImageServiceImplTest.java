package kr.co.player.api.domain.image.service;

import kr.co.player.api.ServiceTest;
import kr.co.player.api.domain.image.model.ImageDto;
import kr.co.player.api.domain.image.model.common.ImageType;
import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.service.UserServiceImpl;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;
import kr.co.player.api.infrastructure.persistence.repository.UserImageRepository;
import kr.co.player.api.infrastructure.persistence.repository.UserRepository;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ServiceTest
class UserImageServiceImplTest {

    @InjectMocks
    private UserImageServiceImpl userImageService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserImageRepository userImageRepository;

    @Test
    void createImageUrl() {
        //given
        ImageDto.CREATE_USER create = new EasyRandom().nextObject(ImageDto.CREATE_USER.class);
        create.setImageType(ImageType.of("MAIN"));

        //when then
        assertDoesNotThrow(() -> userImageService.createImageUrl(create));
    }

    @Test
    void getUserImages() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);

            List<UserImageEntity> userImageEntityList = Collections.singletonList(new EasyRandom().nextObject(UserImageEntity.class));
            given(userImageRepository.fetchByUserEntity(userEntity)).willReturn(userImageEntityList);

            //when
            List<ImageDto.READ_USER> read = userImageService.getUserImages();

            //then
            assertNotNull(read);
            assertEquals(userImageEntityList.get(0).getUrl(), read.get(0).getImageUrl());
        }
    }

    @Test
    void deleteUserImage() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);

            UserImageEntity userImageEntity = new EasyRandom().nextObject(UserImageEntity.class);
            given(userImageRepository.fetchByUserEntityAndUrl(userEntity, userImageEntity.getUrl())).willReturn(Optional.ofNullable(userImageEntity));

            //when then
            assertDoesNotThrow(() -> userImageService.deleteUserImage(userImageEntity.getUrl()));
        }
    }
}