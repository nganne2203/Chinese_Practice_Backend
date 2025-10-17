package fptedu.nganmtt.ChinesePractice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitRequest {
    String title;
    int unitNumber;
    String levelId;
}
