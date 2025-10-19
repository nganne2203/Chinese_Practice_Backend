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
@Schema(description = "Request to create or update HSK level",
        title = "HskLevelRequest",
        requiredProperties = {"name"}
)
public class HskLevelRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String name;
}
