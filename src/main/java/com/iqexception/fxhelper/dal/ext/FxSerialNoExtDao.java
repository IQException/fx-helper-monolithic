package com.iqexception.fxhelper.dal.ext;

import com.iqexception.fxhelper.dal.generator.tables.daos.FxSerialNoDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxSerialNo;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class FxSerialNoExtDao extends FxSerialNoDao {

    private static final com.iqexception.fxhelper.dal.generator.tables.FxSerialNo TBL
            = com.iqexception.fxhelper.dal.generator.tables.FxSerialNo.FX_SERIAL_NO;


    public FxSerialNoExtDao(Configuration configuration) {
        super(configuration);
    }

    public FxSerialNo fetchOne(Long shopId,String serialNo){
      return   ctx().selectFrom(getTable())
                .where(TBL.SHOP_ID.eq(shopId))
                .and(TBL.SERIAL_NO.eq(serialNo))
                .fetchOne(mapper());
    }
}
