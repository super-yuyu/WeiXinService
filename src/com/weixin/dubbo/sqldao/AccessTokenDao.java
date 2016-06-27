package com.weixin.dubbo.sqldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.weixin.dubbo.util.DBConnPool;
import com.weixin.dubbo.vo.AccessToken;

public class AccessTokenDao{
	IDaoBack daoBack;
	
	/**
	 * 保存数据
	 * 
	 * @param entity
	 */
	public Boolean save(AccessToken entity) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into access_token (app_Id,app_secret,access_token,cre_time) values");
		sb.append("(?,?,?,?)");
		try {
			conn = DBConnPool.getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sb.toString());
			st.setString(1, entity.getApp_Id());
			st.setString(2, entity.getApp_secret());
			st.setString(3, entity.getAccess_token());
			st.setLong(4, Long.valueOf(entity.getCre_time()));
			int num = st.executeUpdate();
			daoBack.run();
			conn.commit();
			if (num > 0) {
				return true;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
					st = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * 保存数据
	 * 
	 * @param entity
	 */
	public AccessToken getAccessToKen(String appid,String app_secret) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select * from access_token where");
		sb.append(" app_Id = '").append(appid).append("'");
		sb.append(" and app_secret = '").append(app_secret).append("'");
		AccessToken token = null;
		try {
			conn = DBConnPool.getConnection();
			st = conn.prepareStatement(sb.toString());
			rs = st.executeQuery();
			if (rs.next()) {
				token = new AccessToken();
				token.setAid(rs.getString("aid"));
				token.setApp_Id(rs.getString("app_Id"));
				token.setApp_secret(rs.getString("app_secret"));
				token.setAccess_token(rs.getString("access_token"));
				token.setCre_time(rs.getString("cre_time"));
			}
			if (token != null) {
				daoBack.run(token);
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
					st = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return token;
	}
	
	
	public IDaoBack getDaoBack() {
		return daoBack;
	}
	public void setDaoBack(IDaoBack daoBack) {
		this.daoBack = daoBack;
	}
}
