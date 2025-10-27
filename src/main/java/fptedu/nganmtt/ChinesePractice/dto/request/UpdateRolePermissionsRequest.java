package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Request to update role permissions",
        title = "UpdateRolePermissionsRequest",
        requiredProperties = {"permissions"}
)
public class UpdateRolePermissionsRequest {
    @NotNull(message = "FIELD_REQUIRED")
    Set<String> permissions;
}
