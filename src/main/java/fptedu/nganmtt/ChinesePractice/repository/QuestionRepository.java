package fptedu.nganmtt.ChinesePractice.repository;

import fptedu.nganmtt.ChinesePractice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllByQuiz_Id(UUID quizId);
}
