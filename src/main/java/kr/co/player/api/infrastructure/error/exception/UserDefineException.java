package kr.co.player.api.infrastructure.error.exception;

import lombok.Getter;

@Getter
public class UserDefineException extends RuntimeException {
    private String errorMessage;

    public UserDefineException(String message) {
        super(message);
    }

    public UserDefineException(String message, String errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }
}
