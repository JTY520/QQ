package Server.Sql;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import Model.User;

public class GetUsers {
	public ArrayList<String> GetFriends(User user) throws Exception {
		ArrayList<String> friends = new ArrayList<String>();
		Statement stmt = SqlCon.getStatement();
		String sqlQuery = "select * from Friends where UserName='" + user.getName() + "'";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		while(rs.next()){
			friends.add(rs.getString("Friend"));
		}
		sqlQuery = "select * from Friends where Friend='" + user.getName() + "'";
		 rs = stmt.executeQuery(sqlQuery);
		while(rs.next()){
			friends.add(rs.getString("UserName"));
		}
		return friends;
	}

	public ArrayList<String> GetGroups(User user) throws Exception {
		ArrayList<String> groups = new ArrayList<String>();
		Statement stmt = SqlCon.getStatement();
		String sqlQuery = "select * from GroupUser where GroupMember='" + user.getName() + "'";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		while(rs.next()){
			groups.add(rs.getString("GroupName"));
		}
		return groups;
	}

    public ArrayList<String> Search(String txt) throws Exception{
    	ArrayList<String> list = new ArrayList<String>();
		Statement stmt = SqlCon.getStatement();
		String sqlQuery = "select * from GroupUser";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		while(rs.next()){
			if(rs.getString("GroupName").contains(txt))
			{
				list.add("»∫£∫"+rs.getString("GroupName"));
			}
		}
		String sqlQuery1 = "select * from Friends";
		ResultSet rs1 = stmt.executeQuery(sqlQuery1);
		while(rs1.next()){
			if(rs1.getString("Friend").contains(txt)){
				list.add("”√ªß£∫"+rs1.getString("Friend"));
			}
		}
		return list;
    }
}
