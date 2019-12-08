package io.bar.beerhub.services.factories;

import io.bar.beerhub.data.models.Beer;
import io.bar.beerhub.services.models.BeerServiceModel;

import java.util.List;

public interface BeerService {
    BeerServiceModel save(BeerServiceModel beerServiceModel);

    List<BeerServiceModel> save(List<BeerServiceModel> beerServiceModels);

    List<BeerServiceModel> getAllBeers();

    BeerServiceModel findOneById(String beerId);

    List<BeerServiceModel> findAllRunoutsBeers(Long num);

    List<BeerServiceModel> findAllBeersOnStock();

    void buyBeer(BeerServiceModel beerServiceModel, Long quantity);
}
