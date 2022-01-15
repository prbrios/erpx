package com.github.prbrios.erpx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.github.prbrios.erpx.model.Script;

import groovy.lang.Binding;

public class App {

	private static Connection connection;

	static {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/erpxmd", "postgres", "123456");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void main( String[] args ) {
		Binding binding = new Binding();
		binding.setProperty("nome", "paulo");

		Script s = new Script(null, binding, "teste1");
		s.init();
		s.execute();



	}

}
