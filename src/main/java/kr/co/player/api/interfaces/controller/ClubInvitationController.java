package kr.co.player.api.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.player.api.domain.invitation.model.ClubInvitationDto;
import kr.co.player.api.domain.invitation.service.ClubInvitationService;
import kr.co.player.api.domain.shared.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/club/invitation")
public class ClubInvitationController {

    private final ClubInvitationService clubInvitationService;

    @ApiOperation("초대받은 내역 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping
    public ResponseFormat<Page<ClubInvitationDto.READ>> getClubInvitations(@RequestParam int pageNo) {
        return ResponseFormat.ok(clubInvitationService.getClubInvitationsByUserEntity(pageNo));
    }

    @ApiOperation("상태에 따라 초대받은 내역 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping("/status")
    public ResponseFormat<Page<ClubInvitationDto.READ>> getClubInvitationsByJoinStatus(@RequestParam int pageNo, @RequestParam List<String> joinStatusList) {
        return ResponseFormat.ok(clubInvitationService.getClubInvitationsByJoinStatus(pageNo, joinStatusList));
    }
}
