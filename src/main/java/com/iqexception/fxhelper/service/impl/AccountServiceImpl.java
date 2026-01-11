package com.iqexception.fxhelper.service.impl;

import com.iqexception.fxhelper.controller.dto.BaseRequest;
import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.account.*;
import com.iqexception.fxhelper.dal.ext.FxAccountExtDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxAccount;
import com.iqexception.fxhelper.service.AccountService;
import com.iqexception.fxhelper.service.builder.AccountBuilder;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.support.TLVarManager;
import com.iqexception.fxhelper.util.BizException;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.wx.WxApi;
import org.jooq.DSLContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseService implements AccountService {

    private final FxAccountExtDao accountExtDao;


    public AccountServiceImpl(MessageSource messageSource,
                              WxApi wxApi,
                              DSLContext jooqClient,
                              JsonMapper jsonMapper,
                              FxAccountExtDao accountExtDao) {
        super(messageSource, wxApi, jooqClient, jsonMapper);
        this.accountExtDao = accountExtDao;

    }

    @Override
    public void createAccount(Long userId) {
        accountExtDao.insert(AccountBuilder.buildNewAccount(userId));
    }

    @Override
    public Response<AccountTransferResult> transfer(Request<AccountTransferParam> request) {

        Response<AccountTransferResult> response = new Response<>();

        try {
            jooqClient.transaction(conf -> {

                FxAccount account = accountExtDao.fetchOneByUserId(request.getParam().getFromAccountId());
                //转出账户不存在
                if (account == null) {
                    throw new BizException(ErrorCode.ACCOUNT_FROM_NOT_EXIST);
                }

                int decr = accountExtDao.incrBalanceByAcctId(
                        request.getParam().getFromAccountId(),
                        request.getParam().getAmount().negate());

                //转出账户余额不足
                if (decr == 0) {
                    throw new BizException(ErrorCode.ACCOUNT_BALANCE_INSUFFICIENT);
                }

                int incr = accountExtDao.incrBalanceByAcctId(
                        request.getParam().getToAccountId(),
                        request.getParam().getAmount());
                //转入账户不存在
                if (incr == 0) {
                    throw new BizException(ErrorCode.ACCOUNT_TO_NOT_EXIST);
                }
            });
        } catch (BizException e) {
            response.setStatus(status(e.getErrorCode()));
        }

        return response;
    }

    @Override
    public Response<AccountPartnerQueryResult> partnerQuery(Request<AccountPartnerQueryParam> request) {
        FxAccount account = accountExtDao.fetchOneByAccountId(request.getParam().getAccountId());
        if (account == null)
            return new Response<>(status(ErrorCode.PARAM_ERROR));

        return new Response<>(new AccountPartnerQueryResult(account));
    }

    @Override
    public Response<AccountUserQueryResult> userQuery(BaseRequest request) {
        FxAccount account = accountExtDao.fetchOneByUserId(TLVarManager.getCurrentUser().getUserId());
        if (account == null)
            return new Response<>(status(ErrorCode.PARAM_ERROR));

        return new Response<>(new AccountUserQueryResult(account));
    }

}
