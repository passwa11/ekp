<%@ page import="com.landray.kmss.sys.modeling.base.result.model.ListviewResult"%>
<%@ page import="com.landray.kmss.common.model.IBaseModel"%>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="resultModel" list="${queryPage.list }" mobile="true">
		<list:data-column property="fdId" />
		<list:data-column col="listIcon" >
			muis-official-doc
		</list:data-column>
		<list:data-column headerStyle="display:none" style="display:none" col="staticsInfos" title="staticsInfos" escape="false" >
			<xform:xtext  dataType="String" value="${staticsInfos}" property="staticsInfos"/>
		</list:data-column>
		<%
			IBaseModel resultModel = (IBaseModel) pageContext.getAttribute("resultModel");
			ListviewResult searchResult = (ListviewResult) request.getAttribute("viewResultInfo");
			Iterator<ListviewResult> columnRowIter = searchResult.getColumnRowIter(resultModel);
			if(searchResult.getSearchModel().getFdViewFlag()){
		%>
				<list:data-column col="href" escape="false">
					/sys/modeling/main/mobile/modelingAppMainMobileView.do?method=modelView&fdId=${resultModel.fdId}&listviewId=${viewResultInfo.searchModel.fdMobileParentId}&tabId=${viewResultInfo.searchModel.fdId}
				</list:data-column>
		<%
			}
		%>
		<c:forEach items="<%=columnRowIter %>" var="columnRow" varStatus="colVstatus">
			<c:forEach items="${columnRow.columns}" var="viewResultColumn">
				<list:data-column col="${viewResultColumn.name }" escape="false">
					<c:choose>
						<c:when test="${(viewResultColumn.property.businessType == 'inputText' || viewResultColumn.property.businessType == 'calculation') && (viewResultColumn.property.type == 'BigDecimal' || viewResultColumn.property.type == 'Double') && viewResultColumn.propertyValue != '' && viewResultColumn.propertyValue != null}">
							<%--格式化单选框(金额)--%>
							<xform:number pattern="${viewResultColumn.pattern}" format="money" showChinese="false" value="${viewResultColumn.propertyValue}" property="${viewResultColumn.property.name}"/><c:if test="${fn:indexOf(viewResultColumn.property.customElementProperties, 'percent') != '-1'}">%</c:if>
						</c:when>
						<c:when test="${viewResultColumn.property.businessType == 'datetime' && viewResultColumn.property.type == 'Date' && viewResultColumn.property.dimension !=null}">
							<%--格式化日期维度--%>
							<xform:datetime dimension="${viewResultColumn.property.dimension}" dateTimeType="${viewResultColumn.property.type}" value="${viewResultColumn.propertyValue}" property="${viewResultColumn.property.name}"/>
						</c:when>
						<c:otherwise>
							<c:out value="${viewResultColumn.propertyValue}" escapeXml="true"></c:out>
						</c:otherwise>
					</c:choose>
				</list:data-column>
			</c:forEach>
		</c:forEach>
		<c:if test="${isWfMain}">
			<list:data-column col="summary" escape="false">
				<kmss:showWfPropertyValues var="nodeValue" idValue="${resultModel.fdId}" propertyName="nodeName" />
				${nodeValue}
				<kmss:showWfPropertyValues var="handlerValue" idValue="${resultModel.fdId}" propertyName="handlerName" />
				（${handlerValue}）
			</list:data-column>
		</c:if>
		<list:data-column headerStyle="display:none" style="display:none" col="group" title="group" escape="false" >
			<xform:xtext  dataType="String" value="${fdDefaultGroup}" property="group"/>
		</list:data-column>
		<list:data-column headerStyle="display:none" style="display:none" col="coverImg" title="coverImg" escape="false" >
			${coverImg}
		</list:data-column>
		<list:data-column headerStyle="display:none" style="display:none" col="summaryList" title="summaryList" escape="false" >
			${summaryList}
		</list:data-column>
		<list:data-column headerStyle="display:none" style="display:none" col="listviewId" title="listviewId" escape="false" >
			${listviewId}
		</list:data-column>
		<list:data-column headerStyle="display:none" style="display:none" col="subject" title="subject" escape="false" >
			${subject}
		</list:data-column>
		<list:data-column headerStyle="display:none" style="display:none" col="summaryFlag" title="summaryFlag" escape="false" >
			${summaryFlag}
		</list:data-column>
	</list:data-columns>

	<%-- 分页 --%>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>