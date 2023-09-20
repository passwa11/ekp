package com.landray.kmss.third.ding.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.third.ding.model.ThirdDingDinstance;
import com.landray.kmss.third.ding.model.ThirdDingDtemplate;
import com.landray.kmss.third.ding.model.ThirdDingTemplateDetail;

public interface IThirdDingDinstanceService extends IExtendDataService {

	public abstract List<ThirdDingDinstance> findByFdTemplate(ThirdDingDtemplate fdTemplate) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 *             创建通用的待办通知实例
	 */
	public String addCommonInstance(String duserid, SysNotifyTodo todo,ThirdDingDtemplate template, List<ThirdDingTemplateDetail> details) throws Exception;

	/**
	 * @param todo
	 * @return
	 * @throws Exception
	 *             查找通用的待办通知实例
	 */
	public ThirdDingDinstance findCommonInstance(SysNotifyTodo todo,
			String templateId) throws Exception;

	public void delInstance(String fdId, String ekpUserId) throws Exception;

}
