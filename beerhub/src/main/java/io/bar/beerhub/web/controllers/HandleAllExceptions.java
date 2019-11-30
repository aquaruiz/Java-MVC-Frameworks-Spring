package io.bar.beerhub.web.controllers;

import io.bar.beerhub.errors.BeerNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HandleAllExceptions {
    @ExceptionHandler(Throwable.class) // upper class exception
    public ModelAndView handleException(Throwable exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject(exception.getMessage());
        return modelAndView;
    }
}
