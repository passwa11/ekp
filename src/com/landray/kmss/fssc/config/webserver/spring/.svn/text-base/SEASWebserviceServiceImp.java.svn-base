package com.landray.kmss.fssc.config.webserver.spring;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.fssc.config.util.WebServerUtil;
import com.landray.kmss.fssc.config.webserver.ISEASWebserviceService;
import com.landray.kmss.fssc.config.webserver.ISFiveWebserviceService;
import com.landray.kmss.web.annotation.RestApi;

@Controller
@RequestMapping(value = "/api/fssc-config/sEASWebserviceService", method = RequestMethod.POST)
@RestApi(docUrl = "/fssc/config/webserver/sFive_webserviceHelp.jsp", name = "sFiveWebserviceServiceImp", resourceKey = "fssc-config:sFiveWebserviceService.title")
public class SEASWebserviceServiceImp implements ISEASWebserviceService{

	@Override
	@ResponseBody
	@RequestMapping(value = "/getData", method = RequestMethod.POST)
	public String getData(String startDate,String endDate,String dataType) throws Exception{
		String mainFields="review.fd_number as fd_code,DATE_FORMAT(main.fd_date, '%Y-%m-%d') as fd_date,main.fd_cz_way,main.fd_person,"
				+ "main.fd_data_type as fd_zc_type,main.fd_cz_type,main.fd_desc,main.fd_org,main.fd_dept,main.fd_user_person,main.fd_data_type ";
		String mainSql = "SELECT "+mainFields+" FROM ekp_s_five_main main "
				+ " LEFT JOIN km_review_main review on main.fd_id=review.fd_id ";
		
		String detailFields="review.fd_number as fd_code,fd_zc_code,fd_money,fd_net_money,DATE_FORMAT(detail.fd_date, '%Y-%m-%d') as fd_date,fd_total_depreciation,fd_warehouse,fd_count,fd_Version";
		String detailSql="SELECT "+detailFields+" FROM ekp_s_five_detail detail "
				+ "LEFT JOIN km_review_main review on detail.fd_parent_id=review.fd_id  "
				+ "LEFT JOIN ekp_s_five_main main on detail.fd_parent_id=main.fd_id  ";
		String result=new WebServerUtil().formatData(mainSql,detailSql, startDate, endDate,dataType);
		return result;
	}
	
	
	
}
