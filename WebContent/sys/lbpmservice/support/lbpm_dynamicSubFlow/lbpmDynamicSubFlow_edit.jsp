<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<template:include ref="config.profile.list" sidebar="no">
	<template:replace name="content">
		<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
		<script language="JavaScript">
			Com_IncludeFile("dialog.js|formula.js|data.js|doclist.js");
		</script>
		<script language="JavaScript">
			DocList_Info.push("TABLE_DocList_Details");
			DocList_Info.push("TABLE_DocList_Detail");
			//系统初始化,控制片段组参数配置下拉框失效及非组织架构类型不显示‘是否多值’
			Com_AddEventListener(window, "load", function() {
				$("#TABLE_DocList_Details .content").each(function(){
					$(this).find("[name='fdParamType']").prop("disabled","true");
					var paramTypeValue = $(this).find("[name='fdParamType']").val();
					var $tr = $(this).find("div[class='isMulti']")[0];
					if(paramTypeValue.indexOf("ORG_TYPE_") != -1){
						$tr.style.display='';
					}else{
						$tr.style.display='none';
					}
				})
			});
			// 提交校验
			Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
				var content = [];
				var alias = [];
				var paramss = [];
				var nodeParamContents = [];
				$("#TABLE_DocList_Detail .content").each(function(){
					var paramContents = [];
					var fdNodeGroupName = $(this).find("[name='wf_embeddedName']").val();
					var fdNodeGroupRefId = $(this).find("[name='wf_embeddedRefId']").val();
					var fdAlias = $(this).find("[name='fdAlias']").val();
					var status = $(this).find("[name='isAvailable']").val();
					var fdParamContent = $(this).find("[name='fdParamContent']").val();
					var	fdNodeGroupId = $(this).find("[name='nodeGroupId']").val();
					var	fdCopyId = $(this).find("[name='copyId']").val();
					var nodeGroupParam = $(this).find("[name='paramConfig']");
					alias.push(fdAlias);
					//找出有参数配置按钮的数据放到数组列表
					if($(nodeGroupParam).css("display") != 'none' && nodeGroupParam.length != 0){
						paramss.push('ss');
					}
					//找出参数配置为空的数据push到数组
					if(fdParamContent != ''){
						nodeParamContents.push(fdParamContent);
					}
					if(undefined == fdNodeGroupId){
						fdNodeGroupId = "";
					}
					if(undefined == fdCopyId){
						fdCopyId = "";
					}else{
						if(fdParamContent != ''){
							var paramsConfig = JSON.parse(fdParamContent);
							if(undefined != paramsConfig){
								for(var i=0;i<paramsConfig.length;i++){
									var paramValue = paramsConfig[i].fdParamValue.split("_");
									var fdParamData = paramValue[2];
									var fdParamValue = "nodeGroup_"+fdCopyId+"_"+paramValue[2];
									var fdParamName = paramsConfig[i].fdParamName;
									var fdParamType = paramsConfig[i].fdParamType;
									var fdType = paramsConfig[i].fdType;
									var fdIsMuti = paramsConfig[i].fdIsMuti;
									var fdFactParamName = paramsConfig[i].fdFactParamName;
									var fdFactParamValue = paramsConfig[i].fdFactParamValue;
									var orgOrFormula = paramsConfig[i].orgOrFormula;
									paramContents.push({"fdParamData":fdParamData,"fdParamValue":fdParamValue,"fdParamName":fdParamName,"fdType":fdType,"fdParamType":fdParamType,"fdIsMuti":fdIsMuti,"fdFactParamName":fdFactParamName,"fdFactParamValue":fdFactParamValue,"orgOrFormula":orgOrFormula});
								}
								fdParamContent = paramContents;
							}
						}
					}
					content.push({"fdNodeGroupName":fdNodeGroupName,"fdNodeGroupId":fdNodeGroupRefId,"fdAlias":fdAlias,"status":status,"fdParamContent":fdParamContent,"fdId":fdNodeGroupId,"fdCopyId":fdCopyId});
				});
				var data = JSON.stringify(content);
				$("textarea[name='fdContent']").val(data);

				//片段组参数配置数据组装
				var paramContent = [];
				$("#TABLE_DocList_Details .content").each(function(){
					var fdParamValue = $(this).find("[name='fdParamValue']").val();
					var fdParamName = $(this).find("[name='fdParamName']").val();
					var fdParamType = $(this).find("[name='fdParamType']").val();
					var fdIsMulti = $(this).find("[name^='fdIsMulti']").val();
					paramContent.push({"fdParamValue":fdParamValue,"fdParamName":fdParamName,"fdParamType":fdParamType,"fdIsMulti":fdIsMulti});
				});
				var paramData = JSON.stringify(paramContent);
				$("textarea[name='fdParamSetContent']").val(paramData);
				//片段组为空校验
				if(alias.length == 0){
					alert("片段组不能为空，请选择节点集！");
					return false;
				}
				//别名重复校验
				for (var i = 0; i < alias.length; i++) {
					if(alias.indexOf(alias[i]) != i) {
						alert("该别名重复：" + alias[i]+"，请修改！");
						return false;
					}
				}
				//校验节点集参数配置不能为空
				if(paramss.length != nodeParamContents.length){
					alert("有参数配置为空的行数，请填加参数配置！");
					return false;
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

		</script>
		<kmss:windowTitle moduleKey="sys-lbpmservice-support:table.lbpmEmbeddedSubFlow" subjectKey="sys-lbpmservice-support:lbpmDynamicSubFlow.partgroupSet" subject="${lbpmDynamicSubFlowForm.fdName}" />
		<html:form action="/sys/lbpmservice/support/lbpmDynamicSubFlow.do" >
			<div id="optBarDiv">
				<c:if test="${lbpmDynamicSubFlowForm.method_GET=='edit'}">
					<%--更新--%>
					<input type=button value="<bean:message key="button.update"/>"
						onclick="Com_Submit(document.lbpmDynamicSubFlowForm, 'update');">
				</c:if>
				 <c:if test="${lbpmDynamicSubFlowForm.method_GET=='add' || lbpmDynamicSubFlowForm.method_GET=='clone'}">
					<%--新增--%>
					<input type=button value="<bean:message key="button.save"/>"
						onclick="Com_Submit(document.lbpmDynamicSubFlowForm, 'save');">
					<input type=button value="<bean:message key="button.saveadd"/>"
						onclick="Com_Submit(document.lbpmDynamicSubFlowForm, 'saveadd');">
				</c:if>
					<%--关闭--%>
					<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
			</div>

			<p class="txttitle">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.partgroupSet" />
			</p>
			<center>
			<script>
				Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
			</script>
			<table id="Label_Tabel" width=95%>
				<tr LKS_LabelName="<bean:message bundle='sys-lbpmservice-support' key='lbpmDynamicSubFlow.basicInfo'/>">
					<td>
						<table class="tb_normal" width=100%>
							<html:hidden property="fdId" />
							<%--节点集名称--%>
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.fdName" />
								</td>
								<td width=85% colspan="3">
									<xform:text property="fdName" style="width:80%;" required="true"></xform:text>
								</td>
							</tr>
							<%--类别--%>
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.fdCatoryName" />
								</td>
								<td width=85% colspan="3">
									<xform:dialog required="true" subject="${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.fdCatoryName') }" propertyId="fdCategoryId" style="width:80%"
										propertyName="fdCategoryName" dialogJs="lbpmEmbeddedSubFlowCategoryTreeDialog()">
									</xform:dialog>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.state" />
								</td>
								<td width=85% colspan="3">
									<xform:radio property="fdIsAvailable" showStatus="edit" >
										<xform:simpleDataSource value="true"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.state.enable" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="false"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.state.disable" />&nbsp;&nbsp;</xform:simpleDataSource>
									</xform:radio>
									<c:if test="${lbpmDynamicSubFlowForm.fdIsAvailable==null }">
										<script type="text/javascript">
											$("input[name='fdIsMobileView']:first").attr('checked', 'checked');
										</script>
									</c:if>
								</td>
							</tr>
							<!-- 排序号 -->
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.fdOrder" />
								</td>
								<td width=85% colspan="3">
									<xform:text property="fdOrder" style="width:80%;" validators="digits" />
								</td>
							</tr>
							<%--说明--%>
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message key="lbpmDynamicSubFlow.fdDesc" bundle="sys-lbpmservice-support"/>
								</td>
								<td width=85% colspan="3"><html:textarea property="fdDesc" style="width:97%;" /></td>
							</tr>
							<!-- 可维护者 -->
							<tr>
								<td class="td_normal_title" width=15%><bean:message key="model.tempEditorName" /></td>
								<td colspan="3">
									<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" style="width:97%;height:90px;" ></xform:address>
									<div class="description_txt">
										<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.description.cate.tempEditor" />
									</div>
								</td>
							</tr>
							<%---新建时，不显示 创建人，创建时间 ---%>
						   <c:if test="${lbpmDynamicSubFlowForm.method_GET=='edit'}">
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
								<c:if test="${not empty lbpmDynamicSubFlowForm.docAlterorName}">
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

				<tr id="dynamicFlowNodeGroupTr" LKS_LabelName="<bean:message bundle='sys-lbpmservice-support' key='lbpmDynamicSubFlow.nodeGroupInfo'/>">
					<td>
						<html:textarea property="fdContent" style="display:none"/>
						<table class="tb_normal" width=100% id="TABLE_DocList_Detail" align="center" style="table-layout:fixed;" frame=void>
							<tr>
								<td width="70px;" align="center">
									<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
								</td>
								<td width="20%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.nodeGroup" /></td>
								<td width="30%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.alias" /></td>
								<%--<td width="10%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.dynamicCreateBranch" /></td>--%>
								<td width="10%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.state" /></td>
								<td width="30%"  align="center">
									<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.operation" />
									<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRows();" style="cursor:pointer">
								</td>
							</tr>
							<tr KMSS_IsReferRow="1" style="display: none" class="content">
								<td KMSS_IsRowIndex="1" align="center">
									!{index}
								</td>
								<td>
									<html:textarea property="fdParamContent" style="display:none"/>
									<input name="wf_embeddedRefId" type="hidden"/>
									<input name="wf_embeddedName" class="inputsgl" style="width:200px" readonly />
									<span id="SPAN_SelectType1">
									<a href="javascript:void(0);" onclick="selectEmbeddedSubFlow(this,false,'${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.select.nodeGroup') }');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
									</span>
								</td>
								<td>
									<xform:text property="fdAlias" subject="${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.alias') }" required="true" style="width:90%"></xform:text>
								</td>
								<!-- 是否动态生成分支，暂时不支持 -->
								<%--<td align="center">
									<ui:switch property="isDynamicCreate"></ui:switch>
								</td>--%>
								<!-- 是否动态生成分支，暂时不支持 -->
								<td class="fdStatus" align="center">
									<input type="hidden" name="isAvailable"/>
								</td>
								<td>
									<input name="nodeGroupId" type="hidden"/>
									<div style="text-align:center">
										<a name="paramConfig" style="display: none" href="javascript:void(0);" onclick="dynamicConfigSet(this);"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.paramSet" /></a>
										<a name="copyThat" style="display: none" href="javascript:void(0);" onclick="dynamicCopy(this);"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.copy" /></a>
										<%--<a href="javascript:void(0);" style="display: none" onclick="dynamicCreateRule(false, 'wf_embeddedRefId', 'wf_embeddedName', '选择节点集');">生成规则</a>--%>
										<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRows();" style="cursor:pointer">
										<img class="paramDelBtn"  src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="DocList_DeleteRow();" style="cursor:pointer;margin-left:2px;">
									</div>
								</td>
							</tr>
							<c:forEach items="${nodeGroupList}" var="lbpmDynamicSubFlowNodeGroup" varStatus="status">
							<tr KMSS_IsContentRow="1" class="content">
								<td KMSS_IsRowIndex="1" align="center">
									${status.index+1}
								</td>
								<td>
									<html:textarea value="${lbpmDynamicSubFlowNodeGroup.fdParamContent}" property="fdParamContent" style="display:none"/>
									<input name="wf_embeddedRefId" type="hidden" value="${lbpmDynamicSubFlowNodeGroup.fdRefId}"/>
									<input name="wf_embeddedName" class="inputsgl" value="${lbpmDynamicSubFlowNodeGroup.nodeGroupName}" style="width:200px" readonly />
									<span id="SPAN_SelectType1">
									<a href="javascript:void(0);" onclick="selectEmbeddedSubFlow(this,false,'${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.select.nodeGroup') }');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
								</span>
								</td>
								<td>
									<xform:text property="fdAlias" value="${lbpmDynamicSubFlowNodeGroup.nodeGroupAlias}" subject="${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.alias') }" required="true" style="width:90%"></xform:text>
								</td>
								<!-- 是否动态生成分支，暂时不支持 -->
								<%--<td align="center">
									<c:if test="${lbpmDynamicSubFlowNodeGroup.isDynamicCreateBranch == '1'}">
									<ui:switch property="isDynamicCreate" checked="true"></ui:switch>
									</c:if>
									<c:if test="${lbpmDynamicSubFlowNodeGroup.isDynamicCreateBranch == '0'}">
										<ui:switch property="isDynamicCreate" checked="false"></ui:switch>
									</c:if>
								</td>--%>
								<!-- 是否动态生成分支，暂时不支持 -->
								<td class="fdStatus" align="center">
									<c:if test="${lbpmDynamicSubFlowNodeGroup.status == '1'}">
										<input type="hidden" name="isAvailable" value="true"/>
										<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.status.enable"/>
									</c:if>
									<c:if test="${lbpmDynamicSubFlowNodeGroup.status == '0'}">
										<input type="hidden" name="isAvailable" value="false"/>
										<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.status.disable"/>
									</c:if>
								</td>
								<td>
									<input name="nodeGroupId" type="hidden" value="${lbpmDynamicSubFlowNodeGroup.fdId}"/>
									<div style="text-align:center">
										<c:if test="${lbpmDynamicSubFlowNodeGroup.isParamConfig == '1'}">
											<a name="paramConfig" href="javascript:void(0);" onclick="dynamicConfigSet(this);"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.paramSet" /></a>
										</c:if>
										<a href="javascript:void(0);" onclick="dynamicCopy(this);"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.copy" /></a>
										<%--<c:if test="${lbpmDynamicSubFlowNodeGroup.isDynamicCreateBranch == '1'}">
											<a href="javascript:void(0);" onclick="dynamicCreateRule(false, 'wf_embeddedRefId', 'wf_embeddedName', '选择节点集');">生成规则</a>
										</c:if>--%>
										<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRows();" style="cursor:pointer">
										<img class="paramDelBtn"  src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="DocList_DeleteRow();" style="cursor:pointer;margin-left:2px;">
									</div>
								</td>
							</tr>
							</c:forEach>

						</table><br/>
						<table width="100%" class="tb_normal">
							<tr>
								<td class="td_normal_title" width="15%"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.paramSet" /></td>
								<td width="85%">
									<html:textarea property="fdParamSetContent" style="display:none"/>
									<table class="tb_normal" width=100% id="TABLE_DocList_Details" align="center" style="table-layout:fixed;" frame=void>
										<tr>
											<td width="70px;" align="center">
												<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
											</td>
											<td width="40%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.part.paramName" /></td>
											<td width="40%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.part.paramType" /></td>
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
												<xform:text property="fdParamName" subject="${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.part.paramName') }" required="true" style="width:90%"></xform:text>
											</td>
											<td>
												<xform:select property="fdParamType" required="true" style="width:90%;" subject="${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.part.paramType') }" onValueChange="switchType">
													<xform:enumsDataSource enumsType="lbpm_embeddedsubflow_param_type" />
												</xform:select>
												<div class="isMulti" style="display:none">
													<xform:checkbox property="fdIsMulti[!{index}]" style="display:none">
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
										<!-- 内容行 -->
										<c:forEach items="${partParamConfig}" var="partParam" varStatus="status">
										<tr KMSS_IsContentRow="1" class="content">
											<td KMSS_IsRowIndex="1" align="center">
												${status.index+1}
											</td>
											<td>
												<input type="hidden" name="fdParamValue" value="${partParam.fdParamValue}">
												<xform:text property="fdParamName" value="${partParam.fdParamName}" subject="${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.part.paramName') }" required="true" style="width:90%"></xform:text>
											</td>
											<td>
												<xform:select property="fdParamType" value="${partParam.fdParamType}" required="true" style="width:90%;" subject="${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.part.paramType') }">
													<xform:enumsDataSource enumsType="lbpm_embeddedsubflow_param_type" />
												</xform:select>
												<div class="isMulti">
													<xform:checkbox property="fdIsMulti[${status.index}]" value="${partParam.fdIsMulti}">
														<xform:simpleDataSource value="true" ><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.fdIsMulti" /></xform:simpleDataSource>
													</xform:checkbox>
												</div>
											</td>
											<td>
												<div style="text-align:center">
													<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRow();" style="cursor:pointer">
													<img class="paramDelBtn"  src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="deleteDocListRows();" style="cursor:pointer;margin-left:2px;">
												</div>
											</td>
										</tr>
										</c:forEach>
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
				var ids = Data_GetRadomId(100);

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

				function addParamRows(){
					if(ids.length <= 0){
						//重新加载10个
						ids = Data_GetRadomId(10);
					}
					var id = ids[0];
					ids.splice(0,1);
					var fieldValues = new Object();
					fieldValues["nodeGroupId"]=id;
					DocList_AddRow("TABLE_DocList_Detail",null,fieldValues);
				}

				function selectEmbeddedSubFlow(res,isMulti,title){
					var obj = $(res).parents("tr").eq(0).find("a[name='paramConfig']")[0]; //获取参数配置的dom
					var copyThat = $(res).parents("tr").eq(0).find("a[name='copyThat']")[0]; //获取复制的dom
					var idField = $(res).parents("tr").eq(0).find("input[name='wf_embeddedRefId']")[0]; //节点集选择后的id
					var nameField = $(res).parents("tr").eq(0).find("input[name='wf_embeddedName']")[0]; //节点集选择后的name
					var alias = $(res).parents("tr").eq(0).find("input[name='fdAlias']")[0]; //节点集别名
					var isAvailable = $(res).parents("tr").eq(0).find("input[name='isAvailable']")[0]; //状态：节点集是否开启
					var fdStatus = $(res).parents("tr").eq(0).find("[class='fdStatus']")[0];
					//构造节点集单选弹窗
					var dialog = new KMSSDialog(isMulti, true);
					dialog.BindingField(idField, nameField);
					dialog.winTitle = title;
					//构造树菜单
					var node = dialog.CreateTree(title);
					//绑定节点集数据
					node.AppendBeanData("lbpmEmbeddedSubFlowTreeService&type=cate&cateId=!{value}","lbpmEmbeddedSubFlowTreeService&type=nodeGroup&cateId=!{value}");
					dialog.SetAfterShow(function(rtnVal){
						if (rtnVal != null && rtnVal.data && rtnVal.data.length>0) {
							//从大字段中获取节点集参数配置信息
							var content = rtnVal.data[0].content;
							var status = rtnVal.data[0].isAvailable;
							var processData = WorkFlow_LoadXMLData(content);
							//别名初始化为节点集选择后名称
							$(alias).val($(nameField).val());
							//设置节点集状态
							$(isAvailable).val(status);
							if(status == 'true'){
								$(fdStatus).html('<input type="hidden" name="isAvailable" value="true"/> <bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.status.enable"/>');
							}else{
								$(fdStatus).html('<input type="hidden" name="isAvailable" value="false"/> <bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.status.disable"/>');
							}
							$(copyThat).show();
							//判断节点集是否配置参数信息，如果配置则显示‘参数配置’弹窗，若无则隐藏
							if(processData.otherParams != "[]" && processData.otherParams != null && processData.otherParams != ""){
								$(obj).show();
							}else {
								$(obj).hide();
							}
						}
					});
					dialog.Show();
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
				}
				//复制方法
				function dynamicCopy(obj){
					if(ids.length <= 0){
						//重新加载10个
						ids = Data_GetRadomId(10);
					}
					var id = ids[0];
					ids.splice(0,1);
					var fieldValues = new Object();
					var nodeGroupRefId = $(obj).parents("tr").eq(0).find("input[name='wf_embeddedRefId']")[0];
					nodeGroupRefId = $(nodeGroupRefId).val();
					var nodeGroupName = $(obj).parents("tr").eq(0).find("input[name='wf_embeddedName']")[0];
					nodeGroupName = $(nodeGroupName).val();
					var nodeGroupAlias = $(obj).parents("tr").eq(0).find("input[name='fdAlias']")[0];
					nodeGroupAlias = $(nodeGroupAlias).val();
					var nodeGroupIsAvailable = $(obj).parents("tr").eq(0).find("input[name='isAvailable']")[0];
					nodeGroupIsAvailable = $(nodeGroupIsAvailable).val();
					var fdParamContent = $(obj).parents("tr").eq(0).find("textarea[name='fdParamContent']")[0];
					fdParamContent = $(fdParamContent).val();
					var nodeGroupId = $(obj).parents("tr").eq(0).find("input[name='nodeGroupId']")[0];
					nodeGroupId = $(nodeGroupId).val();
					//获取被复制行的参数配置按钮dom
					var paramConfigButton = $(obj).parents("tr").eq(0).find("a[name='paramConfig']")[0];
					fieldValues["wf_embeddedRefId"]=nodeGroupRefId;
					fieldValues["wf_embeddedName"]=nodeGroupName;
					fieldValues["fdAlias"]=nodeGroupAlias;
					fieldValues["isAvailable"]=nodeGroupIsAvailable;
					fieldValues["fdParamContent"]=fdParamContent;
					fieldValues["nodeGroupId"]=nodeGroupId;
					var newRow = DocList_AddRow("TABLE_DocList_Detail",null,fieldValues);
					var fdStatus = $(newRow).find("[class='fdStatus']")[0];
					if(nodeGroupIsAvailable == 'true'){
						$(fdStatus).append('<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.status.enable"/>');
					}else{
						$(fdStatus).append('<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.status.disable"/>');
					}
					//标识复制行的copyId
					$(fdStatus).append('<input name="copyId" type="hidden" value="'+id+'"/>');
					//获取新复制行的节点集参数配置内容
					var newRowParamContent = $(newRow).find("textarea[name='fdParamContent']")[0];
					var paramConfig = $(newRow).find("a[name='paramConfig']")[0]; //获取参数配置的dom
					var copyThat = $(newRow).find("a[name='copyThat']")[0]; //获取复制的dom
					//判断被复制行是否显示了参数配置按钮，如果显示则复制行显示参数配置按钮，反之不显示
					if(undefined != paramConfigButton && $(paramConfigButton).css("display") != 'none'){
						$(paramConfig).show();
					}
					$(copyThat).show();
				}

				//参数配置
				function dynamicConfigSet(obj){
					seajs.use(['lui/dialog','lui/topic'],function(dialog, topic) {
						var nodeGroup = $(obj).parents("tr").eq(0).find("input[name='wf_embeddedRefId']")[0];
						var groupId = $(obj).parents("tr").eq(0).find("input[name='nodeGroupId']")[0];
						var nodeGroupId = $(groupId).val();
						var nodeGroupRefId = $(nodeGroup).val();
						var fdCopyId = $(obj).parents("tr").eq(0).find("input[name='copyId']")[0];
						if(undefined != fdCopyId){
							fdCopyId = $(fdCopyId).val();
						}
						//整合片段组参数传递到节点集参数配置页面
						var content = [];
						$("#TABLE_DocList_Details .content").each(function(){
							var fdPartGroupParamId = $(this).find("[name='fdParamValue']").val();
							var fdPartGroupParamName = $(this).find("[name='fdParamName']").val();
							var fdPartGroupParamType = $(this).find("[name='fdParamType']").val();
							if(fdPartGroupParamId && fdPartGroupParamName && fdPartGroupParamType) {
								var fdPartGroupParamIsMulti = $(this).find("[name^='fdIsMulti']").val();
								if (fdPartGroupParamType && fdPartGroupParamType.indexOf("ORG_TYPE_") != -1) {
									if (fdPartGroupParamType == 'ORG_TYPE_PERSON') {
										if (fdPartGroupParamIsMulti == "true") {
											fdPartGroupParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson[]";
										} else {
											fdPartGroupParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson";
										}
									} else {
										if (fdPartGroupParamIsMulti == "true") {
											fdPartGroupParamType = "com.landray.kmss.sys.organization.model.SysOrgElement[]";
										} else {
											fdPartGroupParamType = "com.landray.kmss.sys.organization.model.SysOrgElement";
										}
									}
								}
								if(fdPartGroupParamType && fdPartGroupParamType.indexOf("BigDecimal_") != -1){
									fdPartGroupParamType = "BigDecimal";
								}
							}
							content.push({"name":fdPartGroupParamId,"label":fdPartGroupParamName,"type":fdPartGroupParamType});
						});
							var fdParamContent = $(obj).parents("tr").eq(0).find("textarea[name='fdParamContent']")[0];
							var paramss = {
								paramCon:$(fdParamContent).val()
							};
							parent.Com_Parameter.Dialog = paramss;
							dialog.iframe("/sys/lbpmservice/support/lbpmDynamicSubFlow.do?method=getContentByRefId&fdRefId="+nodeGroupRefId+"&fdNodeGroupId="+nodeGroupId+"&flag=edit&fdCopyId="+fdCopyId,
									"节点集参数配置",
									function(rtn){
										if(null != rtn){
											$(fdParamContent).val(rtn.paramContents);
										}
									},
									{
										width:700,
										height:500,
										params:{
											paramContent:content
										}
									}
							);
					});
				}
				function deleteDocListRows(optTR){
					seajs.use(['lui/dialog','lui/topic'],function(dialog, topic) {
						if (optTR == null)
							optTR = DocListFunc_GetParentByTagName("TR");
						//操作行不允许删除
						if (optTR.getAttribute('type') == 'optRow') {
							return;
						}
						var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
						//增加表格操作事件 作者 曹映辉 #日期 2014年6月19日
						$(optTB).trigger($.Event("table-delete"), optTR);
						var tbInfo = DocList_TableInfo[optTB.id];
						var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
						var index = DocList_GetRowIndex(tbInfo, optTR);
						dialog.confirm('<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.part.deleteRows"/>', function(value) {
							if(value == true){
								optTB.deleteRow(rowIndex);
								tbInfo.lastIndex--;
								for (var i = rowIndex; i < tbInfo.lastIndex; i++)
									DocListFunc_RefreshIndex(tbInfo, i, index++);
								$(optTB).trigger($.Event("table-delete-finish"), {'row': optTR, 'table': optTB});
							}
						});
					});
				}
			</script>
		</html:form>
		<%@ include file="/resource/jsp/edit_down.jsp"%>
	</template:replace>
</template:include>