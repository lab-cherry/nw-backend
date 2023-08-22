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
    RUNTIME_EXCEPTION(400, "Bad Request."),
    INVALID_INPUT_VALUE(400, "Invalid Input Value."),
    ENTITY_NOT_FOUND(400, "Entity Not Found"),
    METHOD_NOT_ALLOWED(405, "Method not Allowed."),
    INVALID_TYPE_VALUE(400, "Invalid Type Value."),
    INVALID_USERNAME(400, "Invalid username/password supplied."),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error."),
    FORBIDDEN(403, "Forbidden."),
    NOT_FOUND(404, "Not Found."),
    HANDLE_ACCESS_DENIED(403, "Access is Denied."),
    ACCESS_DENIED_EXCEPTION(401, "Unauthorized."),
    DUPLICATE(409, "Already Exist."),
    NO_BODY(400, "No Parameters.");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
    }