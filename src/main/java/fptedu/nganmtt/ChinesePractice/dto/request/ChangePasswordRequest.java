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
@Schema(description = "Request to change user password",
        title = "ChangePasswordRequest",
        requiredProperties = {"currentPassword", "newPassword"}
)
public class ChangePasswordRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String currentPassword;

    @NotBlank(message = "FIELD_REQUIRED")
    String newPassword;
}
