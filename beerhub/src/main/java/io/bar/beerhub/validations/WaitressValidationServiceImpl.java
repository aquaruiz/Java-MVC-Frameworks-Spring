package io.bar.beerhub.validations;

import io.bar.beerhub.data.models.Waitress;
import io.bar.beerhub.services.models.WaitressServiceModel;

public class WaitressValidationServiceImpl implements WaitressValidationService {

    @Override
    public boolean isValid(Waitress waitress) {
        return  waitress != null;
    }

    @Override
    public boolean isValid(WaitressServiceModel waitressServiceModel) {
        return waitressServiceModel != null;
    }
}
