package com.zema.user;

import com.zema.commons.BasePath;
import com.zema.commons.reponses.HttpResponse;
import com.zema.commons.reponses.HttpResponseWithPagination;
import com.zema.user.dto.UserCreateDto;
import com.zema.user.dto.UserUpdateDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(produces={MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping(BasePath.USER_CONTROLLER_PATH)
        public ResponseEntity<HttpResponseWithPagination<List<UserVM>>> getUsers(@RequestParam(defaultValue = "1") String page) {
        int currentPage = Integer.parseInt(page);
        Page<User> usersPage = userService.getUsers(currentPage);
        HttpResponseWithPagination<List<UserVM>> httpResponseWithPagination = new HttpResponseWithPagination<>();
        List<UserVM> listOfUsers = new ArrayList<>();
        usersPage.getContent().forEach(userEntity -> {
            UserVM userResponse = modelMapper.map(userEntity, UserVM.class);
            listOfUsers.add(userResponse);
            log.info("userResponse: {}", userResponse.getEmail());
        });

        httpResponseWithPagination.setData(listOfUsers);
        httpResponseWithPagination.setCurrentPage(usersPage.getNumber());
        httpResponseWithPagination.setTotalPages(usersPage.getTotalPages());
        httpResponseWithPagination.setTotalElements(usersPage.getTotalElements());

        return new ResponseEntity<>(httpResponseWithPagination, HttpStatus.OK);
    }
    @PostMapping(BasePath.USER_CONTROLLER_PATH)
    public ResponseEntity<HttpResponse<UserVM>> create(@Valid @RequestBody UserCreateDto userCreateDto) {
        User user= modelMapper.map(userCreateDto,User.class);
        User newUser = userService.register(user);

        HttpResponse<UserVM> httpResponse = new HttpResponse<>();
        UserVM userResponse = modelMapper.map(newUser, UserVM.class);
        httpResponse.setData(userResponse);

        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @PatchMapping(path = BasePath.USER_CONTROLLER_PATH+"/{id}")
    public ResponseEntity<HttpResponse<UserVM>> update(@PathVariable("id") String id,@Valid @RequestBody UserUpdateDto userUpdateDto) {
        Long userId = Long.parseLong(id);
        User user = userService.update(userId,userUpdateDto);
        HttpResponse<UserVM> httpResponse = new HttpResponse<>();
        UserVM userResponse = modelMapper.map(user, UserVM.class);
        httpResponse.setData(userResponse);
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }
    @GetMapping(path = BasePath.USER_CONTROLLER_PATH+"/{id}")
    public ResponseEntity<HttpResponse<UserVM>> getUser(@PathVariable("id") String id) {
        Long userId = Long.parseLong(id);
        User user = userService.getUser(userId);
        HttpResponse<UserVM> httpResponse = new HttpResponse<>();
        UserVM userResponse = modelMapper.map(user, UserVM.class);
        httpResponse.setData(userResponse);
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }
    @DeleteMapping(path = BasePath.USER_CONTROLLER_PATH+"/{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable("id") String id) {
        Long userId = Long.parseLong(id);
        userService.delete(userId);
        HttpResponse<UserVM> httpResponse = new HttpResponse<>();
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

   @GetMapping(path = "/api/v1/internal/users/{id}")
    public Optional<User> getUserInternally(@PathVariable("id") String id) {
        Long userId = Long.parseLong(id);
        return userService.getUserInternally(userId);
    }
    @GetMapping(path = "/api/v1/internal/users/username/{email}")
    public Optional<User> getUserInternallyByEmail(@PathVariable("email") String email) {
        return userService.getUserInternallyByEmail(email);
    }

}
