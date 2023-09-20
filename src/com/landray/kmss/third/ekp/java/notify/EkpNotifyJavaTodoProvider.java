package com.landray.kmss.third.ekp.java.notify;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.ISysNotifyTodoProviderExtend;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.util.ArrayUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EkpNotifyJavaTodoProvider implements ISysNotifyTodoProviderExtend {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpNotifyJavaTodoProvider.class);

	private ISysNotifyTodoProviderExtend ekpNotifyJavaTodoProviderEkpj;

	private ISysNotifyTodoProviderExtend ekpNotifyJavaTodoProviderSimple;

	private boolean isUseEkpjApi() {
		try {
			EkpJavaConfig config = new EkpJavaConfig();
			String apiType = config.getValue("kmss.notify.todoExtend.api.type");
			if ("ekpj".equals(apiType)) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	private ISysNotifyTodoProviderExtend getNotifyTodoProvider() {
		if (isUseEkpjApi()) {
			return ekpNotifyJavaTodoProviderEkpj;
		} else {
			return ekpNotifyJavaTodoProviderSimple;
		}
	}

	public boolean isSynchroNotifyEnable() throws Exception {
		EkpJavaConfig config = new EkpJavaConfig();
		String synchroNotify = config
				.getValue("kmss.notify.todoExtend.java.enabled");
		if ("true".equals(synchroNotify)) {
			return true;
		}
		return false;
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
    public void add(SysNotifyTodo todo, NotifyContext context) throws Exception {
		if (!isSynchroNotifyEnable()) {
			return;
		}
		if (!context.isFdNotifyEKP()) {
			return;
		}
		getNotifyTodoProvider().add(todo, context);
	}



	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
		if (!isSynchroNotifyEnable()) {
			return;
		}
		getNotifyTodoProvider().remove(todo);
	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		if (!isSynchroNotifyEnable()) {
			return;
		}
		getNotifyTodoProvider().removeDonePerson(todo, person);
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		if (!isSynchroNotifyEnable()) {
			return;
		}
		getNotifyTodoProvider().setPersonsDone(todo, persons);
	}

	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		if (!isSynchroNotifyEnable()) {
			return;
		}
		getNotifyTodoProvider().clearTodoPersons(todo);
	}

	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {
		if (!isSynchroNotifyEnable()) {
			return;
		}
		getNotifyTodoProvider().setTodoDone(todo);
	}

	@Override
    public void updateTodo(SysNotifyTodo todo) throws Exception {
		if (!isSynchroNotifyEnable()) {
			return;
		}
		getNotifyTodoProvider().updateTodo(todo);
	}

	public ISysNotifyTodoProviderExtend getEkpNotifyJavaTodoProviderEkpj() {
		return ekpNotifyJavaTodoProviderEkpj;
	}

	public void setEkpNotifyJavaTodoProviderEkpj(
			ISysNotifyTodoProviderExtend ekpNotifyJavaTodoProviderEkpj) {
		this.ekpNotifyJavaTodoProviderEkpj = ekpNotifyJavaTodoProviderEkpj;
	}

	public ISysNotifyTodoProviderExtend getEkpNotifyJavaTodoProviderSimple() {
		return ekpNotifyJavaTodoProviderSimple;
	}

	public void setEkpNotifyJavaTodoProviderSimple(
			ISysNotifyTodoProviderExtend ekpNotifyJavaTodoProviderSimple) {
		this.ekpNotifyJavaTodoProviderSimple = ekpNotifyJavaTodoProviderSimple;
	}

}
