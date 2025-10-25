package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.*;
import fptedu.nganmtt.ChinesePractice.dto.response.QuestionResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.QuizResultResponse;
import fptedu.nganmtt.ChinesePractice.service.QuestionService;
import fptedu.nganmtt.ChinesePractice.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Create a new quiz", description = "Creates a new quiz with the provided details.", tags = {"Quiz"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDetailResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lesson not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping
    public ApiResult<QuizDetailResponse> createQuiz(@RequestBody @Valid QuizDetailRequest quizRequest) {
        return ApiResult.<QuizDetailResponse>builder()
                .result(quizService.createQuiz(quizRequest))
                .build();
    }

    @Operation(summary = "Get quiz by ID", description = "Retrieves a quiz by its unique identifier.", tags = {"Quiz"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping("/{quizId}")
    public ApiResult<QuizResponse> getQuizById(@PathVariable String quizId) {
        return ApiResult.<QuizResponse>builder()
                .result(quizService.getQuizById(quizId))
                .build();
    }

    @Operation(summary = "Get quizzes by lesson ID", description = "Retrieves all quizzes associated with a specific lesson.", tags = {"Quiz"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDetailResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping("/lesson/{lessonId}")
    public ApiResult<List<QuizDetailResponse>> getQuizzesByLessonId(@PathVariable String lessonId) {
        return ApiResult.<List<QuizDetailResponse>>builder()
                .result(quizService.getQuizzesByLessonId(lessonId))
                .build();
    }

    @Operation(summary = "Delete a quiz", description = "Deletes a quiz by its unique identifier.", tags = {"Quiz"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @DeleteMapping("/{quizId}")
    public ApiResult<Void> deleteQuiz(@PathVariable String quizId) {
        quizService.deleteQuiz(quizId);
        return ApiResult.<Void>builder()
                .message("Deleted quiz successfully")
                .build();
    }

    @Operation(summary = "Update a quiz", description = "Updates the details of an existing quiz.", tags = {"Quiz"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lesson not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PutMapping("/{quizId}")
    public ApiResult<Void> updateQuiz(@PathVariable String quizId, @RequestBody @Valid QuizDetailRequest quizRequest) {
        quizService.updateQuiz(quizId, quizRequest);
        return ApiResult.<Void>builder()
                .message("Updated quiz successfully")
                .build();
    }

    @Operation(summary = "Create a new question for a quiz", description = "Creates a new question associated with a specific quiz.", tags = {"Question"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Quiz not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping("/{quizId}/questions")
    public ApiResult<QuestionResponse> createQuestion(@PathVariable String quizId, @RequestBody @Valid QuestionDetailRequest questionRequest) {
        return ApiResult.<QuestionResponse>builder()
                .result(questionService.createQuestion(quizId, questionRequest))
                .build();
    }

    @Operation(summary = "Update a question", description = "Updates the details of an existing question.", tags = {"Question"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Question not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PutMapping("/questions/{questionId}")
    public ApiResult<Void> updateQuestion(@PathVariable String questionId, @RequestBody @Valid QuestionDetailRequest questionRequest) {
        questionService.updateQuestion(questionId, questionRequest);
        return ApiResult.<Void>builder()
                .message("Updated question successfully")
                .build();
    }

    @Operation(summary = "Delete a question", description = "Deletes a question by its unique identifier.", tags = {"Question"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Question not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @DeleteMapping("/questions/{questionId}")
    public ApiResult<Void> deleteQuestion(@PathVariable String questionId) {
        questionService.deleteQuestion(questionId);
        return ApiResult.<Void>builder()
                .message("Deleted question successfully")
                .build();
    }

    @Operation(summary = "Get question by ID", description = "Retrieves a question by its unique identifier.", tags = {"Question"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Question not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping("/questions/{questionId}")
    public ApiResult<QuestionResponse> getQuestionById(@PathVariable String questionId) {
        return ApiResult.<QuestionResponse>builder()
                .result(questionService.getQuestionById(questionId))
                .build();
    }

    @Operation(summary = "Start a quiz", description = "Marks the quiz as started and records the start time.", tags = {"Quiz"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz started successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Quiz not found or not allow at this time", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping("/{quizId}/start")
    public ApiResult<Void> startQuiz(@PathVariable String quizId) {
        quizService.startQuiz(quizId);
        return ApiResult.<Void>builder()
                .message("Quiz started successfully")
                .build();
    }

    @Operation(summary = "Submit a quiz", description = "Submits the quiz answers and calculates the result.", tags = {"Quiz"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz submitted successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResultResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Quiz or User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping("/{quizId}/submit")
    public ApiResult<QuizResultResponse> submitQuiz(@PathVariable String quizId, @RequestBody @Valid QuizSubmissionRequest request) {
        return ApiResult.<QuizResultResponse>builder()
                .result(quizService.submitQuiz(quizId, request))
                .build();
    }

    @Operation(summary = "Get all quizzes", description = "Retrieves a list of all quizzes available in the system.", tags = {"Quiz"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping()
    public ApiResult<List<QuizResponse>> getAllQuestions() {
        return ApiResult.<List<QuizResponse>>builder()
                .result(quizService.getAllQuizzes())
                .build();
    }
}
