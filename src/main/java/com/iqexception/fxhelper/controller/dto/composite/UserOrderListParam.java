package com.iqexception.fxhelper.controller.dto.composite;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class UserOrderListParam {

    private Integer status;
    private LocalDateTime from;
    private LocalDateTime to;
    @PositiveOrZero
    private Integer offset;
    @Max(20)
    @Positive
    private Integer limit;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
