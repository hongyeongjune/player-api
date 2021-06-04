package kr.co.player.api.infrastructure.error.exception;


public class BadRequestException extends BusinessLogicException {
    public BadRequestException(String message) {
        super(String.format("Bad Request : %s", message));
    }
}
