<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<style type="text/css">
.lui_paging_t_content_box .lui_icon_s {
    margin-top: 4px;
}
.lui_paging_t_refresh_l {
    top: 0px !important;
}
</style>
<template:include ref="nohead.config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1" expand="false">
            	 <list:cri-ref key="fdLogDetail" multi="true" ref="criterion.sys.docSubject" title="详细日志关键字" />
                <list:cri-auto modelName="com.landray.kmss.sys.oms.model.SysOmsTempTrx" property="beginTime" />
                <list:cri-auto modelName="com.landray.kmss.sys.oms.model.SysOmsTempTrx" property="endTime" />
				<list:cri-criterion title="${lfn:message('sys-oms:sysOmsTempTrx.fdSynStatus') }" key="fdSynStatus" multi="false" expand="true">
	            	<list:box-select>
	            		<list:item-select cfg-defaultValue="">
	            			<ui:source type="Static">
	            				[{text:'同步中', value:'0'},{text:'成功', value:'1'},{text:'失败', value:'2'},{text:'警告', value:'3'}]
	            			</ui:source>
	            		</list:item-select>
	            	</list:box-select>
	            </list:cri-criterion>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!--deleteall-->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <!-- 列表 -->
           <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdId;fdSynModel;beginTime;endTime;fdSynStatus;handle;" /></list:colTable>
           		 </list:listview>
            
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'temp_trx',
                modelName: 'com.landray.kmss.sys.oms.model.SysOmsTempTrx',
                templateName: '',
                basePath: '/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do',
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
            
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function($, dialog, topic, dialogCommon) {
            	function synAgain(fdId){
            		
            		dialog.confirm("再次执行同步，会将本次同步失败的数据重新同步一次，可能会将当前最新的数据覆盖掉，确定要再次执行同步吗？", function(ok) {
						 if(ok == true) {
							 $.ajax({
			    	     			url : "${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=synAgin&fdTrxId="+fdId,
			    					type : 'get',
			    					async : true,
			    					dataType : "json",
			    					success : function(data) {
			    						topic.publish("list.refresh");	
			    					} ,
			    					error : function(req) {
			                            //dialog.failure('操作失败');
			    					}
			    	     			
			    			  });		
						 }
					});	
            		
            	}
            	window.synAgain=synAgain;
            });
            
        </script>
    </template:replace>
</template:include>