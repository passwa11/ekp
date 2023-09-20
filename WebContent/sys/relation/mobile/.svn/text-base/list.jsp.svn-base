<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="com.landray.kmss.sys.relation.util.SysRelationUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.ftsearch.config.LksField"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.sys.ftsearch.search.LksHit"%>
<%@page import="com.landray.kmss.common.model.BaseModel"%>
<%@page import="com.landray.kmss.sys.relation.model.SysRelationDoc"%>
<%@page import="com.landray.kmss.sys.relation.model.SysRelationMainDataPreview"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictCommonProperty"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
	prefix="person"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%!public int rtnType(Object obj) {
		// 高级搜索
		if (obj instanceof BaseModel)
			return 0;
		// 静态链接
		if (obj instanceof String[])
			return 1;
		// 全文搜索
		if (obj instanceof LksHit)
			return 2;
		// 文档关联
		if (obj instanceof SysRelationDoc)
			return 5;
		// 主数据  
		if (obj instanceof SysRelationMainDataPreview)
			return 7;
		return 0;
	}%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }" mobile="true">

		<%
			Object obj = pageContext.getAttribute("item");
			
			//System.out.println(propertyMap);
					int i = rtnType(obj);
					if (i == 0) {
						String modelName = obj.getClass().toString();
						modelName = modelName.substring(6,modelName.length());
						//System.out.println(modelName);
						Map<String, SysDictCommonProperty> propertyMap = null;
					 	if(!(modelName.indexOf("SysRelationDoc")>0)){
						 	SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
						 	if(null != dictModel){
					            propertyMap = dictModel.getPropertyMap();
						 	}
						}
		%>
		<list:data-column title="" col="label" escape="false">
			<%
			String[] fields = { "fdName", "fdSubject", "docSubject" };
			if(null!=propertyMap){
	            for (String field : fields) {
	                if (propertyMap.get(field) != null) {
	                    out.print((String) PropertyUtils.getSimpleProperty(obj, field));
	                    break;
	                }
	            }
			}
			%>
		</list:data-column>
		<list:data-column title="" col="href" escape="false">
			<c:if test="${not empty item }">
				<%=ModelUtil.getModelUrl(obj)%>
			</c:if>
		</list:data-column>

		<list:data-column title="" col="creator">
			<%	
            if (null !=propertyMap && propertyMap.get("docCreator") != null) {
                if(PropertyUtils.getSimpleProperty(obj, "docCreator")!=null){
                	Object docCreator = PropertyUtils.getSimpleProperty(obj, "docCreator");
                	out.print((String) PropertyUtils.getSimpleProperty(docCreator, "fdName")  );
                }
            }
	            
			%>
		</list:data-column>

		<list:data-column title="" col="created">
			<c:if test="${not empty item }">
				<%
				String[] fields = { "docCreateTime", "fdCreateTime" };
				if(null != propertyMap){
		            for (String field : fields) {
		                if (propertyMap.get(field) != null) {
		                	
		                    out.print(DateUtil.convertDateToString(
									(Date) PropertyUtils.getProperty(obj,
											field), ResourceUtil
											.getString("date.format.date")));
		                    break;
		                }
		            }
				}				
				%>
			</c:if>
		</list:data-column>

		<%
			} else if (i == 1) {
						String docSubject = "";
						String docLink = "";
						String[] urlArr = (String[]) obj;
						if (urlArr.length > 0
								&& StringUtil.isNotNull(urlArr[0])) {
							docSubject = urlArr[0];
							docSubject = SysRelationUtil
									.replaceStrongStyle(docSubject);
							if (urlArr.length > 1
									&& StringUtil.isNotNull(urlArr[1])) {
								docLink = urlArr[1];
							} else {
								docLink = urlArr[0];
							}
						}
						docSubject = StringUtil.XMLEscape(docSubject);
		%>
		<list:data-column title="" col="label">
			<%=docSubject%>
		</list:data-column>
		<list:data-column title="" col="href" escape="false">
			<%=docLink%>
		</list:data-column>
		<%
			} else if (i == 5) { // 文档关联
				String docSubject = "";
				String docLink = "";
				if(obj != null) {
					SysRelationDoc _doc = (SysRelationDoc)obj;
					docSubject = _doc.getDocSubject();
					docLink = _doc.getFdUrl();
				}
		%>
		<list:data-column title="" col="label">
			<%=docSubject%>
		</list:data-column>
		<list:data-column title="" col="href" escape="false">
			<%=docLink%>
		</list:data-column>
		<%
			} else if (i == 7) { // 主数据
				String docSubject = "";
				String docLink = "";
				if(obj != null) {
					SysRelationMainDataPreview _doc = (SysRelationMainDataPreview)obj;
					docSubject =  _doc.getFdMainDataName();
					docLink = _doc.getFdUrl();
				}
		%>
		<list:data-column title="" col="label">
			<%=docSubject%>
		</list:data-column>
		<list:data-column title="" col="href" escape="false">
			<%=docLink%>
		</list:data-column>
		<%
			} else if (i == 2) {

						LksHit lksHit = (LksHit) obj;
						Map lksFieldsMap = lksHit.getLksFieldsMap();
						if (lksFieldsMap != null && !lksFieldsMap.isEmpty()) {
		%>
		<list:data-column title="" col="label">
			<%
				String docSubject = "";
									LksField subject = (LksField) lksFieldsMap
											.get("subject");
									LksField title = (LksField) lksFieldsMap
											.get("title");
									LksField fileName = (LksField) lksFieldsMap
											.get("fileName");
									if (subject != null) {
										docSubject = subject.getValue();
									} else if (title != null) {
										docSubject = title.getValue();
									} else if (fileName != null) {
										docSubject = fileName.getValue();
									}
									if (StringUtil.isNotNull(docSubject)) {
										docSubject = SysRelationUtil
												.replaceStrongStyle(docSubject);
									}
									docSubject = StringUtil.XMLEscape(docSubject);
			%>
			<%=docSubject%>
		</list:data-column>
		<list:data-column title="" col="href" escape="false">
			<%
				String docLink = "";
									LksField link = (LksField) lksFieldsMap
											.get("linkStr");
									if (link != null) {
										docLink = link.getValue();
									}
			%>
			<%=docLink%>

		</list:data-column>

		<list:data-column title="" col="creator">
			<%
				String docCreatorName = "";

									LksField creator = (LksField) lksFieldsMap
											.get("creator");
									if (creator != null) {
										docCreatorName = SysRelationUtil
												.replaceStrongStyle(creator
														.getValue());
									}
			%>
			<%=docCreatorName%>
		</list:data-column>

		<list:data-column title="" col="created">
			<%
				String docCreateTime = "";

									LksField createTime = (LksField) lksFieldsMap
											.get("createTime");
									if (createTime != null) {
										docCreateTime = SysRelationUtil
												.replaceStrongStyle(createTime
														.getValue());
									}
			%>
			<%=docCreateTime%>
		</list:data-column>
		<%
			}
					}
		%>
	</list:data-columns>
</list:data>
