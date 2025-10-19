package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.PermissionRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.PermissionResponse;
import fptedu.nganmtt.ChinesePractice.service.PermissionService;
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
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @Operation(summary = "Create a new permission", description = "Creates a new permission with the provided details.", tags = {"Permission"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PermissionResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Permission is already exist", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping()
    ApiResult<PermissionResponse> create(@Valid @RequestBody PermissionRequest request){
        return ApiResult.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @Operation(summary = "Get all permissions", description = "Retrieves a list of all permissions.", tags = {"Permission"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of permissions retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PermissionResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    ApiResult<List<PermissionResponse>> getAll(){
        return ApiResult.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @Operation(summary = "Delete a permission", description = "Deletes the permission with the specified name.", tags = {"Permission"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission deleted successfully", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{permission}")
    ApiResult<Void> delete(@PathVariable String permission){
        permissionService.delete(permission);
        return ApiResult.<Void>builder().build();
    }
}
