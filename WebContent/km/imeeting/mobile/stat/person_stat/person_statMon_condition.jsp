<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="queryForm" value="${requestScope[param['formName']] }"/>
<div id="div_condtionSection">
	<table class="muiSimple" cellpadding="0" cellspacing="0">
		<tbody>
			<%--统计名称 --%>
			<tr>
				<td width="20%" class="muiTitle">
					<bean:message key="kmImeetingStat.fdName" bundle="km-imeeting"/>
				</td>
				<td width="80%">
					<xform:text property="fdName" style="width:80%" showStatus="${param.mode }" mobile="true"
					  	required="true" subject="${lfn:message('km-imeeting:kmImeetingStat.fdName')}">
					</xform:text>
					<c:if test="${not empty param.mode && param.mode == 'view' }">
						<input type="hidden" name="fdName" value="${queryForm.fdName}"/>
					</c:if>
					<input type="hidden" name="fdType" value="${fdType}"/>
				</td>
			</tr>
			<%--统计人员 --%>
			<tr>
				<td class="muiTitle">
					<bean:message key="kmImeetingStat.personScope" bundle="km-imeeting"/>
				</td>
				<td>
					<xform:address propertyId="queryCondIds" propertyName="queryCondNames" style="width:80%" mulSelect="true" 
						subject="${lfn:message('km-imeeting:kmImeetingStat.personScope')}" showStatus="${param.mode }" mobile="true"
				        orgType="ORG_TYPE_ALL"  textarea="false" required="true"></xform:address>
				    <c:if test="${not empty param.mode && param.mode == 'view' }">
						<input type="hidden" name="queryCondIds" value="${queryForm.queryCondIds}"/>
						<input type="hidden" name="queryCondNames" value="${queryForm.queryCondNames}"/>
					</c:if> 
				</td>
			</tr>
			<%--会议类型 --%>
			<tr>
				<td class="muiTitle">
					<bean:message key="kmImeetingMain.fdTemplate" bundle="km-imeeting"/>
				</td>
				<td>
					 <!-- TODO 编辑模式下JS -->
					 <xform:text property="fdTemplateName" style="width:80%" showStatus="${param.mode }" mobile="true"
					  	required="true" subject="${lfn:message('km-imeeting:kmImeetingMain.fdTemplate')}">
					 </xform:text>
					<input type="hidden" name="fdTemplateId" value="${queryForm.fdTemplateId}"/>
				</td>
			</tr>
			<%--阅读者 --%>
			<tr>
				<td class="muiTitle">
					<bean:message bundle="sys-right" key="right.read.authReaders" />
				</td>
				<td>
					<xform:address textarea="false" mulSelect="true" showStatus="${param.mode }" mobile="true"
						propertyId="authReaderIds" propertyName="authReaderNames" style="width:80%;">
					</xform:address>
					 <c:if test="${not empty param.mode && param.mode == 'view' }">
						<input type="hidden" name="authReaderIds" value="${queryForm.authReaderIds}"/>
						<input type="hidden" name="authReaderNames" value="${queryForm.authReaderNames}"/>
					</c:if>    
				</td>
			</tr>
			<c:import url="/km/imeeting/mobile/stat/common/timeArea.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="${JsParam.formName }"/>
			</c:import>
		</tbody>
	</table>	
	<input name="rowsize" type="hidden"/>
	<input name="pageno" type="hidden"/>
</div>