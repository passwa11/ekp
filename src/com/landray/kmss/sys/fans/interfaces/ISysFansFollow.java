package com.landray.kmss.sys.fans.interfaces;

public interface ISysFansFollow extends ISysFansMain{
	/**
	 * 关注数
	 * @return
	 */
	Integer getFdAttentionNum();
	
	void setFdAttentionNum(Integer count);
}
