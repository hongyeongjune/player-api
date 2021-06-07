package kr.co.player.api.domain.image.service;

import kr.co.player.api.domain.image.model.ImageDto;
import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;

import java.util.List;

public interface UserImageService {

    //create
    String createImageUrl(ImageDto.CREATE_USER create);

    //read
    List<ImageDto.READ_USER> getUserImages();

    //delete
    void deleteUserImage(String url);
}
