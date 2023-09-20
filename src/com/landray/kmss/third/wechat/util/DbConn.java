package com.landray.kmss.third.wechat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.util.ClassUtils;

import com.landray.kmss.sys.config.action.SysConfigAdminUtil;
import com.landray.kmss.util.StringUtil;

public class DbConn {
	
	public static String driverClass;
	public static String dialect;
	public static String url;
	public static String userName;
	public static String password;
	
	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;

	public static Properties configuration = new Properties();
	
	static {
		try{
			SysConfigAdminUtil.loadKmssConfigProperties(configuration);
			configuration();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void configuration() {
		driverClass = configuration.getProperty("hibernate.connection.driverClass");
		dialect = configuration.getProperty("hibernate.dialect");
		url = configuration.getProperty("hibernate.connection.url");
		userName = configuration.getProperty("hibernate.connection.userName");
		password = configuration.getProperty("hibernate.connection.password");
	}
	
	 public static Connection getconn(){
		  try {
			  com.landray.kmss.util.ClassUtils.forName(driverClass).newInstance();
		  } catch (InstantiationException e) {
		        e.printStackTrace();
		  } catch (IllegalAccessException e) {
		        e.printStackTrace();
		  } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		  }
		  try {
		       connection=DriverManager.getConnection(url,userName,password);
		  } catch (SQLException e) {
		        e.printStackTrace();    
		  }
	      return connection;
	  }

	 public static Statement getStemd(){
		connection = getconn();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	 }
	 
	 
	 public static ResultSet executeQ(String sql){
		 if(StringUtil.isNull(sql)) {
             return null;
         }
		 try{
			 statement = getStemd();
			 resultSet = statement.executeQuery(sql);
		 }catch (SQLException e) {
				e.printStackTrace();
		 }finally{
			 //close();
		 }
		 return resultSet;
	 }
	 
	 
	 public static int executeU(String sql){
		 int flag = 0;
		 if(StringUtil.isNull(sql)){
			 flag = 0;
		 }
		 try{
			 statement = getStemd();
			 flag = statement.executeUpdate(sql);
		 }catch (SQLException e) {
			e.printStackTrace();
		 }finally{
			 close();
		 }
		 return flag;
	 }
	 
	 public static void close(){
		 try{
			 if(resultSet !=null) {
                 resultSet.close();
             }
			 if(statement !=null) {
                 statement.close();
             }
			 if(connection !=null) {
                 connection.close();
             }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	 
}
