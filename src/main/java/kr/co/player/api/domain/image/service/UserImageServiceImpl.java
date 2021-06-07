package kr.co.player.api.domain.image.service;

import kr.co.player.api.domain.image.model.ImageDto;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;
import kr.co.player.api.infrastructure.persistence.repository.UserImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserImageServiceImpl implements UserImageService {

    private final UserImageRepository userImageRepository;

    @Override
    public String createImageUrl(ImageDto.CREATE_USER create) {
        Optional<UserImageEntity> userImageEntity = userImageRepository.fetchFirstOrderByIdDesc();
        if(userImageEntity.isPresent()) {
            Long count = userImageEntity.get().getId();
            count += 1;
            userImageRepository.save(
                    UserImageEntity.builder()
                            .url(create.getUrl() + count)
                            .userEntity(create.getUserEntity())
                            .imageType(create.getImageType())
                            .build()
            );

            return String.valueOf(count);
        }

        userImageRepository.save(
                UserImageEntity.builder()
                        .url(create.getUrl() + 1)
                        .userEntity(create.getUserEntity())
                        .imageType(create.getImageType())
                        .build()
        );

        return String.valueOf(1);
    }

    @Override
    public List<ImageDto.READ_USER> getUserImages() {
        UserEntity userEntity = UserThreadLocal.get();
        return userImageRepository.fetchByUserEntity(userEntity).stream()
                .map(UserImageEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteUserImage(String url) {
        UserEntity userEntity = UserThreadLocal.get();
        UserImageEntity userImageEntity = userImageRepository.fetchByUserEntityAndUrl(userEntity, url)
                .orElseThrow(() -> new NotFoundException("UserImageEntity"));

        userImageEntity.deleted();
    }
}
