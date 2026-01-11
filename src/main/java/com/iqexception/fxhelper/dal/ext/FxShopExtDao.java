package com.iqexception.fxhelper.dal.ext;

import com.iqexception.fxhelper.dal.generator.tables.daos.FxShopDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxShop;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.iqexception.fxhelper.dal.generator.tables.FxShop.*;

@Repository
public class FxShopExtDao extends FxShopDao {


    public FxShopExtDao(Configuration configuration) {
        super(configuration);
    }

    public FxShop fetchOne(String name, String address) {

        return ctx().selectFrom(getTable())
                .where(FX_SHOP.SHOP_NAME.eq(name))
                .and(FX_SHOP.ADDRESS.eq(address))
                .fetchOne(mapper());

    }

    public int updateStatus(Long shopId, Integer switchValue) {

        return ctx().update(getTable())
                .set(FX_SHOP.STATUS, switchValue)
                .where(FX_SHOP.SHOP_ID.eq(shopId))
                .execute();
    }
}
