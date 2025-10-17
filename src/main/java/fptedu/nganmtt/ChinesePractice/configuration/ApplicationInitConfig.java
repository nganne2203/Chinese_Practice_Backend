package fptedu.nganmtt.ChinesePractice.configuration;

import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.model.Permission;
import fptedu.nganmtt.ChinesePractice.model.Role;
import fptedu.nganmtt.ChinesePractice.model.User;
import fptedu.nganmtt.ChinesePractice.repository.PermissionRepository;
import fptedu.nganmtt.ChinesePractice.repository.RoleRepository;
import fptedu.nganmtt.ChinesePractice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (permissionRepository.findAll().isEmpty()) {
                Permission permission = Permission.builder()
                        .name("CREATE_ROLE")
                        .description("Create new role")
                        .build();
                permissionRepository.save(permission);
                log.warn("Permissions have been initialized");
            }

            if (roleRepository.findAll().isEmpty() && !permissionRepository.findAll().isEmpty()) {
                Role role = Role.builder()
                        .name("ADMIN")
                        .description("Admin_Role")
                        .permissions(new HashSet<>(permissionRepository.findAll()))
                        .build();
                roleRepository.save(role);
                log.warn("Roles have been initialized");
            }

            if(userRepository.findByUserName("admin").isEmpty()){
                User user = User.builder()
                        .userName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Set.of(
                                roleRepository.findById("ADMIN")
                                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND))))
                        .build();
                userRepository.save(user);
                log.warn("Admin has been created");
            }
        };
    }
}
