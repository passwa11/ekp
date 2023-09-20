package com.landray.kmss.km.review.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.fssc.config.util.FormDataUtil;
import com.landray.kmss.km.cogroup.util.HttpRequest;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.shapesecurity.salvation2.Values.Hash;

public class SendSFUtil {
	//sf.api.feedbackWork
	public static void sendSF(KmReviewMain main,String fdStatus) throws Exception {
		Properties	proer = FileUtil.getProperties("zxgl-config.properties");
		ExtendDataModelInfo extendDataModelInfo = main.getExtendDataModelInfo();
		String fdTemplateId=main.getFdTemplate().getFdId();
		String hcTemplateId=proer.getProperty("sf.fdTemplateId.hc");
		String bxTemplateId=proer.getProperty("sf.fdTemplateId.bx");
		String apiKey="";
		Map<String, Object> map=extendDataModelInfo.getModelData();
		String sfdcid=(String) FormDataUtil.getValueByKey(map, "sfdcid");//sfdcid SF系统内的ID
		String oaId=main.getFdId();//oaId 流程ID
		String status=fdStatus;//Status "OA审批通过",OA审批状态：根据结果回传，OA审批拒绝、OA审批通过
		String oaRejecter=UserUtil.getUser().getFdName();//OA审批拒绝人
		JSONObject paramJson=new JSONObject();
		if(StringUtil.isNotNull(fdTemplateId)&&fdTemplateId.equals(hcTemplateId)){
			//外出申请回传流程id
			apiKey="sf.api.feedbackWork";
			paramJson.put("oaCode", oaId);
			paramJson.put("oaStatus", status);
			paramJson.put("sfdcid", sfdcid);
		}else if(StringUtil.isNotNull(fdTemplateId)&&fdTemplateId.equals(bxTemplateId)){
			apiKey="sf.api.feedbackExpenses";
			paramJson.put("status", status);
			paramJson.put("oaId", oaId);
			paramJson.put("sfdcid", sfdcid);
			paramJson.put("oaRejecter", oaRejecter);
		}else{
			throw new Exception("调用SF系统出错请联系管理员！缺少配置项");
		}
		String url=proer.getProperty("sf.url");
		String api=proer.getProperty(apiKey);
		url=url+api;
		
//		String param="sfdcid="+sfdcid+"&oaId="+oaId+"&Status="+Status+"&oaRejecter="+oaRejecter;
		String param=paramJson.toString();
//		String param="{\"sfdcid\":\"12345678\",\"oaCode\":\"222222\",\"oaStatus\":\"OA审批通过\"}";
		param=param.replace("=", ":");
		System.out.println("回调SF参数为："+param);
		String result=HttpRequest.sendPost(url,param, null);
		JSONObject json=JSON.parseObject(result);
		System.out.println("SF返回数据："+json);
 		String returnCode=json.getString("returnCode");
 		String returnMsg=json.getString("returnMsg");
 		if(!"0".equals(returnCode)){
 			throw new Exception("调用SF系统出错请联系管理员！提示："+returnMsg);
 		}
	}
public static void main(String[] args) {
//	String param="{\"sfdcid\":\"a0hO00000070l8wIAA\",\"oaCode\":\"222222\",\"oaStatus\":\"OA审批通过\"}";
//		String result=HttpRequest.sendPost("http://121.15.145.113:5388/fscLanling/feedbackWork",param, null);
		String param2="{\"sfdcid\":\"a0BO000000U0DYsMAN\",\"oaId\":\"184120da884511f589641bc4de89400b\",\"status\":\"OA审批通过\"}";
		String result2=HttpRequest.sendPost("http://121.15.145.113:5388/fscLanling/feedbackExpenses",param2, null);
		JSONObject json=JSON.parseObject(result2);
		System.out.println(json);
}
}
