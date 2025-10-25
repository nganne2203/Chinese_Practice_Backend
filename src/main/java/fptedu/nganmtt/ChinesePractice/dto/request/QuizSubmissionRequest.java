package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Request to submit answers for a quiz",
        title = "QuizSubmissionRequest",
        requiredProperties = {"userId", "answers"}
)
public class QuizSubmissionRequest {
    String userId;
    Map<UUID, String> answers;
}
