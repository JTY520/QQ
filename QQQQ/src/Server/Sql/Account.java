package Server.Sql;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.User;

public class Account {
	public void register(User user) throws Exception {
		Statement stmt = SqlCon.getStatement();
		String sqlQuery = "select count(*) from [User] where UserName='" + user.getName() + "'";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		int count = -1;
		/* int count = stmt.executeUpdate(sqlQuery); */
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count == 0) {
			String sql = "insert into [User] values ('" + user.getName() + "','" + user.getPassword() + "')";
			System.out.println(sql);
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				stmt.close();
				rs.close();
				throw new Exception("success:注册成功");
			}
			stmt.close();
			rs.close();
			throw new Exception("error:未知错误");
		} else {
			stmt.close();
			rs.close();
			throw new Exception("error:该帐号已注册");
		}

	}

	public void checkLogin(User user,PrintWriter writer) throws Exception {
		Statement stmt = SqlCon.getStatement();
		String sqlQuery = "select * from [User] where UserName='" + user.getName() + "' and Password ='"
				+ user.getPassword() + "'";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		int count = -1;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count > 0) {
			if(CS.Server.usersOnline.get(user.getName())==null){
				CS.Server.usersOnline.put(user.getName(), writer);
				stmt.close();
				rs.close();
				throw new Exception("success:登录成功");
			}
			else{
				stmt.close(); rs.close(); 
				throw new Exception("error:该用户名已经登陆"); 
			}
		} 
		else{
			stmt.close(); rs.close(); 
			throw new Exception("error:密码或用户名错误"); 
		}
	}

	public void logout(User user) throws Exception {
		try{
			CS.Server.usersOnline.remove(user.getName());
		throw new Exception("success:注销成功");
		}
		catch(Exception e){
			throw new Exception("error:注销失败，未知错误");
		}
	}
}
