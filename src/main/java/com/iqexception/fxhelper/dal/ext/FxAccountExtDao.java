package com.iqexception.fxhelper.dal.ext;


import com.iqexception.fxhelper.dal.generator.tables.daos.FxAccountDao;
import com.iqexception.fxhelper.dal.generator.tables.records.FxAccountRecord;
import org.jooq.Configuration;
import org.jooq.UpdateConditionStep;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import static com.iqexception.fxhelper.dal.generator.tables.FxAccount.FX_ACCOUNT;

@Repository
public class FxAccountExtDao extends FxAccountDao {

    public FxAccountExtDao(Configuration configuration) {
        super(configuration);
    }

    public int incrBalanceByAcctId(Long accountId, BigDecimal delta) {

        UpdateConditionStep<FxAccountRecord> updateConditionStep = ctx().update(getTable())
                .set(FX_ACCOUNT.BALANCE, FX_ACCOUNT.BALANCE.plus(delta))
                .where(FX_ACCOUNT.ACCOUNT_ID.eq(accountId));

        if (delta.compareTo(BigDecimal.ZERO) >= 0) {
            return updateConditionStep
                    .execute();
        } else {
            return updateConditionStep
                    .and(FX_ACCOUNT.BALANCE.gt(delta.negate()))
                    .execute();
        }


    }


}
