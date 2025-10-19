package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Logout request containing access and refresh tokens",
        title = "LogoutRequest",
        requiredProperties = {"accessToken", "refreshToken"}
)
public class LogoutRequest {
    @NotBlank(message = "ACCESS_TOKEN_REQUIRED")
    String accessToken;
    @NotBlank(message = "REFRESH_TOKEN_REQUIRED")
    String refreshToken;
}
