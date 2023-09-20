package com.landray.kmss.third.im.kk.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyException;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.im.kk.KkConfig;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.constant.KkNotifyConstants;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.service.IKkImNotifyService;
import com.landray.kmss.third.im.kk.util.NotifyConfigUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 用来处理kk待办处理,根据imsName过滤,判断是否执行待办
 * 
 * @author zhangtian
 * 
 */
public class KkNotifyTodoProvider extends BaseSysNotifyProviderExtend implements
		IEventMulticasterAware {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KkNotifyTodoProvider.class);
	
	private Kk5NotifyTodoProvider kk5NotifyTodoProvider;

	public Kk5NotifyTodoProvider getKk5NotifyTodoProvider() {
		return kk5NotifyTodoProvider;
	}

	public void setKk5NotifyTodoProvider(
			Kk5NotifyTodoProvider kk5NotifyTodoProvider) {
		this.kk5NotifyTodoProvider = kk5NotifyTodoProvider;
	}


	protected ISysNotifyTodoService sysNotifyTodoService;

	public ISysNotifyTodoService getSysNotifyTodoService() {
		return sysNotifyTodoService;
	}

	public void setSysNotifyTodoService(
			ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}

	protected IKkImConfigService kkImConfigService;

	public void setKkImConfigService(IKkImConfigService kkImConfigService) {
		this.kkImConfigService = kkImConfigService;
	}

	protected IKkImNotifyService kkImNotifyService;

	public void setKkImNotifyService(IKkImNotifyService kkImNotifyService) {
		this.kkImNotifyService = kkImNotifyService;
	}

	private static ThreadPoolTaskExecutor kkTaskExecutor;

	public ThreadPoolTaskExecutor getKkTaskExecutor() {
		return kkTaskExecutor;
	}

	public void setKkTaskExecutor(ThreadPoolTaskExecutor kkTaskExecutor) {
		Map<String, String> configs = new HashMap<String, String>();
		configs.put(KkNotifyConstants.KK_COREPOOLSIZE, "corePoolSize");
		configs.put(KkNotifyConstants.KK_MAXPOOLSIZE, "maxPoolSize");
		configs.put(KkNotifyConstants.KK_QUEUECAPACITY, "queueCapacity");
		configs.put(KkNotifyConstants.KK_KEEPALIVESECONDS, "keepAliveSeconds");
		for (String prop : configs.keySet()) {
		
			String valName = configs.get(prop);
			// 校验是否可写入
			if (!PropertyUtils.isWriteable(kkTaskExecutor, valName)) {
				continue;
			}
		
			// 校验是否空值
			String propVal = NotifyConfigUtil.getNotifyConfig(prop);
			if (StringUtil.isNull(propVal)) {
				continue;
			}
			// 校验是否int
			Integer intVal = null;
			try {
				intVal = Integer.parseInt(propVal);
			} catch (Exception e) {
				continue;
			}
			try {
				PropertyUtils.setProperty(kkTaskExecutor, valName, intVal);
			} catch (Exception e) {
				continue;
			}
		}
		/*	//线程池所使用的缓冲队列  
			kkTaskExecutor.setQueueCapacity(200);
			//线程池维护线程的最少数量  
			kkTaskExecutor.setCorePoolSize(5);
			//线程池维护线程的最大数量  
			kkTaskExecutor.setMaxPoolSize(1000);
			//线程池维护线程所允许的空闲时间  
			kkTaskExecutor.setKeepAliveSeconds(30000);*/
		KkNotifyTodoProvider.kkTaskExecutor = kkTaskExecutor;
	}

	// notes:如果扩展点中名字修改了,一定要对应修改这个名字,用来过滤是否发送待办
	private static final String imsName = "KK";
	private static final String notify_url = "/third/im/kk/kkNotify.do?method=kkSkip&fdId=!{fdId}";
	private static final String del_prefix = "删除_";


	@Override
    public void add(SysNotifyTodo todo, NotifyContext context) throws NotifyException {
		
		String appType = context.getFdAppType();
		boolean isNeed = StringUtil.isNull(appType) || (appType != null
				&& (appType.contains("all") || appType.contains("kk")));
		
		if (!isNeed) {
            return;
        }
		if (!isNeedSend(todo)) {
			return;
		}

		kk5NotifyTodoProvider.add(todo, context);

		//启动线程,执行发送待办通知
		/*KkConfig config = new KkConfig();
		String url = config.getValue(KeyConstants.KK_INNER_DOMAIN);
		kkTaskExecutor.execute(new KkNotifyTaskRunner(KkNotifyConstants.SNED_NOTIFY__TODO, url));*/
	}


	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		if (!isNeedSend(todo)) {
            return;
        }

		kk5NotifyTodoProvider.clearTodoPersons(todo);

		//启动线程
		/*KkConfig config = new KkConfig();
		String url = config.getValue(KeyConstants.KK_INNER_DOMAIN);
		kkTaskExecutor.execute(new KkNotifyTaskRunner(KkNotifyConstants.SNED_NOTIFY__TODO, url));*/
	}


	@Override
    public void remove(SysNotifyTodo todo) throws Exception {

		if (!isNeedSend(todo)) {
            return;
        }

		kk5NotifyTodoProvider.remove(todo);

		//启动线程
		/*KkConfig config = new KkConfig();
		String url = config.getValue(KeyConstants.KK_INNER_DOMAIN);
		kkTaskExecutor.execute(new KkNotifyTaskRunner(KkNotifyConstants.SNED_NOTIFY__TODO, url));*/
	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		
		if (!isNeedSend(todo)) {
            return;
        }

		kk5NotifyTodoProvider.removeDonePerson(todo, person);

		//启动线程
		/*KkConfig config = new KkConfig();
		String url = config.getValue(KeyConstants.KK_INNER_DOMAIN);
		kkTaskExecutor.execute(new KkNotifyTaskRunner(KkNotifyConstants.SNED_NOTIFY__TODO, url));*/

	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {

		if (!isNeedSend(todo)) {
            return;
        }

		kk5NotifyTodoProvider.setPersonsDone(todo, persons);

		//启动线程,执行更新角标
		/*KkConfig config = new KkConfig();
		String url = config.getValue(KeyConstants.KK_INNER_DOMAIN);
		kkTaskExecutor.execute(new KkNotifyTaskRunner(KkNotifyConstants.UPDATE_NOTIFY__TODO_NUM, url));*/
	}

	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {

		if (!isNeedSend(todo)) {
            return;
        }

		kk5NotifyTodoProvider.setTodoDone(todo);

		//启动线程,执行更新角标
		/*	KkConfig config = new KkConfig();
			String url = config.getValue(KeyConstants.KK_INNER_DOMAIN);
			kkTaskExecutor.execute(new KkNotifyTaskRunner(KkNotifyConstants.UPDATE_NOTIFY__TODO_NUM, url));*/

	}

	/**
	 * <p>查询是否开启待办通知</p>
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public Boolean getNeed_sync_todo() {
		KkConfig config = new KkConfig();
		String kk5_todo_enable = config.getValue(KeyConstants.EKP_NOTIFY_TODO);
		if ("true".equals(kk5_todo_enable)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * <p>查询是否开启待阅通知</p>
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public Boolean getNeed_sync_toread() {
		KkConfig config = new KkConfig();
		String kk5_todo_enable = config.getValue(KeyConstants.EKP_NOTIFY_TOREAD);
		if ("true".equals(kk5_todo_enable)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	private boolean isNeedSend(SysNotifyTodo todo) {
		Integer type = Integer.valueOf(todo.getFdType());
		if (type.equals(KkNotifyConstants.KK_NOTIFY_DO) || type.equals(KkNotifyConstants.KK_NOTIFY_SUSPEND)) {
			return getNeed_sync_todo().booleanValue();
		} else if (type.equals(KkNotifyConstants.KK_NOTIFY_READ)) {
			return getNeed_sync_toread().booleanValue();
		}
		return false;
	}


	private IEventMulticaster multicaster;

	@Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;

	}

	/*public static boolean useJsonInterface() {
		KkConfig config;
		try {
			config = new KkConfig();
			String interfaceType = config.getValue("kmss.kk.notify.interface.format");
			if ("JSON".equals(interfaceType)) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
	
		return false;
	}*/

}
