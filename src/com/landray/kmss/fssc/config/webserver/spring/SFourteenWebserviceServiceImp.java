package com.landray.kmss.fssc.config.webserver.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.fssc.config.service.IFsscConfigScoreService;
import com.landray.kmss.fssc.config.webserver.ISFourteenWebserviceService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/api/fssc-config/sFourteenWebserviceService", method = RequestMethod.POST)
@RestApi(docUrl = "/fssc/config/webserver/sFourteen_webserviceHelp.jsp", name = "sFourteenWebserviceServiceImp", resourceKey = "fssc-config:sFourteenWebserviceService.title")
public class SFourteenWebserviceServiceImp implements ISFourteenWebserviceService{

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private IFsscConfigScoreService fsscConfigScoreService;
	public IFsscConfigScoreService getServiceImp() {
		if (fsscConfigScoreService == null) {
			fsscConfigScoreService = (IFsscConfigScoreService) SpringBeanUtil.getBean("fsscConfigScoreService");
		}
		return fsscConfigScoreService;
	}
	@Override
	@ResponseBody
	@RequestMapping(value = "/getData", method = RequestMethod.POST)
	public String getData(String startDate,String endDate) throws Exception{
		String sql ="";
		JSONObject result=new JSONObject();
		try {
			logger.info("请求的数据:开始日期：" + startDate+"-结束日期:"+endDate);
			String fields="detail.fd_zc_code,main.fd_type ";
			 sql = "SELECT "+fields+" FROM ekp_s_fourteen_main main "
					+ " LEFT JOIN km_review_main review on main.fd_id=review.fd_id "
					+ " LEFT JOIN ekp_s_fourteen_detail detail on main.fd_id=detail.fd_parent_id ";
			String where ="WHERE 1=1 and review.doc_status='30'  ";//只查询已发布的单据and main.fd_date=:dataType and review.doc_status='30' 
			if(StringUtil.isNotNull(startDate)){
				where+=" and review.doc_create_time>=:startDate";
			}
			if(StringUtil.isNotNull(endDate)){
				where+=" and review.doc_create_time<=:endDate";
			}
			where+=" ORDER BY review.doc_create_time";
			sql+=where;
			System.out.println("mainSql:"+sql);
			Query query = getServiceImp().getBaseDao().getHibernateSession().createNativeQuery(sql);

			if(StringUtil.isNotNull(startDate)){
				query.setParameter("startDate", startDate);
			}
			if(StringUtil.isNotNull(endDate)){
				query.setParameter("endDate", endDate);
			}
			List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			Map<String, Object> codeMap=new HashMap<>();
			for (Map<String, Object> map : list) {
				String fdZcCode=(String) map.get("fd_zc_code");
				String fdType=(String) map.get("fd_type");
				if(StringUtil.isNotNull(fdZcCode)&&StringUtil.isNotNull(fdType)){
					codeMap.put(fdZcCode, fdType);
				}
			}
			List<Map<String, Object>> resultList=new ArrayList<>();
			for(String key:codeMap.keySet()){
			       String value = codeMap.get(key).toString();
			       Map<String, Object> one=new HashMap<>();  
			       one.put("fd_zc_code", key);
			       one.put("fd_type", value);
			       resultList.add(one);
			}
			net.sf.json.JSONArray jsonarray = net.sf.json.JSONArray.fromObject(resultList);
			result.put("code", 200);
			result.put("data", jsonarray.toString());
			
		} catch (Exception e) {
			result.put("code", 300);
			result.put("data", "请联系管理员！sql为:"+sql);
		}
		return result.toString();
	}
}
