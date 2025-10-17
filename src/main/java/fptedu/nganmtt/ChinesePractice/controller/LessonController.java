package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.LessonRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.LessonResponse;
import fptedu.nganmtt.ChinesePractice.service.LessonService;
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

    @GetMapping("/{id}")
    public ApiResult<LessonResponse> getLessonById(@PathVariable String id) {
        return ApiResult.<LessonResponse>builder()
                .result(lessonService.getLessonById(id))
                .build();
    }

    @PostMapping()
    public ApiResult<LessonResponse> create(@RequestBody @Valid LessonRequest request) {
        return ApiResult.<LessonResponse>builder()
                .result(lessonService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResult<Void> update(@PathVariable String id, @RequestBody @Valid LessonRequest request) {
        lessonService.update(id, request);
        return ApiResult.<Void>builder()
                .message("Lesson updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable String id) {
        lessonService.delete(id);
        return ApiResult.<Void>builder()
                .message("Lesson deleted successfully")
                .build();
    }

    @GetMapping()
    public ApiResult<List<LessonResponse>> getAllLessons() {
        return ApiResult.<List<LessonResponse>>builder()
                .result(lessonService.getAllLessons())
                .build();
    }
}
