package com.landray.kmss.fssc.config.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;

import com.landray.kmss.fssc.config.service.IFsscConfigScoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class WebServerUtil {
	public  final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private IFsscConfigScoreService fsscConfigScoreService;
	public  IFsscConfigScoreService getServiceImp() {
		if (fsscConfigScoreService == null) {
			fsscConfigScoreService = (IFsscConfigScoreService) SpringBeanUtil.getBean("fsscConfigScoreService");
		}
		return fsscConfigScoreService;
	}
	
	private  Map<String, List<Map<String, Object>>> getDetailMap(String detailSql,String startDate,String endDate,String dataType) throws Exception{
		Map<String, List<Map<String, Object>>> result=new HashMap<String, List<Map<String, Object>>>();
		String where="WHERE 1=1 and main.fd_data_type=:dataType and review.doc_status='30' ";//只查询已发布的单据and main.fd_date=:dataType and review.doc_status='30' 
		if(StringUtil.isNotNull(startDate)){
			where+=" and main.fd_date>=:startDate";
		}
		if(StringUtil.isNotNull(endDate)){
			where+=" and main.fd_date<=:endDate";
		}
		
		detailSql+=where;
		System.out.println("detailSql:"+detailSql);
		Query query = getServiceImp().getBaseDao().getHibernateSession().createNativeQuery(detailSql);
		if(StringUtil.isNotNull(startDate)){
			query.setParameter("startDate", startDate);
		}
		if(StringUtil.isNotNull(endDate)){
			query.setParameter("endDate", endDate);
		}
		if(StringUtil.isNotNull(dataType)){
			query.setParameter("dataType", dataType);
		}
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for (Map<String, Object> map : list) {
			String key=(String) map.get("fd_code");
			List<Map<String, Object>> detailList=result.get(map.get("fd_code"));
			if(detailList==null){
				detailList=new ArrayList<Map<String,Object>>();
			}
			detailList.add(map);
			result.put(key, detailList);
		}
		return result;
	}
	
	public  String formatData(String mainSql,String detailSql,String startDate,String endDate,String dataType) throws Exception {
		JSONObject result=new JSONObject();
		if(StringUtil.isNull(dataType)){//数据类型：1：资产领用；2：资产调拨
			result.put("code", 400);
			result.put("data", "请求失败,参数【dataType】为必填参数!");
			return result.toString();
		}
		try {
			String where ="WHERE 1=1 and main.fd_data_type=:dataType and review.doc_status='30' ";//只查询已发布的单据and main.fd_date=:dataType and review.doc_status='30' 
			if(StringUtil.isNotNull(startDate)){
				where+=" and main.fd_date>=:startDate";
			}
			if(StringUtil.isNotNull(endDate)){
				where+=" and main.fd_date<=:endDate";
			}
			mainSql+=where;
			System.out.println("mainSql:"+mainSql);
			Query query = getServiceImp().getBaseDao().getHibernateSession().createNativeQuery(mainSql);
			query.setParameter("dataType", dataType);

			if(StringUtil.isNotNull(startDate)){
				query.setParameter("startDate", startDate);
			}
			if(StringUtil.isNotNull(endDate)){
				query.setParameter("endDate", endDate);
			}
			List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if(list!=null&&list.size()>0){
				Map<String, List<Map<String, Object>>> detailMap=getDetailMap(detailSql,startDate, endDate,dataType);
				for (Map<String, Object> map : list) {
					String code=(String) map.get("fd_code");
					List<Map<String, Object>> detail= detailMap.get(code);
					map.put("detail",detail==null?"":detail);
				}
			}
			net.sf.json.JSONArray jsonarray = net.sf.json.JSONArray.fromObject(list);
			result.put("code", 200);
			result.put("data", jsonarray.toString());
			return result.toString();
		} catch (Exception e) {
			result.put("code", 300);
			result.put("data", "请求失败请联系管理员！mainSql为:"+mainSql+"=========detailSql为:"+detailSql);
			return result.toString();
		}
	}
}
