package Server.Sql;

import java.sql.ResultSet;
import java.sql.Statement;

public class Create {
   public void createGroup(String groupName,String groupAdmin) throws Exception{
	   Statement stmt = SqlCon.getStatement();
		String sqlQuery = "select count(*) from Group where GroupName='" + groupName + "'";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		int count = -1;
		/* int count = stmt.executeUpdate(sqlQuery); */
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count == 0) {
			String sql = "insert into Group values ('" + groupName + "','" + groupAdmin+ "')";
			System.out.println(sql);
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				stmt.close();
				rs.close();
				throw new Exception("success:�½��ɹ�");
			}
			stmt.close();
			rs.close();
			throw new Exception("error:δ֪����");
		} else {
			stmt.close();
			rs.close();
			throw new Exception("error:��Ⱥ�Ѿ�����");
   }
}
}