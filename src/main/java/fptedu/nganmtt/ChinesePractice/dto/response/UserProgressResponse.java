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
        description = "Response containing user progress information",
        title = "UserProgressResponse"
)
public class UserProgressResponse {
    UserResponse user;
    QuizResponse quiz;
    LessonResponse lesson;
    int score;
    LocalDateTime completedAt;
}
