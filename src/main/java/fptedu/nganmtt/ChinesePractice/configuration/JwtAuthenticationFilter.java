package fptedu.nganmtt.ChinesePractice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.error("JWT Exception occurred: {}", e.getMessage());
            handleJwtException(response, e);
        } catch (Exception e) {
            // Check if the exception is caused by a JWT exception
            Throwable cause = e.getCause();
            if (cause instanceof JwtException) {
                log.error("JWT Exception occurred (wrapped): {}", cause.getMessage());
                handleJwtException(response, (JwtException) cause);
            } else {
                throw e;
            }
        }
    }

    private void handleJwtException(HttpServletResponse response, JwtException exception) throws IOException {
        ErrorCode errorCode;

        String message = exception.getMessage().toLowerCase();
        if (message.contains("expired")) {
            errorCode = ErrorCode.TOKEN_EXPIRED;
        } else if (message.contains("invalid")) {
            errorCode = ErrorCode.UNAUTHENTICATED;
        } else {
            errorCode = ErrorCode.UNAUTHENTICATED;
        }

        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResult<?> apiResult = ApiResult.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(apiResult));
        response.flushBuffer();
    }
}
