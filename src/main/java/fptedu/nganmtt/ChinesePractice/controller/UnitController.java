package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.UnitRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UnitUpdateRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.UnitResponse;
import fptedu.nganmtt.ChinesePractice.service.UnitService;
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

    @GetMapping
    public ApiResult<List<UnitResponse>> getAllUnitsByHskLevel(
            @RequestParam(name = "level", required = false) String level) {

        var units = unitService.getAllUnitsByHskLevel(level);
        return ApiResult.<List<UnitResponse>>builder()
                .result(units)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResult<UnitResponse> getUnitById(@PathVariable String id) {
        var unit = unitService.getUnitById(java.util.UUID.fromString(id));
        return ApiResult.<UnitResponse>builder()
                .result(unit)
                .build();
    }

    @PostMapping()
    public ApiResult<UnitResponse> createUnit(@RequestBody @Valid UnitRequest request) {
        var result = unitService.createUnit(request);
        return ApiResult.<UnitResponse>builder()
                .result(result)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResult<Void> updateUnit(@PathVariable String id, @RequestBody @Valid UnitUpdateRequest request) {
        unitService.updateUnit(java.util.UUID.fromString(id), request);
        return ApiResult.<Void>builder()
                .message("Unit updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteUnit(@PathVariable String id) {
        unitService.deleteUnit(java.util.UUID.fromString(id));
        return ApiResult.<Void>builder()
                .message("Unit deleted successfully")
                .build();
    }
}
