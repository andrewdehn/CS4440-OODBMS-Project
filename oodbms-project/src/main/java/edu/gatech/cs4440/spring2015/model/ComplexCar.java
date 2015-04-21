package edu.gatech.cs4440.spring2015.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ComplexCar extends SimpleCar {
	
	private List<Accident> accidents = new ArrayList<>();
	private List<Maintenance> maintenance = new ArrayList<>();

	public ComplexCar(String make, String model, int year, String vin, CarColor color) {
		super(make, model, year, vin, color);
	}

	public List<Accident> getAccidents() {
		return accidents;
	}

	public void setAccidents(List<Accident> accidents) {
		this.accidents = accidents;
	}

	public List<Maintenance> getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(List<Maintenance> maintenance) {
		this.maintenance = maintenance;
	}
	
	

}