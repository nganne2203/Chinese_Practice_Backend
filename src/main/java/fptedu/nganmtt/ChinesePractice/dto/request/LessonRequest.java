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
        description = "Request to create or update a lesson",
        title = "LessonRequest",
        requiredProperties = {"title", "unitId"}
)
public class LessonRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String title;
    String description;
    @NotBlank(message = "FIELD_REQUIRED")
    String unitId;
}
