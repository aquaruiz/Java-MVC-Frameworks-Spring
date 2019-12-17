package biz.bar.beerhub.services;

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
import io.bar.beerhub.services.services.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTests {
    UserRepository userRepositoryMock;
    OrderRepository orderRepositoryMock;
    WaitressRepository waitressRepositoryMock;
    BeerRepository beerRepositoryMock;
    ModelMapper modelMapper;

    OrderService orderService;

    @Before
    public void before() {
        this.userRepositoryMock = Mockito.mock(UserRepository.class);
        this.orderRepositoryMock = Mockito.mock(OrderRepository.class);
        this.waitressRepositoryMock = Mockito.mock(WaitressRepository.class);
        this.beerRepositoryMock = Mockito.mock(BeerRepository.class);
        this.modelMapper = new ModelMapper();

        this.orderService = new OrderServiceImpl(userRepositoryMock, orderRepositoryMock, waitressRepositoryMock, beerRepositoryMock, modelMapper);
    }

    @Test
    public void closeCustomerOrders_WhenNoOpenOrders_ShouldReturnFalse() {
        when(userRepositoryMock.findByUsername(any())).thenReturn(new User());
        when(orderRepositoryMock.getAllOpenOrdersByCustomer(any(User.class))).thenReturn(new ArrayList<>());

        boolean result = this.orderService.closeCustomerOrders("anybody");

        Assert.assertFalse(result);
    }

    @Test
    public void closeCustomerOrders_WhenSomeOpenOrders_ShouldReturnFalse() {
        Order order = new Order();
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);

        when(userRepositoryMock.findByUsername(any())).thenReturn(new User());
        when(orderRepositoryMock.getAllOpenOrdersByCustomer(any(User.class))).thenReturn(orderList);

        boolean result = this.orderService.closeCustomerOrders("anybody");

        Assert.assertTrue(result);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void closeCustomerOrders_WhenCustomerNotFound_ShouldThrowUsernameNotFoundException() {
        when(userRepositoryMock.findByUsername(any())).thenReturn(null);

        boolean result = this.orderService.closeCustomerOrders("anybody");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void getCustomerCurrentOrder_WhenCustomerNotFound_ShouldThrowUsernameNotFoundException() {
        when(userRepositoryMock.findByUsername(any())).thenReturn(null);

        Order result = orderService.getCustomerCurrentOrder("anybody");
    }

    @Test
    public void getCustomerCurrentOrder_WhenNoOrders_ShouldReturnNewOrder() {
        User newUser = new User();
        Order newOrder = new Order();
        newOrder.setCustomer(newUser);

        when(userRepositoryMock.findByUsername(any(String.class))).thenReturn(newUser);
        when(orderRepositoryMock.getAllOpenOrdersByCustomer(any(User.class))).thenReturn(new ArrayList<>());

        Order result = orderService.getCustomerCurrentOrder("anybody");

        Assert.assertThat(result, samePropertyValuesAs(newOrder));
    }

    @Test
    public void getCustomerCurrentOrder_WhenOrders_ShouldReturnOrderList() {
        User newUser = new User();
        Order newOrder = new Order();
        newOrder.setCustomer(newUser);
        List<Order> orderList = new ArrayList<>();
        orderList.add(newOrder);

        when(userRepositoryMock.findByUsername(any(String.class))).thenReturn(newUser);
        when(orderRepositoryMock.getAllOpenOrdersByCustomer(any(User.class))).thenReturn(orderList);

        Order result = orderService.getCustomerCurrentOrder("anybody");

        Assert.assertThat(result, samePropertyValuesAs(orderList.get(0)));
    }


    @Test(expected = UsernameNotFoundException.class)
    public void PaycheckServiceModel_WhenCustomerNotFound_ShouldThrowUsernameNotFoundException() {
        when(userRepositoryMock.findByUsername(any())).thenReturn(null);

        PaycheckServiceModel result = this.orderService.finalizePayCheck("anybody");
    }

    @Test()
    public void PaycheckServiceModel_WhenNoOrders_ShouldReturnEmptyModel() {
        when(userRepositoryMock.findByUsername(any())).thenReturn(new User());
        when(orderRepositoryMock.getAllByCustomer(any(User.class))).thenReturn(new ArrayList<>());

        PaycheckServiceModel emptyPaycheckModel = new PaycheckServiceModel();
        PaycheckServiceModel result = this.orderService.finalizePayCheck("anybody");

        Assert.assertThat(emptyPaycheckModel, samePropertyValuesAs(result));
    }


    @Test()
    public void PaycheckServiceModel_WhenSomeOrders_ShouldReturnRightModel() {
        User newUser = new User();
        newUser.setUsername("anybody");

        Beer beer = new Beer();
        beer.setName("Beck's");
        beer.setSellPrice(BigDecimal.valueOf(5));

        List<Beer> myBeers = new ArrayList<>();
        myBeers.add(beer);
        myBeers.add(beer);
        myBeers.add(beer);

        Waitress waitress = new Waitress();
        waitress.setTipsRate(0.05);
        waitress.setName("Peggy");

        Order order = new Order();
        order.setWaitress(waitress);
        order.setBeers(myBeers);
        order.setCustomer(newUser);
        order.setClosed(false);

        List<Order> myOrders = new ArrayList<>();
        myOrders.add(order);

        PaycheckServiceModel paycheckServiceModel = new PaycheckServiceModel();
        paycheckServiceModel.setLastWaitressName(waitress.getName());
        paycheckServiceModel.setBeersNum(myBeers.size());
        paycheckServiceModel.setTips(BigDecimal.valueOf(waitress.getTipsRate()).multiply((beer.getSellPrice().multiply(BigDecimal.valueOf(myBeers.size())))));
        paycheckServiceModel.setOrdersNum(myOrders.size());
        paycheckServiceModel.setBill(beer.getSellPrice().multiply(BigDecimal.valueOf(myBeers.size())));

        when(userRepositoryMock.findByUsername(any())).thenReturn(newUser);
        when(orderRepositoryMock.getAllByCustomer(any(User.class))).thenReturn(myOrders);

        PaycheckServiceModel result = orderService.finalizePayCheck("anybody");

        Assert.assertThat(result, samePropertyValuesAs(paycheckServiceModel));
    }
}
