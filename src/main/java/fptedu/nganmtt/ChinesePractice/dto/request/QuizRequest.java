package fptedu.nganmtt.ChinesePractice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizRequest {
    String title;
    String type;
    String lessonId;
    String createdById;
    Set<QuestionDetailRequest> questions;
}
