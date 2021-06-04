package kr.co.player.api.infrastructure.error.exception.jwt;

import kr.co.player.api.infrastructure.error.exception.BusinessLogicException;

public class JwtTokenExpiredException extends BusinessLogicException {
    public JwtTokenExpiredException() {
        super("jwt token expired");
    }
}
