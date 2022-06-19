package com.nextm3project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nextm3project.models.TruckModel;

@Repository
public interface TruckRepository extends JpaRepository<TruckModel, Integer> {
	
	// Declarando os métodos aqui no Repository para serem chamados dentro do Service.
	
	boolean existsByLicensePlateTruck(String licensePlateTruck);
	Optional<TruckModel> findByLicensePlateTruck(String licensePlateTruck); 
		
}