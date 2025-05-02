package io.github.vsinkievic.mockwallesterapi.web.rest.errors;

import lombok.Getter;

public class WallesterApiException extends RuntimeException {

    @Getter
    private int httpStatus;

    @Getter
    private Long errorCode;

    @Getter
    private String errorText;

    @Getter
    private String message;

    public WallesterApiException(String message) {
        super(message);
        this.httpStatus = 500;
        this.errorCode = 500L;
        this.errorText = "Internal Server Error";
        this.message = message;
    }

    public WallesterApiException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = (long) httpStatus;
        this.errorText = null;
        this.message = message;
    }

    public WallesterApiException(int httpStatus, Long errorCode, String errorText, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorText = errorText;
        this.message = message;
    }
}
