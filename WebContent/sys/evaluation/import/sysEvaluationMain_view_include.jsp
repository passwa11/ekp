<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
	<div class="eval_EditMain" id="eval_EditMain">
		<input type="hidden" name="fdEvaluationTime"/>
		<input type="hidden" name="fdKey" />
		<input type="hidden" name="fdModelId" value="${HtmlParam.fdModelId}"/>
		<input type="hidden" name="fdModelName" value="${HtmlParam.fdModelName}"/>
		<sunbor:enums property="fdEvaluationScore" enumsType="sysEvaluation_Score" elementType="select" elementClass="eval_hidden" value="1"/>
		<table class="tb_simple eval_opt_table" width="100%" border="0" cellspacing="0" cellpadding="0">
			 <tr id="eval_EditMain_new_msg" style="display:${param.fdIsCommented == 'true' ? 'none' : 'table-row'}">
					<td colspan="2">
						<span class="eval_title eval_title_color"><bean:message key="sysEvaluation.title" bundle="sys-evaluation"/></span>
						<ul class="eval_star">
							<li id="eval_star_4" star="4"></li>
							<li id="eval_star_3" star="3"></li>
							<li id="eval_star_2" star="2"></li>
							<li id="eval_star_1" star="1"></li>
							<li id="eval_star_0" star="0"></li>
						</ul>
						<span id="eval_level" class="eval_level"></span>
					</td>
			</tr>
			<tr id="eval_EditMain_add_msg" style="display:${param.fdIsCommented == 'true' ?  'table-row' : 'none' }">
					<td colspan="2">
						<span class="eval_title eval_title_color"><bean:message key="sysEvaluationMain.addition" bundle="sys-evaluation"/></span>
					</td>
			</tr>
			<tr>
				<td valign="top" class="lui_eval_add_content">
				<!-- 
					<textarea name="fdEvaluationContent" class="eval_content">
				 	</textarea>
				  -->
				  <iframe name="fdEvaluationContent" id="mainIframe" class="eval_content_iframe" 
				  		 src="${ LUI_ContextPath}/sys/evaluation/import/sysEvaluationMain_content_area.jsp?iframeType=eval&iframeId=mainIframe" frameborder="0">
				  </iframe>
				</td>
				<td align="center" valign="top" class="lui_eval_add_btn">
					<input id="eval_button" class="eval_button" type=button value="<bean:message key="button.submit" />"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<label>
						<span class='eval_icons' data-iframe-uid='mainIframe'>
							<bean:message key="sysEvaluation.reply.icon.smile" bundle="sys-evaluation"/>
						</span>
						<div class='eval_biaoqing'></div>
					</label>
					
					<c:if test="${ empty param.notify || param.notify != 'false' }">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label class="eval_notify eval_summary_color">
							<input name="isNotify" type="checkbox" value="" 
								onclick="var isNotify = document.getElementsByName('isNotify');if(isNotify[0].checked){isNotify[0].value='yes'}else{isNotify[0].value=''}">
							<bean:message key="sysEvaluationMain.isNotify" bundle="sys-evaluation" />
						</label>
						<label class="eval_notify eval_summary_color">
							<c:if test="${param.notifyOtherName!='' && param.notifyOtherName!= null}">
								<input name="notifyOther" type="checkbox" value="" 
									    onclick="var notifyOther = document.getElementsByName('notifyOther');if(notifyOther[0].checked){notifyOther[0].value='${HtmlParam.notifyOtherName}'}else{notifyOther[0].value=''}">
									   <bean:message key="${param.key}" bundle="${param.bundel}" />
							</c:if>
						</label>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label class="eval_notify eval_summary_color">
							<bean:message key="sysNotifySetting.fdNotifyType" bundle="sys-notify" />：<kmss:editNotifyType property="fdNotifyType" />
						</label>
					
					</c:if>
					<span class="eval_prompt"></span>
				</td>
			</tr>
			
			<%-- <!-- 附件 --> --%>
			<tr>
				<td colspan="3">
					<div id="attachment_eva">
					</div>
				</td>
			</tr> 
		</table>
	</div>
