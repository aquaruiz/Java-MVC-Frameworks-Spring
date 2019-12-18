package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Cash;
import io.bar.beerhub.data.repositories.CashRepository;
import io.bar.beerhub.services.factories.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CashServiceImpl implements CashService {
    private final CashRepository cashRepository;

    @Autowired
    public CashServiceImpl(CashRepository cashRepository) {
        this.cashRepository = cashRepository;
    }

    @Override
    public void initCashInDb() {
        Cash cash = new Cash();
        cash.setQuantity(BigDecimal.valueOf(10000));
        this.cashRepository.saveAndFlush(cash);
    }

    @Override
    public boolean spendMoney(BigDecimal money) {
        Cash currentCash = this.cashRepository.findAll().get(0);

        if (currentCash.getQuantity().compareTo(money) >= 0) {
            BigDecimal cash = currentCash.getQuantity().subtract(money);
            this.cashRepository.saveAndFlush(currentCash);
            return true;
        }

        return false;
    }

    @Override
    public void collectMoney(BigDecimal money) {
        List<Cash> currentCashes = this.cashRepository.findAll();
        Cash currentCash = null;

        if (currentCashes.size() == 0) {
            currentCash = new Cash();
            currentCash.setQuantity(BigDecimal.valueOf(10000));
        }

        currentCash = currentCashes.get(0);
        BigDecimal calc = currentCash.getQuantity().add(money);
        currentCash.setQuantity(calc);
        this.cashRepository.saveAndFlush(currentCash);
    }

    public BigDecimal getCurrentCash() {
        List<Cash> currentCashes = this.cashRepository.findAll();
        Cash currentCash = null;

        if (currentCashes.size() == 0) {
            currentCash = new Cash();
            currentCash.setQuantity(BigDecimal.valueOf(10000));
        }

        currentCash = currentCashes.get(0);
        return currentCash.getQuantity();
    }
}
