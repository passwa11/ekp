package com.landray.kmss.util;

import com.landray.kmss.sys.config.loader.datasource.KmssDataSourceFactory;
import com.landray.kmss.sys.config.service.ISysEmptyService;
import com.landray.kmss.sys.hibernate.HibernateHelper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据库相关操作
 * 
 * @author 叶中奇
 * 
 */
public abstract class DbUtils {

	private static long delta = 0;

	private static long updateTime = 0;

	private static Thread updateTimeThread = null;

	/**
	 * 获取数据库的当前时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date getDbTime() {
		return new Date(getDbTimeMillis());
	}

	public static long getDbTimeMillis() {
		long localTime = System.currentTimeMillis();
		if (localTime - updateTime > DateUtil.HOUR) {
			syncDbTime();
		}
		return localTime - delta;
	}

	private static synchronized void syncDbTime() {
		if (updateTimeThread == null) {
			updateTimeThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						long dbTime = ((ISysEmptyService) SpringBeanUtil
								.getBean("sysEmptyService")).getDbTime()
								.getTime();
						long localTime = System.currentTimeMillis();
						delta = localTime - dbTime;
						updateTime = localTime;
					} catch (Exception e) {
						delta = 0;
						updateTime = System.currentTimeMillis();
					} finally {
						updateTimeThread = null;
					}
				}
			});
			updateTimeThread.start();
		}
	}

	/**
	 * 对象转成Blob
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static Blob obj2Blob(Serializable obj) throws IOException {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return HibernateHelper.createBlob(baos.toByteArray());
		} finally {
			if (oos != null) {
                oos.close();
            }
			if (baos != null) {
                baos.close();
            }
		}
	}

	/**
	 * Blob转成对象
	 * 
	 * @param blob
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Object blob2Obj(Blob blob) throws IOException, SQLException,
			ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(blob.getBinaryStream());
			return ois.readObject();
		} finally {
			IOUtils.closeQuietly(ois);
		}
	}

	/**
	 * 对象转成String
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static String obj2String(Serializable obj) throws IOException {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return new String(Base64.encodeBase64(baos.toByteArray()));
		} finally {
			if (oos != null) {
                oos.close();
            }
			if (baos != null) {
                baos.close();
            }
		}
	}

	/**
	 * String转成对象
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Object string2Obj(String s) throws IOException, SQLException,
			ClassNotFoundException {
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(Base64.decodeBase64(s.getBytes()));
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} finally {
			if (ois != null) {
                ois.close();
            }
			if (bis != null) {
                bis.close();
            }
		}
	}
	/**
	 * 检查表是否存在
	 * @param tableName 数据库表名
	 * @return boolean，true：表存在；false：表不存在
	 * @throws Exception
	 */
	public static boolean isExitTable(String tableName) throws Exception {
		return isExitTableInfo(tableName, null);
	}

	/**
	 * 检查表和字段是否存在
	 * @param tableName 数据库表名
	 * @param column 数据库表字段
	 * @return boolean，true：表存在；false：表不存在
	 * @throws Exception
	 */
	public static boolean isExitTableInfo(String tableName, String column) throws Exception {
		DataSource ds = KmssDataSourceFactory.createDataSource();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			DatabaseMetaData dmd = conn.getMetaData();
			String driverClass = ResourceUtil.getKmssConfigString("hibernate.connection.driverClass");
			rs = queryRs(driverClass, conn, tableName, dmd);
			if (rs.next()) {
				if (StringUtils.isNotBlank(column)) {
					return checkColumn(tableName, column, conn);
				}
				return true;
			}
			//某些数据库对表名的大小写是敏感的，所以还要尝试使用小写和大写的模式，不过要先关闭之前的游标
			String tableName2 = tableName.toLowerCase();
			if (!StringUtils.equals(tableName, tableName2)) {
				JdbcUtils.closeResultSet(rs);
				//新开一个游标
				rs = queryRs(driverClass, conn, tableName2, dmd);
				if (rs.next()) {
					if (StringUtils.isNotBlank(column)) {
						return checkColumn(tableName, column, conn);
					}
					return true;
				}
			}
			tableName2 = tableName.toUpperCase();
			if (!StringUtils.equals(tableName, tableName2)) {
				JdbcUtils.closeResultSet(rs);
				//新开一个游标
				rs = queryRs(driverClass, conn, tableName2, dmd);
				if (rs.next()) {
					if (StringUtils.isNotBlank(column)) {
						return checkColumn(tableName, column, conn);
					}
					return true;
				}
			}
			return false;
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeConnection(conn);
		}
	}

	/**
	 * 检查
	 * @param column
	 * @param tname
	 * @param conn
	 * @return
	 */
	private static boolean checkColumn(String tname, String column, Connection conn) {
		String sql = "select " + column + " from " + tname;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData data = null;
		try {
			Set<String> columnSet = new HashSet<String>();
			columnSet.add(column);
			columnSet.add(column.toLowerCase());
			columnSet.add(column.toUpperCase());

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			data = rs.getMetaData();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				if (columnSet.contains(data.getColumnName(i))) {
					return true;
				}
			}
		} catch (Exception e) {
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
		}
		return false;
	}
	
	private static ResultSet queryRs(String driverClass, Connection conn, String tableName, DatabaseMetaData dmd) throws Exception {
		ResultSet rs;
		if (StringUtils.containsIgnoreCase(driverClass, "Oracle")) {
			//Oracle数据库
			String userName = conn.getMetaData().getUserName();
			rs = dmd.getTables(null, userName, tableName, new String[] { "TABLE" });
		}else{
			rs = dmd.getTables(null, null, tableName, new String[] { "TABLE" });
		}
		return rs;
	}
}
