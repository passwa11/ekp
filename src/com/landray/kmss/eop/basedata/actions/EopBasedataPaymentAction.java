package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataPaymentForm;
import com.landray.kmss.eop.basedata.model.EopBasedataPayment;
import com.landray.kmss.eop.basedata.service.IEopBasedataPaymentDataService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPaymentService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EopBasedataPaymentAction extends ExtendAction {

    private IEopBasedataPaymentService eopBasedataPaymentService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataPaymentService == null) {
            eopBasedataPaymentService = (IEopBasedataPaymentService) getBean("eopBasedataPaymentService");
        }
        return eopBasedataPaymentService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataPayment.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataPaymentForm eopBasedataPaymentForm = (EopBasedataPaymentForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataPaymentService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataPaymentForm;
    }
    /**
     * 获取使用了付款单业务的模块信息
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForm getModelName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray rtn = new JSONArray();
		JSONObject node = null;
    	List<IBaseService> services = SpringBeanUtil.getBeansForType(IEopBasedataPaymentDataService.class);
    	SysDataDict data = SysDataDict.getInstance();
		for(IBaseService service : services){
			String fdModelName = service.getModelName();
			SysDictModel dict = data.getModel(fdModelName);
			String[] key = dict.getMessageKey().split(":");
			node = new JSONObject();
			node.put("text", ResourceUtil.getString(key[1],key[0]));
			node.put("value", fdModelName);
			rtn.add(node);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
    	return null;
    }
    /**
     * 校验付款单金额与原单据金额是否匹配
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return 
     * @throws Exception
     */
    public ActionForm checkPayMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String fdModelName = request.getParameter("fdModelName");
    	String fdModelId = request.getParameter("fdModelId");
    	String info = request.getParameter("info");
    	SysDictModel dict = SysDataDict.getInstance().getModel(fdModelName);
		IEopBasedataPaymentDataService service = (IEopBasedataPaymentDataService) SpringBeanUtil.getBean(dict.getServiceBean());
    	String rtn = service.checkMoney(fdModelId, JSONObject.fromObject(info));
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().write(rtn);
		return null;
    }
    /**
     * 批量确认付款
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForm batchConfirmPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String fdModelName = request.getParameter("fdModelName");
    	String ids = request.getParameter("ids");
    	JSONObject rtn = new JSONObject();
    	rtn.put("result", "success");
		try {
			SysDictModel dict = SysDataDict.getInstance().getModel(fdModelName);
			IEopBasedataPaymentDataService service = (IEopBasedataPaymentDataService) SpringBeanUtil.getBean(dict.getServiceBean());
			rtn = service.updatePyament(ids,"paymentList");
		} catch (Exception e) {
			rtn.put("result", "faliure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
    }
}
