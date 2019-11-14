package com.example.demo.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BeerRepository extends JpaRepository<Beer, Long> {
	List<Beer> findByBrand(String brand);
	List<Beer> findAll();

	@Query("select b from Beer b where b.brand like %?1")
	  List<Beer> findByBrandEndsWith(String brand);
}