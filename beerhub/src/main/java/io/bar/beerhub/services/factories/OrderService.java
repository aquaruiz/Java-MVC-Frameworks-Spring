package io.bar.beerhub.services.factories;

import io.bar.beerhub.data.models.Order;
import io.bar.beerhub.services.models.PaycheckServiceModel;

public interface OrderService {
    void orderBeer(String id, Long quantity, String customerName);

    void bookWaitress(String waitressId, String customerName);

    PaycheckServiceModel finalizePayCheck(String customerName);

    Order getCustomerCurrentOrder(String username);

    void closeCustomerOrders(String name);
}
