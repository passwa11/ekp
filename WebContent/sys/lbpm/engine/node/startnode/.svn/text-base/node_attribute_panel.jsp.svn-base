<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<c:choose>
	<c:when test="${JsParam.isFixedNode eq 'true'}">
		<table width="590px" id="Label_Tabel">
			<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
				<td>
					<table width="100%" class="tb_normal">
						<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8">
							<c:param name="flowType" value="1" />
						</c:import>
						<tr style="display: none;">
							<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.description" bundle="sys-lbpm-engine" /></td>
							<td>
								<c:if test="${!isLangSuportEnabled }">
									<textarea name="wf_description" style="width:100%"></textarea>
									<br>
								</c:if>
								<c:if test="${isLangSuportEnabled }">
									<xlang:lbpmlangAreaNew property="_wf_description" alias="wf_description" style="width:100%" langs=""/>
								</c:if>
								<kmss:message key="FlowChartObject.Lang.Node.imgLink" bundle="sys-lbpm-engine" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Event" bundle="sys-lbpm-engine" />">
				<td>
				<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
				</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table width="420px" id="Label_Tabel">
			<tr>
				<td style="background-color:#f6f6f6 !important;">
					<table width="95%" style="background-color:#f6f6f6 !important;" class="tb_normal">
						<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8">
							<c:param name="flowType" value="1" />
						</c:import>
						<c:import url="/sys/lbpmservice/node/common/node_freeflow_more_attribute.jsp" charEncoding="UTF-8">
						</c:import>
						<tr style="display: none;">
							<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.description" bundle="sys-lbpm-engine" /></td>
							<td>
								<c:if test="${!isLangSuportEnabled }">
									<textarea name="wf_description" style="width:100%"></textarea>
									<br>
								</c:if>
								<c:if test="${isLangSuportEnabled }">
									<xlang:lbpmlangAreaNew property="_wf_description" alias="wf_description" style="width:100%" langs=""/>
								</c:if>
								<kmss:message key="FlowChartObject.Lang.Node.imgLink" bundle="sys-lbpm-engine" />
							</td>
							<td>
								<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</c:otherwise>
</c:choose>

<script>
	AttributeObject.Init.EditModeFuns.push(function(nodeData) {
		//多语言
		_initPropLang4Edit("nodeDesc",nodeData,"description","_");
		// 自由流实时保存节点数据
		var isOpenNewWin = "${JsParam.isOpenNewWin eq 'true'}";
		if(isOpenNewWin!="true"){
			$("input[name^='__notify_type_']:checkbox").change(function(){
				saveNodeData();
			});
			$("input[name^='_wf_name_']").change(function(){
				saveNodeData();
			});
			$("input[name^='wf_']").change(function(){
				saveNodeData();
			});
		}
	});
	AttributeObject.Init.ViewModeFuns.push(function(nodeData) {
		//多语言
		_initPropLang4View("nodeDesc",nodeData,"description","_");
	});

	AttributeObject.AppendDataFuns.push(function(nodeData){
	/**
		"nodeDesc":[//描述
			{"lang":"zh-CN","value":"主管审批意见"},{"lang":"en-US","value":"Manager Auditing Note"}
		],
	**/
		_propLang4AppendData("nodeDesc",nodeData,"description","_");
	});
</script>