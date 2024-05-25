package com.SEProject.SEP.user.service;


import com.SEProject.SEP.user.domain.User;
import com.SEProject.SEP.user.dto.UserDto;
import com.SEProject.SEP.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public void saveUser(UserDto userDto) {
        User user = User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();

        userRepository.save(user);
    }

    @Transactional
    public String login(String userId, String userPassword){
        User optionalSiteUser = userRepository.findByUserNameAndPassword(userId, userPassword)
                .orElseThrow();
        return optionalSiteUser.getUserName();
    }
}
