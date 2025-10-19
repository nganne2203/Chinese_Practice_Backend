package fptedu.nganmtt.ChinesePractice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Request to create or update a vocabulary",
        title = "VocabularyRequest",
        requiredProperties = {"hanzi", "pinyin", "meaning", "lessonId"}
)
public class VocabularyRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String hanzi;
    @NotBlank(message = "FIELD_REQUIRED")
    String pinyin;
    @NotBlank(message = "FIELD_REQUIRED")
    String meaning;
    String exampleSentence;
    @NotBlank(message = "FIELD_REQUIRED")
    String lessonId;
}
