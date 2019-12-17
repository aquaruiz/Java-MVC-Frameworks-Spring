package io.bar.beerhub.services.factories;

import io.bar.beerhub.data.models.Order;
import io.bar.beerhub.services.models.PaycheckServiceModel;

public interface OrderService {
    boolean orderBeer(String id, Long quantity, String customerName);

    boolean bookWaitress(String waitressId, String customerName);

    PaycheckServiceModel finalizePayCheck(String customerName);

    Order getCustomerCurrentOrder(String username);

    boolean closeCustomerOrders(String name);
}
