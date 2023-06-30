package lab.cherry.nw.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.model.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL) // null 일 때만 필드를 무시
public class ResultResponse {
    private String message;
    private Object data;
    private int status;

    private ResultResponse(final SuccessCode code) {
        this.status = code.getStatus();
        this.message = code.getMessage();
    }

    private ResultResponse(final SuccessCode code, final Object data) {
        this.status = code.getStatus();
        this.data = data;
    }


    public static ResultResponse of(final SuccessCode code) {
        return new ResultResponse(code);
    }

    public static ResultResponse of(final SuccessCode code, final Object data) {
        return new ResultResponse(code, data);
    }

}

