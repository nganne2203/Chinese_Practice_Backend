package fptedu.nganmtt.ChinesePractice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionRequest {
    String questionText;
    String answer;
    List<String> options;
    String quizId;
}
