package io.aquariuz.beerhub.web.controllers;

import io.aquariuz.beerhub.services.factories.CustomerService;
import io.aquariuz.beerhub.services.models.CustomerServiceModel;
import io.aquariuz.beerhub.web.models.CustomerRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController extends BaseController {
    private final ModelMapper modelMapper;
    private final CustomerService customerService;

    public CustomerController(ModelMapper modelMapper, CustomerService customerService) {
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @GetMapping("/customers/home")
    public ModelAndView getIndex() {
        return view("/customers/home");

//        return redirect("/customers/home");
    }

    @GetMapping("/customers/register")
    public ModelAndView getRegister() {
        return view("/customers/register");
    }

    @GetMapping("/customers/login")
    public ModelAndView getLogin() {
        return view("/customers/login");
    }

    @PostMapping("/customer/register")
//    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute CustomerRegisterModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("customer/register");
        }

        // TODO add message

        this.customerService.registerCustomer(this.modelMapper.map(model, CustomerServiceModel.class));

        return super.redirect("customer/login");
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        // TODO
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
