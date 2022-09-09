package com.zema.user;

import com.zema.commons.exceptions.AppException;
import com.zema.commons.exceptions.ErrorMessage;
import com.zema.user.dto.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Value("${user.config.pageSize:10}")
    private String pageSize;

    public Page<User> getUsers(int page) {
        int limit = Integer.parseInt(pageSize);
        if (page <= 0) {
            throw new AppException(ErrorMessage.PAGE_NUMBER_MUST_BE_GREATER_THAN_ZERO.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        if (limit <= 0) {
            throw new AppException(ErrorMessage.PAGE_SIZE_MUST_BE_GREATER_THAN_ZERO.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<User> users = userRepository.findAll(pageRequest);
        log.info("users: {}", users.getContent());
        return users;
    }

    public User register(User user) {
        //store new user
        return userRepository.save(user);
    }

    public User update(Long id, UserUpdateDto userUpdateDto) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppException(ErrorMessage.NOT_FOUND_RECODE.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
        User newUser = user.get();
        newUser.setUsername(userUpdateDto.getUsername());
        newUser.setEmail(userUpdateDto.getEmail());
        return userRepository.save(newUser);
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppException(ErrorMessage.NOT_FOUND_RECODE.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppException(ErrorMessage.NOT_FOUND_RECODE.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
        userRepository.delete(user.get());
    }


}
