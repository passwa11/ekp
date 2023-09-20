<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	String _modelName = request.getParameter("modelName");
	String _roles = request.getParameter("roles");
	if(StringUtil.isNull(_modelName)) {
		throw new Exception("modelName不能为空！");
	}
	if(StringUtil.isNotNull(_roles)) {
		request.setAttribute("roles", _roles);
	}
	String[] modelNames = _modelName.split("\\s*[;,]\\s*");
	String modelName = modelNames[0];
	JSONArray array = new JSONArray();
	if(modelNames.length > 1) {
		for(String name : modelNames) {
			JSONObject obj = new JSONObject();
			obj.put("text", ResourceUtil.getString(SysDataDict.getInstance().getModel(name).getMessageKey()));
			obj.put("value", name);
			array.add(obj);
		}
	}
%>

<c:choose>
	<c:when test="${not empty param.orderBy  }">
		<c:set var="fdOrderBy" value="${param.orderBy}" />
	</c:when>
	<c:otherwise>
		<c:set var="fdOrderBy" value="docCreateTime" />
	</c:otherwise>
</c:choose>
<template:include ref="default.simple">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<list:criteria id="criteria1">
			<%-- <list:cri-ref key="docSubject" ref="criterion.sys.docSubject"></list:cri-ref> --%>
			<%@include
				file="/kms/common/kms_comment/import/kmsCommentMain_index_cri_include.jsp"%>
			
			<% if(array.size() > 0) { %>
			<list:tab-criterion title="${ lfn:message('kms-common:kmsCommonDataPush.tree.maindirectory') }" key="fdModelName" multi="false">
				<list:box-select>
					<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-required="true" cfg-defaultValue="<%=modelName%>">
						<ui:source type="Static">
							<%=array.toString()%>
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:tab-criterion>
			<% } %>
		</list:criteria>

		<div class="lui_list_operation">
		
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
		
			<!-- 排序按钮 --> 
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
					    <list:sort property="docCreateTime" text="${ lfn:message('kms-common:kmsCommonRecycleLog.fdOperateTime') }"  value="down"></list:sort>
						<%-- 
						<list:sort property="sysRecycleLog.fdOptType" text="${lfn:message('sys-recycle:sysRecycleLog.fdOptType') }" ></list:sort>
						--%>
					</ui:toolbar>
				</div>
			</div>
			<div class="lui_list_operation_page_top" >
				<list:paging layout="sys.ui.paging.top"></list:paging>
			</div>
			<div style="float: right">
				<div style="display: inline-block; vertical-align: middle;">
					<ui:toolbar count="10">
						<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="2" ></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>

		<ui:fixed elem=".lui_list_operation"></ui:fixed>

		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=listModelCorrection&modelName=<%=modelName%>'}
			</ui:source>
			<!-- 列表视图 -->
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
				rowHref="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=viewinfo&fdId=!{fdId}"
				name="columntable">
				<%-- <kmss:authShow roles="ROLE_SYS_RECYCLE_BACKSTAGE_MANAGER"> --%>
				<list:col-checkbox></list:col-checkbox>
				<%-- </kmss:authShow> --%>
				
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
				
			</list:colTable>
			<ui:event topic="list.loaded">
				modelName = '<%=modelName%>';
				var sourceURL = LUI("listview").sourceURL;
				if(sourceURL) {
					var _modelName = Com_GetUrlParameter(sourceURL, "q.fdModelName");
					if(_modelName && _modelName.length > 10) {
						modelName = _modelName;
					}
				}
			</ui:event>
		</list:listview>

		<list:paging></list:paging>
		
		<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
		<c:if test="${frameShowTop=='yes' }">
			<ui:top id="top"></ui:top>
			<kmss:ifModuleExist path="/sys/help">
				<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
			</kmss:ifModuleExist>
		</c:if>

		<script type="text/javascript">
			seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
				
				//删除
				window.deleteAll = function(id) {
					__ajax('<bean:message bundle="kms-common" key="kmsCommonDocErrorCorrection.confirmDeleteView"/>', 
					'<c:url value="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=deleteall"/>&fdModelName=' + modelName,id);
				};

				
				
				window.__ajax = function(msg, url,id) {
					var values = [];
					if(id){
						values.push(id);
					}else{
	                   $("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm(msg, function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: _callback
						   });
						}
					});
				}

				window._callback = function(data) {
					if (window.del_load != null) {
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
				
				
				

			});
		</script>
	</template:replace>
</template:include>