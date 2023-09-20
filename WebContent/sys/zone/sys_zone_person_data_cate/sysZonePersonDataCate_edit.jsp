<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysZonePersonDataCateForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-zone:module.sys.zone') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${sysZonePersonDataCateForm.fdName} - ${ lfn:message('sys-zone:module.sys.zone') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="head">
		<style>
		.lui_zone_catalog_edit { 
			padding: 5px 8px;
		  	display: inline-block;
		 	border-width: 1px;
		  	border-style: solid;
		  	cursor: pointer;
		 }
		</style>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysZonePersonDataCateForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysZonePersonDataCateForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysZonePersonDataCateForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysZonePersonDataCateForm, 'save');"></ui:button>
<%-- 					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysZonePersonDataCateForm, 'saveadd');"></ui:button> --%>
				</c:when>
			</c:choose>	
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				<c:if test="${param.method eq 'edit' }">
					 <bean:write name="sysZonePersonDataCateForm" property="fdName" />
				</c:if>
				<c:if test="${param.method eq 'add' }">
					<bean:message bundle="sys-zone" key="table.sysZonePersonDataCate"/>  
				</c:if>
			</div>
			<div class='lui_form_baseinfo'>
			</div>
		</div>
		<html:form action="/sys/zone/sys_zone_person_data_cate/sysZonePersonDataCate.do">
			<c:if test="${!empty sysZonePersonDataCateForm.fdName}">
				<p class="txttitle" style="display: none;">${sysZonePersonDataCateForm.fdName }</p>
			</c:if> 
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class=tb_normal width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-zone" key="sysZonePersonDataCate.fdName"/>
						</td>
						<td width="85%">
							<!-- 非空（done） 重名（todo）特殊字符（todo） 长度限制（todo）-->
							<xform:text property="fdName" style="width:85%" validators="noSpecialChar"/>
						</td>
<!-- 						<td class="td_normal_title" width=15%> -->
<%-- 							<bean:message bundle="sys-zone" key="sysZonePersonDataCate.docStatus"/> --%>
<!-- 						</td> -->
<!-- 						<td width="35%"> -->
<%-- 							<c:if test="${sysZonePersonDataCateForm.docStatus eq '1' }"> --%>
<!-- 								<input type="radio" name="docStatus" value="1" checked="checked">启用 -->
<!-- 								<input type="radio" name="docStatus" value="0">禁用 -->
<%-- 							</c:if> --%>
<%-- 							<c:if test="${sysZonePersonDataCateForm.docStatus eq '0'}"> --%>
<!-- 								<input type="radio" name="docStatus" value="1">启用 -->
<!-- 								<input type="radio" name="docStatus" value="0" checked="checked">禁用 -->
<%-- 							</c:if> --%>
<%-- 							<c:if test="${sysZonePersonDataCateForm.docStatus eq null}"> --%>
<!-- 								<input type="radio" name="docStatus"value="1" checked="checked">启用 -->
<!-- 								<input type="radio" name="docStatus" value="0">禁用 -->
<%-- 							</c:if> --%>
<!-- 						</td> -->
					</tr>
					<!-- 目录模版明细表 === 开始 -->
					<tr>
						<td colspan="4">
							<table class="tb_normal" width=100% id="TABLE_DocList">
								<%-- 标题行 == 开始 --%>
								<tr>
									<td align="center" width=5% class="td_normal_title">
										<bean:message bundle="sys-zone" key="sysZonePerDataTempl.fdOrder"/>
									</td>
									
									<td align="center" width=40% class="td_normal_title">
										<bean:message bundle="sys-zone" key="sysZonePerDataTempl.fdName"/>
									</td>
									<td align="center" width=30% class="td_normal_title">
										<bean:message bundle="sys-zone" key="sysZonePerDataTempl.docContent"/>
									</td>
									<td align="center" width=15% class="td_normal_title">
										<%-- 添加行按钮 ，会根据基准行的内容进行添加行--%>
										<a href="javascript:;" onclick="DocList_AddRow();">
											<img src="${KMSS_Parameter_StylePath}icons/add.gif">
										</a>
									</td>
								</tr>
								<%-- 标题行 == 结束 --%>
								<%-- 基准行，默认不展现，基准行的TR标签必须定义KMSS_IsReferRow="1"：表示该行为基准行--%>
								<tr KMSS_IsReferRow="1" style="display:none" align="center">
									<td KMSS_IsRowIndex="1" align="center">!{index}</td>
									<td>
										<input type="hidden" name="fdDataCateTemplForms[!{index}].fdId"/>
									     <input type="hidden" name="fdDataCateTemplForms[!{index}].fdPersonDataCateId" value = "${sysZonePersonDataCateForm.fdId }"/>
									     <!-- 排序号 -->
										 <input readonly="readonly" type="hidden" name="fdDataCateTemplForms[!{index}].fdOrder" style="text-align:center;width:95%;border: 0px;"/>
									
										<!-- 目录名 -->
										<!-- 非空（done） 重名（todo）特殊字符（todo） -->
									    <xform:text validators="required noSpecialChar limitLength checkNameOnly" property="fdDataCateTemplForms[!{index}].fdName" style="text-align:center;width:95%;"/>
										<span style="color: red;">*</span>
									</td>
									<td align="center">
										<!--内容 -->
										<div 
										    class="lui_zone_catalog_edit com_bgcolor_d com_bordercolor_d com_fontcolor_d"
											onclick="edit_templ_content(this,!{index});"
											name="_fdDataCateTemplForms[!{index}].docContent">	<bean:message bundle="sys-zone" key="sysZonePerson.docContent"/></div>
										<div style="display: none">
											<div id="fdDataCateTemplForms_!{index}">
											</div>
											<xform:text property="fdDataCateTemplForms[!{index}].docContent" className="content_mark"/>
										</div>
									</td>	
									<td>
										<%-- 操作按钮--%>
										<center>
											<a href="javascript:;" onclick="delete_row(this);">
												<img src="${KMSS_Parameter_StylePath}icons/delete.gif">
											</a>
											<a href="javascript:;" onclick="up_row();">
												<img src="${KMSS_Parameter_StylePath}icons/up.gif">
											</a>
											<a href="javascript:;" onclick="down_row();">
												<img src="${KMSS_Parameter_StylePath}icons/down.gif">
											</a>
										</center>
									</td>		
								</tr>
								<!-- 内容行 -->
								
								<c:forEach items="${ sysZonePersonDataCateForm.fdDataCateTemplForms}" var="fdDataCateTemplForm" varStatus="vstatus">
									<tr KMSS_IsContentRow="1">
										<td KMSS_IsRowIndex="1" align ="center">
											<c:out value="${fdDataCateTemplForm.fdOrder}"/>
										</td>
										<td>
											<input type="hidden" name="fdDataCateTemplForms[${vstatus.index}].fdId" value="${fdDataCateTemplForm.fdId }"/>
										     <input type="hidden" name="fdDataCateTemplForms[${vstatus.index}].fdPersonDataCateId" value = "${sysZonePersonDataCateForm.fdId }"/>
										     <!-- 排序号 -->
										     <input readonly="readonly" type="hidden" name="fdDataCateTemplForms[${vstatus.index}].fdOrder" value=${fdDataCateTemplForm.fdOrder }/>
										
											<!-- 目录名 -->
										    <xform:text validators="required noSpecialChar limitLength checkNameOnly" property="fdDataCateTemplForms[${vstatus.index}].fdName" style="text-align:center;width:95%;" value="${fdDataCateTemplForm.fdName }"/>
											<span style="color: red;">*</span>
										</td>
										<td align="center">
											<!--内容 -->
											<div  class="lui_zone_catalog_edit com_bgcolor_d com_bordercolor_d com_fontcolor_d"
												  onclick="edit_templ_content(this, ${vstatus.index});"
												  name="_fdDataCateTemplForms[${vstatus.index}].docContent">
										    	<bean:message bundle="sys-zone" key="sysZonePerson.docContent"/></div>
										    </div>
											<div style="display: none">
												<div id="fdDataCateTemplForms_${vstatus.index}">
													 ${fdDataCateTemplForm.docContent}
												</div>
												<xform:text property="fdDataCateTemplForms[${vstatus.index}].docContent" className="content_mark"/>
											</div>
										</td>	
										<td>
											<%-- 删除行按钮--%>
											<center>
												<%-- 操作按钮--%>
										<center>
											<a href="javascript:;" onclick="delete_row(this);">
												<img src="${KMSS_Parameter_StylePath}icons/delete.gif">
											</a>
											<a href="javascript:;" onclick="up_row();">
												<img src="${KMSS_Parameter_StylePath}icons/up.gif">
											</a>
											<a href="javascript:;" onclick="down_row();">
												<img src="${KMSS_Parameter_StylePath}icons/down.gif">
											</a>
										</center>
											</center>
										</td>		
									</tr>
								</c:forEach>
								
							</table>
						</td>
					</tr>
					<!-- 目录模版明细表 === 结束 -->
				</table>
			</div>
			<ui:tabpage expand="false">
			</ui:tabpage>
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />
		</html:form>
		<%@include file="sysZonePersonDataCate_edit_js.jsp" %>
	</template:replace>
</template:include>