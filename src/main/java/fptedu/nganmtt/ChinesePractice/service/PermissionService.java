package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.PermissionRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.PermissionResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.PermissionMapper;
import fptedu.nganmtt.ChinesePractice.model.Permission;
import fptedu.nganmtt.ChinesePractice.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @PreAuthorize("hasAuthority('PERMISSION_MANAGE') or hasRole('ADMIN')")
    public PermissionResponse create(PermissionRequest request) {
        try {
            if (permissionRepository.existsById(request.getName())) {
                throw new AppException(ErrorCode.PERMISSION_ALREADY_EXISTS);
            }
            Permission permission = permissionMapper.toPermission(request);
            permission = permissionRepository.save(permission);
            return permissionMapper.toPermissionResponse(permission);
        } catch (Exception e){
            log.error("Error creating permission: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('PERMISSION_MANAGE') or hasRole('ADMIN')")
    public List<PermissionResponse> getAll() {
        try {
            var permissions = permissionRepository.findAll();
            return permissions.stream()
                    .map(permissionMapper::toPermissionResponse)
                    .toList();
        } catch (Exception e){
            log.error("Error fetching permissions: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('PERMISSION_MANAGE') or hasRole('ADMIN')")
    public void delete(String permission){
        try {
            permissionRepository.deleteById(permission);
        } catch (Exception e){
            log.error("Error deleting permission {}: {}", permission, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
