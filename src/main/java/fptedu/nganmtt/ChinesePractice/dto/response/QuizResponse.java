package fptedu.nganmtt.ChinesePractice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
        description = "Response containing quiz details along with associated questions",
        title = "QuizResponse"
)
public class QuizResponse {
    String id;
    String title;
    String type;
    LessonResponse lesson;
    UserResponse createdBy;
    Set<QuestionDetailResponse> questions;
    Integer durationInMinutes;
    boolean timed;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Integer attemptLimit;
}
