package fptedu.nganmtt.ChinesePractice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "User Response",
        title = "UserResponse",
        requiredProperties = {
                "userName",
                "firstName",
                "lastName",
                "birthDate",
                "email"
        }
)
public class UserResponse {
    String id;
    String userName;
    String firstName;
    String lastName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate birthDate;
    String email;
    Set<RoleResponse> roles;
    @JsonFormat(pattern = "HH:mm:ss, dd/MM/yyyy")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm:ss, dd/MM/yyyy")
    LocalDateTime updatedAt;
}