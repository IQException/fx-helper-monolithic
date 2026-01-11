package com.iqexception.fxhelper.dal.ext;

import com.iqexception.fxhelper.dal.generator.tables.daos.FxUserDao;
import com.iqexception.fxhelper.service.builder.UserBuilder;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.iqexception.fxhelper.dal.generator.tables.FxUser.FX_USER;

@Repository
public class FxUserExtDao extends FxUserDao {
    public FxUserExtDao(Configuration configuration) {
        super(configuration);
    }

    public int updatePaySecret(Long userId, String secret) {

        return ctx().update(getTable())
                .set(FX_USER.PAY_SECRET, UserBuilder.encryptPaySecret(secret))
                .where(FX_USER.USER_ID.eq(userId))
                .execute();
    }
}
