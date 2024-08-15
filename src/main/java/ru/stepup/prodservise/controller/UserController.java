package ru.stepup.prodservise.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.stepup.prodservise.dto.UserDto;
import ru.stepup.prodservise.entity.User;
import ru.stepup.prodservise.exceptions.NotFoundException;
import ru.stepup.prodservise.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/adduser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long addUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.userName());
        return userService.save(user);
    }

    @GetMapping("/user")
    public UserDto getUserById(@RequestParam Long id) throws SQLException {
        User user = userService.getUser(id).orElseThrow(() -> new NotFoundException("Пользователь  с id = " + id + " не найден", HttpStatus.NOT_FOUND));
        return new UserDto(user.getId(), user.getUserName());
    }

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getUsers().stream().map(x -> new UserDto(x.getId(), x.getUserName())).collect(Collectors.toList());
    }


}
