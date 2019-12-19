package io.bar.beerhub.services.models;

public class OrderServiceModel {
    private String id;
    private String customerUsername;
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

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
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
