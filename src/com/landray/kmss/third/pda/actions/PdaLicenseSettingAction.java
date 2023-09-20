package com.landray.kmss.third.pda.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.actions.BaseAction;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.pda.service.IPdaLicenseSettingService;
import com.landray.kmss.third.pda.util.LicenseUtil;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class PdaLicenseSettingAction extends BaseAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PdaLicenseSettingAction.class);

	private IPdaLicenseSettingService pdaLicenseSettingService = null;

	private IPdaLicenseSettingService getPdaLicenseSettingService() {
		if (this.pdaLicenseSettingService == null) {
            this.pdaLicenseSettingService = (IPdaLicenseSettingService) SpringBeanUtil
                    .getBean("pdaLicenseSettingService");
        }
		return this.pdaLicenseSettingService;
	}

	/**
	 * 打开配置界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward config(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return getForward(mapping, form, request, response);
	}

	/**
	 * 保存增加的license配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String phonePersons = request.getParameter("phonePersonIds");
		String phonePersonNames = request.getParameter("phonePersonNames");
		String forward = request.getParameter("s_forward");
		IPdaLicenseSettingService settingService = getPdaLicenseSettingService();
		int phoneLimit = LicenseUtil.getPhoneLicence();
		int phoneErrorCount = 0;
		List phonesMap = settingService.getAccessorList(null);
		if (StringUtil.isNotNull(phonePersons)) { // 数据库插入手机版授权用户
			String[] phones = phonePersons.split(";");
			Object[] phoneList = settingService.praseOrgInfo(phones);
			int oldCount = phonesMap != null ? phonesMap.size() : 0;
			if ((phoneList.length + oldCount) > phoneLimit) {
				phoneErrorCount = phoneList.length;
				forward = "edit";
			} else {
                settingService.addAccessorList(settingService
                        .praseOrgInfo(phones), false);
            }
		}

		if (StringUtil.isNull(forward) || "success".equalsIgnoreCase(forward)) {
			forward = "success";
			settingService.getAccessorList(null);
			KmssReturnPage.getInstance(request).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
		} else {
			if ("edit".equalsIgnoreCase(forward)) {
				if (phoneErrorCount > 0) {
					request.setAttribute("phoneErrorCount", phoneErrorCount);
					request.setAttribute("phonePersonIds", phonePersons);
					request.setAttribute("phonePersonNames", phonePersonNames);
				}
				if ("1".equals(request.getParameter("isAdd"))) {
                    request.setAttribute("s_forward", "success");
                } else {
                    request.setAttribute("s_forward", "view");
                }

			}
			getForward(mapping, form, request, response);
		}
		return mapping.findForward(forward);
	}

	/**
	 * 维护界面的删除处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String phonePersons = request.getParameter("s_phones");

		IPdaLicenseSettingService settingService = getPdaLicenseSettingService();

		if (StringUtil.isNotNull(phonePersons)) {
			String[] phones = phonePersons.split(";");
			settingService.deleteOrgs(phones);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("删除数据,并重获配置!");
		}
		return getForward(mapping, form, request, response);
	}

	/**
	 * 剔除失效组织架构人员
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPdaLicenseSettingService settingService = getPdaLicenseSettingService();
		HQLInfo hqInfo = new HQLInfo();
		hqInfo.setSelectBlock("pdaLicenseSetting.fdId");
		hqInfo
				.setWhereBlock("pdaLicenseSetting.fdAccessor.fdIsAvailable=false");
		List avalList = settingService.findValue(hqInfo);
		if (avalList != null && avalList.size() > 0) {
			logger.debug("失效组织架构 " + avalList.size() + "个");
			String arrStr = "";
			for (Iterator iterator = avalList.iterator(); iterator.hasNext();) {
				String tmp = (String) iterator.next();
				if (StringUtil.isNotNull(tmp)) {
					tmp = "'" + tmp + "'";
					arrStr += "," + tmp;
				}
			}
			if (StringUtil.isNotNull(arrStr)) {
				arrStr = arrStr.substring(1);
				int count = settingService
						.deleteExecuteQuery("delete from PdaLicenseSetting pdaLicenseSetting "
								+ "where pdaLicenseSetting.fdId in"
								+ " ( "
								+ arrStr + " )");
				LicenseUtil.clearKmssCachePdaLicense();
				if (logger.isDebugEnabled()) {
					logger.debug("剔除失效组织架构配置" + count + "个,并重获配置!");
				}
			}
		}
		return getForward(mapping, form, request, response);
	}

	/**
	 * 返回页面处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private ActionForward getForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPdaLicenseSettingService settingService = getPdaLicenseSettingService();

		request.setAttribute("PhoneUseFlag", LicenseUtil.getPhoneLicence());
		String sort = request.getParameter("sort");
		List phoneMap = settingService.getAccessorList(sort);
		List tmpList = new ArrayList();
		tmpList.addAll(phoneMap);
		if (phoneMap != null && !phoneMap.isEmpty()) {
            request.setAttribute("phoneMap", phoneMap);
        }

		if (tmpList.isEmpty() || tmpList.size() == 0) {
			request.setAttribute("s_forward", "view");
			return mapping.findForward("edit");
		} else {
			String isAdd = request.getParameter("isAdd");
			if ("1".equals(isAdd)) {
				return mapping.findForward("edit");
			} else {
				request.setAttribute("sort", sort);
				return mapping.findForward("view");
			}
		}
	}
}
