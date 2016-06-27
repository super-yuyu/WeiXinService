package com.weixin.dubbo.sqldao;

import com.weixin.dubbo.vo.AccessToken;

public interface IDaoBack {
	public void run();
	public void run(AccessToken token);
}
