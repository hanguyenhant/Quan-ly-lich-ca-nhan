import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectMySQL {
	public static Connection connect = null;
	public final static String className="com.mysql.jdbc.Driver";
	public final static String url="jdbc:mysql://localhost:3306/baitaplon";
	
	//Ket noi toi mysql
  	public static Connection connectMySQL() {
  		
  		try {
  			Class.forName(className);
  			connect=DriverManager.getConnection(url, "root", "123456");
  			System.out.println("Kết nối thành công!\n");
  		} catch (SQLException e) {
  			System.out.println("Không tìm thấy class");
  		} catch (ClassNotFoundException e) {
  			System.out.println("Lỗi kết nối");
  		}
  		return connect;
  	}
}
