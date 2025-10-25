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
        description = "Response containing detailed information about a quiz",
        title = "QuizDetailResponse"
)
public class QuizDetailResponse {
    String id;
    String title;
    String type;
    LessonResponse lesson;
    UserResponse createdBy;
    Integer durationInMinutes;
    boolean isTimed;
    LocalDateTime startTime;

    LocalDateTime endTime;

    Integer attemptLimit;
}
