package com.landray.kmss.km.imeeting.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingEquipmentForm;
import com.landray.kmss.km.imeeting.model.KmImeetingConfig;
import com.landray.kmss.km.imeeting.model.KmImeetingEquipment;
import com.landray.kmss.km.imeeting.service.IKmImeetingEquipmentService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

 
/**
 * 会议辅助设备 Action
 * 
 * @author 
 * @version 1.0 2016-01-25
 */
public class KmImeetingEquipmentAction extends ExtendAction {
	protected IKmImeetingEquipmentService kmImeetingEquipmentService;
	private IKmImeetingMainService kmImeetingMainService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(kmImeetingEquipmentService == null){
			kmImeetingEquipmentService = (IKmImeetingEquipmentService)getBean("kmImeetingEquipmentService");
		}
		return kmImeetingEquipmentService;
	}

	protected IKmImeetingMainService getKmImeetingMainService(
			HttpServletRequest request) {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
	}


	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingEquipmentForm kmImeetingEquipmentForm = (KmImeetingEquipmentForm) super
				.createNewForm(mapping, form, request, response);
		if (StringUtil.isNull(kmImeetingEquipmentForm.getFdIsAvailable())) {
			kmImeetingEquipmentForm.setFdIsAvailable("1");
		}
		return kmImeetingEquipmentForm;
	}

	public ActionForward listEquipment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listEquipment", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			changeFindPageHQLInfo(request, hqlInfo);
			// 使用权限过滤
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
			List<KmImeetingEquipment> list = getServiceImp(request)
					.findValue(hqlInfo);
			// 记录操作日志
			UserOperHelper.logFindAll(list,
					getServiceImp(request).getModelName());
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listEquipment", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listEquipment", mapping, form, request,
					response);
		}
	}

	// 资源冲突检测（AJAX调用）
	public ActionForward checkConflict(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkConflict", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String equipmentIds = request.getParameter("equipmentIds");
			String fdHoldDate = request.getParameter("fdHoldDate");
			String fdFinishDate = request.getParameter("fdFinishDate");
			String meetingId = request.getParameter("meetingId");
			KmImeetingConfig kmImeetingConfig = new KmImeetingConfig();
			Boolean check = "true".equals(kmImeetingConfig.getUnShow());// 冲突检测配置
			if (StringUtil.isNotNull(equipmentIds) && check) {
				JSONArray array = new JSONArray();
				List<KmImeetingEquipment> equipments = ((IKmImeetingEquipmentService) getServiceImp(
						request)).findConflictEquipment(fdHoldDate,
								fdFinishDate, meetingId, request.getLocale());
				if (equipments != null && !equipments.isEmpty()) {
					for (KmImeetingEquipment equipment : equipments) {
						if (equipmentIds.indexOf(equipment.getFdId()) > -1) {
							JSONObject obj = new JSONObject();
							obj.put("fdId", equipment.getFdId());
							obj.put("name", StringUtil
									.XMLEscape(equipment.getFdName()));
							array.add(obj);
						}
					}
				}
				if (!array.isEmpty()) {
					json.put("conflictArray", array);
					json.put("conflict", true);
				} else {
					json.put("conflict", false);
				}
			} else {
				json.put("conflict", false);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());// 结果
		TimeCounter.logCurrentTime("Action-checkConflict", false, getClass());
		return null;
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		String type = request.getParameter("type");
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNotNull(type)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingEquipment.fdIsAvailable=:fdIsAvailable ");
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		}
		if (StringUtil.isNotNull(type)
				&& ImeetingConstant.MEETING_RES_REFF.equals(type)) {
			String fdHoldDate = request.getParameter("fdHoldDate");
			String fdFinishDate = request.getParameter("fdFinishDate");
			String meetingId = request.getParameter("meetingId");
			String method = request.getParameter("method_GET");
			List<KmImeetingEquipment> confEquipments = ((IKmImeetingEquipmentService) getServiceImp(
					request)).findConflictEquipment(fdHoldDate, fdFinishDate,
							meetingId, request.getLocale());
			if (confEquipments != null && !confEquipments.isEmpty()) {
				if (!"edit".equals(method)) {  // 如果是变更不过滤已选择的记录
					whereBlock = StringUtil.linkString(
							whereBlock,
							" and ",
							((IKmImeetingEquipmentService) getServiceImp(
									request)).buildLogicNotIN(
											"kmImeetingEquipment.fdId",
											confEquipments));
					hqlInfo.setModelName(KmImeetingEquipment.class.getName());
				}
			}
		}
		String fdName = request.getParameter("fdName");
		if (StringUtil.isNotNull(fdName)) {
			whereBlock = StringUtil.linkString(whereBlock,
					" and ", "kmImeetingEquipment.fdName like :fdName");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
		}
		hqlInfo.setWhereBlock(whereBlock);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, KmImeetingEquipment.class);
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.list(mapping, form, request, response);
		String contentType = request.getParameter("contentType");
		if (!"failure".equals(forward.getName()) && "json".equals(contentType)) {
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}

}

