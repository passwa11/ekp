<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
//#79908 移除页面漏洞代码，增加移动端审批意见获取方法，避免移动端审批意见绕过主文档可阅读权限 作者 曹映辉 #日期 2019年8月14日
%>
<script>
	var LbpmAuditNoteList = ${auditNotes};
</script>
<c:if test="${not empty auditNotes}">
	<div
		data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceAuditLabelItem"
		data-dojo-props="fdFactNodeName:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmservice.flowchart.process.begin"/>',nodeFlag:'start'"></div>
	<c:forEach items="${auditNotes}" var="auditNote" varStatus="vstatus">
		<%
			JSONObject auditNote = (JSONObject) pageContext
							.getAttribute("auditNote");
		%>
		<c:if test="${auditNote.fdActionKey=='_concurrent_branch'}">
			<%
				if (auditNote.getBoolean("firstBlank")) {
			%>
			<div
				data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceAuditLabelItem"
				data-dojo-props="fdFactNodeName:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmservice.flowchart.process.branch"/>',toggle:true,fdExecutionId:'${auditNote.fdParentExecutionId }'"></div>
			<%
				}
			%>
			<div
				data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceAuditBranchItem"
				data-dojo-props="store:LbpmAuditNoteList['${vstatus.index }']"></div>
		</c:if>
		<c:if test="${auditNote.fdActionKey == 'distributeNote' || auditNote.fdActionKey == 'recoverNote'}">
			<div
					data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceAuditLabelItem"
					data-dojo-props="fdFactNodeName:'${auditNote.fdFactNodeName}',toggle:true,fdExecutionId:'${auditNote.fdParentExecutionId }'"></div>
			<div
					data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceAuditBranchItem"
					data-dojo-props="store:LbpmAuditNoteList['${vstatus.index }']"></div>
		</c:if>
		<c:if test="${auditNote.fdActionKey!='_concurrent_branch' && auditNote.fdActionKey != 'distributeNote' && auditNote.fdActionKey != 'recoverNote'}">
			<%
				if (auditNote.getBoolean("firstNode")) {
			%>
				<div
					data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceAuditLabelItem"
					data-dojo-props="store:LbpmAuditNoteList['${vstatus.index }'],formBeanName:'${ formBeanName}'">
				</div>
			<%
				}
			%>
			<div
				data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceAuditNodeItem"
				data-dojo-props="store:LbpmAuditNoteList['${vstatus.index }'],formBeanName:'${ formBeanName}'">
				<c:if test="${auditNote.fdIsHide eq '2'}">
					<c:forEach items="${auditNote.auditNoteListsJsps4Mobile}"
						var="auditNoteListsJsp" varStatus="vstatus">
						
						<c:import url="${auditNoteListsJsp}" charEncoding="UTF-8">
							<c:param name="auditNoteFdId" value="${auditNote.fdId}" />
							<c:param name="modelName"
								value="${auditNote.fdProcess.fdModelName}" />
							<c:param name="modelId" value="${auditNote.fdProcess.fdModelId}" />
							<c:param name="formName" value="${formBeanName}" />
						</c:import>
					</c:forEach>
					
					<c:import url="/sys/attachment/mobile/import/view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="${ formBeanName}"></c:param>
						<c:param name="fdKey" value="${auditNote.fdId}"></c:param>
						<c:param name="fdViewType" value="simple"></c:param>
					</c:import>
				</c:if>
			</div>
		</c:if>
	</c:forEach>
	<c:if test="${param['docStatus']>='30' || param['docStatus']=='00'}">
		<div
			data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceAuditLabelItem"
			data-dojo-props="fdFactNodeName:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmservice.flowchart.process.end"/>',nodeFlag:'end'"></div>		
	</c:if>
	
	<c:if test="${param['docStatus']>='30' || param['docStatus']=='00'}">
		<script type="text/javascript">
			require(["dojo/ready","dojo/dom-style","dojo/dom"], function(ready,domStyle,dom) {
				ready(function(){
					var _Lbpm_SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0]; //统一在此获取流程默认值与功能开关等配置	
					var isShow = true;
					if (typeof _Lbpm_SettingInfo != "undefined" && 
							_Lbpm_SettingInfo.isShowApprovalTime === "false"){
						isShow = false;
					}
					if (!isShow){
						var vEfficiencyWrap = dom.byId("efficiencyWrap");
						if (vEfficiencyWrap != null){
							domStyle.set(vEfficiencyWrap,"display","none");
						}
					}
				});
			});
		</script>
	</c:if>
	<%--添加流程耗时和排名显示 by王祥 2017-6-22 17:23:43--%>
	<c:if test="${param['docStatus']>='30' || param['docStatus']=='00'}">
	<script type="text/javascript">
		require(["dojo/query" ,"dojo/dom","dojo/dom-construct", "dojo/dom-style"], function(query,dom,domConstruct,domStyle){
			var vEfficiencyDiv = dom.byId("efficiency");
			//获取流程耗时和排名
			var vEfficiency=LbpmAuditNoteList[LbpmAuditNoteList.length-1].fdCostTimeDisplayString;
			var vRemarks=LbpmAuditNoteList[LbpmAuditNoteList.length-1].fdRemarks;
			if(vEfficiency!=undefined&&vEfficiency!=null){
				domConstruct.place("<span style='font-size:  1.5rem'>"+vEfficiency+"</span>",vEfficiencyDiv);
				if(vRemarks!=undefined&&vRemarks!=null){
					domConstruct.place("</br><span style='font-size:  1rem;color: #777676;'>"+vRemarks+"</span>",vEfficiencyDiv);
				}
			}			
		});
    </script>
    <div style="display: table;" class="muiFlowEfficiency" id="efficiencyWrap">
    	<div style="display: table-cell;vertical-align: top;">
    		<img style="width: 3.5rem" src="${LUI_ContextPath}/sys/lbpmservice/mobile/lbpm_audit_note/images/efficiencyBig.png">&nbsp;&nbsp;
    	</div>
    	<div style="display: table-cell;" id="efficiency">
		</div>
    </div>
	</c:if>
</c:if>