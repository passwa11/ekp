package com.landray.kmss.third.ding.service.spring;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.request.OapiProcessWorkrecordTaskCreateRequest;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskCreateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskCreateResponse.TaskTopVo;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;
import com.landray.kmss.third.ding.model.ThirdDingDtaskXform;
import com.landray.kmss.third.ding.model.ThirdDingXformNotifyLog;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IThirdDingDtaskXformService;
import com.landray.kmss.third.ding.service.IThirdDingXformNotifyLogService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONObject;

public class ThirdDingDtaskXformServiceImp extends ExtendDataServiceImp implements IThirdDingDtaskXformService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingDtaskXformServiceImp.class);

	private IThirdDingXformNotifyLogService thirdDingXformNotifyLogService;

	public IThirdDingXformNotifyLogService getThirdDingXformNotifyLogService() {
		if (thirdDingXformNotifyLogService == null) {
			thirdDingXformNotifyLogService = (IThirdDingXformNotifyLogService) SpringBeanUtil
					.getBean("thirdDingXformNotifyLogService");
		}
		return thirdDingXformNotifyLogService;
	}

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingDtaskXform) {
            ThirdDingDtaskXform thirdDingDtaskXform = (ThirdDingDtaskXform) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingDtaskXform thirdDingDtaskXform = new ThirdDingDtaskXform();
        thirdDingDtaskXform.setDocCreateTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingDtaskXform, requestContext);
        return thirdDingDtaskXform;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingDtaskXform thirdDingDtaskXform = (ThirdDingDtaskXform) model;
    }

    @Override
    public List<ThirdDingDtaskXform> findByFdInstance(ThirdDingDinstanceXform fdInstance) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("thirdDingDtaskXform.fdInstance.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdInstance.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public String addTask(ThirdDingDtaskXform dtask, SysNotifyTodo todo,
			Long agentId) throws Exception {
		String instanceid = dtask.getFdInstance().getFdInstanceId();
		String token = DingUtils.getDingApiService().getAccessToken();
		OapiProcessWorkrecordTaskCreateRequest req = new OapiProcessWorkrecordTaskCreateRequest();
		OapiProcessWorkrecordTaskCreateResponse response = DingNotifyUtil
				.createTask(req, token, instanceid,
						dtask.getFdDingUserId(), dtask.getFdUrl(), agentId,
						dtask.getFdEkpUser() != null
								? dtask.getFdEkpUser().getFdId() : null);
		if (response.getErrcode() == 0) {
			List<TaskTopVo> tasks = response.getTasks();
			String taskid = null;
			for (TaskTopVo task : tasks) {
				taskid = task.getTaskId().toString();
			}
			dtask.setFdTaskId(taskid);
			dtask.setFdStatus("12");
		} else {
			dtask.setFdStatus("11");
			logger.error("发送待办失败，详细错误：" + response.getBody());
		}
		dtask.setFdDesc(response.getBody());
		dtask.setDocCreateTime(new Date());
		JSONObject jo = JSONObject.fromObject(response.getBody());
		addNotifyLog(todo, jo, JSONObject.fromObject(req).toString());
		return super.add(dtask);
	}

	private void addNotifyLog(SysNotifyTodo todo, JSONObject jo,
			String reqData) {
		try {
			ThirdDingXformNotifyLog notifylog = new ThirdDingXformNotifyLog();
			notifylog.setDocSubject(todo.getFdSubject());
			notifylog.setFdNotifyData(reqData);
			notifylog.setFdSendTime(new Date());
			notifylog.setFdRtnMsg(jo.toString());
			notifylog.setFdNotifyId(todo.getFdId());
			notifylog.setFdRtnTime(new Date());
			getThirdDingXformNotifyLogService().add(notifylog);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
