<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:choose>
	<c:when test="${'true' eq JsParam.newUI}">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/sys/xform/designer/auditNote/newAuditNote.css">
		<div style="display:none;width:${JsParam.width};" name="div_${JsParam.controlId}" id='newAuditNoteWrap_${JsParam.controlId}'>
			<!-- 手写签批 -->
			<c:if test="${'true' eq JsParam.enableSignature }">
				<div name="xform_auditNote_div_signture_${JsParam.controlId}" class="lui-audit-signature lui-audit-hidden">
					<c:import url="/sys/lbpmservice/signature/handSignture/iWebRevision_edit_xform.jsp" charEncoding="UTF-8">
						<c:param name="recordID" value="${JsParam.recordID}" />
						<c:param name="fieldName" value="${JsParam.fieldName}" />
						<c:param name="userName" value="${JsParam.userName}" />
					</c:import>
				</div>
			</c:if>
			<!-- 文字签批 -->
			<div name="xform_auditNote_div_word_${JsParam.controlId}">
			<span class="lui-audit-opinion-outerBox">
				<span id="fdUsageContentSpan_${JsParam.controlId}">
					<textarea fd_type='newAuditNote' name="textareaNote_${JsParam.controlId}"  tagId="${JsParam.controlId}" resvalue="${JsParam.resvalue}"
							  mould="${JsParam.mould}" wfvalue="${JsParam.wfvalue}" class="process_review_content" height='${JsParam.height}'
							  operationtype="${JsParam.operationtype}" info="${JsParam.info}" onblur='$("textarea[name=fdUsageContent]").val($(this).val());'
							  placeholder="请选择或输入审批意见" subject="处理意见" validate="fdUsageContentMaxLength(4000)">
					</textarea>
				</span>
				<div class="lui-audit-opinion-action">
			       	<div class="lui-audit-opinion-action-box">
						<div class="lui-audit-common-usage">
							<div class="lui-audit-advice">
			                     <span>常用意见</span>
			                     <i class="lui-audit-downIcon"></i>
			                     <div class="lui-audit-common-usage-list" name="newAuditNoteCommonUsages_${JsParam.controlId}">
			                     	<ul></ul>
			                     </div>
			                </div>
			            </div>
			            <div class="lui-audit-opinion-otherFnc">
				            <kmss:ifModuleExist path="/km/signature/">
								<kmss:authShow roles="ROLE_SIGNATURE_DEFAULT">
									<!-- 电子签名 -->
									<div class="lui-audit-opinion-signature lui-audit-opinion-btn" title="电子签名" onclick="newUIAduitNoteSignature(this);">
					                	<i></i>
					               	</div>
								</kmss:authShow>
							</kmss:ifModuleExist>
							<!-- @已处理人 -->
			               	<div class="lui-audit-opinion-noticeHandler lui-audit-opinion-btn" title="@已处理人" onclick="lbpm.globals.selectHistoryHandlers(this,'textareaNote_${JsParam.controlId}');">
			               		<i></i>
			             		<input type="hidden" name="noticeHandlerIds" id="noticeHandlerIds">
			             	</div>
			             	<c:if test="${'true' eq JsParam.enableSignature }">
								<!-- 文字签批 -->
								<div name="xform_auditNote_a_word_${JsParam.controlId}" class="lui-audit-opinion-words lui-audit-opinion-btn lui-audit-hidden" title="文字签批" onclick="xform_handSignture_change('1','${JsParam.controlId}','newAuditNote');">
				               		<i></i>
				             	</div>
								<!-- 手写签批 -->
								<div name="xform_auditNote_a_signture_${JsParam.controlId}" class="lui-audit-opinion-hand lui-audit-opinion-btn" title="手写签批" onclick="xform_handSignture_change('0','${JsParam.controlId}','newAuditNote');">
				               		<i></i>
				             	</div>
							</c:if>
			            </div>
			        </div>
			    </div>
			</span>
				<div id="operationMethodsGroup_${JsParam.controlId}" class="lui-audit-oper-group">
				</div>
				<div id="operationAuditRow_${JsParam.controlId}" class="lui-audit-content">
					<div id="operationAuditTDTitle_${JsParam.controlId}"></div>
					<div id="operationAuditTDContent_${JsParam.controlId}"></div>
				</div>
				<div id="operationsRow_Scope_${JsParam.controlId}" style="width: 100%; margin-top: 5px; display: none;">
					<div id="operationsTDTitle_Scope_${JsParam.controlId}" style="display:inline-block;float:left;margin-right:10px;line-height:30px;"></div>
					<div id="operationsTDContent_Scope_${JsParam.controlId}" style="display:inline-block;min-width:70%;width:auto;line-height:30px;"></div>
				</div>
			</div>
			<!-- 提交按钮 -->
			<div class="lui-audit-submit-button">
				<ui:button order="2" text="${ lfn:message('button.submit') }" onclick="lbpm.globals.newAuditSimpleTagFlowSubmitEvent(this,'${JsParam.controlId}');">
				</ui:button>
			</div>
		</div>
		<div name="tip_${JsParam.controlId}" class="lui-audit-hidden">
		</div>
	</c:when>
	<c:otherwise>
		<div style="display: none; border: 0px;width:${JsParam.width};" name="div_${JsParam.controlId}" id="newAuditNoteWrap_${JsParam.controlId}">
			<div id="operationItemsRow_${JsParam.controlId}">
				<div class="lui-lbpm-detailNode">
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationItems" />
					<select name="operationItemsSelect_${JsParam.controlId}" onchange="auditOperationItemsChanged(this);">
					</select>
				</div>
			</div>
			<div style="display:inline-block;float:left;">
				<label><bean:message bundle="sys-lbpmservice" key="lbpmUsage.logOper.updateDefinePerson" /></label>&nbsp;
				<select name="newAuditNoteCommonUsages_${JsParam.controlId}" style="width: 120px; -ms-overflow-x: hidden;display:inline-block;margin-bottom:3px;" onchange="newAuditNoteSetUsages(this,'${JsParam.controlId}');">
					<option value=""><bean:message key="page.firstOption" /></option>
				</select>
			</div>
			<div style="float:right;text-align:right;" id="xform_auditNote_oper_${JsParam.controlId}">
				<a href="javascript:;" class="com_btn_link" onclick="lbpm.globals.selectHistoryHandlers(this,'textareaNote_${JsParam.controlId}');"><bean:message bundle="sys-lbpmservice" key="lbpm.process.noticeHandler.name" /></a>
				<span style="display:none">(<span class="noticeHandlerNum">0</span>/<span class="noticeHandlerTotal">0</span>)</span>
				<c:if test="${'true' eq JsParam.enableSignature }">
					<a href="javascript:void(0);" class="com_btn_link" name="xform_auditNote_a_word_${JsParam.controlId}" style="display:none;" onclick="xform_handSignture_change('1','${JsParam.controlId}');">文字签批</a>
					<a href="javascript:void(0);" class="com_btn_link" name="xform_auditNote_a_signture_${JsParam.controlId}" onclick="xform_handSignture_change('0','${JsParam.controlId}','newAuditNote');">手写签批</a>
				</c:if>
			</div>
			<!-- 手写签批 -->
			<c:if test="${'true' eq JsParam.enableSignature }">
				<div name="xform_auditNote_div_signture_${JsParam.controlId}" style="display:none;border:1px solid #dfdfdf;width:100%;">
					<c:import url="/sys/lbpmservice/signature/handSignture/iWebRevision_edit_xform.jsp" charEncoding="UTF-8">
						<c:param name="recordID" value="${JsParam.recordID}" />
						<c:param name="fieldName" value="${JsParam.fieldName}" />
						<c:param name="userName" value="${JsParam.userName}" />
					</c:import>
				</div>
			</c:if>
			<div name="xform_auditNote_div_word_${JsParam.controlId}">
			<textarea validate="fdUsageContentMaxLength(2000)" subject="处理意见 "
					  style="width:100%;height:${JsParam.height};resize:none;display:block;"
					  name="textareaNote_${JsParam.controlId}" tagid="${JsParam.controlId}"
					  mould="${JsParam.mould}" wfvalue="${JsParam.wfvalue}" resvalue="${JsParam.resvalue}"
					  fd_type="newAuditNote" operationtype="${JsParam.operationtype}" info="${JsParam.info}"
					  onblur="$('textarea[name=fdUsageContent]').val($(this).val());">
			</textarea>
				<span class="txtstrong">*</span><strong><bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationMethods" />&nbsp;&nbsp;</strong>
				<div id="operationMethodsGroup_${JsParam.controlId}" style="display:inline-block;margin-top:10px;">
				</div>
				<!-- 提交按钮 -->
				<div style="display:inline-block;float:right;margin-top:5px;">
					<ui:button order="2" text="${ lfn:message('button.submit') }" onclick="lbpm.globals.newAuditSimpleTagFlowSubmitEvent(this,'${JsParam.controlId}');">
					</ui:button>
				</div>
				<div id="operationAuditRow_${JsParam.controlId}" style="width: 100%; margin-top: 5px; display: none;">
					<div id="operationAuditTDTitle_${JsParam.controlId}" style="display:inline-block;float:left;margin-right:10px;line-height:30px;"></div>
					<div id="operationAuditTDContent_${JsParam.controlId}" style="display:inline-block;min-width:70%;width:auto;line-height:30px;"></div>
				</div>
				<div id="operationsRow_Scope_${JsParam.controlId}" style="width: 100%; margin-top: 5px; display: none;">
					<div id="operationsTDTitle_Scope_${JsParam.controlId}" style="display:inline-block;float:left;margin-right:10px;line-height:30px;"></div>
					<div id="operationsTDContent_Scope_${JsParam.controlId}" style="display:inline-block;min-width:70%;width:auto;line-height:30px;"></div>
				</div>
				<c:import url="/sys/xform/designer/auditNote/sysFormNewAduitNote_signature.jsp" charEncoding="UTF-8">
					<c:param name="controlId" value="${JsParam.controlId}" />
				</c:import>
			</div>
		</div>
		<div name="tip_${JsParam.controlId}" class="lui-audit-hidden">
		</div>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
	var XForm_NewAudit_Note = {
		handlerOperationTypepass: '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypepass" />',
		calcBranch: '<bean:message bundle="sys-lbpmservice" key="lbpmNode.calcBranch" />',
		handlerOperationTypeRefuse: '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse" />',
		noRefuseNode: '<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.noRefuseNode" />'
	};
	Com_IncludeFile("newAuditNote_script.js",Com_Parameter.ContextPath+"sys/xform/designer/auditNote/","js",true);
	Com_IncludeFile('xform_handSignture_auditNote_script.js',Com_Parameter.ContextPath + 'sys/lbpmservice/signature/handSignture/','js',true);
</script>
