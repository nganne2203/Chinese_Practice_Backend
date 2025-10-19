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
        description = "Response indicating whether the access token is valid",
        title = "IntrospectResponse"
)
public class IntrospectResponse {
    boolean valid;
}
