package fptedu.nganmtt.ChinesePractice.mapper;

import fptedu.nganmtt.ChinesePractice.dto.request.RoleRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.RoleResponse;
import fptedu.nganmtt.ChinesePractice.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
