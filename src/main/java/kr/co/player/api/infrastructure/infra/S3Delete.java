package kr.co.player.api.infrastructure.infra;

import com.amazonaws.services.s3.AmazonS3;
import kr.co.player.api.domain.image.service.ImageDeleteService;
import kr.co.player.api.domain.image.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class S3Delete implements ImageDeleteService {

    private final UserImageService userImageService;
    private final AmazonS3 amazonS3;

    private final String bucket = "player-s3-bucket";
    private final String bucketUrl = "https://player-s3-bucket.s3.ap-northeast-2.amazonaws.com/";

    @Transactional
    @Override
    public String deleteUserImage(String path) {

        String[] key = path.split(bucketUrl);

        if(amazonS3.doesObjectExist(bucket, key[key.length-1])) {
            amazonS3.deleteObject(bucket, key[key.length-1]);
            userImageService.deleteUserImage(path);

            return "success";
        }

        return "fail";
    }
}
