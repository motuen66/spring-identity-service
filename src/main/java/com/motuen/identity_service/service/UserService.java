package com.motuen.identity_service.service;

import com.motuen.identity_service.dto.request.ErrorCode;
import com.motuen.identity_service.dto.request.UserCreationRequest;
import com.motuen.identity_service.dto.request.UserUpdateRequest;
import com.motuen.identity_service.dto.response.UserResponse;
import com.motuen.identity_service.entity.User;
import com.motuen.identity_service.enums.Role;
import com.motuen.identity_service.exception.AppException;
import com.motuen.identity_service.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.motuen.identity_service.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers(){
        List<UserResponse> list = new ArrayList<>();
        List<User> foundList = userRepository.findAll();
        for(User user : foundList){
            list.add(userMapper.toUserResponse(user));
        }
        return  list;
    }

    public UserResponse getUser(String id){
        return userMapper.toUserResponse( userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not exist."));
        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
