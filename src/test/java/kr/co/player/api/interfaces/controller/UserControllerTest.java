package kr.co.player.api.interfaces.controller;

import kr.co.player.api.IntegrationTest;
import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.model.common.UserRole;
import kr.co.player.api.domain.user.service.UserService;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

    private final String URL = "/v1/api/users";

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void login() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");
        userService.signUp(create);

        UserDto.LOGIN login = new UserDto.LOGIN(create.getIdentity(), create.getPassword(), "fcmToken");

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

    @Test
    void login_비밀번호불일치() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");
        userService.signUp(create);

        UserDto.LOGIN login = new UserDto.LOGIN(create.getIdentity(), "inconsistency", "fcmToken");

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

    @Test
    void reCheckPassword() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");
        userService.signUp(create);

        String accessToken = jwtProvider.createAccessToken(create.getIdentity(), UserRole.of(create.getRole()), create.getName());

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/re/check/password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(create.getPassword())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void checkIdentity() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");
        userService.signUp(create);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/check/identity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(create.getIdentity())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));

    }

    @Test
    void resetPasswordCheck() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");
        userService.signUp(create);

        UserDto.RESET_CHECK reset = new UserDto.RESET_CHECK(create.getIdentity(), create.getName(), create.getBirth());
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

    @Test
    void signUp() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

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

    @Test
    void signUp_아이디중복() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

        userService.signUp(create);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(create))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(jsonPath("$.result").value(false));
    }

    @Test
    void getUser() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

        userService.signUp(create);

        String accessToken = jwtProvider.createAccessToken(create.getIdentity(), UserRole.of(create.getRole()), create.getName());

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
                .andExpect(jsonPath("$.data.identity").value(create.getIdentity()));
    }

    @Test
    void findIdentity() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

        userService.signUp(create);

        UserDto.ID_READ read = new UserDto.ID_READ(create.getName(), create.getBirth());

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
                .andExpect(jsonPath("$.data[0]").value(create.getIdentity()));
    }

    @Test
    void updateUser() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

        userService.signUp(create);

        String accessToken = jwtProvider.createAccessToken(create.getIdentity(), UserRole.of(create.getRole()), create.getName());
        UserDto.UPDATE update = new EasyRandom().nextObject(UserDto.UPDATE.class);
        update.setPositionType("MF");
        update.setMainPosition("CDM");
        update.setSubPosition("RB");

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

    @Test
    void updatePassword() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

        userService.signUp(create);

        String accessToken = jwtProvider.createAccessToken(create.getIdentity(), UserRole.of(create.getRole()), create.getName());
        UserDto.UPDATE_PASSWORD update = new UserDto.UPDATE_PASSWORD(create.getPassword(), "newPassword", "newPassword");

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

    @Test
    void updatePassword_비밀번호불일치() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

        userService.signUp(create);

        String accessToken = jwtProvider.createAccessToken(create.getIdentity(), UserRole.of(create.getRole()), create.getName());
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

    @Test
    void updatePassword_새로운비밀번호불일치() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

        userService.signUp(create);

        String accessToken = jwtProvider.createAccessToken(create.getIdentity(), UserRole.of(create.getRole()), create.getName());
        UserDto.UPDATE_PASSWORD update = new UserDto.UPDATE_PASSWORD(create.getPassword(), "newPassword", "reNewPassword");

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

    @Test
    void resetPassword() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

        userService.signUp(create);

        UserDto.RESET_PASSWORD reset = new UserDto.RESET_PASSWORD(create.getIdentity(), "newPassword", "newPassword");

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

    @Test
    void refreshToken() throws Exception {
        //given
        UserDto.CREATE create = new EasyRandom().nextObject(UserDto.CREATE.class);
        create.setGender("MALE");
        create.setRole("USER");

        userService.signUp(create);
        UserDto.TOKEN token = userService.login(new UserDto.LOGIN(create.getIdentity(), create.getPassword(), "fcmToken"));

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token.getRefreshToken())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }
}