package kr.co.player.api.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.domain.integrated.model.MatchIntegratedDto;
import kr.co.player.api.domain.integrated.service.MatchIntegratedService;
import kr.co.player.api.domain.shared.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/match/integrated")
public class MatchIntegratedController {

    private final MatchIntegratedService matchIntegratedService;

    @ApiOperation("클럽 가입 신청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PostMapping
    public ResponseFormat createMatch(@RequestBody MatchIntegratedDto.CREATE create) {
        matchIntegratedService.createMatch(create);
        return ResponseFormat.ok();
    }
}
