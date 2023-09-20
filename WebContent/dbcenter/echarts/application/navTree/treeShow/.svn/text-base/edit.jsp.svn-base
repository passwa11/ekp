<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" showQrcode="false">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ param.method == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="NavTree_Submit('update');"></ui:button>
				</c:when>
				<c:when test="${ param.method == 'add' || param.method == 'saveadd'}">	
					<ui:button text="${ lfn:message('button.save') }" onclick="NavTree_Submit('save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="NavTree_Submit('saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	<c:choose>
		<c:when test="${navTreeForm.method_GET=='add' }">
			<p class="txttitle"><c:out value="${ lfn:message('dbcenter-echarts-application:treeShow.add') }"></c:out></p>	
		</c:when>
		<c:otherwise>
			<p class="txttitle"><c:out value="${navTreeForm.fdName}"></c:out></p>
		</c:otherwise>
	</c:choose>
<script>
	Com_IncludeFile('doclist.js');
</script>
<html:form action="/dbcenter/echarts/application/dbEchartsNavTreeShow.do">
<center>
	<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}dbcenter/echarts/application/navTree/css/treeShow.css?s_cache=${LUI_Cache}" />
	<table class="tb_normal" width=95%> 
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="dbcenter-echarts-application" key="treeShow.name"/>
			</td>
			<td width="85%" colspan="3">
				<xform:text property="fdName" required="true"/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="dbcenter-echarts-application" key="treeShow.isEnable"/>
			</td>
			<td width="35%" >
				<xform:radio property="fdIsEnable">
					<xform:enumsDataSource enumsType="common_yesno" />
				</xform:radio>
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message key="model.fdOrder"/>
			</td>
			<td width="35%" >
				<xform:text property="fdOrder" validators="number"/>
			</td>
		</tr>
	</table>
	<div class="tableList">
		<div class="tableList-header">
			<bean:message bundle="dbcenter-echarts-application" key="treeShow.containChart"/>
		</div>
		<div clas="tableList-content">
			<table class="tb_normal" width="100%" id="TABLE_DocList">
				<tr align="center" class="tr_normal_title">
					<%--序号--%> 
					<td width="5%"><bean:message key="page.serial"/></td>
					<%-- 图表导航名称 --%>
					<td width="60%"><bean:message bundle="dbcenter-echarts-application" key="treeShow.item.name"/></td>
					<td width="30%"><bean:message bundle="dbcenter-echarts-application" key="treeShow.item.category"/></td>
					<td width="5%"><bean:message key="list.operation"/></td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display: none" align="center">
					<td KMSS_IsRowIndex="1" width="5%" align="center"></td>
					<td align="center">
						<xform:dialog required="true" subject="${lfn:message('dbcenter-echarts-application:treeShow.item.name') }" propertyId="fdNavTreeShowList[!{index}].fdNavTreeId" style="width:30%"
							propertyName="fdNavTreeShowList[!{index}].fdNavTitleTxt" dialogJs="NavTree_Dialog('fdNavTreeShowList[!{index}].fdNavTreeId','fdNavTreeShowList[!{index}].fdNavTitleTxt',this);">
						</xform:dialog>
						<xform:text property="fdNavTreeShowList[!{index}].fdNavTreeModelName" showStatus="noShow"/>
					</td>
					<td align="center">
						<input type="text" readOnly name="fdNavTreeShowList[!{index}].fdDbEchartsCategoryName" style="border:0px;text-align:center;"/>
					</td>
					<td align="center" coltype="copyCol">
						<span style='cursor:pointer' class='optStyle opt_copy_style'  title="${lfn:message('button.copy') }" onmousedown='DocList_CopyRow();'></span>
						<span style='cursor:pointer' class='optStyle opt_del_style' title="${lfn:message('button.delete') }" onmousedown='DocList_DeleteRow();'></span>
						<input type="hidden" name="fdNavTreeShowList[!{index}].fdId" />
					</td>
				</tr>
				<c:forEach items="${dbEchartsNavTreeShowForm.fdNavTreeShowList}"  var="dbEchartsNavTreeShowItemForm" varStatus="vstatus">
					<tr KMSS_IsContentRow="1" align="center">
						<td width="5%">${vstatus.index+1}
						</td>
						<td>
							<xform:text showStatus="view" property="fdNavTreeShowList[${vstatus.index}].fdNavTitleTxt" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.showValue') }"></xform:text>
						</td>
						<td>
							<xform:text showStatus="view" property="fdNavTreeShowList[${vstatus.index}].fdDbEchartsCategoryName" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.realValue') }"></xform:text>
						</td>
						<td align="center" coltype="copyCol">
							<span style='cursor:pointer' class='optStyle opt_del_style' title="${lfn:message('button.delete') }" onmousedown='DocList_DeleteRow();'></span>
							<html:hidden property="fdNavTreeShowList[${vstatus.index}].fdId" />
						</td>
					</tr>
				</c:forEach>
				<tr type="optRow" class="tr_normal_opt">
					<td row="3" column="0" align="center" coltype="optCol" colspan="6" style="">
						<div class="tr_normal_opt_content" style="min-width:580px;;" >
							<div class="tr_normal_opt_c"  >
								<span onclick='DocList_AddRow();'>
									<span class="optStyle opt_add_style"  title='<bean:message bundle="sys-xform" key="xform.button.add" />' ></span>
									<span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">
										<bean:message bundle="sys-xform" key="xform.button.add" />
									</span>
								</span>
								<span style="margin-left:15px;" onclick='DocList_MoveRowBySelect(-1);'>
									<span class="optStyle opt_up_style" title='<bean:message bundle="sys-xform" key="xform.button.moveup" />' ></span>
									<span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;"><bean:message bundle="sys-xform" key="xform.button.moveup" /></span>
								</span>
								<span style="margin-left:15px;" onclick='DocList_MoveRowBySelect(1);'>
									<span class="optStyle opt_down_style" title='<bean:message bundle="sys-xform" key="xform.button.movedown" />' ></span>
									<span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">
										<bean:message bundle="sys-xform" key="xform.button.movedown" />
									</span>
								</span>
							</div>
						</div>
					</td>
				</tr>		
			</table>
		</div>
	</div>
<br>
</center>
<html:hidden property="fdId" />
<html:hidden property="fdServiceModelName" />
<html:hidden property="fdKey" />
<html:hidden property="method_GET" />
</html:form>
<script>
	var g_validator = $KMSSValidation();
	
	function NavTree_Dialog(id,name,dom){
		var beanUrl = "dbEchartsNavTreeShowService&parent=!{value}&modelName=!{modelName}&serviceModelName=" + $("[name='fdServiceModelName']").val() 
						+ "&key=" + $("[name='fdKey']").val();
		Dialog_Tree(false,id,name,null,beanUrl,'title',false,function(kmssData){
			var node = kmssData.data[0]["node"];
			$(dom).closest("tr").find("[name*='fdDbEchartsCategoryName']").val(node.categoryTxt);
			$(dom).closest("tr").find("[name*='fdNavTreeModelName']").val(node.modelName);
		});
	}
	
	function NavTree_Submit(method){
		Com_Submit(document.forms[0],method);
	}
	
</script>
</template:replace>
</template:include>