package io.bar.beerhub.data.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private User customer;
    private Waitress waitress;
    private List<Beer> beers;
    private boolean closed;

    public Order() {
    }

    @ManyToOne
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @ManyToOne
    public Waitress getWaitress() {
        return waitress;
    }

    public void setWaitress(Waitress waitress) {
        this.waitress = waitress;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "orders_beers",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "beer_id", referencedColumnName = "id"))
    public List<Beer> getBeers() {
        return beers;
    }

    public void setBeers(List<Beer> beers) {
        this.beers = beers;
    }

    @Column
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
