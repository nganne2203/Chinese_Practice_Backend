package fptedu.nganmtt.ChinesePractice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResponse {
    String id;
    String title;
    String type;
    LessonResponse lesson;
    UserResponse createdBy;
    Set<QuestionDetailResponse> questions;
}
