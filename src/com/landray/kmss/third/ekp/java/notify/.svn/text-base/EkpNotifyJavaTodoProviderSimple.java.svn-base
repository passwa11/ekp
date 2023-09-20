package com.landray.kmss.third.ekp.java.notify;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.ISysNotifyTodoProviderExtend;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.notify.util.SysNotifyLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoRemoveContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoSendContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoUpdateContext;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EkpNotifyJavaTodoProviderSimple implements ISysNotifyTodoProviderExtend {

	private static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpNotifyJavaTodoProviderSimple.class);

	private String formatPerson(List persons) throws Exception {
		JSONArray array = new JSONArray();
		String[] loginNames = ArrayUtil.joinProperty(persons, "fdLoginName",
				";")[0].split(";");
		for (int i = 0; i < loginNames.length; i++) {
			JSONObject jsonObj = new JSONObject();
			String loginName = loginNames[i];
			jsonObj.accumulate("LoginName", loginName);
			array.element(jsonObj);
		}
		return array.toString();
	}

	@Override
    public void add(SysNotifyTodo todo, NotifyContext context) throws Exception {
		if (!context.isFdNotifyEKP()) {
			return;
		}
		List persons = ((NotifyContextImp) context).getNotifyPersons();
		if (persons == null || persons.isEmpty()) {
			return;
		}
		NotifyTodoSendContext sendContext = new NotifyTodoSendContext();
		String CURRSYSNAME = new EkpJavaConfig()
				.getValue("kmss.java.system.name");
		 
		sendContext.setAppName(StringUtil.isNull(CURRSYSNAME) ? "EKP"
				: CURRSYSNAME);
		sendContext.setCreateTime(DateUtil.convertDateToString(todo
				.getFdCreateTime(), DATETIMEFORMAT));
		if (todo.getFdKey() != null) {
			sendContext.setKey(todo.getFdKey());
		}
		sendContext
				.setLink(StringUtil
						.formatUrl("/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
								+ todo.getFdId()));
		sendContext.setType(todo.getFdType());
		if (StringUtil.isNotNull(todo.getFdModelId())) {
			sendContext.setModelId(todo.getFdModelId());
		} else {
			logger.warn("待发送的待办modelId信息为空，忽略该待办的发送,待办对应id为：" + todo.getFdId());
			return;
		}
		SysDictModel sysDict = SysDataDict.getInstance().getModel(
				todo.getFdModelName());
		if (sysDict != null) {
			// sendContext.setModelName(ResourceUtil.getString(sysDict
			// .getMessageKey()));
			String lang = SysNotifyLangUtil.getDefaultLang();
			if (todo.getFdLang() != null) {
				lang = todo.getFdLang();
			}
			sendContext.setModelName(ResourceUtil.getStringValue(SysDataDict
					.getInstance().getModel(todo.getFdModelName())
					.getMessageKey(), null, ResourceUtil.getLocale(lang)));
		} else {
			sendContext.setModelName(todo.getFdModelName());
		}
		if (StringUtil.isNotNull(todo.getFdParameter1())) {
			sendContext.setParam1(todo.getFdParameter1());
		}
		if (StringUtil.isNotNull(todo.getFdParameter2())) {
			sendContext.setParam2(todo.getFdParameter2());
		}
		if (StringUtil.isNotNull(context.getSubject())) {
			sendContext.setSubject(context.getSubject());
		}
		sendContext.setTargets(formatPerson(persons));
		// 待办创建者
		List list = new ArrayList();
		if (todo.getDocCreator() != null) {
			list.add(todo.getDocCreator());
			sendContext.setDocCreator(formatPerson(list));
		}
		// 优先级
		sendContext.setLevel(todo.getFdLevel());
		// 消息内容扩展
		sendContext.setExtendContent(todo.getFdExtendContent());
		if (StringUtil.isNotNull(todo.getFdLang())) {
			sendContext.setLanguage(todo.getFdLang());
		}
		// 增加唯一标识校验参数
		JSONObject otherParam = new JSONObject();
		otherParam.accumulate("MD5", sendContext.generateMD5());
		sendContext.setOthers(otherParam.toString());
		
		
		new EkpClientTodoThread(EkpClientTodoThread.SENDTODO, sendContext, null)
				.run();
	}

	public void add(JSONObject json) throws Exception {
		logger.debug("【EKP-JAVA】错误待办重复推送接口被调用，信息为：" + json.toString());
		NotifyTodoSendContext sendContext = new NotifyTodoSendContext();
		String appName = StringUtil.getString(json.getString("appName"));
		String createTime = StringUtil.getString(json.getString("createTime"));
		String docCreator = StringUtil.getString(json.getString("docCreator"));
		String extendContent = StringUtil
				.getString(json.getString("extendContent"));
		String fdKey = (String) json.get("key");
		String language = StringUtil.getString(json.getString("language"));
		Integer level = (Integer) json.get("level");
		String link = StringUtil.getString(json.getString("link"));
		String modelId = StringUtil.getString(json.getString("modelId"));
		String modelName = StringUtil.getString(json.getString("modelName"));
		String others = StringUtil.getString(json.getString("others"));
		String param1 = StringUtil.getString(json.getString("param1"));
		String param2 = StringUtil.getString(json.getString("param2"));
		String subject = StringUtil.getString(json.getString("subject"));
		String targets = StringUtil.getString(json.getString("targets"));
		int type = json.getInt("type");
		 
		sendContext.setAppName(StringUtil.isNull(appName) ? "EKP"
				: appName);
		sendContext.setCreateTime(createTime);
		if (fdKey != null) {
			sendContext.setKey(fdKey);
		}
		sendContext.setLink(link);
		sendContext.setType(type);
		if (StringUtil.isNotNull(modelId)) {
			sendContext.setModelId(modelId);
		} else {
			logger.warn(
					"【EKP-JAVA 】待发送的待办modelId信息为空，忽略该待办的发送,待办对应id为：" + modelId);
			return;
		}
		sendContext.setModelName(modelName);
		if (StringUtil.isNotNull(param1)) {
			sendContext.setParam1(param1);
		}
		if (StringUtil.isNotNull(param2)) {
			sendContext.setParam2(param2);
		}
		if (StringUtil.isNotNull(subject)) {
			sendContext.setSubject(subject);
		}
		sendContext.setTargets(targets);
		sendContext.setDocCreator(docCreator);
		// 优先级
		sendContext.setLevel(level);
		// 消息内容扩展
		sendContext.setExtendContent(extendContent);
		if (StringUtil.isNotNull(language)) {
			sendContext.setLanguage(language);
		}
		sendContext.setOthers(others);

		new EkpClientTodoJob(EkpClientTodoThread.SENDTODO, sendContext, null)
				.run();
	}

	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
		todoDoneOrRemove(todo, null, true);
	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		List persons = new ArrayList();
		persons.add(person);
		todoDoneOrRemove(todo, persons, true);
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		todoDoneOrRemove(todo, persons);
	}

	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		todoDoneOrRemove(todo, null);
	}

	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {
		todoDoneOrRemove(todo, null);
	}

	public void todoDoneOrRemove(SysNotifyTodo todo, List persons)
			throws Exception {
		todoDoneOrRemove(todo, persons, false);
	}

	public void todoDoneOrRemove(SysNotifyTodo todo, List persons,
			boolean isRemove) throws Exception {
		// modelId为空不予清理
		if (StringUtil.isNull(todo.getFdModelId())) {
			logger.warn("待办的modelId信息为空，忽略该待办的清理处理,待办id为：" + todo.getFdId());
			return;
		}
		NotifyTodoRemoveContext removeContext = new NotifyTodoRemoveContext();
		String CURRSYSNAME = new EkpJavaConfig()
				.getValue("kmss.java.system.name");
		removeContext.setAppName(StringUtil.isNull(CURRSYSNAME) ? "EKP"
				: CURRSYSNAME);
		if (todo.getFdKey() != null) {
			removeContext.setKey(todo.getFdKey());
		}
		if (persons != null && !persons.isEmpty()) {
			removeContext
					.setOptType(NotifyTodoRemoveContext.OPT_NOTIFY_TODO_PERSON);
			removeContext.setTargets(formatPerson(persons));
		} else {
			removeContext.setOptType(NotifyTodoRemoveContext.OPT_NOTIFY_TODO);
		}
		if (StringUtil.isNotNull(todo.getFdModelId())) {
			removeContext.setModelId(todo.getFdModelId());
		}
		if (StringUtil.isNotNull(todo.getFdModelName())) {
			SysDictModel sysDict = SysDataDict.getInstance().getModel(
					todo.getFdModelName());
			if (sysDict != null) {
				String lang = SysNotifyLangUtil.getDefaultLang();
				if (todo.getFdLang() != null) {
					lang = todo.getFdLang();
				}
				removeContext.setModelName(
						ResourceUtil.getStringValue(sysDict.getMessageKey(),
								null, ResourceUtil.getLocale(lang)));
			} else {
				removeContext.setModelName(todo.getFdModelName());
			}
		}
		if (StringUtil.isNotNull(todo.getFdParameter1())) {
			removeContext.setParam1(todo.getFdParameter1());
		}
		if (StringUtil.isNotNull(todo.getFdParameter2())) {
			removeContext.setParam2(todo.getFdParameter2());
		}
		removeContext.setType(todo.getFdType());
		int opt = isRemove ? EkpClientTodoThread.DELETETODO
				: EkpClientTodoThread.SETTODODONE;

		// 增加唯一标识校验参数
		JSONObject otherParam = new JSONObject();
		otherParam.accumulate("MD5",
				removeContext.generateMD5(opt, todo.getFdId()));
		removeContext.setOthers(otherParam.toString()); 
		new EkpClientTodoThread(opt, removeContext, todo.getFdId()).run();
	}

	public void todoDoneOrRemove(JSONObject json, boolean isRemove)
			throws Exception {
		logger.debug("【EKP-JAVA】错误待办重复删除或置为已办接口被调用，信息为：" + json.toString()
				+ ";isRemove=" + isRemove);
		NotifyTodoRemoveContext removeContext = new NotifyTodoRemoveContext();
		String appName = StringUtil.getString(json.getString("appName"));
		String fdKey = (String) json.get("key");
		String modelId = StringUtil.getString(json.getString("modelId"));
		String modelName = StringUtil.getString(json.getString("modelName"));
		String others = StringUtil.getString(json.getString("others"));
		String param1 = StringUtil.getString(json.getString("param1"));
		String param2 = StringUtil.getString(json.getString("param2"));
		String targets = StringUtil.getString(json.getString("targets"));
		// String notifyId = StringUtil.getString(json.getString("notifyId"));
		int type = json.getInt("type");

		// modelId为空不予清理
		if (StringUtil.isNull(modelId)) {
			logger.warn("待办的modelId信息为空，忽略该待办的清理处理,待办为：" + modelId);
			return;
		} 
		removeContext.setAppName(appName);
		if (fdKey != null) {
			removeContext.setKey(fdKey);
		}
		if (StringUtil.isNotNull(targets)) {
			removeContext
					.setOptType(NotifyTodoRemoveContext.OPT_NOTIFY_TODO_PERSON);
			removeContext.setTargets(targets);
		} else {
			removeContext.setOptType(NotifyTodoRemoveContext.OPT_NOTIFY_TODO);
		}
		removeContext.setModelId(modelId);
		if (StringUtil.isNotNull(modelName)) {
			removeContext.setModelName(modelName);
		}
		if (StringUtil.isNotNull(param1)) {
			removeContext.setParam1(param1);
		}
		if (StringUtil.isNotNull(param2)) {
			removeContext.setParam2(param2);
		}
		removeContext.setType(type);
		int opt = isRemove ? EkpClientTodoThread.DELETETODO
				: EkpClientTodoThread.SETTODODONE;
		removeContext.setOthers(others);

		new EkpClientTodoJob(opt, removeContext, null).run();
	}

	@Override
    public void updateTodo(SysNotifyTodo todo) throws Exception {
		NotifyTodoUpdateContext todoContext = new NotifyTodoUpdateContext();
		todoContext.setAppName(StringUtil.isNotNull(todo.getFdAppName()) ? todo
				.getFdAppName() : "");
		todoContext.setKey(todo.getFdKey() != null ? todo
				.getFdKey() : "");
		todoContext
				.setLink(StringUtil
						.formatUrl("/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
								+ todo.getFdId()));
		todoContext.setType(todo.getFdType());
		if (StringUtil.isNotNull(todo.getFdModelId())) {
			todoContext.setModelId(todo.getFdModelId());
		}
		if (StringUtil.isNotNull(todo.getFdModelName())) {
			SysDictModel sysDict = SysDataDict.getInstance().getModel(
					todo.getFdModelName());
			if (sysDict != null) {
				// todoContext.setModelName(ResourceUtil.getString(sysDict
				// .getMessageKey()));
				String lang = SysNotifyLangUtil.getDefaultLang();
				if (todo.getFdLang() != null) {
					lang = todo.getFdLang();
				}
				ResourceUtil.getLocale(todo.getFdLang());
				todoContext.setModelName(ResourceUtil.getStringValue(SysDataDict
						.getInstance().getModel(todo.getFdModelName())
						.getMessageKey(), null, ResourceUtil.getLocale(lang)));
			} else {
				todoContext.setModelName(todo.getFdModelName());
			}
		}
		if (StringUtil.isNotNull(todo.getFdParameter1())) {
			todoContext.setParam1(todo.getFdParameter1());
		}
		if (StringUtil.isNotNull(todo.getFdParameter2())) {
			todoContext.setParam2(todo.getFdParameter2());
		}
		todoContext.setType(todo.getFdType());
		if (StringUtil.isNotNull(todo.getFdSubject())) {
			todoContext.setSubject(todo.getFdSubject());
		}
		todoContext.setLevel(todo.getFdLevel());
		todoContext.setExtendContent(StringUtil.isNotNull(todo
				.getFdExtendContent()) ? todo.getFdExtendContent() : "");
		// 增加唯一标识校验参数
		JSONObject otherParam = new JSONObject();
		otherParam.accumulate("MD5", todoContext.generateMD5());
		todoContext.setOthers(otherParam.toString()); 
		new EkpClientTodoThread(EkpClientTodoThread.UPDATETODO, todoContext,
				null)
						.run();
	}

	public void updateTodo(JSONObject json) throws Exception {
		logger.debug("【EKP-JAVA】错误待办重复更新接口被调用，信息为：" + json.toString());
		NotifyTodoUpdateContext todoContext = new NotifyTodoUpdateContext();
		String appName = StringUtil.getString(json.getString("appName"));
		String extendContent = StringUtil
				.getString(json.getString("extendContent"));
		String fdKey = (String) json.get("key");
		Integer level = (Integer) json.get("level");
		String link = StringUtil.getString(json.getString("link"));
		String modelId = StringUtil.getString(json.getString("modelId"));
		String modelName = StringUtil.getString(json.getString("modelName"));
		String others = StringUtil.getString(json.getString("others"));
		String param1 = StringUtil.getString(json.getString("param1"));
		String param2 = StringUtil.getString(json.getString("param2"));
		String subject = StringUtil.getString(json.getString("subject"));
		int type = json.getInt("type");

		todoContext.setAppName(appName);
		todoContext.setKey(fdKey != null ? fdKey : "");
		todoContext.setLink(link);
		todoContext.setType(type);
		if (StringUtil.isNotNull(modelId)) {
			todoContext.setModelId(modelId);
		}
		if (StringUtil.isNotNull(modelName)) {
			todoContext.setModelName(modelName);
		}
		if (StringUtil.isNotNull(param1)) {
			todoContext.setParam1(param1);
		}
		if (StringUtil.isNotNull(param2)) {
			todoContext.setParam2(param2);
		}
		if (StringUtil.isNotNull(subject)) {
			todoContext.setSubject(subject);
		}
		todoContext.setLevel(level);
		todoContext.setExtendContent(extendContent);
		todoContext.setOthers(others); 
		new EkpClientTodoJob(EkpClientTodoThread.UPDATETODO, todoContext, null)
				.run();
	}

}
