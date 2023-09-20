package com.landray.kmss.km.comminfo.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.comminfo.forms.KmComminfoCategoryForm;
import com.landray.kmss.km.comminfo.model.KmComminfoCategory;
import com.landray.kmss.km.comminfo.service.IKmComminfoCategoryService;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2010年5月4日 13:51:12
 * 
 * @author 徐乃瑞
 * 
 */

public class KmComminfoCategoryAction extends ExtendAction {

	protected IKmComminfoCategoryService kmComminfoCategoryService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmComminfoCategoryService == null) {
            kmComminfoCategoryService = (IKmComminfoCategoryService) getBean("kmComminfoCategoryService");
        }
		return kmComminfoCategoryService;
	}

	/**
	 * 类别
	 */
	protected IKmComminfoCategoryService getKmComminfoCategoryServiceImp(
			HttpServletRequest request) {
		if (kmComminfoCategoryService == null) {
            kmComminfoCategoryService = (IKmComminfoCategoryService) getBean("kmComminfoCategoryService");
        }
		return kmComminfoCategoryService;
	}

	/**
	 * 初始化表格
	 */
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmComminfoCategoryForm kmComminfoCategoryForm = (KmComminfoCategoryForm) super
				.createNewForm(mapping, form, request, response);

		// 默认创建时间
		kmComminfoCategoryForm.setDocCreateTime(DateUtil.convertDateToString(
				new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
		// 默认创建者
		kmComminfoCategoryForm
				.setDocCreatorName(UserUtil.getUser().getFdName());

		return kmComminfoCategoryForm;
	}

	/**
	 * 检查要增加的类别名称是否已存在
	 */
	public ActionForward checkCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkConflict", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String categoryName = request.getParameter("fdName");
			categoryName = "kmComminfoCategory.fdName = '" + categoryName + "'";
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(categoryName);
			List list = getServiceImp(request).findList(hqlInfo);
			if (list.size() > 0) {
				json.put("result", true);
			} else {
				json.put("result", false);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		response.getWriter().write(json.toString());
		response.setCharacterEncoding("UTF-8");
		TimeCounter.logCurrentTime("Action-checkConflict", false, getClass());
		return null;
	}

	/**
	 * 查看类别时不显示系统默认的”其他"类别
	 */
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = null;
		whereBlock = "kmComminfoCategory.fdId <> 'other'";
		hqlInfo.setWhereBlock(whereBlock);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, KmComminfoCategory.class);
		//hqlInfo.setOrderBy("kmComminfoCategory.fdOrder");
	}

	/**
	 * 获取筛选器中的类别
	 */
	public ActionForward getComminfoCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy("kmComminfoCategory.fdOrder");
		List<?> result = getServiceImp(request).findList(hqlInfo);
		JSONArray jsonArr = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		for (int i = 0; i < result.size(); i++) {
			KmComminfoCategory kmComminfoCategory = (KmComminfoCategory) result
					.get(i);
			jsonObj.put("text", kmComminfoCategory.getFdName());
			jsonObj.put("value", kmComminfoCategory.getFdId());
			jsonArr.add(jsonObj);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonArr.toString());
		return null;
	}

	/**
	 * 分类转移
	 */
	public ActionForward transfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		String requiredFieldMessage = null;
		try {

			String[] cateIds = request.getParameter("cateIds").split(
					"\\s*;\\s*");
			String[] newCateIds = new String[cateIds.length];
			;
			String cateId = request.getParameter("cateId");
			if (cateIds == null || cateId == null) {
				json.put("value", "true");
			}
			// 判断是否分类是否为否，若为否则删除cateIds
			List<KmComminfoCategory> categorys = getServiceImp(request)
					.findByPrimaryKeys(cateIds);
			for (int i = 0, j = 0; i < categorys.size(); i++) {
				newCateIds[j] = cateIds[i];
				j++;
			}
			if (StringUtil.isNull(newCateIds[0])) {
				json.put("success", true);
				requiredFieldMessage = ResourceUtil.getString(
						"kmComminfo.isTranfer.isNull", "km-comminfo");
				json.put("message", requiredFieldMessage);
			} else {
				((IKmComminfoCategoryService) this.getServiceImp(request))
						.updateDataFromCategorysTo(newCateIds, cateId);
				json.put("value", "true");
				requiredFieldMessage = ResourceUtil.getString(
						"kmComminfo.isTranfer.success", "km-comminfo");
				json.put("message", requiredFieldMessage);
			}

		} catch (Exception e) {
			messages.addError(e);

		}

		KmssReturnPage.getInstance(request).setTitle(
				new KmssMessage("km-comminfo:kmComminfo.success.title"))
				.addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN)
				.save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		String modelName = request.getParameter("modelName");
		String forward = "failure";
		String[] ids = null;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			ids = request.getParameterValues("List_Selected");

			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}
				if (!ArrayUtils.isEmpty(authIds)) {
                    getServiceImp(request).delete(authIds);
                }
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (Exception e) {
			messages.setHasError();
			messages.addMsg(new KmssMessage(
					"km-comminfo:kmComminfo.deleteall.tip"));
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward(forward, mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
}
