package kr.co.player.api.interfaces.controller;

import kr.co.player.api.IntegrationTest;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.domain.user.service.UserService;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubSubmitEntity;
import kr.co.player.api.infrastructure.persistence.entity.ClubUserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubRepository;
import kr.co.player.api.infrastructure.persistence.repository.ClubSubmitRepository;
import kr.co.player.api.infrastructure.persistence.repository.ClubUserRepository;
import kr.co.player.api.infrastructure.persistence.repository.UserRepository;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import kr.co.player.api.infrastructure.utils.builder.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClubSubmitControllerTest extends IntegrationTest {

    private final String URL = "/v1/api/club/submit";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubSubmitRepository clubSubmitRepository;

    @Order(21)
    @Test
    void getClubSubmits() throws Exception {
        //given
        UserEntity savedUserEntity = userRepository.save(UserBuilder.SunJuHo);
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = clubRepository.save(ClubBuilder.Liverpool);

        ClubSubmitEntity savedClubSubmitEntity = clubSubmitRepository.save(ClubSubmitBuilder.build(savedClubEntity, savedUserEntity));

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
                .andExpect(jsonPath("$.data.content[0].clubName").value(savedClubSubmitEntity.getClubEntity().getClubName()));
    }

    @Order(22)
    @Test
    void getClubSubmitsByJoinStatus() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.SunJuHo;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = ClubBuilder.Liverpool;

        ClubSubmitEntity savedClubSubmitEntity = ClubSubmitBuilder.build(savedClubEntity, savedUserEntity);
        String waiting = JoinStatus.WAITING.name();
        String accept = JoinStatus.ACCEPT.name();
        String cancel = JoinStatus.CANCEL.name();
        String reject = JoinStatus.REJECT.name();

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .param("pageNo", "1")
                .param("joinStatusList", waiting)
                .param("joinStatusList", accept)
                .param("joinStatusList", cancel)
                .param("joinStatusList", reject)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data.content[0].clubName").value(savedClubSubmitEntity.getClubEntity().getClubName()));

    }
}