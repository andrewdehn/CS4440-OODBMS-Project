package edu.gatech.cs4440.spring2015.testing;

import edu.gatech.cs4440.spring2015.model.SimpleCar;
import java.sql.*;

public class MySQLTestModule implements DatabaseTestModule {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/Cars";

	Connection conn = null;
	Statement stmt = null;

	@Override
	public String databaseName() {
		// TODO Auto-generated method stub
		return "MySQL";
	}

	@Override
	public void setup() throws DatabaseTestException {
		// TODO Auto-generated method stub
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
			System.exit(1);
		}

		//System.out.println("Connecting to a selected database...");
		try
		{
			conn = DriverManager.getConnection(DB_URL,"root","");
			System.out.println("Connected database successfully...");
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void teardown() throws DatabaseTestException {
		// TODO Auto-generated method stub
		try{
			if(conn!=null)
				conn.close();
		}catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void clean() throws DatabaseTestException {
		// TODO 'delete from MyTable'
		//STEP 4: Execute a query
		//System.out.println("Creating statement...");
		try{
			stmt = conn.createStatement();
			String sql = "DELETE FROM Cars";
			stmt.executeUpdate(sql);
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void add(SimpleCar[] cars) throws DatabaseTestException {
		// TODO add all of cars in car array
		//STEP 4: Execute a query
		//System.out.println("Inserting records into the table...");
		try{
			stmt = conn.createStatement();

			for(int i=0; i< cars.length; i++)
			{
				String sql = "INSERT INTO cars " +
						"VALUES (\"" + cars[i].getVin() +"\",\"" + 
						cars[i].getMake() + "\",\"" + cars[i].getModel() + "\",\""
						+ cars[i].getColor() + "\"," + cars[i].getYear()+ ")";
				//System.out.println(sql);
				stmt.executeUpdate(sql);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}

	}

	@Override
	public void update(SimpleCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub
		// update where vin = cars[i].getVin 
		// set color = getColor, make = getMake, model = getModel, year = getYear

		//System.out.println("Creating statement...");
		try
		{
			stmt = conn.createStatement();
			for(int i = 0; i < cars.length; i++)
			{
				String sql = "UPDATE Cars " +
						"SET make = \"" + cars[i].getMake() + "\"" +
						", model = \"" + cars[i].getModel() + "\"" +
						", color = \"" + cars[i].getColor() + "\"" +
						", year = " + cars[i].getYear() +
						" WHERE VIN = \"" + cars[i].getVin() + "\"";
				stmt.executeUpdate(sql);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}

	}

	@Override
	public void query(SimpleCar[] cars) throws DatabaseTestException {
		//STEP 4: Execute a query
		//System.out.println("Creating statement...");

		try
		{
			stmt = conn.createStatement();
			ResultSet rs = null;
			for(int i=0; i < cars.length; i++)
			{
				String sql = "SELECT VIN, make, model, color, year FROM Cars WHERE VIN = \"" 
						+ cars[i].getVin() + "\"";
				rs = stmt.executeQuery(sql);
				//STEP 5: Extract data from result set
				rs.next();
				//Retrieve by column name
				String VIN  = rs.getString("VIN");
				int year = rs.getInt("year");
				String make = rs.getString("make");
				String model = rs.getString("model");
				String color = rs.getString("color");

				//Display values
				//System.out.print("VIN: " + VIN);
				//System.out.print(", Make: " + make);
				//System.out.print(", Model: " + model);
				//System.out.println(", Color: " + color);
				//System.out.println(", Year: " + year);
			}
			rs.close();
		} catch (SQLException e){
			throw new DatabaseTestException(e);
		}

	}

	@Override
	public void remove(SimpleCar[] cars) throws DatabaseTestException {
		// TODO for entire array, delete each row
		//STEP 4: Execute a query
		//System.out.println("Creating statement...");
		try
		{
			stmt = conn.createStatement();

			for(int i =0; i < cars.length; i++)
			{
				String sql = "DELETE FROM Cars " +
						"WHERE VIN = \"" + cars[i].getVin() + "\"";
				stmt.executeUpdate(sql);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
	}

}


