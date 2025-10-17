package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.PermissionRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.PermissionResponse;
import fptedu.nganmtt.ChinesePractice.service.PermissionService;
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

    @PostMapping()
    ApiResult<PermissionResponse> create(@Valid @RequestBody PermissionRequest request){
        return ApiResult.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResult<List<PermissionResponse>> getAll(){
        return ApiResult.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResult<Void> delete(@PathVariable String permission){
        permissionService.delete(permission);
        return ApiResult.<Void>builder().build();
    }
}
