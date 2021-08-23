package kr.co.player.api.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.player.api.domain.integrated.model.ClubIntegratedDto;
import kr.co.player.api.domain.integrated.service.JoinIntegratedService;
import kr.co.player.api.domain.shared.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/club/join/integrated")
public class JoinIntegratedController {

    private final JoinIntegratedService joinIntegratedService;

    @ApiOperation("클럽 가입 신청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PostMapping("/submit")
    public ResponseFormat createClubSubmit(@RequestBody ClubIntegratedDto.CREATE_SUBMIT create) {
        joinIntegratedService.createClubSubmit(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("클럽장이 클럽으로 초대 신청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PostMapping("/invitation")
    public ResponseFormat createClubInvitation(@RequestBody ClubIntegratedDto.CREATE_INVITATION create) {
        joinIntegratedService.createClubInvitation(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("클럽장이 해당 클럽에 가입 요청을 한 목록 확인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping("/submit")
    public ResponseFormat<Page<ClubIntegratedDto.READ_SUBMIT>> getClubSubmits(@RequestParam int pageNo, @RequestParam String clubName) {
        return ResponseFormat.ok(joinIntegratedService.getClubSubmits(pageNo, clubName));
    }

    @ApiOperation("클럽 장이 클럽으로 초대한 리스트 확인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping("/invitation")
    public ResponseFormat<Page<ClubIntegratedDto.READ_INVITATION>> getClubInvitations(@RequestParam int pageNo, @RequestParam String clubName) {
        return ResponseFormat.ok(joinIntegratedService.getClubInvitations(pageNo, clubName));
    }

    @ApiOperation("클럽장이 사람들의 클럽 가입 신청 상태 변경(수락 or 거절)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PutMapping("/submit")
    public ResponseFormat updateClubSubmit(@RequestBody ClubIntegratedDto.UPDATE_SUBMIT update) {
        joinIntegratedService.updateClubSubmitStatus(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("본인이 직접 클럽 가입 신청 후 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PutMapping("/submit/cancel")
    public ResponseFormat updateClubSubmitDirectly(@RequestBody String clubName) {
        joinIntegratedService.updateClubSubmitStatusDirectly(clubName);
        return ResponseFormat.ok();
    }

    @ApiOperation("본인이 초대 받은 클럽 상태 변경(수락 or 거절)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PutMapping("/invitation")
    public ResponseFormat updateClubInvitation(@RequestBody ClubIntegratedDto.UPDATE_INVITATION update) {
        joinIntegratedService.updateClubInvitationStatus(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("클럽 장이 초대한 회원 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PutMapping("/invitation/cancel")
    public ResponseFormat updateClubInvitationStatusDirectly(@RequestBody ClubIntegratedDto.UPDATE_INVITATION_DIRECTLY update) {
        joinIntegratedService.updateClubInvitationStatusDirectly(update);
        return ResponseFormat.ok();
    }
}
