package kr.co.player.api.interfaces.controller;

import kr.co.player.api.IntegrationTest;
import kr.co.player.api.domain.shared.JoinStatus;
import kr.co.player.api.infrastructure.persistence.entity.*;
import kr.co.player.api.infrastructure.persistence.repository.*;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import kr.co.player.api.infrastructure.utils.builder.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClubInvitationControllerTest extends IntegrationTest {

    private final String URL = "/v1/api/club/invitation";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubUserRepository clubUserRepository;
    @Autowired
    private ClubInvitationRepository clubInvitationRepository;

    @Test
    void getClubInvitations() throws Exception {
        //given
        UserEntity savedUserEntity = userRepository.save(UserBuilder.MinJaeHong);
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = clubRepository.save(ClubBuilder.ManchesterCity);
        ClubUserEntity savedClubUserEntity = clubUserRepository.save(ClubUserBuilder.leader(savedClubEntity, savedUserEntity));

        ClubInvitationEntity savedClubInvitationEntity = clubInvitationRepository.save(ClubInvitationBuilder.build(savedClubUserEntity, savedUserEntity));

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
                .andExpect(jsonPath("$.data.content[0].clubName").value(savedClubInvitationEntity.getClubUserEntity().getClubEntity().getClubName()));

    }

    @Test
    void getClubInvitationsByJoinStatus() throws Exception {
        //given
        UserEntity savedUserEntity = UserBuilder.MinJaeHong;
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());
        ClubEntity savedClubEntity = ClubBuilder.ManchesterCity;
        ClubUserEntity savedClubUserEntity = ClubUserBuilder.leader(savedClubEntity, savedUserEntity);

        ClubInvitationEntity savedClubInvitationEntity = ClubInvitationBuilder.build(savedClubUserEntity, savedUserEntity);
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
                .andExpect(jsonPath("$.data.content[0].clubName").value(savedClubInvitationEntity.getClubUserEntity().getClubEntity().getClubName()));

    }
}