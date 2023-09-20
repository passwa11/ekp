package com.landray.kmss.sys.evaluation.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.util.face.SysFaceConfig;
import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationModel;
import com.landray.kmss.sys.evaluation.model.SysEvaluationMain;
import com.landray.kmss.sys.evaluation.model.SysEvaluationReply;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationMainService;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationReplyService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.query.NativeQuery;

public class SysEvaluationReplyServiceImp extends BaseServiceImp implements
		ISysEvaluationReplyService {
	private ISysOrgCoreService sysOrgCoreService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	protected ISysEvaluationMainService sysEvaluationMainService;

	public void setSysEvaluationMainService(
			ISysEvaluationMainService sysEvaluationMainService) {
		this.sysEvaluationMainService = sysEvaluationMainService;
	}

	/**
	 * 
	 * 保存评论
	 */
	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String docContent = requestContext.getRequest().getParameter(
				"replyContent");
		String fdEvaluationId = requestContext.getParameter("fdEvaluationId");// 点评Id
		String fdModelName = requestContext.getParameter("modelName");
		if (StringUtil.isNull(fdModelName)) {
            fdModelName = SysEvaluationMain.class.getName();
        }
		String replyId = requestContext.getParameter("replyId");// 被回复的回复id
		String mainModelId = requestContext.getParameter("mainModelId");// 主文档Id
		String mainModelName = requestContext.getParameter("mainModelName");// 主文档modelName
		String evalMark = requestContext.getParameter("evalMark");// '0'：回复点评；'1'：回复特定回复

		SysEvaluationReply sysEvaluationReply = new SysEvaluationReply();
		sysEvaluationReply.setDocContent(docContent);
		sysEvaluationReply.setFdReplyTime(new Date());

		// 回复特定回复
		if (StringUtil.isNotNull(evalMark) && "1".equals(evalMark)) {
			if (StringUtil.isNotNull(replyId)) {
				sysEvaluationReply.setFdParentId(replyId);
				SysEvaluationReply parentReply = (SysEvaluationReply) getBaseDao()
						.findByPrimaryKey(replyId);
				sysEvaluationReply.setFdParentReplyer(parentReply
						.getFdReplyer());

				// 发送通知给回复者
				notifyReplyer(parentReply.getFdReplyer().getFdId(),
						mainModelId, mainModelName, requestContext);
			}
		} else {
			// 发送通知给点评者
			IBaseModel baseModel = (IBaseModel) getModelByIdName(
					fdEvaluationId, fdModelName);
			SysOrgElement fdEvaluator = (SysOrgElement) PropertyUtils
					.getProperty(baseModel, "fdEvaluator");
			notifyReplyer(fdEvaluator.getFdId(), mainModelId, mainModelName,
					requestContext);
		}
		if (StringUtil.isNotNull(fdEvaluationId)) {
			// 更新回复数
			updateReplyCount(fdModelName, fdEvaluationId, "ADD");

			sysEvaluationReply.setFdModelId(fdEvaluationId);
			sysEvaluationReply.setFdModelName(fdModelName);
		} else {
			return null;
		}
		sysEvaluationReply.setFdReplyer(UserUtil.getUser());

		sysEvaluationReply.setFdHierarchyId(getHierarchyId(sysEvaluationReply));
		
		logAdd(sysEvaluationReply);
		
		return super.add(sysEvaluationReply);
	}
	
	/**
	 * 记录新增日志
	 * @param sysEvaluationReply
	 */
	private void logAdd(SysEvaluationReply sysEvaluationReply) {
		if (UserOperHelper.allowLogOper("save", getModelName())) {
			UserOperContentHelper.putAdd(sysEvaluationReply, "docContent",
					"fdReplyTime", "fdParentId", "fdParentReplyer", "fdModelId",
					"fdModelName", "fdReplyer", "fdHierarchyId");
		}
	}

	/**
	 * 删除点评回复
	 */
	@Override
	public void deleteReply(String replyId, String evalId, String fdModelName)
			throws Exception {
		if (StringUtil.isNotNull(evalId)) {
			// 更新回复数
			updateReplyCount(fdModelName, evalId, "DELETE");
			// 删除回复
			if (StringUtil.isNotNull(replyId)) {
                this.delete(replyId);
            }
		}
	}

	/**
	 * 通过modelName和modelId更新对应模块回复数
	 */
	public void updateReplyCount(String fdModelName, String fdModelId,
			String updateType) throws Exception {
		String serviceName = SysDataDict.getInstance().getModel(fdModelName)
				.getServiceBean();
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(serviceName);
		BaseModel baseModel = (BaseModel) service.findByPrimaryKey(fdModelId);

		if (baseModel != null) {
			// 更新回复数
			Object value = PropertyUtils.getProperty(baseModel, "fdReplyCount");
			Integer fdReplyCount = 0;// 回复数
			if (value != null && !"".equals(value)) {
				if ("ADD".equals(updateType)) {
					fdReplyCount = Integer.parseInt(value.toString()) + 1;
				} else if ("DELETE".equals(updateType)) {
					fdReplyCount = Integer.parseInt(value.toString()) - 1;
				}
			}
			PropertyUtils.setProperty(baseModel, "fdReplyCount", fdReplyCount);
			service.getBaseDao().update(baseModel);
		}
	}

	/**
	 * 发送通知给被回复者
	 * 
	 * @param goalPersonIds
	 * @param modelName
	 *            点评/段落点评id
	 * @param mainModelId
	 *            主文档id
	 * @param requestContext
	 * @throws Exception
	 */
	public void notifyReplyer(String goalPersonIds, String mainModelId,
			String mainModelName, RequestContext requestContext)
			throws Exception {
		// 分享对象
		List orgList = sysOrgCoreService.findByPrimaryKeys(ArrayUtil
				.toStringArray(goalPersonIds.split(";")));
		List personList = sysOrgCoreService.expandToPerson(orgList);
		List sendList = new ArrayList();
		sendList.addAll(personList);

		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("sys-evaluation:sysEvaluationReply.notify");
		notifyContext.setNotifyType("todo");// 待办
		notifyContext.setNotifyTarget(sendList);
		ISysEvaluationModel mainModel = (ISysEvaluationModel) getModelByIdName(
				mainModelId, mainModelName);
		String link = ModelUtil.getModelUrl(mainModel);
		if (link != null){
			//代办URL里面增加点评标识，提供跳转页面特定需求下使用
			link=link+"&dueTo=evalution";
			notifyContext.setLink(link);
		}
		// HashMap replaceMap = new HashMap();
		// replaceMap.put("fdReplyer", UserUtil.getUser().getFdName());
		// replaceMap.put("docSubject", mainModel.getDocSubject());

		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceModel("fdReplyer", UserUtil.getUser(),
				"fdName");
		notifyReplace.addReplaceText("docSubject", mainModel.getDocSubject());

		sysNotifyMainCoreService.sendNotify(mainModel, notifyContext,
				notifyReplace);
	}

	/**
	 * 通过fdModelId 和 fdModelName得到model
	 * 
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	public IBaseModel getModelByIdName(String fdModelId, String fdModelName)
			throws Exception {
		String serviceBean = SysDataDict.getInstance().getModel(fdModelName)
				.getServiceBean();
		// 主model
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(serviceBean);
		IBaseModel baseModel = (IBaseModel) service.findByPrimaryKey(fdModelId,
				fdModelName, true);
		return baseModel;
	}

	/**
	 * 获取并封装父级回复信息
	 * 
	 * @param jsonArray
	 * @param parentReplyId
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONArray getParentReplyInfo(JSONArray jsonArray, String replyId,
										RequestContext requestContext) throws Exception {
		SysEvaluationReply evalReply = (SysEvaluationReply) this
				.findByPrimaryKey(replyId);
		String hierarchyId = evalReply.getFdHierarchyId();
		if (StringUtil.isNotNull(hierarchyId)) {
			String[] ids = hierarchyId.split("x");
			List<String> idList = new ArrayList<String>();
			for (int i = ids.length - 1; i > 0; i--) {
				if (StringUtil.isNotNull(ids[i])) {
					SysEvaluationReply parentReply = (SysEvaluationReply) this
							.findByPrimaryKey(ids[i]);
					JSONObject replyInfo = new JSONObject();
					replyInfo.accumulate("replyId", parentReply.getFdId());
					String replyerId = parentReply.getFdReplyer().getFdId();
					replyInfo.accumulate("replyerId", replyerId);
					String path = PersonInfoServiceGetter.getPersonHeadimageUrl(replyerId, "m");
					if (!PersonInfoServiceGetter.isFullPath(path)) {
						path = requestContext.getContextPath() + path;
					}
					replyInfo.accumulate("replyerImgUrl", path);
					replyInfo.accumulate("replyerName", parentReply
							.getFdReplyer().getFdName());
					if (parentReply.getFdParentReplyer() != null) {
						replyInfo.accumulate("parentReplyerId", parentReply
								.getFdParentReplyer().getFdId());
						replyInfo.accumulate("parentReplyerName", parentReply
								.getFdParentReplyer().getFdName());
					}
					replyInfo.accumulate("replyContent",
							SysFaceConfig.getUrl(requestContext.getRequest(), parentReply.getDocContent()));
					replyInfo.accumulate("replyTime", DateUtil
							.convertDateToString(parentReply.getFdReplyTime(),
									DateUtil.TYPE_DATETIME,
									requestContext.getLocale()));
					jsonArray.add(replyInfo);
				}
			}
		}
		return jsonArray;
	}

	public static final String HIERARCHY_ID_SPLIT = "x";

	public String getHierarchyId(SysEvaluationReply evalRelpy) throws Exception {
		String parentId = evalRelpy.getFdParentId();
		String idSplit = HIERARCHY_ID_SPLIT;
		if (StringUtil.isNotNull(parentId)) {
			SysEvaluationReply parentReply = (SysEvaluationReply) this
					.findByPrimaryKey(parentId);
			String parentHierId = parentReply.getFdHierarchyId();
			if (StringUtil.isNotNull(parentHierId)) {
				idSplit = parentHierId;
			}
		}
		return idSplit + evalRelpy.getFdId() + HIERARCHY_ID_SPLIT;
	}

	/**
	 * 更新子回复fdParentId和fdHierarchyId
	 * 
	 * @param replyId
	 * @throws Exception
	 */
	@Override
	public void updateSubReply(String replyId) throws Exception {
		List<String> hierarchyIdList = this.getBaseDao().getHibernateSession().createNativeQuery(
						"select b.fd_hierarchy_id from sys_evaluation_reply a,"
								+ "sys_evaluation_reply b where a.fd_id = ? and a.fd_parent_id = b.fd_id")
				.setParameter(0, replyId).list();

		int listSize = hierarchyIdList.size();
		String parentHierId = listSize > 0 ? hierarchyIdList.get(0) : "x";

		String driverClass = ResourceUtil
				.getKmssConfigString("hibernate.connection.driverClass");

		String subSql = " concat(?,concat(fd_id,'x')) ";
		if ("net.sourceforge.jtds.jdbc.Driver".equals(driverClass)) {
			subSql = " ? + fd_id + 'x' ";
		}
		NativeQuery nativeQuery = this.getBaseDao().getHibernateSession().createNativeQuery(
						"update sys_evaluation_reply set fd_parent_id = "
								+ "(select s.fd_parent_id from (select * from sys_evaluation_reply) s "
								+ "where s.fd_id = ?) , fd_hierarchy_id = "
								+ subSql + "  where fd_parent_id = ?");
		nativeQuery.setParameter(0, replyId);
		nativeQuery.setParameter(1, parentHierId);
		nativeQuery.setParameter(2, replyId);
		nativeQuery.addSynchronizedQuerySpace("sys_evaluation_reply");
		nativeQuery.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getReplyCount(String ids) throws Exception {
		String[] idss = ids.split(";");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysEvaluationReply.fdModelId,count(*)");
		hqlInfo.setWhereBlock("sysEvaluationReply.fdModelId in (:ids) group by sysEvaluationReply.fdModelId");
		hqlInfo.setParameter("ids", ArrayUtil.convertArrayToList(idss));
		return this.findValue(hqlInfo);
	}
}
