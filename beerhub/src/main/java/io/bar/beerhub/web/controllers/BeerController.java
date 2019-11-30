package io.bar.beerhub.web.controllers;

import io.bar.beerhub.services.factories.BeerService;
import io.bar.beerhub.services.models.BeerServiceModel;
import io.bar.beerhub.web.models.BeerCreateModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/beers")
public class BeerController extends BaseController {
    private final BeerService beerService;
    private final ModelMapper modelMapper;

    public BeerController(BeerService beerService, ModelMapper modelMapper) {
        this.beerService = beerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public ModelAndView getCreateBeer(ModelAndView modelAndView) {
        modelAndView.addObject("title", "BEER HOME");
        return view("beers/create", modelAndView);
    }

    @PostMapping("/create")
    public ModelAndView createBeer(@ModelAttribute BeerCreateModel beerCreateModel, HttpSession session) {
        BeerServiceModel beerServiceModel = this.modelMapper.map(beerCreateModel, BeerServiceModel.class);
        this.beerService.save(beerServiceModel);
        return redirect("/");
    }

    // small pics only
// <form th:action="@{/avater/create}" th:method="POST" enctype="multipart/form-data">
//        <input type="file" name="image"/>
//        <button type="submit">Upload</button>
// </form>

    // inside controller
    // avatar.setImageUrl(cloudinaryService.upload(uploadModel.getImage()));
}