package fptedu.nganmtt.ChinesePractice.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        description = "Standard API response wrapper",
        title = "ApiResult"
)
public class ApiResult<T>{
    @Builder.Default
    int code = 1000;
    String message;
    T result;
}
