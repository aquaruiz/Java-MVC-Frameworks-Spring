package io.bar.beerhub.validations;

import io.bar.beerhub.data.models.Beer;
import io.bar.beerhub.services.models.BeerServiceModel;

public class BeerValidationServiceImpl implements BeerValidationService {
    @Override
    public boolean isValid(Beer beer) {
        return beer != null;
    }

    @Override
    public boolean isValid(BeerServiceModel beerServiceModel) {
        return beerServiceModel != null;
    }
}
