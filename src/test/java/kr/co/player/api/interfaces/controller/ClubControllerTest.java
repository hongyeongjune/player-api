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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClubControllerTest extends IntegrationTest {

    private final String URL = "/v1/api/club";

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubUserRepository clubUserRepository;

    @Test
    void checkClubName() throws Exception {
        //given
        UserEntity savedUserEntity = userRepository.save(UserBuilder.LeeYeChan);
        String accessToken = jwtProvider.createAccessToken(savedUserEntity.getIdentity(), savedUserEntity.getRole(), savedUserEntity.getName());

        ClubEntity savedClubEntity = clubRepository.save(ClubBuilder.build());
        clubUserRepository.save(ClubUserBuilder.build(savedClubEntity, savedUserEntity));

        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
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
}