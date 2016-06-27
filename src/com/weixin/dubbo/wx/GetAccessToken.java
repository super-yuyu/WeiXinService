package com.weixin.dubbo.wx;

import org.json.JSONObject;

import com.weixin.dubbo.util.ConnetUtil;

public class GetAccessToken {
	ConnetUtil util;
	/**
	 * 获取微信授权ken
	 * @return
	 */
	public WXToken toAccessToKen(String grant_type, String appid, String secret){
		StringBuffer sb = new StringBuffer();
		sb.append("https://api.weixin.qq.com/cgi-bin/token?");
		sb.append("grant_type=").append(grant_type);
		sb.append("&appid=").append(appid);
		sb.append("&secret=").append(secret);
		String json = util.connet(sb.toString());
		if (json != null && !"".equals(json)) {
			WXToken entity = new WXToken();
			JSONObject obje = new JSONObject(json);
			String access_token = obje.getString("access_token");
			int expires_in = obje.getInt("expires_in");
			entity.setAccess_token(access_token);
			entity.setExpires_in(expires_in);
			return entity;
		}
		return null;
	}
	
	public ConnetUtil getUtil() {
		return util;
	}
	public void setUtil(ConnetUtil util) {
		this.util = util;
	}
}
