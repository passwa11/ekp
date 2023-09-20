package com.landray.kmss.km.signature.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNote;

/**
 * 印章库业务对象接口
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public interface IKmSignatureMainService extends IBaseService {
	/**
	 * 根据用户ID获取用户的签名图片地址
	 * 
	 * @param lbpmAuditNote
	 * @return
	 * @throws Exception
	 */
	public String getPicPath(LbpmAuditNote lbpmAuditNote) throws Exception;

	/**
	 * 根据用户ID获取用户可用的签名图片
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List showSig(String userId) throws Exception;

	/**
	 * 保存重置密码
	 * 
	 * @param id
	 *            签章ID
	 * @param newPassword
	 *            新密码
	 * @throws Exception
	 */
	public abstract void savePassword(String id, String newPassword,
			RequestContext requestContext) throws Exception;
	
	// 经过筛选器筛选后的文档hql（已权限处理）
	public HQLWrapper getDocHql(String whereBlock, String __joinBlock,
			HttpServletRequest request) throws Exception;
	/**
	 * 获取资产卡片的图片
	 */
	public String getCardPicIdsBySignatureId(String fdId) throws Exception;
	
	/**
	 * 置为无效
	 * 
	 * @param id
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateInvalidated(String id, RequestContext requestContext)
			throws Exception;

	/**
	 * 批量置为无效
	 * 
	 * @param ids
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateInvalidated(String[] ids, RequestContext requestContext)
			throws Exception;
	
	/**
	 * 批量启用
	 * 
	 * @param ids
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateValidated(String[] ids, RequestContext requestContext)
			throws Exception;
	/**
	 * 设置默认签章：对当前人 其他的个人签名自动置为为否，每个人仅一个默认个人签名
	 * @param currentSignatureId 当前签章对象
	 * @param userName 当前签章对象者
	 * @param needUpdateSignature 是否需要更新当前签章
	 * @throws Exception
	 */
	public void setDefaultSignature(String currentSignatureId,String userName,Boolean needUpdateSignature) throws Exception ;
	
	public void updateDefault(KmSignatureMain kmSignatureMain,Boolean needUpdateSignature) throws Exception ;
			
	/**
	 * 根据用户获得免密签名对象
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public List getAutoSignature(int count) throws Exception;
}
