<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${param.contentType eq 'baseInfo'}">
		<div class="lui_form_content_frame">
			<p class="txttitle">${lfn:message('sys-iassister:table.sysIassisterItem') }</p>
			<table class="tb_n_simple" width="100%">
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('sys-iassister:msg.rule.cite') }</td>
					<td width="85%" colspan="3">
						<div id="_xform_ruleId" _xform_type="dialog">
							<xform:dialog propertyId="ruleId" propertyName="ruleName"
								showStatus="view" style="width:96%;"
								subject="${lfn:message('sys-iassister:sysIassisterItem.rule') }">
							</xform:dialog>
						</div>
					</td>
				</tr>
				<!-- 检查项名称 -->
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('sys-iassister:sysIassisterItem.fdName') }</td>
					<td width="85%" colspan="3"><xform:text property="fdName"
							showStatus="view" style="width:96%"
							subject="${lfn:message('sys-iassister:sysIassisterItem.fdName') }" />
					</td>
				</tr>
				<!-- 排序号 -->
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('sys-iassister:sysIassisterItem.fdOrder') }</td>
					<td width="85%" colspan="3"><xform:text property="fdOrder"
							style="width:96%"
							showStatus="view" 
							subject="${lfn:message('sys-iassister:sysIassisterItem.fdOrder') }" />
					</td>
				</tr>
			</table>
		</div>
	</c:when>
	<c:when test="${param.contentType eq 'ruleInfo'}">
		<c:choose>
			<c:when test="${param.useVue eq 'true' }">
				<c:import url="/${baseUrl }/rule_info/ruleInfo_vue.jsp"
					charEncoding="UTF-8">
				</c:import>
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${param.contentType eq 'configInfo'}">
		<c:choose>
			<c:when test="${param.useVue eq 'true' }">
				<c:import url="/${baseUrl }/config_info/configInfo_vue.jsp"
					charEncoding="UTF-8">
				</c:import>
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	</c:when>
</c:choose>