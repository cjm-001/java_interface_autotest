package com.longteng.framework.dao;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public class DBUtil {

	private static DataSource dataSource;
	private static Properties prop = new Properties();
	static {
		InputStream is = null;

		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
			prop.load(new InputStreamReader(is, "UTF-8"));
			dataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws Throwable {
		return dataSource.getConnection();
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Throwable th) {
		}

	}

	public static String excuteQeury(String sql) throws Exception {
		Connection conn = null;
		JSONArray jsonResult = new JSONArray();
		try {
			conn = DBUtil.getConnection();
			System.out.println(conn);
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					JSONObject json = new JSONObject();
					// resultSet数据下标从1开始

					String columnName = metaData.getColumnName(i);
					String value = rs.getString(i);
					json.put(columnName, value);
					jsonResult.put(json);
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return jsonResult.toString();
	}

	public static int excuteUpdate(String sql) {
		int result = 0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			System.out.println(conn);
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			result = prepareStatement.executeUpdate(sql);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			System.out.println(DBUtil.excuteQeury("select * from cardinfo "));

			// Connection connection = DBUtil.getConnection();
			// String sql = "select * from cardinfo where cardNumber = '22223333'";
			// PreparedStatement prepareStatement = connection.prepareStatement(sql);
			//
			// ResultSet rs = prepareStatement.executeQuery();
			//
			// while (rs.next()) {
			// System.out.println(rs.getString(1) + ":" + rs.getString(2) + ":" +
			// rs.getString(3) + ":"
			// + rs.getString(4) + ":" + rs.getString(5));
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
