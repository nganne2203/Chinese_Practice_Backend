package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.VocabularyRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.VocabularyDetailResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.VocabularyResponse;
import fptedu.nganmtt.ChinesePractice.service.VocabularyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get vocabularies by unit and HSK level", description = "Retrieve a list of vocabularies filtered by unit ID and HSK level ID. Both parameters are optional.", tags = {"Vocabulary"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vocabularies", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VocabularyResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ApiResult<List<VocabularyResponse>> getAllVocabulariesByUnitAndHsk(
            @RequestParam(name = "unit", required = false) String unitId,
            @RequestParam(name = "level", required = false) String levelId) {

        return ApiResult.<List<VocabularyResponse>>builder()
                .result(vocabularyService.getVocabByUnitAndHsk(unitId, levelId))
                .build();
    }

    @Operation(description = "Get vocabulary by ID", summary = "Get vocabulary by ID", tags = {"Vocabulary"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vocabulary", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VocabularyResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Vocabulary not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{id}")
    public ApiResult<VocabularyResponse> getVocabularyById(@PathVariable String id) {
        return ApiResult.<VocabularyResponse>builder()
                .result(vocabularyService.getVocabularyById(id))
                .build();
    }

    @Operation(summary = "Create a new vocabulary", description = "Create a new vocabulary entry in the system.", tags = {"Vocabulary"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created vocabulary", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VocabularyResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Lesson not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ApiResult<VocabularyResponse> createVocabulary(@RequestBody @Valid VocabularyRequest request) {
        return ApiResult.<VocabularyResponse>builder()
                .result(vocabularyService.createVocabulary(request))
                .build();
    }

    @Operation(summary = "Update an existing vocabulary", description = "Update the details of an existing vocabulary entry.", tags = {"Vocabulary"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated vocabulary", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vocabulary not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Lesson not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public ApiResult<Void> updateVocabulary(@PathVariable String id, @RequestBody @Valid VocabularyRequest request) {
        vocabularyService.updateVocabulary(id, request);
        return ApiResult.<Void>builder()
                .message("Vocabulary updated successfully")
                .build();
    }

    @Operation(summary = "Delete a vocabulary", description = "Delete a vocabulary entry by its ID.", tags = {"Vocabulary"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted vocabulary", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vocabulary not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteVocabulary(@PathVariable String id) {
        vocabularyService.deleteVocabulary(id);
        return ApiResult.<Void>builder()
                .message("Vocabulary deleted successfully")
                .build();
    }

    @Operation(summary = "Get all vocabularies", description = "Retrieve a list of all vocabularies in the system.", tags = {"Vocabulary"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vocabularies", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VocabularyResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/all")
    public ApiResult<List<VocabularyResponse>> getAllVocabularies() {
        return ApiResult.<List<VocabularyResponse>>builder()
                .result(vocabularyService.getAllVocabularies())
                .build();
    }

    @Operation(summary = "Search vocabularies by keyword", description = "Search for vocabularies that match the given keyword.", tags = {"Vocabulary"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved search results", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VocabularyDetailResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/search")
    public ApiResult<List<VocabularyDetailResponse>> searchVocabulary(@RequestParam(name = "keyword", required = false) String keyword) {
        return ApiResult.<List<VocabularyDetailResponse>>builder()
                .result(vocabularyService.searchVocabularyDetailByKeyword(keyword))
                .build();
    }

}
