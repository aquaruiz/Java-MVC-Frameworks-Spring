package io.bar.beerhub.web.controllers;

import io.bar.beerhub.web.annotations.PageTitle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/unauthorized")
public class AccessController  extends BaseController {

    public AccessController() {
    }

    // TODO
    @GetMapping("")
    public ModelAndView ddd(ModelAndView modelAndView) {
        return view("");
    }
}