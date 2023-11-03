package lab.cherry.nw.error.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

/**
 * <pre>
 * ClassName : ErrorCode
 * Type : enum
 * Descrption : 에러 코드, 에러 메시지를 포함하고 있는 enum입니다.
 * Related : ErrorResponse
 * </pre>
 */
@Getter
@ToString
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SuccessCode {

    // Common
    OK(200, "Success"),
    FILE_UPLOAD_SUCCESS(200, "파일 업로드 완료"),
    REGISTER_SUCCESS(200, "회원가입 완료"),
    USERID_CHECK_OK(200, "사용 가능합니다."),
    PASSWORD_RESET_OK(200, "초기화 비밀번호 이메일 발송 완료"),
    EMAIL_INVITE_USER_OK(200, "사용자(랑부) 초대 이메일 발송 완료"),
    EMAIL_CHECK_OK(200, "이메일 인증 완료"),
    EMAIL_RESEND_OK(200, "이메일 인증 메일 재발송 완료");

    private final int status;
    private final String message;

    SuccessCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
    }