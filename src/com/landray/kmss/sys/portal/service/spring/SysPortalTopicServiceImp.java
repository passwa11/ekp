package com.landray.kmss.sys.portal.service.spring;

import java.util.Date;
import java.util.Iterator;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.portal.forms.SysPortalTopicForm;
import com.landray.kmss.sys.portal.service.ISysPortalTopicService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.UserUtil;

/** 
* @author 	陈经纬 
* @date 	2017年8月7日 下午2:19:08  
*/
public class SysPortalTopicServiceImp extends BaseServiceImp implements ISysPortalTopicService{
	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		
		SysPortalTopicForm sysPortalTopicForm = (SysPortalTopicForm) form;
		
		HQLInfo hqlInfo = new HQLInfo();

		Iterator<Integer> iterator = this.getBaseDao().findValueIterator(hqlInfo);
		
		SysOrgPerson user = UserUtil.getUser();
		Date curDate = new Date(System.currentTimeMillis());
		sysPortalTopicForm.setDocCreatorId(user.getFdId());
		sysPortalTopicForm.setDocCreatorName(user.getFdName());
		sysPortalTopicForm.setDocAlterorId(user.getFdId());
		sysPortalTopicForm.setDocAlterorName(user.getFdName());
		sysPortalTopicForm.setDocCreateTime(DateUtil
				.convertDateToString(curDate, DateUtil.TYPE_DATETIME,
						requestContext.getLocale()));
		sysPortalTopicForm.setDocAlterTime(DateUtil.convertDateToString(
				curDate, DateUtil.TYPE_DATETIME, requestContext.getLocale()));
		return super.add(form, requestContext);
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		SysPortalTopicForm sysPortalTopicForm = (SysPortalTopicForm) form;
		Date curDate = new Date(System.currentTimeMillis());
		SysOrgPerson user = UserUtil.getUser();
		sysPortalTopicForm.setDocAlterorId(user.getFdId());
		sysPortalTopicForm.setDocAlterorName(user.getFdName());
		sysPortalTopicForm.setDocAlterTime(DateUtil.convertDateToString(
				curDate, DateUtil.TYPE_DATETIME, requestContext.getLocale()));
		super.update(form, requestContext);
	}
}
