import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Timer;
	    
public class NhacLich {
	public NhacLich() throws ParseException
	{
		Connection connect;
		ConnectMySQL connection;
	
		connection = new ConnectMySQL();
		connect = ConnectMySQL.connectMySQL();
		
		 try {
		        Statement stm = connect.createStatement();
		        ResultSet rs = stm.executeQuery("SELECT \r\n" + 
								        		"	name, content, start_time,\r\n" + 
								        		"    DATE_SUB(\r\n" + 
								        		"		start_time, \r\n" + 
								        		"		INTERVAL CONCAT(substr(prompt_before,1,2),':00')\r\n" + 
								        		"		MINUTE_SECOND\r\n" + 
								        		"	) as reminder,\r\n" + 
								        		"    sound \r\n" + 
								        		"from baitaplon.lich\r\n" + 
								        		"where\r\n" + 
								        		"	DATE_SUB(\r\n" + 
								        		"		start_time, \r\n" + 
								        		"		INTERVAL CONCAT(substr(prompt_before,1,2),':00')\r\n" + 
								        		"		MINUTE_SECOND\r\n" + 
								        		"	)>now()\r\n" +
								        		"and prompt != 'No';");
		        
		        while (rs.next()) {
		            LichHen lichhen = new LichHen();

		            lichhen.setName(rs.getString(1));
		            lichhen.setContent(rs.getString(2));
		            lichhen.setStart_time(rs.getString(3));
		            lichhen.setReminder(rs.getString(4));		            
		            lichhen.setSound(rs.getString(5));
		            
		            lichhen.getMyreminder();
		        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
				
		 
	}
}
