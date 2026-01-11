package com.iqexception.fxhelper.service.builder;

import com.iqexception.fxhelper.dal.generator.tables.pojos.FxAccount;

public class AccountBuilder {


    public static FxAccount buildNewAccount(Long userId) {

        FxAccount account = new FxAccount();
        account.setUserId(userId);
        return account;

    }


}
