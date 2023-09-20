<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('third-ding:thirdDingDtask.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtask" property="fdDingUserId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtask" property="fdEkpUser" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtask" property="fdStatus" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtask" property="fdTaskId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtask" property="fdEkpTaskId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtask" property="docCreateTime" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingDtask.fdName" text="${lfn:message('third-ding:thirdDingDtask.fdName')}" group="sort.list" />
                            <list:sort property="thirdDingDtask.fdTaskId" text="${lfn:message('third-ding:thirdDingDtask.fdTaskId')}" group="sort.list" />
                            <list:sort property="thirdDingDtask.fdStatus" text="${lfn:message('third-ding:thirdDingDtask.fdStatus')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <%-- <kmss:auth requestURL="/third/ding/third_ding_dtask/thirdDingDtask.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/third/ding/third_ding_dtask/thirdDingDtask.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" /> --%>

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/third/ding/third_ding_dtask/thirdDingDtask.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/third_ding_dtask/thirdDingDtask.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdInstance.name;fdDingUserId;fdEkpUser.name;fdTaskId;fdEkpTaskId;fdStatus.name;docCreateTime;operations"/></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
	        seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	        	window.sendOpr = function(id){
					var url = '<c:url value="/third/ding/third_ding_dtask/thirdDingDtask.do?method=updateSend"/>&fdId='+id;
					dialog.confirm("${lfn:message('third-ding:enums.status.opr.send')}",function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:{fdId:id},
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									//dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
				};
	        	window.delCallback = function(data){
	        		if(data.error=='1'){
	        			alert(data.msg);
	        		}
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					//dialog.result(data);
				};
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
		 	});
        
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingDtask',
                templateName: '',
                basePath: '/third/ding/third_ding_dtask/thirdDingDtask.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-ding:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>