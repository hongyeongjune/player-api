package kr.co.player.api.interfaces.controller;

import kr.co.player.api.IntegrationTest;
import kr.co.player.api.infrastructure.persistence.entity.ClubEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.repository.ClubRepository;
import kr.co.player.api.infrastructure.persistence.repository.ClubUserRepository;
import kr.co.player.api.infrastructure.persistence.repository.UserRepository;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import kr.co.player.api.infrastructure.utils.builder.ClubBuilder;
import kr.co.player.api.infrastructure.utils.builder.ClubUserBuilder;
import kr.co.player.api.infrastructure.utils.builder.UserBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClubUserControllerTest extends IntegrationTest {

    private final String URL = "/v1/api/club/user";

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubUserRepository clubUserRepository;

    @Test
    void getMyClubs() throws Exception {
        //given
        UserEntity savedUserEntity = userRepository.save(UserBuilder.LeeJaeSung);
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubEntity savedClubEntity = clubRepository.save(ClubBuilder.TottenhamHotspur);
        clubUserRepository.save(ClubUserBuilder.build(savedClubEntity, savedUserEntity));

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
                .andExpect(jsonPath("$.data[0].name").value(savedClubEntity.getClubName()));
    }
}