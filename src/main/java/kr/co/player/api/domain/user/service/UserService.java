package kr.co.player.api.domain.user.service;

import kr.co.player.api.domain.user.model.UserDto;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;

import java.util.List;

public interface UserService {

    //common - service
    UserDto.TOKEN login(UserDto.LOGIN login);
    boolean reCheckPassword(String password);
    boolean checkIdentity(String identity);
    boolean resetPasswordCheck(UserDto.RESET_CHECK reset);

    //create - service
    void signUp(UserDto.CREATE create);

    //read - service
    UserDto.READ getUser();
    List<String> findIdentity(UserDto.ID_READ read);

    //read - integrated
    UserEntity getUserEntity(String identity);

    //update - service
    void updateUser(UserDto.UPDATE update);
    void updatePassword(UserDto.UPDATE_PASSWORD update);
    void resetPassword(UserDto.RESET_PASSWORD reset);

    //delete

}
