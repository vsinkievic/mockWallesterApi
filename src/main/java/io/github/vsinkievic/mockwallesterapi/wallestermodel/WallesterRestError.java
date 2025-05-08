package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.vsinkievic.mockwallesterapi.web.rest.errors.WallesterApiException;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WallesterRestError {

    @JsonIgnore
    private int httpStatus;

    @JsonProperty("error_code")
    private Long errorCode;

    @JsonProperty("error_text")
    private String errorText;

    @JsonProperty("message")
    private String message;

    public WallesterRestError(WallesterApiException exception) {
        this.httpStatus = exception.getHttpStatus();
        this.errorCode = exception.getErrorCode();
        this.errorText = exception.getErrorText();
        this.message = exception.getMessage();
    }

    public WallesterRestError(String message) {
        this.message = message;
    }

    public WallesterRestError errorText(String errorText) {
        this.errorText = errorText;
        return this;
    }

    public WallesterRestError errorCode(Long errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public WallesterRestError httpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }
}
