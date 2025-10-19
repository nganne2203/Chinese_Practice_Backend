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
        description = "Response after refreshing tokens",
        title = "RefreshTokenResponse"
)
public class RefreshTokenResponse {
    String accessToken;
    String refreshToken;
    boolean authenticated;
}
