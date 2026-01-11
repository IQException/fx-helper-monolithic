package com.iqexception.fxhelper.controller;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.user.UserLoginParam;
import com.iqexception.fxhelper.controller.dto.user.UserLoginResult;
import com.iqexception.fxhelper.service.UserService;
import com.iqexception.fxhelper.support.Constants;
import com.iqexception.fxhelper.util.ErrorCode;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping("/login")
    public Response<UserLoginResult> login(@RequestBody @Valid Request<UserLoginParam> request, HttpSession session) {

        Response<UserLoginResult> response = userService.login(request);
        if (String.valueOf(ErrorCode.SUCCESS).equals(response.getStatus().getErrorCode())) {
            session.setAttribute(Constants.SESSION_ATTR_USER_ID, response.getResult().getUserId());
            session.setAttribute(Constants.SESSION_ATTR_OPEN_ID, response.getResult().getOpenId());

        }
        return response;
    }

}
