package com.landray.kmss.sys.praise.interfaces;

import com.landray.kmss.common.model.IBaseModel;

public interface ISysPraiseMain extends IBaseModel{
	/*
	 * 设定点赞次数
	 */
	public void setDocPraiseCount(Integer docPraiseCount);

	/*
	 * 获得点赞次数
	 */
	public Integer getDocPraiseCount();
}
