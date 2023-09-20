package com.landray.kmss.fssc.budget.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;

public class FsscBudgetPortalService implements IXMLDataBean{
	 protected IFsscBudgetDataService fsscBudgetDataService;

		public void setFsscBudgetDataService(IFsscBudgetDataService fsscBudgetDataService) {
			this.fsscBudgetDataService = fsscBudgetDataService;
		}
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList=new ArrayList();
		String source=requestInfo.getParameter("source");
		if("checkZhixing".equals(source)){
			String fdCompanyId=requestInfo.getParameter("fdCompanyId");
			String fdscheme=requestInfo.getParameter("fdscheme");
			String sql = "SELECT " + 
					"	SUM( " + 
					"		fssc_budget_zhixing.fd_money1 " + 
					"	), " + 
					"	SUM( " + 
					"		fssc_budget_zhixing.fd_money2 " + 
					"	), " + 
					"	SUM( " + 
					"		fssc_budget_zhixing.fd_money3 " + 
					"	), " + 
					"	SUM( " + 
					"		fssc_budget_zhixing.fd_money4 " + 
					"	) " + 
					"FROM " + 
					"	( " + 
					"		SELECT " + 
					"			exe_.fd_budget_id, " + 
					"			SUM( " + 
					"				CASE " + 
					"				WHEN exe_.fd_type = '1' THEN " + 
					"					exe_.fd_money " + 
					"				ELSE " + 
					"					0 " + 
					"				END " + 
					"			) AS fd_money1, " + 
					"			SUM( " + 
					"				CASE " + 
					"				WHEN exe_.fd_type = '2' THEN " + 
					"					exe_.fd_money " + 
					"				ELSE " + 
					"					0 " + 
					"				END " + 
					"			) AS fd_money2, " + 
					"			SUM( " + 
					"				CASE " + 
					"				WHEN exe_.fd_type = '3' THEN " + 
					"					exe_.fd_money " + 
					"				ELSE " + 
					"					0 " + 
					"				END " + 
					"			) AS fd_money3, " + 
					"			SUM( " + 
					"				CASE " + 
					"				WHEN exe_.fd_type = '4' THEN " + 
					"					exe_.fd_money " + 
					"				ELSE " + 
					"					0 " + 
					"				END " + 
					"			) AS fd_money4 " + 
					"		FROM " + 
					"			fssc_budget_execute exe_ " + 
					"		WHERE " + 
					"			fd_budget_id IN ( " + 
					"				SELECT " + 
					"					a.fd_id " + 
					"				FROM " + 
					"					fssc_budget_data a " + 
					"				WHERE " + 
					"					a.fd_budget_scheme_id = '"+fdscheme+"' " + 
					"				AND a.fd_cost_center_id IN ( " + 
					"					SELECT " + 
					"						center_.fd_id " + 
					"					FROM " + 
					"						eop_basedata_cost_center center_ " + 
					"					WHERE " + 
					"						center_.fd_company_id = '"+fdCompanyId+"' " + 
					"				) " + 
					"			) " + 
					"		GROUP BY " + 
					"			exe_.fd_budget_id " + 
					"	) AS fssc_budget_zhixing";
			
			Query query = fsscBudgetDataService.getBaseDao().getHibernateSession().createNativeQuery(sql);
			List<Object[]> list = query.list();
			for (Object[] object : list) {
				Map map =new HashMap<String, Double>();
				map.put("fdAllMoney", object[0]);//初始金额
				map.put("fdOccupyMoney", object[1]);//占用金额
				map.put("fdUseMoney", object[2]);//已使用金额
				map.put("fdAdjustMoney", object[3]);//调整金额
				rtnList.add(map);
			}
		}else {
			
		}
		return rtnList;
	}

}
