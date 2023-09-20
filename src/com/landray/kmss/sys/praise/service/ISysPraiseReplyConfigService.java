package com.landray.kmss.sys.praise.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.service.IBaseService;

public interface ISysPraiseReplyConfigService extends IBaseService{

	// 查看页面获取数据
	public void view(HttpServletRequest request, HttpServletResponse response) throws Exception;

	// 查询是否开启默认回复
	public Boolean isOpenReply() throws Exception;

	// 查询默认回复内容
	public String checkReplyText() throws Exception;

	// 修改配置
	public void updateReplyConfig(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
