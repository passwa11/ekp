package com.landray.kmss.km.smissive.service;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.km.smissive.forms.KmSmissiveMainForm;
import com.landray.kmss.km.smissive.model.KmSmissiveMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 张鹏xn 公文管理业务对象接口
 */
public interface IKmSmissiveMainService extends IExtendDataService {
	/**
	 * 更新附件的权限
	 * 
	 * @param form
	 * @param requestContext
	 */
	void updateAttachmentRight(KmSmissiveMainForm form,
			RequestContext requestContext) throws Exception;

	/**
	 * 更新文件的权限
	 * 
	 * @param form
	 * @param requestContext
	 */
	void updateRight(KmSmissiveMainForm form, RequestContext requestContext)
			throws Exception;

	/**
	 * 传阅文档
	 * 
	 * @param form
	 * @param requestContext
	 * @throws Exception
	 */
	void addCirculation(KmSmissiveMainForm form, RequestContext requestContext)
			throws Exception;

	/**
	 * 修改签发人
	 * 
	 * @param form
	 * @param requestContext
	 * @throws Exception
	 */
	void updateIssuer(KmSmissiveMainForm form, RequestContext requestContext)
			throws Exception;
	
	public String  getdocNum(KmSmissiveMain kmSmissiveMain) throws Exception ;
	public String updateDocNum(HttpServletRequest request) throws Exception;
	public String getdocNumByNumberId(KmSmissiveMain kmSmissiveMain,String fdNumberId) throws Exception;

	public String initdocNumByNumberId(KmSmissiveMain kmSmissiveMain, String fdNumberId) throws Exception;

	/*
	 * 判断编号是否唯一
	 */
	public Boolean checkUniqueNum(String fdId,String TempId, String docNum) throws Exception;

	public void addWpsBookMarks(String fdModelId) throws Exception;

}
