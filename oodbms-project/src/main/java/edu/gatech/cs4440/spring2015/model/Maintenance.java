package edu.gatech.cs4440.spring2015.model;

public class Maintenance {
	
	protected String date;
	protected String vendor;
	protected double cost;

	public Maintenance(String date, String vendor, double cost) {
		this.date = date;
		this.vendor = vendor; 
		this.cost = cost;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

 }
