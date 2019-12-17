package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Beer;
import io.bar.beerhub.data.models.Order;
import io.bar.beerhub.data.models.User;
import io.bar.beerhub.data.models.Waitress;
import io.bar.beerhub.data.repositories.BeerRepository;
import io.bar.beerhub.data.repositories.OrderRepository;
import io.bar.beerhub.data.repositories.UserRepository;
import io.bar.beerhub.data.repositories.WaitressRepository;
import io.bar.beerhub.errors.BeerNotFoundException;
import io.bar.beerhub.errors.WaitressNotFoundException;
import io.bar.beerhub.services.factories.OrderService;
import io.bar.beerhub.services.models.PaycheckServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public boolean orderBeer(String id, Long quantity, String customerName) {
        User savedUser = this.userRepository.findByUsername(customerName);

        if (savedUser == null) {
            throw new UsernameNotFoundException("Not such customer");
        }

        Optional<Beer> foundBeer = this.beerRepository.findById(id);

        if (foundBeer == null || foundBeer.isEmpty()){
            throw new BeerNotFoundException("Not such beer");
        }

        Beer orderedBeer = foundBeer.get();

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
        return true;
    }

    @Override
    public boolean bookWaitress(String waitressId, String customerName) {
        User savedUser = this.userRepository.findByUsername(customerName);

        if (savedUser == null) {
            throw new UsernameNotFoundException("Not such customer");
        }

        List<Order> customerOrders = this.orderRepository.getAllOpenOrdersByCustomer(savedUser);

        Order currentOrder = null;

        if (customerOrders.size() > 0) {
            currentOrder = customerOrders.get(0);
        } else {
            currentOrder = new Order();
            currentOrder.setCustomer(savedUser);
        }

        Optional<Waitress> foundWaitress = this.waitressRepository.findById(waitressId);

        if (foundWaitress == null || foundWaitress.isEmpty()){
            throw new WaitressNotFoundException("Not Such waitress");
        }

        Waitress chosenWaitress = foundWaitress.get();
        currentOrder.setWaitress(chosenWaitress);

        this.orderRepository.saveAndFlush(currentOrder);
        return true;
    }

    @Override
    public PaycheckServiceModel finalizePayCheck(String customerName) {
        User customer = this.userRepository.findByUsername(customerName);

        if (customer == null) {
            throw new UsernameNotFoundException("Not such customer");
        }

        List<Order> orders = this.orderRepository.getAllByCustomer(customer);

        if (orders.size() == 0) {
            return new PaycheckServiceModel();
        }

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

        if (savedUser == null) {
            throw new UsernameNotFoundException("Not such customer");
        }

        List<Order> customerOrders = this.orderRepository.getAllOpenOrdersByCustomer(savedUser);
        Order currentOrder;

        if (customerOrders.size() == 0) {
            currentOrder = new Order();
            currentOrder.setCustomer(savedUser);
        } else {
            currentOrder = customerOrders.get(0);
        }

        return currentOrder;
    }

    @Override
    public boolean closeCustomerOrders(String name) {
        User customer = this.userRepository.findByUsername(name);

        if (customer == null) {
            throw new UsernameNotFoundException("Not such customer");
        }

        List<Order> orders = this.orderRepository.getAllOpenOrdersByCustomer(customer);

        if (orders.size() < 1) {
            return false;
        }

        for (Order order : orders) {
            order.setClosed(true);
        }

        this.orderRepository.saveAll(orders);
        return true;
    }
}
