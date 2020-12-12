package com.credit.suisse.LogManager.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hsqldb.Server;

public class HSqlDbManager {

	private Connection con = null;

	

	public Connection getConnection() {

		if (con == null) {
			try {
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
				//con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/logdatabase", "SA", "");
				con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/logdb", "SA", "");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}

	public int executeQuery(String query) {
		Statement stmt = null;
		int result = 0;

		try {
			Connection conn = getConnection();
			stmt = con.createStatement();

			result = stmt.executeUpdate(query);
			con.commit();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}
	
	public ResultSet executeSelectQuery(String query) {
		Statement stmt = null;
		ResultSet result = null;

		try {
			Connection conn = getConnection();
			stmt = con.createStatement();

			result = stmt.executeQuery(query);
			
			
			con.commit();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}
}
