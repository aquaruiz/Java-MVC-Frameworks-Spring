package io.bar.beerhub.data.repositories;

import io.bar.beerhub.data.models.Waitress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaitressRepository extends JpaRepository<Waitress, String> {
    List<Waitress> findAllByOrderByTipsRateAsc();

    List<Waitress> findAllByOrderByTipsRateDesc();

    Waitress findByName(String name);
}
