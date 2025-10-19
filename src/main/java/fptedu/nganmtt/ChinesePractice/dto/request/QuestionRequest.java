package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Request to create or update a question",
        title = "QuestionRequest",
        requiredProperties = {"questionText", "answer", "options", "quizId"}
)
public class QuestionRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String questionText;
    @NotBlank(message = "FIELD_REQUIRED")
    String answer;
    @NotNull(message = "FIELD_REQUIRED")
    List<String> options;
    @NotBlank(message = "FIELD_REQUIRED")
    String quizId;
}
