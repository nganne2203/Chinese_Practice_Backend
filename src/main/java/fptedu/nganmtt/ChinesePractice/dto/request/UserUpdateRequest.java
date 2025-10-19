package fptedu.nganmtt.ChinesePractice.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import fptedu.nganmtt.ChinesePractice.validator.DobConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
        description = "Request to update user information",
        title = "UserUpdateRequest",
        requiredProperties = {"firstName", "lastName", "email"}
)
public class UserUpdateRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String firstName;

    @NotBlank(message = "FIELD_REQUIRED")
    String lastName;

    @Email(message = "INVALID_EMAIL")
    String email;

    @DobConstraint(min = 2, message = "INVALID_DOB")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate birthDate;
}
