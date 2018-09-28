package Server.Sql;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Model.User;

public class GetHistoryMessage {
  public  ArrayList<String> getChatMessage(User user,String toName) throws Exception{
	  ArrayList<String> speak = new ArrayList<String>();
		Statement stmt = SqlCon.getStatement();
		String sqlQuery = "select * from Friends where UserName='" + user.getName() + "'";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		while(rs.next()){
			speak.add(rs.getString("Friend"));
		}
		return speak;
  }
  public ArrayList<String> getGroupMessage(String groupName) throws Exception{
	  ArrayList<String> speak = new ArrayList<String>();
			Statement stmt = SqlCon.getStatement();
			String sqlQuery = "select * from GroupHistory where GroupName='" + groupName + "'";
			ResultSet rs = stmt.executeQuery(sqlQuery);
			while(rs.next()){
				speak.add(rs.getString("Friend"));
			}
			return speak;
  }
}
