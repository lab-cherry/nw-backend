package lab.cherry.nw.error;

import lab.cherry.nw.error.enums.SuccessCode;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultResponse {
    private String message;
    private int status;

    private ResultResponse(final SuccessCode code) {
        this.status = code.getStatus();
        this.message = code.getMessage();
    }

    private ResultResponse(final SuccessCode code, final String customMessaage) {
        this.status = code.getStatus();
        this.message = customMessaage;
    }


    public static ResultResponse of(final SuccessCode code) {
        return new ResultResponse(code);
    }

    public static ResultResponse of(final SuccessCode code, final String customMessage) {
        return new ResultResponse(code, customMessage);
    }
}

