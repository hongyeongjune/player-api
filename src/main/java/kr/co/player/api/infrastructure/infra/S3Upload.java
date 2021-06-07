package kr.co.player.api.infrastructure.infra;

import com.amazonaws.services.s3.AmazonS3;
import kr.co.player.api.domain.image.model.ImageDto;
import kr.co.player.api.domain.image.model.common.ImageType;
import kr.co.player.api.domain.image.service.ImageUploadService;
import kr.co.player.api.domain.image.service.UserImageService;
import kr.co.player.api.infrastructure.error.exception.BadRequestException;
import kr.co.player.api.infrastructure.error.exception.ConvertFailException;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Upload implements ImageUploadService {

    private final UserImageService userImageService;
    private final AmazonS3 amazonS3;

    private final String bucket = "player-s3-bucket";
    private final String bucketUrl = "https://player-s3-bucket.s3.ap-northeast-2.amazonaws.com/";

    @Override
    public String uploadUserImage(MultipartFile[] multipartFiles, String type) {

        UserEntity userEntity = UserThreadLocal.get();

        String key = "USER/" + userEntity.getIdentity();

        Arrays.stream(multipartFiles)
                .map(this::convert)
                .forEach(file -> {
                    String fullKey = generateKey(key, "U");

                    String url = userImageService.createImageUrl(
                            ImageDto.CREATE_USER.builder()
                                    .url(bucketUrl + fullKey)
                                    .userEntity(userEntity)
                                    .imageType(ImageType.of(type))
                                    .build()
                    );

                    try {
                        amazonS3.putObject(bucket, fullKey + url, file);
                    } catch (Exception e) {
                        throw new BadRequestException("S3 파일 업로드 실패 : " + e.getMessage());
                    }

                    if(!file.delete())
                        throw new ConvertFailException("S3");
                });

        return "success";
    }

    private File convert(MultipartFile multipartFile){
        try {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            return file;
        }catch (Exception e){
            throw new ConvertFailException("S3");
        }
    }

    private String generateKey(String key, String seq){
        return key + "/" + UUID.randomUUID().toString() + seq;
    }
}
