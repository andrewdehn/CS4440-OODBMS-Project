package edu.gatech.cs4440.spring2015;

public class TestResult {
	
	double addMean;
	double addSD;
	
	double updateMean;
	double updateSD;
	
	double queryMean;
	double querySD;
	
	double removeMean;
	double removeSD;
	
	public void setAdd(double mean, double sd) {
		addMean = mean;
		addSD = sd;
	}
	
	public void setUpdate(double mean, double sd) {
		updateMean = mean;
		updateSD = sd;
	}
	
	public void setQuery(double mean, double sd) {
		queryMean = mean;
		querySD = sd;
	}
	
	public void setRemove(double mean, double sd) {
		removeMean = mean;
		removeSD = sd;
	}

	public double getAddMean() {
		return addMean;
	}

	public double getAddSD() {
		return addSD;
	}

	public double getUpdateMean() {
		return updateMean;
	}

	public double getUpdateSD() {
		return updateSD;
	}

	public double getQueryMean() {
		return queryMean;
	}

	public double getQuerySD() {
		return querySD;
	}

	public double getRemoveMean() {
		return removeMean;
	}

	public double getRemoveSD() {
		return removeSD;
	}
	
}