package io.aquariuz.beerhub.services.factories;

import io.aquariuz.beerhub.services.models.BeerServiceModel;

import java.util.List;

public interface BeerService {
    BeerServiceModel addBeer(BeerServiceModel beerServiceModel);

    List<BeerServiceModel> findAllBeers();

    List<BeerServiceModel> findAllRunoutBeers();
}
