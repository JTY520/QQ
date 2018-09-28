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
				throw new Exception("success:ע��ɹ�");
			}
			stmt.close();
			rs.close();
			throw new Exception("error:δ֪����");
		} else {
			stmt.close();
			rs.close();
			throw new Exception("error:���ʺ���ע��");
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
				throw new Exception("success:��¼�ɹ�");
			}
			else{
				stmt.close(); rs.close(); 
				throw new Exception("error:���û����Ѿ���½"); 
			}
		} 
		else{
			stmt.close(); rs.close(); 
			throw new Exception("error:������û�������"); 
		}
	}

	public void logout(User user) throws Exception {
		try{
			CS.Server.usersOnline.remove(user.getName());
		throw new Exception("success:ע���ɹ�");
		}
		catch(Exception e){
			throw new Exception("error:ע��ʧ�ܣ�δ֪����");
		}
	}
}
