package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.ChangePasswordRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UserCreationRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UserUpdateRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UserUpdateRoleRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.UserResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.UserMapper;
import fptedu.nganmtt.ChinesePractice.model.User;
import fptedu.nganmtt.ChinesePractice.repository.RoleRepository;
import fptedu.nganmtt.ChinesePractice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest user) {
        if(userRepository.existsByUserName(user.getUserName()))
            throw new AppException(ErrorCode.USER_EXISTED);

        if (userRepository.existsByEmail(user.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        User newUser = userMapper.toUser(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        newUser.setRoles(Set.of(
                roleRepository.findById("USER")
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND))
        ));

        return userMapper.toUserResponse(userRepository.save(newUser));
    }

    // has role only for role
    // permission need to use hasAuthority
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    //only who logged in can get their info
    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse getUserById(UUID id) {
        return userMapper.toUserResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
        );
    }


    public void updateUser(UUID id, UserUpdateRequest user) {
        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(updateUser, user);

        userRepository.save(updateUser);
    }

    public void updateRoleUser(UUID id, UserUpdateRoleRequest request) {
        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        var roles = roleRepository.findAllById(request.getRoles());
        updateUser.setRoles(new HashSet<>(roles));
        userRepository.save(updateUser);
    }

    public void changePassword(UUID id, ChangePasswordRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean matches = passwordEncoder.matches(request.getCurrentPassword(), user.getPassword());
        if (!matches) {
            throw new AppException(ErrorCode.UNAUTHORIZED_EXCEPTION);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User byUserName = userRepository.findByUserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(byUserName);
    }
}
