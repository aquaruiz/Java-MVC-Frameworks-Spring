package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Beer;
import io.bar.beerhub.data.repositories.BeerRepository;
import io.bar.beerhub.errors.BeerNotFoundException;
import io.bar.beerhub.services.factories.BeerService;
import io.bar.beerhub.services.models.BeerServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
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
    public BeerServiceModel save(BeerServiceModel beerServiceModel) {
        Beer beer = this.modelMapper.map(beerServiceModel, Beer.class);
        beer.setQuantity(0L);
        if (beer.getBuyPrice() == null) {
            beer.setBuyPrice(BigDecimal.ZERO);
        }

        if (beer.getSellPrice() == null) {
            beer.setSellPrice(BigDecimal.ZERO);
        }

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
    public BeerServiceModel findOneById(String beerId) {
        Optional<Beer> foundBeer = this.beerRepository.findById(beerId);

        if (foundBeer == null || foundBeer.isEmpty()){
            throw new BeerNotFoundException("Beer not found");
        }

        Beer savedBeer = foundBeer.get();
        return this.modelMapper.map(savedBeer, BeerServiceModel.class);
    }

    @Override
    public List<BeerServiceModel> findAllRunoutsBeers(Long num) {
        return this.beerRepository.findAllByQuantityLessThan(num)
                .stream()
                .map(b -> this.modelMapper.map(b, BeerServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BeerServiceModel> findAllBeersOnStock() {
        return this.beerRepository.findAllByQuantityGreaterThan(0L)
                .stream()
                .map(b -> this.modelMapper.map(b, BeerServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean buyBeer(BeerServiceModel beerServiceModel, Long quantity) {
        String beerId = beerServiceModel.getId();
        Optional<Beer> foundBeer = this.beerRepository.findById(beerId);

        if (foundBeer == null || foundBeer.isEmpty() || !foundBeer.get().getName().equals(beerServiceModel.getName())) {
            throw new BeerNotFoundException("Beer not found");
        }

        Beer beerToBuy = foundBeer.get();

        if (quantity < 1) {
            return false;
        }

        beerToBuy.setQuantity(beerToBuy.getQuantity() + quantity);
        this.beerRepository.saveAndFlush(beerToBuy);
        return true;
    }

    @Override
    public boolean edit(BeerServiceModel beerServiceModel) {
        Optional<Beer> foundBeer = this.beerRepository.findById(beerServiceModel.getId());

        if (foundBeer == null || foundBeer.isEmpty() || !foundBeer.get().getName().equals(beerServiceModel.getName())) {
            throw new BeerNotFoundException("Beer not found");
        }

        Beer beer = this.modelMapper.map(beerServiceModel, Beer.class);

        if (beer.getBuyPrice() == null) {
            beer.setBuyPrice(BigDecimal.ZERO);
        }

        if (beer.getSellPrice() == null) {
            beer.setSellPrice(BigDecimal.ZERO);
        }

        beer.setId(foundBeer.get().getId());
        this.beerRepository.saveAndFlush(beer);
        return true;
    }
}