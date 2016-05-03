package me.legopal92.bountyhunter.utils.dataStore.mysql;

import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.logging.Level;

/**
 * Connects to and uses a MySQL database
 * 
 * @author -_Husky_-
 * @author tips48
 */
public class MySQL extends Database {
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;

	private Connection connection;

	/**
	 * Creates a new MySQL instance
	 * 
	 * @param plugin
	 *            Plugin instance
	 * @param hostname
	 *            Name of the host
	 * @param port
	 *            Port number
	 * @param database
	 *            Database name
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 */
	public MySQL(Plugin plugin, String hostname, String port, String database,
			String username, String password) {
		super(plugin);
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.connection = null;
	}

	@Override
	public Connection openConnection() {
		if (!checkConnection()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String statement = "CREATE DATABASE IF NOT EXISTS " + database;
				Connection conn = DriverManager.getConnection("jdbc:mysql://"
						+ hostname + ":" + port, user, password);
				Statement st = conn.createStatement();
				st.executeUpdate(statement);
				st.close();
				conn.close();
				connection = DriverManager.getConnection(
						"jdbc:mysql://" + this.hostname + ":" + this.port + "/"
								+ this.database, this.user, this.password);
			} catch (SQLException e) {
				plugin.getLogger().log(
						Level.SEVERE,
						"Could not connect to MySQL server! because: "
								+ e.getMessage());
			} catch (ClassNotFoundException e) {
				plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
			}
		}
		return connection;

	}

	@Override
	public boolean checkConnection() {
		return connection != null;
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				plugin.getLogger().log(Level.SEVERE,
						"Error closing the MySQL Connection!");
				e.printStackTrace();
			}
		}
	}

	public ResultSet querySQL(String query) throws SQLException {

		Connection c = openConnection();
		Statement s = null;

		try {
			s = c.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		ResultSet ret = null;

		try {
			ret = s.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            s.close();
            c.close();
        }

		return ret;
	}

	public void updateSQL(String update) throws SQLException {

		Connection c = openConnection();
		Statement s = null;

		try {
			s = c.createStatement();
			s.executeUpdate(update);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
            s.close();
			c.close();
		}

	}

}
