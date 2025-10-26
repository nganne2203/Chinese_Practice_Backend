package fptedu.nganmtt.ChinesePractice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    User createdBy;

    @Column(length = 100, nullable = false)
    String title;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    QuizType type;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Question> questions;

    Integer durationInMinutes;

    boolean timed;

    LocalDateTime startTime;

    LocalDateTime endTime;

    Integer attemptLimit;

    public enum QuizType {
        MULTIPLE_CHOICE,
        MATCHING,
        FILL_IN_BLANK;

        public static QuizType fromString(String type) {
            if (type == null) {
                throw new IllegalArgumentException("Quiz type cannot be null");
            }
            return switch (type.trim().toUpperCase()) {
                case "MULTIPLE_CHOICE" -> MULTIPLE_CHOICE;
                case "MATCHING" -> MATCHING;
                case "FILL_IN_BLANK" -> FILL_IN_BLANK;
                default -> throw new IllegalArgumentException("Invalid quiz type: " + type);
            };
        }
    }

}

