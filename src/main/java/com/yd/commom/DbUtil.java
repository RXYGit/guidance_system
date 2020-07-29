package com.yd.commom;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtil {

	private static Connection getConn() {
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/testsql?characterEncoding=utf8&serverTimezone=GMT%2B8&allowMultiQueries=true", "root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static int updateResult(int id, String result, String outparam,
			String ip) {
		Connection conn = null;
		Statement stmt = null;
		String sql = "update t_batch_callinfo t set t.logdate=now(),t.result='"
				+ result + "', t.outparam='" + outparam + "',t.ip='" + ip
				+ "' where t.id=" + id;
		int rs = -1;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = getConn();
			stmt = conn.createStatement();

			long l1 = new Date().getTime();
			rs = stmt.executeUpdate(sql);
			// stmt.executeQuery(sql);
			long l2 = new Date().getTime();
			// System.out.println(sql+"耗时: " + (l2-l1)/1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}

			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}
		return rs;
	}

	public static int inserTask(String taskid) {
		Connection conn = null;
		Statement stmt = null;
		String sql = "INSERT INTO t_batch_taskinfo(taskid,createtime) VALUES('"
				+ taskid + "',NOW())";
		int rs = -1;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = getConn();
			stmt = conn.createStatement();

			long l1 = new Date().getTime();
			rs = stmt.executeUpdate(sql);
			// stmt.executeQuery(sql);
			long l2 = new Date().getTime();
			// System.out.println(sql+"耗时: " + (l2-l1)/1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}

			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}

		return rs;
	}

	public static int updateTaskId(String taskId, int idstart, int idend) {
		Connection conn = null;
		Statement stmt = null;
		String sql = "update t_batch_callinfo t set t.taskid='" + taskId
				+ "' where t.id >=" + idstart + " AND  t.id <=" + idend
				+ " and t.result =''";
		int rs = -1;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = getConn();
			stmt = conn.createStatement();

			long l1 = new Date().getTime();
			rs = stmt.executeUpdate(sql);
			// stmt.executeQuery(sql);
			long l2 = new Date().getTime();
			// System.out.println(sql+"耗时: " + (l2-l1)/1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}

			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}

		return rs;
	}

	public static List<Map<String, String>> qryCallList(int idstart, int idend) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT t.id, t.url, t.postparam, t.header,t.taskid FROM t_batch_callinfo t WHERE t.id >="
				+ idstart + " AND  t.id <=" + idend + " AND NOT EXISTS(SELECT 1 FROM t_batch_callinforesult t1 WHERE t1.id=t.id)";
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", rs.getString("id"));
				map.put("url", rs.getString("url"));
				map.put("postparam", rs.getString("postparam"));
				map.put("header", rs.getString("header"));
				map.put("taskid", rs.getString("taskid"));
				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}

			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}
		// System.out.println(sql);
		return list;
	}

	public static int qryCallCount(int idstart, int idend) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(1) FROM t_batch_callinfo t WHERE NOT EXISTS(SELECT 1 FROM t_batch_callinforesult t1 WHERE t1.id=t.id) and t.id >="
				+ idstart + " AND  t.id <=" + idend;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}

			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}
		// System.out.println(sql);
		return 0;
	}

	public static void batchExecute(List<Map<String, String>> list) {

		long a = System.currentTimeMillis();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
	

			conn = getConn();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("load data local infile '' "
							+ "into table t_batch_callinforesult  fields terminated by '(@#@)' (id,result,outparam,ip,taskid)");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				sb.append(map.get("id") + "(@#@)" + map.get("result") + "(@#@)" + map.get("outparam") + "(@#@)" + map.get("ip") + "(@#@)" + map.get("taskid") + "\n");
			}
			//System.out.println("sb:"+sb);
			InputStream is = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
			//((com.mysql.jdbc.Statement) pstmt).setLocalInfileInputStream(is);
			pstmt.execute();
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static List<Map<String, String>> qry(String sql) {
		//System.out.println("sql:"+sql);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
            		map.put(rs.getMetaData().getColumnName(i).toUpperCase(),rs.getString(i) == null ? "":rs.getString(i));
            		//System.out.println(rs.getMetaData().getColumnName(i)+" " +i);
            	}
				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}

			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}
		// System.out.println(sql);
		return list;
	}
}
