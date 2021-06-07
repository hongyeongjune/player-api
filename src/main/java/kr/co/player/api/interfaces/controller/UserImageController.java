package kr.co.player.api.interfaces.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.player.api.domain.image.model.ImageDto;
import kr.co.player.api.domain.image.service.ImageDeleteService;
import kr.co.player.api.domain.image.service.ImageUploadService;
import kr.co.player.api.domain.image.service.UserImageService;
import kr.co.player.api.domain.shared.ResponseFormat;
import kr.co.player.api.infrastructure.infra.S3Delete;
import kr.co.player.api.infrastructure.infra.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users/image")
public class UserImageController {

    private final UserImageService userImageService;
    private final ImageUploadService imageUploadService;
    private final ImageDeleteService imageDeleteService;

    @ApiOperation("이미지 업로드")
    @PostMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true),
            @ApiImplicitParam(name = "Authorization", value = "이미지 종류 - MAIN or SUB", paramType = "header", required = true)
    })
    public ResponseFormat<String> upload(@RequestParam("file") MultipartFile[] multipartFiles, @RequestParam("type") String type) {
        return ResponseFormat.ok(imageUploadService.uploadUserImage(multipartFiles, type));
    }

    @ApiOperation("사용자 이미지 조회")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat<List<ImageDto.READ_USER>> getImages() {
        return ResponseFormat.ok(userImageService.getUserImages());
    }

    @ApiOperation("이미지 삭제")
    @DeleteMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat<String> delete(@RequestParam("path") String path) {
        return ResponseFormat.ok(imageDeleteService.deleteUserImage(path));
    }
}
