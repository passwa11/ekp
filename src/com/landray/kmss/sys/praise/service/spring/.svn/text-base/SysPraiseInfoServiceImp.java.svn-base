package com.landray.kmss.sys.praise.service.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.constant.SysNotifyConstants;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoForm;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseInfo;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseInfoAfterDone;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseInfoTitleService;
import com.landray.kmss.sys.praise.model.SysPraiseInfo;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoService;
import com.landray.kmss.sys.praise.util.SysPraiseInfoTypeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class SysPraiseInfoServiceImp extends ExtendDataServiceImp
		implements ISysPraiseInfoService {

	private ISysAppConfigService sysAppConfigService;

	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
			sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
					.getBean("sysAppConfigService");
		}
		return sysAppConfigService;
	}

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	@Override
    public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		SysPraiseInfoForm sysPraiseInfoForm = (SysPraiseInfoForm) form;
		String fdModelName = sysPraiseInfoForm.getFdTargetName();
		String fdModelId = sysPraiseInfoForm.getFdTargetId();
		SysDictModel dictModel =
				SysDataDict.getInstance().getModel(fdModelName);
		if (dictModel != null) {
			String serviceName = dictModel.getServiceBean();
			IBaseService service =
					(IBaseService) SpringBeanUtil.getBean(serviceName);
			if (service instanceof ISysPraiseInfoTitleService) {
				JSONObject object = ((ISysPraiseInfoTitleService) service)
						.getSysPraiseInfoSourceInfo(fdModelName, fdModelId);
				if (object.containsKey("fdSourceId")
						&& object.containsKey("fdSourceName")) {
					sysPraiseInfoForm
							.setFdSourceId(object.getString("fdSourceId"));
					sysPraiseInfoForm
							.setFdSourceName(object.getString("fdSourceName"));
				} else {
					sysPraiseInfoForm.setFdSourceId(fdModelId);
					sysPraiseInfoForm.setFdSourceName(fdModelName);
				}
				if (object.containsKey("fdTargetPersonId")
						&& object.containsKey("fdTargetPersonName")) {
					sysPraiseInfoForm.setFdTargetPersonId(
							object.getString("fdTargetPersonId"));
					sysPraiseInfoForm.setFdTargetPersonName(
							object.getString("fdTargetPersonName"));
				}
			}
			IBaseModel baseModel =
					service.findByPrimaryKey(fdModelId, null, true);
			if (baseModel != null && baseModel instanceof ISysPraiseInfo) {
				ISysPraiseInfo sysPraiseInfo = (ISysPraiseInfo) baseModel;
				sysPraiseInfo.setDocPraiseInfoCount(
						sysPraiseInfo.getDocPraiseInfoCount() + 1);
				service.update(sysPraiseInfo);
				if (service instanceof ISysPraiseInfoAfterDone) {
					JSONObject object = new JSONObject();
					object.accumulate("fdModelId", fdModelId);
					((ISysPraiseInfoAfterDone) service).updateAfterDone(object);
				}
			}
		}
		if (StringUtil.isNull(fdModelId) && StringUtil
				.isNotNull(sysPraiseInfoForm.getFdTargetPersonId())) {
			fdModelId = sysPraiseInfoForm.getFdTargetPersonId();
			fdModelName =
					"com.landray.kmss.sys.organization.model.SysOrgElement";
			sysPraiseInfoForm.setFdTargetId(fdModelId);
			sysPraiseInfoForm.setFdTargetName(fdModelName);
		}
		if (StringUtil.isNull(sysPraiseInfoForm.getFdSourceId())) {
			sysPraiseInfoForm.setFdSourceId(fdModelId);
			sysPraiseInfoForm.setFdSourceName(fdModelName);
		}
		UserOperHelper.logAdd(getModelName());
		return super.add(sysPraiseInfoForm, requestContext);
	}

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		String fdId = super.add(modelObj);
		SysPraiseInfo sysPraiseInfo = (SysPraiseInfo) modelObj;
		sendNotifyToTarget(sysPraiseInfo);
		return fdId;
	}

	private void sendNotifyToTarget(SysPraiseInfo sysPraiseInfo)
			throws Exception {
		SysOrgElement fdPraisePerson = sysPraiseInfo.getFdPraisePerson();
		SysOrgElement fdTargetPerson = sysPraiseInfo.getFdTargetPerson();
		if (fdPraisePerson != null && fdTargetPerson != null
				&& !fdTargetPerson.getFdId().equals(fdPraisePerson.getFdId())) {
			List<SysOrgElement> list = new ArrayList<SysOrgElement>();
			list.add(fdTargetPerson);
			HashMap<String, String> replaceMap = new HashMap<String, String>();
			if (StringUtil.isNotNull(sysPraiseInfo.getFdIsHideName())
					&& "true".equals(sysPraiseInfo.getFdIsHideName())) {
				replaceMap.put("fdPraiser", ResourceUtil
						.getString("sys-praise:sysPraiseInfo.name.hide"));
			} else {
				replaceMap.put("fdPraiser", fdPraisePerson.getFdName());
			}

			NotifyContext notifyContext = sysNotifyMainCoreService.getContext("sys-praise:sysPraiseInfo.notify");
			notifyContext.setNotifyType(SysNotifyConstants.NOTIFY_QUEUE_TYPE_TODO);
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setNotifyTarget(list);
			notifyContext.setLink(
					"/sys/praise/sys_praise_info/sysPraiseInfo.do?method=view&fdId="
							+ sysPraiseInfo.getFdId());
			if (StringUtil.isNotNull(sysPraiseInfo.getFdIsHideName())
					&& "true".equals(sysPraiseInfo.getFdIsHideName())) {
				SysOrgPerson sysOrgElement = (SysOrgPerson) sysOrgPersonService
						.findByPrimaryKey("1183b0b84ee4f581bba001c47a78b2d9");
				notifyContext.setDocCreator(sysOrgElement);
			}

			List<String> notifyList = Arrays.asList(sysPraiseInfo.getFdNotifyType().split(";"));
			if(notifyList.contains(SysNotifyConstants.NOTIFY_QUEUE_TYPE_TODO)){
				notifyContext.setNotifyType(SysNotifyConstants.NOTIFY_QUEUE_TYPE_TODO);
				sysNotifyMainCoreService.send(sysPraiseInfo, notifyContext, replaceMap);
			}
			if(notifyList.contains(SysNotifyConstants.NOTIFY_QUEUE_TYPE_MAIL)){
				notifyContext.setNotifyType(SysNotifyConstants.NOTIFY_QUEUE_TYPE_MAIL);
				sysNotifyMainCoreService.send(sysPraiseInfo, notifyContext, replaceMap);
			}
			if(notifyList.contains(SysNotifyConstants.NOTIFY_QUEUE_TYPE_MOBILE)){
				notifyContext.setNotifyType(SysNotifyConstants.NOTIFY_QUEUE_TYPE_MOBILE);
				sysNotifyMainCoreService.send(sysPraiseInfo, notifyContext, replaceMap);
			}
		}

	}

	@Override
	public List<String> getExtendTypes() {
		return SysPraiseInfoTypeUtil.getExtendList();
	}

	@Override
	public Integer getPraiseNum(HttpServletRequest request) throws Exception {
		Integer praiseNum = 0;
		String fdModelId = request.getParameter("fdModelId");
		String fdModelName = request.getParameter("fdModelName");
		if (StringUtil.isNotNull(fdModelId)
				&& StringUtil.isNotNull(fdModelName)) {
			SysDictModel dictModel =
					SysDataDict.getInstance().getModel(fdModelName);
			if (dictModel != null) {
				String serviceName = dictModel.getServiceBean();
				IBaseService service =
						(IBaseService) SpringBeanUtil.getBean(serviceName);
				IBaseModel baseModel =
						service.findByPrimaryKey(fdModelId, null, true);
				if (baseModel != null && baseModel instanceof ISysPraiseInfo) {
					praiseNum = ((ISysPraiseInfo) baseModel)
							.getDocPraiseInfoCount();
				}
			}
		}
		return praiseNum;
	}

	@Override
	public Integer getSumRich(Date fdStart, String userId, String fdType)
			throws Exception {
		Integer rich = 0;

		String fdTarget = " fd_praise_person_id ";
		if (StringUtil.isNull(userId)) {
			userId = UserUtil.getUser().getFdId();
		}
		if (StringUtil.isNotNull(fdType) && "earn".equals(fdType)) {
			fdTarget = " fd_target_person_id ";
		}
		String sql =
				"select sum(fd_rich) as sum_rich from sys_praise_info where "
						+ fdTarget + " = :userId and fd_type = 2 ";
		List list = null;
		if (fdStart == null) {
			list = getBaseDao().getHibernateSession().createNativeQuery(sql)
					.setParameter("userId", userId).list();
		} else {
			sql += "and doc_create_time >=:fdStart";
			list = getBaseDao().getHibernateSession().createNativeQuery(sql)
					.setParameter("userId", userId)
					.setTimestamp("fdStart", fdStart).list();
		}

		if (list.size() > 0 && list.get(0) != null) {
			rich = Integer.parseInt(list.get(0).toString());
		}
		return rich;
	}

	@Override
	public List<SysPraiseInfo> getInfoByParams(Date lastTime, Date nowTime,
			boolean withoutSelf) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " 1=1 ";
		if (lastTime != null) {
			whereBlock += " and sysPraiseInfo.docCreateTime >= :lastTime ";
			hqlInfo.setParameter("lastTime", lastTime);
		}
		if (nowTime != null) {
			whereBlock += " and sysPraiseInfo.docCreateTime < :nowTime ";
			hqlInfo.setParameter("nowTime", nowTime);
		}
		if (withoutSelf) {
			whereBlock +=
					" and sysPraiseInfo.fdPraisePerson.fdId != sysPraiseInfo.fdTargetPerson.fdId";
		}
		hqlInfo.setWhereBlock(whereBlock);
		List<SysPraiseInfo> rtnList = findValue(hqlInfo);
		return rtnList;
	}

	/**
	 * 获取上次更新的时间
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
    public Date getLastExecuteTime() throws Exception {
		String fdKey =
				"com.landray.kmss.sys.praise.service.spring.SysPraiseInfoDetailServiceImp";
		String fdField = "fdUpdateTime";
		SysAppConfig sysAppConfig = (SysAppConfig)getSysAppConfigService().findFirstOne(
				"fdKey='" + fdKey + "' and fdField='" + fdField + "'", null);
		if (sysAppConfig == null) {
			return null;
		} else {
			if (StringUtil.isNull(sysAppConfig.getFdValue())) {
				return null;
			} else {
				return DateUtil.convertStringToDate(sysAppConfig.getFdValue(),
						DateUtil.PATTERN_DATETIME);
			}
		}
	}

	@Override
    public void saveReply(HttpServletRequest request) throws Exception {

		String fdId = request.getParameter("modelId");
		SysPraiseInfo sysPraiseInfo = null;

		if (StringUtil.isNotNull(fdId)) {
			sysPraiseInfo = (SysPraiseInfo) this.findByPrimaryKey(fdId);
		}

		if (sysPraiseInfo != null) {

			String replyContent = request.getParameter("replyContent");

			Date now = new Date();

			if (UserOperHelper.allowLogOper("saveReply", getModelName())) {
				UserOperContentHelper.putUpdate(sysPraiseInfo)
						.putSimple("replyContent",
								sysPraiseInfo.getReplyContent(), replyContent)
						.putSimple("replyTime", sysPraiseInfo.getReplyTime(),
								now);
			}

			sysPraiseInfo.setReplyContent(replyContent);
			sysPraiseInfo.setReplyTime(now);
			this.update(sysPraiseInfo);

			// 发送待办
			sendCheckPraiseNotify(sysPraiseInfo);
		}
	}

	private void sendCheckPraiseNotify(SysPraiseInfo sysPraiseInfo)
			throws Exception {
		// 开启自动回执，被赞者阅读，发送待办
		if (sysPraiseInfo.getIsReply() && UserUtil.getUser().getFdId()
				.equals(sysPraiseInfo.getFdTargetPerson().getFdId())) {
			this.addCheckPraiseNotify(sysPraiseInfo);
		}
	}

	@Override
    public void addCheckPraiseNotify(SysPraiseInfo sysPraiseInfo)
			throws Exception {
		SysOrgElement fdPraisePerson = sysPraiseInfo.getFdPraisePerson();
		SysOrgElement fdTargetPerson = sysPraiseInfo.getFdTargetPerson();
		if (fdPraisePerson != null && fdTargetPerson != null
				&& !fdTargetPerson.getFdId().equals(fdPraisePerson.getFdId())) {
			List<SysOrgElement> list = new ArrayList<SysOrgElement>();
			list.add(fdPraisePerson);
			HashMap<String, String> replaceMap = new HashMap<String, String>();
			replaceMap.put("praiseTargetName", fdTargetPerson.getFdName());
			String date = DateUtil.convertDateToString(new Date(),
					DateUtil.PATTERN_DATETIME);
			replaceMap.put("checkTime", date);

			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext("sys-praise:sysPraiseInfo.praiseCheckNotify");
			notifyContext
					.setNotifyType(SysNotifyConstants.NOTIFY_QUEUE_TYPE_TODO);
			notifyContext.setNotifyTarget(list);
			notifyContext.setLink(
					"/sys/praise/sys_praise_info/sysPraiseInfo.do?method=view&fdId="
							+ sysPraiseInfo.getFdId());

			sysNotifyMainCoreService.send(sysPraiseInfo, notifyContext,
					replaceMap);
		}

	}

}
