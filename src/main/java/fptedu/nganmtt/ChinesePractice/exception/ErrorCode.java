package fptedu.nganmtt.ChinesePractice.exception;

public enum ErrorCode {
    USER_EXISTED(1002, "User already exists"),
    USERNAME_INVALID(1003, "Username must be at least 3 characters"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters"),
    KEY_INVALID(1001, "Invalid message key"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception"),
    USER_NOT_FOUND(1005, "User not found")
    ;

    ErrorCode(int code, String message) {
        this.message = message;
        this.code = code;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
