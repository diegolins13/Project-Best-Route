package com.nextm3project.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TRUCK")
//Serializable + serialVersionUID serve para fazer o controle das versões no JVM.
public class TruckModel implements Serializable {  
	private static final long serialVersionUID = 1L;
	
	@Id
	@JsonIgnore										
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 7)
	private String licensePlateTruck;
	
	@Column(nullable = false, length = 5)
	private String status;
	
	@Column(nullable = false, length = 5)
	private String location;
	
	@JsonIgnore
	@Column(nullable = false)
	private LocalDateTime registrationDate;
	
	@Column
	private String route;
	

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicensePlateTruck() {
		return licensePlateTruck;
	}

	public void setLicensePlateTruck(String licensePlateTruck) {
		this.licensePlateTruck = licensePlateTruck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
}