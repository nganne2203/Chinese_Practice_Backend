package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.RoleRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.RoleResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.RoleMapper;
import fptedu.nganmtt.ChinesePractice.repository.PermissionRepository;
import fptedu.nganmtt.ChinesePractice.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @PreAuthorize("hasAuthority('ROLE_MANAGE') or hasRole('ADMIN')")
    public RoleResponse create (RoleRequest request) {
        try {
            if (roleRepository.existsById(request.getName()))
                throw new AppException(ErrorCode.ROLE_ALREADY_EXISTS);

            var role = roleMapper.toRole(request);

            var permissions = permissionRepository.findAllById(request.getPermissions());

            role.setPermissions(new HashSet<>(permissions));

            role = roleRepository.save(role);

            return roleMapper.toRoleResponse(role);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE') or hasRole('ADMIN')")
    public List<RoleResponse> getAll() {
        try {
            return roleRepository.findAll().stream()
                    .map(roleMapper::toRoleResponse).toList();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGE') or hasRole('ADMIN')")
    public void delete(String role) {
        try {
            roleRepository.deleteById(role);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
