package fptedu.nganmtt.ChinesePractice.exception;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResult<?>> handlingRuntimeException(RuntimeException exception) {
        return ResponseEntity.badRequest()
                .body(ApiResult.builder()
                        .code(1001)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResult<?>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResult.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResult<?>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getBindingResult().getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.KEY_INVALID;

        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolation = exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {
            log.warn(enumKey + ": " + e.getMessage());
        }

        return ResponseEntity.badRequest()
                .body(ApiResult.builder()
                        .code(errorCode.getCode())
                        .message(Objects.nonNull(attributes) ? mapAttribute(errorCode.getMessage(), attributes) : errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResult<?>> handlingException(Exception exception) {
        return ResponseEntity.badRequest()
                .body(ApiResult
                        .builder()
                        .code(1001)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResult<?>> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED_EXCEPTION;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResult.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException ex) {
        ErrorCode errorCode = ErrorCode.TOKEN_EXPIRED;
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResult.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue =  String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
