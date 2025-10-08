package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.RoleRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.RoleResponse;
import fptedu.nganmtt.ChinesePractice.service.RoleService;
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

    @PostMapping
    ApiResult<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        return ApiResult.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .build();
    }

    @GetMapping
    ApiResult<List<RoleResponse>> getAll() {
        return ApiResult.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResult<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResult.<Void>builder().build();
    }
}
