package biz.bar.beerhub.services;

import io.bar.beerhub.data.models.Cash;
import io.bar.beerhub.data.repositories.CashRepository;
import io.bar.beerhub.services.factories.CashService;
import io.bar.beerhub.services.services.CashServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CashServiceTests {
    private final BigDecimal INITIAL_CASH = BigDecimal.valueOf(10000);
    CashRepository cashRepositoryMock;
    CashService cashService;
    Cash currentCash;

    @Before
    public void before() {
        this.cashRepositoryMock = Mockito.mock(CashRepository.class);
        this.cashService = new CashServiceImpl(cashRepositoryMock);
        currentCash = new Cash(){{
            setQuantity(INITIAL_CASH);
        }};
    }

    @Test
    public void spendMoney_withGivenAmount_shouldAddItToDbCash() {
        BigDecimal cashToSpend = BigDecimal.valueOf(50);

        List<Cash> cashList = new ArrayList<>();
        cashList.add(currentCash);
        when(cashRepositoryMock.findAll()).thenReturn(cashList);

        boolean result = cashService.spendMoney(cashToSpend);

        Assert.assertEquals(true, result);
    }

    @Test
    public void spendMoney_withTooBigAmount_shouldReturnFalse() {
        BigDecimal cashToSpend = INITIAL_CASH.multiply(BigDecimal.valueOf(2));

        List<Cash> cashList = new ArrayList<>();
        cashList.add(currentCash);
        when(cashRepositoryMock.findAll()).thenReturn(cashList);

        boolean result = cashService.spendMoney(cashToSpend);

        Assert.assertEquals(false, result);
    }
    @Test
    public void collectMoney_withAnyMoneyAmount_ShouldAddThemToDbAmount() {
        BigDecimal cashToCollect = BigDecimal.valueOf(5);

        List<Cash> cashList = new ArrayList<>();
        cashList.add(currentCash);
        BigDecimal total = currentCash.getQuantity().add(cashToCollect);

        Cash cash = new Cash(){{
            setQuantity(total);
        }};

        when(cashRepositoryMock.findAll()).thenReturn(cashList);
        when(cashRepositoryMock.saveAndFlush(any(Cash.class))).thenReturn(cash);


        cashService.collectMoney(cashToCollect);

        Assert.assertEquals(total, cash.getQuantity());
    }
}
