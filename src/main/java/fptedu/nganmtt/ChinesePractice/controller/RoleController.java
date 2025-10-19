package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.RoleRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.RoleResponse;
import fptedu.nganmtt.ChinesePractice.service.RoleService;
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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @Operation(summary = "Create a new role", description = "Creates a new role in the system.", tags = {"Role"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Role is already exist", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    ApiResult<RoleResponse> create(@Valid @RequestBody RoleRequest roleRequest) {
        return ApiResult.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .build();
    }

    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles in the system.", tags = {"Role"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    ApiResult<List<RoleResponse>> getAll() {
        return ApiResult.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @Operation(summary = "Delete a role", description = "Deletes a role from the system by its name.", tags = {"Role"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{role}")
    ApiResult<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResult.<Void>builder()
                .message("Role deleted successfully")
                .build();
    }
}
