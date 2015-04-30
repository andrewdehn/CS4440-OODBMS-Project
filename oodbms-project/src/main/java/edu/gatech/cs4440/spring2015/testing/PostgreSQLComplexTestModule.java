package edu.gatech.cs4440.spring2015.testing;

import edu.gatech.cs4440.spring2015.model.Accident;
import edu.gatech.cs4440.spring2015.model.ComplexCar;
import edu.gatech.cs4440.spring2015.model.Maintenance;
import edu.gatech.cs4440.spring2015.model.SimpleCar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PostgreSQLComplexTestModule implements ComplexDatabaseTestModule {
	public Connection con = null;
	public Statement st = null;
	
	ResultSet rs = null;
	java.sql.PreparedStatement pst = null;
	public static final String DB_NAME = "PostgreSQLCars";
	
	String url = "jdbc:postgresql://localhost:5432/"+ DB_NAME;
    String user = "postgres";
    String password = "12345";


    public PostgreSQLComplexTestModule(){

    }
    @Override
    public String databaseName() {
		return DB_NAME;
    }

    @Override
    public void setup() throws DatabaseTestException {
        try {
        	pst = null;
			con = DriverManager.getConnection(url, user, password);
//			PreparedStatement state = con.prepareStatement(" CREATE TABLE accident(vin text, company text, date text, insurance text, claim text )");
//			state.executeUpdate();
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
			
			PreparedStatement pst1 = con.prepareStatement("DELETE FROM cars");
//			PreparedStatement pst2 = con.prepareStatement("DELETE FROM maintenance");
//			PreparedStatement pst3 = con.prepareStatement("DELETE FROM Accident");
			pst1.executeUpdate();
//			pst2.executeUpdate();
//			pst3.executeUpdate();
		}
		catch(SQLException e)
		{
			throw new DatabaseTestException(e);
		}
    }

    @Override
    public void add(ComplexCar[] cars) throws DatabaseTestException {
    	
		try {
			List<Accident> accidents;
			List<Maintenance> mnt;
			Maintenance mainOccur;
			PreparedStatement st2 = con.prepareStatement("UPDATE Accident SET insurance = ?, date = ?, claim = ? WHERE VIN = ?");
			PreparedStatement s3 = con.prepareStatement("UPDATE Maintenance SET date = ?, vendor = ?, cost = ? WHERE VIN = ?");
			for(int i = 0; i < cars.length; i++) {
				
				accidents = cars[i].getAccidents();
				PreparedStatement st = con.prepareStatement("UPDATE cars SET make = ?, model = ?, color = ?, year = ? WHERE VIN = ?");
				
				st.setString(1,cars[i].getMake());
				st.setString(2,cars[i].getModel());
				st.setString(3,cars[i].getColor().getValue());
				st.setInt(4, cars[i].getYear());
				st.setString(5, cars[i].getVin());
				st.executeUpdate();
				for(int j = 0; j< cars[i].getAccidents().size(); j++)
				{
					Accident accOccur;
					accOccur = accidents.get(j);
					
					st2.setString(1,accOccur.getCompany());
					st2.setString(2, accOccur.getDate());
					st2.setDouble(3, accOccur.getClaim());
					st2.setString(4, cars[i].getVin());
					st2.executeUpdate();
				}
				mnt = cars[i].getMaintenance();
				for(int k=0;k<cars[i].getMaintenance().size();k++)
				{
					mainOccur = mnt.get(k);
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
		try
		{
			List<Accident> accidents;
			Accident accOccur;
			List<Maintenance> mnt;
			Maintenance mainOccur;
			
			PreparedStatement s1 = con.prepareStatement("UPDATE Cars SET make = ?, model = ?, color = ?, year = ? WHERE VIN = ?;");
			PreparedStatement s2 = con.prepareStatement("UPDATE Accident SET company = ?, claim = ? WHERE VIN = ? AND date = ?;");
			PreparedStatement s3 = con.prepareStatement("UPDATE Maintenance SET vendor = ?, cost = ? WHERE VIN = ? AND date = ?;");

			for(int i = 0; i < cars.length; i++)
			{
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
    	try
		{			
			PreparedStatement statement = con.prepareStatement("SELECT b.VIN, b.make, b.model, b.color, b.year, b.date, "
															  + "b.company, b.claim, m.date, m.vendor,m.cost FROM "
															  + "(SELECT c.VIN, c.make, c.model, c.color, c.year, a.date, "
															  + "a.company, a.claim FROM (SELECT VIN, make, model, color, year "
															  + "FROM Cars WHERE VIN = ?) AS c "
															  + "LEFT OUTER JOIN accident AS a ON c.VIN = a.VIN) AS b "
															  + "LEFT OUTER JOIN maintenance as m ON b.VIN = m.VIN;");
			
			
			ResultSet rs = null;
			for(int i=0; i < cars.length; i++)
			{
				statement.setString(1, cars[i].getVin());
				rs = statement.executeQuery();
				rs.next();
			}
			rs.close();		
		} catch(SQLException e) {
			throw new DatabaseTestException(e);
		}
    }

    @Override
    public void remove(ComplexCar[] cars) throws DatabaseTestException {
    	try{
			st = con.createStatement();
			for(int i =0; i < cars.length; i++) {
				String sql = "DELETE FROM Cars " +
						"WHERE VIN = \'" + cars[i].getVin() + "\'";
				String sql2 = " ; DELETE FROM Accident " +
						"WHERE VIN = \'" + cars[i].getVin() + "\'";
				String sql3 = " ; DELETE FROM Maintenance " +
						"WHERE VIN = \'" + cars[i].getVin() + "\'";
				String q = sql + " " + sql2 + " " + sql3;
				st.executeUpdate(q);
				}
			}
		catch(SQLException e) {
			throw new DatabaseTestException(e);
		}
    }

}
