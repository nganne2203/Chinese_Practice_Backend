package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.VocabularyRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.VocabularyDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.VocabularyResponse;
import fptedu.nganmtt.ChinesePractice.service.VocabularyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocabularies")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VocabularyController {
    VocabularyService vocabularyService;

    @GetMapping
    public ApiResult<List<VocabularyResponse>> getAllVocabulariesByUnitAndHsk(
            @RequestParam(name = "unit", required = false) String unitId,
            @RequestParam(name = "level", required = false) String levelId) {

        return ApiResult.<List<VocabularyResponse>>builder()
                .result(vocabularyService.getVocabByUnitAndHsk(unitId, levelId))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResult<VocabularyResponse> getVocabularyById(@PathVariable String id) {
        return ApiResult.<VocabularyResponse>builder()
                .result(vocabularyService.getVocabularyById(id))
                .build();
    }

    @PostMapping
    public ApiResult<VocabularyResponse> createVocabulary(@RequestBody VocabularyRequest request) {
        return ApiResult.<VocabularyResponse>builder()
                .result(vocabularyService.createVocabulary(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResult<Void> updateVocabulary(@PathVariable String id, @RequestBody VocabularyRequest request) {
        vocabularyService.updateVocabulary(id, request);
        return ApiResult.<Void>builder()
                .message("Vocabulary updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteVocabulary(@PathVariable String id) {
        vocabularyService.deleteVocabulary(id);
        return ApiResult.<Void>builder()
                .message("Vocabulary deleted successfully")
                .build();
    }

    @GetMapping("/all")
    public ApiResult<List<VocabularyResponse>> getAllVocabularies() {
        return ApiResult.<List<VocabularyResponse>>builder()
                .result(vocabularyService.getAllVocabularies())
                .build();
    }

    @GetMapping("/search")
    public ApiResult<List<VocabularyDetailResponse>> searchVocabulary(@RequestParam(name = "keyword", required = false) String keyword) {
        return ApiResult.<List<VocabularyDetailResponse>>builder()
                .result(vocabularyService.searchVocabularyDetailByKeyword(keyword))
                .build();
    }

}
