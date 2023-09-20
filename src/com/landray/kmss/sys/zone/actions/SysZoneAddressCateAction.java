package com.landray.kmss.sys.zone.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.zone.forms.SysZoneAddressCateForm;
import com.landray.kmss.sys.zone.forms.SysZoneOrgRelationForm;
import com.landray.kmss.sys.zone.model.SysZoneAddressCateVo;
import com.landray.kmss.sys.zone.model.SysZoneOrgOuter;
import com.landray.kmss.sys.zone.model.SysZoneOrgRelation;
import com.landray.kmss.sys.zone.service.ISysZoneAddressCateService;
import com.landray.kmss.sys.zone.service.ISysZoneAddressCateVoService;
import com.landray.kmss.sys.zone.service.ISysZoneOrgOuterService;
import com.landray.kmss.sys.zone.service.ISysZoneOrgRelationService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

@SuppressWarnings("unchecked")
public class SysZoneAddressCateAction extends ExtendAction {
	private static final String ITEM_TYPE_PUBLIC = "public";// 事项类型为公共
	private static final String ITEM_TYPE_PRIVATE = "private";// 事项类型为个人

	protected ISysCategoryMainService sysCategoryMainService;
	protected ISysZoneAddressCateService sysZoneAddressCateService;
	protected ISysZoneOrgRelationService sysZoneOrgRelationService;
	protected ISysZoneOrgOuterService outerOrgService;
	protected ISysOrgPersonService personService = null;
	protected ISysZoneAddressCateVoService sysZoneAddressCateVoService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysCategoryMainService == null) {
            sysCategoryMainService = (ISysCategoryMainService) getBean("sysCategoryMainService");
        }
		return sysCategoryMainService;
	}
	
	public ISysZoneAddressCateService getSysZoneAddressCateService() {
		if (sysZoneAddressCateService == null) {
            sysZoneAddressCateService = (ISysZoneAddressCateService) getBean("sysZoneAddressCateService");
        }
		return sysZoneAddressCateService;
	}

	public ISysZoneAddressCateVoService getSysZoneAddressCateVoService() {
		if (sysZoneAddressCateVoService == null) {
            sysZoneAddressCateVoService = (ISysZoneAddressCateVoService) getBean(
                    "sysZoneAddressCateVoService");
        }
		return sysZoneAddressCateVoService;
	}

	public ISysZoneOrgRelationService getSysZoneOrgRelationService() {
		if (sysZoneOrgRelationService == null) {
			sysZoneOrgRelationService = (ISysZoneOrgRelationService) SpringBeanUtil
					.getBean("sysZoneOrgRelationService");
		}
		return sysZoneOrgRelationService;
	}

	public ISysZoneOrgOuterService getOuterOrgService() {
		if (outerOrgService == null) {
			outerOrgService = (ISysZoneOrgOuterService) SpringBeanUtil.getBean("sysZoneOrgOuterService");
		}
		return outerOrgService;
	}

	public ISysOrgPersonService getPersonService() {
		if (personService == null) {
			personService = (ISysOrgPersonService) getBean("sysOrgPersonService");
		}
		return personService;
	}

	/**
	 * 执行添加操作主要的业务代码。<br>
	 * 仅用于add的操作。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		SysZoneAddressCateForm cateForm = (SysZoneAddressCateForm) form;
		String cateType = request.getParameter("cateType");
		cateForm.reset(mapping, request);
		cateForm.setFdModelName("com.landray.kmss.sys.zone.SysZoneAddressCate." + cateType);
		KMSSUser user = UserUtil.getKMSSUser(request);
		cateForm.setAuthAreaId(user.getAuthAreaId());
		cateForm.setAuthAreaName(user.getAuthAreaName());
		cateForm.setFdItemType(ITEM_TYPE_PRIVATE);
		return cateForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		SysZoneAddressCateForm rtnForm = null;
		String cateId = request.getParameter("fdId");
		if (!StringUtil.isNull(cateId)) {
			SysCategoryMain model = (SysCategoryMain) getServiceImp(request).findByPrimaryKey(cateId);
			if (model != null) {
				rtnForm = new SysZoneAddressCateForm();
				rtnForm = (SysZoneAddressCateForm) getServiceImp(request)
						.convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				// BeanUtils.copyProperties(rtnForm, model);
				String cateType = request.getParameter("cateType");
				rtnForm.setCateType(cateType);
				List<SysZoneOrgRelation> orgRelations = getSysZoneOrgRelationService()
						.findList("fdCategoryId='" + cateId + "'", "fdOrder asc");
				for (SysZoneOrgRelation orgRelation : orgRelations) {
					rtnForm.addOrgRelation(convertFromRelationModel(orgRelation));
				}
				SysZoneAddressCateVo cateVo = getSysZoneAddressCateVoService()
						.getCateVoByCateId(cateId);
				if (cateVo != null) {
					rtnForm.setFdItemType(cateVo.getFdItemType());
				} else {
					rtnForm.setFdItemType(ITEM_TYPE_PUBLIC);
				}

			}
			if (rtnForm != form) {
                request.setAttribute(getFormName(rtnForm, request), rtnForm);
            }
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
	}

	private SysZoneOrgRelationForm convertFromRelationModel(SysZoneOrgRelation orgRelation) throws Exception {
		SysZoneOrgRelationForm relationForm = new SysZoneOrgRelationForm();
		BeanUtils.copyProperties(relationForm, orgRelation);
		if ("inner".equals(orgRelation.getFdOrgType())) {
			SysOrgPerson personEle = (SysOrgPerson) getPersonService().findByPrimaryKey(orgRelation.getFdOrgId());
			relationForm.setFdOrgName(personEle.getFdName());
		} else if ("outer".equals(orgRelation.getFdOrgType())) {
			SysZoneOrgOuter outerPerosn = (SysZoneOrgOuter) getOuterOrgService()
					.findByPrimaryKey(orgRelation.getFdOrgId());
			relationForm.setFdOrgName(outerPerosn.getFdName());
			relationForm.setFdOrgEmail(outerPerosn.getFdEmail());
			relationForm.setFdOrgOtherInfo(outerPerosn.getFdOtherInfo());
			relationForm.setFdOrgMemo(outerPerosn.getFdMemo());
			relationForm.setFdOrgPost(outerPerosn.getFdPostDesc());
			relationForm.setFdOrgPhone(outerPerosn.getFdMobileNo());
			relationForm.setFdOrgWorkPhone(outerPerosn.getFdWorkPhone());
		}
		return relationForm;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String strPara = request.getParameter("parentId");
		String modelName = request.getParameter("modelName");
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		if (strPara != null) {
			whereBlock += " and sysCategoryMain.hbmParent.fdId= :hbmParentFdId";
			hqlInfo.setParameter("hbmParentFdId", strPara);
		}
		if (modelName != null) {
			if (StringUtil.isNotNull(whereBlock)) {
				whereBlock += " and sysCategoryMain.fdModelName= :fdModelName";
				hqlInfo.setParameter("fdModelName", modelName);
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		String forward = "failure";
		//
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward(forward, mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}


	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysZoneAddressCateForm cateForm = (SysZoneAddressCateForm) form;
			String existId = cateForm.getFdId();
			String cateId = existId;
			String cateType = cateForm.getCateType();
			String itemType = cateForm.getFdItemType();
			if (ITEM_TYPE_PRIVATE.equals(itemType)) {// 个人事项 设置 可阅读者和可编辑者为当前创建用户
				SysOrgPerson curPerson = UserUtil.getUser();
				String id = curPerson.getFdId();
				String name = curPerson.getFdName();
				cateForm.setAuthReaderIds(id);
				cateForm.setAuthReaderNames(name);
				cateForm.setAuthEditorIds(id);
				cateForm.setAuthEditorNames(name);
			}
			if ("edit".equals(request.getParameter("method_GET"))) {
				getSysZoneAddressCateService().update((IExtendForm) form,
						new RequestContext(request));
			} else {
				getSysZoneAddressCateService().add((IExtendForm) form,
					new RequestContext(request));
			}
			getSysZoneOrgRelationService().deleteAllCateRelations(cateId,
					cateType);// 先删除之前保存的
			if ("inner".equals(cateType)) {
				getSysZoneAddressCateVoService().deleteByCateId(cateId);
				SysZoneAddressCateVo cateVo = new SysZoneAddressCateVo();
				cateVo.setFdCategoryId(cateId);
				cateVo.setFdItemType(itemType);
				getSysZoneAddressCateVoService().add(cateVo);
			}

			List<SysZoneOrgRelationForm> relationForms = cateForm
					.getCateRelations();
			SysZoneOrgRelation orgRelation = null;
			// 还没加顺序
			for (SysZoneOrgRelationForm relationForm : relationForms) {
				String orgId = relationForm.getFdOrgId();
				if ("outer".equals(relationForm.getFdOrgType())) {
					SysZoneOrgOuter outerPerson = new SysZoneOrgOuter();
					outerPerson.setFdEmail(relationForm.getFdOrgEmail());
					outerPerson.setFdMemo(relationForm.getFdOrgMemo());
					outerPerson.setFdMobileNo(relationForm.getFdOrgPhone());
					outerPerson
							.setFdWorkPhone(relationForm.getFdOrgWorkPhone());
					outerPerson.setFdPostDesc(relationForm.getFdOrgPost());
					outerPerson.setFdName(relationForm.getFdOrgName());
					outerPerson
							.setFdOtherInfo(relationForm.getFdOrgOtherInfo());
					orgId = getOuterOrgService().add(outerPerson);
				}
				orgRelation = new SysZoneOrgRelation();
				orgRelation.setFdCategoryId(cateId);
				orgRelation.setFdOrgType(relationForm.getFdOrgType());
				orgRelation.setFdOrgId(orgId);
				orgRelation.setFdOrgMemo(relationForm.getFdOrgMemo());
				orgRelation.setFdOrder(0);
				getSysZoneOrgRelationService().add(orgRelation);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		String forward = "edit";
		String cateType = request.getParameter("cateType");
		if (StringUtil.isNull(cateType)) {
			messages.addError(new Exception("缺少参数cateType"));
		} else {
			if ("inner".equals(cateType.toLowerCase())) {
				forward += "Inner";
			} else if ("outer".equals(cateType.toLowerCase())) {
				forward += "Outer";
			}
			try {
				loadActionForm(mapping, form, request, response);
			} catch (Exception e) {
				messages.addError(e);
			}
		}
		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward(forward, mapping, form, request, response);
		}
	}

	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		String forward = "edit";
		String cateType = request.getParameter("cateType");
		if (StringUtil.isNull(cateType)) {
			messages.addError(new Exception("缺少参数cateType"));
		} else {
			if ("inner".equals(cateType.toLowerCase())) {
				forward += "Inner";
			} else if ("outer".equals(cateType.toLowerCase())) {
				forward += "Outer";
			}
			try {
				ActionForm newForm = createNewForm(mapping, form, request, response);
				if (newForm != form) {
                    request.setAttribute(getFormName(newForm, request), newForm);
                }
			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward(forward, mapping, form, request, response);
		}
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String cateId = request.getParameter("fdId");
			String cateType = request.getParameter("menuType");
			getSysZoneOrgRelationService().deleteAllCateRelations(cateId,
					cateType);
			getSysZoneAddressCateVoService().deleteByCateId(cateId);
			if (StringUtil.isNull(cateId)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).delete(cateId);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
}
