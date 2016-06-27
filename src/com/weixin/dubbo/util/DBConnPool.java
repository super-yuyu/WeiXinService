package com.weixin.dubbo.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Future;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * @author: �ҷ���
 * @version: 1.0 2009-3-30
 * @function ���ݿ����ӳ���,���ڴ����ӳ���ȡ������
 */
public class DBConnPool {
	private static Context initCtx = null;
	private static Context envCtx = null;
	private static DataSource datasource = null;

	/**
	 * ��Tomcat���ӳ���ȡ������,�ǵ�Ҫ�ر�
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
			throw new SQLException("����Դ��ʼ��ʧ��.ԭ����" + e.getMessage());
		}
	}
}
