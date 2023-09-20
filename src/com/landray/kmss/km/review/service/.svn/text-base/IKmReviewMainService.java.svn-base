package com.landray.kmss.km.review.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.km.review.forms.KmReviewMainForm;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.sunbor.web.tag.Page;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌 审批文档基本信息业务对象接口
 */
public interface IKmReviewMainService extends IExtendDataService {
	/**
	 * 转移流程文档
	 * 
	 * @param fdId
	 *            文档ID
	 * @param categoryId
	 *            目标类ID
	 * @throws Exception
	 */
	public void updateDocumentCategory(String fdId, String categoryId)
			throws Exception;

	/**
	 * 修改流程文档权限
	 * 
	 * @param form
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateDocumentPermission(IExtendForm form,
			RequestContext requestContext) throws Exception;

	/**
	 * 批量转移文档
	 * 
	 * @param ids
	 * @param templateId
	 * @throws Exception
	 */
	public int updateDucmentTemplate(String ids, String templateId)
			throws Exception;

	/**
	 * 文档指定反馈人
	 * 
	 * @param main
	 * @param notifyTarget
	 * @throws Exception
	 */
	public void updateFeedbackPeople(KmReviewMain main, List notifyTarget)
			throws Exception;

	/**
	 * 复制文档附件，用于“复制流程”功能
	 * 
	 * @param newDoc
	 * @param oldDoc
	 * @throws Exception
	 */
	public void copyAttachment(KmReviewMainForm newForm, KmReviewMainForm oldForm)throws Exception;

	public String getCount(String type, Boolean isDraft) throws Exception;

	public String getCount(HQLInfo hqlInfo) throws Exception;

	public List getOrgAndPost(HttpServletRequest request, String[] orgIds) throws Exception;

	/**
	 * 流程门户部件数据源
	 * 
	 * @param request
	 *            请求
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> listPortlet(RequestContext request) throws Exception;

	/**
	 * 获取当前用户最近使用的模板列表
	 * 
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getRecentTemplate(int count)
			throws Exception;

	/**
	 * @Description 统计各种文档状态数量 @Param @param: hqlInfo @return
	 * java.util.List @throws
	 */
	List countAllStatus(HQLInfo hqlInfo) throws Exception;

	/**
	*实现功能描述:待我审核结果集
	*@param [requestContext]
	*@return com.sunbor.web.tag.Page
	*/
	Page listArrival(RequestContext requestContext) throws Exception;

	/**
	*实现功能描述:我已审核结果集
	*@param [requestContext]
	*@return com.sunbor.web.tag.Page
	*/
	Page listApproved(RequestContext requestContext) throws Exception;

	/**
	*实现功能描述:统计待我审总数
	*@param [requestContext]
	*@return int
	*/
	int getArrivalListUseTotal(RequestContext requestContext);

	/**
	 *实现功能描述:统计我已审总数
	 *@param [requestContext]
	 *@return int
	 */
	int getApprovedTotal(RequestContext requestContext);
}
