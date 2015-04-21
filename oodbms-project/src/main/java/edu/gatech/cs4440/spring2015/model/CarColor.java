package edu.gatech.cs4440.spring2015.model;


public enum CarColor {
	BLACK("BLACK"),
	WHITE("WHITE"),
	YELLOW("YELLOW"),
	RED("RED"),
	BLUE("BLUE"),
	SILVER("SILVER"),
	GRAY("GRAY"),
	GREEN("GREEN");
	
	private final String value;
	
	private CarColor(String color){
		value = color;
	}
	public String getValue(){
		return value;
	}
}