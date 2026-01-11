package com.iqexception.fxhelper.controller;

import com.iqexception.fxhelper.controller.dto.BaseRequest;
import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.account.*;
import com.iqexception.fxhelper.service.AccountService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {


    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/user_query")
    public Response<AccountUserQueryResult> userQuery(@RequestBody @Valid BaseRequest request) {
        return accountService.userQuery(request);
    }

}
