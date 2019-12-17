package io.bar.beerhub.data.repositories;

import io.bar.beerhub.data.models.Order;
import io.bar.beerhub.data.models.User;
import io.bar.beerhub.data.models.Waitress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> getAllByCustomer(User customer);

    List<Waitress> getAllByWaitress(Waitress waitress);

    List<Order> getByCustomer(User customer);

    @Query("select o from orders o where o.closed = FALSE and o.customer =:customer")
    List<Order> getAllOpenOrdersByCustomer(@Param("customer") User customer);
}
