package fptedu.nganmtt.ChinesePractice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import fptedu.nganmtt.ChinesePractice.model.Role;
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
@Schema(description = "User Response", requiredProperties = {
        "userName", "firstName", "lastName", "birthDate", "email"
})
public class UserResponse {

    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
    String id;

    @Schema(description = "Username of the user", example = "john_doe")
    String userName;

    @Schema(description = "First name of the user", example = "John")
    String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    String lastName;

    @Schema(description = "Birth date of the user in the format dd/MM/yyyy", example = "31/12/1990")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate birthDate;

    @Schema(description = "Email address of the user", example = "abc@gmail.com")
    String email;

    Set<RoleResponse> roles;

    @JsonFormat(pattern = "HH:mm:ss, dd/MM/yyyy")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm:ss, dd/MM/yyyy")
    LocalDateTime updatedAt;
}