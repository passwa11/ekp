package com.landray.kmss.fssc.expense.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.fssc.common.util.DataBaseUtil;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.constant.FsscExpenseConstant;
import com.landray.kmss.fssc.expense.service.IFsscExpenseDetailService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.fssc.expense.service.IFsscExpensePortalService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FsscExpensePortalService implements IXMLDataBean,IFsscExpensePortalService{
	 protected IFsscExpenseMainService fsscExpenseMainService;
	 protected IFsscExpenseDetailService fsscExpenseDetailService;
	 
	public void setFsscExpenseMainService(IFsscExpenseMainService fsscExpenseMainService) {
		this.fsscExpenseMainService = fsscExpenseMainService;
	}

	public void setFsscExpenseDetailService(IFsscExpenseDetailService fsscExpenseDetailService) {
		this.fsscExpenseDetailService = fsscExpenseDetailService;
	}
	
	private ISysOrgCoreService sysOrgCoreService;
	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}
	
	private IEopBasedataCostCenterService eopBasedataCostCenterService;
	public void setEopBasedataCostCenterService(IEopBasedataCostCenterService eopBasedataCostCenterService) {
		this.eopBasedataCostCenterService = eopBasedataCostCenterService;
	}
	private ISysOrgElementService sysOrgElementService;
	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList=new ArrayList();
		String fdType = requestInfo.getParameter("fdType");
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		if(StringUtil.isNotNull(fdType)&&"checkSubject".equals(fdType)) {
			String sql = "SELECT " + 
					"	item.fd_id as fd_id,item.fd_name as fd_name,SUM(detail.fd_apply_money) AS fd_apply_money " + 
					"FROM " + 
					"	fssc_expense_detail detail " + 
					"LEFT JOIN eop_basedata_expense_item item ON item.fd_id = detail.fd_expense_item_id " + 
					"LEFT JOIN fssc_expense_main main ON main.fd_id = detail.doc_main_id " + 
					"WHERE " + 
					"	main.doc_status IN ('30', '20') AND detail.fd_company_id = '"+fdCompanyId+"' " + 
					"GROUP BY " + 
					"			item.fd_id,item.fd_name";
			Query query = fsscExpenseDetailService.getBaseDao().getHibernateSession().createNativeQuery(sql);
			List<Object[]> list = query.list();
			Object[] nameObjects = new Object[list.size()];
			Object[] moneyObjects = new Object[list.size()];
			for (int i = 0;i<list.size();i++) {
				Object[] object = list.get(i);
				nameObjects[i]=(null!=object?object[1]:null);
				moneyObjects[i]=(null!=object?object[2]:null);
			}
			JSONObject json = new JSONObject();
			json.put("nameObjects", nameObjects);
			json.put("moneyObjects", moneyObjects);
			Map map = new HashMap();
			map.put("keyValue", json);
			rtnList.add(map);
		}else if(StringUtil.isNotNull(fdType)&&"checkCost".equals(fdType)) {
			String sql = "SELECT fssc_expense_portal.fd_cost_center_id,fssc_expense_portal.fd_name ,fssc_expense_portal.fd_apply_money from (SELECT  " + 
					"	detail.fd_cost_center_id as fd_cost_center_id, " + 
					"	center.fd_name as fd_name, " + 
					"	SUM(detail.fd_apply_money) as fd_apply_money " + 
					"FROM " + 
					"	fssc_expense_detail detail " + 
					"LEFT JOIN fssc_expense_main main ON main.fd_id = detail.doc_main_id " + 
					"LEFT JOIN eop_basedata_cost_center center ON center.fd_id = detail.fd_cost_center_id " + 
					"WHERE " + 
					"	main.doc_status IN ('30', '20') and detail.fd_company_id = '"+fdCompanyId+"' " + 
					"GROUP BY " + 
					"	detail.fd_cost_center_id,center.fd_name) fssc_expense_portal " + 
					"ORDER BY fssc_expense_portal.fd_apply_money desc";
			Query query = fsscExpenseDetailService.getBaseDao().getHibernateSession().createNativeQuery(sql);
			List<Object[]> list = query.list();
			Object[] nameObjects = new Object[list.size()];
			Object[] moneyObjects = new Object[list.size()];
			for (int i = 0;i<list.size();i++) {
				Object[] object = list.get(i);
				nameObjects[i]=(null!=object?object[1]:null);
				moneyObjects[i]=(null!=object?object[2]:null);
			}
			JSONObject json = new JSONObject();
			json.put("nameObjects", nameObjects);
			json.put("moneyObjects", moneyObjects);
			Map map = new HashMap();
			map.put("keyValue", json);
			rtnList.add(map);
		}else if(StringUtil.isNotNull(fdType)&&"checkCostList".equals(fdType)) {
			String sql = "SELECT fssc_expense_portal.fd_cost_center_id,fssc_expense_portal.fd_name ,fssc_expense_portal.fd_apply_money from (SELECT  " + 
					"	detail.fd_cost_center_id as fd_cost_center_id, " + 
					"	center.fd_name as fd_name," + 
					"	SUM(detail.fd_apply_money) as fd_apply_money " + 
					"FROM " + 
					"	fssc_expense_detail detail " + 
					"LEFT JOIN fssc_expense_main main ON main.fd_id = detail.doc_main_id " + 
					"LEFT JOIN eop_basedata_cost_center center ON center.fd_id = detail.fd_cost_center_id " + 
					"WHERE " + 
					"	main.doc_status IN ('30', '20') and detail.fd_company_id = '"+fdCompanyId+"' " + 
					"GROUP BY " + 
					"	detail.fd_cost_center_id,center.fd_name ) fssc_expense_portal " + 
					"ORDER BY fssc_expense_portal.fd_apply_money desc";
			Query query = fsscExpenseDetailService.getBaseDao().getHibernateSession().createNativeQuery(sql);
			List<Object[]> list = query.list();
			Object[] nameObjects = new Object[list.size()];
			Object[] moneyObjects = new Object[list.size()];
			JSONArray jsonArray = new JSONArray();
			for (int i = 0;i<list.size();i++) { 
				if(i==10) {
					break;
				}
				JSONObject json = new JSONObject();
				Object[] object = list.get(i);
				json.put("fdOrder", i+1);
				json.put("fdId", (null!=object?object[0]:null));
				json.put("fdName", (null!=object?object[1]:null));
				json.put("fdMoney", (null!=object?NumberUtil.roundDecimal(object[2], "#,#00.00"):null));
				jsonArray.add(json);
			}
			JSONObject jsonq = new JSONObject();
			jsonq.put("keyValue", jsonArray);
			Map map = new HashMap();
			map.put("keyValue", jsonq);
			rtnList.add(map);
		}else if(StringUtil.isNotNull(fdType)&&"checkSubjectList".equals(fdType)) {
			String sql = "SELECT " + 
					"	item.fd_id as fd_id,item.fd_name as fd_name,SUM(detail.fd_apply_money) AS fd_apply_money " + 
					"FROM " + 
					"	fssc_expense_detail detail " + 
					"LEFT JOIN eop_basedata_expense_item item ON item.fd_id = detail.fd_expense_item_id " + 
					"LEFT JOIN fssc_expense_main main ON main.fd_id = detail.doc_main_id " + 
					"WHERE " + 
					"	main.doc_status IN ('30', '20') AND detail.fd_company_id = '"+fdCompanyId+"' " + 
					"GROUP BY " + 
					"			item.fd_id,item.fd_name";
			Query query = fsscExpenseDetailService.getBaseDao().getHibernateSession().createNativeQuery(sql);
			List<Object[]> list = query.list();
			Object[] nameObjects = new Object[list.size()];
			Object[] moneyObjects = new Object[list.size()];
			JSONArray jsonArray = new JSONArray();
			for (int i = 0;i<list.size();i++) {
				if(i==10) {
					break;
				}
				JSONObject json = new JSONObject();
				Object[] object = list.get(i);
				json.put("fdOrder", i+1);
				json.put("fdId", (null!=object?object[0]:null));
				json.put("fdName", (null!=object?object[1]:null));
				json.put("fdMoney", (null!=object?NumberUtil.roundDecimal(object[2], "#,#00.00"):null));
				jsonArray.add(json);
			}
			JSONObject jsonq = new JSONObject();
			jsonq.put("keyValue", jsonArray);
			Map map = new HashMap();
			map.put("keyValue", jsonq);
			rtnList.add(map);
		}
		return rtnList;
	}

	/**
	 * 已报销
	 */
	@Override
	public JSONObject alreadyExpense(String userId) throws Exception {
		//报销总额
		double fdApprovedApplyMoney = getExpenseByUserId(userId, getCurYear(),FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
		
		//获取今年报销金额
		double thisYear = getExpenseByUserId(userId,getCurYear(),FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
		
		//获取去年报销金额
		double lastYear = getExpenseByUserId(userId, getLastYear(),FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
		
        JSONObject  json =new JSONObject();
        json.put("fdApprovedApplyMoney", new BigDecimal(fdApprovedApplyMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        json.put("lastYearMoney", new BigDecimal(lastYear).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		json.put("thisYearMoney",new BigDecimal(thisYear).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		return json;
	}
	
	/**
	 * 报销中
	 */
	@Override
	public JSONObject expsenseIng(String userId) throws Exception {
		//报销中总额
		double fdApprovedApplyMoney = getExpenseByUserId(userId, getCurYear(),FsscExpenseConstant.FSSC_DOC_STATUS_APPROVE);
		
		//每月份报销
		JSONObject expenseIng = getExpenseTotal(getCurYear(), null,userId,FsscExpenseConstant.FSSC_DOC_STATUS_APPROVE);
        JSONObject obj  =new JSONObject();
        obj.put("fdApprovedApplyMoney", new BigDecimal(fdApprovedApplyMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        obj.put("expenseIng", expenseIng);
		return obj;
	}

	/**
	 * 获取部门下人员userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> getUserId() throws Exception{
		if(UserUtil.getKMSSUser().isAdmin()){
			return null;
		}
		// 获取当前部门下人员ID
		List orgList = new ArrayList<>();
		orgList.add(UserUtil.getKMSSUser().getDeptId());
		List<SysOrgPerson> pseronIds = sysOrgCoreService.expandToPerson(orgList);
		List<String> ids = new ArrayList<>();
		for (SysOrgPerson sysOrgPerson : pseronIds) {
			ids.add(sysOrgPerson.getFdId());
		}
		return ids;
	}
	
	/**
	 * 获取当年年份
	 * @return
	 */
	private Integer getCurYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取上一年年份
	 * @return
	 */
	private Integer getLastYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		Date y = calendar.getTime();
		String last = new SimpleDateFormat("yyyy").format(y);
		return Integer.parseInt(last);
	}


	/**
	 * 获取部门借款金额的统计数据
	 * @param year
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public JSONObject getTotalLoan(int year,  String deptId) throws Exception{
		SysOrgElement sysOrgElement = (SysOrgElement) sysOrgElementService.findByPrimaryKey(deptId);
		//由于转移会出现多次，先查询借款对应最新的转移记录，否则转移金额会翻倍
		StringBuilder transferSql=new StringBuilder();
		transferSql.append("select transfer.fd_id from fssc_loan_transfer transfer right join( ");
		transferSql.append(" select fd_loan_main_id,max(doc_create_time) doc_create_time from fssc_loan_transfer where doc_status<>'10' ");
		transferSql.append(" group by fd_loan_main_id) lastest on (lastest.doc_create_time=transfer.doc_create_time and transfer.fd_loan_main_id=lastest.fd_loan_main_id)");
		List<String> lastestIdList=fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(transferSql.toString()).list();
		String driveName = DataBaseUtil.getDataBaseType();
		//查询金额的sql
		StringBuffer sql = new StringBuffer();
		sql.append(" select sum(case when detail.fd_type = '1' and detail.fd_model_name = 'com.landray.kmss.fssc.loan.model.FsscLoanMain' then detail.fd_money else 0 End) as fdMoneyOne, ");
		sql.append(" sum(case when detail.fd_type = '2' and detail.fd_model_name != 'com.landray.kmss.fssc.loan.model.FsscLoanTransfer' then detail.fd_money else 0 End) as fdMoneyTwo, ");
		sql.append(" sum(case when detail.fd_type = '3' then detail.fd_money else 0 End) as fdMoneyThree, ");
		sql.append(" sum(case when detail.fd_type = '1' and detail.fd_model_name = 'com.landray.kmss.fssc.loan.model.FsscLoanTransfer' and detail.fd_multiplier = '-1' ");
		if(!ArrayUtil.isEmpty(lastestIdList)){
			sql.append(" and "+HQLUtil.buildLogicIN("detail.fd_model_id", lastestIdList));
		}
		sql.append(" then detail.fd_money else 0 End) as fdMoneyFour ,");
		sql.append(" sum(case when detail.fd_type = '1' and detail.fd_model_name = 'com.landray.kmss.fssc.loan.model.FsscLoanTransfer' and detail.fd_multiplier = '1' ");
		if(!ArrayUtil.isEmpty(lastestIdList)){
			sql.append(" and "+HQLUtil.buildLogicIN("detail.fd_model_id", lastestIdList));
		}
		sql.append(" then detail.fd_money else 0 End) as fdMoneyFive ,");
		if ("oracle".equals(driveName)) {
			sql.append( "  TO_CHAR(detail.doc_alter_time,'MM') as month_ ");
		}else if("mysql".equals(driveName)){
			sql.append( "  MONTH(detail.doc_alter_time) as month_ ");
		}else if("sqlserver".equals(driveName)){
			sql.append( "  DATEPART(mm,detail.doc_alter_time) as month_ ");
		}
		sql.append(" from fssc_loan_execute_detail  detail join sys_org_element element on   element.fd_id=detail.fd_person_id  ");
		sql.append(" where 1=1    ");
		if(!UserUtil.getKMSSUser().isAdmin()) {
			sql.append(" and (element.fd_hierarchy_id LIKE  '" + sysOrgElement.getFdHierarchyId() + "%') ");
		}
		if ("oracle".equals(driveName)) {
			sql.append( " and  TO_CHAR(detail.doc_alter_time,'yyyy') = '" + year + "'  group by  TO_CHAR(detail.doc_alter_time,'MM')");
		}else if("mysql".equals(driveName)){
			sql.append( " and  YEAR(detail.doc_alter_time) = '" + year + "' group by  MONTH(detail.doc_alter_time) ");
		}else if("sqlserver".equals(driveName)){
			sql.append( " and  DATEPART(yy,detail.doc_alter_time) = '" + year + "' group by  DATEPART(mm,detail.doc_alter_time) ");
		}
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		JSONObject  loanJson = new JSONObject();
		JSONObject repIngJson = new JSONObject();
		JSONObject  notRepJson=new JSONObject();
		JSONObject alreadyJson=new JSONObject();
		List<String>  monthList=new ArrayList<String>();
		List<Object[]> list = query.list();
		if (!list.isEmpty() && null != list.get(0)){
			for(Object[] objs : list){
				 repIngJson = new JSONObject();
				  notRepJson=new JSONObject();
				 alreadyJson=new JSONObject();
				double fdMoney1 =0d;
				double fdMoney2 = 0d;
				double fdMoney3 =0d;
				double fdMoney4 = 0d;
				double fdMoney5 = 0d;
				String month=null;
				if(objs[0]!=null) {
					fdMoney1 = FsscNumberUtil.doubleToUp(objs[0]+"");//借款金额
				}
				if(objs[1]!=null) {
					fdMoney2 = FsscNumberUtil.doubleToUp(objs[1]+"");//借款中的金额
				}
				if(objs[2]!=null) {
					fdMoney3 = FsscNumberUtil.doubleToUp(objs[2]+"");//已还款金额（包括还款+报销抵扣）
				}
				if(objs[3]!=null) {
					fdMoney4 = FsscNumberUtil.doubleToUp(objs[3]+"");//转移出去的金额
				}
				if(objs[4]!=null) {
					fdMoney5 = FsscNumberUtil.doubleToUp(objs[4]+"");//转移进来的金额
				}
				if(objs[5]!=null) {
					month=objs[5]+"";//月份
				}
				double fdLoanMoney=FsscNumberUtil.getAddition(FsscNumberUtil.getSubtraction(fdMoney1, fdMoney4), fdMoney5);//实际借款金额=借款金额-转移出去的金额+转移进来的金额
				repIngJson.put(getEnglishMonth(month),fdMoney2);//还款中金额（包括报销抵扣，还款的审批中的金额）
				notRepJson.put(getEnglishMonth(month),FsscNumberUtil.getSubtraction(fdLoanMoney, fdMoney3));//未还借款=实际借款金额-已还款金额（包括报销抵扣，还款）
				alreadyJson.put(getEnglishMonth(month),fdMoney3);//已还款金额（包括报销抵扣，还款）
				if(StringUtil.isNotNull(month)){
					monthList.add(month);
				}
			}
		}
		for(int i=0;i<12;i++){
			if(!(monthList.contains((i+1)+"")||monthList.contains("0"+(i+1)))){
				repIngJson.put(getEnglishMonth((i+1)+""),0);
				notRepJson.put(getEnglishMonth((i+1)+""),0);
				alreadyJson.put(getEnglishMonth((i+1)+""),0);
			}
		}
		JSONObject repIngJsonValues = new JSONObject();

		JSONObject  notRepJsonValues=new JSONObject();
		JSONObject alreadyJsonValues=new JSONObject();
		repIngJsonValues.put("values",repIngJson);
		notRepJsonValues.put("values",notRepJson);
		alreadyJsonValues.put("values",alreadyJson);
		loanJson.put("repIng", repIngJsonValues);// 还款中
		loanJson.put("notRep", notRepJsonValues);// 未还借款
		loanJson.put("already", alreadyJsonValues);// 已还借款
		return loanJson;
	}


	public String getEnglishMonth(String month){
		if("01".equals(month)|| "1".equals(month)) {
			return "one";
		}
		if("02".equals(month)|| "2".equals(month)) {
			return "two";
		}
		if("03".equals(month)|| "3".equals(month)) {
			return "three";
		}
		if("04".equals(month)|| "4".equals(month)) {
			return "four";
		}
		if("05".equals(month)|| "5".equals(month)) {
			return "five";
		}
		if("06".equals(month)|| "6".equals(month)) {
			return "six";
		}
		if("07".equals(month)|| "7".equals(month)) {
			return "seven";
		}
		if("08".equals(month)|| "8".equals(month)) {
			return "eight";
		}
		if("09".equals(month)|| "9".equals(month)) {
			return "nine";
		}
		if("10".equals(month)) {
			return "ten";
		}
		if("11".equals(month)) {
			return "eleven";
		}
		if("12".equals(month)) {
			return "twelve";
		}
		return "";
	}


	/**
	 * 获取借款金额
	 * 
	 * @param year
	 * @param ids
	 * @throws Exception
	 */
	private double getTotalLoanAmount(int year, String ids) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fd_money) from fssc_loan_execute_detail  detail left join fssc_loan_main main on detail.fd_loan_id = main.fd_id ");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( " where  TO_CHAR(doc_alter_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append( " where  YEAR(doc_alter_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( " where  DATEPART(yy,doc_alter_time) = '" + year + "' ");
		}
		sql.append("AND fd_type = 1  AND fd_multiplier = 1  AND fd_model_name = 'com.landray.kmss.fssc.loan.model.FsscLoanMain' ")
				.append(" AND  main.fd_loan_dept_id ='"+ids+"'");
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		Number cnt = (Number) query.uniqueResult();
		return cnt != null ? cnt.doubleValue() : 0;
	}


	public double getLoanTotalAmount(int year, String deptId) throws Exception{
		SysOrgElement sysOrgElement = (SysOrgElement)  getSysOrgElementService().findByPrimaryKey(deptId);
		//由于转移会出现多次，先查询借款对应最新的转移记录，否则转移金额会翻倍
		StringBuilder transferSql=new StringBuilder();
		transferSql.append("select transfer.fd_id from fssc_loan_transfer transfer right join( ");
		transferSql.append(" select fd_loan_main_id,max(doc_create_time) doc_create_time from fssc_loan_transfer where doc_status<>'10' ");
		transferSql.append(" group by fd_loan_main_id) lastest on (lastest.doc_create_time=transfer.doc_create_time and transfer.fd_loan_main_id=lastest.fd_loan_main_id)");
		List<String> lastestIdList=fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(transferSql.toString()).list();
		//查询金额的sql
		StringBuffer sql = new StringBuffer();
		sql.append(" select sum(case when detail.fd_type = '1' and detail.fd_model_name = 'com.landray.kmss.fssc.loan.model.FsscLoanMain' then detail.fd_money else 0 End) as fdMoneyOne, ");
		sql.append(" sum(case when detail.fd_type = '1' and detail.fd_model_name = 'com.landray.kmss.fssc.loan.model.FsscLoanTransfer' and detail.fd_multiplier = '-1' ");
		if(!ArrayUtil.isEmpty(lastestIdList)){
			sql.append(" and "+HQLUtil.buildLogicIN("detail.fd_model_id", lastestIdList));
		}
		sql.append("then detail.fd_money else 0 End) as fdMoneyTwo ,");
		sql.append(" sum(case when detail.fd_type = '1' and detail.fd_model_name = 'com.landray.kmss.fssc.loan.model.FsscLoanTransfer' and detail.fd_multiplier = '1' ");
		if(!ArrayUtil.isEmpty(lastestIdList)){
			sql.append(" and "+HQLUtil.buildLogicIN("detail.fd_model_id", lastestIdList));
		}
		sql.append("then detail.fd_money else 0 End) as fdMoneyThree ");
		sql.append(" from fssc_loan_execute_detail  detail join sys_org_element element on   element.fd_id=detail.fd_person_id  ");
		sql.append(" where 1=1    ");
		if(!UserUtil.getKMSSUser().isAdmin()) {
			sql.append(" and (element.fd_hierarchy_id LIKE  '" + sysOrgElement.getFdHierarchyId() + "%') ");
		}
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( " and  TO_CHAR(detail.doc_alter_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append( " and  YEAR(detail.doc_alter_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( " and  DATEPART(yy,detail.doc_alter_time) = '" + year + "' ");
		}
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		List<Object[]> list = query.list();
		if (!list.isEmpty() && null != list.get(0)){
			for(Object[] objs : list){
				double fdMoney1 =0d;
				double fdMoney2 = 0d;
				double fdMoney3 = 0d;
				if(objs[0]!=null) {
					fdMoney1 = FsscNumberUtil.doubleToUp(objs[0]+"");
				}
				if(objs[1]!=null) {
					fdMoney2 = FsscNumberUtil.doubleToUp(objs[1]+"");
				}
				if(objs[2]!=null) {
					fdMoney3 = FsscNumberUtil.doubleToUp(objs[2]+"");
				}
				return  FsscNumberUtil.getAddition(FsscNumberUtil.getSubtraction(fdMoney1, fdMoney2), fdMoney3);//借款金额=借款金额-转移出去的金额+转移进来的金额
			}
		}else {
			return 0;
		}
        return 0;
	}



	/**
	 * 获取借款转移金额
	 *
	 * @param year
	 * @param ids
	 * @throws Exception
	 */
	private double getTotalTransAmount(int year, String ids) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fd_money) from fssc_loan_execute_detail  detail left join fssc_loan_transfer trans on detail.fd_loan_id = trans.fd_loan_main_id");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( " where  TO_CHAR(doc_alter_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append( " where  YEAR(doc_alter_time) = '" + year + "'");
		}else if("sqlserver".equals(driveName)){
			sql.append( " where  DATEPART(yy,doc_alter_time) = '" + year + "' ");
		}
		sql.append("  AND fd_type = 1 AND fd_model_name = 'com.landray.kmss.fssc.loan.model.FsscLoanTransfer' AND fd_multiplier = '-1'").append("AND trans.fd_receive_dept_id ='"+ids+"'");
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		Number cnt = (Number) query.uniqueResult();
		return cnt != null ? cnt.doubleValue() : 0;
	}
	
	/**
	 * 获取报销台账核准金额
	 * @param year
	 * @param ids
	 * @return double
	 * @throws Exception
	 */
	private double getTotalExpenseAmount(int year,List<String> ids,String status) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select SUM(detail.fd_standard_money) from fssc_expense_detail detail  "
				+ " left join fssc_expense_main main  on detail.doc_main_id = main.fd_id");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( " where  TO_CHAR(main.doc_create_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append( " where  YEAR(main.doc_create_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( " where  DATEPART(yy,main.doc_create_time) = '" + year + "' ");
		}
		sql.append("  AND main.doc_status='"+status+"'").append("AND ").append(HQLUtil.buildLogicIN("detail.fd_cost_center_id", ids));
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		Number cnt = (Number) query.uniqueResult();
		return cnt != null ? cnt.doubleValue() : 0;
	}

	
	/**
	 * 根据年份、状态、当前部门下人员获取报错台账金额
	 * @param year
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getExpenseTotal(int year,List<String> ids,String userId,String status) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append("select   sum(one) AS one,sum(two) AS two,sum(three) AS three,sum(four) AS four," 
				  + "sum(five) AS five,sum(six) AS six,sum(seven) AS seven,sum(eight) AS eight," 
				  + "sum(nine) AS nine,sum(ten) AS ten,sum(eleven) AS eleven,sum(twelve) AS twelve " 
				  + " from (   " );
		String driveName = DataBaseUtil.getDataBaseType();
		if(StringUtil.isNotNull(status)) {
			if ("oracle".equals(driveName)) {
				sql.append(" select   " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '01' then detail.fd_approved_standard_money else 0 end) as one,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '02' then detail.fd_approved_standard_money else 0 end) as two,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '03' then detail.fd_approved_standard_money else 0 end) as three, " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '04' then detail.fd_approved_standard_money else 0 end) as four,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '05' then detail.fd_approved_standard_money else 0 end) as five,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '06' then detail.fd_approved_standard_money else 0 end) as six,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '07' then detail.fd_approved_standard_money else 0 end) as seven,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '08' then detail.fd_approved_standard_money else 0 end) as eight,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '09' then detail.fd_approved_standard_money else 0 end) as nine,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '10' then detail.fd_approved_standard_money else 0 end) as ten,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '11' then detail.fd_approved_standard_money else 0 end) as eleven,  " +
						"	sum(case TO_CHAR(main.doc_create_time,'MM') when '12' then detail.fd_approved_standard_money else 0 end) as twelve  " +
						"	from  fssc_expense_detail detail " +
						"	left join fssc_expense_main main  on detail.doc_main_id = main.fd_id ");
				sql.append("	WHERE TO_CHAR(main.doc_create_time,'yyyy') = '" + year + "' " );
			}else if("mysql".equals(driveName)){
				sql.append(" select   " +
						"	sum(case month(main.doc_create_time) when '1' then detail.fd_approved_standard_money else 0 end) as one,  " +
						"	sum(case month(main.doc_create_time) when '2' then detail.fd_approved_standard_money else 0 end) as two,  " +
						"	sum(case month(main.doc_create_time) when '3' then detail.fd_approved_standard_money else 0 end) as three, " +
						"	sum(case month(main.doc_create_time) when '4' then detail.fd_approved_standard_money else 0 end) as four,  " +
						"	sum(case month(main.doc_create_time) when '5' then detail.fd_approved_standard_money else 0 end) as five,  " +
						"	sum(case month(main.doc_create_time) when '6' then detail.fd_approved_standard_money else 0 end) as six,  " +
						"	sum(case month(main.doc_create_time) when '7' then detail.fd_approved_standard_money else 0 end) as seven,  " +
						"	sum(case month(main.doc_create_time) when '8' then detail.fd_approved_standard_money else 0 end) as eight,  " +
						"	sum(case month(main.doc_create_time) when '9' then detail.fd_approved_standard_money else 0 end) as nine,  " +
						"	sum(case month(main.doc_create_time) when '10' then detail.fd_approved_standard_money else 0 end) as ten,  " +
						"	sum(case month(main.doc_create_time) when '11' then detail.fd_approved_standard_money else 0 end) as eleven,  " +
						"	sum(case month(main.doc_create_time) when '12' then detail.fd_approved_standard_money else 0 end) as twelve  " +
						"	from  fssc_expense_detail detail " +
						"	left join fssc_expense_main main  on detail.doc_main_id = main.fd_id ");
				sql.append("	WHERE YEAR(main.doc_create_time) = '" + year + "' " );
			}else if("sqlserver".equals(driveName)){
				sql.append(" select   " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '01' then detail.fd_approved_standard_money else 0 end) as one,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '02' then detail.fd_approved_standard_money else 0 end) as two,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '03' then detail.fd_approved_standard_money else 0 end) as three, " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '04' then detail.fd_approved_standard_money else 0 end) as four,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '05' then detail.fd_approved_standard_money else 0 end) as five,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '06' then detail.fd_approved_standard_money else 0 end) as six,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '07' then detail.fd_approved_standard_money else 0 end) as seven,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '08' then detail.fd_approved_standard_money else 0 end) as eight,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '09' then detail.fd_approved_standard_money else 0 end) as nine,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '10' then detail.fd_approved_standard_money else 0 end) as ten,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '11' then detail.fd_approved_standard_money else 0 end) as eleven,  " +
						"	sum(case DATEPART(mm,main.doc_create_time) when '12' then detail.fd_approved_standard_money else 0 end) as twelve  " +
						"	from  fssc_expense_detail detail " +
						"	left join fssc_expense_main main  on detail.doc_main_id = main.fd_id ");
				sql.append("	WHERE DATEPART(yy,main.doc_create_time) = '" + year + "' " );
			}
			sql.append("	AND main.doc_status = '" + status + "' ");
			if(StringUtil.isNotNull(userId)) {
				sql.append(" AND  main.doc_creator_id = '"+userId+"'");
			}
			if(null != ids) {
				sql.append(" AND ").append(HQLUtil.buildLogicIN("detail.fd_cost_center_id", ids));
			}
			sql.append("GROUP BY main.doc_create_time)t");
		}else {
			if ("oracle".equals(driveName)) {
				sql.append("select   " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '01' then sum(invoice.fd_jshj) else 0 end as one,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '02' then sum(invoice.fd_jshj) else 0 end as two,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '03' then sum(invoice.fd_jshj) else 0 end as three,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '04' then sum(invoice.fd_jshj) else 0 end as four,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '05' then sum(invoice.fd_jshj) else 0 end as five,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '06' then sum(invoice.fd_jshj) else 0 end as six,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '07' then sum(invoice.fd_jshj) else 0 end as seven,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '08' then sum(invoice.fd_jshj) else 0 end as eight,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '09' then sum(invoice.fd_jshj) else 0 end as nine,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '10' then sum(invoice.fd_jshj) else 0 end as ten,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '11' then sum(invoice.fd_jshj) else 0 end as eleven,  " +
						"	case TO_CHAR(invoice.doc_create_time,'MM') when '12' then sum(invoice.fd_jshj) else 0 end as twelve  " +
						"	from  fssc_ledger_invoice invoice ");
				sql.append("	WHERE TO_CHAR(invoice.doc_create_time,'yyyy') = '" + year + "' ");
			}else if("mysql".equals(driveName)){
				sql.append("select   " +
						"	case month(invoice.doc_create_time) when '1' then sum(invoice.fd_jshj) else 0 end as one,  " +
						"	case month(invoice.doc_create_time) when '2' then sum(invoice.fd_jshj) else 0 end as two,  " +
						"	case month(invoice.doc_create_time) when '3' then sum(invoice.fd_jshj) else 0 end as three,  " +
						"	case month(invoice.doc_create_time) when '4' then sum(invoice.fd_jshj) else 0 end as four,  " +
						"	case month(invoice.doc_create_time) when '5' then sum(invoice.fd_jshj) else 0 end as five,  " +
						"	case month(invoice.doc_create_time) when '6' then sum(invoice.fd_jshj) else 0 end as six,  " +
						"	case month(invoice.doc_create_time) when '7' then sum(invoice.fd_jshj) else 0 end as seven,  " +
						"	case month(invoice.doc_create_time) when '8' then sum(invoice.fd_jshj) else 0 end as eight,  " +
						"	case month(invoice.doc_create_time) when '9' then sum(invoice.fd_jshj) else 0 end as nine,  " +
						"	case month(invoice.doc_create_time) when '10' then sum(invoice.fd_jshj) else 0 end as ten,  " +
						"	case month(invoice.doc_create_time) when '11' then sum(invoice.fd_jshj) else 0 end as eleven,  " +
						"	case month(invoice.doc_create_time) when '12' then sum(invoice.fd_jshj) else 0 end as twelve  " +
						"	from  fssc_ledger_invoice invoice ");
				sql.append("	WHERE YEAR(invoice.doc_create_time) = '" + year + "' ");
			}else if("sqlserver".equals(driveName)){
				sql.append("select   " +
						"	case DATEPART(mm,invoice.doc_create_time) when '01' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as one,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '02' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as two,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '03' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as three,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '04' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as four,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '05' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as five,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '06' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as six,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '07' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as seven,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '08' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as eight,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '09' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as nine,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '10' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as ten,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '11' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as eleven,  " +
						"	case DATEPART(mm,invoice.doc_create_time) when '12' then sum(CAST(invoice.fd_jshj AS decimal)) else 0 end as twelve  " +
						"	from  fssc_ledger_invoice invoice ");
				sql.append("	WHERE DATEPART(yy,invoice.doc_create_time) = '" + year + "' ");
			}
			sql.append("	AND (invoice.fd_use_status = '0' or invoice.fd_use_status is null)"	+
					"	AND (invoice.fd_is_available = '1' or invoice.fd_is_available is null)"	+
					"	AND invoice.fd_state = '0' ");
			if(!UserUtil.getKMSSUser().isAdmin()) {
				sql.append(" AND ");
				sql.append(HQLUtil.buildLogicIN("invoice.doc_creator_id", ids));
			}
			sql.append("GROUP BY invoice.doc_create_time)t");
		}
		List<Object[]> list = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).list();
		JSONObject json = null;
		for (int i = 0; i < list.size(); i++) {
			json = new JSONObject();
			Object[] object = list.get(i);
			json.put("one", (null != object[0] ? FsscNumberUtil.doubleToUp(object[0].toString()): 0));
			json.put("two", (null != object[1] ? FsscNumberUtil.doubleToUp(object[1].toString()) : 0));
			json.put("three", (null != object[2] ? FsscNumberUtil.doubleToUp(object[2].toString()) : 0));
			json.put("four", (null != object[3] ? FsscNumberUtil.doubleToUp(object[3].toString()) : 0));
			json.put("five", (null != object[4] ? FsscNumberUtil.doubleToUp(object[4].toString()) : 0));
			json.put("six", (null != object[5] ? FsscNumberUtil.doubleToUp(object[5].toString()) : 0));
			json.put("seven", (null != object[6] ? FsscNumberUtil.doubleToUp(object[6].toString()) : 0));
			json.put("eight", (null != object[7] ? FsscNumberUtil.doubleToUp(object[7].toString()) : 0));
			json.put("nine", (null != object[8] ? FsscNumberUtil.doubleToUp(object[8].toString()) : 0));
			json.put("ten", (null != object[9] ? FsscNumberUtil.doubleToUp(object[9].toString()) : 0));
			json.put("eleven", (null != object[10] ? FsscNumberUtil.doubleToUp(object[10].toString()) : 0));
			json.put("twelve", (null != object[11] ? FsscNumberUtil.doubleToUp(object[11].toString()): 0));
		}
		JSONObject obj = new JSONObject();
		obj.put("values", json);
		return obj;
	}
	
	/**
	 * 获取个人报销金额
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private double getExpenseByUserId(String userId,Integer year,String status) throws  Exception {
		// 获取去年报销金额
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(detail.fd_standard_money) from fssc_expense_detail  detail left join fssc_expense_main main on detail.doc_main_id = main.fd_id where  detail.fd_real_user_id='"
				+ userId + "'  and main.doc_status='"+status+"'");
		if(null != year) {
			String driveName = DataBaseUtil.getDataBaseType();
			if ("oracle".equals(driveName)) {
				sql.append( " and  TO_CHAR(main.doc_create_time,'yyyy') = '" + year + "' ");
			}else if("mysql".equals(driveName)){
				sql.append( " and  YEAR(main.doc_create_time) = '" + year + "' ");
			}else if("sqlserver".equals(driveName)){
				sql.append( " and  DATEPART(yy,main.doc_create_time) = '" + year + "' ");
			}
		}
		Query query = fsscExpenseDetailService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		Number cnt = (Number) query.uniqueResult();
		return cnt != null ? cnt.doubleValue():0;
	}

	/**
	 * 统计付款(预付款+付款)
	 * @param year
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	private double getPaymentTotalAmount(int year,List<String> ids,String status)throws  Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT SUM(fd_money*fd_exchange_rate) FROM fssc_payment_accounts   account " +
				" JOIN  fssc_payment_main main ON account.doc_main_id=main.fd_id WHERE  ");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( "   TO_CHAR(main.doc_create_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append( "   YEAR(main.doc_create_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( "   DATEPART(yy,main.doc_create_time) = '" + year + "' ");
		}
		sql.append(" and ").append(HQLUtil.buildLogicIN("fd_cost_center_id", ids));
		sql.append(" and main.doc_status = '"+status+"'");
		Query query = fsscExpenseDetailService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		Number cnt = (Number) query.uniqueResult();
		return cnt != null ? cnt.doubleValue():0;
	}


	/**
	 * 统计预付款
	 * @param year
	 * @param ids
	 * @param isPre
	 * @param status
	 * @return double
	 * @throws Exception
	 */
	private double getPaymentTotalByIsPre(int year,List<String> ids,int isPre,String status)throws  Exception{
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT " +
				" sum( CASE WHEN detail.fd_type = '1' THEN detail.fd_money ELSE 0 END ) AS fdMoneyOne," +
				" sum( CASE WHEN detail.fd_type = '2' THEN detail.fd_money ELSE 0 END ) AS fdMoneyTwo," +
				" sum( CASE WHEN detail.fd_type = '3' THEN detail.fd_money ELSE 0 END ) AS fdMoneyThree " +
				" FROM fssc_payment_offset_data detail " +
				" LEFT JOIN fssc_payment_main main ON detail.fd_main_id = main.fd_id" +
				" LEFT JOIN fssc_payment_category ca ON main.doc_template_id = ca.fd_id ");
		sql.append(" where ca.fd_is_pre = '"+ isPre + "'");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( " and  TO_CHAR(main.doc_create_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append( " and  YEAR(main.doc_create_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( " and  DATEPART(yy,main.doc_create_time) = '" + year + "' ");
		}
		sql.append(" and ").append(HQLUtil.buildLogicIN("fd_cost_center_id", ids)).append(" and main.doc_status = '"+status+"'");
		List<Object[]> list = fsscExpenseDetailService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).list();
		double money = 0;
		if (!list.isEmpty() && null != list.get(0)){
			for(Object[] objs : list){
				if(objs != null && objs[0] != null && objs[1] != null && objs[2] != null){
					double fdMoney1 = FsscNumberUtil.doubleToUp(objs[0]+"");//初始化金额
					double fdMoney2 = FsscNumberUtil.doubleToUp(objs[1]+"");//冲抵中金额
					double fdMoney3 = FsscNumberUtil.doubleToUp(objs[2]+""); //已冲抵金额
					money = FsscNumberUtil.getSubtraction(fdMoney1, FsscNumberUtil.getAddition(fdMoney2, fdMoney3));//未冲抵金额
				}
			}
		}
		return money;
	}
	
	/**
	 * 统计挂账付款金额
	 * @param year
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	private double getPaySuspendTotalAmount(int year,List<String> ids,String status)throws  Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select SUM(fd_amount_money*fd_exchange_rate) from  fssc_payment_suspend_main");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( " WHERE  TO_CHAR(doc_create_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append( " WHERE  YEAR(doc_create_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( " WHERE  DATEPART(yy,doc_create_time) = '" + year + "' ");
		}
		sql.append(" and ").append(HQLUtil.buildLogicIN("fd_cost_center_id", ids));
		sql.append("  and doc_status = '"+status+"'");
		Query query = fsscExpenseDetailService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		Number cnt = (Number) query.uniqueResult();
		return cnt != null ? cnt.doubleValue():0;
	}
	
	
	/**
	 * 统计退款金额
	 * @param year
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	private double getRefundTotalAmount(int year,List<String> ids,String status)throws  Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fd_this_refund_money*fd_exchange_rate)  from  fssc_payment_refund ");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( " WHERE  TO_CHAR(doc_create_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append( " WHERE  YEAR(doc_create_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( " WHERE  DATEPART(yy,doc_create_time) = '" + year + "' ");
		}
		sql.append(" and ").append(HQLUtil.buildLogicIN("fd_cost_center_id", ids));
		sql.append("  and doc_status = '"+status+"'");
		Query query = fsscExpenseDetailService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		Number cnt = (Number) query.uniqueResult();
		return cnt != null ? cnt.doubleValue():0;
	}
	
	
	/**
	 * 根据年份、成本中心负责人统计预付款金额(减掉退款后的金额)
	 * @param year
	 * @param ids
	 * @param isPre
	 * @return json
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getPrePaymentTotal(int year,List<String> ids,String status)throws  Exception {
		StringBuilder sql = new StringBuilder();

		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append(
					"SELECT " +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'01' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS ONE," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'02' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS  two," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'03' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS  three," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'04' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS four," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'05' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS five," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'06' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS six," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'07' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS seven," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'08' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS eight," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'09' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS nine," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'10' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS ten," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'11' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS eleven," +
					"SUM(CASE TO_CHAR(main.doc_create_time,'MM') WHEN'12' THEN (NVL((account.fd_money*account.fd_exchange_rate),0)-NVL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS twelve " +
					"FROM fssc_payment_accounts   account JOIN   fssc_payment_main main    ON account.doc_main_id=main.fd_id  LEFT JOIN fssc_payment_category ca ON main.doc_template_id = ca.fd_id " +
					"LEFT JOIN  fssc_payment_refund  refund  ON    main.fd_id=refund.fd_pre_payment_id WHERE   TO_CHAR(main.doc_create_time,'yyyy') = '" + year + "' " );
		}else if("mysql".equals(driveName)){

			sql.append(
					"SELECT " +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'1' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS ONE," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'2' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS  two," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'3' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS  three," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'4' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS four," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'5' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS five," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'6' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS six," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'7' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS seven," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'8' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS eight," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'9' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS nine," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'10' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS ten," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'11' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS eleven," +
					"SUM(CASE MONTH(main.doc_create_time) WHEN'12' THEN (IFNULL((account.fd_money*account.fd_exchange_rate),0)-IFNULL((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS twelve " +
					"FROM fssc_payment_accounts   account JOIN   fssc_payment_main main    ON account.doc_main_id=main.fd_id  LEFT JOIN fssc_payment_category ca ON main.doc_template_id = ca.fd_id " +
					"LEFT JOIN  fssc_payment_refund  refund  ON    main.fd_id=refund.fd_pre_payment_id WHERE YEAR(main.doc_create_time) = '" + year + "' " );
		}else if("sqlserver".equals(driveName)){
			sql.append(
					"SELECT " +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'01' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS ONE," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'02' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS  two," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'03' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS  three," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'04' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS four," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'05' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS five," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'06' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS six," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'07' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS seven," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'08' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS eight," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'09' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS nine," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'10' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS ten," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'11' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS eleven," +
					"SUM(CASE DATEPART(mm,main.doc_create_time)  WHEN'12' THEN (IsNull((account.fd_money*account.fd_exchange_rate),0)-IsNull((refund.fd_this_refund_money*refund.fd_exchange_rate),0)) ELSE 0 END) AS twelve " +
					"FROM fssc_payment_accounts   account JOIN   fssc_payment_main main    ON account.doc_main_id=main.fd_id  LEFT JOIN fssc_payment_category ca ON main.doc_template_id = ca.fd_id " +
					"LEFT JOIN  fssc_payment_refund  refund  ON    main.fd_id=refund.fd_pre_payment_id WHERE DATEPART(yy,main.doc_create_time) = '" + year + "' " );
		}
		sql.append(" and ca.fd_is_pre = '1'");
		sql.append(" and main.doc_status = '"+status+"'").append(" AND ").append(HQLUtil.buildLogicIN("main.fd_cost_center_id", ids));
		List<Object[]> list = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).list();
		JSONObject json = null;
		for (int i = 0; i < list.size(); i++) {
			json = new JSONObject();
			Object[] object = list.get(i);
			json.put("one", (null != object[0] ? FsscNumberUtil.doubleToUp(object[0].toString()): 0));
			json.put("two", (null != object[1] ? FsscNumberUtil.doubleToUp(object[1].toString()) : 0));
			json.put("three", (null != object[2] ? FsscNumberUtil.doubleToUp(object[2].toString()) : 0));
			json.put("four", (null != object[3] ? FsscNumberUtil.doubleToUp(object[3].toString()) : 0));
			json.put("five", (null != object[4] ? FsscNumberUtil.doubleToUp(object[4].toString()) : 0));
			json.put("six", (null != object[5] ? FsscNumberUtil.doubleToUp(object[5].toString()) : 0));
			json.put("seven", (null != object[6] ? FsscNumberUtil.doubleToUp(object[6].toString()) : 0));
			json.put("eight", (null != object[7] ? FsscNumberUtil.doubleToUp(object[7].toString()) : 0));
			json.put("nine", (null != object[8] ? FsscNumberUtil.doubleToUp(object[8].toString()) : 0));
			json.put("ten", (null != object[9] ? FsscNumberUtil.doubleToUp(object[9].toString()) : 0));
			json.put("eleven", (null != object[10] ? FsscNumberUtil.doubleToUp(object[10].toString()) : 0));
			json.put("twelve", (null != object[11] ? FsscNumberUtil.doubleToUp(object[11].toString()): 0));
		}
		JSONObject obj = new JSONObject();
		obj.put("values", json);
		return obj;
	}


	private JSONObject getPaymentTotal(int year,List<String> ids,int isPre,String status)throws  Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select   " +
				"sum( one )  one,sum( two ) AS two,sum( three ) AS three,sum( four ) AS four," +
				"sum( five ) AS five,sum( six ) AS six,sum( seven ) AS seven,sum( eight ) AS eight," +
				"sum( nine ) AS nine,sum( ten ) AS ten,sum( eleven ) AS eleven,sum( twelve ) AS twelve " +
				"				from (   ");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( "	select   " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '01' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as one,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '02' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as two,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '03' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as three,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '04' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as four,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '05' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as five,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '06' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as six,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '07' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as seven,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '08' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as eight,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '09' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as nine,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '10' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as ten,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '11' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as eleven,  " +
					"	case TO_CHAR(main.doc_create_time,'MM')	when '12' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as twelve  " +
					"	from fssc_payment_accounts   account JOIN   fssc_payment_main main " +
					"   on account.doc_main_id=main.fd_id  left join fssc_payment_category ca on main.doc_template_id = ca.fd_id ");
			sql.append( " WHERE  TO_CHAR(main.doc_create_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append("	select   " +
					"		case month(main.doc_create_time) when '1' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as one,  " +
					"		case month(main.doc_create_time) when '2' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as two,  " +
					"		case month(main.doc_create_time) when '3' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as three,  " +
					"		case month(main.doc_create_time) when '4' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as four,  " +
					"		case month(main.doc_create_time) when '5' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as five,  " +
					"		case month(main.doc_create_time) when '6' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as six,  " +
					"		case month(main.doc_create_time) when '7' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as seven,  " +
					"		case month(main.doc_create_time) when '8' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as eight,  " +
					"		case month(main.doc_create_time) when '9' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as nine,  " +
					"		case month(main.doc_create_time) when '10' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as ten,  " +
					"		case month(main.doc_create_time) when '11' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as eleven,  " +
					"		case month(main.doc_create_time) when '12' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as twelve  " +
					"	from fssc_payment_accounts   account JOIN   fssc_payment_main main " +
					"   on account.doc_main_id=main.fd_id  left join fssc_payment_category ca on main.doc_template_id = ca.fd_id ");
			sql.append( "		WHERE  YEAR(main.doc_create_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( "	select   " +
					"	case DATEPART(mm,main.doc_create_time) when '01' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as one,  " +
					"	case DATEPART(mm,main.doc_create_time) when '02' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as two,  " +
					"	case DATEPART(mm,main.doc_create_time) when '03' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as three,  " +
					"	case DATEPART(mm,main.doc_create_time) when '04' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as four,  " +
					"	case DATEPART(mm,main.doc_create_time) when '05' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as five,  " +
					"	case DATEPART(mm,main.doc_create_time) when '06' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as six,  " +
					"	case DATEPART(mm,main.doc_create_time) when '07' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as seven,  " +
					"	case DATEPART(mm,main.doc_create_time) when '08' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as eight,  " +
					"	case DATEPART(mm,main.doc_create_time) when '09' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as nine,  " +
					"	case DATEPART(mm,main.doc_create_time) when '10' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as ten,  " +
					"	case DATEPART(mm,main.doc_create_time) when '11' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as eleven,  " +
					"	case DATEPART(mm,main.doc_create_time) when '12' then sum(account.fd_money*account.fd_exchange_rate) else 0 end as twelve  " +
					"	from fssc_payment_accounts   account JOIN   fssc_payment_main main " +
					"   on account.doc_main_id=main.fd_id  left join fssc_payment_category ca on main.doc_template_id = ca.fd_id ");
			sql.append( " WHERE  DATEPART(yy,main.doc_create_time) = '" + year + "' ");
		}
		sql.append(" and ca.fd_is_pre = '"+ isPre + "'");
		sql.append(" and main.doc_status = '"+status+"'").append(" AND ").append(HQLUtil.buildLogicIN("main.fd_cost_center_id", ids)).append("GROUP BY main.doc_create_time)t");
		List<Object[]> list = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).list();
		JSONObject json = null;
		for (int i = 0; i < list.size(); i++) {
			json = new JSONObject();
			Object[] object = list.get(i);
			json.put("one", (null != object[0] ? FsscNumberUtil.doubleToUp(object[0].toString()): 0));
			json.put("two", (null != object[1] ? FsscNumberUtil.doubleToUp(object[1].toString()) : 0));
			json.put("three", (null != object[2] ? FsscNumberUtil.doubleToUp(object[2].toString()) : 0));
			json.put("four", (null != object[3] ? FsscNumberUtil.doubleToUp(object[3].toString()) : 0));
			json.put("five", (null != object[4] ? FsscNumberUtil.doubleToUp(object[4].toString()) : 0));
			json.put("six", (null != object[5] ? FsscNumberUtil.doubleToUp(object[5].toString()) : 0));
			json.put("seven", (null != object[6] ? FsscNumberUtil.doubleToUp(object[6].toString()) : 0));
			json.put("eight", (null != object[7] ? FsscNumberUtil.doubleToUp(object[7].toString()) : 0));
			json.put("nine", (null != object[8] ? FsscNumberUtil.doubleToUp(object[8].toString()) : 0));
			json.put("ten", (null != object[9] ? FsscNumberUtil.doubleToUp(object[9].toString()) : 0));
			json.put("eleven", (null != object[10] ? FsscNumberUtil.doubleToUp(object[10].toString()) : 0));
			json.put("twelve", (null != object[11] ? FsscNumberUtil.doubleToUp(object[11].toString()): 0));
		}
		JSONObject obj = new JSONObject();
		obj.put("values", json);
		return obj;
	}

	/**
	 * 根据年份、成本中心负责人统计挂账付款金额
	 * @param year
	 * @param ids
	 * @param status
	 * @return json
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getSuspendTotal(int year,List<String> ids,String status)throws  Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select   " +
				"sum( one )  one,sum( two ) AS two,sum( three ) AS three,sum( four ) AS four," +
				"sum( five ) AS five,sum( six ) AS six,sum( seven ) AS seven,sum( eight ) AS eight," +
				"sum( nine ) AS nine,sum( ten ) AS ten,sum( eleven ) AS eleven,sum( twelve ) AS twelve " +
				"				from (   ");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( "	select   " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '01' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as one,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '02' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as two,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '03' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as three,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '04' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as four,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '05' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as five,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '06' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as six,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '07' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as seven,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '08' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as eight,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '09' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as nine,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '10' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as ten,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '11' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as eleven,  " +
					"	case TO_CHAR(main.doc_create_time,'MM')	when '12' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as twelve  " +
					"	from  fssc_payment_suspend_main main  ");
			sql.append( " WHERE  TO_CHAR(main.doc_create_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append("	select   " +
					"		case month(main.doc_create_time) when '1' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as one,  " +
					"		case month(main.doc_create_time) when '2' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as two,  " +
					"		case month(main.doc_create_time) when '3' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as three,  " +
					"		case month(main.doc_create_time) when '4' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as four,  " +
					"		case month(main.doc_create_time) when '5' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as five,  " +
					"		case month(main.doc_create_time) when '6' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as six,  " +
					"		case month(main.doc_create_time) when '7' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as seven,  " +
					"		case month(main.doc_create_time) when '8' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as eight,  " +
					"		case month(main.doc_create_time) when '9' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as nine,  " +
					"		case month(main.doc_create_time) when '10' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as ten,  " +
					"		case month(main.doc_create_time) when '11' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as eleven,  " +
					"		case month(main.doc_create_time) when '12' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as twelve  " +
					"		from  fssc_payment_suspend_main main  ");
			sql.append( "		WHERE  YEAR(main.doc_create_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( "	select   " +
					"	case DATEPART(mm,main.doc_create_time) when '01' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as one,  " +
					"	case DATEPART(mm,main.doc_create_time) when '02' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as two,  " +
					"	case DATEPART(mm,main.doc_create_time) when '03' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as three,  " +
					"	case DATEPART(mm,main.doc_create_time) when '04' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as four,  " +
					"	case DATEPART(mm,main.doc_create_time) when '05' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as five,  " +
					"	case DATEPART(mm,main.doc_create_time) when '06' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as six,  " +
					"	case DATEPART(mm,main.doc_create_time) when '07' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as seven,  " +
					"	case DATEPART(mm,main.doc_create_time) when '08' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as eight,  " +
					"	case DATEPART(mm,main.doc_create_time) when '09' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as nine,  " +
					"	case DATEPART(mm,main.doc_create_time) when '10' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as ten,  " +
					"	case DATEPART(mm,main.doc_create_time) when '11' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as eleven,  " +
					"	case DATEPART(mm,main.doc_create_time) when '12' then sum(main.fd_amount_money*main.fd_exchange_rate) else 0 end as twelve  " +
					"	from  fssc_payment_suspend_main main  ");
			sql.append( " WHERE  DATEPART(yy,main.doc_create_time) = '" + year + "' ");
		}
		sql.append(" and main.doc_status = '"+status+"'").append(" AND ").append(HQLUtil.buildLogicIN("main.fd_cost_center_id", ids)).append("GROUP BY main.doc_create_time)t");
		List<Object[]> list = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).list();
		JSONObject json = null;
		for (int i = 0; i < list.size(); i++) {
			json = new JSONObject();
			Object[] object = list.get(i);
			json.put("one", (null != object[0] ? FsscNumberUtil.doubleToUp(object[0].toString()): 0));
			json.put("two", (null != object[1] ? FsscNumberUtil.doubleToUp(object[1].toString()) : 0));
			json.put("three", (null != object[2] ? FsscNumberUtil.doubleToUp(object[2].toString()) : 0));
			json.put("four", (null != object[3] ? FsscNumberUtil.doubleToUp(object[3].toString()) : 0));
			json.put("five", (null != object[4] ? FsscNumberUtil.doubleToUp(object[4].toString()) : 0));
			json.put("six", (null != object[5] ? FsscNumberUtil.doubleToUp(object[5].toString()) : 0));
			json.put("seven", (null != object[6] ? FsscNumberUtil.doubleToUp(object[6].toString()) : 0));
			json.put("eight", (null != object[7] ? FsscNumberUtil.doubleToUp(object[7].toString()) : 0));
			json.put("nine", (null != object[8] ? FsscNumberUtil.doubleToUp(object[8].toString()) : 0));
			json.put("ten", (null != object[9] ? FsscNumberUtil.doubleToUp(object[9].toString()) : 0));
			json.put("eleven", (null != object[10] ? FsscNumberUtil.doubleToUp(object[10].toString()) : 0));
			json.put("twelve", (null != object[11] ? FsscNumberUtil.doubleToUp(object[11].toString()): 0));
		}
		JSONObject obj = new JSONObject();
		obj.put("values", json);
		return obj;
	}
	
	
	/**
	 * 根据年份、成本中心负责人统计退款金额
	 * @param year
	 * @param ids
	 * @param status
	 * @return json
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getRefundTotal(int year,List<String> ids,String status)throws  Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("	select   " +
				"	sum( one )  one,sum( two ) AS two,sum( three ) AS three,sum( four ) AS four," +
				"	sum( five ) AS five,sum( six ) AS six,sum( seven ) AS seven,sum( eight ) AS eight," +
				"	sum( nine ) AS nine,sum( ten ) AS ten,sum( eleven ) AS eleven,sum( twelve ) AS twelve " +
				"	from (   ");
		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			sql.append( "	select   " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '01' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as one,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '02' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as two,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '03' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as three,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '04' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as four,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '05' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as five,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '06' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as six,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '07' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as seven,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '08' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as eight,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '09' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as nine,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '10' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as ten,  " +
					"	case TO_CHAR(main.doc_create_time,'MM') when '11' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as eleven,  " +
					"	case TO_CHAR(main.doc_create_time,'MM')	when '12' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as twelve  " +
					"	from  fssc_payment_refund main  ");
			sql.append( " WHERE  TO_CHAR(main.doc_create_time,'yyyy') = '" + year + "' ");
		}else if("mysql".equals(driveName)){
			sql.append("	select   " +
					"		case month(main.doc_create_time) when '1' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as one,  " +
					"		case month(main.doc_create_time) when '2' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as two,  " +
					"		case month(main.doc_create_time) when '3' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as three,  " +
					"		case month(main.doc_create_time) when '4' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as four,  " +
					"		case month(main.doc_create_time) when '5' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as five,  " +
					"		case month(main.doc_create_time) when '6' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as six,  " +
					"		case month(main.doc_create_time) when '7' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as seven,  " +
					"		case month(main.doc_create_time) when '8' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as eight,  " +
					"		case month(main.doc_create_time) when '9' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as nine,  " +
					"		case month(main.doc_create_time) when '10' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as ten,  " +
					"		case month(main.doc_create_time) when '11' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as eleven,  " +
					"		case month(main.doc_create_time) when '12' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as twelve  " +
					"		from  fssc_payment_refund main  ");
			sql.append( "		WHERE  YEAR(main.doc_create_time) = '" + year + "' ");
		}else if("sqlserver".equals(driveName)){
			sql.append( "	select   " +
					"	case DATEPART(mm,main.doc_create_time) when '01' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as one,  " +
					"	case DATEPART(mm,main.doc_create_time) when '02' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as two,  " +
					"	case DATEPART(mm,main.doc_create_time) when '03' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as three,  " +
					"	case DATEPART(mm,main.doc_create_time) when '04' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as four,  " +
					"	case DATEPART(mm,main.doc_create_time) when '05' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as five,  " +
					"	case DATEPART(mm,main.doc_create_time) when '06' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as six,  " +
					"	case DATEPART(mm,main.doc_create_time) when '07' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as seven,  " +
					"	case DATEPART(mm,main.doc_create_time) when '08' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as eight,  " +
					"	case DATEPART(mm,main.doc_create_time) when '09' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as nine,  " +
					"	case DATEPART(mm,main.doc_create_time) when '10' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as ten,  " +
					"	case DATEPART(mm,main.doc_create_time) when '11' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as eleven,  " +
					"	case DATEPART(mm,main.doc_create_time) when '12' then sum(main.fd_this_refund_money*main.fd_exchange_rate) else 0 end as twelve  " +
					"	from  fssc_payment_refund main  ");
			sql.append( " WHERE  DATEPART(yy,main.doc_create_time) = '" + year + "' ");
		}
		sql.append( " and main.doc_status = '"+status+"'").append(" AND ").append(HQLUtil.buildLogicIN("main.fd_cost_center_id", ids)).append("GROUP BY main.doc_create_time)t");
		List<Object[]> list = fsscExpenseMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).list();
		JSONObject json = null;
		for (int i = 0; i < list.size(); i++) {
			json = new JSONObject();
			Object[] object = list.get(i);
			json.put("one", (null != object[0] ? FsscNumberUtil.doubleToUp(object[0].toString()): 0));
			json.put("two", (null != object[1] ? FsscNumberUtil.doubleToUp(object[1].toString()) : 0));
			json.put("three", (null != object[2] ? FsscNumberUtil.doubleToUp(object[2].toString()) : 0));
			json.put("four", (null != object[3] ? FsscNumberUtil.doubleToUp(object[3].toString()) : 0));
			json.put("five", (null != object[4] ? FsscNumberUtil.doubleToUp(object[4].toString()) : 0));
			json.put("six", (null != object[5] ? FsscNumberUtil.doubleToUp(object[5].toString()) : 0));
			json.put("seven", (null != object[6] ? FsscNumberUtil.doubleToUp(object[6].toString()) : 0));
			json.put("eight", (null != object[7] ? FsscNumberUtil.doubleToUp(object[7].toString()) : 0));
			json.put("nine", (null != object[8] ? FsscNumberUtil.doubleToUp(object[8].toString()) : 0));
			json.put("ten", (null != object[9] ? FsscNumberUtil.doubleToUp(object[9].toString()) : 0));
			json.put("eleven", (null != object[10] ? FsscNumberUtil.doubleToUp(object[10].toString()) : 0));
			json.put("twelve", (null != object[11] ? FsscNumberUtil.doubleToUp(object[11].toString()): 0));
		}
		JSONObject obj = new JSONObject();
		obj.put("values", json);
		return obj;
	}
	
	

	@Override
	public JSONObject loadCostHead() throws Exception {
		//获取当前登录人员是成本中心负责人的成本中心ID
		List<String> centerIds = eopBasedataCostCenterService.getCenterByUserId(UserUtil.getKMSSUser().getUserId());

		JSONObject loanJson = null;
		JSONObject payJson = null;
		if (FsscCommonUtil.checkHasModule("/fssc/loan/")) {
			// 统计今年的借款金额
			double loanAmount = getLoanTotalAmount(getCurYear(), UserUtil.getKMSSUser().getDeptId());

			// 统计去年借款金额
			double lastLoanAmount = getLoanTotalAmount(getLastYear(), UserUtil.getKMSSUser().getDeptId());

			loanJson = new JSONObject();
			loanJson.put("thisLoanTotal", loanAmount);// 今年借款金额
			loanJson.put("lastLoanTotal", lastLoanAmount);// 去年借款金额
		}
		if(FsscCommonUtil.checkHasModule("/fssc/payment/")) {
			double thisPayAmount = 0;
			double thisSuspendAmount = 0;
			double thisRefundAmount = 0;
			double lastPayAmount = 0;
			double lastSuspendAmount = 0;
			double lastRefundAmount = 0;
			if(!ArrayUtil.isEmpty(centerIds)) {
				//今年付款
				thisPayAmount = getPaymentTotalAmount(getCurYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);


				//今年挂账
				thisSuspendAmount = getPaySuspendTotalAmount(getCurYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
				
				//今年退款
				thisRefundAmount = getRefundTotalAmount(getCurYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
				
				
				//去年付款
				lastPayAmount =getPaymentTotalAmount(getLastYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);

				//去年挂账
				lastSuspendAmount = getPaySuspendTotalAmount(getLastYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
				
				//今年退款
				lastRefundAmount = getRefundTotalAmount(getLastYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
			}
			payJson = new JSONObject();

			Double thisPayTotal=FsscNumberUtil.getAddition(thisPayAmount,FsscNumberUtil.getSubtraction(thisSuspendAmount,thisRefundAmount),2);
			Double lastPayTotal=FsscNumberUtil.getAddition(lastPayAmount,FsscNumberUtil.getSubtraction(lastSuspendAmount,lastRefundAmount),2);
			payJson.put("thisPayTotal", thisPayTotal);// 今年付款金额
			payJson.put("lastPayTotal", lastPayTotal);// 去年付款金额
		}
		// 统计今年的报销金额
		double expenseAmount = getTotalExpenseAmount(getCurYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
		// 统计去年报销金额
		double lastExpenseAmount = getTotalExpenseAmount(getLastYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
		JSONObject expenseJson = new JSONObject();
		expenseJson.put("thisExpenseTotal", new BigDecimal(expenseAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());// 今年报销金额
		expenseJson.put("lastExpenseTotal", new BigDecimal(lastExpenseAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());// 去年借款金额
		
		JSONObject json = new JSONObject();
		json.put("loan", loanJson);
		json.put("expense", expenseJson);
		json.put("payment", payJson);
		return json;
	}

	@Override
	public JSONObject costContent() throws Exception {
		// 获取当前部门下人员ID
		List<String> ids = getUserId();
		
		//获取当前登录人员是成本中心负责人的成本中心ID
		List<String> centerIds = eopBasedataCostCenterService.getCenterByUserId(UserUtil.getKMSSUser().getUserId());

		JSONObject loanJson = null;
		JSONObject payJson = null;
		if (FsscCommonUtil.checkHasModule("/fssc/loan/")) {
			loanJson=getTotalLoan(getCurYear(), UserUtil.getKMSSUser().getDeptId());
		}
		if (FsscCommonUtil.checkHasModule("/fssc/payment/")) {
			JSONObject pay = null;
			JSONObject prePay = null;
			JSONObject suspend = null;
			JSONObject refund = null;
			if(!ArrayUtil.isEmpty(centerIds)) {
				// 付款
				pay = getPaymentTotal(getCurYear(), centerIds, FsscExpenseConstant.FSSC_IS_NOT_PRE,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
				// 预付款
				prePay = getPrePaymentTotal(getCurYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
				// 挂账付款
				suspend = getSuspendTotal(getCurYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
				//退款
				refund = getRefundTotal(getCurYear(), centerIds,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
			}

			payJson = new JSONObject();
			payJson.put("pay", null != pay ? pay : convert());// 付款
			payJson.put("prePay", null != prePay ? prePay : convert());// 预付款
			payJson.put("suspend", null != suspend ? suspend : convert());// 挂账付款
			payJson.put("refund", null != refund ? refund : convert());// 退款
		}
		JSONObject expenseIng = null;
		JSONObject publicExpense = null;
		if(!ArrayUtil.isEmpty(centerIds)) {
			// 报销中
			expenseIng = getExpenseTotal(getCurYear(), centerIds, null,FsscExpenseConstant.FSSC_DOC_STATUS_APPROVE);
			// 已报销
			publicExpense = getExpenseTotal(getCurYear(), centerIds, null,FsscExpenseConstant.FSSC_DOC_STATUS_PUBLIC);
	}
	// 未报销
	JSONObject notExpense = getExpenseTotal(getCurYear(), ids, null, null);

	JSONObject expenseJson = new JSONObject();
		expenseJson.put("expenseIng", null != expenseIng ? expenseIng : convert());// 报销中
		expenseJson.put("notExpense", notExpense);// 未报销
		expenseJson.put("publicExpense", null != publicExpense ? publicExpense : convert());// 已报销

		JSONObject json = new JSONObject();
		json.put("loan", loanJson);
		json.put("expense", expenseJson);
		json.put("payment", payJson);
		return json;
	}
	
	private JSONObject convert() {
		JSONObject json = new JSONObject();
		json.put("one",  0);
		json.put("two", 0);
		json.put("three", 0);
		json.put("four", 0);
		json.put("five", 0);
		json.put("six", 0);
		json.put("seven", 0);
		json.put("eight", 0);
		json.put("nine", 0);
		json.put("ten", 0);
		json.put("eleven", 0);
		json.put("twelve", 0);
		JSONObject obj = new JSONObject();
		obj.put("values", json);
		return obj;
	}


}
