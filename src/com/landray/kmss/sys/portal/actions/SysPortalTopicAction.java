package com.landray.kmss.sys.portal.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.portal.forms.SysPortalTopicForm;
import com.landray.kmss.sys.portal.model.SysPortalTopic;
import com.landray.kmss.sys.portal.service.ISysPortalTopicService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

/** 
* @author 	陈经纬 
* @date 	2017年8月7日 下午2:38:03  
*/
public class SysPortalTopicAction  extends ExtendAction {
	protected ISysPortalTopicService sysPortalTopicService;
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalTopicService == null) {
            sysPortalTopicService = (ISysPortalTopicService) getBean("sysPortalTopicService");
        }
		return sysPortalTopicService;
	}
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPortalTopicForm rtnForm = (SysPortalTopicForm)super.createNewForm(mapping, form, request, response);
		rtnForm.setFdPortalId(IDGenerator.generateID());
		return rtnForm;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}
		hqlInfo.setWhereBlock(where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysPortalTopic.class);
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
	}

}
