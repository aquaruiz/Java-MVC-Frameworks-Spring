package io.bar.beerhub.web.controllers;

import io.bar.beerhub.services.factories.BeerService;
import io.bar.beerhub.services.factories.CashService;
import io.bar.beerhub.services.factories.OrderService;
import io.bar.beerhub.services.models.BeerServiceModel;
import io.bar.beerhub.services.models.PaycheckServiceModel;
import io.bar.beerhub.web.annotations.PageTitle;
import io.bar.beerhub.web.models.BeerViewModel;
import io.bar.beerhub.web.models.Billmodel;
import io.bar.beerhub.web.models.OrderModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bar")
public class OrderController extends BaseController {
    private final BeerService beerService;
    private final OrderService orderService;
    private final CashService cashService;
    private final ModelMapper modelMapper;

    public OrderController(BeerService beerService, OrderService orderService, CashService cashService, ModelMapper modelMapper) {
        this.beerService = beerService;
        this.orderService = orderService;
        this.cashService = cashService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/lobby")
    @PageTitle("BeerHub Lobby")
    public ModelAndView getLobby(ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("user", principal);

        return view("bar/lobby", modelAndView);
    }

    @GetMapping("/menu")
    @PageTitle("Beer Menu")
    public ModelAndView getBeerMenu(ModelAndView modelAndView) {
        List<BeerServiceModel> savedBeers = this.beerService.findAllBeersOnStock();
        List<BeerViewModel> beerListing = savedBeers.stream()
                .map(b -> this.modelMapper.map(b, BeerViewModel.class))
                .collect(Collectors.toUnmodifiableList());
        modelAndView.addObject("beers", beerListing);
        return view("bar/menu", modelAndView);
    }

    @PostMapping("/order")
    public ModelAndView orderOneMore(@ModelAttribute OrderModel orderModel, ModelAndView modelAndView, Principal principal) {
        this.orderService.orderBeer(orderModel.getBeerId(), 1L, principal.getName());
        BeerViewModel beerViewModel = this.modelMapper.map(this.beerService.findOneById(orderModel.getBeerId()), BeerViewModel.class);
        modelAndView.addObject("order", beerViewModel);
        return view("/bar/served", modelAndView);
    }

    @GetMapping("/paycheck")
    public ModelAndView callPayCheck(ModelAndView modelAndView, Principal principal) {
        PaycheckServiceModel paycheckModel = this.orderService.finalizePayCheck(principal.getName());
        modelAndView.addObject("paycheck", paycheckModel);
        return this.view("bar/paycheck", modelAndView);
    }

    @PostMapping("/pay")
    public ModelAndView finishPaycheck(@ModelAttribute Billmodel billmodel, ModelAndView modelAndView, Principal principal) {
        if (billmodel.getBill().equals(BigDecimal.ZERO)) {
            return redirect("/home");
        }

        this.cashService.collectMoney(billmodel.getBill());
        this.orderService.closeCustomerOrders(principal.getName());
        return redirect("/home");
    }
}
