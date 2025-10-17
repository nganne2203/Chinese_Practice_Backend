package fptedu.nganmtt.ChinesePractice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_EXISTED(1002, "User already exists", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    KEY_INVALID(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1005, "User not found", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_EXCEPTION(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1007, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    INVALID_EMAIL(1008, "Invalid email address", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1009, "Email already exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1010, "Role not found", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1011, "Permission not found", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_REQUIRED(1012, "Refresh token is required", HttpStatus.BAD_REQUEST),
    ACCESS_TOKEN_REQUIRED(1013, "Access token is required", HttpStatus.BAD_REQUEST),
    FIELD_REQUIRED(1014, "You are missing the required field", HttpStatus.BAD_REQUEST),
    HSK_LEVEL_NOT_FOUND(2001, "HSK level not found", HttpStatus.BAD_REQUEST),
    UNIT_NOT_FOUND(2002, "Unit not found", HttpStatus.BAD_REQUEST),
    LESSON_NOT_FOUND(2003, "Lesson not found", HttpStatus.BAD_REQUEST),
    VOCABULARY_NOT_FOUND(2004, "Vocabulary not found", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
