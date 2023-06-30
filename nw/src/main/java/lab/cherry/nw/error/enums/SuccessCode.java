package lab.cherry.nw.error.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SuccessCode {

    // Common
    OK(200, "Success");

    private final int status;
    private final String message;

    SuccessCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
    }