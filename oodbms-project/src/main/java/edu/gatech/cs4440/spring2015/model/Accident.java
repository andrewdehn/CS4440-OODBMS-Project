package edu.gatech.cs4440.spring2015.model;

public class Accident {
	
	protected String date;
	protected String company;
	protected double claim;

	public Accident(String date, String company, double claim) {
		this.date = date;
		this.company = company;
		this.claim = claim;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public double getClaim() {
		return claim;
	}

	public void setClaim(double claim) {
		this.claim = claim;
	}

}
