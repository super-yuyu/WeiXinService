package com.weixin.dubbo.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Future;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * @author: 褚凤明
 * @version: 1.0 2009-3-30
 * @function 数据库连接池类,用于从连接池中取得连接
 */
public class DBConnPool {
	private static Context initCtx = null;
	private static Context envCtx = null;
	private static DataSource datasource = null;

	/**
	 * 从Tomcat连接池中取得连接,记得要关闭
	 */
	public static Connection getConnection() throws SQLException {
		try {
			if(datasource == null){
				initCtx = new InitialContext();
				envCtx = (Context) initCtx.lookup("java:/comp/env");
				datasource = (DataSource) envCtx.lookup("jdbc/mysql");
			}
			Future<Connection> future = datasource.getConnectionAsync();
			while (!future.isDone()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException x) {
					Thread.currentThread();
					Thread.interrupted();
				}
			}
			Connection conn = future.get();
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("数据源初始化失败.原因是" + e.getMessage());
		}
	}
}
