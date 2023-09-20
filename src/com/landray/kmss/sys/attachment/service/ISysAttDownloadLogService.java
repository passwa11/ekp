package com.landray.kmss.sys.attachment.service;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.model.SysAttBase;

public interface ISysAttDownloadLogService extends IBaseService {
	// 未知
	static String ATT_DOWNLOAD_TYPE_UNKNOW = "unknow";
	// 手动点击下载
	static String ATT_DOWNLOAD_TYPE_MANUAL = "manual";

	/**
	 * 根据下载附件新增下载日志
	 * 
	 * @param atts
	 * @throws Exception
	 */
	public void addDownloadLogByAtt(SysAttBase att, RequestContext context)
			throws Exception;

	/**
	 * 根据下载附件列表新增下载日志
	 * 
	 * @param atts
	 * @throws Exception
	 */
	public void addDownloadLogByAttList(List<?> atts, RequestContext context)
			throws Exception;

	/**
	 * 根据主文档获得下载记录数
	 * 
	 * @param fdModelName
	 * @param fdModelId
	 * @return
	 * @throws Exception
	 */
	public Long getDownloadCount(String fdModelName, String fdModelId)
			throws Exception;

	/**
	 * 根据附件ID删除日志
	 * 
	 * @param attId
	 * @throws Exception
	 */
	public void deleteByAttId(String attId) throws Exception;

	/**
	 * 需要展示的附件
	 * 
	 * @param hqlInfo
	 */
	public void includeRecord(HQLInfo hqlInfo);
}
