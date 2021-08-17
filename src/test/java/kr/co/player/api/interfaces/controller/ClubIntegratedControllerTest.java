package kr.co.player.api.interfaces.controller;

import kr.co.player.api.IntegrationTest;
import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.UserRepository;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import kr.co.player.api.infrastructure.utils.builder.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClubIntegratedControllerTest extends IntegrationTest {

    private final String URL = "/v1/api/club/integrated";

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;

    @Order(15)
    @Test
    void isLeader() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.KimDongWook;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubEntity savedClubEntity = ClubBuilder.create;
        ClubUserBuilder.leader(savedClubEntity, savedUserEntity);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/leader")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(savedClubEntity.getClubName())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Order(14)
    @Test
    void createClub() throws Exception {
        //given
        UserEntity savedUserEntity = userRepository.save(UserBuilder.KimDongWook);
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubIntegratedDto.CREATE create = ClubIntegratedBuilder.create;

        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(create))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(16)
    @Test
    void getClubs() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.KimDongWook;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubIntegratedDto.CREATE create = ClubIntegratedBuilder.create;

        //when
        ResultActions resultActions = mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .param("pageNo", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data.content[0].name").value(create.getName()));

    }

    @Order(17)
    @Test
    void getClubsByAddress() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.KimDongWook;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubIntegratedDto.CREATE create = ClubIntegratedBuilder.create;

        String city = DataBuilder.city;
        String district = DataBuilder.district;

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "/address")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .param("pageNo", "1")
                .param("cityList", city)
                .param("districtList", district)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data.content[0].name").value(create.getName()));
    }

    @Order(18)
    @Test
    void getClubsByKeyword() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.KimDongWook;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubIntegratedDto.CREATE create = ClubIntegratedBuilder.create;

        String keyword = DataBuilder.keyword;

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "/keyword")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .param("pageNo", "1")
                .param("keyword", keyword)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data.content[0].name").value(create.getName()));

    }

    @Order(19)
    @Test
    void updateClub() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.KimDongWook;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubIntegratedDto.UPDATE update = ClubIntegratedBuilder.update;

        //when
        ResultActions resultActions = mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(update))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(20)
    @Test
    void updateClubName() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.KimDongWook;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubIntegratedDto.UPDATE_CLUB_NAME update = ClubIntegratedBuilder.updateClubName;

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(update))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }
}