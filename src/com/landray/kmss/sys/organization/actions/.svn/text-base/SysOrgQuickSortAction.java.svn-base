package com.landray.kmss.sys.organization.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.forms.SysOrgQuickSortElement;
import com.landray.kmss.sys.organization.forms.SysOrgQuickSortForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

public class SysOrgQuickSortAction extends BaseAction {

	protected ISysOrgElementService sysOrgElementService = null;

	protected ISysOrgElementService getSysOrgElementServiceImp(
			HttpServletRequest request) {
		if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
        }
		return sysOrgElementService;
	}

	/**
	 * 打开快速排序界面。<br>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回快速排序页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward quickSort(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String parentId = request.getParameter("parentId");
			String type = request.getParameter("type");
			HQLInfo hqlInfo = new HQLInfo();
			String orderBy = "sysOrgElement.fdOrder";
			String whereBlock = "sysOrgElement.fdIsAvailable = :fdIsAvailable";
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
			if (StringUtil.isNotNull(parentId)) {
				whereBlock += " and sysOrgElement.hbmParent.fdId = :parentId ";
				hqlInfo.setParameter("parentId", parentId);
			} else {
				whereBlock += " and sysOrgElement.hbmParent is null";
			}
			if (StringUtil.isNotNull(type)) {
				whereBlock += " and sysOrgElement.fdOrgType = :fdOrgType";
				hqlInfo.setParameter("fdOrgType", Integer.valueOf(type));
			}
			String selectBlock = "sysOrgElement.fdId,sysOrgElement.fdOrder,sysOrgElement.fdName";
			hqlInfo.setOrderBy(orderBy);
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setSelectBlock(selectBlock);
			List elementList = getSysOrgElementServiceImp(request).findList(
					hqlInfo);
			SysOrgQuickSortForm sysOrgQuickSortForm = new SysOrgQuickSortForm();
			List<SysOrgQuickSortElement> elements = new ArrayList<SysOrgQuickSortElement>();
			for (int i = 0, size = elementList.size(); i < size; i++) {
				Object[] val = (Object[]) elementList.get(i);
				SysOrgQuickSortElement sysOrgQuickSortElement = new SysOrgQuickSortElement();
				sysOrgQuickSortElement.setFdId(val[0].toString());
				sysOrgQuickSortElement.setFdOrder(val[1] != null ? val[1]
						.toString() : "");
				sysOrgQuickSortElement.setFdName(val[2].toString());
				elements.add(sysOrgQuickSortElement);
			}
			sysOrgQuickSortForm.setElements(elements);
			request.setAttribute("sysOrgQuickSortForm", sysOrgQuickSortForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("quickSort");
		}
	}

	/**
	 * 保存排序
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveSort(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			SysOrgQuickSortForm sysOrgQuickSortForm = (SysOrgQuickSortForm) form;
			List<SysOrgQuickSortElement> elements = sysOrgQuickSortForm
					.getElements();
			for (int i = 0, size = elements.size(); i < size; i++) {
				SysOrgQuickSortElement sysOrgQuickSortElement = elements.get(i);
				SysOrgElement sysOrgElement = (SysOrgElement) getSysOrgElementServiceImp(
						request).findByPrimaryKey(
						sysOrgQuickSortElement.getFdId());
				sysOrgElement
						.setFdOrder(StringUtil.isNotNull(sysOrgQuickSortElement
								.getFdOrder()) ? Integer
								.parseInt(sysOrgQuickSortElement.getFdOrder())
								: null);
				getSysOrgElementServiceImp(request).update(sysOrgElement);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			KmssMessage message = new KmssMessage("return.optSuccess");
			messages.addMsg(message);
			KmssReturnPage.getInstance(request).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("success");
		}
	}
}
