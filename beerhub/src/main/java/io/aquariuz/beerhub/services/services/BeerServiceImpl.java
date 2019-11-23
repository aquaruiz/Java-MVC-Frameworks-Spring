package io.aquariuz.beerhub.services.services;

import io.aquariuz.beerhub.data.models.Beer;
import io.aquariuz.beerhub.data.repositories.BeerRepository;
import io.aquariuz.beerhub.services.factories.BeerService;
import io.aquariuz.beerhub.services.models.BeerServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BeerServiceImpl(BeerRepository beerRepository, ModelMapper modelMapper) {
        this.beerRepository = beerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BeerServiceModel addBeer(BeerServiceModel beerServiceModel) {
        Beer beer = this.modelMapper.map(beerServiceModel, Beer.class);
//        Beer beer = this.beerRepository
//                .findByName(productServiceModel.getBrand().getName())
//                .orElse(null);
//        if (brand == null) {
//            throw new BrandNotFoundException("Brand with the given id is not found!");
//        }

//        product.setBrand(brand);
        this.beerRepository.saveAndFlush(beer);

//        brand.getProducts().add(product);
//        this.brandRepository.saveAndFlush(brand);

        return this.modelMapper.map(beer, BeerServiceModel.class);
    }

    @Override
    public List<BeerServiceModel> findAllBeers() {
        return this.beerRepository.findAll()
                .stream()
                .map(b -> this.modelMapper.map(b, BeerServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BeerServiceModel> findAllRunoutBeers() {
        return this.beerRepository.findAllByQuantityLessThan(10)
                .stream()
                .map(b -> this.modelMapper.map(b, BeerServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }
}