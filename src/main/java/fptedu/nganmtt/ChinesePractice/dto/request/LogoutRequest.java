package fptedu.nganmtt.ChinesePractice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutRequest {
    @NotBlank(message = "ACCESS_TOKEN_REQUIRED")
    String accessToken;
    @NotBlank(message = "REFRESH_TOKEN_REQUIRED")
    String refreshToken;
}
