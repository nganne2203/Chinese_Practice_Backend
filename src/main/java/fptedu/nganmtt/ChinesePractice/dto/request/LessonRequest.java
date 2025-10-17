package fptedu.nganmtt.ChinesePractice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String title;
    String description;
    @NotBlank(message = "FIELD_REQUIRED")
    String unitId;
}
