package io.bar.beerhub.data.repositories;

import io.bar.beerhub.data.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
    List<Log> findAllByUsername(String username);

    List<Log> findAllByOrderByTimeDesc();
}
