<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script>
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
	Com_IncludeFile("jquery.js|doclist.js|docutil.js|dialog.js|formula.js");
	var dialogObject;
	if(window.showModalDialog){
		dialogObject = window.dialogArguments;
	}else{
		dialogObject = opener.Com_Parameter.Dialog;
	}
	var FlowChartObject = dialogObject.Parameters.FlowChartObject;
</script>
</head>
<body>
<center>
<br>
节点集信息查看
<table width="95%" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal" id="config_table">
				<tr>
					<td width="100px">别名<%--<kmss:message key="FlowChartObject.Lang.Node.embeddedId" bundle="sys-lbpmservice-node-group" />--%></td>
					<td>
						<span class="fdAlias"></span>
					</td>
				</tr>
				<tr>
					<td width="100px">对应节点集</td>
					<td width="490px">
						<span class="fdRefName"></span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr id="subFlowTr" LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Node.subFlowChart" bundle="sys-lbpmservice-node-group" />" LKS_LabelId="subFlowChart">
		<td>
			<table style="width:100%;height:100%;" class="tb_normal">
				<tr>
					<td>
						<textarea name="fdSubFlowContent" style="display:none"></textarea>
						<iframe style="width:100%;height:600px;" scrolling="no" id="WF_IFrame"></iframe>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script>
	Com_AddEventListener(window, "load", function() {
		// 添加标签切换事件
		var table = document.getElementById("subFlowTr");
		while((table != null) && (table.tagName.toLowerCase() != "table")){
			table = table.parentNode;
		}
		if(table != null && window.Doc_AddLabelSwitchEvent){
			Doc_AddLabelSwitchEvent(table, "DynamicSubFlow_OnLabelSwitch");
		}
		__init();
	});

	function DynamicSubFlow_OnLabelSwitch(tableName, index) {
		var trs = document.getElementById(tableName).rows;
		if(trs[index].id!="subFlowTr")
			return;
		setTimeout(function(){
			$("#WF_IFrame").css("width","100%");
			$("#WF_IFrame").css("height","600px");
		},300);
	}

	function __init(){
		if(dialogObject.Parameters.__groups){
			var ___groups = JSON.parse(dialogObject.Parameters.__groups);
			for(var i=0;i<___groups.length;i++){
				var param = ___groups[i];
				if(param.fdId == dialogObject.Parameters.fdId){
					$(".fdAlias").text(param.fdAlias);
					$(".fdRefName").text(param.fdRefName);
					var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=getContentByRefId&fdRefId=';
					ajaxurl += param.fdRefId;
					var kmssData = new KMSSData();
					kmssData.SendToUrl(ajaxurl, function(http_request) {
						var responseText = http_request.responseText;
						var json = eval("("+responseText+")");
						if (json.fdContent){
							$("textarea[name='fdSubFlowContent']").val(json.fdContent);
							var loadUrl =  '<c:url value="/sys/lbpm/flowchart/page/panel.html" />?embedded=true&edit=false&extend=oa&contentField=fdSubFlowContent&showBar=false&showMenu=false';
							loadUrl += '&template=' + FlowChartObject.IsTemplate;
							loadUrl += '&modelName=' + FlowChartObject.ModelName + '&modelId=' + FlowChartObject.ModelId;
							document.getElementById("WF_IFrame").setAttribute("src", loadUrl);
						}
					},false);
					break;
				}
			}
		}
	}
</script>
</center>
</body>
</html>