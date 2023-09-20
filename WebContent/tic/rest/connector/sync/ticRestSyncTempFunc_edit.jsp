<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<script type="text/javascript">
	Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js");
</script>
<script>
	// 自定义表单的全局变量
	var Xform_ObjectInfo = {};
	Xform_ObjectInfo.Xform_Controls = [];
</script>
<%@ include file="/sys/xform/include/sysForm_script.jsp"%>
<script
		src="${KMSS_Parameter_ContextPath}tic/core/resource/js/mustache.js"
		type="text/javascript"></script>
<script type="text/javascript"
		src="${KMSS_Parameter_ContextPath}tic/core/resource/js/sapEkp.js"></script>
<link
		href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.treeTable.css"
		rel="stylesheet" type="text/css" />
<script
		src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.treeTable.js"
		type="text/javascript"></script>
<script
		src="${KMSS_Parameter_ContextPath}tic/core/resource/js/erp.parser.js"
		type="text/javascript"></script>
<script type="text/javascript"
		src="${KMSS_Parameter_ContextPath}tic/core/resource/plugins/XMLParseUtil.js"></script>

<script>
	XMLParseUtil.initDomByMozilla();
</script>
<div id="optBarDiv">
	<input type=button value="${lfn:message('button.ok')}"
		   onclick="submitRestQuartzCfg()"> <input type="button"
												   value="<bean:message key="button.close"/>"
												   onclick="Com_CloseWindow();">
</div>
<center>
	<script type="text/javascript"
			src="${KMSS_Parameter_ContextPath}tic/core/resource/js/tools.js"></script>
	<p class="txttitle">
		Rest
		${lfn:message('tic-rest-connector:ticRestSyncJob.syncJobMappingSetting')}
		<!--
		<img
				src="${KMSS_Parameter_ContextPath}resource/style/default/tag/help.gif"
				title="${lfn:message('home.help')}" style="cursor: hand"
				onclick='Com_OpenWindow("ticRestSyncHelp.jsp","_blank")'></img>
		-->
	</p>
	<table class="tb_normal" width=95%>
		<tr>
			<td style="padding: 0px;">
				<div id="tic_rest_head">
					<table class="tb_normal" width="100%">
						<tr>
							<td width="15%">${lfn:message('tic-core-common:ticCoreCommon.funcName')}</td>
							<td width="35%"><input type="hidden" readOnly="readonly"
												   id="fdRestMainId" name="fdRestMainId" /> <input type="text"
																								   readOnly="readonly" id="fdRestMainName" name="fdRestMainName"
																								   class="inputsgl" /></td>
							<td width="15%">${lfn:message('tic-core-common:ticCoreCommon.dataSource')}</td>
							<td width="35%"><select id="dbselect" name="dbselect"
													onchange="">
							</select></td>
						</tr>
						<tr>
							<td width="15%">${lfn:message('tic-core-common:ticCoreCommon.lastExecuteTime')}</td>
							<td width="85%" colspan="3"><span id="fdLastDate"></span></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<table id="tic_rest_url" width="100%">
					<tr>
						<td colspan="4" class="com_subject" style="font-weight: bold;">${lfn:message('tic-rest-connector:ticRestSetting.urlParam')}</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="35%">${lfn:message('tic-core-common:ticCoreTransSett.paramName')}</td>
						<td class="td_normal_title" width="20%">${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}</td>
						<td class="td_normal_title" width="15%">${lfn:message('tic-core-common:ticCoreTransSett.paramType')}</td>
						<td class="td_normal_title" width="30%">${lfn:message('tic-core-common:ticCoreCommon.writeData')}</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table id="tic_rest_body" width="100%">
					<tr>
						<td colspan="6" class="com_subject" style="font-weight: bold;">${lfn:message('tic-rest-connector:ticRestSetting.bodyParam')}</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="35%">${lfn:message('tic-core-common:ticCoreTransSett.paramName')}</td>
						<td class="td_normal_title" width="15%">${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}</td>
						<td class="td_normal_title" width="10%">${lfn:message('tic-core-common:ticCoreTransSett.paramType')}</td>
						<td class="td_normal_title" width="15%">${lfn:message('tic-core-common:ticCoreCommon.writeData')}</td>
						<td class="td_normal_title"
							style="width: 18%; font-size: 13px; text-align: center;"
							nowrap="">映射名</td>
						<td class="td_normal_title"
							style="width: 7%; font-size: 13px; text-align: center;" nowrap=""><img
								src="<c:url value='/resource/style/default/calendar/finish.gif'/>" alt="一键匹配"
								onclick="oneKeyMatch('tic_soap_output');" style="cursor: hand"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table id="tic_rest_return" width="100%">
					<tr>
						<td colspan="6" class="com_subject" style="font-weight: bold;">
							返回参数</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="35%">${lfn:message('tic-core-common:ticCoreTransSett.paramName')}</td>
						<td class="td_normal_title" width="15%">${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}</td>
						<td class="td_normal_title" width="10%">${lfn:message('tic-core-common:ticCoreTransSett.paramType')}</td>
						<td class="td_normal_title" width="15%">${lfn:message('tic-core-common:ticCoreCommon.writeData')}</td>
						<td class="td_normal_title"
							style="width: 18%; font-size: 13px; text-align: center;"
							nowrap="">映射名</td>
						<td class="td_normal_title"
							style="width: 7%; font-size: 13px; text-align: center;" nowrap=""><img
								src="${KMSS_Parameter_ContextPath}resource/style/default/calendar/finish.gif" alt="一键匹配"
								onclick="oneKeyMatch('tic_soap_output');" style="cursor: hand"></td>
					</tr>

				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</center>
<script type="text/javascript" src="restQuartzCfg.js"></script>

<%@ include file="/resource/jsp/edit_down.jsp"%>


