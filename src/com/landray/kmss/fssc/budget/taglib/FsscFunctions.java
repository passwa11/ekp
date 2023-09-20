package com.landray.kmss.fssc.budget.taglib;

import java.util.List;
import java.util.Map;

import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class FsscFunctions {
	
	protected static  IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;

	public static  IEopBasedataBudgetSchemeService getEopBasedataBudgetSchemeService() {
		if(eopBasedataBudgetSchemeService==null){
			eopBasedataBudgetSchemeService=(IEopBasedataBudgetSchemeService) SpringBeanUtil.getBean("eopBasedataBudgetSchemeService");
		}
		return eopBasedataBudgetSchemeService;
	}
	/*************************************************
	 * @param schemeId  预算方案ID
	 * 功能：根据预算方案ID拼接index展现字段
	 * **********************************************/
	public static String showBudgetColomu(String fdSchemeId) throws Exception{
		StringBuilder colStr=new StringBuilder();
		if(StringUtil.isNull(fdSchemeId)||"null".equals(fdSchemeId)){
			return colStr.toString();
		}
		Map<String,List<String>>  propertyMap=EopBasedataFsscUtil.getPropertyByScheme(fdSchemeId);
		for(String property:propertyMap.get("inPropertyList")){
			colStr.append(property).append(".name;");
		}
		EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) getEopBasedataBudgetSchemeService().findByPrimaryKey(fdSchemeId, EopBasedataBudgetScheme.class, true);
		if(!"1".equals(scheme.getFdPeriod())){//非不限
			colStr.append("fdYear;fdPeriod;");
		}
		return colStr.toString();
	}
}
