<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
		<bean:message bundle="sys-recycle" key="module.sys.recycle"/> - <xform:text property="docSubject"/>
	</template:replace>
	<template:replace name="head">
		<style type="text/css">
		<c:choose>
			<c:when test="${sysRecycleLogForm.fdOptType == 0}">
			.lui-status-stamp{
			  padding: 10px;
			  width: 184px;
			  height: 55px;
			  line-height: 55px;
			  text-align: center;
			  color: #22b07b;
			  font-size: 16px;
			  font-weight: normal;
			  font-family: "Microsoft YaHei";
			  text-transform: uppercase;
			  background-image: url(icon-recover.png);
			  background-repeat: no-repeat;
			  background-position: 0 0;
			  background-size: 100% auto;
			  display: block;
			  float:right;
			  white-space: nowrap;
			  overflow: hidden;
			  text-overflow: ellipsis;
			  z-index:100;
			  position:relative; 
			  top:50px;
			  -webkit-transform: rotate(15deg);
			     -moz-transform: rotate(15deg);
			      -ms-transform: rotate(15deg);
			       -o-transform: rotate(15deg);
			          transform: rotate(15deg);
			}
			</c:when>
			<c:otherwise>
			.lui-status-stamp{
			  padding: 10px;
			  width: 184px;
			  height: 55px;
			  line-height: 55px;
			  text-align: center;
			  color: #f56b6b;
			  font-size: 16px;
			  font-weight: normal;
			  font-family: "Microsoft YaHei";
			  text-transform: uppercase;
			  background-image: url(icon-stamp.png);
			  background-repeat: no-repeat;
			  background-position: 0 0;
			  background-size: 100% auto;
			  display: block;
			  float:right;
			  white-space: nowrap;
			  overflow: hidden;
			  text-overflow: ellipsis;
			  z-index:100;
			  position:relative; 
			  top:50px;
			  -webkit-transform: rotate(15deg);
			     -moz-transform: rotate(15deg);
			      -ms-transform: rotate(15deg);
			       -o-transform: rotate(15deg);
			          transform: rotate(15deg);
			}
			</c:otherwise>
		</c:choose>
		</style>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<c:if test="${redirectUrl != null && sysRecycleLogForm.fdOptType!=2}">
			<kmss:auth requestURL="${redirectUrl}" requestMethod="GET">
				<ui:button text="${lfn:message('sys-recycle:button.viewOriDoc')}" 
					onclick="Com_OpenWindow('${LUI_ContextPath}${redirectUrl}&isAdmin=true','_blank');" order="1">
				</ui:button>
			</kmss:auth>
			</c:if>
			<c:if test="${sysRecycleLogForm.fdOptType==1}">
			<kmss:auth requestURL="/sys/recycle/sys_recycle_doc/sysRecycle.do?method=hardDelete&modelName=${JsParam.modelName}&modelId=${JsParam.modelId}" requestMethod="GET">
				<!-- 永久删除 -->
				<ui:button text="${lfn:message('sys-recycle:button.hardDelete')}" 
					onclick="hardDelete();" order="2">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/recycle/sys_recycle_doc/sysRecycle.do?method=recover&modelName=${JsParam.modelName}&modelId=${JsParam.modelId}" requestMethod="GET">
				<!-- 还原 -->
				<ui:button text="${lfn:message('sys-recycle:button.recover')}" 
					onclick="recover();" order="3">
				</ui:button>
			</kmss:auth>
			</c:if>
			
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>	
	<template:replace name="content">
		<p class="txttitle">
			<xform:text property="docSubject"/>
		</p>
		<div class="lui-status-stamp">
			<c:choose>
				<c:when test="${sysRecycleLogForm.fdOptType == 0}">
					<bean:message bundle="sys-recycle" key="stamp.recover"/>
				</c:when>
				<c:when test="${sysRecycleLogForm.fdOptType == 1}">
					<bean:message bundle="sys-recycle" key="stamp.deleted"/>
				</c:when>
				<c:otherwise>
					<bean:message bundle="sys-recycle" key="stamp.hardDelete"/>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="lui_form_content_frame" style="padding-top: 10px;">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-recycle" key="sysRecycleLog.docSubject"/>
					</td>
					<td width="85%" colspan="3">
						<xform:text property="docSubject" style="width:85%" />
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-recycle" key="sysRecycleLog.fdOperator"/>
					</td>
					<td width="35%">
				     <c:if test="${sysRecycleLogForm.fdOperatorLoginName != 'anonymous'}">
				     	${lfn:escapeHtml(sysRecycleLogForm.fdOperatorName)}
			    	 </c:if>
			    	 <c:if test="${sysRecycleLogForm.fdOperatorLoginName == 'anonymous' && sysRecycleLogForm.fdOperatorIp != null}">
			    		${lfn:escapeHtml(sysRecycleLogForm.fdOperatorName)}
			    	 </c:if>			    	 
			    	 <c:if test="${sysRecycleLogForm.fdOperatorLoginName == 'anonymous' && sysRecycleLogForm.fdOperatorIp == null}">
			    		${ lfn:message('sys-recycle:sysRecycleLog.systemOperator') }
			    	 </c:if>						
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-recycle" key="sysRecycleLog.fdOptDate"/>
					</td>
						<td width="35%">
							<xform:text property="fdOptDate" style="width:85%"/>
						</td>
					</tr>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-recycle" key="sysRecycleLog.fdOperatorIp"/>
					</td>
					<td width="35%">
						<xform:text property="fdOperatorIp" style="width:85%"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-recycle" key="sysRecycleLog.fdOptType"/>
					</td>
					<td width="35%">
						<sunbor:enumsShow 
							value="${sysRecycleLogForm.fdOptType}" enumsType="sysRecycle_fdOptType" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-recycle" key="sysRecycleLog.fdCreator"/>
					</td>
					<td width="35%">
						<xform:text property="fdCreatorName"  style="width:85%" showStatus="view"></xform:text>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-recycle" key="sysRecycleLog.fdCreateTime"/>
					</td>
						<td width="35%">
							<xform:text property="fdCreateTime" style="width:85%"/>
						</td>
					</tr>
				</tr>				
			</table>
		</div>
		
		<script type="text/javascript">
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			window.hardDelete = function() {
				dialog.confirm('<bean:message bundle="sys-recycle" key="page.comfirmHardDelete"/>', function(value) {
					if(value == true) {
						Com_OpenWindow('sysRecycle.do?method=hardDelete&modelName=${JsParam.modelName}&modelId=${JsParam.modelId}','_self');
					}
				});
			}
			
			window.recover = function(delUrl) {
				dialog.confirm('<bean:message bundle="sys-recycle" key="page.comfirmRecover"/>', function(value) {
					if(value == true) {
						Com_OpenWindow('sysRecycle.do?method=recover&modelName=${JsParam.modelName}&modelId=${JsParam.modelId}','_self');
					}
				});
			}
		});
		</script>
	</template:replace>
</template:include>
