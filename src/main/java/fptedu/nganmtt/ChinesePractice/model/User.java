package fptedu.nganmtt.ChinesePractice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(length = 50, unique = true, nullable = false)
    String userName;

    @Column(nullable = false)
    String password;

    String firstName;

    String lastName;

    LocalDate birthDate;

    @Column(length = 100)
    String email;

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

    @ManyToMany
    Set<Role> roles;
}