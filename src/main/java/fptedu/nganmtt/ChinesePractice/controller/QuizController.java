package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.QuestionDetailRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.QuizDetailRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.QuizRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.QuestionResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizResponse;
import fptedu.nganmtt.ChinesePractice.service.QuestionService;
import fptedu.nganmtt.ChinesePractice.service.QuizService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class QuizController {
    QuizService quizService;
    QuestionService questionService;
    @PostMapping
    public ApiResult<QuizDetailResponse> createQuiz(@RequestBody QuizDetailRequest quizRequest) {
        return ApiResult.<QuizDetailResponse>builder()
                .result(quizService.createQuiz(quizRequest))
                .build();
    }

    @GetMapping("/{quizId}")
    public ApiResult<QuizResponse> getQuizById(@PathVariable String quizId) {
        return ApiResult.<QuizResponse>builder()
                .result(quizService.getQuizById(quizId))
                .build();
    }

    @GetMapping("/lesson/{lessonId}")
    public ApiResult<List<QuizDetailResponse>> getQuizzesByLessonId(@PathVariable String lessonId) {
        return ApiResult.<List<QuizDetailResponse>>builder()
                .result(quizService.getQuizzesByLessonId(lessonId))
                .build();
    }

    @DeleteMapping("/{quizId}")
    public ApiResult<Void> deleteQuiz(@PathVariable String quizId) {
        quizService.deleteQuiz(quizId);
        return ApiResult.<Void>builder()
                .message("Deleted quiz successfully")
                .build();
    }

    @PutMapping("/{quizId}")
    public ApiResult<Void> updateQuiz(@PathVariable String quizId, @RequestBody QuizDetailRequest quizRequest) {
        quizService.updateQuiz(quizId, quizRequest);
        return ApiResult.<Void>builder()
                .message("Updated quiz successfully")
                .build();
    }

    @PostMapping("/{quizId}/questions")
    public ApiResult<QuestionResponse> createQuestion(@PathVariable String quizId, @RequestBody QuestionDetailRequest questionRequest) {
        return ApiResult.<QuestionResponse>builder()
                .result(questionService.createQuestion(quizId, questionRequest))
                .build();
    }

    @PutMapping("/questions/{questionId}")
    public ApiResult<Void> updateQuestion(@PathVariable String questionId, @RequestBody QuestionDetailRequest questionRequest) {
        questionService.updateQuestion(questionId, questionRequest);
        return ApiResult.<Void>builder()
                .message("Updated question successfully")
                .build();
    }

    @DeleteMapping("/questions/{questionId}")
    public ApiResult<Void> deleteQuestion(@PathVariable String questionId) {
        questionService.deleteQuestion(questionId);
        return ApiResult.<Void>builder()
                .message("Deleted question successfully")
                .build();
    }

    @GetMapping("/questions/{questionId}")
    public ApiResult<QuestionResponse> getQuestionById(@PathVariable String questionId) {
        return ApiResult.<QuestionResponse>builder()
                .result(questionService.getQuestionById(questionId))
                .build();
    }

}
