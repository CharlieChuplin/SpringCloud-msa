package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final Greeting greeting;
    private final UserService userService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    /** yml 파일의 데이터 값 가져오는 방법 */
    // Environment 사용
    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }
    // @Value 사용 : vo.Greeting에 저장해서 사용
    @GetMapping("/welcomeV2")
    public String welcomeV2() {
        return greeting.getMessage();
    }
    
    /** 회원가입 */
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser) {
        // ModelMapper 생성 및 설정
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // RequestUser -> UserDto
        UserDto userDto = mapper.map(requestUser, UserDto.class);
        userService.createUser(userDto);

        // UserDto -> ResponseUser
        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
