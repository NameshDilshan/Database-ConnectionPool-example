/**
 * 
 */
package dbpoolingexample.dbpoolingexample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @date 30 Sep 2022
 * @Project dbpoolingexample
 * @user namesh_001543
 */
public class C3P0Demo {

	static ComboPooledDataSource comboPooledDataSource = null;
	
	static {
		comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://10.152.2.16:3306/cbmswf?useSSL=false");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("mysql");

		comboPooledDataSource.setMinPoolSize(3);
		comboPooledDataSource.setAcquireIncrement(3);
		comboPooledDataSource.setMaxPoolSize(30);
	}
	
	public static void main(String[] args) throws SQLException  {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = comboPooledDataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from Mandate");
			while (resultSet.next()) {
				System.out.println("ID :" + resultSet.getInt("id"));
				System.out.println("CIF :" + resultSet.getString("cif"));
				System.out.println("NIC :" + resultSet.getString("nic"));
			}
		} finally {
			resultSet.close();
			statement.close();
			connection.close();
		}
	}
}
