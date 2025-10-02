package fptedu.nganmtt.ChinesePractice.exception;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResult> handlingRuntimeException(RuntimeException exception) {
        ApiResult apiResult = new ApiResult();

        apiResult.setCode(1001);
        apiResult.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(apiResult);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResult> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(errorCode.getCode());
        apiResult.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResult);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResult> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getBindingResult().getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.KEY_INVALID;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {

        }
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(errorCode.getCode());
        apiResult.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResult);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResult> handlingException(Exception exception) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResult.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResult);
    }

}
