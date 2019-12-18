package io.bar.beerhub.validations;

import io.bar.beerhub.data.models.Beer;
import io.bar.beerhub.services.models.BeerServiceModel;
import io.bar.beerhub.services.models.UserServiceModel;

public interface BeerValidationService {
    boolean isValid(Beer beer);

    boolean isValid(BeerServiceModel beerServiceModel);
}
