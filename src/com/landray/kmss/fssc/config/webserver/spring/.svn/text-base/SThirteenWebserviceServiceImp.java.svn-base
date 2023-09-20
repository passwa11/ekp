package com.landray.kmss.fssc.config.webserver.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.fssc.config.util.WebServerUtil;
import com.landray.kmss.fssc.config.webserver.ISThirteenWebserviceService;
import com.landray.kmss.web.annotation.RestApi;

@Controller
@RequestMapping(value = "/api/fssc-config/sThirteenWebserviceService", method = RequestMethod.POST)
@RestApi(docUrl = "/fssc/config/webserver/sThirteen_webserviceHelp.jsp", name = "sThirteenWebserviceServiceImp", resourceKey = "fssc-config:sThirteenWebserviceService.title")
public class SThirteenWebserviceServiceImp implements ISThirteenWebserviceService{
	@Override
	@ResponseBody
	@RequestMapping(value = "/getData", method = RequestMethod.POST)
	public String getData(String startDate,String endDate,String dataType) throws Exception{
		String mainFields="review.fd_number as fd_code,DATE_FORMAT(main.fd_date, '%Y-%m-%d') as fd_date,"
				+ "main.fd_person,main.fd_desc,main.fd_dept,main.fd_data_type ";
		String mainSql = "SELECT "+mainFields+" FROM ekp_s_thirteen_main main "
				+ " LEFT JOIN km_review_main review on main.fd_id=review.fd_id ";
		
		String detailFields="review.fd_number as fd_code,fd_wz_code,fd_wz_name,fd_count,fd_ck_name,fd_ck_name_in";
		String detailSql="SELECT "+detailFields+" FROM ekp_s_thirteen_detail detail "
				+ "LEFT JOIN km_review_main review on detail.fd_parent_id=review.fd_id  "
				+ "LEFT JOIN ekp_s_thirteen_main main on detail.fd_parent_id=main.fd_id  ";
		String result=new WebServerUtil().formatData(mainSql,detailSql, startDate, endDate,dataType);
		return result;
	}
	
}
