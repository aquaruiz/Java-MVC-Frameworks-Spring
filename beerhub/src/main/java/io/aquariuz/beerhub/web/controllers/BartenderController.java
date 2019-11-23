package io.aquariuz.beerhub.web.controllers;

import io.aquariuz.beerhub.services.factories.BeerService;
import io.aquariuz.beerhub.web.models.BeerViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BartenderController extends BaseController {
    private final BeerService beerService;
    private final ModelMapper modelMapper;

    public BartenderController(BeerService beerService, ModelMapper modelMapper) {
        this.beerService = beerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/bartender/home")
    public ModelAndView getIndex() {
        return view("/bartender/home");
    }

    @GetMapping("/beers/runouts")
    public ModelAndView getRunouts(ModelAndView modelAndView) {
        List<BeerViewModel> beers = this.beerService.findAllRunoutBeers()
                .stream()
                .map(b -> this.modelMapper.map(b, BeerViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("beers", beers);
        return view("beers/runouts", modelAndView);
    }
}