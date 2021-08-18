package kr.co.player.api.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.player.api.domain.shared.ResponseFormat;
import kr.co.player.api.domain.submit.model.ClubSubmitDto;
import kr.co.player.api.domain.submit.service.ClubSubmitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/club/submit")
public class ClubSubmitController {

    private final ClubSubmitService clubSubmitService;

    @ApiOperation("내가 신청한 클럽 목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping
    public ResponseFormat<Page<ClubSubmitDto.READ>> getClubSubmits(@RequestParam int pageNo) {
        return ResponseFormat.ok(clubSubmitService.getClubSubmits(pageNo));
    }

    @ApiOperation("상태에 따른 신청한 클럽 목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping("/status")
    public ResponseFormat<Page<ClubSubmitDto.READ>> getClubSubmitsByJoinStatus(@RequestParam int pageNo, @RequestParam List<String> joinStatusList) {
        return ResponseFormat.ok(clubSubmitService.getClubSubmitsByJoinStatus(pageNo, joinStatusList));
    }
}
