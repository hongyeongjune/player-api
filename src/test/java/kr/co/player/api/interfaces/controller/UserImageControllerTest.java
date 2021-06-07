package kr.co.player.api.interfaces.controller;

import kr.co.player.api.IntegrationTest;
import kr.co.player.api.domain.image.service.UserImageService;
import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.model.common.UserRole;
import kr.co.player.api.domain.user.service.UserService;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserImageControllerTest extends IntegrationTest {

    private final String URL = "/v1/api/users/image";

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void getImages() throws Exception{
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
                .andExpect(jsonPath("$.result").value(true));
    }
}