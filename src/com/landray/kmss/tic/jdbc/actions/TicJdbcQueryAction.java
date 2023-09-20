package com.landray.kmss.tic.jdbc.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.tic.core.log.constant.TicCoreLogConstant;
import com.landray.kmss.tic.core.middleware.TicMiddlewareService;
import com.landray.kmss.tic.jdbc.forms.TicJdbcQueryForm;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.model.TicJdbcQuery;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcQueryService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.sso.client.oracle.StringUtil;

public class TicJdbcQueryAction extends ExtendAction {

	protected ITicJdbcQueryService TicJdbcQueryService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicJdbcQueryService == null) {
			TicJdbcQueryService = (ITicJdbcQueryService) getBean(
					"ticJdbcQueryService");
		}
		return TicJdbcQueryService;
	}
	
	/**
	 * json接口预览页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 严海星
	 * 2018年12月1日
	 */
	public ActionForward getJsonResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return getActionForward("json_result", mapping, form, request,
				response);
	}
	
	/**
	 * 保存查询结果
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 严海星
	 * 2018年12月1日
	 */
	public ActionForward saveSearchResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicJdbcQueryForm ticJdbcQueryForm = (TicJdbcQueryForm) form;
		String fdFuncId = request.getParameter("fdFuncId");
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			try {
			TicJdbcQuery ticJdbcQuery = (TicJdbcQuery) getServiceImp(request)
					.findByPrimaryKey(fdId);
			if (ticJdbcQuery != null) {
				ticJdbcQueryForm.setFdId(IDGenerator.generateID());
				ticJdbcQueryForm.setTicJdbcDataSetId(
						ticJdbcQuery.getTicJdbcDataSet().getFdId());
			}
			} catch (Exception e) {

			}
		}
		if (StringUtil.isNull(ticJdbcQueryForm.getTicJdbcDataSetId())) {
			ticJdbcQueryForm.setTicJdbcDataSetId(fdFuncId);
		}
		ticJdbcQueryForm.setFdId(null);
		return super.save(mapping, form, request, response);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 严海星
	 * 2018年12月1日
	 */
	@Override
    public ActionForward view(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicJdbcQueryForm ticJdbcQueryForm = (TicJdbcQueryForm) form;
		String fdId = ticJdbcQueryForm.getFdId();
		ITicJdbcQueryService ticJdbcQueryService = (ITicJdbcQueryService) SpringBeanUtil.getBean("ticJdbcQueryService");
		HQLInfo info = new HQLInfo();
		info.setSelectBlock("docSubject,docCreateTime,fdJsonResult,ticJdbcDataSet.fdSqlExpression,docCreator.fdName,ticJdbcDataSet.fdId");
		info.setWhereBlock("fdId='"+fdId+"'");
		Object[] o = (Object[]) ticJdbcQueryService.findFirstOne(info);
		ticJdbcQueryForm.setDocSubject((String)o[0]);
		ticJdbcQueryForm.setDocCreateTime(((java.sql.Timestamp)o[1]).toString());
		ticJdbcQueryForm.setFdJsonResult((String)o[2]);
		String fdSqlExpression = (String) o[3];
		ticJdbcQueryForm.setTicJdbcDataSetName(fdSqlExpression);
		ticJdbcQueryForm.setDocCreatorName((String)o[4]);
		ticJdbcQueryForm.setTicJdbcDataSetId((String)o[5]);
		
		//TicJdbcQuery tt = (TicJdbcQuery) ticJdbcQueryService.findByPrimaryKey(fdId, null, true);
		//TicJdbcQueryForm ff = (TicJdbcQueryForm) ticJdbcQueryService.convertModelToForm(ticJdbcQueryForm, tt, new RequestContext(request));
		
		return getActionForward("view", mapping, ticJdbcQueryForm, request,
				response);
	}
	
	/**
	 * 重新查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 严海星
	 * 2018年12月3日
	 */
	public ActionForward reQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-viewQueryEdit", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("ticJdbcDataSetId");

		ITicJdbcDataSetService ticJdbcDataSetService = (ITicJdbcDataSetService) SpringBeanUtil
				.getBean("ticJdbcDataSetService");
		TicJdbcDataSet ticJdbcDataSet = (TicJdbcDataSet) ticJdbcDataSetService
				.findByPrimaryKey(fdId);
		request.setAttribute("ticJdbcDataSetId", fdId);
		request.setAttribute("ticJdbcDataSetName", ticJdbcDataSet.getFdName());
		request.setAttribute("fdSqlExpression",
				ticJdbcDataSet.getFdSqlExpression());
		request.setAttribute("dataJson", ticJdbcDataSet.getFdData());
		TimeCounter.logCurrentTime("Action-viewQueryEdit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("reQuery", mapping, form, request,
					response);
		}
	}
	
	/**
	 * 查询结果页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 严海星
	 * 2018年11月27日
	 */
	public ActionForward getQueryResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicJdbcQueryForm ticJdbcQueryForm = (TicJdbcQueryForm) form;
		String ticJdbcDataSetId = ticJdbcQueryForm.getTicJdbcDataSetId();
		String queryFormData = ticJdbcQueryForm.getFdJsonResult();
		
		//用于获取测试结果后展示入参数据所用JSON
		//JSONObject viewJo = new JSONObject();
		//转化成调用函数所用的JSON
		JSONObject inJo = new JSONObject();
		JSONObject queryJo = JSON.parseObject(queryFormData);
		Set<String> keySet = queryJo.keySet();
		for(String key : keySet)
		{
			JSONArray array = queryJo.getJSONArray(key);
			/*JSONObject chilJo = new JSONObject();
			chilJo.put("type", array.get(0));
			chilJo.put("value", array.get(1));
			viewJo.put(key, chilJo);*/
			inJo.put(key, array.get(1));
		}
		
		TicMiddlewareService ticMiddlewareService = (TicMiddlewareService) SpringBeanUtil.getBean("ticMiddlewareService");
		String fdJsonResult = ticMiddlewareService.executeFromExecSource(inJo, ticJdbcDataSetId,TicCoreLogConstant.TIC_CORE_LOG_SOURCE_TEST.toString());
		JSONObject resultJo = JSON.parseObject(fdJsonResult);
		resultJo.put("view", queryJo);
		resultJo.put("in", inJo);
		ticJdbcQueryForm.setFdJsonResult(resultJo.toJSONString());
		return getActionForward("query_result", mapping, ticJdbcQueryForm, request,
				response);
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String funcId = request.getParameter("fdId");
		hqlInfo.setWhereBlock(
				"ticJdbcQuery.ticJdbcDataSet.fdId=:ticJdbcDataSetFdId");
		hqlInfo.setParameter("ticJdbcDataSetFdId", funcId);
	}
}
