package fptedu.nganmtt.ChinesePractice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String title;
    @NotBlank(message = "FIELD_REQUIRED")
    int unitNumber;
    @NotBlank(message = "FIELD_REQUIRED")
    String levelId;
}
