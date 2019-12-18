package io.bar.beerhub.services.factories;

import java.math.BigDecimal;

public interface CashService {
    void initCashInDb();

    boolean spendMoney(BigDecimal money);

    void collectMoney(BigDecimal money);

    public BigDecimal getCurrentCash();
}
