/**
 * 
 */
package dbpoolingexample.dbpoolingexample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 
 * @date 30 Sep 2022
 * @Project dbpoolingexample
 * @user namesh_001543
 */
public class DBCP2Demo {
 
	// BASIC DATASOURCE EXAMPLE
//	private static BasicDataSource datasource = null;
//	static {
//		datasource = new BasicDataSource();
//		datasource.setUrl("jdbc:mysql://10.152.2.16:3306/cbmswf?useSSL=false");
//		datasource.setUsername("root");
//		datasource.setPassword("mysql");
//		datasource.setMinIdle(5);
//		datasource.setMaxIdle(10);
//		datasource.setMaxTotal(25);
//	}
	
	
	// POOLING DATASOURCE EXAMPLE
	private static DataSource dataSource = null;
	static {
		Properties properties = new Properties();
		properties.setProperty("user", "root");
		properties.setProperty("password", "mysql");
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory("jdbc:mysql://10.152.2.16:3306/cbmswf?useSSL=false", properties);
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
		GenericObjectPoolConfig<PoolableConnection> config = new GenericObjectPoolConfig<PoolableConnection>();
		config.setMaxTotal(25);
		config.setMaxIdle(10);
		config.setMinIdle(5);
		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory, config);
		poolableConnectionFactory.setPool(connectionPool);
		dataSource = new PoolingDataSource<>(connectionPool);
	}
		
	public static void main (String [] args)  throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null; 
			connection = dataSource.getConnection();
			statement = connection.createStatement(); 
			resultSet = statement.executeQuery("select * from Mandate");
			while(resultSet.next()) {
				System.out.println("ID :" + resultSet.getInt("id"));
				System.out.println("CIF :" + resultSet.getString("cif"));
				System.out.println("NIC :" + resultSet.getString("nic"));
			}
	}  
}
