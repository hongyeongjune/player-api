package kr.co.player.api.infrastructure.error.exception;


public class ConvertFailException extends BusinessLogicException {
    public ConvertFailException(String domain) {
        super(String.format("Cannot convert %s", domain));
    }
}
