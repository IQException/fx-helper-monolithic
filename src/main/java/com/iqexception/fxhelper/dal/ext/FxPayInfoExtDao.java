package com.iqexception.fxhelper.dal.ext;

import com.iqexception.fxhelper.dal.generator.tables.daos.FxPayInfoDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.iqexception.fxhelper.dal.generator.tables.FxPayInfo.FX_PAY_INFO;

@Repository
public class FxPayInfoExtDao extends FxPayInfoDao {

    public FxPayInfoExtDao(Configuration configuration) {
        super(configuration);
    }

    public com.iqexception.fxhelper.dal.generator.tables.pojos.FxPayInfo fetchOneByBizId(String value) {
        return fetchOne(FX_PAY_INFO.BIZ_ID, value);
    }
/*
    public int update(Long id, String fromTrxId, String status) {
        return ctx().update(getTable())
                .set(FX_PAY_INFO.FROM_TRX_ID, fromTrxId)
                .where(FX_PAY_INFO.ID.eq(id))
                .execute();

    }*/
}
