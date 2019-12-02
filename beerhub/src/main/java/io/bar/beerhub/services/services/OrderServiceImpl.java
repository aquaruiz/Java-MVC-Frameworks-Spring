package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Order;
import io.bar.beerhub.data.repositories.BeerRepository;
import io.bar.beerhub.data.repositories.OrderRepository;
import io.bar.beerhub.data.repositories.UserRepository;
import io.bar.beerhub.data.repositories.WaitressRepository;
import io.bar.beerhub.services.factories.OrderService;
import org.modelmapper.ModelMapper;

public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final WaitressRepository waitressRepository;
    private final BeerRepository beerRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(UserRepository userRepository,
                            OrderRepository orderRepository,
                            WaitressRepository waitressRepository,
                            BeerRepository beerRepository,
                            ModelMapper modelMapper) {
        
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.waitressRepository = waitressRepository;
        this.beerRepository = beerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void orderBeer(String id, Long quantity, String customerId) {


    }

    @Override
    public void bookWaitress(String waitressId, String customerId) {


    }

    @Override
    public void finalizePayCheck(String customerId) {

    }

    @Override
    public Order getCustomerCurrentOrder(String username) {
        return null;
    }
}
