package com.landray.kmss.third.welink.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.welink.dao.IThirdWelinkTodoTaskMappDao;
import com.landray.kmss.third.welink.model.ThirdWelinkTodoTaskMapp;
import com.landray.kmss.third.welink.service.IThirdWelinkTodoTaskMappService;
import com.landray.kmss.third.welink.util.ThirdWelinkUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdWelinkTodoTaskMappServiceImp extends ExtendDataServiceImp implements IThirdWelinkTodoTaskMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWelinkTodoTaskMapp) {
            ThirdWelinkTodoTaskMapp thirdWelinkTodoTaskMapp = (ThirdWelinkTodoTaskMapp) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWelinkTodoTaskMapp thirdWelinkTodoTaskMapp = new ThirdWelinkTodoTaskMapp();
        thirdWelinkTodoTaskMapp.setDocCreateTime(new Date());
        ThirdWelinkUtil.initModelFromRequest(thirdWelinkTodoTaskMapp, requestContext);
        return thirdWelinkTodoTaskMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWelinkTodoTaskMapp thirdWelinkTodoTaskMapp = (ThirdWelinkTodoTaskMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public void addMapp(String todoId, String title, String personId,
                        String taskId, String welinkUserId) throws Exception {
		ThirdWelinkTodoTaskMapp thirdWelinkTodoTaskMapp = new ThirdWelinkTodoTaskMapp();
		thirdWelinkTodoTaskMapp.setDocSubject(title);
		thirdWelinkTodoTaskMapp.setDocCreateTime(new Date());
		thirdWelinkTodoTaskMapp.setFdPersonId(personId);
		thirdWelinkTodoTaskMapp.setFdTaskId(taskId);
		thirdWelinkTodoTaskMapp.setFdTodoId(todoId);
		thirdWelinkTodoTaskMapp.setFdWelinkUserId(welinkUserId);
		this.add(thirdWelinkTodoTaskMapp);
	}

	@Override
    public void deleteByTaskId(String taskId) throws Exception {
		((IThirdWelinkTodoTaskMappDao) getBaseDao()).deleteByTaskId(taskId);
	}
}
