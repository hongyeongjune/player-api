package kr.co.player.api.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.player.api.domain.club.model.ClubIntegratedDto;
import kr.co.player.api.domain.integrated.ClubIntegratedService;
import kr.co.player.api.domain.shared.Address;
import kr.co.player.api.domain.shared.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/club/integrated")
public class ClubIntegratedController {

    private final ClubIntegratedService clubIntegratedService;

    @ApiOperation("클럽 장 인지 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PostMapping("/leader")
    public ResponseFormat<Boolean> isLeader(@RequestBody String clubName) {
        return ResponseFormat.ok(clubIntegratedService.isLeader(clubName));
    }

    @ApiOperation("클럽 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PostMapping
    public ResponseFormat createClub(@RequestBody ClubIntegratedDto.CREATE create) {
        clubIntegratedService.createClub(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("모든 클럽 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping
    public ResponseFormat<Page<ClubIntegratedDto.READ>> getClubs(@RequestParam int pageNo) {
        return ResponseFormat.ok(clubIntegratedService.getClubs(pageNo));
    }

    @ApiOperation("주소 기준으로 모든 클럽 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping("/address")
    public ResponseFormat<Page<ClubIntegratedDto.READ>> getClubsByAddress(@RequestParam int pageNo, @RequestParam List<String> districtList, @RequestParam List<String> cityList) {
        return ResponseFormat.ok(clubIntegratedService.getClubsByAddress(pageNo, districtList, cityList));
    }

    @ApiOperation("키워드 기준으로 모든 클럽 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @GetMapping("/keyword")
    public ResponseFormat<Page<ClubIntegratedDto.READ>> getClubsByKeyword(@RequestParam int pageNo, @RequestParam String keyword) {
        return ResponseFormat.ok(clubIntegratedService.getClubsByKeyword(pageNo, keyword));
    }

    @ApiOperation("클럽장이 클럽 정보 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PutMapping
    public ResponseFormat updateClub(@RequestBody ClubIntegratedDto.UPDATE update) {
        clubIntegratedService.updateClub(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("클럽장이 클럽 이름 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    @PutMapping("/name")
    public ResponseFormat updateClubName(@RequestBody ClubIntegratedDto.UPDATE_CLUB_NAME update) {
        clubIntegratedService.updateClubName(update);
        return ResponseFormat.ok();
    }
}
