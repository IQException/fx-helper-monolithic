package com.iqexception.fxhelper.service;

import com.iqexception.fxhelper.controller.dto.BaseRequest;
import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.account.*;

public interface AccountService {

    void createAccount(Long userId);

    Response<AccountTransferResult> transfer(Request<AccountTransferParam> request);

    Response<AccountPartnerQueryResult> partnerQuery(Request<AccountPartnerQueryParam> request);

    Response<AccountUserQueryResult> userQuery(BaseRequest request);

}
