<%@page import="com.landray.kmss.sys.modeling.base.result.model.ListviewResult"%>
<%@page import="com.landray.kmss.common.model.IBaseModel"%>
<%@ page import="com.landray.kmss.sys.modeling.base.constant.ModelingConstant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="resultModel" list="${queryPage.list }">
		<%
			IBaseModel resultModel = (IBaseModel) pageContext.getAttribute("resultModel");
			ListviewResult searchResult = (ListviewResult) request.getAttribute("viewResultInfo");
			request.setAttribute("fdHandler", ModelingConstant.LBPMEXPECTERLOG_FDHANDLER);
			request.setAttribute("fdNode", ModelingConstant.LBPMEXPECTERLOG_FDNODE);
		%>
		<c:forEach items="<%=searchResult.getColumnRowIter(resultModel) %>" var="columnRow" varStatus="colVstatus">
			<c:forEach items="${columnRow.columns}" var="viewResultColumn">
				<c:if test="${colVstatus.index == 0 or viewResultColumn.rowSpan == 1}">
					<c:set var="colWidthStyle" value=""/>
					<c:if test="${viewResultColumn.width > 0}">
						<c:set var="colWidthStyle" value="width:${viewResultColumn.width}px"/>
					</c:if>
					<list:data-column col="${viewResultColumn.property.name }" style="${colWidthStyle }" title="${viewResultColumn.label}" escape="false">
						<c:choose>
							<c:when test="${viewResultColumn.property.name == fdHandler}">
								<%--当前处理人--%>
								<kmss:showWfPropertyValues var="handlerValue" idValue="${resultModel.fdId}" propertyName="handlerName" />
								${handlerValue}
							</c:when>
							<c:when test="${viewResultColumn.property.name == fdNode}">
								<%--当前环节--%>
								<kmss:showWfPropertyValues var="nodeValue" idValue="${resultModel.fdId}" propertyName="nodeName" />
								${nodeValue}
							</c:when>
							<c:when test="${(viewResultColumn.property.businessType == 'inputText' || viewResultColumn.property.businessType == 'calculation') && (viewResultColumn.property.type == 'BigDecimal' || viewResultColumn.property.type == 'Double') && viewResultColumn.propertyValue != '' && viewResultColumn.propertyValue != null}">
								<%--格式化单选框(金额)--%>
								<xform:number pattern="${viewResultColumn.pattern}" format="money" showChinese="false" value="${viewResultColumn.propertyValue}" property="${viewResultColumn.property.name}"/>
							</c:when>
							<c:when test="${viewResultColumn.property.businessType == 'inputText' && viewResultColumn.displayFormat != '' && viewResultColumn.propertyValue != '' && viewResultColumn.propertyValue != null}">
								<%--格式化单选框--%>
								<xform:xtext displayFormat="${viewResultColumn.displayFormat}" dataType="${viewResultColumn.property.type}" value="${viewResultColumn.propertyValue}" property="${viewResultColumn.property.name}"/>
							</c:when>
							<%--日期控件格式化已在后台处理，该处删除--%>
<%--							<c:when test="${viewResultColumn.property.businessType == 'datetime' && viewResultColumn.property.type == 'Date' && viewResultColumn.property.dimension !=null}">--%>
<%--								&lt;%&ndash;格式化日期维度&ndash;%&gt;--%>
<%--								<xform:datetime dimension="${viewResultColumn.property.dimension}" dateTimeType="${viewResultColumn.property.type}" value="${viewResultColumn.propertyValue}" property="${viewResultColumn.property.name}"/>--%>
<%--							</c:when>--%>
							<c:when test="${viewResultColumn.property.businessType == 'textarea' && viewResultColumn.propertyValue != '' && viewResultColumn.propertyValue != null}">
								<%--格式化多行文本--%>
								<span class="listViewTextOverflow">
									<c:out value="${viewResultColumn.propertyValue}" escapeXml="true"></c:out>
								</span>
							</c:when>
							<c:otherwise>
								<%--其它列正常输出--%>
								<c:out value="${viewResultColumn.propertyValue}" escapeXml="true"></c:out>
							</c:otherwise>
						</c:choose>
					</list:data-column>
				</c:if>
			</c:forEach>
		</c:forEach>

		<c:forEach items="${attachmentArr}" var="attachment">
			<c:if test="${attachment.fdId eq resultModel.fdId}">
				<c:set var="colWidthStyle" value=""/>
				<c:if test="${attachment.width > 0}">
					<c:set var="colWidthStyle" value="width:${attachment.width}px"/>
				</c:if>
				<list:data-column  style="min-width:268px;${colWidthStyle }" col="${attachment.field}" title="${attachment.text}" escape="false">
					<c:forEach items="${attachment.attMainList}" var="attMain" varStatus="colAttMain">
						<c:if test="${colAttMain.index <= 1}" >
							<div class="listViewAttachmentDiv" onclick="jumpToAttachmentDetail(this)" attMainId="${attMain.fdId}" >
								<i class="listViewAttachmentDiv-i-icon" style="background-image: url(../../attachment/view/img/file/2x/${attachment.attIconList[colAttMain.index]});"></i>
								<i class="listViewAttachmentDiv-i-text" title="${attMain.fdFileName}">${attMain.fdFileName}</i>
							</div>
						</c:if>
						<c:if test="${(fn:length(attachment.attMainList)) == 3 && colAttMain.index == 2 }" >
							<div class="listViewAttachmentDiv" onclick="jumpToAttachmentDetail(this)" attMainId="${attMain.fdId}" >
								<i class="listViewAttachmentDiv-i-icon" style="background-image: url(../../attachment/view/img/file/2x/${attachment.attIconList[colAttMain.index]});"></i>
								<i class="listViewAttachmentDiv-i-text" title="${attMain.fdFileName}">${attMain.fdFileName}</i>
							</div>
						</c:if>
						<c:if test="${(fn:length(attachment.attMainList)) > 3 && colAttMain.index == 2 }" >
							<div  class="listViewAttachmentDiv" onclick="showMoreAttachment(this,<c:out value="${attachment.attMainListStr}" escapeXml="true"></c:out>,<c:out value="${attachment.attIconListStr}" escapeXml="true"></c:out>)">
								<i class="listViewAttachmentDiv-i-text">${ lfn:message('sys-modeling-main:collectionListView.attachment.morePrefix') }<c:out value="${(fn:length(attachment.attMainList)) - 2}" escapeXml="true"></c:out>${ lfn:message('sys-modeling-main:collectionListView.attachment.suffix') }</i>
							</div>
						</c:if>
					</c:forEach>
				</list:data-column>
			</c:if>
		</c:forEach>

		<list:data-column headerStyle="display:none" style="display:none" col="staticsInfos" title="staticsInfos" escape="false" >
			<xform:xtext  dataType="String" value="${staticsInfos}" property="staticsInfos"/>
		</list:data-column>
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

		<list:data-column property="fdId" title="ID" />
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>