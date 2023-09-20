package com.landray.kmss.km.review.service.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/api/km-review/main", method = RequestMethod.POST)
@RestApi(docUrl = "/km/review/restservice/kmReviewRestHelp.jsp", name = "kmReviewMainDataService", resourceKey = "km-review:kmReviewMain.job.sync")
public class KmReviewMainDataServiceImp {

	private IKmReviewMainService kmReviewMainService;
	
	private ISysOrgCoreService sysOrgCoreService; 

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setKmReviewMainService(IKmReviewMainService kmReviewMainService) {
		this.kmReviewMainService = kmReviewMainService;
	}

	@ResponseBody
	@RequestMapping("/get")
	public JSONObject getCarmng(@RequestParam("templateId") String templateId, @RequestParam("btField") String btField,@RequestParam("etField") String etField,@RequestParam(value="urField",required=false) String urField,@RequestBody(required=false) JSONObject paramData) {
		JSONObject result = new JSONObject();
		result.put("success", false);
		try {
			Date beginTime = null;
			if (paramData != null && paramData.containsKey("beginTime")) {
				beginTime = DateUtil.convertStringToDate(paramData.getString("beginTime"));
			}
			// 只获取当天的有效会议
			HQLInfo info = new HQLInfo();
			StringBuffer sb = new StringBuffer();			
			if (beginTime == null) {
				// 如果是当天第一次同步，则同步所有的有效流程信息
				sb.append(
						"kmReviewMain.fdTemplate.fdId=:templateId and kmReviewMain.docStatus=:docStatus");
			} else {
				info.setParameter("publishTime", beginTime);
				sb.append(
						"kmReviewMain.fdTemplate.fdId=:templateId and kmReviewMain.docStatus=:docStatus and kmReviewMain.docPublishTime>:publishTime");
			}
			info.setWhereBlock(sb.toString());
			info.setParameter("templateId", templateId);
			info.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
			List<?> retVal = kmReviewMainService.findList(info);
			JSONArray array = new JSONArray();
			for (int i = 0; i < retVal.size(); i++) {
				KmReviewMain kmReviewMain = (KmReviewMain) retVal.get(i);				
				Object bt = kmReviewMain.getExtendDataModelInfo().getModelData().get(btField);
				Object et = kmReviewMain.getExtendDataModelInfo().getModelData().get(etField);
				if(bt!=null&&et!=null&&bt instanceof Date&&et instanceof Date){
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);					
					Date begin = (Date)bt;
					Date end = (Date)et;
					if(cal.getTime().before(begin) || cal.getTime().before(end)){
						//如果开始时间在当天之后或者结束时间在当天之后，则同步到今日工作中
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("workItemId", kmReviewMain.getFdId());
						jsonObject.put("workTitle", kmReviewMain.getDocSubject());
						jsonObject.put("bgTime",
								begin.getTime());
						jsonObject.put("endTime",
								end.getTime());
						jsonObject.put("jobStatus", "UPDATE");
						jsonObject.put("detailUrl", StringUtil.formatUrl(ModelUtil.getModelUrl(kmReviewMain),true));
						List<String> fdLoginName = new ArrayList<String>();
						if(StringUtil.isNotNull(urField)) {
							//TODO
							Object usrs = kmReviewMain.getExtendDataModelInfo().getModelData().get(urField);
							if(usrs instanceof HashMap){
								HashMap orgMap = (HashMap)usrs;
								String ids = orgMap.containsKey("id")?(String)orgMap.get("id"):"";
								String[] orgIds = ids.split(";"); 
								for(String orgId:orgIds){
									if(StringUtil.isNotNull(orgId)){
										SysOrgPerson person = (SysOrgPerson)sysOrgCoreService.findByPrimaryKey(orgId, SysOrgPerson.class);
										if(person!=null){
											fdLoginName.add(person.getFdLoginName());
										}
									}
								}
							}else{
								
							}
							System.out.println(usrs.getClass().getName()+"==" + usrs.toString());
							
						}else{
							fdLoginName.add(kmReviewMain.getDocCreator().getFdLoginName());
						}
						jsonObject.put("loginNames", fdLoginName);
						array.add(jsonObject);
					}
				}				
			}
			result.put("datas", array);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "处理过程中出错：" + e.getMessage());
		}
		return result;
	}

	
}
