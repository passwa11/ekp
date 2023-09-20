package com.landray.kmss.tic.jdbc.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import org.apache.commons.lang.ArrayUtils;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.tic.core.common.actions.TicExtendAction;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.common.service.ITicCoreTransSettService;
import com.landray.kmss.tic.core.util.RecursionUtil;
import com.landray.kmss.tic.jdbc.forms.TicJdbcDataSetForm;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcQueryService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 数据集管理 Action
 *
 * @author
 * @version 1.0 2014-04-15
 */
public class TicJdbcDataSetAction extends TicExtendAction {
	protected ITicJdbcDataSetService ticJdbcDataSetService;

	@Override
    protected ITicJdbcDataSetService getServiceImp(HttpServletRequest request) {
		if(ticJdbcDataSetService == null) {
			ticJdbcDataSetService = (ITicJdbcDataSetService)getBean("ticJdbcDataSetService");
		}
		return ticJdbcDataSetService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		if(!StringUtil.isNull(categoryId)){
			String hql=hqlInfo.getWhereBlock();
			hql=StringUtil.linkString(hql, " and ", "ticJdbcDataSet.docCategory.fdHierarchyId like :fdHierarchyId ");
			hqlInfo.setParameter("fdHierarchyId", "%"+categoryId+"%");
			hqlInfo.setWhereBlock(hql);
		}
	}

	/**
	 * 加载数据库的表信息
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void loadDbtableDatas(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getServiceImp(request).loadDbtableDatas(request, response);
	}

	@Override
    public ActionForward saveadd(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}

			TicJdbcDataSetForm dataform = (TicJdbcDataSetForm) form;
			String fdata = dataform.getFdData();
			dataform.setFdParaIn(RecursionUtil.paseJdbcIn(
					JSON.parseObject(fdata).getJSONArray("in"),
					null));
			dataform.setFdParaOut(RecursionUtil.paseJdbcOut(JSON.parseObject(fdata).getJSONArray("out")));

			getServiceImp(request).add((IExtendForm) dataform,
					new RequestContext(request));
			String licenseLding = LicenseUtil.get("license-lding");
			if(!"true".equals(licenseLding)){//蓝钉场景不用生成默认的转换函数
				//生成默认转换配置信息
				((ITicCoreTransSettService) SpringBeanUtil
						.getBean("ticCoreTransSettService")).addTicCoreTransSett(
						(TicCoreFuncBase) ((ITicJdbcDataSetService) getServiceImp(
								request))
								.findByPrimaryKey(
										((IExtendForm) dataform)
												.getFdId()));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return add(mapping, form, request, response);
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

			TicJdbcDataSetForm dataform = (TicJdbcDataSetForm) form;
			String fdata = dataform.getFdData();
			dataform.setFdParaIn(RecursionUtil.paseJdbcIn(
					JSON.parseObject(fdata).getJSONArray("in"), null));
			dataform.setFdParaOut(RecursionUtil.paseJdbcOut(JSON.parseObject(fdata).getJSONArray("out")));

			getServiceImp(request).add((IExtendForm) dataform,
					new RequestContext(request));
			String licenseLding = LicenseUtil.get("license-lding");
			if(!"true".equals(licenseLding)){//蓝钉场景不用生成默认的转换函数
				//生成默认转换配置信息
				((ITicCoreTransSettService) SpringBeanUtil
						.getBean("ticCoreTransSettService")).addTicCoreTransSett(
						(TicCoreFuncBase) ((ITicJdbcDataSetService) getServiceImp(
								request))
								.findByPrimaryKey(
										((IExtendForm) dataform)
												.getFdId()));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));

			TicJdbcDataSet dataSet = (TicJdbcDataSet)getServiceImp(request).findByPrimaryKey(((IExtendForm)form).getFdId());
			String data = dataSet.getFdData();
			dataSet.setFdParaIn(RecursionUtil.paseJdbcIn(JSON.parseObject(data).getJSONArray("in"), null));
			dataSet.setFdParaOut(RecursionUtil.paseJdbcOut(JSON.parseObject(data).getJSONArray("out")));

			((ITicCoreTransSettService) SpringBeanUtil
					.getBean("ticCoreTransSettService")).addTicCoreTransSett(
					(TicCoreFuncBase) ((ITicJdbcDataSetService) getServiceImp(
							request))
							.findByPrimaryKey(
									((IExtendForm) form)
											.getFdId()));

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			// 返回按钮
			IExtendForm mainForm = (IExtendForm) form;
			String fdModelId = mainForm.getFdId();
			String fdModelName = mainForm.getModelClass().getName();
			String fdAppType = request.getParameter("fdAppType");
			SysDictModel model = SysDataDict.getInstance()
					.getModel(fdModelName);
			if (model != null && !StringUtil.isNull(model.getUrl())) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(
								"button.back",
								formatModelUrl(fdModelId,fdAppType, model.getUrl()),
								false)
						.save(request);
			}

			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	private String formatModelUrl(String value, String fdAppType, String url) {
		if (StringUtil.isNull(url)) {
			return null;
		}
		Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(url);
		while (m.find()) {
			String property = m.group(1);
			try {
				if("fdAppType".equals(property)){
					url = StringUtil.replace(url, "${" + property + "}", fdAppType);
				}
				else{
					url = StringUtil.replace(url, "${" + property + "}", value);
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return url;
	}

	public ActionForward viewQueryEdit(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-viewQueryEdit", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdFuncId = request.getParameter("fdFuncId");

		ITicJdbcDataSetService ticJdbcDataSetService = (ITicJdbcDataSetService) SpringBeanUtil
				.getBean("ticJdbcDataSetService");
		TicJdbcDataSet ticJdbcDataSet = (TicJdbcDataSet) ticJdbcDataSetService
				.findByPrimaryKey(fdFuncId);
		request.setAttribute("ticJdbcDataSetId", fdFuncId);
		request.setAttribute("ticJdbcDataSetName", ticJdbcDataSet.getFdName());
		request.setAttribute("fdSqlExpression",ticJdbcDataSet.getFdSqlExpression());
		request.setAttribute("dataJson", ticJdbcDataSet.getFdData());
		TimeCounter.logCurrentTime("Action-viewQueryEdit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("viewQuery", mapping, form, request,
					response);
		}
	}
	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			for (String id : ids) {
				boolean noTransSett = ((ITicCoreTransSettService) SpringBeanUtil
						.getBean("ticCoreTransSettService"))
						.validateTransSett(id);
				boolean noQuery = ((ITicJdbcQueryService) SpringBeanUtil
						.getBean("ticJdbcQueryService"))
						.validateQuery(id);
				if (noTransSett && noQuery) {
				} else {
					throw new KmssException(
							new KmssMessage(
									"tic-core-common:function.delete.fail"));
				}
			}
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
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		return getActionForward("success", mapping, form, request, response);
	}

	@Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			boolean noTransSett = ((ITicCoreTransSettService) SpringBeanUtil
					.getBean("ticCoreTransSettService"))
					.validateTransSett(id);
			boolean noQuery = ((ITicJdbcQueryService) SpringBeanUtil
					.getBean("ticJdbcQueryService"))
					.validateQuery(id);
			if (noTransSett && noQuery) {
			} else {
				throw new KmssException(
						new KmssMessage(
								"tic-core-common:function.delete.fail"));
			}
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).delete(id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null){
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				TicJdbcDataSetForm ticJdbcDataSetForm = (TicJdbcDataSetForm)rtnForm;
				String fdData = ticJdbcDataSetForm.getFdData();
				if(StringUtil.isNotNull(fdData)){
					JSONObject fdDataObj = JSONObject.parseObject(fdData);
					JSONArray inArray = fdDataObj.getJSONArray("in");
					JSONArray inArray_result = new JSONArray()	;
					if(inArray!=null){
						for(int i=0;i<inArray.size();i++){
							JSONObject inObj = inArray.getJSONObject(i);
							Boolean isJdbcSearch = inObj.getBoolean("isJdbcSearch");
							if(isJdbcSearch==null || isJdbcSearch==false){
								inArray_result.add(inObj);
							}
						}
						fdDataObj.put("in",inArray_result);
					}
					ticJdbcDataSetForm.setFdData(fdDataObj.toString());
				}
				UserOperHelper.logFind(model);
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

}

