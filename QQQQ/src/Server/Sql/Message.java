package Server.Sql;

import java.sql.ResultSet;
import java.sql.Statement;

import Model.User;

public class Message {
  public void saveChatMessage(User user,String toName,String message,String time) throws Exception{
	  Statement stmt = SqlCon.getStatement();
		String sqlQuery = "insert into ChatHistory values('"+user.getName()+"','"+toName+"','"+message+"','"+time+"')";
		int result = stmt.executeUpdate(sqlQuery);
		if(result > 0){
			stmt.close();
			throw new Exception("success:���ͳɹ�");
		}
		else{
			stmt.close();
			throw new Exception("error:δ֪����ʧ��");
		}
  }

  public void savaGroupChatMessage(User user,String groupName,String message,String time){
	  
  }
   
}
