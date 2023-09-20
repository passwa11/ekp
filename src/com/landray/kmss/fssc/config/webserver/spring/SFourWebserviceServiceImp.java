package com.landray.kmss.fssc.config.webserver.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.fssc.config.util.WebServerUtil;
import com.landray.kmss.fssc.config.webserver.ISFourWebserviceService;
import com.landray.kmss.web.annotation.RestApi;

@Controller
@RequestMapping(value = "/api/fssc-config/sFourWebserviceService", method = RequestMethod.POST)
@RestApi(docUrl = "/fssc/config/webserver/sFour_webserviceHelp.jsp", name = "sFourWebserviceServiceImp", resourceKey = "fssc-config:sFourWebserviceService.title")
public class SFourWebserviceServiceImp implements ISFourWebserviceService{

	@Override
	@ResponseBody
	@RequestMapping(value = "/getData", method = RequestMethod.POST)
	public String getData(String startDate,String endDate,String dataType) throws Exception{
		String mainFields="review.fd_number as fd_code,DATE_FORMAT(main.fd_date, '%Y-%m-%d') as fd_date,"
				+ "main.fd_dept,main.fd_person,main.fd_tp_person,main.fd_desc,main.fd_data_type ";
		String mainSql = "SELECT "+mainFields+" FROM ekp_s_four_main main "
				+ " LEFT JOIN km_review_main review on main.fd_id=review.fd_id ";
		
		String detailFields="review.fd_number as fd_code,fd_zc_code,DATE_FORMAT(fd_yjgh_date, '%Y-%m-%d') as fd_yjgh_date,fd_dc_dept,fd_user_person";

		String detailSql="SELECT "+detailFields+" FROM ekp_s_four_detail detail "
				+ "LEFT JOIN km_review_main review on detail.fd_parent_id=review.fd_id  "
				+ "LEFT JOIN ekp_s_four_main main on detail.fd_parent_id=main.fd_id  ";
		String result=new WebServerUtil().formatData(mainSql,detailSql, startDate, endDate,dataType);
		return result;
	}
	
}
