package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

import com.example.demo.src.user.model.*;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // http://127.0.0.1:9000/users
public class UserController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired // 의존성 주입을 위한 어노테이션. 객체 생성을 자동으로 해준다.
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // 회원가입 메서드
    @ResponseBody
    @PostMapping("") // GET http://vici-minn.shop:9000/users
    public BaseResponse<PostUserRes> login(@RequestBody PostUserReq postUserReq) {
        // validation : 이메일을 입력했는지?
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        // validation : 비밀번호를 입력했는지?
        if (postUserReq.getPwd() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PWD);
        }
        // validation : 닉네임을 입력했는지?
        if (postUserReq.getNickname() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
        }
        // validation : 휴대폰 번호를 입력했는지?
        if (postUserReq.getPwd()== null) {
            return new BaseResponse<>(POST_USERS_EMPTY_TELNUM);
        }
        // validation : 이메일 형식이 유효한가?
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        try{
            PostUserRes postUserRes = userService.signUp(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 로그인 메서드
    @ResponseBody
    @PostMapping("/login") // GET http://vici-minn.shop:9000/users/login
    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq) {
        // validation : 이메일을 입력했는지?
        if (postLoginReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        // validation : 비밀번호를 입력했는지?
        if (postLoginReq.getPwd() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PWD);
        }
        // validation : 이메일 형식이 유효한가?
        if (!isRegexEmail(postLoginReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostLoginRes postLoginRes = userService.login(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 회원 탈퇴 메서드
    @ResponseBody
    @PatchMapping("/{userId}/status") // GET http://vici-minn.shop:9000/users/:userId/status
    public BaseResponse<String> deleteUser(@PathVariable("userId") int userId) {
        try{
            String message = userService.deleteUser(userId);

            return new BaseResponse<>(message);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 회원 정보 수정
    @ResponseBody
    @PatchMapping("/{userId}") // GET http://vici-minn.shop:9000/users/:userId
    public BaseResponse<PatchUserRes> updateUser(@PathVariable("userId") int userId, @RequestBody PatchUserReq patchUserReq) {
        try{
            // validation : 이메일 형식이 유효한지?
            if(!isRegexEmail(patchUserReq.getEmail()))
                throw new BaseException(POST_USERS_INVALID_EMAIL);

            PatchUserRes patchUserRes = userService.updateUser(userId, patchUserReq);
            return new BaseResponse(patchUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
