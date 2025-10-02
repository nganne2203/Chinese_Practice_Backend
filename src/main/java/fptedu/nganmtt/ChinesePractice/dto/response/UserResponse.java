package fptedu.nganmtt.ChinesePractice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserResponse {
    String id;
    String userName;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String email;
    Set<String> roles;

    @JsonFormat(pattern = "HH:mm:ss, dd/MM/yyyy")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "HH:mm:ss, dd/MM/yyyy")
    LocalDateTime updatedAt;
}
