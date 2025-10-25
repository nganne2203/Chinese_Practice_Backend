package fptedu.nganmtt.ChinesePractice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Response containing the result of a quiz attempt",
        title = "QuizResultResponse"
)
public class QuizResultResponse {
    int score;
    int totalQuestions;
    LocalDateTime completedAt;
}
