package io.bar.beerhub.web.models;

import io.bar.beerhub.data.models.Beer;
import io.bar.beerhub.data.models.User;
import io.bar.beerhub.data.models.Waitress;

import java.util.List;

public class OrderViewModel {
    private String id;
    private String customerName;
    private String waitressName;
    private int beersNum;
    private boolean closed;

    public OrderViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getWaitressName() {
        return waitressName;
    }

    public void setWaitressName(String waitressName) {
        this.waitressName = waitressName;
    }

    public int getBeersNum() {
        return beersNum;
    }

    public void setBeersNum(int beersNum) {
        this.beersNum = beersNum;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
