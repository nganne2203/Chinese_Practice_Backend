package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Request to create or update a unit",
        title = "UnitRequest",
        requiredProperties = {"title", "unitNumber", "levelId"}
)
public class UnitRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String title;
    @NotNull(message = "FIELD_REQUIRED")
    int unitNumber;
    @NotBlank(message = "FIELD_REQUIRED")
    String levelId;
}
