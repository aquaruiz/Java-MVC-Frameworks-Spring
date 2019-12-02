package io.bar.beerhub.web.controllers;

import io.bar.beerhub.services.factories.BeerService;
import io.bar.beerhub.services.models.BeerServiceModel;
import io.bar.beerhub.web.annotations.PageTitle;
import io.bar.beerhub.web.models.BeerViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bar")
public class OrderController extends BaseController {
    private final BeerService beerService;
    private final ModelMapper modelMapper;

    public OrderController(BeerService beerService, ModelMapper modelMapper) {
        this.beerService = beerService;
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
}
