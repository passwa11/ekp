<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
	request.setAttribute("fdTrxId", request.getParameter("fdTrxId"));
	request.setAttribute("fdSynModel", request.getParameter("fdSynModel"));
%>
<style type="text/css">
.criterion-calendar li:nth-child(4),
.criterion-calendar li:nth-child(5),
.criterion-calendar li:nth-child(6){
	display: none !important;
}
.ld_icon_back {
    width: 24px;
    height: 24px;
    background: url(../resource/images/left_arrow.png) no-repeat left top;
    background-size: cover;
    margin-left: 20px;
}
.cursor {
    cursor: pointer;
}
.ld_icon {
    display: inline-block;
    vertical-align: middle;
    margin-right: 10px;
}
.ld_icon_back {
    width: 24px;
    height: 24px;
    background: url(../../../lding/console/resource/images/left_arrow.png) no-repeat left top;
    background-size: cover;
}
.ld_form_path_frame {
    font-size: 18px;
    margin-top: 15px;
 }
 .lui_paging_t_content_box .lui_icon_s {
    margin-top: 4px;
}
.lui_paging_t_refresh_l {
    top: 0px !important;
}
</style>
<template:include ref="nohead.config.list">
        <div class="ld_titleBar ld_titleBar_form">
               <!-- 当前位置 -->
<%--                <div class="ld_form_path_frame">
               <a href="${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/index.jsp" class="ld_icon ld_icon_back cursor"></a>
               		返回事务列表</div> 
               		
	          		</div> --%>
        
        <%-- <div> 
        <a style="font-size: 26px;padding: 20px 10px 0px 20px;" href="${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/index.jsp" >< <span style="font-size: 16px;">返回事物列表</span></a>
        </div> --%>
          
    <template:replace name="content">
    	<div>
          <ui:tabpanel style="margin-top: 25px;">
          <ui:content selectedIndex="0" title="部门信息列表">
            <!-- 筛选 -->
            <list:criteria id="criteriadept" channel="listviewdept">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-oms:sysOmsTempDept.fdName')}" />
				<list:cri-criterion title="${lfn:message('sys-oms:sysOmsTempDept.fdStatus') }" key="fdStatus" expand="true">
	            	<list:box-select>
	            		<list:item-select>
	            			<ui:source type="Static">
	            				[{text:'${lfn:message('sys-oms:result.1') }', value:'1'},
								{text:'${lfn:message('sys-oms:result.0') }',value:'0'}]
	            			</ui:source>
	            		</list:item-select>
	            	</list:box-select>
	            </list:cri-criterion>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" channel="listviewdept"/>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listviewdept"  channel="listviewdept" topic="listviewdept.list">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/oms/sys_oms_temp_dept/sysOmsTempDept.do?method=data&fdTrxId=${fdTrxId}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                    <%-- <list:col-checkbox />
                    <list:col-serial/> --%>
                    <list:col-auto props="fdDeptId;fdName;fdParentid;fdIsAvailable.name;fdExtend;fdTrxId;fdStatus;fdFailReasonDesc;handle" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging channel="listviewdept" />
            </ui:content>
            
            
             <ui:content selectedIndex="1" title="人员信息列表">
             	 <!-- 筛选 -->
            <list:criteria id="criteriaperson" channel="listviewperson">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-oms:sysOmsTempPerson.fdName')}" />
				<list:cri-criterion title="${lfn:message('sys-oms:sysOmsTempPerson.fdStatus') }" key="fdStatus" expand="true">
	            	<list:box-select>
	            		<list:item-select>
	            			<ui:source type="Static">
	            				[{text:'${lfn:message('sys-oms:result.1') }', value:'1'},
								{text:'${lfn:message('sys-oms:result.0') }',value:'0'}]
	            			</ui:source>
	            		</list:item-select>
	            	</list:box-select>
	            </list:cri-criterion>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" channel="listviewperson"/>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listviewperson" channel="listviewperson" topic="listviewperson.list">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/oms/sys_oms_temp_person/sysOmsTempPerson.do?method=data&fdTrxId=${fdTrxId}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                   <%--  <list:col-checkbox />
                    <list:col-serial/> --%>
                    <list:col-auto props="fdPersonId;fdName;fdParentid;fdIsAvailable.name;fdMobileNo;fdLoginName;fdExtend;fdTrxId;fdStatus;fdFailReasonDesc;handle" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging channel="listviewperson"/>
             </ui:content>
             
 			<c:if test="${fdSynModel == 30 || fdSynModel == 40 || fdSynModel == 41 ||  fdSynModel == 300 || fdSynModel == 400}">
             <ui:content selectedIndex="2" title="岗位信息列表">
             	 <!-- 筛选 -->
			<list:criteria id="criteriapost" channel="listviewpost">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-oms:sysOmsTempPost.fdName')}" />
                <list:cri-criterion title="${lfn:message('sys-oms:sysOmsTempPost.fdStatus') }" key="fdStatus" expand="true">
	            	<list:box-select>
	            		<list:item-select>
	            			<ui:source type="Static">
	            				[{text:'${lfn:message('sys-oms:result.1') }', value:'1'},
								{text:'${lfn:message('sys-oms:result.0') }',value:'0'}]
	            			</ui:source>
	            		</list:item-select>
	            	</list:box-select>
	            </list:cri-criterion>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" channel="listviewpost"/>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listviewpost" channel="listviewpost" topic="listviewpost.list">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/oms/sys_oms_temp_post/sysOmsTempPost.do?method=data&fdTrxId=${fdTrxId}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                    <%-- <list:col-checkbox />
                    <list:col-serial/> --%>
                    <list:col-auto props="fdPostId;fdName;fdParentid;fdIsAvailable.name;fdExtend;fdTrxId;fdStatus;fdFailReasonDesc;handle" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging channel="listviewpost"/>
             </ui:content>
             </c:if>
             
             <c:if test="${fdSynModel == 20 || fdSynModel == 21 || fdSynModel == 40 || fdSynModel== 41 ||  fdSynModel == 200 || fdSynModel == 400}">
             <ui:content selectedIndex="3" title="人员部门信息列表">
            <!-- 筛选 -->
            <list:criteria id="criteriadp" channel="listviewdp">
                <list:cri-ref key="fdSearchName" ref="criterion.sys.docSubject" title="关键字 （人员ID或部门ID）" />
				<list:cri-criterion title="${lfn:message('sys-oms:sysOmsTempDp.fdStatus') }" key="fdStatus" expand="true">
	            	<list:box-select>
	            		<list:item-select>
	            			<ui:source type="Static">
	            				[{text:'${lfn:message('sys-oms:result.1') }', value:'1'},
								{text:'${lfn:message('sys-oms:result.0') }',value:'0'}]
	            			</ui:source>
	            		</list:item-select>
	            	</list:box-select>
	            </list:cri-criterion>
            </list:criteria> 
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" channel="listviewdp"/>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listviewdp"  channel="listviewdp" topic="listviewdp.list">
               <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/oms/sys_oms_temp_dp/sysOmsTempDp.do?method=data&fdTrxId=${fdTrxId}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                   <%--  <list:col-checkbox />
                    <list:col-serial/> --%>
                    <list:col-auto props="fdPersonId;fdPersonName;fdDeptId;fdDeptName;fdOrder;fdIsAvailable.name;fdExtend;fdTrxId;fdStatus;fdFailReasonDesc" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging channel="listviewdp" />
            </ui:content>
            </c:if>
            
            
            <c:if test="${fdSynModel == 30 || fdSynModel == 40 || fdSynModel== 41 ||  fdSynModel == 300 || fdSynModel == 400}">  
            <ui:content selectedIndex="4" title="人员岗位信息列表">
            <!-- 筛选 -->
            <list:criteria id="criteriapp" channel="listviewpp">
                <list:cri-ref key="fdSearchName" ref="criterion.sys.docSubject" title="关键字 （人员ID或岗位ID）" />
				<list:cri-criterion title="${lfn:message('sys-oms:sysOmsTempPp.fdStatus') }" key="fdStatus" expand="true">
	            	<list:box-select>
	            		<list:item-select>
	            			<ui:source type="Static">
	            				[{text:'${lfn:message('sys-oms:result.1') }', value:'1'},
								{text:'${lfn:message('sys-oms:result.0') }',value:'0'}]
	            			</ui:source>
	            		</list:item-select>
	            	</list:box-select>
	            </list:cri-criterion>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" channel="listviewpp"/>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listviewpp"  channel="listviewpp" topic="listviewpp.list">
               <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/oms/sys_oms_temp_pp/sysOmsTempPp.do?method=data&fdTrxId=${fdTrxId}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                    <%-- <list:col-checkbox />
                    <list:col-serial/> --%>
                    <list:col-auto props="fdPersonId;fdPersonName;fdPostId;fdPostName;fdIsAvailable.name;fdExtend;fdTrxId;fdStatus;fdFailReasonDesc" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging channel="listviewpp" />
            </ui:content>
             </c:if>
             
            </ui:tabpanel>
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'temp_dept',
                modelName: 'com.landray.kmss.sys.oms.model.SysOmsTempDept',
                templateName: '',
                basePath: '/sys/oms/sys_oms_temp_dept/sysOmsTempDept.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-oms:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/oms/resource/js/", 'js', true);
            Com_IncludeFile("dialog.js","${LUI_ContextPath}/lding/console/lding_address/address/", 'js', true);
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function($, dialog, topic, dialogCommon) {
            	function synAgainDept(fdId){
            		
            		dialog.confirm("再次执行同步，可能会将当前最新的部门数据覆盖掉，确定要再次执行同步吗？", function(ok) {
						 if(ok == true) {
							 var load = dialog.loading(); 
			            		$.ajax({
			    	     			url : "${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=synDept&fdDeptId="+fdId,
			    					type : 'get',
			    					async : true,
			    					dataType : "json",
			    					success : function(data) {
			    						//加载完成
			    						load.hide();
			    						//dialog.alert(data.message);
			    						topic.channel("listviewdept").publish("list.refresh");

			    					} ,
			    					error : function(req) {
			    						load.hide();
			                            dialog.failure('操作失败');
			    					}
			    	     			
			    			});	
						}
					});	
            		
            	}
            	
            	function synAgainPerson(fdId){
            		dialog.confirm("再次执行同步，可能会将当前最新的人员数据覆盖掉，确定要再次执行同步吗？", function(ok) {
						 if(ok == true) {
							 var load = dialog.loading(); 
							 $.ajax({
			    	     			url : "${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=synPerson&fdPersonId="+fdId,
			    					type : 'get',
			    					async : true,
			    					dataType : "json",
			    					success : function(data) {
			    						//加载完成
			    						load.hide();
			    						//dialog.alert(data.message);
			    						topic.channel("listviewperson").publish("list.refresh");

			    					} ,
			    					error : function(req) {
			    						load.hide();
			                            dialog.failure('操作失败');
			    					}
		
			    			});	
						}
					});	
            		
            	}
            	
            	function synAgainPost(fdId){
            		dialog.confirm("再次执行同步，可能会将当前最新的人员数据覆盖掉，确定要再次执行同步吗？", function(ok) {
						 if(ok == true) {
							 var load = dialog.loading(); 
							 $.ajax({
			    	     			url : "${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=synPost&fdPostId="+fdId,
			    					type : 'get',
			    					async : true,
			    					dataType : "json",
			    					success : function(data) {
			    						//加载完成
			    						load.hide();
			    						//dialog.alert(data.message);
			    						topic.channel("listviewpost").publish("list.refresh");

			    					} ,
			    					error : function(req) {
			    						load.hide();
			                            dialog.failure('操作失败');
			    					}
			    			  });	
						   }
					});	
            		
            	}
            	window.synAgainDept=synAgainDept;
            	window.synAgainPerson=synAgainPerson;
            	window.synAgainPost=synAgainPost;
            });
        </script>
    </template:replace>
</template:include>