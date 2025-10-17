package fptedu.nganmtt.ChinesePractice.controller;
import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.HskLevelRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.HskLevelResponse;
import fptedu.nganmtt.ChinesePractice.service.HskLevelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hsk-levels")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HskLevelController {
    HskLevelService hskLevelService;

    @GetMapping()
    public ApiResult<List<HskLevelResponse>> getAllHskLevels() {
        return ApiResult.<List<HskLevelResponse>>builder()
                .result(hskLevelService.getAll())
                .build();
    }

    @PostMapping()
    public ApiResult<HskLevelResponse> createHskLevel(@RequestBody HskLevelRequest request) {
        return ApiResult.<HskLevelResponse>builder()
                .result(hskLevelService.create(request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteHskLevel(@PathVariable("id") String id) {
        hskLevelService.delete(java.util.UUID.fromString(id));
        return ApiResult.<Void>builder()
                .message("Successfully deleted hsk level")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResult<HskLevelResponse> getHskLevelById(@PathVariable("id") String id) {
        return ApiResult.<HskLevelResponse>builder()
                .result(hskLevelService.getById(java.util.UUID.fromString(id)))
                .build();
    }
}
