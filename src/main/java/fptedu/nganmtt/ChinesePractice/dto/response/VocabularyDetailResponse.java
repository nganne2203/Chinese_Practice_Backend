package fptedu.nganmtt.ChinesePractice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularyDetailResponse {
    String id;
    String hanzi;
    String pinyin;
    String meaning;
    String exampleSentence;
}
