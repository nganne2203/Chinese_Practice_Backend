package fptedu.nganmtt.ChinesePractice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {

    @NotBlank(message = "FIELD_REQUIRED")
    String name;
    private String description;

    @NotBlank(message = "FIELD_REQUIRED")
    Set<String> permissions;
}
