package fptedu.nganmtt.ChinesePractice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Response containing detailed vocabulary information",
        title = "VocabularyDetailResponse"
)
public class VocabularyDetailResponse {
    String id;
    String hanzi;
    String pinyin;
    String meaning;
    String exampleSentence;
}
