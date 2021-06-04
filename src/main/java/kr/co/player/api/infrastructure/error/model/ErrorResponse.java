package kr.co.player.api.infrastructure.error.model;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private String message;

    public static ErrorResponse of(ErrorCode errorCode){
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(String errorMessage){
        return ErrorResponse.builder()
                .status(400)
                .message(errorMessage)
                .build();
    }
}
