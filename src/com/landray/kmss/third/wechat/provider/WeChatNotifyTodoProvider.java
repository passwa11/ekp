package com.landray.kmss.third.wechat.provider;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventAsyncCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.wechat.dto.WeChatNotifyTodoDto;
import com.landray.kmss.third.wechat.dto.WeChatPostData;
import com.landray.kmss.third.wechat.dto.WeChatSysNotifyTodo;
import com.landray.kmss.third.wechat.service.IWechatConfigService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 用来处理wechat待办
 * 
 * @author lizh
 * 
 */
public class WeChatNotifyTodoProvider extends BaseSysNotifyProviderExtend
		implements IEventMulticasterAware {

	private static MultiThreadedHttpConnectionManager connectionManager;
	ISysOrgPersonService sysOrgPersonService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WeChatNotifyTodoProvider.class);

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private MultiThreadedHttpConnectionManager getConnectionManager() {
		if (connectionManager == null) {
			connectionManager = new MultiThreadedHttpConnectionManager();
		}
		return connectionManager;
	}

	@Override
    public void add(SysNotifyTodo todo, NotifyContext context) throws Exception {
		
		WeChatNotifyTodoDto cloneToDo = createNotifyToDoDTO(todo);
		NotifyContextImp ctx = (NotifyContextImp) context;
		List notifyTargetList = ctx.getNotifyTarget();
		List<String> userIdList = new ArrayList<String>();
		if (notifyTargetList != null && notifyTargetList.size() > 0) {
			Iterator<?> it = ctx.getNotifyPersons().iterator();
			while (it.hasNext()) {
				SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
				userIdList.add(sysOrgPerson.getFdId());
			}
		}
		cloneToDo.setType(todo.getFdType());
		cloneToDo.setPersons(userIdList);

		// 处理延时加载问题
		multicaster.attatchEvent(new EventOfTransactionCommit(cloneToDo),
				new IEventAsyncCallBack() {
					@Override
                    public void execute(ApplicationEvent event)throws Throwable {
						Object obj = event.getSource();
						if (obj == null){
							logger.error("WeChatNotifyTodoProvider.add 事件源对象为NULL");
							return;
						}
						
						if (!(obj instanceof WeChatNotifyTodoDto)){
							logger.error("WeChatNotifyTodoProvider.add 事件类型不是微信消息推送类型");
							return;
						}
						
						WeChatNotifyTodoDto todoDTO = (WeChatNotifyTodoDto) obj;
						WeChatSysNotifyTodo todo = todoDTO.getTodo();
						int type = todoDTO.getType();
						try {
							List<WeChatPostData> postDatas = new ArrayList<WeChatPostData>();
							for (Iterator iter = todoDTO.getPersons().iterator(); iter.hasNext();) {
								String personid = (String) iter.next();
								WeChatPostData kkPostData = new WeChatPostData(todo.getFdId(), todo.getFdSubject(),personid);
								if(logger.isDebugEnabled()){
									logger.debug("微信推送待办："+ kkPostData.toString());
								}
								postDatas.add(kkPostData);
								if (postDatas.size() % 20 == 0) {
									executePostData(postDatas,type);
									// 清空
									postDatas = new ArrayList<WeChatPostData>();
								}
							}
							if (postDatas.size() > 0) {
								executePostData(postDatas,type);
							}
						} catch (Exception e) {
							logger.error("WeChatNotifyTodoProvider.add 发生异常,异常信息:"+e.getMessage());
							e.printStackTrace();
						}
					}
				});
	}

	@SuppressWarnings("deprecation")
	public void executePostData(List<WeChatPostData> postDatas,int type) {
		if (postDatas.isEmpty()) {
			return;
		}
		HttpClient httpClient = new HttpClient(getConnectionManager());
		// 设置超时
		httpClient.setConnectionTimeout(15000);
		httpClient.setTimeout(15000);
		WeChatPostDataRunner runner = new WeChatPostDataRunner(httpClient,
				postDatas,type);
		Thread t = new Thread(runner);
		t.start();
		if(logger.isDebugEnabled()){
			logger.debug("微信推送待办：推送成功。 ");
		}
	}

	public WeChatNotifyTodoDto createNotifyToDoDTO(SysNotifyTodo todo)
			throws IllegalAccessException, InvocationTargetException {
		WeChatSysNotifyTodo cloneToDo = new WeChatSysNotifyTodo();
		cloneToDo.setFdId(todo.getFdId());
		cloneToDo.setFdSubject(todo.getFdSubject());
		WeChatNotifyTodoDto todoDTO = new WeChatNotifyTodoDto();
		todoDTO.setTodo(cloneToDo);
		List<String> persons = new ArrayList<String>();
		for (Iterator iter = todo.getHbmTodoTargets().iterator(); iter.hasNext();) {
			Object person = iter.next();
			if (person instanceof SysOrgPerson) {
				String personId = ((SysOrgPerson) person).getFdId();
				try {
					if (todo.getFdType() == 1) {
						// 校验待审
						List l = getWechatConfigService().findList("fdEkpid='" + personId+ "' and fdPushProcess = '1'", null);
						if (l == null || l.size() == 0) {
							continue;
						}
					} else if (todo.getFdType() == 2) {
						// 校验待阅
						List l = getWechatConfigService().findList("fdEkpid='" + personId+ "' and fdPushRead = '1'", null);
						if (l == null || l.size() == 0) {
							continue;
						}
					}
				} catch (Exception e) {
					logger.error("WeChatNotifyTodoProvider.createNotifyToDoDTO 发生异常,异常信息:"+e.getMessage());
					e.printStackTrace();
					continue;
				}

				todo.getFdType();
				persons.add(((SysOrgPerson) person).getFdId());
			}
		}
		todoDTO.setPersons(persons);
		return todoDTO;
	}

	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
	}

	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
	}

	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {
	}

	private IEventMulticaster multicaster;

	@Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}

	private IWechatConfigService wechatConfigService;

	public IWechatConfigService getWechatConfigService() {
		if (wechatConfigService == null) {
            wechatConfigService = (IWechatConfigService) SpringBeanUtil
                    .getBean("wechatConfigService");
        }
		return wechatConfigService;
	}

}
