package kr.co.player.api.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.player.api.domain.shared.ResponseFormat;
import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.service.UserService;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @ApiOperation("로그인")
    @PostMapping("/login")
    public ResponseFormat<UserDto.TOKEN> login(@RequestBody UserDto.LOGIN login) {
        return ResponseFormat.ok(userService.login(login));
    }

    @ApiOperation("비밀번호 재확인")
    @PostMapping("/re/check/password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat<Boolean> reCheckPassword(@RequestBody String password) {
        return ResponseFormat.ok(userService.reCheckPassword(password));
    }

    @ApiOperation("아이디 중복확인")
    @PostMapping("/check/identity")
    public ResponseFormat<Boolean> checkIdentity(@RequestBody String identity) {
        return ResponseFormat.ok(userService.checkIdentity(identity));
    }

    @ApiOperation("비밀번호 초기화 전 사용자 정보 확인")
    @PostMapping("/reset/password/check")
    public ResponseFormat<Boolean> resetPasswordCheck(@RequestBody UserDto.RESET_CHECK reset) {
        return ResponseFormat.ok(userService.resetPasswordCheck(reset));
    }

    @ApiOperation("회원가입")
    @PostMapping
    public ResponseFormat signUp(@RequestBody UserDto.CREATE create) {
        userService.signUp(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("회원 정보 조회")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat<UserDto.READ> getUser() {
        return ResponseFormat.ok(userService.getUser());
    }

    @ApiOperation("아이디 찾기")
    @PostMapping("/find/identity")
    public ResponseFormat<List<String>> findIdentity(@RequestBody UserDto.ID_READ read) {
        return ResponseFormat.ok(userService.findIdentity(read));
    }

    @ApiOperation("회원 정보 수정")
    @PutMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat updateUser(@RequestBody UserDto.UPDATE update) {
        userService.updateUser(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("비밀번호 변경")
    @PutMapping("/password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat updatePassword(@RequestBody UserDto.UPDATE_PASSWORD update) {
        userService.updatePassword(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("비밀번호 초기화")
    @PutMapping("/reset/password")
    public ResponseFormat resetPassword(@RequestBody UserDto.RESET_PASSWORD reset) {
        userService.resetPassword(reset);
        return ResponseFormat.ok();
    }

    @ApiOperation("토큰 재발급")
    @GetMapping("/refresh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "accessToken 재발급을 위한 refreshToken", paramType = "header", required = true)
    })
    public ResponseFormat<String> refreshToken(@RequestHeader("Authorization") String refreshToken){
        return ResponseFormat.ok(jwtProvider.createAccessToken(refreshToken));
    }


}
