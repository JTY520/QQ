package Server.Sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlCon {
	
	public static Connection getConnection() throws Exception {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=QQ";
		String userName = "sa";
		String userPwd = "123456";
		Class.forName(driverName);
		return DriverManager.getConnection(dbURL, userName, userPwd);
	}
	
	public static Statement getStatement() throws Exception
	{ 
		return getConnection().createStatement();
	}
	
	public static PreparedStatement getPreparedStatement(String sql)throws Exception{
		return getConnection().prepareStatement(sql);
	}

}
