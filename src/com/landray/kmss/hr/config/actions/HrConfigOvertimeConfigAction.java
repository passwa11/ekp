package com.landray.kmss.hr.config.actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mina.proxy.utils.StringUtilities;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.config.forms.HrConfigOvertimeConfigForm;
import com.landray.kmss.hr.config.model.HrConfigOvertimeConfig;
import com.landray.kmss.hr.config.service.IHrConfigOvertimeConfigService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
  * 加班规则配置 Action
  */
public class HrConfigOvertimeConfigAction extends ExtendAction {

    private IHrConfigOvertimeConfigService hrConfigOvertimeConfigService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrConfigOvertimeConfigService == null) {
            hrConfigOvertimeConfigService = (IHrConfigOvertimeConfigService) getBean("hrConfigOvertimeConfigService");
        }
        return hrConfigOvertimeConfigService;
    }

    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrConfigOvertimeConfig.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.config.util.HrConfigUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.config.model.HrConfigOvertimeConfig.class);
        com.landray.kmss.hr.config.util.HrConfigUtil.buildHqlInfoModel(hqlInfo, request);
    }

    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrConfigOvertimeConfigForm hrConfigOvertimeConfigForm = (HrConfigOvertimeConfigForm) super.createNewForm(mapping, form, request, response);
        ((IHrConfigOvertimeConfigService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return hrConfigOvertimeConfigForm;
    }
    
    /**
     * 获取对应加班配置
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String getOvertimeConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject rtn = new JSONObject();
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fdPersonId=request.getParameter("fdPersonId");//申请人
		String fdRankName=request.getParameter("fdRankName");//职级
		String fdOvertimeType=request.getParameter("fdOvertimeType");//加班类型
		HrConfigOvertimeConfig config=((IHrConfigOvertimeConfigService) getServiceImp(request)).getDataByParams(fdPersonId, fdRankName, fdOvertimeType);
		if(config!=null){
			rtn.put("fdOvertimeWelfare", config.getFdOvertimeWelfare());
			rtn.put("fdName", config.getFdName());
			rtn.put("fdId", config.getFdId());
		}
		response.getWriter().write(rtn.toString());
		return null;
	}
    
    
    /**
     * 判断单据是否能提交,
     * 1.判断是否超时
     * 2.不满一个小时不能提交
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void canSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject rtn = new JSONObject();
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fdPersonId=request.getParameter("fdPersonId");//申请人
		String fdStartTime=request.getParameter("fdStartTime");//申请开始时间
		String fdEndTime=request.getParameter("fdEndTime");//申请结束时间
		SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");
		Date date = myFmt2.parse(fdStartTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,-1);
		rtn=((IHrConfigOvertimeConfigService) getServiceImp(request)).canSubmit(fdPersonId, myFmt2.format(calendar.getTime()),fdEndTime);
		response.getWriter().write(rtn.toString());
	}
    
    /**
     * 判断批量加班单据是否能提交,
     * 1.在这个月4号之前提交
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void canSubmitBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject rtn = new JSONObject();
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String month=request.getParameter("month");//申请开始时间
		//根据归属月获取当前月份时间
		String year=DateUtil.convertDateToString(new Date(), "yyyy");
		if(Integer.valueOf(month)>10){
			month="0"+month;
		}
		
		Date startDate=DateUtil.convertStringToDate(year+month,"yyyyMM");
		rtn=((IHrConfigOvertimeConfigService) getServiceImp(request)).canSubmitBatch(startDate);
		response.getWriter().write(rtn.toString());
	}
    
   
    /**
     * 根据申请时间获取对应加班类别
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void getOvertimeType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject rtn = new JSONObject();
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fdPersonId=request.getParameter("fdPersonId");//申请人
		String fdStartTime=request.getParameter("fdStartTime");//申请开始时间
		
		rtn=((IHrConfigOvertimeConfigService) getServiceImp(request)).getOvertimeType(fdPersonId, fdStartTime);
		response.getWriter().write(rtn.toString());
	}
    
    
    
    
    /**
     * 根据不同流程以及职级获取值
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void getValueByRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject rtn = new JSONObject();
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fdRank=request.getParameter("fdRankName");//职级
		String fdRankType=request.getParameter("fdRankType");//职类
		String first=request.getParameter("first");//一级部门负责人
		String fdTemplateId=request.getParameter("fdTemplateId");//类型id
		String fdRankValue=fdRank;
		if(StringUtil.isNotNull(fdRankType)){
			fdRankValue+="-"+fdRankType;
		}
		System.out.println("路径1："+System.getProperty("user.dir"));
		String url=request.getParameter("url");//类型id
		if(StringUtil.isNull(url)){
			url="lc-config.properties";
		}
		rtn=((IHrConfigOvertimeConfigService) getServiceImp(request)).getValueByRank(fdRankValue+";", fdTemplateId,first,url);
		response.getWriter().write(rtn.toString());
	}
    
    
    
    /**
     * 校验发票
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void checkInvoice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject rtn = new JSONObject();
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fdInvoice=request.getParameter("fdInvoice");//发票
		rtn=((IHrConfigOvertimeConfigService) getServiceImp(request)).checkInvoice(fdInvoice);
		response.getWriter().write(rtn.toString());
	}
}
