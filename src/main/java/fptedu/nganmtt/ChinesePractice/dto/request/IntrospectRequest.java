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
        description = "Request to introspect an access token",
        title = "IntrospectRequest",
        requiredProperties = {"accessToken"}
)
public class IntrospectRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String accessToken;
}
