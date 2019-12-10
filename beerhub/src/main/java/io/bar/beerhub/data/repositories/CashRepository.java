package io.bar.beerhub.data.repositories;

import io.bar.beerhub.data.models.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CashRepository extends JpaRepository<Cash, String> {
}
