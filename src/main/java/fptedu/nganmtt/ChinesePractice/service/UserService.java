package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.UserCreationRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UserUpdateRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.UserResponse;
import fptedu.nganmtt.ChinesePractice.enums.Role;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.UserMapper;
import fptedu.nganmtt.ChinesePractice.model.User;
import fptedu.nganmtt.ChinesePractice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest user) {
        if(userRepository.existsByUserName(user.getUserName()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User newUser = userMapper.toUser(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.LEARNER.name());
        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUserById(UUID id) {
        return userMapper.toUserResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }


    public UserResponse updateUser(UUID id, UserUpdateRequest user) {
        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(updateUser, user);

        return userMapper.toUserResponse(userRepository.save(updateUser));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
