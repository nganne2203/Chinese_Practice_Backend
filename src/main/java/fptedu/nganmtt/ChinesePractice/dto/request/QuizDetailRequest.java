package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Request to create or update a quiz detail",
        title = "QuizDetailRequest",
        requiredProperties = {"title", "type", "lessonId", "createdById"}
)
public class QuizDetailRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String title;

    @NotBlank(message = "FIELD_REQUIRED")
    String type;

    @NotBlank(message = "FIELD_REQUIRED")
    String lessonId;

    @NotBlank(message = "FIELD_REQUIRED")
    String createdById;

    Integer durationInMinutes;
    boolean timed;
    LocalDateTime startTime;

    LocalDateTime endTime;

    Integer attemptLimit;
}
