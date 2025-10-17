package fptedu.nganmtt.ChinesePractice.dto.request;
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
public class UserUpdateRequest {
    @NotBlank(message = "FIELD_REQUIRED")
    String firstName;

    @NotBlank(message = "FIELD_REQUIRED")
    String lastName;

    @Email(message = "INVALID_EMAIL")
    String email;

    LocalDate birthDate;

    List<String> roles;
}
