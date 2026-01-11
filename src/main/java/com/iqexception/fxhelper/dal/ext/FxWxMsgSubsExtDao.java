package com.iqexception.fxhelper.dal.ext;

import com.iqexception.fxhelper.dal.generator.tables.daos.FxWxMsgSubsDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxWxMsgSubs;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.iqexception.fxhelper.dal.generator.tables.FxWxMsgSubs.*;

@Repository
public class FxWxMsgSubsExtDao extends FxWxMsgSubsDao {

    public FxWxMsgSubsExtDao(Configuration configuration) {
        super(configuration);
    }

    private static final com.iqexception.fxhelper.dal.generator.tables.FxWxMsgSubs TBL = FX_WX_MSG_SUBS;

    public int decrCount(Long id) {
        return ctx().update(getTable())
                .set(TBL.COUNT, TBL.COUNT.minus(1))
                .where(TBL.ID.eq(id))
                .and(TBL.COUNT.gt(0))
                .execute();
    }

    public FxWxMsgSubs fetchByKey(String openId, String depositMessageId) {

        return ctx().selectFrom(getTable())
                .where(TBL.OPEN_ID.eq(openId))
                .and(TBL.TEMPLATE_ID.eq(depositMessageId))
                .fetchOne(mapper());

    }

    public int incrCount(Long id) {
        return ctx().update(getTable())
                .set(TBL.COUNT, TBL.COUNT.plus(1))
                .where(TBL.ID.eq(id))
                .execute();
    }
}
