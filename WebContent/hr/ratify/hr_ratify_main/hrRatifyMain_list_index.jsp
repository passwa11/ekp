<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content"> 
		<list:criteria id="criteria1">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyMain" property="docStatus" multi="false"/>
			<list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyMain" property="docNumber" />
			<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false" title="${lfn:message('hr-ratify:hrRatifyMain.docCreatorName') }" />
		   <%--当前处理人--%>
			<list:cri-ref ref="criterion.sys.postperson.availableAll"
						  key="fdCurrentHandler" multi="false"
						  title="${lfn:message('hr-ratify:lbpm.currentHandler') }" />
		   <%--已处理人--%>
			<list:cri-ref ref="criterion.sys.person"
						  key="fdAlreadyHandler" multi="false"
						  title="已处理人" />
			<list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyMain" 
				property="fdDepartment" />
			<list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyMain" property="docCreateTime"/>
			<list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyMain" property="docPublishTime"/>			
		</list:criteria>
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sortgroup>
							<list:sort property="docCreateTime" text="${lfn:message('hr-ratify:hrRatifyMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="docPublishTime" text="${lfn:message('hr-ratify:hrRatifyMain.docPublishTime') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="10">
						<kmss:authShow roles="ROLE_HRRATIFY_CREATE">
						 	<ui:button text="${lfn:message('button.add')}" id="add" onclick="addDoc()" order="8" ></ui:button>
						</kmss:authShow>
						<kmss:auth requestURL="/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=deleteall">
                             <ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc();" order="4" id="btnDelete" />
                        </kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview>
			<ui:source type="AjaxJson">
					{url:'/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=manageList&categoryId=${param.categoryId}&nodeType=${param.nodeType}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" rowHref="/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=view4Main&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="docSubject;docNumber;docCreator.fdName;docCreateTime;docPublishTime;docStatus.name;lbpm_main_listcolumn_node;lbpm_main_listcolumn_handler"></list:col-auto> 
			</list:colTable>
		</list:listview> 
		<br>
	 	<list:paging></list:paging>
	 	<script type="text/javascript">
	 	
	 	var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.hr.ratify.model.HrRatifyMain";
	 	seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/toolbar'], function($, strutil, dialog , topic,toolbar) {
		 	
		 	//新建
	 		window.addDoc = function() {
	 			dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn) {
					if (rtn != false
							&& rtn != null) {
						addByTemplate(rtn.id);
					}
				});
		 	};
		 	function addByTemplate(tempId) {
				if (tempId != null && tempId != '') {
					var data = new KMSSData();
					var url = Com_Parameter.ContextPath
							+ "hr/ratify/hr_ratify_main/hrRatifyMain.do?method=loadRatifyTemplate&tempId="
							+ tempId;
					var results;
					data.SendToUrl(url, function(data) {
						results = data.responseText;

					}, false);
					window.open(Com_Parameter.ContextPath
							+ results, '_blank');
				}
			}; 
           //删除文档
     		window.delDoc = function(draft){
     			var values = [];
     			$("input[name='List_Selected']:checked").each(function(){
     				values.push($(this).val());
     			});
     			if(values.length==0){
     				dialog.alert('<bean:message key="page.noSelect"/>');
     				return;
     			}
     			var url = '<c:url value="/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=deleteall"/>';
     			url = Com_SetUrlParameter(url, 'categoryId', "${param.categoryId}");
     			url = Com_SetUrlParameter(url, 'nodeType', "${param.nodeType}"); 
     			if(draft == '0'){
     				url = '<c:url value="/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=deleteall&status=10"/>';
     			}
     			var config = {
     				url : url, // 删除数据的URL
     				data : $.param({"List_Selected":values},true), // 要删除的数据
     				modelName : "com.landray.kmss.hr.ratify.model.HrRatifyMain" // 主要是判断此文档是否有部署软删除
     			};
     			
     			Com_Delete(config, delCallback);
     		};
     		
		 	
			window.delCallback = function(data){
				if(window.del_load!=null){
					window.del_load.hide(); 
					topic.publish("list.refresh");
				}
				dialog.result(data);
			};
			
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
			
	 	});
	 	</script>
	</template:replace>
</template:include>