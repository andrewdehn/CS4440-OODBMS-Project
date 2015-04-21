package edu.gatech.cs4440.spring2015.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class ComplexCar extends SimpleCar {
	
	@OneToMany private List<Accident> accidents = new ArrayList<>();
	@OneToMany private List<Maintenance> maintenance = new ArrayList<>();

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accidents == null) ? 0 : accidents.hashCode());
		result = prime * result
				+ ((maintenance == null) ? 0 : maintenance.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ComplexCar))
			return false;
		ComplexCar other = (ComplexCar) obj;
		if (accidents == null) {
			if (other.accidents != null)
				return false;
		} else if (!accidents.equals(other.accidents))
			return false;
		if (maintenance == null) {
			if (other.maintenance != null)
				return false;
		} else if (!maintenance.equals(other.maintenance))
			return false;
		return true;
	}
	
	

}