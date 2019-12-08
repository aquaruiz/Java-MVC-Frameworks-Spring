package io.bar.beerhub.services.models;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PaycheckServiceModel {
     private long ordersNum;
     private long beersNum;
     private BigDecimal bill;
     private BigDecimal tips;
     private String lastWaitressName;

    public PaycheckServiceModel() {
    }

    public long getOrdersNum() {
        return ordersNum;
    }

    public void setOrdersNum(long ordersNum) {
        this.ordersNum = ordersNum;
    }

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }

    public BigDecimal getTips() {
        return tips;
    }

    public void setTips(BigDecimal tips) {
        this.tips = tips;
    }

    public String getLastWaitressName() {
        return lastWaitressName;
    }

    public void setLastWaitressName(String lastWaitressName) {
        this.lastWaitressName = lastWaitressName;
    }

    public long getBeersNum() {
        return beersNum;
    }

    public void setBeersNum(long beersNum) {
        this.beersNum = beersNum;
    }
}
