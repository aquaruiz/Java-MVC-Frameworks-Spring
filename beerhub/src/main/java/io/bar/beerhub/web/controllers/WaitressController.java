package io.bar.beerhub.web.controllers;

import io.bar.beerhub.services.factories.LogService;
import io.bar.beerhub.services.factories.OrderService;
import io.bar.beerhub.services.factories.WaitressService;
import io.bar.beerhub.services.models.LogServiceModel;
import io.bar.beerhub.services.models.WaitressServiceModel;
import io.bar.beerhub.web.annotations.PageTitle;
import io.bar.beerhub.web.models.WaitressDetailsViewModel;
import io.bar.beerhub.web.models.WaitressViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/waitress")
public class WaitressController extends BaseController {
    private final ModelMapper modelMapper;
    private final WaitressService waitressService;
    private final OrderService orderService;
    private final LogService logService;

    public WaitressController(ModelMapper modelMapper, WaitressService waitressService, OrderService orderService, LogService logService) {
        this.modelMapper = modelMapper;
        this.waitressService = waitressService;
        this.orderService = orderService;
        this.logService = logService;
    }

    @GetMapping("/add")
//    @PreAuthorize("hasAuthority('MODERATOR')")
    @PageTitle("Add Waitress")
    public ModelAndView addWaitress(ModelAndView modelAndView) {
        modelAndView.addObject("waitress", new WaitressViewModel());
        return this.view("waitress/add", modelAndView);
    }

    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('MODERATOR')")
    @PageTitle("Add Waitress")
    public ModelAndView addWaitress(Principal principal,
                                    ModelAndView modelAndView,
                                    @ModelAttribute(name = "waitress") @Valid WaitressViewModel waitressViewModel) {

        WaitressServiceModel waitressServiceModel = this.modelMapper.map(waitressViewModel, WaitressServiceModel.class);
        this.logService.recLogInDb(new LogServiceModel()
                                   {{
                                        setUsername(principal.getName());
                                        setTime(LocalDateTime.now());
                                        setDescription("Added new waitress: " + waitressViewModel.getName());
                                   }}
        );

        this.waitressService.addWaitress(waitressServiceModel, waitressViewModel.getImage());

        modelAndView.addObject("waitress", new WaitressViewModel());
        return this.redirect("/all");
    }


    @GetMapping("/details/{id}")
    @PageTitle("Waitress Details")
    public ModelAndView getDetails(@PathVariable String id, ModelAndView modelAndView){
        WaitressServiceModel found = this.waitressService.findById(id);
        WaitressDetailsViewModel waitressViewModel = this.modelMapper.map(found,
                WaitressDetailsViewModel.class);
        modelAndView.addObject("waitress", waitressViewModel);
        return this.view("/waitress/details", modelAndView);
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('MODERATOR')")
    @PageTitle("All waitresses")
    public ModelAndView viewAllWaitresses(ModelAndView modelAndView){
        List<WaitressServiceModel> waitresses = this.waitressService.findAll();
        modelAndView.addObject("waitresses", waitresses.stream()
                        .map(w -> this.modelMapper.map(w, WaitressDetailsViewModel.class))
                        .collect(Collectors.toList())
        );

        return this.view("/waitress/all", modelAndView);
    }

    @PostMapping("/pick")
    public ModelAndView pickWaitress(@ModelAttribute WaitressViewModel waitressViewModel, ModelAndView modelAndView, Principal principal) {
        WaitressServiceModel chosen = this.waitressService.findById(waitressViewModel.getId());
        this.orderService.bookWaitress(waitressViewModel.getId(), principal.getName());
        return this.redirect("/bar/menu");
    }
}
