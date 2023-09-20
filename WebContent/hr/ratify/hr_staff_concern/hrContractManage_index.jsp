<!-- 人事合同 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
		<link rel="stylesheet" href="../resource/style/hr.css">
		
	    <script>
	    	seajs.use(['theme!list']);
	    </script>
	    <!-- 筛选 -->
	    <list:criteria id="criteria2">
		     <list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-staff:hrStaffPersonInfo.search.placehoder') }">
			 </list:cri-ref>
		     <list:cri-ref key="_fdDept" ref="criterion.sys.dept" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }"></list:cri-ref>
		     <list:cri-ref key="_fdPost" ref="criterion.sys.postperson.availableAll" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }"></list:cri-ref>
		     <c:if test="${param.fdSignType == '1' }">
			     <list:cri-ref key="fdBeginDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }"></list:cri-ref>
				 <list:cri-ref key="fdEndDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }"></list:cri-ref>
	  	     </c:if> 
	  	</list:criteria>
	  	<!-- 操作 -->
	    <div class="lui_list_operation">
	        <!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
	        <div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
	                <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
	                    <list:sort property="fdTimeOfEnterprise" text="${lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }" group="sort.list" value="down"></list:sort>
	                </ui:toolbar>
	            </div>
	        </div>
	        <div class="lui_list_operation_page_top">
	            <list:paging layout="sys.ui.paging.top" />
	        </div>
	        <div style="float:right">
	            <div style="display: inline-block;vertical-align: middle;">
	                <ui:toolbar count="3">
	                    <ui:button text="${lfn:message('button.export')}" onclick="exportStaff();" order="1" />
	                    <c:if test="${param.fdSignType == '1' }">
	                    	<ui:button text="${lfn:message('hr-staff:hrStaffPersonExperience.contract.batchContinueSign') }" onclick="batchRenew();" order="2" />
	                    </c:if>
	                    <c:if test="${param.fdSignType == '0' }">
	                    	<ui:button text="${lfn:message('hr-staff:hrStaffPersonExperience.contract.batchSign') }" onclick="batchSign();" order="2" />
	                    </c:if>
	                </ui:toolbar>
	            </div>
	        </div>
	    </div>
	    <ui:fixed elem=".lui_list_operation" />
	    <!-- 列表 -->
	    <list:listview id="listview">
	        <ui:source type="AjaxJson">
	            {url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=contractManageList&fdSignType=${param.fdSignType }'}
	        </ui:source>
	        <!-- 列表视图 -->
	        <list:colTable isDefault="false" rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{fdId}" name="columntable">
	            <list:col-checkbox />
	            <list:col-serial/>
	             <c:if test="${param.fdSignType == '1' }">
					<list:col-auto props="fdName;fdStatus;contInfo;fdContStatus;contDate;fdHandleDate;operations"></list:col-auto>
				 </c:if>
				 <c:if test="${param.fdSignType == '0' }">
					<list:col-auto props="fdName;fdStatus;fdSignType;fdHistoryContract;operations"></list:col-auto>
				 </c:if>
			</list:colTable>
	    </list:listview>
	   
	    <!-- 翻页 -->
	    <list:paging />
	    <script>
	    	var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'remove',
                modelName: 'com.landray.kmss.hr.ratify.model.HrRatifyRemove',
                templateName: 'com.landray.kmss.hr.ratify.model.HrRatifyTemplate',
                basePath: '/hr/ratify/hr_ratify_remove/hrRatifyRemove.do',
                canDelete: '${canDelete}',
                mode: 'main_template',
                templateService: 'hrRatifyTemplateService',
                templateAlert: '${lfn:message("hr-ratify:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
	    	Com_IncludeFile("list.js", "${LUI_ContextPath}/hr/ratify/resource/js/", 'js', true);
		    seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'],function($, dialog, topic){
		    
		    	//导出
		    	window.exportStaff = function(){
		    		var _values = [];
					$("input[name='List_Selected']:checked").each(function() {
						_values.push($(this).val());
					});
					if(_values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var ids = _values.join(";");
		    		var url = Com_Parameter.ContextPath+"hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=exportContract&fdSignType=${param.fdSignType}&ids="+ids;
					Com_OpenWindow(url, '_blank');
		    	};
		    	//合同变更
				window.changeContract = function(fdId){
					var path = "/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=changeContract&fdId="+fdId;
	    	   		dialog.iframe(path,"${ lfn:message('hr-staff:hrStaffRatifyLog.fdRatifyType.change') }",function(value){
	    	    		topic.publish("list.refresh");
	        		},{
	    				width : 900,
	    				height : 600
	    			});
				};
				//合同续签
	    		window.renew = function(fdId){
	    			var path = "/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=renewContractPage&fdId="+fdId;
	    	   		dialog.iframe(path,"${ lfn:message('hr-staff:hrStaffPersonExperience.contract.renewal') }",function(value){
	    	   			if(value == "success"){
	    	    			topic.publish("list.refresh");
	    	   			}
	        		},{
	    				width : 700,
	    				height : 700
	    			});
	    		};
	    		//合同签订
	    		window.signContract = function(fdId){
	    			var path = "/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=signContract&personId="+fdId;
	    	   		dialog.iframe(path,"${ lfn:message('hr-staff:hrStaffRatifyLog.fdRatifyType.sign') }",function(value){
	    	   			topic.publish("list.refresh");
	        		},{
	    				width : 600,
	    				height : 600
	    			});
	    		};
	    		window.selectIds = function(){
	    			var _values = [];
					$("input[name='List_Selected']:checked").each(function() {
						_values.push($(this).val());
					});
					if(_values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var ids = _values.join(";");
					return ids;
	    		}
	    		//批量续签
	    		window.batchRenew = function(){
	    			var ids = selectIds();
	    			if(null != ids){
		    			var path = "/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=batchRenewPage&ids="+ids;
		    			dialog.iframe(path,"${ lfn:message('hr-staff:hrStaffPersonExperience.contract.batchContinueSign') }",function(value){
		    	   			if(value == "success"){
		    	    			topic.publish("list.refresh");
		    	   			}
		        		},{
		    				width : 700,
		    				height : 700
		    			});
	    			}
	    		};
	    		//批量签订
	    		window.batchSign = function(){
	    			var ids = selectIds();
	    			if(null != ids){
		    			var path = "/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=batchSignPage&ids="+ids;
		    			dialog.iframe(path,"${ lfn:message('hr-staff:hrStaffPersonExperience.contract.batchSign') }",function(value){
		    	   			if(value == "success"){
		    	    			topic.publish("list.refresh");
		    	   			}
		        		},{
		    				width : 700,
		    				height : 700
		    			});
	    			}
	    		}
		    });
	    </script>
    </template:replace>
</template:include>