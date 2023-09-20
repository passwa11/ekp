package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.forms.HrStaffPrivateChangeForm;
import com.landray.kmss.hr.staff.model.HrStaffPrivateChange;
import com.landray.kmss.hr.staff.service.IHrStaffPrivateChangeService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 个人隐私设置
 * 
 * @author 朱湖强 2017-01-09
 * 
 */
public class HrStaffPrivateChangeAction extends ExtendAction {
	private IHrStaffPrivateChangeService hrStaffPrivateChangeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPrivateChangeService == null) {
            hrStaffPrivateChangeService = (IHrStaffPrivateChangeService) getBean("hrStaffPrivateChangeService");
        }
		return hrStaffPrivateChangeService;
	}
	
	protected ISysOrgPersonService personService;
	
	public ISysOrgPersonService getPersonService() {
		if(null == personService) {
			personService = (ISysOrgPersonService)this.getBean("sysOrgPersonService");
		}
		return personService;
	}

	public ActionForward editPrivate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editPrivate", true, getClass());
		KmssMessages messages = new KmssMessages();
		HrStaffPrivateChangeForm rtnForm = (HrStaffPrivateChangeForm)form;
		try {
			String fdIds = request.getParameter("fdIds");
			
			String[] ids = fdIds.split(";");
			List<String> idList = ArrayUtil.convertArrayToList(ids);
			String fdPersonId = "";
			
			HQLInfo personHqlInfo = new HQLInfo();
			personHqlInfo.setSelectBlock("sysOrgPerson.fdId, sysOrgPerson.fdName");
			personHqlInfo.setWhereBlock("sysOrgPerson.fdId in(:ids)");
			personHqlInfo.setParameter("ids", idList);
			List<Object[]> personList = this.getPersonService().findList(personHqlInfo);
			
			if(!ArrayUtil.isEmpty(personList)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("hrStaffPrivateChange.fdPersonId in(:ids)");
				hqlInfo.setParameter("ids", idList);
				List<HrStaffPrivateChange> list = getServiceImp(request).findList(hqlInfo);
				if(list.size() == 1) {
					HrStaffPrivateChange model = list.get(0);
					fdPersonId = model.getFdPersonId();
				}
				if(list.size() == 1 && ids.length==1 && fdPersonId.equals(ids[0])) {
					HrStaffPrivateChange model = list.get(0);
					rtnForm = (HrStaffPrivateChangeForm)getServiceImp(request).convertModelToForm(rtnForm, model, new RequestContext(request));
				}else{
					//rtnForm.setFdPersonId(ids[0]);
					String personNames = null;
					String personIds = null;
					for(Object[] obj : personList) {
						personIds = StringUtil.linkString(personIds, ";", obj[0].toString());
						personNames = StringUtil.linkString(personNames , ";", obj[1].toString());
					}
					rtnForm.setFdPersonId(personIds); 
					rtnForm.setFdPersonName(personNames);
				}
			}
			// 添加日志信息
			if (UserOperHelper.allowLogOper("editPrivate",
					getServiceImp(request).getModelName())) {
				UserOperContentHelper.putFind(rtnForm.getFdPersonId(),
						rtnForm.getFdPersonName(),
						getServiceImp(request).getModelName());
			}
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
			
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, rtnForm, request, response);
		} else {
			return getActionForward("editPrivate", mapping, rtnForm, request, response);
		}
	}
	
	public ActionForward updatePrivate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updatePrivate", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			HrStaffPrivateChangeForm hrStaffPrivateChangeForm = (HrStaffPrivateChangeForm)form;
			String[] idArray = hrStaffPrivateChangeForm.getFdPersonId().split(";");
			if(idArray.length > 1){
				for(int i=0;i<idArray.length;i++){
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock("hrStaffPrivateChange.fdPersonId =:fdPersonId");
					hqlInfo.setParameter("fdPersonId", idArray[i]);
					List<HrStaffPrivateChange> list = getServiceImp(request).findList(hqlInfo);
					if(list.size()>0){
						HrStaffPrivateChange hrStaffPrivateChange = list.get(0);
						hrStaffPrivateChange.setFdPersonId(idArray[i]);
						SysOrgPerson sysOrgPerson = (SysOrgPerson)this.getPersonService().findByPrimaryKey(idArray[i]);
						hrStaffPrivateChange.setFdPersonName(sysOrgPerson.getFdName());
						hrStaffPrivateChange.setIsBriefPrivate("1".equals(hrStaffPrivateChangeForm.getIsBriefPrivate())?Boolean.TRUE:Boolean.FALSE);
						hrStaffPrivateChange.setIsProjectPrivate("1".equals(hrStaffPrivateChangeForm.getIsProjectPrivate())?Boolean.TRUE:Boolean.FALSE);
						hrStaffPrivateChange.setIsWorkPrivate("1".equals(hrStaffPrivateChangeForm.getIsWorkPrivate())?Boolean.TRUE:Boolean.FALSE);
						hrStaffPrivateChange.setIsEducationPrivate("1".equals(hrStaffPrivateChangeForm.getIsEducationPrivate())?Boolean.TRUE:Boolean.FALSE);
						hrStaffPrivateChange.setIsTrainingPrivate("1".equals(hrStaffPrivateChangeForm.getIsTrainingPrivate())?Boolean.TRUE:Boolean.FALSE);
						hrStaffPrivateChange.setIsBonusPrivate("1".equals(hrStaffPrivateChangeForm.getIsBonusPrivate())?Boolean.TRUE:Boolean.FALSE);
						// 添加日志信息
						if (UserOperHelper.allowLogOper("updatePrivate",
								getServiceImp(request).getModelName())) {
							UserOperContentHelper.putUpdate(hrStaffPrivateChange)
									.putSimple("fdPersonName", null,
											hrStaffPrivateChange.getFdPersonName())
									.putSimple("isBriefPrivate", null,
											hrStaffPrivateChange.getIsBriefPrivate())
									.putSimple("isProjectPrivate", null,
											hrStaffPrivateChange.getIsProjectPrivate())
									.putSimple("isWorkPrivate", null,
											hrStaffPrivateChange.getIsWorkPrivate())
									.putSimple("isEducationPrivate", null,
											hrStaffPrivateChange.getIsEducationPrivate())
									.putSimple("isTrainingPrivate", null,
											hrStaffPrivateChange.getIsTrainingPrivate())
									.putSimple("isBonusPrivate", null,
											hrStaffPrivateChange.getIsBonusPrivate());
						}
						getServiceImp(request).update(hrStaffPrivateChange);
					}else{
						HrStaffPrivateChange  hspc=  new HrStaffPrivateChange();
						hspc.setFdPersonId(idArray[i]);
						SysOrgPerson sysOrgPerson = (SysOrgPerson)this.getPersonService().findByPrimaryKey(idArray[i]);
						hspc.setFdPersonName(sysOrgPerson.getFdName());
						hspc.setIsBriefPrivate("1".equals(hrStaffPrivateChangeForm.getIsBriefPrivate())?Boolean.TRUE:Boolean.FALSE);
						hspc.setIsProjectPrivate("1".equals(hrStaffPrivateChangeForm.getIsProjectPrivate())?Boolean.TRUE:Boolean.FALSE);
						hspc.setIsWorkPrivate("1".equals(hrStaffPrivateChangeForm.getIsWorkPrivate())?Boolean.TRUE:Boolean.FALSE);
						hspc.setIsEducationPrivate("1".equals(hrStaffPrivateChangeForm.getIsEducationPrivate())?Boolean.TRUE:Boolean.FALSE);
						hspc.setIsTrainingPrivate("1".equals(hrStaffPrivateChangeForm.getIsTrainingPrivate())?Boolean.TRUE:Boolean.FALSE);
						hspc.setIsBonusPrivate("1".equals(hrStaffPrivateChangeForm.getIsBonusPrivate())?Boolean.TRUE:Boolean.FALSE);
						// 添加日志信息
						if (UserOperHelper.allowLogOper("updatePrivate",
								getServiceImp(request).getModelName())) {
							UserOperContentHelper.putAdd(hspc, "fdPersonId",
									"fdPersonName", "isBriefPrivate",
									"isProjectPrivate", "isWorkPrivate",
									"isEducationPrivate", "isTrainingPrivate",
									"isBonusPrivate");
						}
						getServiceImp(request).add(hspc);
					}
				}
			}else{
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("hrStaffPrivateChange.fdPersonId =:fdPersonId");
				hqlInfo.setParameter("fdPersonId", hrStaffPrivateChangeForm.getFdPersonId());
				List<HrStaffPrivateChange> list = getServiceImp(request).findList(hqlInfo);
				if(list.size()>0){
					HrStaffPrivateChange hrStaffPrivateChange = list.get(0);
					hrStaffPrivateChange.setFdPersonId(hrStaffPrivateChangeForm.getFdPersonId());
					SysOrgPerson sysOrgPerson = (SysOrgPerson)this.getPersonService().findByPrimaryKey(hrStaffPrivateChangeForm.getFdPersonId());
					hrStaffPrivateChange.setFdPersonName(sysOrgPerson.getFdName());
					hrStaffPrivateChange.setIsBriefPrivate("1".equals(hrStaffPrivateChangeForm.getIsBriefPrivate())?Boolean.TRUE:Boolean.FALSE);
					hrStaffPrivateChange.setIsProjectPrivate("1".equals(hrStaffPrivateChangeForm.getIsProjectPrivate())?Boolean.TRUE:Boolean.FALSE);
					hrStaffPrivateChange.setIsWorkPrivate("1".equals(hrStaffPrivateChangeForm.getIsWorkPrivate())?Boolean.TRUE:Boolean.FALSE);
					hrStaffPrivateChange.setIsEducationPrivate("1".equals(hrStaffPrivateChangeForm.getIsEducationPrivate())?Boolean.TRUE:Boolean.FALSE);
					hrStaffPrivateChange.setIsTrainingPrivate("1".equals(hrStaffPrivateChangeForm.getIsTrainingPrivate())?Boolean.TRUE:Boolean.FALSE);
					hrStaffPrivateChange.setIsBonusPrivate("1".equals(hrStaffPrivateChangeForm.getIsBonusPrivate())?Boolean.TRUE:Boolean.FALSE);
					getServiceImp(request).update(hrStaffPrivateChange);
				}else{
					getServiceImp(request).add(hrStaffPrivateChangeForm, new RequestContext(request));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-updatePrivate", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("editPrivate");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return  mapping.findForward("success");
		}
	}
	
}
