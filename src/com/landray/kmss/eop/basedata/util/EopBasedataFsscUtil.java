package com.landray.kmss.eop.basedata.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataSwitch;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataSwitchService;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EopBasedataFsscUtil{
	
	protected static IEopBasedataSwitchService eopBasedataSwitchService;
	
	public static IEopBasedataSwitchService getEopBasedataSwitchService() {
		if(eopBasedataSwitchService==null){
			eopBasedataSwitchService=(IEopBasedataSwitchService) SpringBeanUtil.getBean("eopBasedataSwitchService");
		}
		return eopBasedataSwitchService;
	}
	
	protected static IEopBasedataCompanyService eopBasedataCompanyService;
	
	public static IEopBasedataCompanyService getEopBasedataCompanyService() {
		if(eopBasedataCompanyService==null){
			eopBasedataCompanyService=(IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
		}
		return eopBasedataCompanyService;
	}

	/**
	 * 拷贝列表属性
	 * @param src
	 * @param dest
	 * @param property
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copyCollectionProperty(IBaseModel src,IBaseModel dest,String property) throws Exception{
		List srcList = (List) PropertyUtils.getProperty(src, property);
		List destList = new ArrayList();
		if(!ArrayUtil.isEmpty(srcList)){
			destList.addAll(srcList);
		}
		PropertyUtils.setProperty(dest, property, destList);
	}
	
	/** 获取对应的开关属性值，非明细
	* @param key
	* **/
	@SuppressWarnings({ "unchecked" })
	public static final String getSwitchValue(String key) throws Exception{
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder();
		if (StringUtil.isNotNull(key)) {
			whereBlock.append(" eopBasedataSwitch.fdProperty=:fdProperty");
			hqlInfo.setParameter("fdProperty", key);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<EopBasedataSwitch> switchList = getEopBasedataSwitchService().findList(hqlInfo);
		if(ArrayUtil.isEmpty(switchList)) {
			return "";
		}else{
			return switchList.get(0)!=null&&switchList.get(0).getFdValue()!=null?switchList.get(0).getFdValue():"";
		}
	}
	
	/** 获取所有开关属性值
	 * 
	 * **/
	@SuppressWarnings({ "unchecked" })
	public static final Map<String,String> getAllSwitchValue() throws Exception{
		Map<String, String> resultMap = new HashMap<>();
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder();
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<EopBasedataSwitch> switchList = getEopBasedataSwitchService().findList(hqlInfo);
		for (EopBasedataSwitch eopBasedataSwitch : switchList) {
			String value = eopBasedataSwitch.getFdValue();
			resultMap.put(eopBasedataSwitch.getFdProperty(), StringUtil.isNotNull(value) ? value : "");
		}
		return resultMap;
	}
	
	/**
	 * 根据公司ID获取对应明细字段的信息
	 * 
	 * @param fdCompanyId
	 * @param preName  例如：明细name属性为fdDetail.!{index}.fdLoanDate,
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public static final String getDetailPropertyValue(String fdCompanyId,String key) throws Exception {
		HQLInfo hqlInfo=new HQLInfo();
		String whereBlock="";
		if (StringUtil.isNotNull(key)) {
			whereBlock=StringUtil.linkString(whereBlock, " and ", " eopBasedataSwitch.fdValue like :value");
			hqlInfo.setParameter("value", "%"+fdCompanyId+"%");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<EopBasedataSwitch> switchList = getEopBasedataSwitchService().findList(hqlInfo);
		List<String> propertyList=new ArrayList<String>();
		for (EopBasedataSwitch eopBasedataSwitch : switchList) {
			String property=eopBasedataSwitch.getFdProperty();
			if(property.indexOf(".")>-1){
				propertyList.add(property.substring(0, property.lastIndexOf(".")+1)+key);
			}
		}
		whereBlock="";
		hqlInfo=new HQLInfo();
		if (StringUtil.isNotNull(key)&&!ArrayUtil.isEmpty(propertyList)) {
			whereBlock=StringUtil.linkString(whereBlock, " and ", HQLUtil.buildLogicIN("eopBasedataSwitch.fdProperty", propertyList));
		}else{
			whereBlock=StringUtil.linkString(whereBlock, " and ","eopBasedataSwitch.fdProperty is null");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setOrderBy("eopBasedataSwitch.fdId desc");
		switchList = getEopBasedataSwitchService().findList(hqlInfo);
		if(!ArrayUtil.isEmpty(switchList)){
			return switchList.get(0).getFdValue();
		}else{
			return null;
		}
	}
	/**
	 * 获取对应模块的开关账时间
	 * fdModelName 模块名 
	 * fdCompanyId  公司ID
	 * @param fdModelName
	 * @param fdCompanyId
	 * @return
	 * @throws Exception
	 */
	public static final JSONObject getAccountsDate(String fdModelName) throws Exception{
		JSONObject obj=new JSONObject();
		HQLInfo hqlInfo=new HQLInfo();
		String whereBlock="";
		if(StringUtil.isNotNull(fdModelName)){
			SysDictModel model=SysDataDict.getInstance().getModel(fdModelName);
			String module=model.getMessageKey().split(":")[0]+"-";
			whereBlock=StringUtil.linkString(whereBlock, " and ", "eopBasedataSwitch.fdProperty like :fdPropertyStart");
			hqlInfo.setParameter("fdPropertyStart", "fdDetail.%");
			whereBlock=StringUtil.linkString(whereBlock, " and ", "eopBasedataSwitch.fdProperty like :fdPropertyEnd");
			hqlInfo.setParameter("fdPropertyEnd", "%.fdModule");
			whereBlock=StringUtil.linkString(whereBlock, " and ", "eopBasedataSwitch.fdValue  like :fdValue");
			hqlInfo.setParameter("fdValue", "%"+module+"%");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setSelectBlock("eopBasedataSwitch.fdProperty");
			List<String> propertyList = getEopBasedataSwitchService().findList(hqlInfo);//找出开关账公司ID为fdCompanyId对应的明细索引列表
			if(!ArrayUtil.isEmpty(propertyList)){
				whereBlock="";
				hqlInfo=new HQLInfo();
					int index=Integer.parseInt(propertyList.get(0).substring(propertyList.get(0).indexOf(".")+1, propertyList.get(0).lastIndexOf(".")));
					whereBlock="";
					hqlInfo=new HQLInfo();
					whereBlock="eopBasedataSwitch.fdProperty='fdDetail."+index+".fdDate'";
					hqlInfo.setWhereBlock(whereBlock);
					hqlInfo.setSelectBlock("eopBasedataSwitch.fdValue");
					List<String> valueList = getEopBasedataSwitchService().findList(hqlInfo);//找出开关账模块名value为module对应的明细索引列表
					if(!ArrayUtil.isEmpty(valueList)){
						obj.put("fdDate", valueList.get(0));  //开关账时间
					}
					whereBlock="";
					hqlInfo=new HQLInfo();
					whereBlock="eopBasedataSwitch.fdProperty='fdDetail."+index+".fdType'";
					hqlInfo.setWhereBlock(whereBlock);
					hqlInfo.setSelectBlock("eopBasedataSwitch.fdValue");
					valueList = getEopBasedataSwitchService().findList(hqlInfo);//找出开关账模块名value为module对应的明细索引列表
					if(!ArrayUtil.isEmpty(valueList)){
						obj.put("fdType", valueList.get(0));  //开账还是关账
					}
					whereBlock="";
					hqlInfo=new HQLInfo();
					whereBlock="eopBasedataSwitch.fdProperty='fdDetail."+index+".fdCompanyId'";
					hqlInfo.setWhereBlock(whereBlock);
					hqlInfo.setSelectBlock("eopBasedataSwitch.fdValue");
					valueList = getEopBasedataSwitchService().findList(hqlInfo);//找出开关账模块名value为module对应的明细索引列表
					if(!ArrayUtil.isEmpty(valueList)){
						obj.put("fdCompanyIds", valueList.get(0));
					}
			}
		}
		return obj;
	}
	

	/**
	 * 返回单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(HSSFCell cell) {
		String cellValue = "";
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case STRING:
			cellValue = cell.getStringCellValue();
			if ("".equals(cellValue.trim()) || cellValue.trim().length() <= 0) {
				cellValue = " ";
			}
			break;
		case NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				cellValue = DateUtil.convertDateToString(cell
						.getDateCellValue(), null);
			} else {
				cellValue = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case FORMULA:
			cell.setCellType(org.apache.poi.ss.usermodel.CellType.NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case BLANK:
			cellValue = " ";
			break;
		case BOOLEAN:
			break;
		case ERROR:
			break;
		default:
			break;
		}
		if (cellValue.endsWith(".0")) {
			cellValue = cellValue.substring(0, cellValue.length() - 2);
		}
		return cellValue.trim();
	}
	
	public static Map<String,List<String>> getPropertyByScheme(String fdSchemeId) throws Exception{
		Map<String,List<String>> rtnMap=new HashMap<>();
		List<String> inPropertyList=new ArrayList<String>();  //选中维度对应的属性
		List<String> notInPropertyList=new ArrayList<String>();  //未选中维度对应的属性
		EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) getEopBasedataSwitchService().findByPrimaryKey(fdSchemeId,EopBasedataBudgetScheme.class,true);
		if(scheme!=null){
			String[] propertys = {"fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdAsset","fdPerson","fdDept"};
			String dimensions=scheme.getFdDimension()+";";
			for(int k=1,len=propertys.length;k<=len;k++){
				if(k==9){
					continue;  //资产暂时排除
				}
				if(isContain(dimensions, k+";", ";")){
					inPropertyList.add(propertys[k-1]);
				}else{
					notInPropertyList.add(propertys[k-1]);
				}
			}
		}
		rtnMap.put("inPropertyList", inPropertyList);
		rtnMap.put("notInPropertyList", notInPropertyList);
		return rtnMap;
	}
	/**
	 * 根据成本中心和费用类型获取对应的预算科目，若没有，返回null
	 * @param costCenter
	 * @param expenseItem
	 * @return
	 * @throws Exception
	 */
	public static String getBudgetItemIds(EopBasedataCostCenter costCenter,EopBasedataExpenseItem expenseItem) throws Exception{
		String rtnBudgetItem=null;
		List<EopBasedataBudgetItem> budgetItemList=expenseItem.getFdBudgetItems();
		if (!ArrayUtil.isEmpty(budgetItemList)&&(budgetItemList.size() == 1||costCenter==null)) {//若是成本中心为空，默认拿第一个
			rtnBudgetItem=budgetItemList.get(0).getFdId();
		} else {
			// 成本中心属性代码
			if(costCenter!=null){
				String propertyCode = costCenter.getFdType().getFdCode();
				for (EopBasedataBudgetItem budgetItem : budgetItemList) {
					if (budgetItem.getFdCode().startsWith(propertyCode)) {
						rtnBudgetItem=budgetItem.getFdId();
						break;  //若是找到一个直接跳出循环
					}
				}
			}
		}
		return rtnBudgetItem;
	}
	
	/**
	 * 获取模块名
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> getFsscModule(HttpServletRequest request) throws Exception{
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		String keyword=request.getParameter("q._keyword");
		SysConfigs configs = SysConfigs.getInstance();
		List<?> modules = configs.getModuleInfoList();
		//开关账设置中，只有付款、报销、借款、凭证、预提四个模块才会进行控制
		String[] fsscModules=new String[] {"expense","loan","payment","voucher","provision"};
		if(modules.size()>0){
			Map<String, String> node = new HashMap<String, String>();
			for(String module:fsscModules) {
				node = new HashMap<String, String>();
				String url="/fssc/"+module+"/";
				if(null!=SysConfigs.getInstance().getModule(url)){
					node.put("text", ResourceUtil.getString("module.fssc."+module, "eop-basedata"));
					node.put("value", "fssc-"+module+"-");
					if(StringUtil.isNull(keyword)){
						if(null!=SysConfigs.getInstance().getModule(url)){
							rtnList.add(node);
						}
					} else {
						if(null!=SysConfigs.getInstance().getModule(url) && ResourceUtil.getString("module.fssc."+module, "eop-basedata").indexOf(keyword)>-1){
							rtnList.add(node);
						}
					}
				}
			}
		}
		return rtnList;
	}
	/**
	 * 判断选中单据是否有选中单据的property属性值为validateValue，多个以英文逗号分隔 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public static boolean checkStatus(String[] ids,String modelName,String property,String validateValue) throws Exception{
		String serviceName = SysDataDict.getInstance().getModel(modelName)
				.getServiceBean();
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(serviceName);
		boolean rtn=Boolean.TRUE;
		if(ids.length>0&&StringUtil.isNotNull(modelName)&&StringUtil.isNotNull(property)&&StringUtil.isNotNull(validateValue)){
			List result=service.findByPrimaryKeys(ids);
			String[] validates=validateValue.split(";");
			for(Object obj:result){
				Object value=PropertyUtils.getProperty(obj, property);
				for(String status:validates){
					if(status.equals(value)){
						rtn=Boolean.FALSE;
						break;
					}
				}
			}
		}
		return rtn;
	}
	
	/*
	 * 判断fdModelName模块是否为开账，且对应开关账的公司ID，多个公司英文分隔符分隔。开账返回的是所有开账公司，关账返回的是关账的公司ID
	 */
	public static JSONObject getAccountAuth(String fdModelName,String fdPersonId) throws Exception{
		JSONObject rtnObj=new JSONObject();
		if(StringUtil.isNull(fdPersonId)) {
			fdPersonId =UserUtil.getUser().getFdId();
		}
		List<String> ids = new ArrayList<String>();
        if(StringUtil.isNotNull(fdPersonId)&&!UserUtil.checkRole("SYSROLE_ADMIN")){
         	List<EopBasedataCompany> list = getEopBasedataCompanyService().findCompanyByUserId(fdPersonId);
         	for(EopBasedataCompany comp:list){
     			ids.add(comp.getFdId());
     		}
        }else {//获取所有公司
        	HQLInfo hql = new HQLInfo();
        	hql.setSelectBlock("eopBasedataCompany.fdId");
    		hql.setWhereBlock("eopBasedataCompany.fdIsAvailable =:fdIsAvailable");
    		hql.setParameter("fdIsAvailable", true);
    		hql.setOrderBy("eopBasedataCompany.fdId asc");
    		Set<String> rtnSet = new HashSet<String>(getEopBasedataCompanyService().findList(hql));// 用set封装，去除重复的记账公司
    		ids=new ArrayList<String>(rtnSet);
        }
		boolean auth=true;
		String fdCompanyIds="";
		if(StringUtil.isNotNull(fdModelName)) {
			JSONObject valObj=getAccountsDate(fdModelName);
			String fdType=valObj.containsKey("fdType")?valObj.getString("fdType"):"";
    		String fdDate=valObj.containsKey("fdDate")?valObj.getString("fdDate"):"";
    		fdCompanyIds=valObj.containsKey("fdCompanyIds")?valObj.getString("fdCompanyIds"):"";
    		String currentDate=DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATE);
    		if(StringUtil.isNotNull(fdDate)&&StringUtil.isNotNull(fdType)&&StringUtil.isNotNull(fdCompanyIds)){	//模块设置了开关帐
    			if(EopBasedataConstant.FSSC_BASE_OPEN.equals(fdType)){//开账
    				if(EopBasedataFsscUtil.dayDiff(DateUtil.convertStringToDate(fdDate, DateUtil.PATTERN_DATE), 
    						DateUtil.convertStringToDate(currentDate, DateUtil.PATTERN_DATE))<0){
    						//当前时间<开账时间
    						auth=false; //不允许新建
    						ids.clear();
    						ids.addAll(ArrayUtil.convertArrayToList(fdCompanyIds.split(";")));//返回关账的公司
    					}
    			}else if(EopBasedataConstant.FSSC_BASE_CLOSE.equals(fdType)){//关账
    				if(EopBasedataFsscUtil.dayDiff(DateUtil.convertStringToDate(fdDate, DateUtil.PATTERN_DATE), 
    						DateUtil.convertStringToDate(currentDate, DateUtil.PATTERN_DATE))>=0){
    					//当前时间>关账时间
    					auth=false; //不允许新建
    					ids.clear();
    					ids.addAll(ArrayUtil.convertArrayToList(fdCompanyIds.split(";")));//返回关账的公司
    				}
    			}
    		}
		}
		String companyIds="";
		if(!ArrayUtil.isEmpty(ids)) {
			for(String companyId:ids) {
				companyIds=StringUtil.linkString(companyIds, ";", companyId);
			}
		}
		rtnObj.put("fdCompanyIds", companyIds);
		rtnObj.put("auth", auth);
		return rtnObj;
	}
	
	/**
	 * 查询是否启用流程提交人身份
	 * @return
	 * @throws Exception
	 */
	public static Boolean getIsEnableDraftorStatus()throws Exception{
		String hql = "select fd_value from sys_app_config where fd_field=:fdField and fd_key=:fdModelName";
		Object value = getEopBasedataSwitchService().getBaseDao().getHibernateSession().createSQLQuery(hql)
				.setParameter("fdField", "isShowDraftsmanStatus").setParameter("fdModelName", "com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting")
				.setMaxResults(1).uniqueResult();
		return value!=null&&"true".equals(value.toString());
	}
	
	/**
	 * 查询流程提交人身份为多个时，新建页面是否弹窗提醒
	 * @return
	 * @throws Exception
	 */
	public static Boolean getIsPopupWindowRemindSubmitter()throws Exception{
		String hql = "select fd_value from sys_app_config where fd_field=:fdField and fd_key=:fdModelName";
		Object value = getEopBasedataSwitchService().getBaseDao().getHibernateSession().createSQLQuery(hql)
				.setParameter("fdField", "isPopupWindowRemindSubmitter").setParameter("fdModelName", "com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting")
				.setMaxResults(1).uniqueResult();
		return value!=null&&"true".equals(value.toString());
	}
	
	/**
	 * 获取提交人身份
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static JSONObject getClaimantStatu(String fdPersonId) throws Exception {
		JSONObject  rtn = new JSONObject();
		JSONArray data = new JSONArray();
		SysOrgElement ele = (SysOrgElement) getEopBasedataSwitchService().findByPrimaryKey(fdPersonId, SysOrgElement.class, true);
		JSONObject o = new JSONObject();
    	o.put("value", ele.getFdId());
    	o.put("text", ele.getFdName());
    	data.add(o);
		rtn.put("result", "success");
		// 查找岗位
	    String sql = "select fd_id, fd_name from sys_org_element left join sys_org_post_person on fd_id=fd_postid where fd_personid=:fdPersonId";
	    List<Object[]> list = getEopBasedataSwitchService().getBaseDao().getHibernateSession().createSQLQuery(sql).setParameter("fdPersonId", fdPersonId).list();
	    for(Object[] obj:list) {
	    	o = new JSONObject();
	    	o.put("value", obj[0]);
	    	o.put("text", obj[1]);
	    	data.add(o);
	    }
		rtn.put("data", data);
		return rtn;
	}
	
	/**
	 * 判断用户是否有多个身份
	 * @param fdPersonId
	 * @return
	 * @throws Exception
	 */
	public static Boolean isMulClaimantStatu(String fdPersonId) throws Exception {
		JSONObject obj = getClaimantStatu(fdPersonId);
		JSONArray arr = obj.getJSONArray("data");
		return arr.size()>1;
	}
	
	/**
	 * 判断是否包含字符串，11传进来变为;11;不会匹配到1;
	 * @param allStr
	 * @param part
	 * @param split
	 * @return
	 */
	public static boolean isContain(String allStr,String part,String split){
		return (split+allStr+split).indexOf(split+part)>-1;
	}
	
	/**
     * 当前日期前进n天(eg:24号返回24+n号)yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getAfterDate(Date date, int n) {
        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat(
                "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(java.util.Calendar.DAY_OF_MONTH, n);
        String s = sim.format(cal.getTime());
        StringBuffer str = new StringBuffer().append(s).append(" 23:59");

        return str.toString();
    }
    /**
     * 当前日期后退n天(eg:26号返回26-n号)yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static Date getBeforeDate(Date date, int n) {
        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat(
                "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(java.util.Calendar.DAY_OF_MONTH, -n);
        String s = sim.format(cal.getTime());
        StringBuffer str = new StringBuffer().append(s).append(" 23:59");
        return DateUtil.convertStringToDate(str.toString(),
                DateUtil.PATTERN_DATETIME);
    }
    
    /** 
     * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数 
     *  
     * @param date1 
     *          Date 
     * @param date2 
     *          Date 
     * @return long 
     */ 
    public static long dayDiff(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 86400000; 
    }
    
    public static boolean checkVersion(String version){
//		return Boolean.valueOf(version)?StringUtil.isNotNull(LicenseUtil.get("license-fssc")):StringUtil.isNull(LicenseUtil.get("license-fssc"));
		return true;
	}
    
    /**
	 * 构造not in语句，若valueList超过1000时，该函数会自动拆分成多个not in语句
	 * 
	 * @param item
	 * @param valueList
	 * @return item in (valueList)
	 */
	public static String buildLogicNotIN(String item, List valueList) {
		int n = (valueList.size() - 1) / 1000;
		StringBuffer rtnStr = new StringBuffer();
		Object obj = valueList.get(0);
		boolean isString = false;
		if (obj instanceof Character || obj instanceof String) {
			isString = true;
		}
		String tmpStr;
		for (int i = 0; i <= n; i++) {
			int size = i == n ? valueList.size() : (i + 1) * 1000;
			if (i > 0) {
				rtnStr.append(" or ");
			}
			rtnStr.append(item + " not in (");
			if (isString) {
				StringBuffer tmpBuf = new StringBuffer();
				for (int j = i * 1000; j < size; j++) {
					tmpStr = valueList.get(j).toString().replaceAll("'", "''");
					tmpBuf.append(",'").append(tmpStr).append("'");
				}
				tmpStr = tmpBuf.substring(1);
			} else {
				tmpStr = valueList.subList(i * 1000, size).toString();
				tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
			}
			rtnStr.append(tmpStr);
			rtnStr.append(")");
		}
		if (n > 0) {
			return "(" + rtnStr.toString() + ")";
		} else {
			return rtnStr.toString();
		}
	}
	
	/**
	 * 过滤list里面的重复值
	 * @param dataList
	 * @return
	 */
	public static List removeRepeat(List dataList){
		List result=new ArrayList<>();
		ArrayUtil.concatTwoList(dataList, result);
		return result;
	}
	
	/**
	 * 查询结果按公司排序，将有公司的放前面，仅支持基础数据model对象
	 * @param data
	 * @throws Exception
	 */
	public static List sortByCompany(List data) throws Exception{
		Object[] arr = data.toArray();
		Arrays.sort(arr, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				if(o1==null||o2==null) {
					return 0;
				}
				if(o1.getClass().getName().contains("eop.basedata")) {
					try {
						List<EopBasedataCompany> list = (List<EopBasedataCompany>) PropertyUtils.getProperty(o1, "fdCompanyList");
						return ArrayUtil.isEmpty(list)?1:-1;
					} catch (Exception e) {
						return 0;
					}
				}
				return 0;
			}
		});
		return Arrays.asList(arr);
	}

	public static String getDataBaseType() throws Exception{
		String dataBaseType="";
		String driveName = ResourceUtil.getKmssConfigString("hibernate.connection.driverClass");
		if("oracle.jdbc.driver.OracleDriver".equals(driveName)){
			dataBaseType="oracle";
		}else if("com.mysql.jdbc.Driver".equals(driveName)){
			dataBaseType="mysql";
		}else if("net.sourceforge.jtds.jdbc.Driver".equals(driveName)||"com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driveName)){
			dataBaseType="sqlserver";
		}else if("com.ibm.db2.jcc.DB2Driver".equals(driveName)){
			dataBaseType="db2";
		}
		return dataBaseType;
	}
}
