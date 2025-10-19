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
@Schema(description = "Authentication request containing username and password",
        title = "AuthenticationRequest",
        requiredProperties = {"userName", "password"}
)
public class AuthenticationRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String userName;

    @NotBlank(message = "FIELD_REQUIRED")
    String password;
}
