package io.bar.beerhub.services.models;

public class OrderServiceModel {
    private String id;
    private String customerName;
    private String waitressName;
    private int beersNum;
    private boolean closed;

    public OrderServiceModel() {
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
