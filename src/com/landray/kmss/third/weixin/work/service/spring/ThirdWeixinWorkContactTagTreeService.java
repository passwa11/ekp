package com.landray.kmss.third.weixin.work.service.spring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.sys.portal.cloud.util.PortletConstants;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ThirdWeixinWorkContactTagTreeService implements IXMLDataBean {

	@Override
    public List<Map<String, Object>> getDataList(RequestContext xmlContext)
			throws Exception {
		String group_id = xmlContext.getParameter("group_id");
		String node_type = xmlContext.getParameter("node_type");
		String multi = xmlContext.getParameter("multi");
		if("tag".equals(node_type)){
			return new ArrayList<>();
		}
		if(StringUtil.isNotNull(group_id)){
			group_id = group_id.substring(6);
			return getTags(group_id);
		}else{
			return getTagGroups(multi);
		}
	}

	private List<Map<String, Object>> getTagGroups(String multi) throws Exception {
		String baseBeanName = "thirdWeixinWorkContactTagTreeService";
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
		JSONObject obj = wxworkApiService.listCorpTag(null,null);
		if(obj!=null && obj.getIntValue("errcode")==0){
			JSONArray tagGroups = obj.getJSONArray("tag_group");
			for(int i=0;i<tagGroups.size();i++){
				JSONObject tagGroup = tagGroups.getJSONObject(i);
				String group_id = tagGroup.getString("group_id");
				String group_name = tagGroup.getString("group_name");
				String beanName = baseBeanName + "&s_seq=" + IDGenerator.generateID()+"&group_id=group_"+group_id;
				HashMap<String, Object> node = new HashMap<String, Object>();
				node.put("text", group_name);
				node.put("value", "group_"+group_id);
				node.put("nodeType", "GROUP");
				node.put("beanName", beanName);
				if("false".equals(multi)) {
					node.put("isShowCheckBox", false);
				}
				rtnList.add(node);
			}
		}
		return rtnList;
	}

	private List<Map<String, Object>> getTags(String group_id) throws Exception {
		String baseBeanName = "thirdWeixinWorkContactTagTreeService";
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
		JSONObject obj = wxworkApiService.listCorpTag(group_id,null);
		if(obj!=null && obj.getIntValue("errcode")==0){
			JSONArray tagGroups = obj.getJSONArray("tag_group");
			for(int i=0;i<tagGroups.size();i++){
				JSONObject tagGroup = tagGroups.getJSONObject(i);
				JSONArray tags = tagGroup.getJSONArray("tag");
				for(int j=0;j<tags.size();j++){
					JSONObject tag = tags.getJSONObject(j);
					String tag_id = tag.getString("id");
					String tag_name = tag.getString("name");
					HashMap<String, Object> node_tag = new HashMap<String, Object>();
					String beanName = baseBeanName + "&s_seq=" + IDGenerator.generateID()+"&node_type=tag";
					node_tag.put("text", tag_name);
					node_tag.put("value", tag_id);
					node_tag.put("nodeType", "TAG");
					node_tag.put("beanName", beanName);
					rtnList.add(node_tag);
				}
			}
		}
		return rtnList;
	}

}
