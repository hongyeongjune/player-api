package kr.co.player.api.infrastructure.error.exception;

import kr.co.player.api.infrastructure.error.model.ErrorCode;

public class DuplicatedException extends BusinessLogicException {
    public DuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
