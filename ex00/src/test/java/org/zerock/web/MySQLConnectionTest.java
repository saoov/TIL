package org.zerock.web;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;


public class MySQLConnectionTest {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/spring5fs?useSSL=false&serverTimezone=UTC";
	private static final String USER = "spring5";
	private static final String PW = "spring5";
	
	@Test
	public void testConnection() throws ClassNotFoundException {
		Class.forName(DRIVER);
		try(Connection con = DriverManager.getConnection(URL,USER,PW)){
			System.out.println(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			
}
