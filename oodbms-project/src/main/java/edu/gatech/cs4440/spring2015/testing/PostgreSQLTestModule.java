package edu.gatech.cs4440.spring2015.testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.gatech.cs4440.spring2015.model.SimpleCar;

public class PostgreSQLTestModule implements DatabaseTestModule {
	// JDBC driver name and database URL
//	static final String JDBC_DRIVER = "";  
//	static final String DB_URL = "";
	public Connection con = null;
	public Statement st = null;
	
	ResultSet rs = null;
	java.sql.PreparedStatement pst = null;
	public static final String DB_NAME = "PostgreSQLCars";
	
	String url = "jdbc:postgresql://localhost:5432/"+ DB_NAME;
    String user = "postgres";
    String password = "12345";
	@Override
	public String databaseName() {
		// TODO Auto-generated method stub
		return DB_NAME;
	}

	@Override
	public void setup() throws DatabaseTestException {
        try {
        	pst = null;
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void teardown() throws DatabaseTestException {
		try{
			if(con!=null)
				con.close();
		}catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}

	}

	@Override
	public void clean() throws DatabaseTestException {
		try{
			
			pst = con.prepareStatement("DELETE FROM Cars");
			pst.executeUpdate();
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void add(SimpleCar[] cars) throws DatabaseTestException {
		try{
			for(int i=0; i< cars.length; i++)
			{
				String stm = "INSERT INTO Cars " +
						"VALUES (\'" + cars[i].getVin() +"\',\'" + 
						cars[i].getMake() + "\',\'" + cars[i].getModel() + "\',\'"
						+ cars[i].getColor() + "\'," + cars[i].getYear()+ ")";
//				String stm = "INSERT INTO cars DEFAULT VALUES";
//				pst = con.prepareStatement(stm);
//				pst.executeUpdate();
				st = con.createStatement();
				st.executeUpdate(stm);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}

	}

	@Override
	public void update(SimpleCar[] cars) throws DatabaseTestException {
		try {
			for(int i = 0; i < cars.length; i++) {
				PreparedStatement st = con.prepareStatement("UPDATE Cars SET make = ?, model = ?, color = ?, year = ? WHERE VIN = ?");
				st.setString(1,cars[i].getMake());
				st.setString(2,cars[i].getModel());
				st.setString(3,cars[i].getColor().getValue());
				st.setInt(4, cars[i].getYear());
				st.setString(5, cars[i].getVin());
				st.executeUpdate();
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}

	}

	@Override
	public void query(SimpleCar[] cars) throws DatabaseTestException {
		try {
			st = con.createStatement();
			ResultSet rs = null;
			for(int i=0; i < cars.length; i++) {
				PreparedStatement sts = con.prepareStatement("SELECT VIN, make, model, color, year FROM Cars WHERE VIN = ?");
				sts.setString(1,cars[i].getVin());
				rs = sts.executeQuery();
				rs.next();
				String VIN  = rs.getString("VIN");
				int year = rs.getInt("year");
				String make = rs.getString("make");
				String model = rs.getString("model");
				String color = rs.getString("color");
				System.out.print("VIN: " + VIN);
				System.out.print(", Make: " + make);
				System.out.print(", Model: " + model);
				System.out.println(", Color: " + color);
				System.out.println(", Year: " + year);
			}			
			rs.close();			
		} catch(SQLException e) {
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void remove(SimpleCar[] cars) throws DatabaseTestException {
		try{
			st = con.createStatement();
			for(int i =0; i < cars.length; i++) {
				String sql = "DELETE FROM Cars " +
						"WHERE VIN = \'" + cars[i].getVin() + "\'";
				st.executeUpdate(sql);
				}
			}
		catch(SQLException e) {
			throw new DatabaseTestException(e);
		}
	}
}