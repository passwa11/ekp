package com.landray.kmss.third.ding.provider;

import com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest.FormItemVo;
import com.dingtalk.api.request.OapiWorkrecordUpdateRequest;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskUpdateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordUpdateResponse;
import com.dingtalk.api.response.OapiWorkrecordAddResponse;
import com.dingtalk.api.response.OapiWorkrecordUpdateResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.dto.CorMode;
import com.landray.kmss.third.ding.model.*;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.*;
import com.landray.kmss.third.ding.util.*;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.taobao.api.ApiException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 单独处理钉钉高级审批表单待办任务(兼容处理)
 * @author chenhw
 *
 */
public class DingXformNotifyProvider {
	

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingXformNotifyProvider.class);
	
	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	public static DingXformNotifyProvider newInstance() {
		DingXformNotifyProvider dingXformNotifyProvider = null;
		try {
			dingXformNotifyProvider = new DingXformNotifyProvider();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return dingXformNotifyProvider;
	}

	private IThirdDingDinstanceXformService thirdDingDinstanceXformService;

	public IThirdDingDinstanceXformService getThirdDingDinstanceXformService() {
		if (thirdDingDinstanceXformService == null) {
			thirdDingDinstanceXformService = (IThirdDingDinstanceXformService) SpringBeanUtil
					.getBean("thirdDingDinstanceXformService");
		}
		return thirdDingDinstanceXformService;
	}

	private IThirdDingDtaskXformService thirdDingDtaskXformService;

	public IThirdDingDtaskXformService getThirdDingDtaskXformService() {
		if (thirdDingDtaskXformService == null) {
			return (IThirdDingDtaskXformService) SpringBeanUtil
					.getBean("thirdDingDtaskXformService");
		}
		return thirdDingDtaskXformService;
	}

	public void setThirdDingDtaskXformService(
			IThirdDingDtaskXformService thirdDingDtaskXformService) {
		this.thirdDingDtaskXformService = thirdDingDtaskXformService;
	}

	public void setThirdDingDinstanceXformService(
			IThirdDingDinstanceXformService thirdDingDinstanceXformService) {
		this.thirdDingDinstanceXformService = thirdDingDinstanceXformService;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}
	
	private IOmsRelationService omsRelationService;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil
					.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	public void setOmsRelationService(IOmsRelationService omsRelationService) {

		this.omsRelationService = omsRelationService;
	}
	

	private IThirdDingNotifyService thirdDingNotifyService;

	public void setThirdDingNotifyService(IThirdDingNotifyService thirdDingNotifyService) {
		this.thirdDingNotifyService = thirdDingNotifyService;
	}

	private IThirdDingXformNotifyLogService thirdDingXformNotifyLogService;

	public IThirdDingXformNotifyLogService getThirdDingXformNotifyLogService() {
		if (thirdDingXformNotifyLogService == null) {
			thirdDingXformNotifyLogService = (IThirdDingXformNotifyLogService) SpringBeanUtil
					.getBean("thirdDingXformNotifyLogService");
		}
		return thirdDingXformNotifyLogService;
	}

	public void setThirdDingXformNotifyLogService(
			IThirdDingXformNotifyLogService thirdDingXformNotifyLogService) {
		this.thirdDingXformNotifyLogService = thirdDingXformNotifyLogService;
	}

	public void setSysNotifyTodoService(ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}

	private IThirdDingDtemplateService thirdDingDtemplateService = null;

	public IThirdDingDtemplateService getThirdDingDtemplateService() {
		if (thirdDingDtemplateService == null) {
			thirdDingDtemplateService = (IThirdDingDtemplateService) SpringBeanUtil
					.getBean("thirdDingDtemplateService");
		}
		return thirdDingDtemplateService;
	}
	
	private Map<String, ThirdDingDtemplate> templatesMap = null;

	private Map<String, List<ThirdDingTemplateDetail>> detailsMap = null;

	public Map<String, ThirdDingDtemplate> getTemplatesMap() {
		return templatesMap;
	}

	public void setTemplatesMap(Map<String, ThirdDingDtemplate> templatesMap) {
		this.templatesMap = templatesMap;
	}

	private ThirdDingDtemplate defaultTemplate = null;

	public ThirdDingDtemplate getDefaultTemplate() {
		return defaultTemplate;
	}

	public void setDefaultTemplate(ThirdDingDtemplate defaultTemplate) {
		this.defaultTemplate = defaultTemplate;
	}

	//场景一：ekp把待办删除,待办接收人应该全部更新为完成
	public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		logger.warn("ekp把待办删除,待办接收人应该全部更新为完成");
		updateWorkrecord(todo, null, 1);
	}

	public void remove(SysNotifyTodo todo) throws Exception {
		// todo.gets
		logger.debug(" remove ：" + todo.getFdId() + "," + todo.getFdSubject()
				+ "  fdType:" + todo.getFdType());
		updateWorkrecord(todo, null, 1);

		// 先判断是否是催办，流程催办是先移除待办，然后新建 #62340
		if (!isReminder(todo)) {
			// 更新实例
			logger.warn("废弃、删除文档时，更新待办实例");
			updateInstance(todo);
		} else {
			logger.debug("-------------催办不更新实例状态------------------");
		}

	}
	
	private boolean isReminder(SysNotifyTodo todo) {
		try {
			String modelId = todo.getFdModelId();
			if ("com.landray.kmss.km.review.model.KmReviewMain"
					.equals(todo.getFdModelName())
					&& ("true".equals(
							DingConfig.newInstance().getAttendanceEnabled())
							|| "true".equals(DingConfig.newInstance()
									.getDingSuitEnabled()))) {

				IBaseService obj = (IBaseService) SpringBeanUtil
						.getBean("kmReviewMainService");
				HQLInfo hql = new HQLInfo();
				hql.setWhereBlock("fdId=:fdId");
				hql.setParameter("fdId", modelId);
				Object kmReviewMainObject = obj.findFirstOne(hql);
				if (kmReviewMainObject == null) {
					return false;
				}
				Class clazz = kmReviewMainObject.getClass();
				Method method = clazz.getMethod("getDocStatus");
				String docStatus = (String) method
						.invoke(kmReviewMainObject);
				if ("20".equals(docStatus)) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}

		return false;
	}

	// 更新实例
	private void updateInstance(SysNotifyTodo todo) {
		try {
			String modelName = todo.getFdModelName();
			logger.debug("modelName:" + modelName);
			if ("com.landray.kmss.km.review.model.KmReviewMain"
							.equals(modelName)) {
				logger.warn("-------开启钉钉审批高级版，审批结束，开始更新实例-------");
				// 找到对应的实例
				String docSubject = todo.getFdSubject();
				logger.warn("流程标题=>" + docSubject);
				String reviewMainId = todo.getFdModelId();
				logger.warn("流程主文档fdId=>" + reviewMainId);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdEkpInstanceId =:fdEkpInstanceId and fdStatus=:fdStatus");
				hqlInfo.setParameter("fdEkpInstanceId", reviewMainId);
				hqlInfo.setParameter("fdStatus", "20");
				List<ThirdDingDinstanceXform> list = getThirdDingDinstanceXformService()
						.findList(hqlInfo);
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						ThirdDingDinstanceXform dinstanceXform = list.get(i);
						OapiProcessWorkrecordUpdateResponse response = DingNotifyUtil
								.updateInstanceState(
										DingUtils.dingApiService
												.getAccessToken(),
										dinstanceXform.getFdInstanceId(),
										Long.valueOf(list.get(i).getFdTemplate()
												.getFdAgentId()),
										dinstanceXform.getFdEkpUser().getFdId(),
										false);

						if (response != null && response.getErrcode() == 0) {
							logger.debug("更新实例成功");
							dinstanceXform.setFdStatus("00");
							getThirdDingDinstanceXformService()
									.update(dinstanceXform);
						} else {
							logger.warn("更新实例失败！" + docSubject
									+ "   reviewMainId:" + reviewMainId);
						}
					}

				} else {
					logger.warn("没有找到需要更新的实例！    主题：" + docSubject
							+ "   reviewMainId:" + reviewMainId);
				}

				return;
			}

		} catch (Exception e) {
			logger.error("更新实例状态失败", e);
		}
	}

	public void setTodoDone(SysNotifyTodo todo) throws Exception {
		updateWorkrecord(todo, null, 1);
	}

	//场景二：ekp把待办中部分人员设置为已办
	public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		List list = new ArrayList();
		list.add(person);
		updateWorkrecord(todo, list, 2);
	}

	public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		updateWorkrecord(todo, persons, 2);
	}
	
	//场景三：ekp更新待办
	public void updateTodo(SysNotifyTodo todo) throws Exception {

	}

	private void updateWorkrecord(SysNotifyTodo todo, List<SysOrgElement> persons, int type) throws ApiException, Exception {
		long time = System.currentTimeMillis();
		try {
			logger.debug("------更新待办----  type：" + type);
			/*if (todo.getFdType() == 3 && type == 3) {
				logger.debug("当前待办(待办名称：" + todo.getFdSubject() + ")为暂挂，直接跳过不更新钉钉的待办");
				return;
			}*/
			String where = "fdStatus!='22'";
			if (persons == null || persons.size() == 0) {
				where += " and fdEkpTaskId='" + todo.getFdId() + "'";
			} else {
				where += " and fdEkpTaskId='" + todo.getFdId() + "'";
				StringBuffer ids = new StringBuffer();
				for (SysOrgElement target : persons) {
					if (target == null) {
                        continue;
                    }
					ids.append("'" + target.getFdId() + "',");
				}
				String fdIds = null;
				if (ids.length() > 0 && ids.toString().endsWith(",")) {
                    fdIds = ids.toString().substring(0, ids.length() - 1);
                }
				if (StringUtil.isNotNull(fdIds)) {
					where += " and fdEkpUser.fdId in (" + fdIds + ")";
				}
			}
			Map<String, String> tpidMap = new HashMap<String, String>();
			if(type == 3){
				SysNotifyTodo rtodo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(todo.getFdId(), null, true);
				if (rtodo != null){
					List<SysOrgElement> targets = rtodo.getHbmTodoTargets();
					for (SysOrgElement ele : targets) {
						tpidMap.put(ele.getFdId(), ele.getFdId());
					}
				}
			}
			TransactionStatus status = null;
			try {
				status = TransactionUtils
						.beginNewTransaction();
				List<ThirdDingDtaskXform> notifies = getThirdDingDtaskXformService()
						.findList(where, null);
				if (notifies != null && notifies.size() > 0) {
					for (ThirdDingDtaskXform task : notifies) {
						logger.debug("更新处理人(名称:" + task.getFdEkpUser().getFdId()
								+ ")的钉钉待办(待办名称：" + todo.getFdSubject()
								+ ",主键：" + todo.getFdId() + ")");
						if (StringUtil.isNull(task.getFdTaskId())) {
                            continue;
                        }
						// 当前人还在待办中直接跳过
						if (tpidMap.containsKey(task.getFdEkpUser().getFdId())
								&& type == 3) {
                            continue;
                        }
						String token = DingUtils.getDingApiService()
								.getAccessToken();
						OapiProcessWorkrecordTaskUpdateRequest req = new OapiProcessWorkrecordTaskUpdateRequest();
						OapiProcessWorkrecordTaskUpdateResponse response = DingNotifyUtil
								.updateTask(req, token,
										task.getFdInstance().getFdInstanceId(),
										Long.parseLong(task.getFdTaskId()),
										Long.parseLong(DingConfig.newInstance()
												.getDingAgentid()),
										task.getFdEkpUser() == null ? null
												: task.getFdEkpUser()
														.getFdId());
						if (response.getErrcode() == 0) {
							task.setFdStatus("22");
							logger.debug("更新待办状态成功！");

						} else {
							task.setFdStatus("21");
							logger.error(
									"更新钉钉待办状态异常，错误信息：" + response.getBody());
						}
						task.setFdDesc(response.getBody());
						getThirdDingDtaskXformService().update(task);
						JSONObject jo = JSONObject
								.fromObject(response.getBody());
						addNotifyLog(todo, jo,
								JSONObject.fromObject(req).toString());
					}
				} else {
					logger.debug("更新待办(待办名称：" + todo.getFdSubject() + ",主键："
							+ todo.getFdId() + ")在钉钉待办任务中找不到对应的数据");
				}
				TransactionUtils.commit(status);
			} catch (Exception e1) {
				if (status != null) {
					try {
						TransactionUtils.getTransactionManager()
								.rollback(status);
					} catch (Exception ex) {
						logger.error("---事务回滚出错---", ex);
					}
				}
			}
			// 老接口历史数据的更新
			// updateWorkrecordOld(todo, persons);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新钉钉待办状态异常，错误信息：", e);
		}
		logger.debug("更新钉钉待办耗时：" + (System.currentTimeMillis() - time) + "毫秒");
	}

	private void addNotifyLog(SysNotifyTodo todo, JSONObject jo, String reqData){
		try {
			ThirdDingXformNotifyLog notifylog = new ThirdDingXformNotifyLog();
			notifylog.setDocSubject(todo.getFdSubject());
			notifylog.setFdNotifyData(reqData);
			notifylog.setFdSendTime(new Date());
			notifylog.setFdRtnMsg(jo.toString());
			notifylog.setFdNotifyId(todo.getFdId());
			notifylog.setFdRtnTime(new Date());
			getThirdDingXformNotifyLogService().add(notifylog);

		} catch (Exception e) {
			logger.error("添加日志发生异常：",e);
		}
	}

	private IThirdDingErrorService thirdDingErrorService;

	public void setThirdDingErrorService(IThirdDingErrorService thirdDingErrorService) {
		this.thirdDingErrorService = thirdDingErrorService;
	}


	public boolean handleError(ThirdDingError error) throws Exception {
		boolean flag = true;
		JSONObject json = JSONObject.fromObject(error.getFdMethodParam());
		try {
			String type = json.getString("type");
			if("notifyUpdate".equals(type)){
				flag = sendUpdateErrorNotify(error.getFdContent());
			}else if("message".equals(type)){
				flag = sendErrorMesssage(error.getFdContent());
			}else if("notify".equals(type)){
				flag = sendErrorNotify(error.getFdContent());
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error("处理数据异常：", e);
		}
		return flag;
	}
	
	private boolean sendErrorMesssage(String content) throws Exception {
		boolean flag = true;
		if (StringUtil.isNull(content)) {
			logger.debug("通知内容为空，不处理...");
			return flag;
		}
		JSONObject json = JSONObject.fromObject(content);
		// 待办通知已经处理则不进行重复发送
		if (existNotify(json)) {
            return flag;
        }
		CorMode corMode = new CorMode();
		corMode.setCorpid(DingUtil.getCorpId());
		String secret = DingConfig.newInstance().getDingCorpSecret();
		if (StringUtil.isNotNull(secret)) {
			corMode.setCorpsecret(DingConfig.newInstance().getDingCorpSecret());
		} else {
			corMode.setCorpsecret(DingConfig.newInstance().getAppSecret());
		}
		String tokenId = TokenUtils.getToken(corMode);
		if (StringUtils.isNotEmpty(tokenId)) {
			String send_message_url = DingConstant.DING_PREFIX
					+ "/message/send?access_token=ACCESS_TOKEN"
					+ DingUtil.getDingAppKeyByEKPUserId("&", null);
			String url = send_message_url.replace("ACCESS_TOKEN", tokenId);
			logger.debug("钉钉接口：" + url);
			String result = DingHttpClientUtil.httpPost(url, JSONObject.fromObject(content), "errcode", String.class);
			if (!"0".equals(result)) {
				logger.error("消息发送失败...");
				flag = false;
			} else {
				logger.debug("消息发送成功...");
				flag = true;
			}
		}
		return flag;
	}
	
	private boolean sendUpdateErrorNotify(String content) throws Exception {
		boolean flag = true;
		if (StringUtil.isNull(content)) {
			logger.debug("待办通知内容为空，不处理...");
			return flag;
		}
		JSONObject json = JSONObject.fromObject(content);
		// 待办通知已经处理则不进行重复发送
		if (existNotify(json)) {
            return flag;
        }
		ThirdDingNotify notify = (ThirdDingNotify) JSONObject.toBean(JSONObject.fromObject(content),
				ThirdDingNotify.class);
		DingApiService dingService = DingUtils.getDingApiService();
		String url = DingConstant.DING_PREFIX + "/topapi/workrecord/update"
				+ DingUtil.getDingAppKeyByEKPUserId("?",
						notify.getFdEkpUserId());
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
		req.setUserid(notify.getFdDingUserId());
		req.setRecordId(notify.getFdRecordId());
		OapiWorkrecordUpdateResponse rsp = client.execute(req, dingService.getAccessToken());
		logger.debug("钉钉待办删除详细：" + rsp.getBody());
		JSONObject jo = JSONObject.fromObject(rsp.getBody());
		if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
			thirdDingNotifyService.delete(notify);
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	
	private boolean sendErrorNotify(String content) throws Exception {
		boolean flag = true;
		if (StringUtil.isNull(content)) {
			logger.debug("钉钉工作待办内容为空，不处理...");
			return flag;
		}
		JSONObject json = JSONObject.fromObject(content);
		// 待办已经处理则不进行重复发送
		if (existNotify(json)) {
            return flag;
        }
		String userid = json.getString("userid");
		String ekpid = json.getString("ekpid");
		String modelid = json.getString("modelid");
		String modelname = json.getString("modelname");
		String title = json.getString("title");
		String subject = json.getString("subject");
		String url = json.getString("url");
		DingApiService dingService = DingUtils.getDingApiService();
		String ding_url = DingConstant.DING_PREFIX + "/topapi/workrecord/add"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpid);
		logger.debug("钉钉接口：" + ding_url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(ding_url);
		OapiWorkrecordAddRequest req = new OapiWorkrecordAddRequest();
		req.setUserid(userid);
		req.setCreateTime((new Date()).getTime());
		req.setTitle(title);
		req.setUrl(url);
		List<FormItemVo> itemVos = new ArrayList<FormItemVo>();
		FormItemVo vo = new FormItemVo();
		vo.setTitle("标题");
		vo.setContent(subject);
		itemVos.add(vo);
		vo = new FormItemVo();
		vo.setTitle("创建时间");
		vo.setContent(DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		itemVos.add(vo);
		req.setFormItemList(itemVos);
		OapiWorkrecordAddResponse rsp = client.execute(req, dingService.getAccessToken());
		JSONObject jo = JSONObject.fromObject(rsp.getBody());
		if (jo.containsKey("errcode") && jo.getInt("errcode") == 0) {
			ThirdDingNotify dingNotify = new ThirdDingNotify();
			dingNotify.setFdDingUserId(userid);
			dingNotify.setFdEkpUserId(ekpid);
			dingNotify.setFdModelId(modelid);
			dingNotify.setFdModelName(modelname);
			dingNotify.setFdRecordId(jo.getString("record_id"));
			dingNotify.setDocCreateTime(new Date());
			dingNotify.setFdEKPDel("0");
			thirdDingNotifyService.add(dingNotify);
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	
	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}

	private boolean existNotify(JSONObject json) throws Exception {
		boolean flag = true;
		try{
			if(json.containsKey("modelid") && json.containsKey("ekpid")){
				String modelid = json.getString("modelid");
				String ekpid = json.getString("ekpid");
				SysNotifyTodo todo = (SysNotifyTodo) getSysNotifyTodoService().findByPrimaryKey(modelid, null, true);
				if(todo==null) {
                    return true;
                }
				if (todo != null && todo.getHbmTodoTargets() != null && todo.getHbmTodoTargets().size() > 0) {
					List<SysOrgElement> todoTargets = todo.getHbmTodoTargets();
					for (SysOrgElement ele : todoTargets) {
						if (ekpid.equals(ele.getFdId())) {
							flag = false;
							break;
						}
					}
				}
			}else{
				flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("判断待办是否存在发送异常：",e);
		}
		return flag;
	}

}
