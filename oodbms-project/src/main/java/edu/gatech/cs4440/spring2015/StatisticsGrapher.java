package edu.gatech.cs4440.spring2015;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticsGrapher {
	
	static final int width = 640;
	static final int height = 480;
	
	String dbName;
	int runs;
	
	Set<Integer> numbers = new HashSet<>();
	
	Map<Integer, Double> addMeans = new HashMap<>();
	Map<Integer, Double> addSDs = new HashMap<>();
	
	Map<Integer, Double> updateMeans = new HashMap<>();
	Map<Integer, Double> updateSDs = new HashMap<>();
	
	Map<Integer, Double> queryMeans = new HashMap<>();
	Map<Integer, Double> querySDs = new HashMap<>();
	
	Map<Integer, Double> removeMeans = new HashMap<>();
	Map<Integer, Double> removeSDs = new HashMap<>();

	public StatisticsGrapher(String dbName, int runs) {
		this.dbName = dbName;
		this.runs = runs;
	}
	
	public void addTestResults(int objNum, DatabaseTest.TestResult result) {
		numbers.add(objNum);
		
		addMeans.put(objNum, result.getAddMean());
		addSDs.put(objNum, result.getAddSD());
		
		updateMeans.put(objNum, result.getUpdateMean());
		updateSDs.put(objNum, result.getUpdateSD());
		
		queryMeans.put(objNum, result.getQueryMean());
		querySDs.put(objNum, result.getQuerySD());
		
		removeMeans.put(objNum, result.getRemoveMean());
		removeSDs.put(objNum, result.getRemoveSD());
	}
	
	public void graph() throws IOException {
		DefaultCategoryDataset addMeanDataset = new DefaultCategoryDataset();
		DefaultCategoryDataset updateMeanDataset = new DefaultCategoryDataset();
		DefaultCategoryDataset queryMeanDataset = new DefaultCategoryDataset();
		DefaultCategoryDataset removeMeanDataset = new DefaultCategoryDataset();
		
		for(Integer objNum : numbers) {
			addMeanDataset.addValue(addMeans.get(objNum), "mean", Integer.toString(objNum));
			updateMeanDataset.addValue(updateMeans.get(objNum), "mean", Integer.toString(objNum));
			queryMeanDataset.addValue(queryMeans.get(objNum), "mean", Integer.toString(objNum));
			removeMeanDataset.addValue(removeMeans.get(objNum), "mean", Integer.toString(objNum));
		}
		
		JFreeChart addMeanLineChart = ChartFactory.createLineChart(dbName + " add test results", "Size of data", "Runtime Mean", addMeanDataset);
		JFreeChart updateMeanLineChart = ChartFactory.createLineChart(dbName + " update test results", "Size of data", "Runtime Mean", updateMeanDataset);
		JFreeChart queryMeanLineChart = ChartFactory.createLineChart(dbName + " query test results", "Size of data", "Runtime Mean", queryMeanDataset);
		JFreeChart removeMeanLineChart = ChartFactory.createLineChart(dbName + " remove test results", "Size of data", "Runtime Mean", removeMeanDataset);
		
		File addFile = new File(dbName + "_add_mean.jpeg");
		File updateFile = new File(dbName + "_update_mean.jpeg");
		File queryFile = new File(dbName + "_query_mean.jpeg");
		File removeFile = new File(dbName + "_remove_mean.jpeg");
		
		ChartUtilities.saveChartAsJPEG(addFile, addMeanLineChart, width, height);
		ChartUtilities.saveChartAsJPEG(updateFile, updateMeanLineChart, width, height);
		ChartUtilities.saveChartAsJPEG(queryFile, queryMeanLineChart, width, height);
		ChartUtilities.saveChartAsJPEG(removeFile, removeMeanLineChart, width, height);
	}
	
	

}
