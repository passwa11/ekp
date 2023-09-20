package com.landray.kmss.sys.attachment.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.springframework.util.StringUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.filestore.forms.SysAttCatalogForm;
import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.sys.filestore.service.ISysAttCatalogService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

/**
 * 目录配置 Action
 * 
 * @author 李衡
 * @version 1.0 2012-08-29
 */
public class SysAttCatalogAction extends ExtendAction {
	protected ISysAttCatalogService sysAttCatalogService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysAttCatalogService == null) {
            sysAttCatalogService = (ISysAttCatalogService) getBean("sysAttCatalogService");
        }
		return sysAttCatalogService;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttCatalog.class);
	}

	/**
	 * 删除操作，需提示转移文件到某个指定目录
	 */
	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		boolean jumpToConfirm = false;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null) {
				if (ids.length > 0) {
					List selectUsedList = new ArrayList();
					HQLInfo info = new HQLInfo();
					info.setSelectBlock("distinct sysAttFile.fdCata.fdId");
					info.setModelName("SysAttFile");
					List usedList = sysAttCatalogService.findValue(info);
					if (!usedList.isEmpty()) {
						for (int i = 0; i < ids.length; i++) {
							if (usedList.contains(ids[i])) {
								selectUsedList.add(ids[i]);
								jumpToConfirm = true;
							} else {
                                sysAttCatalogService.delete(ids[i]);
                            }
						}
					} else {
						sysAttCatalogService.delete(ids);
					}
					if (!selectUsedList.isEmpty()) {
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo
								.setSelectBlock(" sysAttCatalog.fdId ,sysAttCatalog.fdName , COUNT(sysAttCatalog.fdId) ");
						hqlInfo
								.setFromBlock("SysAttCatalog sysAttCatalog , SysAttFile sysAttFile ");
						hqlInfo
								.setWhereBlock("sysAttCatalog.fdId = sysAttFile.fdCata.fdId and "
										+ HQLUtil.buildLogicIN(
												"sysAttCatalog.fdId",
												selectUsedList)
										+ " group by sysAttCatalog.fdId,sysAttCatalog.fdName ");

						List deletingList = sysAttCatalogService
								.findValue(hqlInfo);
						request.setAttribute("currentIds", StringUtils
								.arrayToDelimitedString(selectUsedList
										.toArray(), ";"));
						request.setAttribute("deletingList", deletingList);
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			if (jumpToConfirm) {
                return getActionForward("confirm", mapping, form, request,
                        response);
            } else {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
				return getActionForward("success", mapping, form, request,
						response);
			}
		}
	}

	/*
	 * 确认目录转移
	 */
	public ActionForward updateCataFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateCurrent", true, getClass());
		int updated = 0;
		int deleted = 0;
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysAttCatalogForm catalogForm = (SysAttCatalogForm) form;
			List checkIds = catalogForm.getCheckIds();
			List changeIds = catalogForm.getChangeIds();
			if (checkIds.size() != changeIds.size()) {
                throw new KmssException(messages.addError(new KmssMessage(
                        "sys-attachment:sysAttCatalog.updateCataFile.error")));
            }

			for (int i = 0; i < checkIds.size(); i++) {
				String chkId = (String) checkIds.get(i);
				String chgId = (String) changeIds.get(i);
				String updateHql = "update SysAttFile sysAttFile set sysAttFile.fdCata.fdId=:chgId " +
						"where sysAttFile.fdCata.fdId=:chkId";
				updated += sysAttCatalogService.getBaseDao()
						.getHibernateSession().createQuery(updateHql)
						.setParameter("chgId", chgId).setParameter("chkId",
								chkId).executeUpdate();
			}
			deleted = sysAttCatalogService.getBaseDao().getHibernateSession()
					.createQuery(
							"delete from SysAttCatalog sysAttCatalog where "
									+ HQLUtil.buildLogicIN(
											"sysAttCatalog.fdId", checkIds))
					.executeUpdate();
			messages.addMsg(new KmssMessage(
					"sys-attachment:sysAttCatalog.updateCataFile.info",
					updated, deleted));
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-updateCurrent", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 
	 * 设置某个目录为当前目录
	 */
	public ActionForward updateCurrent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateCurrent", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null && ids.length > 0) {
				if (ids.length > 1) {
                    throw new KmssException(messages.addError(new KmssMessage(
                            "sys-attachment:sysAttCatalog.update.error")));
                } else {
					sysAttCatalogService.updateCurrent(ids[0]);
				}

			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-updateCurrent", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
}
