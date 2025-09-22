package fptedu.nganmtt.ChinesePractice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String userName;
    String password;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String email;
    String role;
    LocalDate createAt;
    LocalDate updateAt;
}
