package com.iqexception.fxhelper.dal.ext;

import com.iqexception.fxhelper.dal.generator.tables.daos.FxOrderDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxOrder;
import com.iqexception.fxhelper.dal.generator.tables.records.FxOrderRecord;
import org.jooq.Configuration;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.iqexception.fxhelper.dal.generator.tables.FxOrder.FX_ORDER;
import static org.jooq.impl.DSL.sum;

@Repository
public class FxOrderExtDao extends FxOrderDao {

    public FxOrderExtDao(Configuration configuration) {
        super(configuration);
    }

    public List<FxOrder> fetch(Long shopId,
                               Long userId,
                               @Nullable Integer status,
                               @Nullable LocalDateTime from,
                               @Nullable LocalDateTime to,
                               Integer offset,
                               Integer limit) {

        //FIXME
        SelectConditionStep<FxOrderRecord> conditionStep = ctx().selectFrom(getTable()).where();

        if (shopId != null) {
            conditionStep = conditionStep.and(FX_ORDER.SHOP_ID.eq(shopId));
        }
        if (status != null) {
            conditionStep = conditionStep.and(FX_ORDER.STATUS.eq(status));
        }
        if (userId != null) {
            conditionStep = conditionStep.and(FX_ORDER.USER_ID.eq(userId));
        }
        if (from != null) {
            conditionStep = conditionStep.and(FX_ORDER.CREATED_AT.gt(from));
        }
        if (to != null) {
            conditionStep = conditionStep.and(FX_ORDER.CREATED_AT.lessOrEqual(to));
        }

        return conditionStep
                .orderBy(FX_ORDER.STATUS.asc(), FX_ORDER.UPDATED_AT.desc())
                .offset(offset)
                .limit(limit)
                .fetch(mapper());
    }

    public FxOrder fetchOne(Long shopId, String serialNo) {
        return ctx().selectFrom(getTable())
                .where(FX_ORDER.SHOP_ID.eq(shopId))
                .and(FX_ORDER.SERIAL_NO.eq(serialNo))
                .fetchOne(mapper());
    }

    public int updateStatus(Long id, int status) {

        return ctx().update(getTable())
                .set(FX_ORDER.STATUS, status)
                .where(FX_ORDER.ID.eq(id))
                .execute();
    }

    public int count(Long shopId, int status) {
        return ctx().fetchCount(
                ctx().selectFrom(getTable())
                        .where(FX_ORDER.SHOP_ID.eq(shopId))
                        .and(FX_ORDER.STATUS.eq(status)));


    }

    public BigDecimal totalAmount(Long shopId, int status) {
        return ctx().select(sum(FX_ORDER.AMOUNT)).from(getTable())
                .where(FX_ORDER.SHOP_ID.eq(shopId))
                .and(FX_ORDER.STATUS.eq(status))
                .fetchOneInto(BigDecimal.class);

    }
}
