package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.QuizDetailRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.QuizSubmissionRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizResultResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.QuizMapper;
import fptedu.nganmtt.ChinesePractice.model.Question;
import fptedu.nganmtt.ChinesePractice.model.Quiz;
import fptedu.nganmtt.ChinesePractice.model.UserProgress;
import fptedu.nganmtt.ChinesePractice.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    QuestionRepository questionRepository;
    UserProgressRepository userProgressRepository;

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
        } catch (AppException e) {
            log.error(e.getMessage());
            throw e;
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
        } catch (AppException e) {
            log.error(e.getMessage());
            throw e;
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
        } catch (AppException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error fetching quizzes for lesson id {}: {}", lessonId, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<QuizDetailResponse> getAllQuizzes() {
        try {
            return quizRepository.findAll()
                    .stream()
                    .map(quizMapper::toQuizDetailResponse)
                    .toList();
        } catch (AppException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error fetching all quizzes: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public QuizResponse getQuizById(String id) {
        try {
            return quizRepository.findById(java.util.UUID.fromString(id))
                    .map(quizMapper::toQuizResponse)
                    .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_FOUND));
        } catch (AppException e) {
            log.error(e.getMessage());
            throw e;
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
        } catch (AppException e) {
            log.error("Error updating quiz with id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error updating quiz with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public void startQuiz(String userId,String quizId) {
        try {
            var quiz = quizRepository.findById(java.util.UUID.fromString(quizId))
                    .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

            var user = userRepository.findById(java.util.UUID.fromString(userId))
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            if (quiz.isTimed() && quiz.getStartTime() != null) {
                LocalDateTime now = LocalDateTime.now();

                if (now.isBefore(quiz.getStartTime()) || now.isAfter(quiz.getEndTime())) {
                    throw new AppException(ErrorCode.QUIZ_NOT_AVAILABLE);
                }
            }

            if (quiz.getAttemptLimit() != null) {
                long attempts = userProgressRepository.countByQuiz_IdAndUser_Id(quiz.getId(), user.getId());
                if (attempts >= quiz.getAttemptLimit()) {
                    throw new AppException(ErrorCode.QUIZ_ATTEMPT_LIMIT_EXCEEDED);
                }
            }
        } catch (AppException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error starting quiz with id {}: {}", quizId, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public QuizResultResponse submitQuiz(String quizId, QuizSubmissionRequest request) {
        try {
            var quiz = quizRepository.findById(java.util.UUID.fromString(quizId))
                    .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

            var user = userRepository.findById(java.util.UUID.fromString(request.getUserId()))
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            var questions = questionRepository.findAllByQuiz_Id(java.util.UUID.fromString(quizId));

            int totalQuestions = questions.size();

            int correct = 0;

            for (Question question : questions) {
                String userAnswer = request.getAnswers().get(question.getId());

                if (userAnswer != null && question.getAnswer().trim().equalsIgnoreCase(userAnswer.trim()))
                    correct++;
            }

            int score = (int) ((correct * 100.0) / totalQuestions);

            UserProgress progress = UserProgress.builder()
                    .user(user)
                    .quiz(quiz)
                    .score(score)
                    .lesson(quiz.getLesson())
                    .completedAt(LocalDateTime.now())
                    .build();

            userProgressRepository.save(progress);

            return QuizResultResponse.builder()
                    .score(score)
                    .totalQuestions(totalQuestions)
                    .completedAt(progress.getCompletedAt())
                    .build();
        } catch (AppException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error submitting quiz with id {}: {}", quizId, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
