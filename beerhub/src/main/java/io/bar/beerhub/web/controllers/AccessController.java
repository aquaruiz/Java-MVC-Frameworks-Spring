package io.bar.beerhub.web.controllers;

import io.bar.beerhub.web.annotations.PageTitle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;

@Controller
@RequestMapping("/unauthorized")
public class AccessController extends BaseController {

    @GetMapping("")
    @PermitAll
    public ModelAndView enterUnauthorized(ModelAndView modelAndView) {
        return view("access-error");
    }
}