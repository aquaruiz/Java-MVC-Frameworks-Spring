package io.bar.beerhub.web.models;

import java.math.BigDecimal;

public class Billmodel {
    private String user;
    private BigDecimal bill;

    public Billmodel() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }
}
