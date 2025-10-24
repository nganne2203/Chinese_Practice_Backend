package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.QuizDetailRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.QuizMapper;
import fptedu.nganmtt.ChinesePractice.model.Quiz;
import fptedu.nganmtt.ChinesePractice.repository.LessonRepository;
import fptedu.nganmtt.ChinesePractice.repository.QuizRepository;
import fptedu.nganmtt.ChinesePractice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizService {
    QuizRepository quizRepository;
    QuizMapper quizMapper;
    UserRepository userRepository;
    LessonRepository lessonRepository;

    @PreAuthorize("hasAuthority('QUIZ_MODIFY') or hasRole('ADMIN')")
    public QuizDetailResponse createQuiz(QuizDetailRequest request) {
        try {
            var quiz = quizMapper.toQuizDetail(request);
            var createBy = userRepository
                    .findById(java.util.UUID.fromString(request.getCreatedById()))
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            var lesson = lessonRepository
                    .findById(java.util.UUID.fromString(request.getLessonId()))
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
            Quiz.QuizType type = Quiz.QuizType.fromString(request.getType());
            quiz.setType(type);
            quiz.setCreatedBy(createBy);
            quiz.setLesson(lesson);

            return quizMapper.toQuizDetailResponse(quizRepository.save(quiz));
        } catch (Exception e) {
            log.error("Error creating quiz: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('QUIZ_MODIFY') or hasRole('ADMIN')")
    public void deleteQuiz(String id) {
        try {
            var quiz = quizRepository.findById(java.util.UUID.fromString(id))
                    .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_FOUND));
            quizRepository.delete(quiz);
        } catch (Exception e) {
            log.error("Error deleting quiz with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<QuizDetailResponse> getQuizzesByLessonId(String lessonId) {
        try {
            return quizRepository.findAllByLesson_Id(java.util.UUID.fromString(lessonId))
                    .stream()
                    .map(quizMapper::toQuizDetailResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching quizzes for lesson id {}: {}", lessonId, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public QuizResponse getQuizById(String id) {
        try {
            return quizRepository.findById(java.util.UUID.fromString(id))
                    .map(quizMapper::toQuizResponse)
                    .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_FOUND));
        } catch (Exception e) {
            log.error("Error fetching quiz by id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('QUIZ_MODIFY') or hasRole('ADMIN')")
    public void updateQuiz(String id, QuizDetailRequest request) {
        try {
            var existingQuiz = quizRepository.findById(java.util.UUID.fromString(id))
                    .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_FOUND));
            if (!userRepository.existsById(java.util.UUID.fromString(request.getCreatedById()))) {
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }

            if (!lessonRepository.existsById(java.util.UUID.fromString(request.getLessonId()))) {
                throw new AppException(ErrorCode.LESSON_NOT_FOUND);
            }
            quizMapper.updateQuizDetail(request, existingQuiz);
            quizRepository.save(existingQuiz);
        } catch (Exception e) {
            log.error("Error updating quiz with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
