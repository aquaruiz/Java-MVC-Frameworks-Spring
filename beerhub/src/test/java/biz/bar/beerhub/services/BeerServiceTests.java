package biz.bar.beerhub.services;

import io.bar.beerhub.data.models.Beer;
import io.bar.beerhub.data.repositories.BeerRepository;
import io.bar.beerhub.errors.BeerNotFoundException;
import io.bar.beerhub.services.factories.BeerService;
import io.bar.beerhub.services.models.BeerServiceModel;
import io.bar.beerhub.services.services.BeerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceTests {
    BeerRepository beerRepositoryMock;
    ModelMapper modelMapper;

    BeerService beerService;

    @Before
    public void before() {
        this.beerRepositoryMock = Mockito.mock(BeerRepository.class);
        this.modelMapper = new ModelMapper();

        this.beerService = new BeerServiceImpl(beerRepositoryMock, modelMapper);
    }

    @Test
    public void save_WhenCorrectBeer_shouldReturnBeerModel() {
        BeerServiceModel beerServiceModel = new BeerServiceModel();
        Beer beer = this.modelMapper.map(beerServiceModel, Beer.class);

        when(beerRepositoryMock.saveAndFlush(any(Beer.class))).thenReturn(beer);

        BeerServiceModel result = this.beerService.save(beerServiceModel);

        Assert.assertThat(result, samePropertyValuesAs(beerServiceModel));
    }

    @Test
    public void save_WhenListBeer_shouldReturnListOfBeerModel() {
        BeerServiceModel beerServiceModel = new BeerServiceModel();
        List<BeerServiceModel> beerServiceModelList = new ArrayList<>();
        beerServiceModelList.add(beerServiceModel);

        Beer beer = this.modelMapper.map(beerServiceModel, Beer.class);
        List<Beer> beerList = new ArrayList<>();
        beerList.add(beer);

        when(beerRepositoryMock.saveAll(anyList())).thenReturn(beerList);

        List<BeerServiceModel> result = this.beerService.save(beerServiceModelList);

        Assert.assertThat(result.get(0), samePropertyValuesAs(beerServiceModelList.get(0)));
    }

    @Test
    public void getAllBeers_WhenNoBeers_shouldReturnEmptyList() {
        List<BeerServiceModel> emptyModelsList = new ArrayList<>();
        when(beerRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        List<BeerServiceModel> result = this.beerService.getAllBeers();

        Assert.assertEquals(emptyModelsList, result);
    }

    @Test
    public void getAllBeers_WhenBeers_shouldReturnBeerList() {
        BeerServiceModel beerServiceModel = new BeerServiceModel();
        List<BeerServiceModel> beerServiceModelList = new ArrayList<>();
        beerServiceModelList.add(beerServiceModel);

        Beer beer = this.modelMapper.map(beerServiceModel, Beer.class);
        List<Beer> beerList = new ArrayList<>();
        beerList.add(beer);

        when(beerRepositoryMock.findAll()).thenReturn(beerList);

        List<BeerServiceModel> result = this.beerService.getAllBeers();

        Assert.assertThat(beerServiceModelList.get(0), samePropertyValuesAs(result.get(0)));
    }

    @Test(expected = BeerNotFoundException.class)
    public void findOneById_WhenNoBeer_shouldThrowBeerNotFoundException() {
        when(beerRepositoryMock.findById(any(String.class))).thenReturn(Optional.empty());

        BeerServiceModel result = this.beerService.findOneById("0000-0000");
    }

    @Test
    public void findOneById_WhenBeerInDb_shouldReturnBeerModel() {
        when(beerRepositoryMock.findById(any(String.class))).thenReturn(Optional.of(new Beer()));

        BeerServiceModel result = this.beerService.findOneById("0000-0000");

        Assert.assertThat(result, samePropertyValuesAs(new BeerServiceModel()));
    }

    @Test
    public void findAllRunoutsBeers_WhenNoRunoutBeers_shouldReturnEmptyList() {
        when(beerRepositoryMock.findAllByQuantityLessThan(any(Long.class))).thenReturn(new ArrayList<>());

        List<BeerServiceModel> result = this.beerService.findAllRunoutsBeers(2l);

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void findAllRunoutsBeers_WhenRunoutBeers_shouldReturnListWithRunouts() {
        when(beerRepositoryMock.findAllByQuantityLessThan(any(Long.class))).thenReturn(List.of(new Beer()));

        List<BeerServiceModel> result = this.beerService.findAllRunoutsBeers(2l);

        Assert.assertThat(result.get(0), samePropertyValuesAs(new BeerServiceModel()));
    }

    @Test
    public void findAllBeersOnStock_WhenNoBeersOnStock_shouldReturnEmptyList() {
        when(beerRepositoryMock.findAllByQuantityGreaterThan(any(Long.class))).thenReturn(new ArrayList<>());

        List<BeerServiceModel> result = this.beerService.findAllBeersOnStock();

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void findAllBeersOnStock_WhenBeersOnStock_shouldReturnListWithBeers() {
        when(beerRepositoryMock.findAllByQuantityGreaterThan(any(Long.class))).thenReturn(List.of(new Beer()));

        List<BeerServiceModel> result = this.beerService.findAllBeersOnStock();

        Assert.assertThat(result.get(0), samePropertyValuesAs(new BeerServiceModel()));
    }

    @Test(expected = BeerNotFoundException.class)
    public void buyBeer_WhenNoBeer_shouldThrowBeerNotFoundException() {
        BeerServiceModel beerServiceModel = new BeerServiceModel();
        beerServiceModel.setId("0000-0000");

        when(beerRepositoryMock.findById(any(String.class))).thenReturn(Optional.empty());

        boolean result = this.beerService.buyBeer(beerServiceModel, 2L);
    }

    @Test(expected = BeerNotFoundException.class)
    public void buyBeer_WhenNoCorrectNamedBeer_shouldThrowBeerNotFoundException() {
        BeerServiceModel beerServiceModel = new BeerServiceModel();
        beerServiceModel.setId("0000-0000");
        beerServiceModel.setName("Beck's");

        Beer beer = new Beer(){{
            setId("0000-0000");
            setName("Kozel");
        }};

        when(beerRepositoryMock.findById(any(String.class))).thenReturn(Optional.of(beer));

        boolean result = this.beerService.buyBeer(beerServiceModel, 2L);
    }

    @Test
    public void buyBeer_WhenNegativeQuantity_shouldReturnFalse() {
        BeerServiceModel beerServiceModel = new BeerServiceModel();
        beerServiceModel.setId("0000-0000");
        beerServiceModel.setName("Beck's");

        Beer beer = new Beer(){{
            setId("0000-0000");
            setName("Beck's");
        }};

        when(beerRepositoryMock.findById(any(String.class))).thenReturn(Optional.of(beer));

        boolean result = this.beerService.buyBeer(beerServiceModel, -2L);

        Assert.assertFalse(result);
    }

    @Test
    public void buyBeer_WhenOkQuantity_shouldReturnTrue() {
        BeerServiceModel beerServiceModel = new BeerServiceModel();
        beerServiceModel.setId("0000-0000");
        beerServiceModel.setName("Beck's");
        beerServiceModel.setQuantity(0L);

        Beer beer = new Beer(){{
            setId("0000-0000");
            setName("Beck's");
            setQuantity(0L);
        }};

        when(beerRepositoryMock.findById(any(String.class))).thenReturn(Optional.of(beer));

        boolean result = this.beerService.buyBeer(beerServiceModel, 1L);

        Assert.assertTrue(result);
    }
}
