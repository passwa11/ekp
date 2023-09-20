<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- <%@ include file="/sys/ui/jsp/jshead.jsp"%> --%>
<c:set var="sysFormTemplateFormPrefix" value="" />
<c:set var="fdKey" value="" />
<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
<script language="JavaScript">
	Com_IncludeFile("dialog.js|formula.js|data.js|doclist.js");
</script>
<script language="JavaScript">
	DocList_Info.push("TABLE_DocList_Details");
	//限定范围仅为一个时才允许使用跟主文档相关的变量
	function _GetSysDictObj(){
		var rtnVal = [];
		var scopeName = $("input[name='fdScopeId']").val();
		if (!scopeName){
			return rtnVal;
		}else{
			var scopeNameArr = new Array();
			scopeNameArr = scopeName.split(";");
			if (scopeNameArr.length === 1){
				return Formula_GetVarInfoByModelName(scopeName);
			}else{
				return rtnVal;
			}
		}
	}
	
	// 流程图初始内容，用于比较流程图内容是否有修改
	if(window.LBPM_Template_InitFlowContent == null) {
		LBPM_Template_InitFlowContent = new Array();
	}
	
	// 数据初始化
	function LBPM_Template_LoadProcessData(key) {
		var content = $("textarea[name='fdContent']").val();
		if(content == "") {
			LBPM_Template_InitFlowContent[key] = "";
		} else {
			var processData = WorkFlow_LoadXMLData(content);
			if(LBPM_Template_InitFlowContent[key] == "" || typeof(LBPM_Template_InitFlowContent[key]) == "undefined"){
				LBPM_Template_InitFlowContent[key] = WorkFlow_BuildXMLString(processData, "process");
			}
			if(processData.otherParams){
				var contents = JSON.parse(processData.otherParams);
				for(var i=0;i<contents.length;i++){
					var param = contents[i];
					var fieldValues = new Object();
					fieldValues["fdParamValue"]=param.fdParamValue;
					fieldValues["fdParamName"]=param.fdParamName;
					fieldValues["fdParamType"]=param.fdParamType;
					fieldValues["fdIsMulti[!{index}]"]=param.fdIsMulti;
					fieldValues["_fdIsMulti[!{index}]"]=param.fdIsMulti;
					var tr = DocList_AddRow("TABLE_DocList_Details",null,fieldValues);
					if(fieldValues["fdParamType"] && fieldValues["fdParamType"].indexOf('ORG_TYPE_') !=-1){
						$(tr).find("div.isMulti").show();
					}
					$(tr).find("[name='fdParamType']").prop("disabled","false");
				}
			}
		}
	}

	Com_AddEventListener(window, "load", function() {
		LBPM_Template_LoadProcessData("lbpmEmbedded");
		// 添加标签切换事件
		var table = document.getElementById("embeddedFlowTr");
		while((table != null) && (table.tagName.toLowerCase() != "table")){
			table = table.parentNode;
		}
		if(table != null && window.Doc_AddLabelSwitchEvent){
			Doc_AddLabelSwitchEvent(table, "EmbeddedSubFlow_OnLabelSwitch");
		}
	});

	//标签切换时加载公式信息
	function EmbeddedSubFlow_OnLabelSwitch(tableName, index) {
		var trs = document.getElementById(tableName).rows;
		if(trs[index].id!="embeddedFlowTr")
			return;
		EmbeddedSubFlow_Load_FlowChartObject();
	}
	
	function EmbeddedSubFlow_Load_FlowChartObject(){
		var iframe = document.getElementById("Embedded_WF_IFrame").contentWindow;
		if(iframe && iframe.FlowChartObject){
			var LBPM_Template_FormFieldList = _GetSysDictObj();
			var otherParam = getOtherParam();
			if(otherParam.length>0){
				LBPM_Template_FormFieldList = LBPM_Template_FormFieldList.concat(otherParam);
			}
			iframe.FlowChartObject.FormFieldList = LBPM_Template_FormFieldList;
		}else{
			setTimeout(EmbeddedSubFlow_Load_FlowChartObject,500);
		}
	}
	
	// 提交校验
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
		var key = "lbpmEmbedded";
		if(LBPM_Template_InitFlowContent[key] == null) {
			return false;
		} 
		var FlowChartObject = document.getElementById("Embedded_WF_IFrame").contentWindow.FlowChartObject;
		if(!FlowChartObject.CheckFlow(true)) {
			// 流程图校验
			return false;
		}
		//嵌入子流程流入流出校验
		if(FlowChartObject.Nodes.all.length==0){
			alert("<bean:message bundle='sys-lbpmservice-support' key='lbpmEmbeddedSubFlow.noNodeInfo'/>");
			return false;
		}
		var noLineInNum = 0;var noLineOutNum = 0;
		for(var i=0; i<FlowChartObject.Nodes.all.length; i++){
			var node = FlowChartObject.Nodes.all[i];
			//检查流入
			if(node.CanLinkInCount>0 && node.LineIn.length==0){
				noLineInNum++;
				if(!(node.Desc && !node.Desc.isBranch(node.Data))){
					FlowChartObject.SelectElement(node);
					alert("<bean:message bundle='sys-lbpmservice-support' key='lbpmEmbeddedSubFlow.lineOutInfo'/>");
					return false;
				}
			}
			//检查流出
			if(node.CanLinkOutCount>0 && node.LineOut.length==0){
				noLineOutNum++;
				if(!(node.Desc && !node.Desc.isBranch(node.Data))){
					FlowChartObject.SelectElement(node);
					alert("<bean:message bundle='sys-lbpmservice-support' key='lbpmEmbeddedSubFlow.lineOutInfo'/>");
					return false;
				}
			}
		}
		if(noLineInNum!=1 || noLineOutNum!=1){
			alert("<bean:message bundle='sys-lbpmservice-support' key='lbpmEmbeddedSubFlow.lineInfo'/>")
			return false;
		}
		// 判断到连线链接的不是分支节点，则不保存连线上的分支条件
		for(var i=0; i<FlowChartObject.Lines.all.length; i++){
			var line = FlowChartObject.Lines.all[i];
			if(line.Data.condition || line.Data.disCondition){
				var node = line.StartNode;
				if (node != null) {
					var nodeDescObj = node.Desc;
					if (nodeDescObj && !((nodeDescObj["isBranch"](node)) && !nodeDescObj["isHandler"](node))) {
						delete line.Data.condition;
						delete line.Data.disCondition;
					}
				}
			}
		}
		var processData = FlowChartObject.BuildFlowData();
		setOtherParam(processData);
		// 设置流程内容
		var xml = WorkFlow_BuildXMLString(processData, "process");
		$("textarea[name='fdContent']").val(xml);
		// 比较流程内容是否修改
		var _fdIsModified = $("input[name='fdIsModified']");
		if(LBPM_Template_InitFlowContent[key] != xml || _fdIsModified.val() == 'true') {
			_fdIsModified.val("true");
		} else {
			_fdIsModified.val("false");
		}
		return true;
	};

	//所属分类的弹框
	function lbpmEmbeddedSubFlowCategoryTreeDialog() {
		Dialog_Tree(false, 'fdCategoryId', 'fdCategoryName', ',', 
				'lbpmEmbeddedSubFlowCategoryTreeService&parentId=!{value}', 
				"${lfn:message('sys-lbpmservice-support:category.set')}", 
				null, null, null, null, null, 
				"${lfn:message('sys-lbpmservice-support:category.set')}");
	}
	
	//使用范围
	function lbpmEmbeddedSubFlowCategoryScopeDialog() {
		Dialog_Tree(true,'fdScopeId', 'fdScopeName',';','lbpmEmbeddedSubFlowScopeTreeService','<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.selectScope" />',false,function(rtn){
			var iframe = $("#Embedded_WF_IFrame");
			var url = iframe.attr("src");
			if(rtn && rtn.data && rtn.data.length==1){
				iframe.attr("src",Com_SetUrlParameter(url,"modelName",rtn.data[0].id));
			}else{
				iframe.attr("src",Com_SetUrlParameter(url,"modelName",""));
			}
		});
	}
	
</script>
<kmss:windowTitle moduleKey="sys-lbpmservice-support:table.lbpmEmbeddedSubFlow" subjectKey="sys-lbpmservice-support:lbpmEmbeddedSubFlow.templateSet" subject="${lbpmEmbeddedSubFlowForm.fdName}" />
<html:form action="/sys/lbpmservice/support/lbpmEmbeddedSubFlow.do" >
	<div id="optBarDiv">
		<c:if test="${lbpmEmbeddedSubFlowForm.method_GET=='edit'}">
			<%--更新--%>
			<input type=button value="<bean:message key="button.update"/>"
				onclick="Com_Submit(document.lbpmEmbeddedSubFlowForm, 'update');">
		</c:if>
		 <c:if test="${lbpmEmbeddedSubFlowForm.method_GET=='add' || lbpmEmbeddedSubFlowForm.method_GET=='clone'}">
		 	<%--新增--%>
			<input type=button value="<bean:message key="button.save"/>"
				onclick="Com_Submit(document.lbpmEmbeddedSubFlowForm, 'save');">
			<input type=button value="<bean:message key="button.saveadd"/>"
				onclick="Com_Submit(document.lbpmEmbeddedSubFlowForm, 'saveadd');">
		</c:if> 
			<%--关闭--%>
			<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	
	<p class="txttitle">
		<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.templateSet" />
	</p>
	<center>
	<script>
		Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
	</script> 
	<table id="Label_Tabel" width=95%>
		<tr LKS_LabelName="<bean:message bundle='sys-lbpmservice-support' key='lbpmEmbeddedSubFlow.basicInfo'/>">
			<td>
				<table class="tb_normal" width=100%>
					<html:hidden property="fdId" />
					<%--节点集名称--%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.fdName" />
						</td>
						<td width=85% colspan="3">
							<xform:text property="fdName" style="width:80%;" required="true"></xform:text>
						</td>
					</tr>
					<%--类别--%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.fdCatoryName" />
						</td>
						<td width=85% colspan="3">
							<xform:dialog required="true" subject="${lfn:message('sys-lbpmservice-support:lbpmEmbeddedSubFlow.fdCatoryName') }" propertyId="fdCategoryId" style="width:80%"
								propertyName="fdCategoryName" dialogJs="lbpmEmbeddedSubFlowCategoryTreeDialog()">
							</xform:dialog>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.state" />
						</td>
						<td width=85% colspan="3">
							<xform:radio property="fdIsAvailable" showStatus="edit" >
								<xform:simpleDataSource value="true"><bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.state.enable" />&nbsp;&nbsp;</xform:simpleDataSource>
								<xform:simpleDataSource value="false"><bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.state.disable" />&nbsp;&nbsp;</xform:simpleDataSource>
							</xform:radio>
							<c:if test="${lbpmEmbeddedSubFlowForm.fdIsAvailable==null }">
								<script type="text/javascript">
									$("input[name='fdIsMobileView']:first").attr('checked', 'checked');
								</script>
							</c:if>
						</td>
					</tr>
					<!-- 排序号 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.fdOrder" />
						</td>
						<td width=85% colspan="3">
							<xform:text property="fdOrder" style="width:80%;" validators="digits min(0)" />
						</td>
					</tr>
					 <!-- 使用范围 -->
					 <tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.scope" />
						</td>
						<td width=85% colspan="3">
							<!-- 被选中的模板ID集合“;”分割 -->
							<xform:text property="fdScopeId" showStatus="noShow"></xform:text>
							<xform:textarea property="fdScopeName" showStatus="edit" style="width:96%" htmlElementProperties="readOnly onclick='lbpmEmbeddedSubFlowCategoryScopeDialog();'"></xform:textarea>
							<a href="javascript:void(0);" onclick="lbpmEmbeddedSubFlowCategoryScopeDialog();"><bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.selectScope" /></a>
							<br>
							<bean:message key="lbpmEmbeddedSubFlow.scope.desc" bundle="sys-lbpmservice-support"/>
						</td>
					</tr>
					<%--说明--%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message key="lbpmEmbeddedSubFlow.fdDesc" bundle="sys-lbpmservice-support"/>
						</td>
						<td width=85% colspan="3"><html:textarea property="fdDesc" style="width:97%;" /></td>
					</tr>
					<!-- 可维护者 -->
					<tr>
						<td class="td_normal_title" width=15%><bean:message key="model.tempEditorName" /></td>
						<td colspan="3">
							<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" style="width:97%;height:90px;" ></xform:address>
							<div class="description_txt">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.description.cate.tempEditor" />
							</div>
						</td>
					</tr>
					<%---新建时，不显示 创建人，创建时间 ---%>
	               <c:if test="${lbpmEmbeddedSubFlowForm.method_GET=='edit'}">
						<tr>
							<!-- 创建人员 -->
							<td class="td_normal_title" width=15%>
								<bean:message key="model.fdCreator" />
							</td>
							<td width=35%>
								<html:text property="docCreatorName" readonly="true" style="width:50%;" />
							</td>
							
							<!-- 创建时间 -->
							<td class="td_normal_title" width=15%>
								<bean:message key="model.fdCreateTime" />
							</td>
							<td width=35%>
								<html:text property="docCreateTime" readonly="true" style="width:50%;" />
							</td>
						</tr>
						<c:if test="${not empty lbpmEmbeddedSubFlowForm.docAlterorName}">
							<tr>
								<!-- 修改人 -->
								<td class="td_normal_title" width=15%>
									<bean:message key="model.docAlteror" />
								</td>
								<td width=35%>
									<html:text property="docAlterorName" readonly="true" style="width:50%;" />
								</td>
								
								<!-- 修改时间 -->
								<td class="td_normal_title" width=15%>
									<bean:message key="model.fdAlterTime" />
								</td>
								<td width=35%>
									<html:text property="docAlterTime" readonly="true" style="width:50%;" />
								</td>
							</tr>
						</c:if>
					</c:if>
			</table>
			</td>
		</tr>

		<tr id="embeddedFlowTr" LKS_LabelName="<bean:message bundle='sys-lbpmservice-support' key='lbpmEmbeddedSubFlow.flowInfo'/>">
		 	<td>
				<table width="100%" class="tb_normal">
					<tr>
						<td colspan="2">
							<html:hidden property="fdIsModified" />
							<html:textarea property="fdContent" style="display:none"/>
							<iframe src="<c:url value="/sys/lbpm/flowchart/page/panel.html" />?embedded=true&edit=true&extend=oa&template=true&contentField=fdContent&isEmpty=true&FormFieldList=FlowChartObject.FormFieldList&modelName=${lbpmEmbeddedSubFlowForm.fdScopeId}"
									style="width:100%;height:500px" scrolling="no" id="Embedded_WF_IFrame"></iframe>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.paramsConfig" /></td>
						<td width="85%">
							<table class="tb_normal" width=100% id="TABLE_DocList_Details" align="center" style="table-layout:fixed;" frame=void>
								<tr>
									<td width="70px;" align="center">
										<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
									</td>
									<td width="40%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.fdParamName" /></td>
									<td width="40%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.fdParamType" /></td>
									<td  align="center">
										<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRow();" style="cursor:pointer">
									</td>
								</tr>
								<!-- 基准行 -->
								<tr KMSS_IsReferRow="1" style="display: none" class="content">
									<!-- 序号列，KMSS_IsRowIndex = 1 -->
									<td KMSS_IsRowIndex="1" align="center">
										!{index}
									</td>
									<td>
										<input type="hidden" name="fdParamValue">
										<xform:text property="fdParamName" subject="${lfn:message('sys-lbpmservice-support:lbpm.embeddedsubflow.fdParamName') }" required="true" style="width:90%" onValueChange="change"></xform:text>
									</td>
									<td>
										<xform:select property="fdParamType" required="true" style="width:90%;" subject="${lfn:message('sys-lbpmservice-support:lbpm.embeddedsubflow.fdParamType') }" onValueChange="switchType">
											<xform:enumsDataSource enumsType="lbpm_embeddedsubflow_param_type" />
										</xform:select>
										<div class="isMulti" style="display:none">
											<xform:checkbox property="fdIsMulti[!{index}]" style="display:none" onValueChange="change">
												<xform:simpleDataSource value="true" ><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.fdIsMulti" /></xform:simpleDataSource>
											</xform:checkbox>
										</div>
									</td>
									<td>
										<div style="text-align:center">
											<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRow();" style="cursor:pointer">
											<img class="paramDelBtn"  src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="DocList_DeleteRow();" style="cursor:pointer;margin-left:2px;">
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
		 	</td>
		</tr>
	</table>
	</center>

	<html:hidden property="method_GET" />
	<script>
		$KMSSValidation();
		//预加载10个参数随机ID
		var ids = Data_GetRadomId(10);

		function addParamRow(){
			if(ids.length <= 0){
				//重新加载10个
				ids = Data_GetRadomId(10);
			}
			var id = ids[0];
			ids.splice(0,1);
			var fieldValues = new Object();
			fieldValues["fdParamValue"]=id;
			DocList_AddRow("TABLE_DocList_Details",null,fieldValues);
		}

		function switchType(value,obj){
			var $tr = $(obj).closest("tr");
			if(value && value.indexOf('ORG_TYPE_') !=-1){
				$tr.find("div.isMulti").show();
			}else{
				$tr.find("div.isMulti").hide();
			}
			$tr.find("[name='fdIsMulti']").val("");
			$tr.find("[name='fdIsMulti']").removeAttr("checked");
			change();
		}

		function change() {
			var iframe = document.getElementById("Embedded_WF_IFrame").contentWindow;
			if(iframe && iframe.FlowChartObject){
				var LBPM_Template_FormFieldList = _GetSysDictObj();
				var otherParam = getOtherParam();
				if(otherParam.length>0){
					LBPM_Template_FormFieldList = LBPM_Template_FormFieldList.concat(otherParam);
				}
				iframe.FlowChartObject.FormFieldList = LBPM_Template_FormFieldList;
			}
		}

		function getOtherParam() {
			var varInfo = [];
			$("#TABLE_DocList_Details .content").each(function(){
				var fdParamValue = $(this).find("[name='fdParamValue']").val();
				var fdParamName = $(this).find("[name='fdParamName']").val();
				var fdParamType = $(this).find("[name='fdParamType']").val();
				if(fdParamValue && fdParamName && fdParamType){
					var fdIsMulti = $(this).find("[name^='fdIsMulti']").val();
					if(fdParamType && fdParamType.indexOf("ORG_TYPE_") != -1){
						if(fdParamType=='ORG_TYPE_PERSON'){
							if(fdIsMulti=="true"){
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson[]";
							}else{
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson";
							}
						}else{
							if(fdIsMulti=="true"){
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgElement[]";
							}else{
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgElement";
							}
						}
					}
					varInfo.push({"name":fdParamValue,"label":fdParamName,"type":fdParamType});
				}
			});
			return varInfo;
		}

		function setOtherParam(processData) {
			var content = [];
			$("#TABLE_DocList_Details .content").each(function(){
				var fdParamValue = $(this).find("[name='fdParamValue']").val();
				var fdParamName = $(this).find("[name='fdParamName']").val();
				var fdParamType = $(this).find("[name='fdParamType']").val();
				var fdIsMulti = $(this).find("[name^='fdIsMulti']").val();
				content.push({"fdParamValue":fdParamValue,"fdParamName":fdParamName,"fdParamType":fdParamType,"fdIsMulti":fdIsMulti});
			});
			processData.otherParams = JSON.stringify(content);
		}
	</script>
</html:form>

<%@ include file="/resource/jsp/edit_down.jsp"%>
