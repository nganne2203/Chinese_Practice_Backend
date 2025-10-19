package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.LessonRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.LessonResponse;
import fptedu.nganmtt.ChinesePractice.service.LessonService;
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
@RequestMapping("/api/lessons")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LessonController {
    LessonService lessonService;

    @Operation(summary = "Get lesson by ID", description = "Retrieve lesson information by lesson ID", tags = {"Lesson"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LessonResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Lesson not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping("/{id}")
    public ApiResult<LessonResponse> getLessonById(@PathVariable String id) {
        return ApiResult.<LessonResponse>builder()
                .result(lessonService.getLessonById(id))
                .build();
    }

    @Operation(summary = "Create a new lesson", description = "Create a new lesson with the provided information", tags = {"Lesson"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LessonResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Unit not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping()
    public ApiResult<LessonResponse> create(@RequestBody @Valid LessonRequest request) {
        return ApiResult.<LessonResponse>builder()
                .result(lessonService.create(request))
                .build();
    }

    @Operation(summary = "Update an existing lesson", description = "Update an existing lesson by its ID", tags = {"Lesson"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "400", description = "Lesson not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Unit not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PutMapping("/{id}")
    public ApiResult<Void> update(@PathVariable String id, @RequestBody @Valid LessonRequest request) {
        lessonService.update(id, request);
        return ApiResult.<Void>builder()
                .message("Lesson updated successfully")
                .build();
    }

    @Operation(summary = "Delete a lesson", description = "Delete a lesson by its ID", tags = {"Lesson"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson deleted successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "400", description = "Lesson not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable String id) {
        lessonService.delete(id);
        return ApiResult.<Void>builder()
                .message("Lesson deleted successfully")
                .build();
    }

    @Operation(summary = "Get all lessons", description = "Retrieve a list of all lessons", tags = {"Lesson"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of lessons retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LessonResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping()
    public ApiResult<List<LessonResponse>> getAllLessons() {
        return ApiResult.<List<LessonResponse>>builder()
                .result(lessonService.getAllLessons())
                .build();
    }
}
