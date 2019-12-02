package io.bar.beerhub.data.repositories;

import io.bar.beerhub.data.models.Order;
import io.bar.beerhub.data.models.User;
import io.bar.beerhub.data.models.Waitress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> getAllByCustomer(User customer);

    List<Waitress> getAllByWaitress(Waitress waitress);
}
