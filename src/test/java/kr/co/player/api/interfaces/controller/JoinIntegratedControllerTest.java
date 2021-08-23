package kr.co.player.api.interfaces.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.player.api.IntegrationTest;
import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubRepository;
import kr.co.player.api.infrastructure.persistence.repository.ClubUserRepository;
import kr.co.player.api.infrastructure.persistence.repository.UserRepository;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import kr.co.player.api.infrastructure.utils.builder.ClubBuilder;
import kr.co.player.api.infrastructure.utils.builder.ClubIntegratedBuilder;
import kr.co.player.api.infrastructure.utils.builder.ClubUserBuilder;
import kr.co.player.api.infrastructure.utils.builder.UserBuilder;
import org.junit.jupiter.api.Order;
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

class JoinIntegratedControllerTest extends IntegrationTest {

    private final String URL = "/v1/api/club/join/integrated";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubUserRepository clubUserRepository;

    @Order(31)
    @Test
    void createClubSubmit() throws Exception {
        //given
        UserEntity savedUserEntity = userRepository.save(UserBuilder.SongHyeonSu);
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = clubRepository.save(ClubBuilder.Arsenal);

        UserEntity savedLeaderEntity = userRepository.save(UserBuilder.JeongYuBin);
        clubUserRepository.save(ClubUserBuilder.leader(savedClubEntity, savedLeaderEntity));

        ClubIntegratedDto.CREATE_SUBMIT createSubmit = ClubIntegratedBuilder.createSubmit;

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(createSubmit))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(32)
    @Test
    void createClubSubmit2() throws Exception {
        //given
        UserEntity savedUserEntity = userRepository.save(UserBuilder.LeeJaeBum);
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        
        ClubIntegratedDto.CREATE_SUBMIT createSubmit = ClubIntegratedBuilder.createSubmit;

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(createSubmit))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(33)
    @Test
    void createClubSubmit3() throws Exception {
        //given
        UserEntity savedUserEntity = userRepository.save(UserBuilder.JeonAYeong);
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubIntegratedDto.CREATE_SUBMIT createSubmit = ClubIntegratedBuilder.createSubmit;

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(createSubmit))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(34)
    @Test
    void createClubInvitation() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.JeongYuBin;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        UserEntity userEntity = userRepository.save(UserBuilder.YuJaeHee);

        ClubIntegratedDto.CREATE_INVITATION createInvitation = ClubIntegratedBuilder.createInvitation(userEntity.getIdentity());

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/invitation")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(createInvitation))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));

    }

    @Order(35)
    @Test
    void createClubInvitation2() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.JeongYuBin;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        UserEntity userEntity = userRepository.save(UserBuilder.NamYeJu);

        ClubIntegratedDto.CREATE_INVITATION createInvitation = ClubIntegratedBuilder.createInvitation(userEntity.getIdentity());

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/invitation")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(createInvitation))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));

    }

    @Order(36)
    @Test
    void createClubInvitation3() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.JeongYuBin;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        UserEntity userEntity = userRepository.save(UserBuilder.NamYeJin);

        ClubIntegratedDto.CREATE_INVITATION createInvitation = ClubIntegratedBuilder.createInvitation(userEntity.getIdentity());

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/invitation")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(createInvitation))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));

    }

    @Order(37)
    @Test
    void getClubSubmits() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.JeongYuBin;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = ClubBuilder.Arsenal;
        UserEntity userEntity = UserBuilder.JeonAYeong;

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .param("pageNo", "1")
                .param("clubName", savedClubEntity.getClubName())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data.content[0].identity").value(userEntity.getIdentity()));
    }

    @Order(38)
    @Test
    void getClubInvitations() throws Exception {
        UserEntity savedUserEntity = UserBuilder.JeongYuBin;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = ClubBuilder.Arsenal;
        UserEntity userEntity = UserBuilder.NamYeJin;

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "/invitation")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .param("pageNo", "1")
                .param("clubName", savedClubEntity.getClubName())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data.content[0].identity").value(userEntity.getIdentity()));
    }

    @Order(39)
    @Test
    void updateClubSubmit() throws Exception {
        //given
        ClubEntity savedClubEntity = ClubBuilder.Arsenal;
        UserEntity savedUserEntity = UserBuilder.JeongYuBin;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        UserEntity userEntity = UserBuilder.SongHyeonSu;
        ClubIntegratedDto.UPDATE_SUBMIT updateSubmit = ClubIntegratedBuilder.updateSubmit(
                savedClubEntity.getClubName(), userEntity.getIdentity(), JoinStatus.ACCEPT.name());

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(updateSubmit))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(40)
    @Test
    void updateClubSubmitDirectly() throws Exception {
        //given
        ClubEntity savedClubEntity = ClubBuilder.Arsenal;
        UserEntity savedUserEntity = UserBuilder.LeeJaeBum;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/submit/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(savedClubEntity.getClubName())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(41)
    @Test
    void updateClubInvitation() throws Exception {
        //given
        UserEntity savedLeaderEntity = UserBuilder.JeongYuBin;
        UserEntity savedUserEntity = UserBuilder.YuJaeHee;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = ClubBuilder.Arsenal;

        ClubIntegratedDto.UPDATE_INVITATION updateInvitation = ClubIntegratedBuilder.updateInvitation(
                savedClubEntity.getClubName(), savedLeaderEntity.getIdentity(), JoinStatus.ACCEPT.name());

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/invitation")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(updateInvitation))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(42)
    @Test
    void updateClubInvitationStatusDirectly() throws Exception {
        UserEntity savedUserEntity = UserBuilder.JeongYuBin;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = ClubBuilder.Arsenal;

        UserEntity userEntity = UserBuilder.NamYeJu;

        ClubIntegratedDto.UPDATE_INVITATION_DIRECTLY updateInvitation = ClubIntegratedBuilder.updateInvitation(
                savedClubEntity.getClubName(), userEntity.getIdentity());

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/invitation/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(updateInvitation))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(43)
    @Test
    void updateClubSubmitCancel() throws Exception {
        //given
        ClubEntity savedClubEntity = ClubBuilder.Arsenal;
        UserEntity savedUserEntity = UserBuilder.JeongYuBin;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        UserEntity userEntity = UserBuilder.JeonAYeong;
        ClubIntegratedDto.UPDATE_SUBMIT updateSubmit = ClubIntegratedBuilder.updateSubmit(
                savedClubEntity.getClubName(), userEntity.getIdentity(), JoinStatus.REJECT.name());

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(updateSubmit))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }

    @Order(44)
    @Test
    void updateClubInvitationCancel() throws Exception {
        //given
        UserEntity savedLeaderEntity = UserBuilder.JeongYuBin;
        UserEntity savedUserEntity = UserBuilder.NamYeJin;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = ClubBuilder.Arsenal;

        ClubIntegratedDto.UPDATE_INVITATION updateInvitation = ClubIntegratedBuilder.updateInvitation(
                savedClubEntity.getClubName(), savedLeaderEntity.getIdentity(), JoinStatus.ACCEPT.name());

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "/invitation")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(updateInvitation))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true));
    }
}