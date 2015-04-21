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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class StatisticsGrapher {

	static final int width = 640;
	static final int height = 480;

	String dbName;
	
	String dataName;

	Set<String> names;
	Set<Integer> numbers = new HashSet<>();

	Map<String, Map<Integer, Double>> addMeans = new HashMap<>();
	Map<String, Map<Integer, Double>> addSDs = new HashMap<>();

	Map<String, Map<Integer, Double>> updateMeans = new HashMap<>();
	Map<String, Map<Integer, Double>> updateSDs = new HashMap<>();

	Map<String, Map<Integer, Double>> queryMeans = new HashMap<>();
	Map<String, Map<Integer, Double>> querySDs = new HashMap<>();

	Map<String, Map<Integer, Double>> removeMeans = new HashMap<>();
	Map<String, Map<Integer, Double>> removeSDs = new HashMap<>();

	public StatisticsGrapher(String dataName, String[] dbNames) {
		this.dataName = dataName;
		names = new HashSet<>();
		for(String name : dbNames) {
			names.add(name);

			addMeans.put(name, new HashMap<Integer, Double>());
			addSDs.put(name, new HashMap<Integer, Double>());

			updateMeans.put(name, new HashMap<Integer, Double>());
			updateSDs.put(name, new HashMap<Integer, Double>());

			queryMeans.put(name, new HashMap<Integer, Double>());
			querySDs.put(name, new HashMap<Integer, Double>());

			removeMeans.put(name, new HashMap<Integer, Double>());
			removeSDs.put(name, new HashMap<Integer, Double>());
		}
	}

	public void addTestResults(String dbName, int objNum, TestResult result) {
		numbers.add(objNum);

		addMeans.get(dbName).put(objNum, result.getAddMean());
		addSDs.get(dbName).put(objNum, result.getAddSD());

		updateMeans.get(dbName).put(objNum, result.getUpdateMean());
		updateSDs.get(dbName).put(objNum, result.getUpdateSD());

		queryMeans.get(dbName).put(objNum, result.getQueryMean());
		querySDs.get(dbName).put(objNum, result.getQuerySD());

		removeMeans.get(dbName).put(objNum, result.getRemoveMean());
		removeSDs.get(dbName).put(objNum, result.getRemoveSD());
	}

	public void graph() throws IOException {
		XYSeriesCollection addMeanDataset = new XYSeriesCollection();
		XYSeriesCollection updateMeanDataset = new XYSeriesCollection();
		XYSeriesCollection queryMeanDataset = new XYSeriesCollection();
		XYSeriesCollection removeMeanDataset = new XYSeriesCollection();
		
		for(String dbName : names) {
			XYSeries addMeanSeries = new XYSeries(dbName);
			XYSeries updateMeanSeries = new XYSeries(dbName);
			XYSeries queryMeanSeries = new XYSeries(dbName);
			XYSeries removeMeanSeries = new XYSeries(dbName);
			
			for(Integer objNum : numbers) {
				addMeanSeries.add(objNum, addMeans.get(dbName).get(objNum));
				updateMeanSeries.add(objNum, updateMeans.get(dbName).get(objNum));
				queryMeanSeries.add(objNum, queryMeans.get(dbName).get(objNum));
				removeMeanSeries.add(objNum, removeMeans.get(dbName).get(objNum));
			}
			
			addMeanDataset.addSeries(addMeanSeries);
			updateMeanDataset.addSeries(updateMeanSeries);
			queryMeanDataset.addSeries(queryMeanSeries);
			removeMeanDataset.addSeries(removeMeanSeries);
		}
		
		JFreeChart addMeanLineChart = ChartFactory.createXYLineChart(
				"Average mean add runtime", 
				"Number of objects", "Runtime Mean (ms)", 
				addMeanDataset, 
				PlotOrientation.VERTICAL, true, true, false);
		
		JFreeChart updateMeanLineChart = ChartFactory.createXYLineChart(
				"Average mean update runtime", 
				"Number of objects", "Runtime Mean (ms)", 
				updateMeanDataset, 
				PlotOrientation.VERTICAL, true, true, false);
		
		JFreeChart queryMeanLineChart = ChartFactory.createXYLineChart(
				"Average mean query runtime", 
				"Number of objects", "Runtime Mean (ms)", 
				queryMeanDataset, 
				PlotOrientation.VERTICAL, true, true, false);
		
		JFreeChart removeMeanLineChart = ChartFactory.createXYLineChart(
				"Average mean remove runtime", 
				"Number of objects", "Runtime Mean (ms)", 
				removeMeanDataset, 
				PlotOrientation.VERTICAL, true, true, false);
		
		File addFile = new File(dataName + "_add_mean.jpeg");
		File updateFile = new File(dataName + "_update_mean.jpeg");
		File queryFile = new File(dataName + "_query_mean.jpeg");
		File removeFile = new File(dataName + "_remove_mean.jpeg");
		
		ChartUtilities.saveChartAsJPEG(addFile, addMeanLineChart, width, height);
		ChartUtilities.saveChartAsJPEG(updateFile, updateMeanLineChart, width, height);
		ChartUtilities.saveChartAsJPEG(queryFile, queryMeanLineChart, width, height);
		ChartUtilities.saveChartAsJPEG(removeFile, removeMeanLineChart, width, height);
	}
	
	public void graph2() throws IOException {
		DefaultCategoryDataset addMeanDataset = new DefaultCategoryDataset();
		DefaultCategoryDataset updateMeanDataset = new DefaultCategoryDataset();
		DefaultCategoryDataset queryMeanDataset = new DefaultCategoryDataset();
		DefaultCategoryDataset removeMeanDataset = new DefaultCategoryDataset();

		for(Integer objNum : numbers) {
			for(String dbName : names) {
				addMeanDataset.addValue(addMeans.get(dbName).get(objNum), dbName, Integer.toString(objNum));
				updateMeanDataset.addValue(updateMeans.get(dbName).get(objNum), dbName, Integer.toString(objNum));
				queryMeanDataset.addValue(queryMeans.get(dbName).get(objNum), dbName, Integer.toString(objNum));
				removeMeanDataset.addValue(removeMeans.get(dbName).get(objNum), dbName, Integer.toString(objNum));
			}
		}

		JFreeChart addMeanLineChart = ChartFactory.createLineChart("Add test results", "Size of data", "Runtime Mean", addMeanDataset);
		JFreeChart updateMeanLineChart = ChartFactory.createLineChart("Update test results", "Size of data", "Runtime Mean", updateMeanDataset);
		JFreeChart queryMeanLineChart = ChartFactory.createLineChart("Query test results", "Size of data", "Runtime Mean", queryMeanDataset);
		JFreeChart removeMeanLineChart = ChartFactory.createLineChart("Remove test results", "Size of data", "Runtime Mean", removeMeanDataset);

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
