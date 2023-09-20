<%@page import="com.landray.kmss.sys.relation.model.SysRelationMain"%>
<%@page import="com.landray.kmss.sys.log.util.UserOperHelper"%>
<%@page import="com.landray.kmss.sys.log.util.oper.UserOperContentHelper"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="com.landray.kmss.sys.relation.util.SysRelationUtil"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.landray.kmss.sys.relation.interfaces.SearchResultEntry"%>
<%@page import="com.landray.kmss.sys.ftsearch.search.LksHit"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.sys.ftsearch.config.LksField"%>
<%@page import="com.sunbor.web.tag.Page"%>
<%@page import="com.landray.kmss.common.model.BaseModel"%>
<%@page import="com.landray.kmss.sys.relation.model.SysRelationDoc"%>
<%@page import="com.landray.kmss.sys.relation.model.SysRelationMainDataPreview"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

<%
	Object queryPage = request.getAttribute("queryPage");
	if(queryPage != null){
		int totalPage = ((Page) queryPage).getTotal();
		List queryList= ((Page) queryPage).getList();
		JSONArray rtnJsonArr = new JSONArray();
		
		Boolean allow = UserOperHelper.allowLogOper("preview", SysRelationMain.class.getName());
		for(int i=0; i<queryList.size(); i++){
			Object resultModel = queryList.get(i);
			String catename = "";
			String docSubject = "";
			String linkUrl = "";
			String imgUrl = "";
			Date date = null;
			String docCreateTime = "";
			String docCreatorName = "";
			String docCreateInfo = "";
			if (resultModel instanceof BaseModel) {		//精确搜索
				try {
					try {
						docSubject = (String) PropertyUtils.getProperty(resultModel, "docSubject");
					} catch (Exception e){
						docSubject = "";
					}
					if(StringUtil.isNull(docSubject)) {
						try {
							docSubject = (String) PropertyUtils.getProperty(resultModel, "fdName");
						} catch(Exception e) {}
					}
					if (StringUtil.isNotNull(ModelUtil.getModelUrl(resultModel))) {
						linkUrl = ModelUtil.getModelUrl(resultModel);
					}
					try {
						date = (Date) PropertyUtils.getProperty(resultModel, "docCreateTime");
					} catch (Exception e) {
						date = null;
					}
					if (date != null) {
						docCreateTime = DateUtil.convertDateToString(date, ResourceUtil
								.getString("date.format.date"));
					}
					try {
						docCreatorName = (String) PropertyUtils.getProperty(resultModel, "docCreator.fdName");
					} catch (Exception e) {
						docCreatorName = "";
					}
				} catch (Exception e){
				}
			}else if (resultModel instanceof String[]) { //静态页面，人员关联配置czk2019
				String[] urlArr = (String[])resultModel;
				if (urlArr.length > 0 && StringUtil.isNotNull(urlArr[0])) {
					docSubject = urlArr[0];
					if (urlArr.length > 1 && StringUtil.isNotNull(urlArr[1])) {
						linkUrl = urlArr[1];
						//自定义图标展示
						if (urlArr.length > 2 && StringUtil.isNotNull(urlArr[2])) {
							imgUrl = urlArr[2];
						}
					} else {
						linkUrl = urlArr[0];
					}
				}
			}else if (resultModel instanceof HashMap) { //文本页面
				HashMap<Integer,String> text = (HashMap<Integer,String>)resultModel;
				if (StringUtil.isNotNull(text.get(0))) {
					
					docSubject = text.get(0);
					
				}
			}else if (resultModel instanceof LksHit) { //全文检索
				LksHit lksHit = (LksHit)resultModel;
				if (lksHit != null) {
					Map lksFieldsMap = lksHit.getLksFieldsMap();
					if (lksFieldsMap != null && !lksFieldsMap.isEmpty()) {
						LksField subject = (LksField)lksFieldsMap.get("subject");
						LksField title = (LksField)lksFieldsMap.get("title");
						LksField fileName = (LksField)lksFieldsMap.get("fileName");
						if (subject != null) {
							docSubject = subject.getValue();
						} else if (title != null) {
							docSubject = title.getValue();
						} else if (fileName != null) {
							docSubject = fileName.getValue();
						}
						LksField link = (LksField)lksFieldsMap.get("linkStr");
						if (link != null) {
							linkUrl = link.getValue();
						}
						LksField createTime = (LksField)lksFieldsMap.get("createTime");
						if (createTime != null) {
							docCreateTime = createTime.getValue();
						}
						LksField creator = (LksField)lksFieldsMap.get("creator");
						if (creator != null) {
							docCreatorName = creator.getValue();
						}
						
						// 去掉样式
						if (StringUtil.	isNotNull(docSubject)) {
							docSubject = SysRelationUtil.replaceStrongStyle(docSubject);
						}
						if (StringUtil.isNotNull(docCreateTime)) {
							docCreateTime = SysRelationUtil.replaceStrongStyle(docCreateTime);
						}
						if (StringUtil.isNotNull(docCreatorName)) {
							docCreatorName = SysRelationUtil.replaceStrongStyle(docCreatorName);
						}
					}
				}
			} else if (resultModel instanceof SearchResultEntry) { //外部扩展
				SearchResultEntry searchModel = (SearchResultEntry)resultModel;
				if (searchModel != null) {
					docSubject = searchModel.getDocSubject();
					linkUrl = searchModel.getLinkUrl();
					if (searchModel.getDocCreateTime() != null) {
						docCreateTime = DateUtil.convertDateToString(searchModel.getDocCreateTime(),ResourceUtil
								.getString("date.format.date"));
					}
					docCreatorName = searchModel.getDocCreatorName();
				}
			} else if (resultModel instanceof SysRelationDoc) { //文档关联
				SysRelationDoc doc = (SysRelationDoc)resultModel;
				if (doc != null) {
					docSubject = doc.getDocSubject();
					linkUrl = doc.getFdUrl();
					if (doc.getDocCreateTime() != null) {
						docCreateTime = DateUtil.convertDateToString(doc.getDocCreateTime(),ResourceUtil
								.getString("date.format.date"));
					}
					docCreatorName = doc.getDocCreatorName();
				}
			} else if (resultModel instanceof SysRelationMainDataPreview) { // 主数据
				SysRelationMainDataPreview mainData = (SysRelationMainDataPreview)resultModel;
				if (mainData != null) {
					catename = mainData.getDocSubject();
					linkUrl = mainData.getFdUrl();
					docSubject = mainData.getFdMainDataName();
				}
			}
			JSONObject tmpObject = new JSONObject();
			if(docSubject != null){
				tmpObject.accumulate("text",docSubject.trim());
			}else{
				tmpObject.accumulate("text","");
			}
			if(linkUrl != null){
				tmpObject.accumulate("href",linkUrl.trim());
			}else{
				tmpObject.accumulate("href","");
			}
			//czk2019
			if(StringUtil.isNotNull(imgUrl)){
				tmpObject.accumulate("icon",imgUrl.trim());
			}else{
				tmpObject.accumulate("icon","");
			}
			if (StringUtil.isNotNull(catename)){
				tmpObject.accumulate("catename",catename);
			}
			if (StringUtil.isNotNull(docCreateTime)){
				tmpObject.accumulate("created",docCreateTime);
			}
			if (StringUtil.isNotNull(docCreatorName)){
				tmpObject.accumulate("creator",docCreatorName);
			}
			tmpObject.accumulate("totalPage",totalPage);
			
			if(allow){
				UserOperContentHelper.putFind("", tmpObject.getString("text"), null);
			}
				
			Object chartUrl = request.getAttribute("chartUrl");
			if(chartUrl != null){
				tmpObject.accumulate("chartUrl",chartUrl);
			}
			rtnJsonArr.element(tmpObject);
		}
		out.print(rtnJsonArr.toString());
	}else{
		JSONArray rtnJsonArr = new JSONArray();
		JSONObject tmpObject = new JSONObject();
		String info=ResourceUtil.getString("sysRelationMain.helptips9","sys-relation");
		tmpObject.accumulate("text",info);
		Object chartUrl = request.getAttribute("chartUrl");
		if(chartUrl != null){
			tmpObject.accumulate("chartUrl",chartUrl);
		}
		rtnJsonArr.element(tmpObject);
		out.print(rtnJsonArr.toString());
	}
%>
