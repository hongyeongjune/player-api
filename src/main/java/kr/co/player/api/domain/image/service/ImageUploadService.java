package kr.co.player.api.domain.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    String uploadUserImage(MultipartFile[] multipartFiles, String type);
}
