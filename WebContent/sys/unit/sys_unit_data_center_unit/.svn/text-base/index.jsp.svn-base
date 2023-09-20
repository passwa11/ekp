<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.sys.unit.model.SysUnitDataCenterUnit" property="fdUnitCode" />
            	<list:cri-auto modelName="com.landray.kmss.sys.unit.model.SysUnitDataCenterUnit" property="fdIsAvailable" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:'/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do?method=list'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdUnitCode;fdIsAvailable;docCreator;docCreateTime;operations"/>
                </list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
	        seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		     	// 增加
		 		window.addDoc = function() {
		 			Com_OpenWindow('<c:url value="/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do" />?method=add');
		 		};
	         	// 编辑
		 		window.edit = function(id) {
			 		if(id) {
			 			Com_OpenWindow('<c:url value="/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do" />?method=edit&fdId=' + id);
			 		}
		 		};
		 		window.deleteAll = function(id){
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
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
	        });
        </script>
    </template:replace>
</template:include>