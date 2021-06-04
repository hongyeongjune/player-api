package kr.co.player.api.infrastructure.error.exception.jwt;

import kr.co.player.api.infrastructure.error.exception.UserDefineException;

public class JwtTokenInvalidException extends UserDefineException {
    public JwtTokenInvalidException(String message) {
        super(message);
    }
}
