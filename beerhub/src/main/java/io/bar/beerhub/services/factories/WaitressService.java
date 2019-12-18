package io.bar.beerhub.services.factories;

import io.bar.beerhub.services.models.WaitressServiceModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WaitressService {
    WaitressServiceModel addWaitress(WaitressServiceModel waitressServiceModel, MultipartFile multipartImg);

    WaitressServiceModel editWaitress(String id, WaitressServiceModel waitressServiceModel, MultipartFile multipartImg);

    void deleteWaitress(String id);

    public List<WaitressServiceModel> findAll();

    WaitressServiceModel findById(String id);

    WaitressServiceModel findByName(String name);

    List<WaitressServiceModel> listAllByTipsRateDesc();

    List<WaitressServiceModel> listAllByTipsRateAsc();

    void initWaitressesInDb();
}
