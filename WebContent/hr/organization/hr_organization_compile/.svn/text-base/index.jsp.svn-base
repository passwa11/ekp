<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>

<template:include ref="default.simple" spa="true">
	<template:replace name="head">
	    <script type="text/javascript">
				seajs.use(['theme!form']);
				Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
				Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|data.js", null, "js");
		</script>
	    <link rel="stylesheet" href="../resource/css/organization.css">
	    <link rel="stylesheet" href="../resource/css/reset.css">
	    <link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/tree.css" />	
	</template:replace>
    <template:replace name="body">
		<ui:tabpanel  layout="sys.ui.tabpanel.list" id="tabpanel">
	    	<ui:content title="${lfn:message('hr-organization:py.ZuZhiBianZhiGuan') }">
    	<div id="hr-org-tree-table-search">
    		<div id="hr-org-tree-table-search-box">
    			<input type="text" placeholder="<bean:message key="hrOrganizationElement.please.input.fdName" bundle="hr-organization"/>" name="" value=""/>
    			<span></span>
    		</div>
    		<div id="hr-org-tree-head-button">
    			<c:if test="${hrToEkpEnable }">
    				<kmss:auth requestURL="/hr/organization/hr_organization_element/hrOrganizationElement.do?method=add">
		    			<ui:button text="${lfn:message('hr-organization:hr.organization.info.add') }" onclick="window.addOrg(1)"></ui:button>
		    			<ui:button text="${lfn:message('hr-organization:hr.organization.info.adddept') }" onclick="window.addOrg(2)"></ui:button>
	    			</kmss:auth>
    			</c:if>
    		</div>
    	</div>
    	<kmss:auth requestURL="/hr/organization/hr_organization_element/hrOrganizationElement.do?method=updateCompile">
    		<input type="hidden" value="true" id="authUpdateCompile"/>
    	</kmss:auth>
		<div id="hr-org-tree-table"></div>
		<div id="namex" style="display: none;"></div>
		<script src="${LUI_ContextPath}/hr/organization/resource/js/tree/index.js"></script>
		<script>
			seajs.use(["${ LUI_ContextPath }/hr/organization/resource/js/tree/treeTable.js","lui/topic","lui/dialog"],function(TreeWidget,topic,dialog){
				var tree = new TreeWidget.TreeTable({icon:true,id:'hr-org-tree-table',authCompile:$("#authUpdateCompile").val(),showEmpty:true, hrToEkpEnable:'${hrToEkpEnable}'});
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function() {
						seajs.use(['lui/topic'], function(topic) {
							topic.publish('list.refresh');
						});
					}, 100);
				});
				//设置编制
				window.compile_edit=function(fdId, fdName, isOpen){
					var opentips = isOpen?"true":"";
					var url = "/hr/organization/hr_organization_element/hrOrganizationElement.do?method=updateCompilePage&fdId="+fdId+"&isOpen="+opentips;
					var dialogObject = dialog.iframe(url,"${lfn:message('hr-organization:hr.organization.info.setup.establishment') }-" + fdName,function(data){
						if(data=="success"){
							topic.publish("hr/org/table/search","");
						}
					},{ 
						buttons:[{
							name:"${lfn:message('button.ok') }",
							fn:function(){
								dialogObject.element.find("iframe").get(0).contentWindow._submit(window);
								topic.publish('list.refresh');
							}
						},{
							name:"${lfn:message('button.cancel') }",
							fn:function(){
								dialogObject.hide();
							},
							
						}],
						width:800,height:660
					});
				}
				
				$("#hr-org-tree-table-search-box span").on("click",function(){
					var searchValue = $(this).prev().val();
					if(!$(this).attr("searched")){
						topic.publish("hr/org/table/search",searchValue);
						$(this).attr("searched","true");
						$(this).addClass("hr-org-search-cancel");
					}else{
						topic.publish("hr/org/table/search/cancel");
						$(this).attr("searched","");
						$("#hr-org-tree-table-search-box input").val("");
						$(this).removeClass("hr-org-search-cancel");
					}
					return false;
				})	
				$("#hr-org-tree-table-search-box input").on("keydown",function(e){
					 var theEvent = e || window.event;
					 var searchValue = $(this).val();
					 var code = theEvent.keyCode || theEvent.which || theEvent.charCode;  
					 if(code==13){
							topic.publish("hr/org/table/search",searchValue);
							$("#hr-org-tree-table-search-box span").attr("searched","true");
							$("#hr-org-tree-table-search-box span").addClass("hr-org-search-cancel");
					 }
				})
				window.refreshTable=function(){
					topic.publish("hr/org/table/refresh");
				}
			})
		</script>
		</ui:content>
		</ui:tabpanel>
    </template:replace>
</template:include>