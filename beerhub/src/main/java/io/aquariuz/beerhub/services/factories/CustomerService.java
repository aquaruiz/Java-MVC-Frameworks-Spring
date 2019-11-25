package io.aquariuz.beerhub.services.factories;

import io.aquariuz.beerhub.services.models.CustomerServiceModel;

public interface CustomerService {
    public CustomerServiceModel registerCustomer(CustomerServiceModel customerServiceModel);

    CustomerServiceModel findUserByUserName(String username);

}
