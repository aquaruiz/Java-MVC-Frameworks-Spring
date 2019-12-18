package io.bar.beerhub.web.controllers;

import io.bar.beerhub.services.factories.LogService;
import io.bar.beerhub.services.factories.OrderService;
import io.bar.beerhub.services.factories.WaitressService;
import io.bar.beerhub.services.models.LogServiceModel;
import io.bar.beerhub.services.models.WaitressServiceModel;
import io.bar.beerhub.util.factory.EscapeCharsUtil;
import io.bar.beerhub.web.annotations.PageTitle;
import io.bar.beerhub.web.models.WaitressDetailsViewModel;
import io.bar.beerhub.web.models.WaitressViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/waitress")
public class WaitressController extends BaseController {
    private final ModelMapper modelMapper;
    private final EscapeCharsUtil escapeCharsUtil;
    private final WaitressService waitressService;
    private final OrderService orderService;
    private final LogService logService;

    @Autowired
    public WaitressController(ModelMapper modelMapper, EscapeCharsUtil escapeCharsUtil, WaitressService waitressService, OrderService orderService, LogService logService) {
        this.modelMapper = modelMapper;
        this.escapeCharsUtil = escapeCharsUtil;
        this.waitressService = waitressService;
        this.orderService = orderService;
        this.logService = logService;
    }

    @GetMapping("/add")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_ROOT", "ROLE_BARTENDER"})
    @PageTitle("Add Waitress")
    public ModelAndView addWaitress(ModelAndView modelAndView) {
        modelAndView.addObject("waitress", new WaitressViewModel());
        return this.view("waitress/add", modelAndView);
    }

    @PostMapping("/add")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_ROOT", "ROLE_BARTENDER"})
    @PageTitle("Add Waitress")
    public ModelAndView addWaitress(Principal principal,
                                    ModelAndView modelAndView,
                                    BindingResult bindingResult,
                                    @ModelAttribute(name = "waitress") @Valid WaitressViewModel waitressViewModel) {

        // TODO
        bindingResult.hasErrors();
        waitressViewModel = escapeCharsUtil.escapeChars(waitressViewModel);
        WaitressServiceModel waitressServiceModel = this.modelMapper.map(waitressViewModel, WaitressServiceModel.class);
        this.logService.recLogInDb(new LogServiceModel()
                                   {{
                                        setUsername(principal.getName());
                                        setTime(LocalDateTime.now());
                                        setDescription("Added new waitress: " + waitressServiceModel.getName());
                                   }}
        );

        this.waitressService.addWaitress(waitressServiceModel, waitressViewModel.getImage());

        modelAndView.addObject("waitress", new WaitressViewModel());
        return this.redirect("/all");
    }


    @RolesAllowed({"ROLE_ADMIN", "ROLE_ROOT", "ROLE_BARTENDER", "ROLE_CUSTOMER"})
    @PageTitle("Waitress Details")
    @GetMapping("/details/{id}")
    public ModelAndView getDetails(@PathVariable String id, ModelAndView modelAndView){
        WaitressServiceModel found = this.waitressService.findById(id);
        WaitressDetailsViewModel waitressViewModel = this.modelMapper.map(found,
                WaitressDetailsViewModel.class);
        modelAndView.addObject("waitress", waitressViewModel);
        return this.view("/waitress/details", modelAndView);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_ROOT", "ROLE_BARTENDER", "ROLE_CUSTOMER"})
    @PageTitle("Waitress Delete")
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable String id){
        this.waitressService.deleteWaitress(id);
        return this.redirect("/waitress/all");
    }

//    @PreAuthorize("hasAuthority('MODERATOR')")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_ROOT", "ROLE_BARTENDER", "ROLE_CUSTOMER"})
    @PageTitle("All waitresses")
    @GetMapping("/all")
    public ModelAndView viewAllWaitresses(ModelAndView modelAndView){
        List<WaitressServiceModel> waitresses = this.waitressService.findAll();
        modelAndView.addObject("waitresses", waitresses.stream()
                        .map(w -> this.modelMapper.map(w, WaitressDetailsViewModel.class))
                        .collect(Collectors.toList())
        );

        return this.view("/waitress/all", modelAndView);
    }

    @PostMapping("/pick")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_ROOT", "ROLE_BARTENDER", "ROLE_CUSTOMER"})
    public ModelAndView pickWaitress(@ModelAttribute WaitressViewModel waitressViewModel, ModelAndView modelAndView, Principal principal) {
        WaitressServiceModel chosen = this.waitressService.findById(waitressViewModel.getId());
        this.orderService.bookWaitress(waitressViewModel.getId(), principal.getName());
        return this.redirect("/bar/menu");
    }
}
