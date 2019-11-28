package io.bar.beerhub.data.repositories;

import io.bar.beerhub.data.models.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, String> {
}
