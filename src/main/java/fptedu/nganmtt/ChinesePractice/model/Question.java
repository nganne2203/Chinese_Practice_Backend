package fptedu.nganmtt.ChinesePractice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "Question")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    Quiz quiz;

    @Lob
    @Column(name = "question_text", nullable = false)
    String questionText;

    @Column(name = "answer_correct", length = 255, nullable = false)
    String answer;

    @Lob
    @Column(name = "options")
    String options;
}

