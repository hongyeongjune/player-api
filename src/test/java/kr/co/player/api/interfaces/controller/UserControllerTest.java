package kr.co.player.api.interfaces.controller;

import kr.co.player.api.IntegrationTest;
import kr.co.player.api.domain.shared.test.UserBuilder;
import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.service.UserService;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.UserRepository;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

    private final String URL = "/v1/api/users";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Order(2)
    @Test
    void login() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        UserDto.LOGIN login = new UserDto.LOGIN(savedUserEntity.getIdentity(), savedUserEntity.getPassword(), "fcmToken");

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }

    @Order(3)
    @Test
    void login_회원정보찾을수없음() throws Exception {
        //given
        UserDto.LOGIN login = new EasyRandom().nextObject(UserDto.LOGIN.class);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(jsonPath("$.result").value(false));
    }

    @Order(4)
    @Test
    void login_비밀번호불일치() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        UserDto.LOGIN login = new UserDto.LOGIN(savedUserEntity.getIdentity(), "inconsistency", "fcmToken");

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(jsonPath("$.result").value(false));
    }

    @Order(5)
    @Test
    void reCheckPassword() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/re/check/password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(savedUserEntity.getPassword())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(6)
    @Test
    void checkIdentity() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/check/identity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(savedUserEntity.getIdentity())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));

    }

    @Order(7)
    @Test
    void resetPasswordCheck() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        UserDto.RESET_CHECK reset = new UserDto.RESET_CHECK(savedUserEntity.getIdentity(), savedUserEntity.getName(), savedUserEntity.getBirth());
        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/reset/password/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reset))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(1)
    @Test
    void signUp() throws Exception {
        //given
        UserDto.CREATE create = UserBuilder.create;

        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(create))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(8)
    @Test
    void getUser() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        //when
        ResultActions resultActions = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data.identity").value(savedUserEntity.getIdentity()));
    }

    @Order(9)
    @Test
    void findIdentity() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        UserDto.ID_READ read = new UserDto.ID_READ(savedUserEntity.getName(), savedUserEntity.getBirth());

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/find/identity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(read))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data[0]").value(savedUserEntity.getIdentity()));
    }

    @Order(10)
    @Test
    void updateUser() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        UserDto.UPDATE update = UserBuilder.update;

        //when
        ResultActions resultActions = mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(update))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(11)
    @Test
    void updatePassword() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        UserDto.UPDATE_PASSWORD update = new UserDto.UPDATE_PASSWORD(savedUserEntity.getPassword(), "newPassword", "newPassword");

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(update))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(12)
    @Test
    void updatePassword_비밀번호불일치() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        UserDto.UPDATE_PASSWORD update = new UserDto.UPDATE_PASSWORD("inconsistency", "newPassword", "newPassword");

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(update))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(jsonPath("$.result").value(false));
    }

    @Order(13)
    @Test
    void updatePassword_새로운비밀번호불일치() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        UserDto.UPDATE_PASSWORD update = new UserDto.UPDATE_PASSWORD("newPassword", "newPassword", "reNewPassword");

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(update))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(jsonPath("$.result").value(false));
    }

    @Order(14)
    @Test
    void resetPassword() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.build();

        UserDto.RESET_PASSWORD reset = new UserDto.RESET_PASSWORD(savedUserEntity.getIdentity(), "newPassword", "newPassword");

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/reset/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reset))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

}