package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.UnitRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UnitUpdateRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.UnitResponse;
import fptedu.nganmtt.ChinesePractice.service.UnitService;
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
@RequestMapping("/api/units")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UnitController {
    UnitService unitService;

    @Operation(summary = "Get all units, optionally filtered by HSK level", description = "Retrieve a list of all units. You can filter the units by providing an HSK level as a query parameter.", tags = {"Unit"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of units", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UnitResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ApiResult<List<UnitResponse>> getAllUnitsByHskLevel(
            @RequestParam(name = "level", required = false) String level) {

        var units = unitService.getAllUnitsByHskLevel(level);
        return ApiResult.<List<UnitResponse>>builder()
                .result(units)
                .build();
    }

    @Operation(summary = "Get unit by ID", description = "Retrieve a specific unit by its unique ID.", tags = {"Unit"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the unit", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UnitResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Unit not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{id}")
    public ApiResult<UnitResponse> getUnitById(@PathVariable String id) {
        var unit = unitService.getUnitById(java.util.UUID.fromString(id));
        return ApiResult.<UnitResponse>builder()
                .result(unit)
                .build();
    }

    @Operation(summary = "Create a new unit", description = "Create a new unit with the provided details.", tags = {"Unit"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the unit", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UnitResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Hsk Level not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping()
    public ApiResult<UnitResponse> createUnit(@RequestBody @Valid UnitRequest request) {
        var result = unitService.createUnit(request);
        return ApiResult.<UnitResponse>builder()
                .result(result)
                .build();
    }

    @Operation(summary = "Update an existing unit", description = "Update the details of an existing unit by its ID.", tags = {"Unit"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the unit", content = @Content),
            @ApiResponse(responseCode = "400", description = "Hsk Level not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Unit not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public ApiResult<Void> updateUnit(@PathVariable String id, @RequestBody @Valid UnitUpdateRequest request) {
        unitService.updateUnit(java.util.UUID.fromString(id), request);
        return ApiResult.<Void>builder()
                .message("Unit updated successfully")
                .build();
    }

    @Operation(summary = "Delete a unit", description = "Delete an existing unit by its ID.", tags = {"Unit"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the unit", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "404", description = "Unit not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteUnit(@PathVariable String id) {
        unitService.deleteUnit(java.util.UUID.fromString(id));
        return ApiResult.<Void>builder()
                .message("Unit deleted successfully")
                .build();
    }
}
