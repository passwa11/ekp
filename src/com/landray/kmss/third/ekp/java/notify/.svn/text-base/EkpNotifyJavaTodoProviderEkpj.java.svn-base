package com.landray.kmss.third.ekp.java.notify;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.ISysNotifyTodoProviderExtend;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.notify.util.SysNotifyLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoAppResult;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoClearContext;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoGetAllContext;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoSendContext;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoUpdateContext;
import com.landray.kmss.third.ekp.java.notify.interfaces.EkpNotifyJavaTodoConstant;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyLog;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyMapp;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyQueErr;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyLogService;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyMappService;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyQueErrService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EkpNotifyJavaTodoProviderEkpj
		implements ISysNotifyTodoProviderExtend {

	private static Map<String, Long> notifyIds = new ConcurrentHashMap<String, Long>();

	private static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final Logger logger = LoggerFactory.getLogger(EkpNotifyJavaTodoProviderEkpj.class);

	private IEkpClientTodoEkpjService ekpClientTodoEkpjService = null;

	public void setEkpClientTodoEkpjService(
			IEkpClientTodoEkpjService ekpClientTodoEkpjService) {
		this.ekpClientTodoEkpjService = ekpClientTodoEkpjService;
	}

	public void clearNotifyIds() {
		Set<String> toDelete = new HashSet<String>();
		for (String key : notifyIds.keySet()) {
			Long expire = notifyIds.get(key);
			if ((System.currentTimeMillis() - expire) > 600000L) {
				toDelete.add(key);
			}
		}
		for (String key : toDelete) {
			notifyIds.remove(key);
		}
	}

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
	public void add(SysNotifyTodo todo, NotifyContext context)
			throws Exception {
		logger.debug("add " + todo.getFdId() + "," + todo.getFdSubject());
		notifyIds.put(todo.getFdId(), System.currentTimeMillis());
		List persons = ((NotifyContextImp) context).getNotifyPersons();
		if (persons == null || persons.isEmpty()) {
			logger.error(
					"待办所属人为空，" + todo.getFdId() + "," + todo.getFdSubject());
			return;
		}
		Date start = new Date();
		String errMsg = null;
		NotifyTodoAppResult result = null;
		NotifyTodoSendContext sendContext = new NotifyTodoSendContext();
		sendContext.setId(todo.getFdId());
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
		}
		SysDictModel sysDict = SysDataDict.getInstance().getModel(
				todo.getFdModelName());
		if (sysDict != null) {
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
		// 优先级
		sendContext.setLevel(todo.getFdLevel());
		// 消息内容扩展
		sendContext.setExtendContent(todo.getFdExtendContent());
		if (StringUtil.isNotNull(todo.getFdLang())) {
			sendContext.setLanguage(todo.getFdLang());
		}
		sendContext.setTargets(formatPerson(persons));
		// 待办创建者
		List list = new ArrayList();
		if (todo.getDocCreator() != null) {
			list.add(todo.getDocCreator());
			sendContext.setDocCreator(formatPerson(list));
		}
		addEkpJavaNotifyMapp(sendContext);
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(EkpNotifyJavaTodoConstant.METHOD_ADD,
					sendContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			try {
				addNotifyQueErr(EkpNotifyJavaTodoConstant.METHOD_ADD,
						sendContext,
						errMsg, todo.getFdSubject());

			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		} finally {
			try {
				addLog(EkpNotifyJavaTodoConstant.METHOD_ADD, start, sendContext,
						result, errMsg, todo.getFdSubject());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void remove(SysNotifyTodo todo) throws Exception {
		logger.debug("remove " + todo.getFdId() + "," + todo.getFdSubject());

		if ((System.currentTimeMillis()
				- todo.getFdCreateTime().getTime() > 600000L)
				&& !notifyIds.containsKey(todo.getFdId())
				&& thirdEkpJavaNotifyMappService
				.findByNotifyId(todo.getFdId()) == null) {
			logger.info("调用旧的remove接口，待办信息："+ com.alibaba.fastjson.JSONObject.toJSONString(todo));
			getEkpNotifyJavaTodoProviderSimple().remove(todo);
			return;
		}
		Date start = new Date();
		String errMsg = null;
		NotifyTodoAppResult result = null;
		NotifyTodoClearContext clearContext = new NotifyTodoClearContext();
		clearContext.setId(todo.getFdId());
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(EkpNotifyJavaTodoConstant.METHOD_REMOVE,
							clearContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
			ThirdEkpJavaNotifyMapp mapp = thirdEkpJavaNotifyMappService
					.findByNotifyId(todo.getFdId());
			if (mapp != null) {
				thirdEkpJavaNotifyMappService.delete(mapp);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			try {
				addNotifyQueErr(EkpNotifyJavaTodoConstant.METHOD_REMOVE,
						clearContext, errMsg, todo.getFdSubject());
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		} finally {
			try {
				addLog(EkpNotifyJavaTodoConstant.METHOD_REMOVE, start,
						clearContext, result, errMsg, todo.getFdSubject());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}



	@Override
	public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		logger.debug("removeDonePerson " + todo.getFdId() + ","
				+ todo.getFdSubject());
		if ((System.currentTimeMillis()
				- todo.getFdCreateTime().getTime() > 600000L)
				&& !notifyIds.containsKey(todo.getFdId())
				&& thirdEkpJavaNotifyMappService
				.findByNotifyId(todo.getFdId()) == null) {
			logger.info("调用旧的removeDonePerson接口，待办信息："+ com.alibaba.fastjson.JSONObject.toJSONString(todo));
			getEkpNotifyJavaTodoProviderSimple().removeDonePerson(todo, person);
			return;
		}
		Date start = new Date();
		String errMsg = null;
		NotifyTodoAppResult result = null;
		NotifyTodoClearContext clearContext = new NotifyTodoClearContext();
		clearContext.setId(todo.getFdId());
		List list = new ArrayList();
		list.add(person);
		clearContext.setTargets(formatPerson(list));
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(
							EkpNotifyJavaTodoConstant.METHOD_REMOVEDONEPERSON,
							clearContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			try {
				addNotifyQueErr(
						EkpNotifyJavaTodoConstant.METHOD_REMOVEDONEPERSON,
						clearContext, errMsg, todo.getFdSubject());
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		} finally {
			try {
				addLog(EkpNotifyJavaTodoConstant.METHOD_REMOVEDONEPERSON, start,
						clearContext, result, errMsg, todo.getFdSubject());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		logger.debug(
				"setPersonsDone " + todo.getFdId() + "," + todo.getFdSubject());
		if ((System.currentTimeMillis()
				- todo.getFdCreateTime().getTime() > 600000L)
				&& !notifyIds.containsKey(todo.getFdId())
				&& thirdEkpJavaNotifyMappService
				.findByNotifyId(todo.getFdId()) == null) {
			logger.info("调用旧的setPersonsDone接口，待办信息："+ com.alibaba.fastjson.JSONObject.toJSONString(todo));
			getEkpNotifyJavaTodoProviderSimple().setPersonsDone(todo, persons);
			return;
		}
		Date start = new Date();
		String errMsg = null;
		NotifyTodoAppResult result = null;
		NotifyTodoClearContext clearContext = new NotifyTodoClearContext();
		clearContext.setId(todo.getFdId());
		clearContext.setTargets(formatPerson(persons));
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(
							EkpNotifyJavaTodoConstant.METHOD_SETPERSONSDONE,
							clearContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			try {
				addNotifyQueErr(EkpNotifyJavaTodoConstant.METHOD_SETPERSONSDONE,
						clearContext, errMsg, todo.getFdSubject());
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		} finally {
			try {
				addLog(EkpNotifyJavaTodoConstant.METHOD_SETPERSONSDONE, start,
						clearContext, result, errMsg, todo.getFdSubject());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		logger.debug("clearTodoPersons " + todo.getFdId() + ","
				+ todo.getFdSubject());
		if ((System.currentTimeMillis()
				- todo.getFdCreateTime().getTime() > 600000L)
				&& !notifyIds.containsKey(todo.getFdId())
				&& thirdEkpJavaNotifyMappService
				.findByNotifyId(todo.getFdId()) == null) {
			logger.info("调用旧的clearTodoPersons接口，待办信息："+ com.alibaba.fastjson.JSONObject.toJSONString(todo));
			getEkpNotifyJavaTodoProviderSimple().clearTodoPersons(todo);
			return;
		}
		Date start = new Date();
		String errMsg = null;
		NotifyTodoAppResult result = null;
		NotifyTodoClearContext clearContext = new NotifyTodoClearContext();
		clearContext.setId(todo.getFdId());
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(
							EkpNotifyJavaTodoConstant.METHOD_CLEARTODOPERSONS,
							clearContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			try {
				addNotifyQueErr(
						EkpNotifyJavaTodoConstant.METHOD_CLEARTODOPERSONS,
						clearContext, errMsg, todo.getFdSubject());
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		} finally {
			try {
				addLog(EkpNotifyJavaTodoConstant.METHOD_CLEARTODOPERSONS, start,
						clearContext, result, errMsg, todo.getFdSubject());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void setTodoDone(SysNotifyTodo todo) throws Exception {
		logger.debug(
				"setTodoDone " + todo.getFdId() + "," + todo.getFdSubject());
		if ((System.currentTimeMillis()
				- todo.getFdCreateTime().getTime() > 600000L)
				&& !notifyIds.containsKey(todo.getFdId())
				&& thirdEkpJavaNotifyMappService
				.findByNotifyId(todo.getFdId()) == null) {
			logger.info("调用旧的setTodoDone接口，待办信息："+ com.alibaba.fastjson.JSONObject.toJSONString(todo));
			getEkpNotifyJavaTodoProviderSimple().setTodoDone(todo);
			return;
		}
		Date start = new Date();
		String errMsg = null;
		NotifyTodoAppResult result = null;
		NotifyTodoClearContext clearContext = new NotifyTodoClearContext();
		clearContext.setId(todo.getFdId());
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(EkpNotifyJavaTodoConstant.METHOD_SETTODODONE,
							clearContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
			ThirdEkpJavaNotifyMapp mapp = thirdEkpJavaNotifyMappService
					.findByNotifyId(todo.getFdId());
//			if (mapp != null) {
//				thirdEkpJavaNotifyMappService.delete(mapp);
//			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			try {
				addNotifyQueErr(EkpNotifyJavaTodoConstant.METHOD_SETTODODONE,
						clearContext, errMsg, todo.getFdSubject());
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		} finally {
			try {
				addLog(EkpNotifyJavaTodoConstant.METHOD_SETTODODONE, start,
						clearContext, result, errMsg, todo.getFdSubject());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}


	@Override
	public void updateTodo(SysNotifyTodo todo) throws Exception {
		logger.debug(
				"updateTodo " + todo.getFdId() + "," + todo.getFdSubject());
		if ((System.currentTimeMillis()
				- todo.getFdCreateTime().getTime() > 600000L)
				&& !notifyIds.containsKey(todo.getFdId())
				&& thirdEkpJavaNotifyMappService
				.findByNotifyId(todo.getFdId()) == null) {
			logger.info("调用旧的updateTodoe接口，待办信息："+ com.alibaba.fastjson.JSONObject.toJSONString(todo));
			getEkpNotifyJavaTodoProviderSimple().updateTodo(todo);
			return;
		}
		Date start = new Date();
		String errMsg = null;
		NotifyTodoAppResult result = null;
		NotifyTodoUpdateContext updateContext = new NotifyTodoUpdateContext();
		updateContext.setId(todo.getFdId());
		updateContext.setExtendContent(todo.getFdExtendContent());
		updateContext.setLevel(todo.getFdLevel());
		updateContext.setLink(StringUtil
				.formatUrl(
						"/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
								+ todo.getFdId()));
		updateContext.setSubject(todo.getFdSubject());
		updateContext.setType(todo.getFdType());
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(EkpNotifyJavaTodoConstant.METHOD_UPDATETODO,
							updateContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			try {
				addNotifyQueErr(EkpNotifyJavaTodoConstant.METHOD_UPDATETODO,
						updateContext, errMsg, todo.getFdSubject());
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		} finally {
			try {
				addLog(EkpNotifyJavaTodoConstant.METHOD_UPDATETODO, start,
						updateContext, result, errMsg, todo.getFdSubject());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}


	public ISysNotifyTodoProviderExtend getEkpNotifyJavaTodoProviderSimple() {
		if (ekpNotifyJavaTodoProviderSimple == null) {
			ekpNotifyJavaTodoProviderSimple = (ISysNotifyTodoProviderExtend) SpringBeanUtil
					.getBean("ekpNotifyJavaTodoProviderSimple");
		}
		return ekpNotifyJavaTodoProviderSimple;
	}

	public IThirdEkpJavaNotifyLogService getThirdEkpJavaNotifyLogService() {
		return thirdEkpJavaNotifyLogService;
	}

	public void setThirdEkpJavaNotifyLogService(
			IThirdEkpJavaNotifyLogService thirdEkpJavaNotifyLogService) {
		this.thirdEkpJavaNotifyLogService = thirdEkpJavaNotifyLogService;
	}

	public IThirdEkpJavaNotifyMappService getThirdEkpJavaNotifyMappService() {
		return thirdEkpJavaNotifyMappService;
	}

	public void setThirdEkpJavaNotifyMappService(
			IThirdEkpJavaNotifyMappService thirdEkpJavaNotifyMappService) {
		this.thirdEkpJavaNotifyMappService = thirdEkpJavaNotifyMappService;
	}

	public IThirdEkpJavaNotifyQueErrService
			getThirdEkpJavaNotifyQueErrService() {
		return thirdEkpJavaNotifyQueErrService;
	}

	public void setThirdEkpJavaNotifyQueErrService(
			IThirdEkpJavaNotifyQueErrService thirdEkpJavaNotifyQueErrService) {
		this.thirdEkpJavaNotifyQueErrService = thirdEkpJavaNotifyQueErrService;
	}

	private ISysNotifyTodoProviderExtend ekpNotifyJavaTodoProviderSimple;

	private IThirdEkpJavaNotifyLogService thirdEkpJavaNotifyLogService;

	private IThirdEkpJavaNotifyMappService thirdEkpJavaNotifyMappService;

	private IThirdEkpJavaNotifyQueErrService thirdEkpJavaNotifyQueErrService;


	private void addEkpJavaNotifyMapp(Object context) throws Exception {
		ThirdEkpJavaNotifyMapp mapp = new ThirdEkpJavaNotifyMapp();
		JSONObject contextObj = JSONObject.fromObject(context);
		mapp.setDocCreateTime(new Date());
		mapp.setDocSubject(contextObj.getString("subject"));
		// mapp.setFdNotifyData(contextObj.toString());
		mapp.setFdNotifyId(contextObj.getString("id"));
		thirdEkpJavaNotifyMappService.add(mapp);
	}

	private void addNotifyQueErr(int method, Object context,
			String errMsg, String subject) throws Exception {
		ThirdEkpJavaNotifyQueErr err = new ThirdEkpJavaNotifyQueErr();
		JSONObject contextObj = JSONObject.fromObject(context);
		err.setDocAlterTime(new Date());
		err.setDocCreateTime(new Date());
		err.setDocSubject(subject);
		err.setFdData(contextObj.toString());
		err.setFdErrMsg(errMsg);
		err.setFdFlag(1);
		err.setFdMethod(method);
		err.setFdNotifyId(contextObj.getString("id"));
		err.setFdRepeatHandle(0);
		thirdEkpJavaNotifyQueErrService.add(err);
	}

	private void addLog(int method, Date start, Object context,
			NotifyTodoAppResult result, String errMsg, String subject)
			throws Exception {
		ThirdEkpJavaNotifyLog log = new ThirdEkpJavaNotifyLog();
		JSONObject contextObj = JSONObject.fromObject(context);
		JSONObject resultObj = null;
		if (result != null) {
			resultObj = JSONObject.fromObject(result);
		}
		log.setDocCreateTime(start);
		log.setFdResTime(new Date());
		log.setDocSubject(subject);
		log.setFdExpireTime(System.currentTimeMillis() - start.getTime());
		log.setFdMethod(method);
		log.setFdNotifyId(contextObj.getString("id"));
		log.setFdReqData(contextObj.toString());
		log.setFdResData(resultObj == null ? null : resultObj.toString());
		if (StringUtil.isNull(errMsg)) {
			if (result != null) {
				int returnState = result.getReturnState();
				if (returnState != 2) {
					errMsg = result.getMessage();
				}
			}
		}
		log.setFdErrMsg(errMsg);
		if (StringUtil.isNotNull(errMsg)) {
			log.setFdResult(EkpNotifyJavaTodoConstant.RESULT_FAIL);
		} else {
			log.setFdResult(EkpNotifyJavaTodoConstant.RESULT_SUCCESS);
		}
		thirdEkpJavaNotifyLogService.add(log);
	}


	public void invoke(Integer method, Object context, String subject)
			throws Exception {
		// Object context = (NotifyTodoSendContext) JSONObject
		// .toBean(json, NotifyTodoSendContext.class);
		NotifyTodoAppResult result = null;
		String errMsg = null;
		Date start = new Date();
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(method,
							context);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
			// if (method == EkpNotifyJavaTodoConstant.METHOD_ADD) {
			// try {
			// addEkpJavaNotifyMapp((NotifyTodoSendContext) context);
			// } catch (Exception e) {
			// logger.error(e.getMessage(), e);
			// }
			// }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = e.getMessage();
			throw e;
		} finally {
			try {
				addLog(method, start, JSONObject.fromObject(context),
						result, errMsg, subject);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public JSONObject getAllTodo(int type, int pageNo, int rowSize,
			List<SysOrgPerson> persons)
			throws Exception {
		NotifyTodoGetAllContext context = new NotifyTodoGetAllContext();
		String appName = new EkpJavaConfig()
				.getValue("kmss.java.system.name");
		if (StringUtil.isNull(appName)) {
			throw new Exception("集成配置中必须配置本系统名称");
		}
		context.setAppName(appName);
		context.setType(type);
		context.setPageNo(pageNo);
		context.setRowSize(rowSize);
		if (persons != null && !persons.isEmpty()) {
			context.setTargets(formatPerson(persons));
		}
		NotifyTodoAppResult result = (NotifyTodoAppResult) ekpClientTodoEkpjService
				.sendByBreaker(EkpNotifyJavaTodoConstant.METHOD_GETALLTODO,
						context);
		logger.warn("获取到待办数据：" + result.getMessage());
		if (result.getReturnState() != 2) {
			throw new Exception(result.getMessage());
		}
		String message = result.getMessage();
		JSONObject messageObj = JSONObject.fromObject(message);
		return messageObj;
	}

	public void remove(String id, String subject) throws Exception {
		logger.debug("remove " + id + "," + subject);
		NotifyTodoAppResult result = null;
		NotifyTodoClearContext clearContext = new NotifyTodoClearContext();
		clearContext.setId(id);
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(EkpNotifyJavaTodoConstant.METHOD_REMOVE,
							clearContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
			ThirdEkpJavaNotifyMapp mapp = thirdEkpJavaNotifyMappService
					.findByNotifyId(id);
			if (mapp != null) {
				thirdEkpJavaNotifyMappService.delete(mapp);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void setTodoDone(String id, String subject) throws Exception {
		logger.debug(
				"setTodoDone " + id + "," + subject);
		NotifyTodoAppResult result = null;
		NotifyTodoClearContext clearContext = new NotifyTodoClearContext();
		clearContext.setId(id);
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(EkpNotifyJavaTodoConstant.METHOD_SETTODODONE,
							clearContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
			ThirdEkpJavaNotifyMapp mapp = thirdEkpJavaNotifyMappService
					.findByNotifyId(id);
//			if (mapp != null) {
//				thirdEkpJavaNotifyMappService.delete(mapp);
//			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void setPersonsDone(String id, String subject, String targets)
			throws Exception {
		logger.debug(
				"setPersonsDone " + id + "," + subject);
		NotifyTodoAppResult result = null;
		NotifyTodoClearContext clearContext = new NotifyTodoClearContext();
		clearContext.setId(id);
		clearContext.setTargets(targets);
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(
							EkpNotifyJavaTodoConstant.METHOD_SETPERSONSDONE,
							clearContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public String getAllTodoId(int type, List<SysOrgPerson> persons)
			throws Exception {
		NotifyTodoGetAllContext context = new NotifyTodoGetAllContext();
		String appName = new EkpJavaConfig()
				.getValue("kmss.java.system.name");
		if (StringUtil.isNull(appName)) {
			throw new Exception("集成配置中必须配置本系统名称");
		}
		context.setAppName(appName);
		context.setType(type);

		if (persons != null && !persons.isEmpty()) {
			context.setTargets(formatPerson(persons));
		}
		NotifyTodoAppResult result = (NotifyTodoAppResult) ekpClientTodoEkpjService
				.sendByBreaker(EkpNotifyJavaTodoConstant.METHOD_GETALLTODOID,
						context);
		logger.warn("获取到待办数据：" + result.getMessage());
		if (result.getReturnState() != 2) {
			throw new Exception(result.getMessage());
		}
		String message = result.getMessage();
		return message;
	}

	public void addTodo(String id)
			throws Exception {
		SysNotifyTodo todo = (SysNotifyTodo) getSysNotifyTodoService()
				.findByPrimaryKey(id, null, true);
		if (todo == null) {
			logger.error("找不到待办，" + id);
			return;
		}
		logger.warn("add " + todo.getFdId() + "," + todo.getFdSubject());
		notifyIds.put(todo.getFdId(), System.currentTimeMillis());
		List persons = todo.getHbmTodoTargets();
		if (persons == null || persons.isEmpty()) {
			logger.error(
					"待办所属人为空，" + todo.getFdId() + "," + todo.getFdSubject());
			return;
		}
		NotifyTodoAppResult result = null;
		NotifyTodoSendContext sendContext = new NotifyTodoSendContext();
		sendContext.setId(todo.getFdId());
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
						.formatUrl(
								"/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
										+ todo.getFdId()));
		sendContext.setType(todo.getFdType());
		if (StringUtil.isNotNull(todo.getFdModelId())) {
			sendContext.setModelId(todo.getFdModelId());
		}
		SysDictModel sysDict = SysDataDict.getInstance().getModel(
				todo.getFdModelName());
		if (sysDict != null) {
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
		sendContext.setSubject(todo.getFdSubject());
		// 优先级
		sendContext.setLevel(todo.getFdLevel());
		// 消息内容扩展
		sendContext.setExtendContent(todo.getFdExtendContent());
		if (StringUtil.isNotNull(todo.getFdLang())) {
			sendContext.setLanguage(todo.getFdLang());
		}
		sendContext.setTargets(formatPerson(persons));
		// 待办创建者
		List list = new ArrayList();
		if (todo.getDocCreator() != null) {
			list.add(todo.getDocCreator());
			sendContext.setDocCreator(formatPerson(list));
		}
		addEkpJavaNotifyMapp(sendContext);
		try {
			result = (NotifyTodoAppResult) ekpClientTodoEkpjService
					.sendByBreaker(EkpNotifyJavaTodoConstant.METHOD_ADD,
							sendContext);
			if (result.getReturnState() != 2) {
				throw new Exception(result.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	private ISysNotifyTodoService sysNotifyTodoService = null;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil
					.getBean("sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}
}
