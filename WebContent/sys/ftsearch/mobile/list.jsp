<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.ftsearch.search.LksHit"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.ftsearch.config.LksField"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="com.landray.kmss.sys.relation.util.SysRelationUtil"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }" mobile="true">
		<%
			Object basedocObj = pageContext.getAttribute("item");
					if (basedocObj != null) {
						LksHit lks = (LksHit) basedocObj;
						String existPersonName = lks.getExistPeronName();
						pageContext.setAttribute("existPersonName",existPersonName);
						
						Map lksFieldsMap = lks.getLksFieldsMap();
						LksField docKeyField = (LksField) lksFieldsMap.get("docKey");
						String docKey = docKeyField.getValue();
						LksField link = (LksField) lksFieldsMap.get("linkStr");
						LksField title = (LksField) lksFieldsMap.get("title");
						LksField subject = (LksField) lksFieldsMap
								.get("subject");
						LksField content = (LksField) lksFieldsMap
								.get("content");
						LksField fileName = (LksField) lksFieldsMap
								.get("fileName");
						LksField creator = (LksField) lksFieldsMap
								.get("creator");
						LksField createTime = (LksField) lksFieldsMap
								.get("createTime");
						LksField fullText = (LksField)lksFieldsMap
								.get("fullText");
						LksField modelNameField = (LksField)lksFieldsMap.get("modelName");
						String modelName = null;
						if(modelNameField != null){
							modelName = modelNameField.getValue();
						}
						String fdDocSubject = "";
						String linkUrl = "";
						String docCreator = "";
						String docCreateTime = "";
						String fdContent = "";
						String docSummary = "";
						if (subject != null) {
							fdDocSubject = subject.getValue();
						} else if (title != null) {
							fdDocSubject = title.getValue();
						} else if (fileName != null) {
							fdDocSubject = fileName.getValue();
						}
						String fullDocSubject = fdDocSubject;
						if (creator != null)
							docCreator = creator.getValue();
						if (createTime != null)
							docCreateTime = createTime.getValue();
						
						if(content != null) {
							docSummary = content.getValue();
						} else if(fullText != null) {
							docSummary = fullText.getValue();
						}
						if(StringUtil.isNotNull(fdDocSubject)) {
							fdDocSubject = fdDocSubject.replaceAll("<font[^>]*>", "[red]").replaceAll("</font>", "[/red]");
						}
						if(StringUtil.isNotNull(docSummary)) {
							docSummary = docSummary.replaceAll("<font[^>]*>", "[red]").replaceAll("</font>", "[/red]");
						}
						if(StringUtil.isNotNull(docCreator)) {
							docCreator = docCreator.replaceAll("<font[^>]*>", "[red]").replaceAll("</font>", "[/red]");
						}
						if (link != null) {
							linkUrl = link.getValue();
						}
						
						if("true".equals(existPersonName)){
							List<Map<String,String>> personSearchs = (List<Map<String,String>>)request.getAttribute("personSearchs");
							for (Map<String, String> personMap : personSearchs) {
								if (personMap.get("module") != null
										&& personMap.get("module").equals(
												modelName)) {
									// 头像链接
									String docId = docKey.substring(docKey
											.lastIndexOf("_") + 1);
									pageContext.setAttribute("headIcon", personMap.get("path")
											+ "&fdId=" + docId + "&size=b");
									// 字段名
									for (int a = 1; a <= 7; a++) {
										pageContext.setAttribute("addField" + a + "Desc",
												personMap.get("addFieldName" + a));
										if (lksFieldsMap.get("addField" + a) != null) {
											String value = ((LksField)lksFieldsMap.get("addField" + a)).getValue();
											value = value.replaceAll("<font[^>]*>", "[red]").replaceAll("</font>", "[/red]");
											pageContext.setAttribute("addField" + a, value);
										}else{
											pageContext.setAttribute("addField" + a,"");
										}
									}

									break;
								}
							}
						}
						
						
						pageContext.setAttribute("fdDocSubject", fdDocSubject);
						pageContext.setAttribute("linkUrl", linkUrl);
						pageContext.setAttribute("docCreator", docCreator);
						pageContext.setAttribute("docCreateTime", docCreateTime);
						pageContext.setAttribute("docSummary", docSummary);
					}
		%>
		<list:data-column col="isPersonName" escape="false">
			${existPersonName}
		</list:data-column>
		<list:data-column col="href" escape="false">
			${linkUrl}
		</list:data-column>
		<list:data-column col="label" escape="false">
			${fdDocSubject}
		</list:data-column>
		<list:data-column col="creator" escape="false">
			${docCreator}
		</list:data-column>
		<list:data-column col="created">
			${docCreateTime}
		</list:data-column>
		<list:data-column col="summary" escape="false">
			${docSummary}
		</list:data-column>
		<%if("true".equals(pageContext.getAttribute("existPersonName"))){%>
			<list:data-column col="headIcon" escape="false">
				${headIcon}
			</list:data-column>
			<list:data-column col="addField1" escape="false">
				${addField1}
			</list:data-column>
			<list:data-column col="addField2" escape="false">
				${addField2}
			</list:data-column>
			<list:data-column col="addField3" escape="false">
				${addField3}
			</list:data-column>
			<list:data-column col="addField4" escape="false">
				${addField4}
			</list:data-column>
			<list:data-column col="addField5" escape="false">
				${addField5}
			</list:data-column>
			<list:data-column col="addField6" escape="false">
				${addField6}
			</list:data-column>
			<list:data-column col="addField7" escape="false">
				${addField7}
			</list:data-column>
			
			<list:data-column col="addField1Desc" escape="false">
				${addField1Desc}
			</list:data-column>
			<list:data-column col="addField2Desc" escape="false">
				${addField2Desc}
			</list:data-column>
			<list:data-column col="addField3Desc" escape="false">
				${addField3Desc}
			</list:data-column>
			<list:data-column col="addField4Desc" escape="false">
				${addField4Desc}
			</list:data-column>
			<list:data-column col="addField5Desc" escape="false">
				${addField5Desc}
			</list:data-column>
			<list:data-column col="addField6Desc" escape="false">
				${addField6Desc}
			</list:data-column>
			<list:data-column col="addField7Desc" escape="false">
				${addField7Desc}
			</list:data-column>
	   <%}%>
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
