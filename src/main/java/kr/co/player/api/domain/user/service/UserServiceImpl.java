package kr.co.player.api.domain.user.service;

import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.domain.user.model.common.Gender;
import kr.co.player.api.domain.user.model.common.UserPhone;
import kr.co.player.api.domain.user.model.common.UserRole;
import kr.co.player.api.infrastructure.error.exception.BadRequestException;
import kr.co.player.api.infrastructure.error.exception.DuplicatedException;
import kr.co.player.api.infrastructure.error.exception.NotFoundException;
import kr.co.player.api.infrastructure.error.model.ErrorCode;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.persistence.entity.UserImageEntity;
import kr.co.player.api.infrastructure.persistence.repository.UserImageRepository;
import kr.co.player.api.infrastructure.persistence.repository.UserRepository;
import kr.co.player.api.infrastructure.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public UserDto.TOKEN login(UserDto.LOGIN login) {

        UserEntity userEntity = userRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new NotFoundException("UserEntity"));

        if(!passwordEncoder.matches(login.getPassword(), userEntity.getPassword())) {
            throw new BadRequestException("비밀번호가 올바르지 않습니다.");
        }

        String[] tokens = generateToken(userEntity);

        userEntity.updateFcmToken(login.getFcmToken());
        userEntity.updateRefreshToken(tokens[1]);

        return new UserDto.TOKEN(tokens[0], tokens[1]);
    }

    @Override
    public boolean reCheckPassword(String password) {
        return passwordEncoder.matches(password, UserThreadLocal.get().getPassword());
    }

    @Override
    public boolean checkIdentity(String identity) {
        return userRepository.existsByIdentity(identity);
    }

    @Override
    public boolean resetPasswordCheck(UserDto.RESET_CHECK reset) {
        return userRepository.existsByIdentityAndNameAndBirth(reset.getIdentity(), reset.getName(), reset.getBirth());
    }

    @Override
    public void signUp(UserDto.CREATE create) {

        if(userRepository.existsByIdentity(create.getIdentity())) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
        }

        userRepository.save(UserEntity.builder()
                .identity(create.getIdentity())
                .password(passwordEncoder.encode(create.getPassword()))
                .name(create.getName())
                .birth(create.getBirth())
                .gender(Gender.of(create.getGender()))
                .userPhone(new UserPhone(create.getUserPhone()))
                .role(UserRole.of(create.getRole()))
                .build());
    }

    @Override
    public UserDto.READ getUser() {
        UserEntity userEntity = UserThreadLocal.get();

        List<String> imageUrlList = userImageRepository.fetchByUserEntity(userEntity).stream()
                .map(userImageEntity -> userImageEntity.getUrl())
                .collect(Collectors.toList());

        return userEntity.toDomain(imageUrlList);
    }

    @Override
    public List<String> findIdentity(UserDto.ID_READ read) {
        return userRepository.findByNameAndBirth(read.getName(), read.getBirth()).stream()
                .map(userEntity -> userEntity.getIdentity())
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(UserDto.UPDATE update) {
        UserEntity userEntity = UserThreadLocal.get();
        userEntity.update(update);
        userRepository.save(userEntity);
    }

    @Override
    public void updatePassword(UserDto.UPDATE_PASSWORD update) {
        UserEntity userEntity = UserThreadLocal.get();
        if(!passwordEncoder.matches(update.getPassword(), userEntity.getPassword())) {
            throw new BadRequestException("비밀번호가 올바르지 않습니다.");
        }

        if(!update.getNewPassword().equals(update.getReNewPassword())) {
            throw new BadRequestException("변경하려는 비밀번호가 서로 일치하지 않습니다.");
        }

        userEntity.updatePassword(passwordEncoder.encode(update.getNewPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public void resetPassword(UserDto.RESET_PASSWORD reset) {
        if(!reset.getNewPassword().equals(reset.getReNewPassword())) {
            throw new BadRequestException("변경하려는 비밀번호가 서로 일치하지 않습니다.");
        }

        UserEntity userEntity = userRepository.findByIdentity(reset.getIdentity())
                .orElseThrow(() -> new NotFoundException("UserEntity"));
        userEntity.updatePassword(passwordEncoder.encode(reset.getNewPassword()));
        userRepository.save(userEntity);
    }

    private String[] generateToken(UserEntity userEntity) {
        String accessToken = jwtProvider.createAccessToken(userEntity.getIdentity(), userEntity.getRole(), userEntity.getName());
        String refreshToken = jwtProvider.createRefreshToken(userEntity.getIdentity(), userEntity.getRole(), userEntity.getName());

        return new String[]{accessToken, refreshToken};
    }
}
