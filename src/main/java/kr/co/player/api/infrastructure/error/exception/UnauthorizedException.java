package kr.co.player.api.infrastructure.error.exception;


import kr.co.player.api.infrastructure.error.model.ErrorCode;

public class UnauthorizedException extends BusinessLogicException{
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED_USER);
    }
}
