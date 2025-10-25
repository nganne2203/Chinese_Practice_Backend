package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
        description = "Request to create or update a quiz with questions",
        title = "QuizRequest",
        requiredProperties = {"title", "type", "lessonId", "createdById", "questions"}
)
public class QuizRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String title;
    @NotBlank(message = "FIELD_REQUIRED")
    String type;
    @NotBlank(message = "FIELD_REQUIRED")
    String lessonId;
    @NotBlank(message = "FIELD_REQUIRED")
    String createdById;
    @NotNull(message = "FIELD_REQUIRED")
    Set<QuestionDetailRequest> questions;

    Integer durationInMinutes;
    boolean isTimed;
    LocalDateTime startTime;

    LocalDateTime endTime;

    int attemptLimit;
}
