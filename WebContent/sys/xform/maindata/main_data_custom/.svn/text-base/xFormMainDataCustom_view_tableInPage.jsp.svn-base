<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="sysFormMainDataCustomForm" value="${requestScope[param.formName]}" scope="request"></c:set>
	<tr align="center">
		<%--序号--%> 
		<td width="5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.index"/></td>
		<%--上级 --%>
		<c:if test="${sysFormMainDataCustomForm.isCascade eq 'true' }">
			<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.super"/></td>
		</c:if>
		<%-- 显示值 --%>
		<td width="35%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.showValue"/></td>
		<%--实际值 --%>
		<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.realValue"/></td>
		<%--排序号--%>
		<td width="5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.order"/></td>
		<%--操作 --%>
		<td width="5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.operation"/></td>
	</tr>
	<tr KMSS_IsReferRow="1" style="display: none">
		<td KMSS_IsRowIndex="1" width="5%" align="center"></td>
		<c:if test="${sysFormMainDataCustomForm.isCascade eq 'true' }">
				<td width="22.5%">
					<!-- 先不考虑多语言 -->
					<div class="xform_main_data_custom_super">
						<xform:text showStatus="readOnly" property="sysFormMainDataCustomList[!{index}].fdCascadeName" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.super') }" className="xform_main_data_custom_input"></xform:text>
						<input type="hidden" name="sysFormMainDataCustomList[!{index}].fdCascadeId" />
					</div>
				</td>
			</c:if>
		
		<td>
			<xform:text showStatus="readOnly" property="sysFormMainDataCustomList[!{index}].fdValueText" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.showValue') }" className="xform_main_data_custom_input"></xform:text>
		</td>
		<td>
			<xform:text showStatus="readOnly" property="sysFormMainDataCustomList[!{index}].fdValue" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.realValue') }" className="xform_main_data_custom_input"></xform:text>
		</td>
		<td>
			<xform:text showStatus="readOnly" property="sysFormMainDataCustomList[!{index}].fdOrder" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.order') }" className="xform_main_data_custom_input"></xform:text>
			<input type="hidden" name="sysFormMainDataCustomList[!{index}].fdId" />
		</td>
		<td align="center">
			<a href="javascript:void(0);" onclick="xform_main_data_custom_editDiv_show(this);" style="color:#1b83d8;">
				<bean:message key="button.edit"/>
			</a>
			<a href="javascript:void(0);" onclick="xform_main_data_custom_del(this);" style="color:#1b83d8;">
				<bean:message key="button.delete"/>
			</a>
		</td>
	</tr>
	<!-- 
	<c:forEach items="${sysFormMainDataCustomForm.sysFormMainDataCustomList}"  var="sysFormMainDataCustomListForm" varStatus="vstatus">
		<tr KMSS_IsContentRow="1">
			<td width="5%" align="center">${vstatus.index+1}
			</td>
			<c:if test="${sysFormMainDataCustomForm.isCascade eq 'true' }">
				<td width="22.5%">
					<div class="xform_main_data_custom_super">
						<xform:text showStatus="readOnly" property="sysFormMainDataCustomList[${vstatus.index}].fdCascadeName" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.super') }" className="xform_main_data_custom_input"></xform:text>
						<html:hidden property="sysFormMainDataCustomList[${vstatus.index}].fdCascadeId"/>
					</div>
				</td>
			</c:if>
			<td>
				<xform:text showStatus="readOnly" property="sysFormMainDataCustomList[${vstatus.index}].fdValueText" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.showValue') }" className="xform_main_data_custom_input"></xform:text>
			</td>
			<td>
				<xform:text showStatus="readOnly" property="sysFormMainDataCustomList[${vstatus.index}].fdValue" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.realValue') }" className="xform_main_data_custom_input"></xform:text>
			</td>
			<td>
				<xform:text showStatus="readOnly" property="sysFormMainDataCustomList[${vstatus.index}].fdOrder" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.order') }" className="xform_main_data_custom_input"></xform:text>
				<html:hidden property="sysFormMainDataCustomList[${vstatus.index}].fdId" />
			</td>
		</tr>
	</c:forEach> -->
