package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Request to create or update a role",
        title = "RoleRequest",
        requiredProperties = {"name", "permissions"}
)
public class RoleRequest {

    @NotBlank(message = "FIELD_REQUIRED")
    String name;
    private String description;

    @NotBlank(message = "FIELD_REQUIRED")
    Set<String> permissions;
}
