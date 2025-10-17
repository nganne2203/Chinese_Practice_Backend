package fptedu.nganmtt.ChinesePractice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularyRequest {
    String hanzi;
    String pinyin;
    String meaning;
    String exampleSentence;
    String lessonId;
}
