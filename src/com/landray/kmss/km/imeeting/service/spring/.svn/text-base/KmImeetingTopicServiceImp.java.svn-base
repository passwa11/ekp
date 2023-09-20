package com.landray.kmss.km.imeeting.service.spring;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.imeeting.model.KmImeetingTopic;
import com.landray.kmss.km.imeeting.service.IKmImeetingTopicService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.unit.model.KmImissiveUnit;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainModel;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KmImeetingTopicServiceImp extends BaseServiceImp implements IKmImeetingTopicService, ApplicationListener {

	private ISysNumberFlowService sysNumberFlowService; // 编号规则

	public void setSysNumberFlowService(ISysNumberFlowService sysNumberFlowService) {
		this.sysNumberFlowService = sysNumberFlowService;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
            return;
        }
		Object obj = event.getSource();
		if (!(obj instanceof KmImeetingTopic)) {
            return;
        }
		if (event instanceof Event_SysFlowFinish) {
			KmImeetingTopic kmImeetingTopic = (KmImeetingTopic) obj;
			kmImeetingTopic.setDocPublishTime(new Date());
			if (kmImeetingTopic instanceof ISysWfMainModel) {
                try {
                    this.update(kmImeetingTopic);
                } catch (Exception e) {
                    throw new KmssRuntimeException(e);
                }
            }
		}
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext) throws Exception {
		// 添加日志信息
		UserOperHelper.logAdd(getModelName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		if (model == null) {
            throw new NoRecordException();
        }
		KmImeetingTopic kmImeetingTopic = (KmImeetingTopic) model;
		kmImeetingTopic.setDocCreator(UserUtil.getUser());
		kmImeetingTopic.setDocCreateTime(new Date());
		if (StringUtil.isNull(kmImeetingTopic.getFdNo())) {
			kmImeetingTopic.setFdNo(sysNumberFlowService.generateFlowNumber(kmImeetingTopic));
		}
		return add(kmImeetingTopic);
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext) throws Exception {
		UserOperHelper.logUpdate(getModelName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		if (model == null) {
            throw new NoRecordException();
        }
		KmImeetingTopic kmImeetingTopic = (KmImeetingTopic) model;
		kmImeetingTopic.setDocAlteror(UserUtil.getUser());
		kmImeetingTopic.setDocAlterTime(new Date());

		if (StringUtil.isNull(kmImeetingTopic.getFdNo())) {
			kmImeetingTopic.setFdNo(sysNumberFlowService.generateFlowNumber(kmImeetingTopic));
		}
		update(kmImeetingTopic);
	}

	/**
	 * 读取需要加载的采购明细,以json形式返回
	 */
	@Override
	public JSONArray loadTopicList(String[] fdId, HttpServletRequest request) throws Exception {
		JSONArray jsonArr = new JSONArray();
		List<KmImeetingTopic> topicLists = this.findByPrimaryKeys(fdId);
		// 添加日志信息
		for (KmImeetingTopic topic : topicLists) {
			String subject = topic.getDocSubject();
			String fdChargeUnitName = topic.getFdChargeUnit() != null ? topic.getFdChargeUnit().getFdName() : "";
			String fdReporterName = topic.getFdReporter().getFdName();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("fdTopicId", topic.getFdId());
			jsonObj.put("docSubject", subject);
			jsonObj.put("fdNo", topic.getFdNo());
			jsonObj.put("fdChargeUnitId", topic.getFdChargeUnit() != null ? topic.getFdChargeUnit().getFdId() : "");
			jsonObj.put("fdChargeUnitName", fdChargeUnitName);
			jsonObj.put("fdReporterId", topic.getFdReporter().getFdId());
			jsonObj.put("fdReporterName", fdReporterName);
			String fdAttendUnitIds = "";
			String fdAttendUnitNames = "";
			if (topic.getFdAttendUnit().size() > 0) {
				List fdAttendUnit = topic.getFdAttendUnit();
				for (int i = 0; i < fdAttendUnit.size(); i++) {
					KmImissiveUnit unit = (KmImissiveUnit) fdAttendUnit.get(i);
					fdAttendUnitIds += unit.getFdId() + ";";
					fdAttendUnitNames += unit.getFdName() + ";";
				}
				if (StringUtil.isNotNull(fdAttendUnitIds)) {
					jsonObj.put("fdAttendUnitIds", fdAttendUnitIds.substring(0, fdAttendUnitIds.length() - 1));
				}
				if (StringUtil.isNotNull(fdAttendUnitNames)) {
					jsonObj.put("fdAttendUnitNames", fdAttendUnitNames.substring(0, fdAttendUnitNames.length() - 1));
				}
			}
			String fdListenUnitIds = "";
			String fdListenUnitNames = "";
			if (topic.getFdListenUnit().size() > 0) {
				List fdListenUnit = topic.getFdListenUnit();
				for (int i = 0; i < fdListenUnit.size(); i++) {
					KmImissiveUnit unit = (KmImissiveUnit) fdListenUnit.get(i);
					fdListenUnitIds += unit.getFdId() + ";";
					fdListenUnitNames += unit.getFdName() + ";";
				}
				if (StringUtil.isNotNull(fdListenUnitIds)) {
					jsonObj.put("fdListenUnitIds", fdListenUnitIds.substring(0, fdListenUnitIds.length() - 1));
				}
				if (StringUtil.isNotNull(fdListenUnitNames)) {
					jsonObj.put("fdListenUnitNames", fdListenUnitNames.substring(0, fdListenUnitNames.length() - 1));
				}
			}
			jsonObj.put("fdMaterialStaffId", topic.getFdMaterialStaff().getFdId());
			jsonObj.put("fdMaterialStaffName", topic.getFdMaterialStaff().getFdName());

			ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");

			JSONArray jsonArr_main = new JSONArray();
			List mainAtt = sysAttMainService.findByModelKey(KmImeetingTopic.class.getName(), topic.getFdId(),
					"mainonline");
			for (int i = 0; i < mainAtt.size(); i++) {
				SysAttMain att = (SysAttMain) mainAtt.get(i);
				JSONObject json = new JSONObject();
				json.put("fdId", att.getFdId());
				json.put("fdFileName", att.getFdFileName());
				jsonArr_main.add(json);
			}
			jsonObj.put("mainAtt", jsonArr_main);

			JSONArray jsonArr_common = new JSONArray();
			List commonAtt = sysAttMainService.findByModelKey(KmImeetingTopic.class.getName(), topic.getFdId(),
					"attachment");
			for (int j = 0; j < commonAtt.size(); j++) {
				SysAttMain att = (SysAttMain) commonAtt.get(j);
				JSONObject json = new JSONObject();
				json.put("fdId", att.getFdId());
				json.put("fdFileName", att.getFdFileName());
				jsonArr_common.add(json);
			}
			jsonObj.put("commonAtt", jsonArr_common);

			jsonArr.add(jsonObj);
		}
		return jsonArr;
	}
}
