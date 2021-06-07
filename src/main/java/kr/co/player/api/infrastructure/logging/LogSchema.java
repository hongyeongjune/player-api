package kr.co.player.api.infrastructure.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.player.api.domain.shared.ObjectMapperUtil;
import kr.co.player.api.domain.shared.ResponseCode;
import kr.co.player.api.domain.shared.ResponseFormat;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import kr.co.player.api.infrastructure.utils.UserInfoConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Getter
@NoArgsConstructor
public class LogSchema {

    private String type;
    private String gender;
    private Integer age;
    private String location;
    private String api;
    private String httpMethod;
    private String responseCode;
    private String responseDescription;
    private String methodName;

    public LogSchema(LogType type, HttpServletRequest request, ResponseFormat response, String methodName) {
        this.type = type.toString();
        this.api = request.getRequestURI();
        this.httpMethod = request.getMethod();
        if(response != null) this.responseCode = ResponseCode.of(response.getCode()).toString();
        this.responseDescription = response.getDescription();
        this.methodName = methodName;
    }

    public LogSchema(UserEntity userEntity, LogType type, HttpServletRequest request, ResponseFormat response, String methodName) {

        if(userEntity.getGender() != null) this.gender = userEntity.getGender().getGender();
        else this.gender = null;

        if(userEntity.getBirth() != null) this.age = UserInfoConverter.getUserAge(userEntity);
        else this.age = null;

        if(userEntity.getAddress() != null) this.location = UserInfoConverter.getUserAddr(userEntity);
        else this.location = null;

        this.type = type.toString();
        this.api = request.getRequestURI();
        this.httpMethod = request.getMethod();
        if(response != null) this.responseCode = ResponseCode.of(response.getCode()).toString();
        this.responseDescription = response.getDescription();
        this.methodName = methodName;
    }

    @Override
    public String toString(){
        ObjectMapper objectMapper = ObjectMapperUtil.objectMapper;
        String jsonStr = "";
        try{
            jsonStr = objectMapper.writeValueAsString(this);
        }catch (IOException e){
            jsonStr = "jsonError";
        }
        return jsonStr;
    }

}
