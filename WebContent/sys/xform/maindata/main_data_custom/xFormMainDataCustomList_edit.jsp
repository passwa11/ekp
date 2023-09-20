<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="sysFormMainDataCustomForm" value="${requestScope[param.formName]}" scope="request"></c:set>
<table class="tb_normal xform_maindata_datalist" width=100% id="${param.tableKey }" align="center" <c:if test="${param.hasSuper eq 'true' }">data-hasSuper="true"</c:if> >
	<tr align="center" invalidrow="true">
		<%--序号--%> 
		<td width="5%" style="min-width: 50px;"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.index"/></td>
		<%--上级 --%>
		<c:if test="${param.hasSuper eq 'true' }">
			<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.super"/></td>
		</c:if>
		<%-- 显示值 --%>
		<td width="35%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.showValue"/></td>
		<%--实际值 --%>
		<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.realValue"/></td>
		<%--排序号--%>
		<td width="10%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.order"/></td>
		<%--操作栏 --%>
		<td width="5%" style="min-width: 45px;">
			<a href="javascript:void(0);" onclick="xform_main_data_addrow();" style="color:#1b83d8;">
				<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.add"/>
			</a>
		</td>
	</tr>
	<tr KMSS_IsReferRow="1" style="display: none">
		<td KMSS_IsRowIndex="1" width="5%" align="center"></td>
		<c:if test="${param.hasSuper eq 'true' }">
			<td width="22.5%" align="center">
				<div class="xform_main_data_custom_super">
					<select name="sysFormMainDataCustomList[!{index}].fdCascadeId">
						
					</select>
				</div>
			</td>
		</c:if>
		
		<td width="35%">
			<xform:text property="sysFormMainDataCustomList[!{index}].fdValueText" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.showValue') }" style="width:80%;" required="true"></xform:text>
		</td>
		<td width="22.5%">
			<xform:text property="sysFormMainDataCustomList[!{index}].fdValue" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.realValue') }" style="width:80%;" required="true"></xform:text>
		</td>
		<td width="10%">
			<xform:text property="sysFormMainDataCustomList[!{index}].fdOrder" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.order') }" style="width:80%;" validators="digits min(0)"></xform:text>
		</td>
		<%--操作栏 --%>
		<td align="center">
			<a href="javascript:void(0);" onclick="DocList_DeleteRow();" style="color:#1b83d8;">
				<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.del"/>
			</a>
			<input type="hidden" name="sysFormMainDataCustomList[!{index}].fdId" />
		</td>
	</tr>
	<c:if test="${param.listData eq true }">
		<c:forEach items="${sysFormMainDataCustomForm.sysFormMainDataCustomList}" var="sysFormMainDataCustomListForm" varStatus="vstatus">
			<tr KMSS_IsContentRow="1">
				<td width="5%" align="center">${vstatus.index+1}</td>
				<c:if test="${param.hasSuper eq 'true' }">
					<td width="22.5%" align="center">
						<div class="xform_main_data_custom_super">
							<select name="sysFormMainDataCustomList[${vstatus.index}].fdCascadeId">
								${sysFormMainDataCustomListForm.fdOptionsHtml }
							</select>
						</div>
					</td>
				</c:if>
				<td>
					<xform:text property="sysFormMainDataCustomList[${vstatus.index}].fdValueText" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.showValue') }" style="width:80%;" required="true"></xform:text>
				</td>
				<td>
					<xform:text property="sysFormMainDataCustomList[${vstatus.index}].fdValue" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.realValue') }" style="width:80%;" required="true"></xform:text>
				</td>
				<td>
					<xform:text property="sysFormMainDataCustomList[${vstatus.index}].fdOrder" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.order') }" style="width:80%;" validators="digits min(0)"></xform:text>
				</td>
				<%--操作栏 --%>
				<td align="center">
					<a href="javascript:void(0);" onclick="DocList_DeleteRow();" style="color:#1b83d8;">
						<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.del"/>
					</a>
					<html:hidden property="sysFormMainDataCustomList[${vstatus.index}].fdId" />
				</td>
			</tr>
		</c:forEach>
	</c:if>
</table>