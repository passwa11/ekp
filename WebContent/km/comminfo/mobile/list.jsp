<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.util.*,java.util.List,net.sf.json.*,com.landray.kmss.km.comminfo.model.KmComminfoMain,com.sunbor.web.tag.Page,org.apache.commons.lang.StringEscapeUtils"%>
<%
	JSONArray jsonArr = new JSONArray();
	List countList = (List)request.getAttribute("countList");
	Page queryPage = (Page)request.getAttribute("queryPage");
	List<KmComminfoMain> queryList = queryPage.getList();

	for (int i = 0; i < countList.size(); i++) {
		Object[] result = (Object[]) countList.get(i);
		String count = result[0].toString();
		String cateId = result[1] != null ? result[1].toString() : "";
		String fdCateName = "";
		
		JSONArray children = new JSONArray();
		for (int j = 0; j < queryList.size(); j++) {
			KmComminfoMain kmComminfoMain = queryList.get(j);
			if (kmComminfoMain.getDocCategory() != null && cateId.equals(kmComminfoMain.getDocCategory().getFdId())) {
				JSONObject chl = new JSONObject();
				chl.accumulate("label", StringEscapeUtils.escapeXml(kmComminfoMain.getDocSubject()));
				chl.accumulate("created", DateUtil.convertDateToString(kmComminfoMain.getDocCreateTime(),DateUtil.TYPE_DATE,request.getLocale()));
				chl.accumulate("creator", kmComminfoMain.getDocCreator().getFdName());
				chl.accumulate("fdId", kmComminfoMain.getFdId());
				children.add(chl);
	
				fdCateName = StringUtil.isNull(fdCateName) ? StringEscapeUtils.escapeXml(kmComminfoMain.getDocCategory().getFdName()): fdCateName;
			}
		}
		JSONArray recordArray = new JSONArray();
		
		JSONObject labelObj = new JSONObject();
		labelObj.accumulate("col", "label");
		labelObj.accumulate("value", fdCateName);
		recordArray.add(labelObj);
		
		JSONObject countObj = new JSONObject();
		countObj.accumulate("col", "count");
		countObj.accumulate("value", count);
		recordArray.add(countObj);

		JSONObject childrenObj = new JSONObject();
		childrenObj.accumulate("col", "childrens");
		childrenObj.accumulate("value", children);
		recordArray.add(childrenObj);

		jsonArr.add(recordArray);
	}
	
	JSONArray columnsArr = new JSONArray();
	JSONObject column1 = new JSONObject();
	column1.accumulate("property", "label");
	columnsArr.add(column1);
	JSONObject column2 = new JSONObject();
	column2.accumulate("property", "count");
	columnsArr.add(column2);
	JSONObject column3 = new JSONObject();
	column3.accumulate("property", "childrens");
	columnsArr.add(column3);
	
	JSONObject paging = new JSONObject();
	paging.accumulate("currentPage",queryPage.getPageno());
	paging.accumulate("pageSize",queryPage.getRowsize());
	paging.accumulate("totalSize",queryPage.getTotalrows());
	JSONObject items = new JSONObject();
	items.accumulate("columns",columnsArr);
	items.accumulate("datas",jsonArr);
	items.accumulate("page",paging);
	pageContext.getOut().append(items.toString());
%>