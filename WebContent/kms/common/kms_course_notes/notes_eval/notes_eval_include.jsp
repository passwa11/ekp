<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
	<div class="eval_EditMain" id="eval_EditMain">
		<input type="hidden" name="fdEvaluationTime"/>
		<input type="hidden" name="fdKey" />
		<input type="hidden" name="fdModelId" value="${param.fdModelId}"/>
		<input type="hidden" name="fdModelName" value="${param.fdModelName}"/>
		<sunbor:enums property="fdEvaluationScore" enumsType="sysEvaluation_Score" elementType="select" elementClass="eval_hidden" value="1"/>
		<table class="eval_opt_table" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2">
				<div style="margin-top:10px;">
					<ul class="eval_star">
						<li id="eval_star_4" star="4"></li>
						<li id="eval_star_3" star="3"></li>
						<li id="eval_star_2" star="2"></li>
						<li id="eval_star_1" star="1"></li>
						<li id="eval_star_0" star="0"></li>
					</ul>
					<span id="eval_level" class="eval_level"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				  <iframe name="fdEvaluationContent" id="mainIframe" class="eval_content_iframe" 
				  		 src="${ LUI_ContextPath}/sys/evaluation/import/sysEvaluationMain_content_area.jsp?iframeType=eval">
				  </iframe>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span class='eval_icons' data-iframe-uid='mainIframe'>
						<bean:message key="sysEvaluation.reply.icon.smile" bundle="sys-evaluation"/>
					</span>
					<div class='eval_biaoqing'></div>
					
					<input id="eval_button" class="eval_button" type=button value="${lfn:message('kms-common:kmsCommon.evaluate') }"/>
					<span class="eval_prompt"></span>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<label class="eval_notify eval_summary_color">
						<input name="isNotify" type="checkbox" value="yes" checked="checked">
						<bean:message key="sysEvaluationMain.isNotify" bundle="sys-evaluation" />
					</label>
					<label class="eval_notify eval_summary_color">
						<c:if test="${param.notifyOtherName!='' && param.notifyOtherName!= null}">
							<input name="notifyOther" type="checkbox" value="${param.notifyOtherName}" checked="checked"><bean:message key="${param.key}" bundle="${param.bundel}" />
						</c:if>
					</label>
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px!important">
					<bean:message key="sysNotifySetting.fdNotifyType" bundle="sys-notify" />：<kmss:editNotifyType property="fdNotifyType" value="todo"/>
				</td>
			</tr>
		</table>
		
		<div class="eval_border_line"></div>
	</div>
