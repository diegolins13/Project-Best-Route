package com.nextm3project.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//Criado para receber e fazer as validações dos dados recebidos,
//pois se tiver dados errados, devem ser descartados e enviar uma bad request para o cliente.

public class TruckDto {

		
	@NotBlank							//Verifica se o campo não está nulo, se tem string vazia e etc.
	@Size(max = 7)						//Verifica se tem o máximo de 7 caracteres
	private String licensePlateTruck;
	
	@NotBlank
	@Size(max = 5)
	private String status;
	
	@NotBlank
	@Size(max = 10)
	private String location;
	
	//Getters e Setters criado de forma automatica
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
}
