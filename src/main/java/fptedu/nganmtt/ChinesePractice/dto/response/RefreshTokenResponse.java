package fptedu.nganmtt.ChinesePractice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenResponse {
    String accessToken;
    String refreshToken;
    boolean authenticated;
}
