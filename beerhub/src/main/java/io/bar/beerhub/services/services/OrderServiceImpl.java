package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Beer;
import io.bar.beerhub.data.models.Order;
import io.bar.beerhub.data.models.User;
import io.bar.beerhub.data.models.Waitress;
import io.bar.beerhub.data.repositories.BeerRepository;
import io.bar.beerhub.data.repositories.OrderRepository;
import io.bar.beerhub.data.repositories.UserRepository;
import io.bar.beerhub.data.repositories.WaitressRepository;
import io.bar.beerhub.services.factories.OrderService;
import io.bar.beerhub.services.models.PaycheckServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
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
    public void orderBeer(String id, Long quantity, String customerName) {
        User savedUser = this.userRepository.findByUsername(customerName);
        Beer orderedBeer = this.beerRepository.findById(id).get();

        List<Order> customerOrders = this.orderRepository.getAllOpenOrdersByCustomer(savedUser);

        Order currentOrder = null;

        if (customerOrders.size() > 0) {
            currentOrder = customerOrders.get(0);
        } else {
            currentOrder = new Order();
            currentOrder.setCustomer(savedUser);

            List<Beer> beers = new ArrayList<>();

            for (int i = 0; i < quantity; i++) {
                beers.add(orderedBeer);
            }

            currentOrder.setBeers(beers);
            currentOrder.setClosed(false);
        }

        if (currentOrder.getWaitress() == null) {
            Waitress myWaitress = this.waitressRepository.findAllByOrderByTipsRateAsc().get(0);
            currentOrder.setWaitress(myWaitress);
        }

        this.orderRepository.saveAndFlush(currentOrder);
    }

    @Override
    public void bookWaitress(String waitressId, String customerName) {
        User savedUser = this.userRepository.findByUsername(customerName);
        List<Order> customerOrders = this.orderRepository.getAllOpenOrdersByCustomer(savedUser);


        Order currentOrder = null;

        if (customerOrders.size() > 0) {
            currentOrder = customerOrders.get(0);
        } else {
            currentOrder = new Order();
            currentOrder.setCustomer(savedUser);
        }

        Waitress chosenWaitress = this.waitressRepository.findById(waitressId).get();
        currentOrder.setWaitress(chosenWaitress);

        this.orderRepository.saveAndFlush(currentOrder);
    }

    @Override
    public PaycheckServiceModel finalizePayCheck(String customerName) {
        User customer = this.userRepository.findByUsername(customerName);
        List<Order> orders = this.orderRepository.getAllByCustomer(customer);
        long ordersNum = 0;
        long beersNum = 0;

        BigDecimal bill = BigDecimal.valueOf(0);
        BigDecimal billTip = BigDecimal.valueOf(0);
        String waitressName = "";

        for (Order order : orders) {
            Waitress waitress = order.getWaitress();
            waitressName = waitress.getName();

            if (!order.isClosed()) {
                ordersNum++;
                List<Beer> currentBeers = order.getBeers();

                BigDecimal currentBill = new BigDecimal(0);
                double tips = waitress.getTipsRate();

                for (Beer beer : currentBeers) {
                    beersNum++;
                    BigDecimal currentPrice = beer.getSellPrice();
                    currentBill = currentBill.add(currentPrice);
                }

                bill = bill.add(currentBill);
                billTip = billTip.add(currentBill.multiply(BigDecimal.valueOf(tips)));
            }
        }

        PaycheckServiceModel paycheckServiceModel = new PaycheckServiceModel();
        paycheckServiceModel.setOrdersNum(ordersNum);
        paycheckServiceModel.setBeersNum(beersNum);
        paycheckServiceModel.setBill(bill);
        paycheckServiceModel.setTips(billTip);
        paycheckServiceModel.setLastWaitressName(waitressName);

        return paycheckServiceModel;
    }

    @Override
    public Order getCustomerCurrentOrder(String username) {
        User savedUser = this.userRepository.findByUsername(username);
        Order customerOrder = this.orderRepository.getByCustomer(savedUser);

        if (customerOrder == null || customerOrder.isClosed()) {
            customerOrder = new Order();
            customerOrder.setCustomer(savedUser);
        }

        return customerOrder;
    }

    @Override
    public void closeCustomerOrders(String name) {
        User customer = this.userRepository.findByUsername(name);
        List<Order> orders = this.orderRepository.getAllByCustomer(customer);

        for (Order order : orders) {
            order.setClosed(true);
        }

        this.orderRepository.saveAll(orders);
    }
}
