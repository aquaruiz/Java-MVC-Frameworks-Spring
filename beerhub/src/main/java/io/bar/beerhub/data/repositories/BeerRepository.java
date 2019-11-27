package io.bar.beerhub.data.repositories;

import io.bar.beerhub.data.models.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeerRepository extends JpaRepository <Beer, String> {
    List<Beer> findAllByQuantityLessThan(long num);
}
