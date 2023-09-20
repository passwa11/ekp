<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<template:include ref="config.profile.list" sidebar="no">
	<template:replace name="content">
	<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
	<script language="JavaScript">
		Com_IncludeFile("dialog.js|formula.js|data.js|doclist.js");
		function confirmDelete(msg) {
			var del = confirm("<bean:message key="page.comfirmDelete"/>");
			return del;
		}
	</script>
	<script language="JavaScript">
		DocList_Info.push("TABLE_DocList_Details");
		DocList_Info.push("TABLE_DocList_Detail");
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
	<div id="optBarDiv">
		<kmss:auth
				requestURL="/sys/lbpmservice/support/lbpmDynamicSubFlow.do?method=edit&fdId=${param.fdId}"
				requestMethod="GET">
			<input type="button" value="<bean:message key="button.edit"/>"
				   onclick="Com_OpenWindow('lbpmDynamicSubFlow.do?method=edit&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
		<kmss:auth
				requestURL="/sys/lbpmservice/support/lbpmDynamicSubFlow.do?method=delete&fdId=${param.fdId}"
				requestMethod="GET">
			<input type="button" value="<bean:message key="button.delete"/>"
				   onclick="if(!confirmDelete())return;Com_OpenWindow('lbpmDynamicSubFlow.do?method=delete&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
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
										<bean:write name="lbpmDynamicSubFlowForm" property="fdName" />
									</td>
								</tr>
									<%--类别--%>
								<tr>
									<td class="td_normal_title" width=15%>
										<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.fdCatoryName" />
									</td>
									<td width=85% colspan="3">
										<bean:write name="lbpmDynamicSubFlowForm" property="fdCategoryName" />
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width=15%>
										<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.state" />
									</td>
									<td width=85% colspan="3">
										<c:if test="${lbpmDynamicSubFlowForm.fdIsAvailable}">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.fdIsAvailable.true" />
										</c:if>
										<c:if test="${!lbpmDynamicSubFlowForm.fdIsAvailable}">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.fdIsAvailable.false" />
										</c:if>
									</td>
								</tr>
								<!-- 排序号 -->
								<tr>
									<td class="td_normal_title" width=15%>
										<bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.fdOrder" />
									</td>
									<td width=85% colspan="3">
										<bean:write property="fdOrder" name="lbpmDynamicSubFlowForm" />
									</td>
								</tr>
									<%--说明--%>
								<tr>
									<td class="td_normal_title" width=15%>
										<bean:message key="lbpmDynamicSubFlow.fdDesc" bundle="sys-lbpmservice-support"/>
									</td>
									<td width=85% colspan="3">
										<bean:write property="fdDesc" name="lbpmDynamicSubFlowForm" />
									</td>
								</tr>
								<!-- 可维护者 -->
								<tr>
									<td class="td_normal_title" width=15%><bean:message key="model.tempEditorName" /></td>
									<td colspan="3"><bean:write name="lbpmDynamicSubFlowForm" property="authEditorNames"/></td>
								</tr>
									<%---新建时，不显示 创建人，创建时间 ---%>
								<c:if test="${lbpmDynamicSubFlowForm.method_GET=='edit'}">
									<tr>
										<!-- 创建人员 -->
										<td class="td_normal_title" width=15%>
											<bean:message key="model.fdCreator" />
										</td>
										<td width=35%>
											<bean:write property="docCreatorName" name="lbpmDynamicSubFlowForm"/>
										</td>

										<!-- 创建时间 -->
										<td class="td_normal_title" width=15%>
											<bean:message key="model.fdCreateTime" />
										</td>
										<td width=35%>
											<bean:write property="docCreateTime"  name="lbpmDynamicSubFlowForm"/>
										</td>
									</tr>
									<c:if test="${not empty lbpmDynamicSubFlowForm.docAlterorName}">
										<tr>
											<!-- 修改人 -->
											<td class="td_normal_title" width=15%>
												<bean:message key="model.docAlteror" />
											</td>
											<td width=35%>
												<bean:write name="lbpmDynamicSubFlowForm" property="docAlterorName" />
											</td>

											<!-- 修改时间 -->
											<td class="td_normal_title" width=15%>
												<bean:message key="model.fdAlterTime" />
											</td>
											<td width=35%>
												<bean:write name="lbpmDynamicSubFlowForm" property="docAlterTime" />
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
									<a href="javascript:void(0);" ><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
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
										<%--是否动态生成分支，暂时不支持--%>
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
											<c:if test="${lbpmDynamicSubFlowNodeGroup.isParamConfig == '1'}">
												<div style="text-align:center">
													<a name="paramConfig" href="javascript:void(0);" onclick="dynamicConfigSet(this,'edit');"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.paramSet" /></a>
												</div>
											</c:if>
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
														<xform:select property="fdParamType" value="${partParam.fdParamType}" required="true" style="width:90%;" subject="${lfn:message('sys-lbpmservice-support:lbpmDynamicSubFlow.part.paramType') }" onValueChange="switchType">
															<xform:enumsDataSource enumsType="lbpm_embeddedsubflow_param_type" />
														</xform:select>
														<c:if test="${ not empty partParam.fdIsMulti }">
															<div class="isMulti">
																<xform:checkbox property="fdIsMulti[${status.index}]" value="${partParam.fdIsMulti}" style="display:none">
																	<xform:simpleDataSource value="true" ><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.fdIsMulti" /></xform:simpleDataSource>
																</xform:checkbox>
															</div>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- 引用模板 -->
					<tr LKS_LabelName="<bean:message bundle='sys-lbpmservice-support' key='lbpmDynamicSubFlow.refInfo'/>">
						<td>
							<ui:iframe src="${LUI_ContextPath}/sys/lbpmservice/support/lbpm_dynamicSubFlow/ref/lbpmDynamicSubFlowRef_list.jsp?fdDynamicId=${lbpmDynamicSubFlowForm.fdId}"></ui:iframe>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<script>
				function dynamicConfigSet(obj,res){
					seajs.use(['lui/dialog','lui/topic'],function(dialog, topic) {
						var nodeGroup = $(obj).parents("tr").eq(0).find("input[name='wf_embeddedRefId']")[0];
						var groupId = $(obj).parents("tr").eq(0).find("input[name='nodeGroupId']")[0];
						var nodeGroupId = $(groupId).val();
						var nodeGroupRefId = $(nodeGroup).val();
						var content = [];
						$("#TABLE_DocList_Details .content").each(function(){
							var fdPartGroupParamId = $(this).find("[name='fdParamValue']").val();
							var fdPartGroupParamName = $(this).find("[name='fdParamName']").val();
							var fdPartGroupParamType = $(this).find("[name='fdParamType']").val();
							var fdPartGroupParamIsMulti = $(this).find("[name^='fdIsMulti']").val();
							var cospan = fdPartGroupParamType+","+fdPartGroupParamIsMulti;
							content.push({"name":fdPartGroupParamId,"label":fdPartGroupParamName,"type":cospan});
						});
						dialog.iframe("/sys/lbpmservice/support/lbpmDynamicSubFlow.do?method=getContentByRefId&fdRefId="+nodeGroupRefId+"&fdNodeGroupId="+nodeGroupId+"&flag=view",
								"节点集参数配置",
								function(rtn){
									var fdParamContent = $(obj).parents("tr").eq(0).find("textarea[name='fdParamContent']")[0];
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
			</script>
		<%@ include file="/resource/jsp/view_down.jsp"%>
	</template:replace>
</template:include>