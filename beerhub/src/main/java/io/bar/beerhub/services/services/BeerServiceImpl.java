package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Beer;
import io.bar.beerhub.data.repositories.BeerRepository;
import io.bar.beerhub.services.factories.BeerService;
import io.bar.beerhub.services.models.BeerServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final ModelMapper modelMapper;

    public BeerServiceImpl(BeerRepository beerRepository, ModelMapper modelMapper) {
        this.beerRepository = beerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BeerServiceModel save(BeerServiceModel beerServiceModel) {
        Beer beer = this.modelMapper.map(beerServiceModel, Beer.class);
        Beer savedBeer = this.beerRepository.saveAndFlush(beer);
        return this.modelMapper.map(savedBeer, BeerServiceModel.class);
    }

    @Override
    public List<BeerServiceModel> save(List<BeerServiceModel> beerServiceModels) {
        List<Beer> beers = beerServiceModels
                .stream()
                .map(b -> this.modelMapper.map(b, Beer.class))
                .collect(Collectors.toList());
        List<Beer> savedBeers = this.beerRepository.saveAll(beers);
        return savedBeers
                .stream()
                .map(b -> this.modelMapper.map(b, BeerServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BeerServiceModel> getAllBeers() {
        return this.beerRepository.findAll()
                .stream()
                .map(b -> this.modelMapper.map(b, BeerServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BeerServiceModel> findAllRunoutsBeers(Long num) {
        return this.beerRepository.findAllByQuantityLessThan(num)
                .stream()
                .map(b -> this.modelMapper.map(b, BeerServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }
}