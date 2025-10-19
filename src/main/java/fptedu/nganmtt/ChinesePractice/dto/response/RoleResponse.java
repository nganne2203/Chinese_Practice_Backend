package fptedu.nganmtt.ChinesePractice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Response containing role details",
        title = "RoleResponse"
)
public class RoleResponse {
    String name;
    String description;
    Set<PermissionResponse> permissions;
}
