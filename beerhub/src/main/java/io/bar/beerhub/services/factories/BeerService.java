package io.bar.beerhub.services.factories;

import io.bar.beerhub.services.models.BeerServiceModel;

import java.util.List;

public interface BeerService {
    BeerServiceModel save (BeerServiceModel beerServiceModel);

    List<BeerServiceModel> save(List<BeerServiceModel> beerServiceModels);

    List<BeerServiceModel> getAllBeers();

    List<BeerServiceModel> findAllRunoutsBeers(Long num);

    void buyBeer(BeerServiceModel beerServiceModel, Long quantity);
}
