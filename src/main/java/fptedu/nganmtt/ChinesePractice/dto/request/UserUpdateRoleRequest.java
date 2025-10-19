package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Request to update user roles",
        title = "UserUpdateRoleRequest",
        requiredProperties = {"roles"}
)
public class UserUpdateRoleRequest {
    @NotNull(message = "FIELD_REQUIRED")
    List<String> roles;
}
