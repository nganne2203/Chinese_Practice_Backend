package fptedu.nganmtt.ChinesePractice.controller;
import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.HskLevelRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.HskLevelResponse;
import fptedu.nganmtt.ChinesePractice.service.HskLevelService;
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
@RequestMapping("/api/hsk-levels")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HskLevelController {
    HskLevelService hskLevelService;

    @Operation(summary = "Get all HSK levels", description = "Retrieve a list of all HSK levels", tags = {"HSK Level"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of HSK levels retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HskLevelResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping()
    public ApiResult<List<HskLevelResponse>> getAllHskLevels() {
        return ApiResult.<List<HskLevelResponse>>builder()
                .result(hskLevelService.getAll())
                .build();
    }

    @Operation(summary = "Create a new HSK level", description = "Create a new HSK level with the provided information", tags = {"HSK Level"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HSK level created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HskLevelResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping()
    public ApiResult<HskLevelResponse> createHskLevel(@RequestBody @Valid HskLevelRequest request) {
        return ApiResult.<HskLevelResponse>builder()
                .result(hskLevelService.create(request))
                .build();
    }

    @Operation(summary = "Delete an HSK level", description = "Delete an HSK level by its ID", tags = {"HSK Level"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HSK level deleted successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "400", description = "HSK level not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteHskLevel(@PathVariable("id") String id) {
        hskLevelService.delete(java.util.UUID.fromString(id));
        return ApiResult.<Void>builder()
                .message("Successfully deleted hsk level")
                .build();
    }

    @Operation(summary = "Get HSK level by ID", description = "Retrieve HSK level information by its ID", tags = {"HSK Level"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HSK level retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HskLevelResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "HSK level not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping("/{id}")
    public ApiResult<HskLevelResponse> getHskLevelById(@PathVariable("id") String id) {
        return ApiResult.<HskLevelResponse>builder()
                .result(hskLevelService.getById(java.util.UUID.fromString(id)))
                .build();
    }
}
