package edu.gatech.cs4440.spring2015.testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import edu.gatech.cs4440.spring2015.model.Accident;
import edu.gatech.cs4440.spring2015.model.ComplexCar;
import edu.gatech.cs4440.spring2015.model.Maintenance;

public class MySQLComplexTestModule implements ComplexDatabaseTestModule {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/Cars";

	Connection conn = null;
	Statement stmt = null;

	@Override
	public String databaseName() {
		// TODO Auto-generated method stub
		return "MySQLComplex";
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
//			System.out.println("Connected database successfully...");
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
		// TODO Auto-generated method stub
		try{
			stmt = conn.createStatement();
			String sql = "DELETE FROM ComplexCar";
			stmt.executeUpdate(sql);
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void add(ComplexCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub
		// TODO add all of cars in car array
		//STEP 4: Execute a query
		//System.out.println("Inserting records into the table...");
		try{
			stmt = conn.createStatement();
			List<Accident> accidents;
			Accident accOccur;
			List<Maintenance> mnt;
			Maintenance mainOccur;
			
			PreparedStatement s1 = conn.prepareStatement("INSERT INTO ComplexCar VALUES (?, ?, ?, ?, ?);");
			PreparedStatement s2 = conn.prepareStatement("INSERT INTO Accident VALUES (?, ?, ?, ?);");
			PreparedStatement s3 = conn.prepareStatement("INSERT INTO Maintenance VALUES (?, ?, ?, ?);");

			for(int i=0; i< cars.length; i++)
			{
//				String sql = "INSERT INTO ComplexCar " +
//						"VALUES (\"" + cars[i].getVin() +"\",\"" + 
//						cars[i].getMake() + "\",\"" + cars[i].getModel() + "\",\""
//						+ cars[i].getColor() + "\"," + cars[i].getYear()+ ")";
//				//System.out.println(sql);
//				stmt.executeUpdate(sql);
				s1.setString(1, cars[i].getVin());
				s1.setString(2, cars[i].getMake());
				s1.setString(3, cars[i].getModel());
				s1.setString(4, cars[i].getColor().toString());
				s1.setInt(5, cars[i].getYear());
				s1.executeUpdate();

				accidents = cars[i].getAccidents();
				for(int j = 0; j< cars[i].getAccidents().size(); j++)
				{
					accOccur = accidents.get(j);
//					sql = "INSERT INTO Accident " +
//							"VALUES (\"" + accOccur.getDate() +"\",\"" + 
//							accOccur.getCompany() + "\"," + accOccur.getClaim() + ",\""
//							+ cars[i].getVin() + "\")";
//					//System.out.println(sql);
//					stmt.executeUpdate(sql);
					s2.setString(1, accOccur.getDate());
					s2.setString(2, accOccur.getCompany());
					s2.setDouble(3, accOccur.getClaim());
					s2.setString(4, cars[i].getVin());
					s2.executeUpdate();
				}

				mnt = cars[i].getMaintenance();
				for(int k=0;k<cars[i].getMaintenance().size();k++)
				{
					mainOccur = mnt.get(k);
//					sql = "INSERT INTO Maintenance " +
//							"VALUES (\"" + mainOccur.getDate() +"\",\"" + 
//							mainOccur.getVendor() + "\"," + mainOccur.getCost() + ",\""
//							+ cars[i].getVin() + "\")";
//					//System.out.println(sql);
//					stmt.executeUpdate(sql);
					s3.setString(1, mainOccur.getDate());
					s3.setString(2, mainOccur.getVendor());
					s3.setDouble(3, mainOccur.getCost());
					s3.setString(4, cars[i].getVin());
					s3.executeUpdate();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void update(ComplexCar[] cars) throws DatabaseTestException {
		// update where vin = cars[i].getVin 
		// set color = getColor, make = getMake, model = getModel, year = getYear

		//System.out.println("Creating statement...");
		try
		{
			stmt = conn.createStatement();
			List<Accident> accidents;
			Accident accOccur;
			List<Maintenance> mnt;
			Maintenance mainOccur;
			
			PreparedStatement s1 = conn.prepareStatement("UPDATE ComplexCar SET make = ?, model = ?, color = ?, year = ? WHERE VIN = ?;");
			PreparedStatement s2 = conn.prepareStatement("UPDATE Accident SET icompany = ?, iclaim = ? WHERE VIN = ? AND date = ?;");
			PreparedStatement s3 = conn.prepareStatement("UPDATE Maintenance SET vendor = ?, cost = ? WHERE VIN = ? AND date = ?;");

			for(int i = 0; i < cars.length; i++)
			{
//				String sql = "UPDATE ComplexCar " +
//						"SET make = \"" + cars[i].getMake() + "\"" +
//						", model = \"" + cars[i].getModel() + "\"" +
//						", color = \"" + cars[i].getColor() + "\"" +
//						", year = " + cars[i].getYear() +
//						" WHERE VIN = \"" + cars[i].getVin() + "\"";
//				//System.out.println(sql);
//				stmt.executeUpdate(sql);
				
				s1.setString(1, cars[i].getMake());
				s1.setString(2, cars[i].getModel());
				s1.setString(3, cars[i].getColor().toString());
				s1.setDouble(4, cars[i].getYear());
				s1.setString(5, cars[i].getVin());
				s1.executeUpdate();
				
				accidents = cars[i].getAccidents();
				for(int j=0; j<cars[i].getAccidents().size(); j++)
				{
					accOccur = accidents.get(j);
//					sql = "UPDATE Accident " +
//							"SET icompany = \"" + accOccur.getCompany() + "\"" +
//							", iclaim = " + accOccur.getClaim() + 
//							" WHERE VIN = \"" + cars[i].getVin() + "\"" +
//							" AND date = \"" + accOccur.getDate() + "\"";
//					//System.out.println(sql);
//					stmt.executeUpdate(sql);
					
					s2.setString(1, accOccur.getCompany());
					s2.setDouble(2, accOccur.getClaim());
					s2.setString(3, cars[i].getVin());
					s2.setString(4, accOccur.getDate());
					s2.executeUpdate();
				}

				mnt = cars[i].getMaintenance();
				for(int k=0;k<cars[i].getMaintenance().size(); k++)
				{
					mainOccur = mnt.get(k);
//					sql = "UPDATE Maintenance " +
//							"SET vendor = \"" + mainOccur.getVendor() + "\"" +
//							", cost = " + mainOccur.getCost() + 
//							" WHERE VIN = \"" + cars[i].getVin() + "\"" +
//							" AND date = \"" + mainOccur.getDate() + "\"";
//					//System.out.println(sql);
//					stmt.executeUpdate(sql);
					
					s3.setString(1, mainOccur.getVendor());
					s3.setDouble(2, mainOccur.getCost());
					s3.setString(3, cars[i].getVin());
					s3.setString(4, mainOccur.getDate());
					s3.executeUpdate();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void query(ComplexCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub
		//STEP 4: Execute a query
		//System.out.println("Creating statement...");

		try
		{
			stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("SELECT b.VIN, b.make, b.model, b.color, b.year, b.date, "
															  + "b.icompany, b.iclaim, m.date, m.vendor,m.cost FROM "
															  + "(SELECT c.VIN, c.make, c.model, c.color, c.year, a.date, "
															  + "a.icompany, a.iclaim FROM (SELECT VIN, make, model, color, year "
															  + "FROM complexcar WHERE VIN = ?) AS c "
															  + "LEFT OUTER JOIN accident AS a ON c.VIN = a.VIN) AS b "
															  + "LEFT OUTER JOIN maintenance as m ON b.VIN = m.VIN;");
			
			
			ResultSet rs = null;
			for(int i=0; i < cars.length; i++)
			{
				/*SELECT b.VIN, b.make, b.model, b.color, b.year, b.date, b.icompany, 
				 * b.iclaim, m.date, m.vendor,m.cost 
				 * FROM
						(SELECT c.VIN, c.make, c.model, c.color, c.year, a.date, a.icompany, a.iclaim 
						FROM
							(SELECT VIN, make, model, color, year FROM complexcar WHERE VIN = "C") AS c
						LEFT OUTER JOIN accident AS a
						ON c.VIN = a.VIN) AS b
				 * LEFT OUTER JOIN maintenance as m
				 * ON b.VIN = m.VIN; */
//				String sql = "SELECT b.VIN, b.make, b.model, b.color, b.year, b.date, "
//						+ "b.icompany, b.iclaim, m.date, m.vendor,m.cost FROM"
//						+ "(SELECT c.VIN, c.make, c.model, c.color, c.year, a.date, "
//						+ "a.icompany, a.iclaim FROM (SELECT VIN, make, model, color, year "
//						+ "FROM complexcar WHERE VIN = \"" + cars[i].getVin() + "\") AS c "
//						+ "LEFT OUTER JOIN accident AS a ON c.VIN = a.VIN) AS b "
//						+ "LEFT OUTER JOIN maintenance as m ON b.VIN = m.VIN";

				statement.setString(1, cars[i].getVin());
				rs = statement.executeQuery();

				// SELECT * FROM
				//(SELECT * FROM cars WHERE VIN = "A") AS c
				//LEFT OUTER JOIN accident AS a
				// ON c.VIN = a.VIN; 
				/*String sql = "SELECT c.VIN, c.make, c.model, c.color, c.year, a.date, a.icompany, a.iclaim"
						+ "FROM (SELECT VIN, make, model, color, year FROM Cars WHERE VIN = \"" 
						+ cars[i].getVin() + "\") AS c LEFT OUTER JOIN accident AS a ON c.VIN = a.VIN";*/
//				rs = stmt.executeQuery(sql);
				//STEP 5: Extract data from result set
				rs.next();
			}
			rs.close();
		} catch (SQLException e){
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void remove(ComplexCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub
		//STEP 4: Execute a query
		//System.out.println("Creating statement...");
		try
		{
			stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("DELETE FROM ComplexCar WHERE VIN = ?;");

			for(int i =0; i < cars.length; i++)
			{
//				String sql = "DELETE FROM ComplexCar " +
//						"WHERE VIN = \"" + cars[i].getVin() + "\"";
//				stmt.executeUpdate(sql);
				
				statement.setString(1, cars[i].getVin());
				statement.executeUpdate();
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
	}
}


