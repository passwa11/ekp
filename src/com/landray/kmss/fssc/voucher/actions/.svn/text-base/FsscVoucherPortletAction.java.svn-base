package com.landray.kmss.fssc.voucher.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.fssc.common.service.spring.FsscCommonPortletService;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.voucher.constant.FsscVoucherConstant;
import com.landray.kmss.fssc.voucher.model.FsscVoucherMain;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherMainService;
import com.landray.kmss.fssc.voucher.service.spring.FsscVoucherPortletService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FsscVoucherPortletAction extends ExtendAction {

	private IFsscVoucherMainService fsscVoucherMainService;

	@Override
    public IFsscVoucherMainService getServiceImp(HttpServletRequest request) {
		if (fsscVoucherMainService == null) {
			fsscVoucherMainService = (IFsscVoucherMainService) getBean("fsscVoucherMainService");
		}
		return fsscVoucherMainService;
	}

	private FsscCommonPortletService fsscCommonPortletService;

	public FsscCommonPortletService getFsscCommonPortletService() {
		if (fsscCommonPortletService == null) {
			fsscCommonPortletService =  (FsscCommonPortletService)getBean("fsscCommonPortletService");
		}
		return fsscCommonPortletService;
	}

	private FsscVoucherPortletService fsscVoucherPortletService;

	public FsscVoucherPortletService getFsscVoucherPortletService() {
		if (fsscVoucherPortletService == null) {
			fsscVoucherPortletService =  (FsscVoucherPortletService)getBean("fsscVoucherPortletService");
		}
		return fsscVoucherPortletService;
	}

	private IEopBasedataCompanyService eopBasedataCompanyService;
	public IEopBasedataCompanyService getCompanyService(HttpServletRequest request) {
		if(eopBasedataCompanyService == null) {
			eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
		}
		return eopBasedataCompanyService;
	}
	public ActionForward countStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-loanAmount", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Calendar calendar = Calendar.getInstance();
			//当前年份
			int year = calendar.get(Calendar.YEAR);

			// 统计当前年份待记账数量
			String hql = "select count(1) from fssc_voucher_main where fd_bookkeeping_status =10 and fd_accounting_year='"+year+"'";
			Query query = getServiceImp(request).getBaseDao().getHibernateSession().createSQLQuery(hql);
			Number curCnt = (Number) query.uniqueResult();


			//上一年
			calendar.setTime(new Date());
			calendar.add(Calendar.YEAR, -1);
			Date y = calendar.getTime();
			String last = new SimpleDateFormat("yyyy").format(y);
			int lastYear = Integer.parseInt(last);
			// 统计上一年待记账数量
			String lastHql = "select count(1) from fssc_voucher_main where fd_bookkeeping_status =10 and fd_accounting_year='"+ lastYear + "'";
			Query result = getServiceImp(request).getBaseDao().getHibernateSession().createSQLQuery(lastHql);
			Number lastCount = (Number) result.uniqueResult();

			//获得当前时间的月份，月份从0开始所以结果要加 1
			int month=calendar.get(Calendar.MONTH)+1;
			// 统计当前时间月份待记账数量
			String monthHql = "select  count(1) from fssc_voucher_main where fd_bookkeeping_status =10 and fd_accounting_year='"+ lastYear + "' and fd_voucher_date='"+month+"'";
			Query monthResult = getServiceImp(request).getBaseDao().getHibernateSession().createSQLQuery(monthHql);
			Number monthCount = (Number) monthResult.uniqueResult();

			JSONObject obj = new JSONObject();
			obj.put("curCount", curCnt != null ? curCnt.intValue() : 0);
			obj.put("lastCount", lastCount != null ? lastCount.intValue() : 0);
			obj.put("monthCount", monthCount != null ? monthCount.intValue() : 0);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}

	/**
	 * 记账/待记账
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward bookkeeping(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
	        String status=request.getParameter("status");
	        String whereBlock=hqlInfo.getWhereBlock();
	        if(!StringUtil.isNotNull(status)) {
	        	return null;
	        }
			SysOrgElement user=UserUtil.getUser();
			if(!UserUtil.getKMSSUser().isAdmin()){
				if(UserUtil.checkRole("ROLE_FSSCVOUCHER_VIEW")){//查看权限
					String tempWhere="";
					//公司财务人员、财务管理员
					List<String> companyIdList=EopBasedataAuthUtil.getCompanyListAuth(user.getFdId());
					if(!ArrayUtil.isEmpty(companyIdList)){
						tempWhere=HQLUtil.buildLogicIN("fsscVoucherMain.fdCompany.fdId", companyIdList);
					}
					if(status.equals(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30)){
						whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscVoucherMain.fdBookkeepingPerson.fdId=:userId");
					}else {
						//只能看自己创建的
						tempWhere=StringUtil.linkString(tempWhere, " or ", "fsscVoucherMain.docCreator.fdId=:userId");
					}
					hqlInfo.setParameter("userId", user.getFdId());
					if(StringUtil.isNotNull(tempWhere)){
						whereBlock=StringUtil.linkString(whereBlock, " and ", "("+tempWhere+")");
					}
				}else {
					//只能看自己创建的
					if(status.equals(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30)){
						whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscVoucherMain.fdBookkeepingPerson.fdId=:userId");
					}else {
						whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscVoucherMain.docCreator.fdId=:userId");
					}
					hqlInfo.setParameter("userId", user.getFdId());
				}
			}
	        whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscVoucherMain.fdBookkeepingStatus=:status");
			whereBlock=StringUtil.linkString(whereBlock, " and ", " fsscVoucherMain.docCreateTime between :startDate and :endDate ");
			Calendar cal = Calendar.getInstance();
			Integer year=cal.get(Calendar.YEAR);
			hqlInfo.setParameter("startDate", DateUtil.convertStringToDate(year+"-01-01"));
			hqlInfo.setParameter("endDate", DateUtil.convertStringToDate(year+"-12-31 23:59:59"));
        	hqlInfo.setParameter("status",status);
	        hqlInfo.setWhereBlock(whereBlock);
	        hqlInfo.setRowSize(FsscVoucherConstant.FSSC_ROW_SIZE);
	        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
	        List<FsscVoucherMain> list = getServiceImp(request).findPage(hqlInfo).getList();
	        if(!list.isEmpty()) {
	        	JSONArray array =  new JSONArray();
	        	JSONObject obj = null;
	        	for (FsscVoucherMain voucherMain : list) {
	        		obj = new JSONObject();
	        		obj.put("company", 	voucherMain.getFdCompany().getFdName());//公司名称
	        		obj.put("financeNumber", voucherMain.getDocFinanceNumber() != null ? voucherMain.getDocFinanceNumber() : "");//财务凭证号
	        		obj.put("docNumber", voucherMain.getDocNumber() != null ? voucherMain.getDocNumber():"");//费控凭证号
	        		obj.put("mondelNumber", voucherMain.getFdModelNumber() != null ? voucherMain.getFdModelNumber():"");//来源单据编号
	        		obj.put("voucherType", 	voucherMain.getFdBaseVoucherType().getFdName() != null ? voucherMain.getFdBaseVoucherType().getFdName():"");//凭证类型
	        		obj.put("year", voucherMain.getFdAccountingYear() != null ? voucherMain.getFdAccountingYear():"");//会计年度
	        		obj.put("period", voucherMain.getFdPeriod() != null ? voucherMain.getFdPeriod():"");//期间
	        		obj.put("voucherDate", 	new SimpleDateFormat("yyyy-MM-dd").format(voucherMain.getFdVoucherDate()));//凭证日期
	        		obj.put("status", 	voucherMain.getFdBookkeepingStatus());//流程状态
	        		array.add(obj);
				}
				JSONObject json = new JSONObject();
				json.put("array", array);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json.toString());
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public ActionForward bookkeepingFailed(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-loanAmount", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Calendar calendar = Calendar.getInstance();
			//当前年份
			int year = calendar.get(Calendar.YEAR); 

			// 记账失败
			Long failedCnt =getFsscVoucherPortletService().getApprovedCount(year+"",FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
			// 记账总数
			Long totalCnt =getFsscVoucherPortletService().getApprovedCount(year+"",null);

			JSONObject json = getFsscVoucherPortletService().findApprovedCount(year+"",FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);

			JSONObject obj = new JSONObject();
			obj.put("count", failedCnt);
			obj.put("values", json);
			

            //计算结果
			String res = null;
			if(failedCnt != 0 && totalCnt != 0) {
				res = String.format("%.2f", (float) failedCnt / (float) totalCnt * 100);
			}else {
				res="0";
			}
			obj.put("fail", res + "%");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		return null;
	}

	/**
	 * 获取待处理单据总量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getVapprovePortletData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();

			try {

				Calendar ca=Calendar.getInstance();
				Integer preYear=ca.get(Calendar.YEAR)-1;
				Integer month=ca.get(Calendar.MONTH)+1;
				Integer nextMonth=month+1;
				Integer day=ca.get(Calendar.DAY_OF_MONTH)+1;

				//今年我处理总单据量
				Integer thisYearApprove = getFsscCommonPortletService().getListApproveCount(request,DateUtil.getBeginDayOfYear(),ca.getTime());
				//去年待处理总单据量
				Integer lastYearApprove = getFsscCommonPortletService().getListApproved(request,
						DateUtil.convertStringToDate(preYear+"-01-01"),
						DateUtil.convertStringToDate(preYear+"-"+(month<10?("0"+month):String.valueOf(month))+"-"+(day<10?("0"+day):String.valueOf(day))));
				Integer cApproveByMonth=getFsscCommonPortletService().getListApproved(request, DateUtil.getBeginDayOfMonth(),DateUtil.getEndDayOfMonth());

				//待处理总单据量
				json.put("vApprove", thisYearApprove);
				//同年比
				if(thisYearApprove<lastYearApprove){
					json.put("AvgType","reduction");
					if(lastYearApprove!=null&&lastYearApprove!=0) {
						BigDecimal moreThanApprove = new BigDecimal(lastYearApprove-thisYearApprove);
						BigDecimal divisorApperove = new BigDecimal(lastYearApprove);
						json.put("vApproveAvg", moreThanApprove.divide(divisorApperove, 2, BigDecimal.ROUND_UP).doubleValue()*100);
					}else{
						json.put("vApproveAvg",100.00);
					}
				}
				if(thisYearApprove>lastYearApprove){
					json.put("AvgType","increase");
					if(lastYearApprove!=null&&lastYearApprove!=0) {
						BigDecimal moreThanApprove = new BigDecimal(thisYearApprove - lastYearApprove);
						BigDecimal divisorApperove = new BigDecimal(lastYearApprove);
						json.put("vApproveAvg", moreThanApprove.divide(divisorApperove, 2, BigDecimal.ROUND_UP).doubleValue()*100);
					}else{
						json.put("vApproveAvg",100.00);
					}
				}
				if(thisYearApprove == lastYearApprove){
					json.put("vApproveAvg", "持平");
				}
				//当月待处理
				json.put("vApproveByMonth",cApproveByMonth);
			} catch(Exception e){
				json.put("result", false);
				json.put("massege", "failure");
				e.printStackTrace();
			}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 获取已处理单据总量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getVapprovedPortletData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String>x=new ArrayList<String>();
		List<Long>y=new ArrayList<Long>();
		Calendar cal = Calendar.getInstance();
		Integer year=cal.get(Calendar.YEAR);
		try {
			//积累处理总单据量
			Long dInteger = getFsscVoucherPortletService().getApprovedModelCount(DateUtil.getBeginDayOfYear(),new Date());
			json.put("vApproved",dInteger);
			//日均处理
			Long recentCount = getFsscVoucherPortletService().getApprovedModelCount(DateUtil.getNextDay(cal.getTime(),-30),cal.getTime());
			if(recentCount!=null) {
				json.put("vApprovedDay", FsscNumberUtil.getDivide(recentCount.doubleValue(),30,0));
			}else {
				json.put("vApprovedDay", 0);
			}

			//对应月份的我已审
			for(int i=1;i<13;i++){
				Long num=
						getFsscVoucherPortletService().getApprovedModelCount(getFsscCommonPortletService().getMonehStartOrEnd(DateUtil.convertStringToDate(year+"-"+(i<10?("0"+i):String.valueOf(i))+"-01"),"")
								,getFsscCommonPortletService().getMonehStartOrEnd(DateUtil.convertStringToDate(year+"-"+(i<10?("0"+i):String.valueOf(i))+"-01"),"last"));
				if(num>0){
					y.add(num);
					x.add(String.valueOf(i));
				}
			}
			map.put("aX",x);
			map.put("aY", y);
			json.put("vEcharts", map);
		} catch (Exception e) {
			json.put("result", false);
			json.put("massege", "failure");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 记账处理完成率
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBookingSuccessData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		try {
			json=getFsscVoucherPortletService().getBookingSuccessData(request);
		} catch (Exception e) {
			json.put("result", false);
			json.put("massege", "failure");
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
	
	/**
	 * 根据状态、年份、登录人ID、记账公司 获取记账信息
	 * @param request
	 * @param status
	 * @param year
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	private int getVoucher(HttpServletRequest request,String status,int year,List<String> ids) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(1) from fssc_voucher_main where fd_bookkeeping_status = '"+status+"' and fd_accounting_year='"+year+"'");
		builder.append(" and ").append(HQLUtil.buildLogicIN("fd_company_id", ids));
		Query query = getServiceImp(request).getBaseDao().getHibernateSession().createSQLQuery(builder.toString());
		Number cnt = (Number) query.uniqueResult();
		return  null != cnt ? cnt.intValue() : 0;
	}
	
	/**
	 * 获取当前登录所在记账公司
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private List<String> getCompanyByUserId(HttpServletRequest request,String userId) throws Exception{
		List<EopBasedataCompany> companyList = getCompanyService(request).findCompanyByUserId(userId);
        if(ArrayUtil.isEmpty(companyList)) {
        	return null;
        }
        List<String> ids = new ArrayList<>();
        for (EopBasedataCompany company : companyList) {
			ids.add(company.getFdId());
		}
        return ids;
	}


}
