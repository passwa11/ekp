<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.unit.util.SysUnitUtil"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-unit:kmImissiveUnit.fdName') }">
			</list:cri-ref>
			<list:cri-criterion title="${ lfn:message('sys-unit:kmImissiveUnit.fdIsAvailable')}" key="fdIsAvailable" multi="false" >
				<list:box-select>
					<list:item-select cfg-defaultValue="1">
						<ui:source type="Static">
							[{text:'${ lfn:message('message.yes')}', value:'1'},
							{text:'${ lfn:message('message.no')}',value:'0'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			
			<list:cri-criterion title="${lfn:message('sys-unit:table.sysUnitDataCenter.text')}" key="fdCenterCode" multi="false">
                <list:box-select>
                    <list:item-select>
                        <ui:source type="Static">
                            <%=SysUnitUtil.getDataCenter()%>
                        </ui:source>
                    </list:item-select>
                </list:box-select>
            </list:cri-criterion>
		</list:criteria>
		<!-- 操作栏 -->
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
						    <list:sort property="fdOrder" text="${lfn:message('sys-unit:kmImissiveUnit.fdOrder') }" group="sort.list" value="up"></list:sort>
							<list:sort property="docCreateTime" text="${lfn:message('sys-unit:kmImissiveUnit.docCreateTime') }" group="sort.list"></list:sort>
							<list:sort property="fdName" text="${lfn:message('sys-unit:kmImissiveUnit.fdName') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar">
						<%if(SysUnitUtil.getExchangeEnable()){ %>
							<ui:button text="单位同步" onclick="syncUnit();" order="1" ></ui:button>
						<%} %>	
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=list&q.fdNature=2&q.fdHasCenter=true'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdCode,fdParent.fdName,fdShortName,fdCenterCode,docCreator.fdName,docCreateTime,fdIsAvailable"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		
		 		// 单位同步
		 		window.syncUnit = function() {
			 		  var  url=Com_GetCurDnsHost()+Com_Parameter.ContextPath+'sys/unit/km_imissive_unit/sysUnitLevel.jsp';
		 			 dialog.iframe(url,'单位同步',function(rtn){
		 		    	 if(rtn!=null&&rtn!="cancel"){
		 		    		window.del_load = dialog.loading();
		 		    		 var fdLevel = rtn.replace(/\;/g,",");
		 		    		 var syncUrl = '<c:url value="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=syncUnit"/>';
		 		    		$.ajax({     
		 			    	     type:"post",     
		 			    	     url:syncUrl,     
		 			    	     data:{fdLevel:fdLevel},    
		 			    	     async:false,    //用同步方式 
		 			    	     success:function(data){
		 			    	    	if(window.del_load!=null){
		 								window.del_load.hide();
		 							}
		 			    	    	var array = JSON.parse(data);
		 			    	    	if(array.length > 0){
		 			    	    		var result = "";
		 			    	    		for(var i = 0;i<array.length;i++){
			 			    	    		var obj = array[i];
			 			    	    		if(obj.centerCode){
			 			    	    			result += "交换中心编码："+obj.centerCode+",交换中心名称："+obj.centerName+",结果："+obj.msg+"<br>";
			 			    	    		}else{
			 			    	    			result = obj;
			 			    	    		}
			 			    	    	}
		 			    	    		dialog.failure(result);
		 			    	    	}else{
		 			    	    		dialog.success("同步成功！");
		 			    	    	}
		 					    }     
		 			          });
		 		    	 }
		 			 },{width:450,height:200});
		 		};
			 	
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
		 	});
	 	</script>
	</template:replace>
</template:include>
