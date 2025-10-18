package fptedu.nganmtt.ChinesePractice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizDetailResponse {
    String id;
    String title;
    String type;
    LessonResponse lesson;
    UserResponse createdBy;
}
