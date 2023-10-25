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
public enum ErrorCode {

    // Common
    RUNTIME_EXCEPTION(400, "잘못된 요청입니다."),
    INVALID_INPUT_VALUE(400, "유효하지 않는 입력 값입니다."),
    ENTITY_NOT_FOUND(400, "데이터를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(405, "허용되지 않는 메소드입니다."),
    INVALID_TYPE_VALUE(400, "유효하지 않은 유형 값입니다."),
    INVALID_USERNAME(400, "잘못된 사용자 이름/비밀번호를 입력했습니다."),
    INTERNAL_SERVER_ERROR(500, "서버에 문제가 발생했습니다."),
    NOT_FOUND(404, "찾을 수 없습니다."),
    FORBIDDEN(403, "접근 권한이 없어 거부되었습니다."),
    ACCESS_DENIED_EXCEPTION(401, "인증 정보가 유효하지 않습니다."),
    DUPLICATE(409, "중복된 데이터가 있습니다."),
    NO_BODY(400, "파라미터 값이 입력되지 않았습니다."),
    URL_NOTFOUND(400, "Minio URL을 다시 확인해주세요."),
    FILE_UPLOAD_FAILED(400, "파일 업로드에 실패했습니다."),
    FOLDER_CREATE_FAILED(400, "uploadPath를 찾을 수 없습니다.");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
    }