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
        description = "Request to update a unit",
        title = "UnitUpdateRequest",
        requiredProperties = {"title", "unitNumber"}
)
public class UnitUpdateRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String title;
    @NotBlank(message = "FIELD_REQUIRED")
    int unitNumber;
    @NotBlank(message = "FIELD_REQUIRED")
    String levelId;
}
