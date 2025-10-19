package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.QuestionDetailRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.QuestionResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.QuestionMapper;
import fptedu.nganmtt.ChinesePractice.repository.QuestionRepository;
import fptedu.nganmtt.ChinesePractice.repository.QuizRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionService {
    QuestionRepository questionRepository;
    QuizRepository quizRepository;
    QuestionMapper questionMapper;

    public QuestionResponse createQuestion(String quizId, QuestionDetailRequest request) {
        try {
            var question = questionMapper.toQuestionByDetail(request);
            var quiz = quizRepository.findById(java.util.UUID.fromString(quizId))
                    .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

            question.setQuiz(quiz);
            return questionMapper.toQuestionResponse(questionRepository.save(question));
        } catch (Exception e) {
            log.error("Error creating question for quiz id {}: {}", quizId, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public void deleteQuestion(String id) {
        try {
            var question = questionRepository.findById(java.util.UUID.fromString(id))
                    .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND));
            questionRepository.delete(question);
        } catch (Exception e) {
            log.error("Error deleting question with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public QuestionResponse getQuestionById(String id) {
        try {
            return questionRepository.findById(java.util.UUID.fromString(id))
                    .map(questionMapper::toQuestionResponse)
                    .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND));
        } catch (Exception e) {
            log.error("Error fetching question by id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public void updateQuestion(String id, QuestionDetailRequest request) {
        try {
            var existingQuestion = questionRepository.findById(java.util.UUID.fromString(id))
                    .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND));

            if (!quizRepository.existsById(java.util.UUID.fromString(id))) {
                throw new AppException(ErrorCode.QUIZ_NOT_FOUND);
            }
            questionMapper.updateQuestion(existingQuestion, request);
            questionRepository.save(existingQuestion);
        } catch (Exception e) {
            log.error("Error updating question with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
