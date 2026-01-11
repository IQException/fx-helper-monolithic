package com.iqexception.fxhelper.service;


import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.user.*;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxUser;

public interface UserService {

    Response<UserLoginResult> login(Request<UserLoginParam> request);

    FxUser getLoginUser(Long userId);

    boolean verifyPaySecret(Long userId, String paySecret);


}
