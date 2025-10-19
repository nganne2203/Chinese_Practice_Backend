package fptedu.nganmtt.ChinesePractice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import fptedu.nganmtt.ChinesePractice.validator.DobConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        description = "Request to create a new user",
        title = "UserCreationRequest",
        requiredProperties = {"userName", "password", "email", "birthDate"}
)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String userName;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 2, message = "INVALID_DOB")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate birthDate;

    @NotBlank(message = "FIELD_REQUIRED")
    @Email(message = "INVALID_EMAIL")
    String email;
}
