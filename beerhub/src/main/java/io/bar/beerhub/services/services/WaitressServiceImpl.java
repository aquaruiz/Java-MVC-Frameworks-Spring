package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Waitress;
import io.bar.beerhub.data.repositories.WaitressRepository;
import io.bar.beerhub.services.factories.CloudinaryService;
import io.bar.beerhub.services.factories.WaitressService;
import io.bar.beerhub.services.models.WaitressServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaitressServiceImpl implements WaitressService {
    private final ModelMapper modelMapper;
    private final WaitressRepository waitressRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public WaitressServiceImpl(ModelMapper modelMapper, WaitressRepository waitressRepository, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.waitressRepository = waitressRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<WaitressServiceModel> findAll() {
        List<Waitress> waitresses = this.waitressRepository.findAll();

        return waitresses.stream()
                .map(w -> this.modelMapper.map(w, WaitressServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public WaitressServiceModel addWaitress(WaitressServiceModel waitressServiceModel, MultipartFile image) {
        Waitress waitress = this.modelMapper.map(waitressServiceModel, Waitress.class);

        try {
            waitress.setImage(
                    this.cloudinaryService.upload(image)
            );
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

        Waitress savedWaitress = this.waitressRepository.saveAndFlush(waitress);
        return this.modelMapper.map(savedWaitress, WaitressServiceModel.class);
    }

    @Override
    public WaitressServiceModel editWaitress(String id, WaitressServiceModel waitressServiceModel, MultipartFile multipartImg) {
        Waitress savedWaitress = this.waitressRepository.findById(id).orElse(null);

        if (savedWaitress == null) {
//            throw new ProductNotFoundException(ConstantsDefinition.ProductConstants.NO_SUCH_PRODUCT);
        }

        savedWaitress.setName(waitressServiceModel.getName());
        savedWaitress.setImage(waitressServiceModel.getImage());
        savedWaitress.setTipsRate(waitressServiceModel.getTipsRate());
        this.waitressRepository.saveAndFlush(savedWaitress);
        return waitressServiceModel;
    }

    @Override
    public void deleteWaitress(String id) {
        Waitress savedWaitress = this.waitressRepository.findById(id).orElse(null);

        if (savedWaitress == null) {
//            throw new ProductNotFoundException(ConstantsDefinition.ProductConstants.NO_SUCH_PRODUCT);
        }

        this.waitressRepository.delete(savedWaitress);
    }

    @Override
    public WaitressServiceModel findById(String id) {
        Waitress waitress = this.waitressRepository.findById(id).get();
        return this.modelMapper.map(waitress, WaitressServiceModel.class);
    }

    @Override
    public WaitressServiceModel findByName(String name) {
        Waitress waitress = this.waitressRepository.findByName(name);
        return this.modelMapper.map(waitress, WaitressServiceModel.class);
    }

    @Override
    public List<WaitressServiceModel> listAllByTipsRateDesc() {
        List<Waitress> waitresses = this.waitressRepository.findAllByOrderByTipsRateDesc();
        return waitresses.stream()
                .map(w -> this.modelMapper.map(w, WaitressServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<WaitressServiceModel> listAllByTipsRateAsc() {
        List<Waitress> waitresses = this.waitressRepository.findAllByOrderByTipsRateAsc();
        return waitresses.stream()
                .map(w -> this.modelMapper.map(w, WaitressServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void initWaitressesInDb() {
        List<Waitress> firstWaitresses = new ArrayList<>();
        Waitress waitressFirst = new Waitress();
        waitressFirst.setName("Peg");
        waitressFirst.setTipsRate(0.25);
        waitressFirst.setImage("http://res.cloudinary.com/babaqga/image/upload/v1576098896/nl1lq40oq4tnge8x0ud0.png");

        firstWaitresses.add(waitressFirst);

        Waitress waitressSecond = new Waitress();
        waitressSecond.setName("Joan");
        waitressSecond.setTipsRate(0.02);
        waitressSecond.setImage("http://res.cloudinary.com/babaqga/image/upload/v1576098918/bupsldqadf23bw2lxjoy.png");

        firstWaitresses.add(waitressSecond);

        Waitress waitressThird = new Waitress();
        waitressThird.setName("Alex");
        waitressThird.setTipsRate(0.05);
        waitressThird.setImage("http://res.cloudinary.com/babaqga/image/upload/v1576098967/mgvvhvqvt9pdzk7vznfb.png");

        firstWaitresses.add(waitressThird);

        Waitress waitressForth = new Waitress();
        waitressForth.setName("Olga");
        waitressForth.setTipsRate(0.15);
        waitressForth.setImage("http://res.cloudinary.com/babaqga/image/upload/v1576098946/beppb0ikajij48oupata.png");

        firstWaitresses.add(waitressForth);
        this.waitressRepository.saveAll(firstWaitresses);
    }
}