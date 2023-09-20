package com.landray.kmss.third.im.kk.provider;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyException;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.im.kk.KkConfig;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.constant.KkNotifyConstants;
import com.landray.kmss.third.im.kk.model.KkImNotify;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.service.IKkImNotifyService;
import com.landray.kmss.third.im.kk.util.NotifyConfigUtil;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class Kk5NotifyTodoProvider extends BaseSysNotifyProviderExtend {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Kk5NotifyTodoProvider.class);

	private static MultiThreadedHttpConnectionManager connectionManager;

	protected ISysNotifyTodoService sysNotifyTodoService;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		return sysNotifyTodoService;
	}

	public void setSysNotifyTodoService(
			ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}

	private MultiThreadedHttpConnectionManager getConnectionManager() {
		if (connectionManager == null) {
			connectionManager = new MultiThreadedHttpConnectionManager();
		}
		return connectionManager;
	}

	protected IKkImNotifyService kkImNotifyService;

	public void setKkImNotifyService(IKkImNotifyService kkImNotifyService) {
		this.kkImNotifyService = kkImNotifyService;
	}

	protected IKkImConfigService kkImConfigService;

	public void setKkImConfigService(IKkImConfigService kkImConfigService) {
		this.kkImConfigService = kkImConfigService;
	}

	public IPdaModuleConfigMainService pdaModuleConfigMainService;

	public void setPdaModuleConfigMainService(IPdaModuleConfigMainService pdaModuleConfigMainService) {
		this.pdaModuleConfigMainService = pdaModuleConfigMainService;
	}

	private static final String notify_url = "/third/im/kk/kkNotify.do?method=kkSkip&fdId=!{fdId}";

	@Override
    public void add(SysNotifyTodo todo, NotifyContext context) throws NotifyException {
		logger.debug("add:" + todo.getFdSubject() + "---"
				+ ((NotifyContextImp) context).getNotifyPersons().size());

			List<SysOrgPerson> notifyPersons = getNotifyPersons(todo, context);
		/*JSONObject jsonObjects = buildNotifyContentJson(todo, null,
				context);*/
		String url = new KkConfig().getValue(KeyConstants.KK_INNER_DOMAIN);
		logger.debug("url:" + url);
			//待办通知
		/*saveKkImNotify(notifyPersons, jsonObjects, KkNotifyConstants.SNED_NOTIFY__TODO,
				KkNotifyConstants.PUSH_USER_URL, todo);*/
		executePostDataKK5(todo ,todo.getFdSubject(),url + KkNotifyConstants.PUSH_USER_URL,
				buildNotifyContentJsons(notifyPersons, context, todo));

			//待办数通知
		Integer type = Integer.valueOf(todo.getFdType());
		if (type.equals(KkNotifyConstants.KK_NOTIFY_DO)) {
			executePostDataKK5(todo,todo.getFdSubject() + "(待办数)", url + KkNotifyConstants.PUSH_BADGEBATCH2_URL,
					buildNotifyBadgeJsons(notifyPersons, todo,0));
		}

		//sendNotifyCounts(todo, notifyPersons);


	}

	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws NotifyException {
		Integer type = Integer.valueOf(todo.getFdType());
		if (type.equals(KkNotifyConstants.KK_NOTIFY_DO)) {
			List<SysOrgPerson> notifyPersons = getNotifyPersons(todo, null);
			//删除队列表中记录
			/*kkImNotifyService.deleteByUserAll(todo.getFdId(), notifyPersons);
			
			sendNotifyCounts(todo, notifyPersons);*/
			String url = new KkConfig().getValue(KeyConstants.KK_INNER_DOMAIN);
			executePostDataKK5(todo,todo.getFdSubject() + "(待办数)", url + KkNotifyConstants.PUSH_BADGEBATCH2_URL,
					buildNotifyBadgeJsons(notifyPersons, todo,1));
		}

	}

	@Override
    public void remove(SysNotifyTodo todo) throws NotifyException {
		Integer type = Integer.valueOf(todo.getFdType());
		if (type.equals(KkNotifyConstants.KK_NOTIFY_DO)) {
			List<SysOrgPerson> notifyPersons = getNotifyPersons(todo, null);
			//删除队列表中记录
			/*kkImNotifyService.deleteByUserAll(todo.getFdId(), notifyPersons);
			sendNotifyCounts(todo, notifyPersons);*/
			String url = new KkConfig().getValue(KeyConstants.KK_INNER_DOMAIN);
			executePostDataKK5(todo,todo.getFdSubject() + "(待办数)", url + KkNotifyConstants.PUSH_BADGEBATCH2_URL,
					buildNotifyBadgeJsons(notifyPersons, todo,1));
		}


	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person) throws NotifyException {
		Integer type = Integer.valueOf(todo.getFdType());
		if (type.equals(KkNotifyConstants.KK_NOTIFY_DO)) {
			List<SysOrgPerson> notifyPersons = new ArrayList<SysOrgPerson>();
			notifyPersons.add(person);

			//删除队列表中记录
			/*kkImNotifyService.deleteByUserId(todo.getFdId(), person.getFdId());
			
			sendNotifyCounts(todo, notifyPersons);*/
			String url = new KkConfig().getValue(KeyConstants.KK_INNER_DOMAIN);
			executePostDataKK5(todo,todo.getFdSubject() + "(待办数)", url + KkNotifyConstants.PUSH_BADGEBATCH2_URL,
					buildNotifyBadgeJsons(notifyPersons, todo,1));
		}
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons) throws NotifyException {
		Integer type = Integer.valueOf(todo.getFdType());
		if (type.equals(KkNotifyConstants.KK_NOTIFY_DO)) {
			//删除队列表中记录
			/*kkImNotifyService.deleteByUserAll(todo.getFdId(), persons);
			
			sendNotifyCounts(todo, persons);*/
			String url = new KkConfig().getValue(KeyConstants.KK_INNER_DOMAIN);
			executePostDataKK5(todo,todo.getFdSubject() + "(待办数)", url + KkNotifyConstants.PUSH_BADGEBATCH2_URL,
					buildNotifyBadgeJsons(persons, todo,1));
		}
	}

	@Override
    public void setTodoDone(SysNotifyTodo todo) throws NotifyException {
		Integer type = Integer.valueOf(todo.getFdType());
		if (type.equals(KkNotifyConstants.KK_NOTIFY_DO)) {
			List<SysOrgPerson> notifyPersons = getNotifyPersons(todo, null);

			//删除队列表中记录
			/*kkImNotifyService.deleteByUserAll(todo.getFdId(), notifyPersons);
			sendNotifyCounts(todo, notifyPersons);*/
			String url = new KkConfig().getValue(KeyConstants.KK_INNER_DOMAIN);
			executePostDataKK5(todo,todo.getFdSubject() + "(待办数)", url + KkNotifyConstants.PUSH_BADGEBATCH2_URL,
					buildNotifyBadgeJsons(notifyPersons, todo,1));
		}
	}

	private void sendNotifyCounts(SysNotifyTodo todo,
			List<SysOrgPerson> notifyPersons) throws Exception {
		if (todo.getFdType() == 1 || todo.getFdType() == 3) {

			JSONObject jsonObjects = buildNotifyCountJson(todo, null, null, null, null,0);
			//保存更新待办数
			saveKkImNotify(notifyPersons, jsonObjects, KkNotifyConstants.UPDATE_NOTIFY__TODO_NUM,
					KkNotifyConstants.PUSH_BADGEBATCH2_URL, todo);
		}
	}


	/**
	 * <p>组装待办消息</p>
	 * @param todo
	 * @param notifyUsers
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	private JSONObject buildNotifyContentJson(SysNotifyTodo todo,
			JSONArray notifyUsers, NotifyContext context) {
		String subject = getSubject(todo);
		if (StringUtil.isNotNull(subject)) {
			subject = subject.replaceAll("\\t", " ").replaceAll("\\r\\n", " ")
					.replaceAll("\\n", " ");
		}
		String linkUrl = getLinkUrl(todo);
		JSONObject notifyInfo = new JSONObject();

		notifyInfo.accumulate("corp", KkNotifyConstants.KK5_CROP);
		// 消息接收器
		String fdAppReceiver = context.getFdAppReceiver();
		fdAppReceiver = fdAppReceiver != null
				&& fdAppReceiver.contains("kk_system")
						? KkNotifyConstants.KK5_RECEIVER2
						: KkNotifyConstants.KK5_RECEIVER;
		notifyInfo.accumulate("receiver", fdAppReceiver);
		notifyInfo.accumulate("userType", 1);

		notifyInfo.accumulate("users", notifyUsers);
		notifyInfo.accumulate("appType", 0);
		notifyInfo.accumulate("title", subject);
		notifyInfo.accumulate("content", StringUtil.isNotNull(context.getContent()) ? context.getContent() : subject);
		notifyInfo.accumulate("linkUrl", linkUrl);

		String openApp = findNotifyId();
		notifyInfo.accumulate("openApp", openApp);
		return notifyInfo;
	}

	private String getSubject(SysNotifyTodo todo) {
		String subject = null;
		KkConfig config = new KkConfig();
		String todoSource = config.getValue(KeyConstants.TODO_SOURCE);
		if ("true".equals(todoSource)) {
			if (StringUtil.isNotNull(todo.getFdAppName())) {
				subject = "【" + todo.getFdAppName() + "】" + todo.getFdSubject();
			} else {
				subject = todo.getFdSubject();
			}
		} else {
			subject = todo.getFdSubject();
		}
		return subject;
	}

	/**
	 * <p>组装待办消息数</p>
	 * @param todo
	 * @param notifyUsers
	 * @param alert
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	private JSONObject buildNotifyCountJson(SysNotifyTodo todo,
			JSONArray notifyUsers, JSONArray alert, JSONArray version, JSONArray lang,int send) {

		JSONObject notifyInfo = new JSONObject();

		notifyInfo.accumulate("corp", KkNotifyConstants.KK5_CROP);
		notifyInfo.accumulate("app", findNotifyId());

		notifyInfo.accumulate("users", notifyUsers);
		notifyInfo.accumulate("alert", alert);

		notifyInfo.accumulate("version", version);

		notifyInfo.accumulate("send", send);

		notifyInfo.accumulate("lang", lang);

		JSONObject message = new JSONObject();
		message.put("zh_CN", "您有{alert}条待办事项");
		notifyInfo.accumulate("message", message);

		return notifyInfo;
	}

	private List<SysOrgPerson> getNotifyPersons(SysNotifyTodo todo,
			NotifyContext context) {
		List<SysOrgPerson> notifyPersons = new ArrayList();
		if (context == null) {
			for (Iterator iter = todo.getHbmTodoTargets().iterator(); iter
					.hasNext();) {
				Object person = iter.next();
				if (person instanceof SysOrgPerson) {
					notifyPersons.add((SysOrgPerson) person);
				}
			}
		} else {
			notifyPersons = ((NotifyContextImp) context).getNotifyPersons();
		}
		return notifyPersons;
	}


	private String getLinkUrl(SysNotifyTodo todo) {
		String pre = NotifyConfigUtil.getNotifyUrlPrefix();
		String notifyUrl = null;
		if(StringUtil.isNotNull(todo.getFdId())){
			notifyUrl = pre + notify_url.replace("!{fdId}", todo.getFdId());
		}else{
			notifyUrl = pre + todo.getFdLink();
		}
		return notifyUrl;
	}

	/**
	 * <p>查询待办事宜 移动模块ID</p>
	 * @author 孙佳
	 * @throws Exception 
	 */
	private String findNotifyId() {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("fdId");
			hqlInfo.setWhereBlock(" fdUrlPrefix = 'sys/notify'");
			return (String) pdaModuleConfigMainService.findFirstOne(hqlInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}



	/**
	 * <p>保存通知到通知队列表中</p>
	 * @author 孙佳
	 * @param snedNotifyTodo 
	 * @param jsonObjects 
	 * @param snedNotifyTodo
	 * @param sendUrl
	 * @param todo
	 * @throws Exception
	 */
	private void saveKkImNotify(List<SysOrgPerson> notifyPersons, JSONObject jsonObjects, Integer snedNotifyTodo, String sendUrl, SysNotifyTodo todo)
			throws Exception {
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			KkImNotify modelObj = null;
			boolean flag = snedNotifyTodo.equals(KkNotifyConstants.UPDATE_NOTIFY__TODO_NUM) ? true : false;
			String subject = flag ? todo.getFdSubject() + "(待办数)" : todo.getFdSubject();
			//兼容消息id为空
			String fdId = null;
			if (StringUtil.isNull(todo.getFdId())) {
				fdId = IDGenerator.generateID();
			}
			for (SysOrgPerson sysOrgPerson : notifyPersons) {
				//无效的用户或者与业务无关的用户不推送待办
				if (!sysOrgPerson.getFdIsAvailable() || !sysOrgPerson.getFdIsBusiness()) {
					continue;
				}
				Date nowDate = new Date();
				modelObj = new KkImNotify();
				jsonObjects.put("users", sysOrgPerson.getFdLoginName());
				if (flag) {
					jsonObjects.put("alert", buildNotifyCountByPerson(sysOrgPerson, todo));
					jsonObjects.put("version", System.currentTimeMillis());
				}
				modelObj.setFdUserId(sysOrgPerson.getFdId());
				modelObj.setFdUserName(sysOrgPerson.getFdLoginName());
				modelObj.setFdNotifyId(StringUtil.isNull(todo.getFdId()) ? fdId : todo.getFdId()); //如果消息id为空，则使用自己生成的id
				modelObj.setFdType(snedNotifyTodo);
				modelObj.setFdSendTime(nowDate);
				modelObj.setFdFirstTime(nowDate);
				modelObj.setFdNotifyData(jsonObjects.toString());
				modelObj.setFdSubject(subject);
				modelObj.setFdSendUrl(sendUrl);
				modelObj.setFdStatus(KkNotifyConstants.NOTIFY_STATUS_WAIT);
				kkImNotifyService.add(modelObj);
			}
			TransactionUtils.getTransactionManager().commit(status);

		} catch (Exception e) {
			if (status != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(status);
				} catch (Exception ex) {
					logger.error("事务回滚出错", ex);
				}
			}
			logger.debug("", e);
		}
	}

	private Long buildNotifyCountByPerson(SysOrgPerson sysOrgPerson, SysNotifyTodo todo) {
		long num = sysNotifyTodoService.getTodoCount(sysOrgPerson.getFdId(),
				todo.getFdType() == 1 ? 13 : todo.getFdType());
		return num;

	}

	//---------------------------- 用NotifyException来重发待办----------------------------
	/**
	 * <p>组装待办消息</p>
	 * @param notifyPersons
	 * @param context
	 * @param todo
	 * @return
	 * @author 孙佳
	 */
	private List<JSONObject> buildNotifyContentJsons(List<SysOrgPerson> notifyPersons,
			NotifyContext context, SysNotifyTodo todo) {
		JSONArray notifyUsers = new JSONArray();
		List<JSONObject> listJson = new ArrayList<JSONObject>();
		for (SysOrgPerson sysOrgPerson : notifyPersons) {
			//无效的用户或者与业务无关的用户不推送待办
			if (!sysOrgPerson.getFdIsAvailable() || !sysOrgPerson.getFdIsBusiness()) {
				continue;
			}
			notifyUsers.add(sysOrgPerson.getFdLoginName());
			if (notifyUsers.size() % 100 == 0) {
				listJson.add(buildNotifyContentJson(todo, notifyUsers, context));
				notifyUsers = new JSONArray();
			}

		}
		if (notifyUsers.size() > 0) {
			listJson.add(buildNotifyContentJson(todo, notifyUsers, context));
		}
		return listJson;
	}

	/**
	 * <p>组装待办角标</p>
	 * @param notifyPersons
	 * @param todo
	 * @return
	 * @author 孙佳
	 */
	private List<JSONObject> buildNotifyBadgeJsons(List<SysOrgPerson> notifyPersons, SysNotifyTodo todo,int send) {
		JSONArray notifyUsers = new JSONArray();
		JSONArray alert = new JSONArray();
		JSONArray version = new JSONArray();
		JSONArray lang = new JSONArray();
		List<JSONObject> listJson = new ArrayList<JSONObject>();
		for (SysOrgPerson sysOrgPerson : notifyPersons) {
			//无效的用户或者与业务无关的用户不推送待办
			if (!sysOrgPerson.getFdIsAvailable() || !sysOrgPerson.getFdIsBusiness()) {
				continue;
			}
			notifyUsers.add(sysOrgPerson.getFdLoginName());
			alert.add(buildNotifyCountByPerson(sysOrgPerson, todo));
			version.add(System.currentTimeMillis());
			lang.add("zh_CN");
			if (notifyUsers.size() % 100 == 0) {
				listJson.add(buildNotifyCountJson(todo, notifyUsers, alert, version, lang,send));
				notifyUsers = new JSONArray();
				alert = new JSONArray();
				version = new JSONArray();
				lang = new JSONArray();
			}

		}
		if (notifyUsers.size() > 0) {
			listJson.add(buildNotifyCountJson(todo, notifyUsers, alert, version, lang,send));

		}
		return listJson;
	}

	public void executePostDataKK5(SysNotifyTodo todo, String title,String url, List<JSONObject> jsonObjects){
		logger.debug(
				"executePostDataKK5 -> jsonObjects.size():【"
						+ jsonObjects.size()
						+ "】 title:【"
						+ title
						+ " 】 url:【" + url + "】");

		logger.debug(" req:"+ JSON.toJSONString(jsonObjects));
		if (jsonObjects.isEmpty()) {
			return;
		}
		HttpClient httpClient = new HttpClient(getConnectionManager());
		// 设置超时
		httpClient.setConnectionTimeout(15000);
		httpClient.setTimeout(15000);

		KkPostDataRunner runner = new KkPostDataRunner(httpClient, url);

		runner.postKK5JsonData(todo,title, jsonObjects);

	}


	public void resendExecutePostDataKK5(String errorId, String url){
		logger.debug("重发 errorId:"+ errorId+"  url:"+url);
		HttpClient httpClient = new HttpClient(getConnectionManager());
		// 设置超时
		httpClient.setConnectionTimeout(15000);
		httpClient.setTimeout(15000);

		KkPostDataRunner runner = new KkPostDataRunner(httpClient, url);
		runner.resendPostKK5JsonData(errorId);
	}


}
