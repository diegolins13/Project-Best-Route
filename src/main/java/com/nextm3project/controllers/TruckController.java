package com.nextm3project.controllers;

import java.net.URISyntaxException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextm3project.bestRoute.BestRoute;
import com.nextm3project.bestRoute.Distance;
import com.nextm3project.dtos.TruckDto;
import com.nextm3project.models.TruckModel;
import com.nextm3project.services.TruckService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)	//Permitindo que seja acessado de qualquer fonte.
@RequestMapping("/truck") 					// Criando o Mapping (URI) a nível de classe
public class TruckController {

	final TruckService truckService;
	
	public TruckController(TruckService truckService) {	
		this.truckService = truckService;
	}
	
	//POST
	@PostMapping
    public ResponseEntity<Object> saveTruck(@RequestBody @Valid TruckDto truckDto) throws URISyntaxException{		
		try {
			validation(truckDto);
		}catch (InvalidParameterException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		if(truckService.existsByLicensePlateTruck(truckDto.getLicensePlateTruck())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Truck License Plate is already in use!");
        }        
        var truckModel = new TruckModel();	
        BeanUtils.copyProperties(truckDto, truckModel);
        truckModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        truckModel.setRoute(BestRoute.routeCalc(truckModel.getStatus(), truckModel.getLocation()));
        return ResponseEntity.status(HttpStatus.CREATED).body(truckService.save(truckModel));
    }
	
	//GET All
	@GetMapping
	public ResponseEntity<List<TruckModel>> getAllTruck(){
		return ResponseEntity.status(HttpStatus.OK).body(truckService.findAll());
	}
	
	//GET ONE
	@GetMapping("/{licensePlateTruck}")
	public ResponseEntity<Object> getOneTruck(@PathVariable(value = "licensePlateTruck") String licensePlateTruck){
		Optional<TruckModel> truckModelOptional = truckService.findByLicensePlateTruck(licensePlateTruck);			
		if (!truckModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Truck not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(truckModelOptional.get());
	}
	
	//GET ROUTE
	@GetMapping("/route/{licensePlateTruck}")
	public ResponseEntity<Object> getRoute(@PathVariable(value = "licensePlateTruck") String licensePlateTruck) throws URISyntaxException{
		Optional<TruckModel> truckModelOptional = truckService.findByLicensePlateTruck(licensePlateTruck);			
		if (!truckModelOptional.isPresent()) {																		
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Truck not found.");							
		}
		var truckModel = truckModelOptional.get();
		var distance = new Distance();
		distance.setCost(Distance.distanceCalc(truckModel.getStatus(), truckModel.getLocation()));
		return ResponseEntity.status(HttpStatus.OK).body(" route: " + truckModel.getRoute() + "\n distance: " + distance.getCost() + "m");
	}
	
	//PUT
	@PutMapping("/{licensePlateTruck}")
	public ResponseEntity<Object> updateTruck(@PathVariable(value = "licensePlateTruck") String licensePlateTruck, @RequestBody @Valid TruckDto truckDto) throws URISyntaxException{
		try {
			validation(truckDto);
		}catch (InvalidParameterException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		Optional<TruckModel> truckModelOptional = truckService.findByLicensePlateTruck(licensePlateTruck);
		if (!truckModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Truck not found.");
		}
		var truckModel = truckModelOptional.get();
		truckModel.setStatus(truckDto.getStatus());
		truckModel.setLocation(truckDto.getLocation());
		truckModel.setRoute(BestRoute.routeCalc(truckModel.getStatus(), truckModel.getLocation()));
		return ResponseEntity.status(HttpStatus.OK).body(truckService.save(truckModel));
	}
	
	//DELETE
	@DeleteMapping("/{licensePlateTruck}")  															
	public ResponseEntity<Object> deleteTruck(@PathVariable(value = "licensePlateTruck") String licensePlateTruck){
		Optional<TruckModel> truckModelOptional = truckService.findByLicensePlateTruck(licensePlateTruck);
		if (!truckModelOptional.isPresent()) {	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Truck not found.");
		}
		truckService.delete(truckModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Truck deleted successfully.");
	}

	
	public void validation(final TruckDto truckDto){

		if(!truckService.validationStatusTruck(truckDto.getStatus())){
			throw new InvalidParameterException("Conflict: Status invalid!");
		}

		if(!truckService.validationLocationTruck(truckDto.getLocation())){
			throw new InvalidParameterException("Conflict: Location invalid!");
		}
	}
}