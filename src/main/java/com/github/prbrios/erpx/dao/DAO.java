package com.github.prbrios.erpx.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.github.prbrios.erpx.App;

public class DAO {

	protected Connection getConnection() {
		return App.getConnection();
	}

	private Map<String, String> informationSchema(String table) {
		return null;
	}

	public Row getRow(String query) {
		try {

			ResultSet rs = this.getResultSet(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			Row row = new Row();
			
			while(rs.next()) {
				
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.put(rsmd.getColumnName(i), this.getValueType(rsmd.getColumnType(i), rs.getObject(rsmd.getColumnName(i))));
				}

			}
			return row;

		} catch (Exception e) {
			throw new RuntimeException("Falha ao obter row");
		}
	}

	private ResultSet getResultSet(String query) throws SQLException {
		Statement stmt = getConnection().createStatement();
		return stmt.executeQuery(query);
	}

	private Object getValueType(int type, Object value){
		return value;
	}

}
