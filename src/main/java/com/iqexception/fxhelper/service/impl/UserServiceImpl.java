package com.iqexception.fxhelper.service.impl;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.user.UserLoginParam;
import com.iqexception.fxhelper.controller.dto.user.UserLoginResult;
import com.iqexception.fxhelper.dal.ext.FxUserExtDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxUser;
import com.iqexception.fxhelper.service.AccountService;
import com.iqexception.fxhelper.service.UserService;
import com.iqexception.fxhelper.service.builder.UserBuilder;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.util.BizException;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.wx.WxApi;
import com.iqexception.fxhelper.wx.model.Code2SessionResponse;
import org.apache.commons.lang.StringUtils;
import org.jooq.DSLContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    private final FxUserExtDao userExtDao;

    private final AccountService accountService;


    public UserServiceImpl(MessageSource messageSource, WxApi wxApi, DSLContext dslContext, JsonMapper jsonMapper, FxUserExtDao userExtDao, AccountService accountService) {
        super(messageSource, wxApi, dslContext, jsonMapper);
        this.wxApi = wxApi;
        this.jooqClient = dslContext;
        this.userExtDao = userExtDao;
        this.accountService = accountService;
    }


    @Override
    public Response<UserLoginResult> login(Request<UserLoginParam> request) {
        // wx.code2session
        // if openId is exist, update userinfo
        // else create user
        //ofboA7XAye7bHYZDxDSrN47hjJJY

        Code2SessionResponse resp = wxApi.code2Session(request.getParam().getCode());

        String openId = resp.getOpenId();
        if (StringUtils.isBlank(openId)) {
            LOG.error("code2Session error: {}", jsonMapper.serialize(resp));
            return new Response<>(status(ErrorCode.SYSTEM_ERROR));
        }

        FxUser user = userExtDao.fetchOneByOpenId(openId);
        // FIXME 已存在的用户更新信息
        if (user != null) {
//            userExtDao.update(UserBuilder.buildUserByFakeData(user,resp.getSessionKey()));
            user.setSessionKey(resp.getSessionKey());
            userExtDao.update(user);
            return new Response<>(new UserLoginResult(user.getUserId(), openId));
        } else {
            //创建新用户和用户账户
            //单机放在一个事务里，分布式用事务性消息
            FxUser fakeUser = UserBuilder.buildUserByFakeData(openId);
            userExtDao.insert(fakeUser);
            accountService.createAccount(fakeUser.getUserId());

            UserLoginResult result = new UserLoginResult();

            result.setUserId(fakeUser.getUserId());
            result.setOpenId(openId);
            result.setNewUser(true);

            return new Response<>(result);
        }

    }

    @Override
    public FxUser getLoginUser(Long userId) {

        FxUser user = userExtDao.fetchOneByUserId(userId);

        if (user == null) throw new BizException(ErrorCode.USER_NOT_EXIST);

        return user;
    }

    @Override
    public boolean verifyPaySecret(Long userId, String paySecret) {

        if (userId == null || paySecret == null) return false;

        FxUser user = userExtDao.fetchOneByUserId(userId);

        if (user == null) return false;

        return user.getPaySecret().equals(UserBuilder.encryptPaySecret(paySecret));
    }


}
