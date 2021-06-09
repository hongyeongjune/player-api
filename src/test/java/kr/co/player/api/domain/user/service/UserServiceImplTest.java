package kr.co.player.api.domain.user.service;

import kr.co.player.api.ServiceTest;
import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.model.common.UserRole;
import kr.co.player.api.infrastructure.error.exception.BadRequestException;
import kr.co.player.api.infrastructure.error.exception.DuplicatedException;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.error.exception.UserDefineException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ServiceTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserImageRepository userImageRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Test
    void login() {
        //given
        UserDto.LOGIN login = new EasyRandom().nextObject(UserDto.LOGIN.class);
        UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);

        given(userRepository.findByIdentity(anyString())).willReturn(Optional.ofNullable(userEntity));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(jwtProvider.createAccessToken(anyString(), any(UserRole.class), anyString())).willReturn("accessToken");
        given(jwtProvider.createRefreshToken(anyString(), any(UserRole.class), anyString())).willReturn("refreshToken");

        //when
        UserDto.TOKEN token = userService.login(login);

        //then
        assertNotNull(token);
        assertNotEquals(token.getAccessToken(), token.getRefreshToken());
    }

    @Test
    void login_회원정보찾을수없음() {
        //given
        UserDto.LOGIN login = new EasyRandom().nextObject(UserDto.LOGIN.class);
        given(userRepository.findByIdentity(anyString())).willThrow(NotFoundException.class);

        //when then
        assertThrows(NotFoundException.class, () -> userService.login(login));
    }

    @Test
    void login_비밀번호불일치() {
        //given
        UserDto.LOGIN login = new EasyRandom().nextObject(UserDto.LOGIN.class);
        UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
        given(userRepository.findByIdentity(anyString())).willReturn(Optional.ofNullable(userEntity));
        given(passwordEncoder.matches(anyString(), anyString())).willThrow(BadRequestException.class);

        //when then
        assertThrows(BadRequestException.class, () -> userService.login(login));
    }

    @Test
    void reCheckPassword() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

            //when
            boolean isSuccess = userService.reCheckPassword("password");

            //then
            assertTrue(isSuccess);
        }
    }

    @Test
    void checkIdentity() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            given(userRepository.existsByIdentity(anyString())).willReturn(true);

            //when
            boolean isSuccess = userService.checkIdentity("identity");

            //then
            assertTrue(isSuccess);
        }
    }

    @Test
    void resetPasswordCheck() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserDto.RESET_CHECK reset = new EasyRandom().nextObject(UserDto.RESET_CHECK.class);
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            given(userRepository.existsByIdentityAndNameAndBirth(anyString(), anyString(), anyString())).willReturn(true);

            //when
            boolean isSuccess = userService.resetPasswordCheck(reset);

            //then
            assertTrue(isSuccess);
        }
    }

    @Test
    void signUp() {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");
        given(userRepository.existsByIdentity(anyString())).willReturn(false);

        //when then
        assertDoesNotThrow(() -> userService.signUp(create));
    }

    @Test
    void signUp_아이디중복() {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");
        given(userRepository.existsByIdentity(anyString())).willReturn(true);

        //when then
        assertThrows(DuplicatedException.class, () -> userService.signUp(create));
    }

    @Test
    void signUp_ENUM_에러() {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        given(userRepository.existsByIdentity(anyString())).willReturn(false);

        //when then
        assertThrows(UserDefineException.class, () -> userService.signUp(create));
    }

    @Test
    void getUser() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            List<UserImageEntity> userImageEntityList = Collections.singletonList(new EasyRandom().nextObject(UserImageEntity.class));
            given(userImageRepository.fetchByUserEntity(userEntity)).willReturn(userImageEntityList);

            //when
            UserDto.READ read = userService.getUser();

            //then
            assertNotNull(read);
            assertEquals(userEntity.getIdentity(), read.getIdentity());
        }
    }

    @Test
    void findIdentity() {
        //given
        UserDto.ID_READ read = new EasyRandom().nextObject(UserDto.ID_READ.class);
        List<UserEntity> userEntityList = Collections.singletonList(new EasyRandom().nextObject(UserEntity.class));
        given(userRepository.findByNameAndBirth(anyString(), anyString())).willReturn(userEntityList);

        //when
        List<String> identity = userService.findIdentity(read);

        //then
        assertNotNull(identity);
        assertEquals(identity.get(0), userEntityList.get(0).getIdentity());
    }

    @Test
    void updateUser() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserDto.UPDATE update = new EasyRandom().nextObject(UserDto.UPDATE.class);
            update.setPositionType("MF");
            update.setMainPosition("CDM");
            update.setSubPosition("RB");
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);

            //when then
            assertDoesNotThrow(() -> userService.updateUser(update));
        }
    }

    @Test
    void updateUser_ENUM_에러() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserDto.UPDATE update = new EasyRandom().nextObject(UserDto.UPDATE.class);
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);

            //when then
            assertThrows(UserDefineException.class, () -> userService.updateUser(update));
        }
    }

    @Test
    void updatePassword() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserDto.UPDATE_PASSWORD update = new EasyRandom().nextObject(UserDto.UPDATE_PASSWORD.class);
            update.setNewPassword("newPassword");
            update.setReNewPassword("newPassword");
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

            //when
            assertDoesNotThrow(() -> userService.updatePassword(update));
        }
    }

    @Test
    void updatePassword_비밀번호불일치() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserDto.UPDATE_PASSWORD update = new EasyRandom().nextObject(UserDto.UPDATE_PASSWORD.class);
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            given(passwordEncoder.matches(anyString(), anyString())).willThrow(BadRequestException.class);

            //when then
            assertThrows(BadRequestException.class, () -> userService.updatePassword(update));
        }
    }

    @Test
    void updatePassword_새로운비밀번호불일치() {
        //given
        try (MockedStatic<UserThreadLocal> utl = Mockito.mockStatic(UserThreadLocal.class)) {
            UserDto.UPDATE_PASSWORD update = new EasyRandom().nextObject(UserDto.UPDATE_PASSWORD.class);
            update.setNewPassword("newPassword");
            update.setReNewPassword("reNewPassword");
            UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
            utl.when(UserThreadLocal::get).thenReturn(userEntity);
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

            //when
            assertThrows(BadRequestException.class, () -> userService.updatePassword(update));
        }
    }

    @Test
    void resetPassword() {
        //given
        UserEntity userEntity = new EasyRandom().nextObject(UserEntity.class);
        given(userRepository.findByIdentity(anyString())).willReturn(Optional.ofNullable(userEntity));
        UserDto.RESET_PASSWORD reset = new EasyRandom().nextObject(UserDto.RESET_PASSWORD.class);
        reset.setNewPassword("newPassword");
        reset.setReNewPassword("newPassword");

        //then when
        assertDoesNotThrow(() -> userService.resetPassword(reset));

    }

    @Test
    void resetPassword_새로운비밀번호불일치() {
        //given
        UserDto.RESET_PASSWORD reset = new EasyRandom().nextObject(UserDto.RESET_PASSWORD.class);
        reset.setNewPassword("newPassword");
        reset.setReNewPassword("reNewPassword");

        //then when
        assertThrows(BadRequestException.class, () -> userService.resetPassword(reset));
    }
}