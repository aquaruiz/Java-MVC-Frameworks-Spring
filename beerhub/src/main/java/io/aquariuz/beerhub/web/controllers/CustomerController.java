package io.aquariuz.beerhub.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController extends BaseController {
    @GetMapping("/customers/home")
    public ModelAndView getIndex() {
        return view("/customers/home");

//        return redirect("/customers/home");
    }
}
