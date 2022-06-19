package com.nextm3project.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.nextm3project.bestRoute.RoutesPointsEnum;
import com.nextm3project.bestRoute.StatusTruckEnum;
import com.nextm3project.models.TruckModel;
import com.nextm3project.repositories.TruckRepository;

@Service
public class TruckService {

	//Criando ponto de injeção de dependencias do TrcukRepository
	
	final TruckRepository truckRepository;
	
	public TruckService(TruckRepository truckRepository) {							
		this.truckRepository = truckRepository;
	}
	
	@Transactional												    			
	public TruckModel save(TruckModel truckmodel) {				 
		return truckRepository.save(truckmodel);
	}
			
	public boolean existsByLicensePlateTruck(String licensePlateTruck) {
		return truckRepository.existsByLicensePlateTruck(licensePlateTruck);
	}

	public List<TruckModel> findAll() {
		return truckRepository.findAll();
	}

	public Optional<TruckModel> findByLicensePlateTruck(String licensePlateTruck) {
		return truckRepository.findByLicensePlateTruck(licensePlateTruck);
	}

	@Transactional																
	public void delete(TruckModel truckModel) {
		truckRepository.delete(truckModel);
	}
	
	public boolean validationStatusTruck(final String status){
		return StatusTruckEnum.check(status);
	}

	public boolean validationLocationTruck(final String location){
		return RoutesPointsEnum.check(location);
	}
		
}