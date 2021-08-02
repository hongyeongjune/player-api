package kr.co.player.api.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.player.api.domain.club.service.ClubService;
import kr.co.player.api.domain.shared.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/club")
public class ClubController {

    private final ClubService clubService;

    @ApiOperation("클럽이름 중복 확인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PostMapping
    public ResponseFormat<Boolean> checkClubName(@RequestBody String clubName) {
        return ResponseFormat.ok(clubService.checkClubName(clubName));
    }
}
