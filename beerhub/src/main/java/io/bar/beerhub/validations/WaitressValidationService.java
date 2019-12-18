package io.bar.beerhub.validations;

import io.bar.beerhub.data.models.Waitress;
import io.bar.beerhub.services.models.WaitressServiceModel;

public interface WaitressValidationService {
    boolean isValid(Waitress waitress);

    boolean isValid(WaitressServiceModel waitressServiceModel);
}
