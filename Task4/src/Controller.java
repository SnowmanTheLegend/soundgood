import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
	
	private final String url = "jdbc:postgresql://localhost/soundgood";
	private final String user = "postgres";
	private final String password = "postgres";
	private String space = " ";
	
	Connection connection;
	public Controller() throws SQLException {
		connection = DriverManager.getConnection(url,user,password);
		connection.setAutoCommit(false);

	}
	
	public void listinstruments() throws SQLException {
		try {
			Statement stmt = connection.createStatement();
			ResultSet students = stmt.executeQuery("SELECT * FROM instruments WHERE id NOT IN (SELECT instrument_id FROM loanedinstrument WHERE loanend IS NULL);");
			while(students.next()) {
				System.out.println(students.getString(1)+space+students.getString(2)+space+students.getString(3)+space+students.getString(4));
				
			}connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}
		
	}
	
	public void rentinstruments(String studentid, String instrumentid) throws SQLException {
		
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("INSERT INTO loanedinstrument(student_id,loanstart, instrument_id) " + 
					"SELECT s.id,CURRENT_TIMESTAMP as loanstart,i.id FROM student AS s, instruments AS i WHERE s.id = "
					+ studentid + "AND i.id="+instrumentid+" AND NOT EXISTS (SELECT student_id,COUNT(*) FROM loanedinstrument WHERE student_id =" + studentid +" AND loanend IS NULL GROUP BY"
					+ " student_id HAVING COUNT(*)>1) AND NOT EXISTS ((SELECT instrument_id FROM loanedinstrument WHERE loanend IS NULL) EXCEPT (SELECT instrument_id FROM loanedinstrument WHERE instrument_id!="+ instrumentid+"));");
					connection.commit();
					System.out.println("instrument "+instrumentid+" rented \ncurrently loaned instruments:");
					showinstruments();
		} catch (SQLException e) {
			connection.rollback();
			System.out.println("rent failed");
			e.printStackTrace();
		}
		
		}
	
	public void terminaterental(String instrumentid) throws SQLException {
		
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("UPDATE loanedinstrument SET loanend = CURRENT_TIMESTAMP WHERE loanend IS NULL AND instrument_id = "+ instrumentid+";");
			connection.commit();
			System.out.println("rental for instrument "+instrumentid+" terminated \ncurrently loaned instruments:");
			showinstruments();
		} catch (SQLException e) {
			connection.rollback();
			System.out.println("termination failed");
			e.printStackTrace();
		}
		
	}
public void showinstruments() throws SQLException {
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet instruments = stmt.executeQuery("SELECT * FROM loanedinstrument WHERE loanend IS NULL");
			while(instruments.next()) {
				System.out.println(" instrument: "+instruments.getString(1)+" loanstart: "+space+instruments.getString(2)+" student: "+space+instruments.getString(3));
				
			}
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			System.out.println("failed to show loaned instruments");
			e.printStackTrace();
		}
		
		}
	
}
