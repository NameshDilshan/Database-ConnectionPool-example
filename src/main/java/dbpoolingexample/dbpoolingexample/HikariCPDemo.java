/**
 * 
 */
package dbpoolingexample.dbpoolingexample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @date 30 Sep 2022
 * @Project dbpoolingexample
 * @user namesh_001543
 */
public class HikariCPDemo {

	private static HikariDataSource datasource = null;
	
	static {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://10.152.2.16:3306/cbmswf?useSSL=false");
		config.setUsername("root");
		config.setPassword("mysql");
		config.addDataSourceProperty("minimumIdle", "5");
		config.addDataSourceProperty("maximumPoolSize", "25");
		datasource = new HikariDataSource(config);
	}
	
	public static void main(String[] args) throws SQLException  {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = datasource.getConnection();
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
