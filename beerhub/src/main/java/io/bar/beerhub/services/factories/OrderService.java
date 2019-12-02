package io.bar.beerhub.services.factories;

import io.bar.beerhub.data.models.Order;

public interface OrderService {
    void orderBeer(String id, Long quantity, String customerId);

    void bookWaitress(String waitressId, String customerId);

    void finalizePayCheck(String customerId);

    Order getCustomerCurrentOrder(String username);
}
