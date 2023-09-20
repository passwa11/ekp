<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
	<tr LKS_LabelName="<c:if test="${empty param.cateTitle}"><bean:message bundle="sys-simplecategory"
		key="table.sysSimpleCategory" /></c:if><c:if test="${not empty param.cateTitle}">${HtmlParam.cateTitle}</c:if>">
		<td>
			<table class="tb_normal" width="100%" ${HtmlParam.styleValue}>
		<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_body.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="${param.formName}" />
			<c:param name="requestURL" value="${param.requestURL}" />
			<c:param name="fdModelName" value="${param.fdModelName}" />
			<c:param name="fdParentName" value="${param.fdParentName}" />
			<c:param name="fdName" value="${param.fdName}" />
			<c:param name="fdParentNameStr" value="${param.fdParentNameStr}" />
			<c:param name="fdNameStr" value="${param.fdNameStr}" />
			<c:param name="titleKey" value="${param.titleKey}" />
		</c:import>
		<c:if test="${param.applyTheChanges == 'simple'}">
			<c:if test="${requestScope[param.formName].method_GET!='add' }">	
				<tr>
					<td
						class="td_normal_title"
						width="15%">
						<bean:message bundle="sys-simplecategory" key="sysSimpleCategory.info" />
		</td>
					<td colspan=3>
						 <input type='checkbox' name="appToChildren"  value='appToChildren'/> 
		     <bean:message bundle="sys-simplecategory" key="sysSimpleCategory.sub.set" />
					</td>
				</tr>
			</c:if>
		</c:if>
			</table>
		</td>
	</tr>
