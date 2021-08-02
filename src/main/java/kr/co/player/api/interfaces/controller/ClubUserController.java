package kr.co.player.api.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.player.api.domain.club.model.ClubUserDto;
import kr.co.player.api.domain.club.service.ClubUserService;
import kr.co.player.api.domain.shared.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/club/user")
public class ClubUserController {

    private final ClubUserService clubUserService;

    @ApiOperation("내 클럽 정보 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping
    public ResponseFormat<List<ClubUserDto.READ_MY_CLUB>> getMyClubs() {
        return ResponseFormat.ok(clubUserService.getMyClubs());
    }
}
