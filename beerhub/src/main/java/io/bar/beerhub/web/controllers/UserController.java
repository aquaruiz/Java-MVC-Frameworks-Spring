package io.bar.beerhub.web.controllers;

import io.bar.beerhub.services.factories.LogService;
import io.bar.beerhub.services.factories.UserService;
import io.bar.beerhub.services.models.LogServiceModel;
import io.bar.beerhub.services.models.UserServiceModel;
import io.bar.beerhub.web.models.UserRegisterModel;
import org.apache.catalina.Session;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
public class UserController extends BaseController {

    private final UserService userService;
    private final LogService logService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, LogService logService, ModelMapper modelMapper) {
        this.userService = userService;
        this.logService = logService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public ModelAndView getRegister() {
        return view("/register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterModel model, ModelAndView modelAndView) {
        if (!model.getConfirmPassword().equals(model.getPassword())){
            return view("register", modelAndView);
        }

        this.userService.register(this.modelMapper.map(model, UserServiceModel.class));
        return redirect("/home");
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return view("/login");
    }

    @GetMapping("/home")
    public ModelAndView getHome(Session session) {
        LogServiceModel logServiceModel = new LogServiceModel();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        logServiceModel.setUsername(username);
        logServiceModel.setDescription("logged in");
        logServiceModel.setTime(LocalDateTime.now());

        logService.recLogInDb(logServiceModel);
        return view("/home");
    }

    @GetMapping("/details")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView getDetails() {
        return view("/details");
    }
}