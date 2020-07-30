package com.yd.commom;

import java.sql.*;
import java.util.*;

public class DBUtils {

    	//获取连接
	public static Connection getConnect() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql?characterEncoding=utf8&serverTimezone=GMT%2B8&allowMultiQueries=true", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static List<Map<String,Object>> qry(String sql) {
		Connection conn = getConnect();
		Statement st =null;
		ResultSet rs = null;
		List<Map<String, Object>> list = null;
		try{
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		list  = resultSetToList(rs);//处理结果集为List
		}catch (Exception e){e.printStackTrace();}
		finally {
			try{
				if (rs!=null)rs.close();
			}catch (Exception e){
				e.printStackTrace();
			}
			try{
				if (st!=null)st.close();
			}catch (Exception e){
				e.printStackTrace();
			}
			try{
				if (conn!=null)conn.close();
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		return list;
	}

	/**
	 * 执行查询返回的结果集
	 * @param rs
	 * @return
	 * @throws java.sql.SQLException
	 */
	private static List<Map<String, Object>> resultSetToList(ResultSet rs) throws java.sql.SQLException {
		if (rs == null)
			return Collections.EMPTY_LIST;

		ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> rowData;
		while (rs.next()) {
			rowData = new HashMap<>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(rowData);
		}
		try {
		}finally {
			rs.close();
		}

		return list;
	}

	/**
	 * 节点的更新删除，并且新增都暂时利用这个方法
	 * @param sql
	 * @return
	 */
	public static Map<String,Object> updateNode(String sql) {
		Connection conn = getConnect();
		Statement st =null;
		Map<String, Object> map = new HashMap<>();
		try{
			st = conn.createStatement();
			int i = st.executeUpdate(sql);
			if (i>0){
				map.put("rspMsg","成功");
				map.put("rspCode","0");
			}else {
				map.put("rspMsg","更新失败");
				map.put("rspCode","1");
			}
		}catch (Exception e){e.printStackTrace();}
		finally {
			try{
				if (st!=null)st.close();
			}catch (Exception e){
				e.printStackTrace();
			}
			try{
				if (conn!=null)conn.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return map;
	}




}
