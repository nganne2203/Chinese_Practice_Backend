package fptedu.nganmtt.ChinesePractice.repository;

import fptedu.nganmtt.ChinesePractice.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserProgressRepository extends JpaRepository<UserProgress, UUID> {
    List<UserProgress> findAllByUser_Id(UUID userId);

    long countByQuiz_IdAndUser_Id(UUID quizId, UUID userId);
}
