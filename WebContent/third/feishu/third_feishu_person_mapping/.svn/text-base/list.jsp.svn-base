<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
    
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdEmployeeId" ref="criterion.sys.docSubject" title="${lfn:message('third-feishu:thirdFeishuPersonMapping.fdEmployeeId')}" />
                <list:cri-auto modelName="com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping" property="fdEkp" />
                <list:cri-auto modelName="com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping" property="fdLoginName" />
                <list:cri-auto modelName="com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping" property="fdMobileNo" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
							<kmss:auth requestURL="/third/feishu/third_feishu_person_mapping/thirdFeishuPersonMapping.do?method=omsInit">
								<ui:button text="${ lfn:message('third-feishu:thirdFeishuOmsInit.person.init') }"
									onclick="window.check();" order="1" >
								</ui:button>
							</kmss:auth>
                            <kmss:auth requestURL="/third/feishu/third_feishu_person_mapping/thirdFeishuPersonMapping.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/third/feishu/third_feishu_person_mapping/thirdFeishuPersonMapping.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/third/feishu/third_feishu_person_mapping/thirdFeishuPersonMapping.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/feishu/third_feishu_person_mapping/thirdFeishuPersonMapping.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdEkp.name;fdEmployeeId;fdOpenId;fdLoginName;fdMobileNo" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'person_mapping',
                modelName: 'com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping',
                templateName: '',
                basePath: '/third/feishu/third_feishu_person_mapping/thirdFeishuPersonMapping.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-feishu:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/feishu/resource/js/", 'js', true);
            
            seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
            	
                window.check = function() {
                	var message = '<bean:message bundle="third-feishu" key="thirdFeishuOmsInit.org.init.tip"/>';
        			var url = '<c:url value="/third/feishu/third_feishu_person_mapping/thirdFeishuPersonMapping.do?method=omsInit" />';
        			var inittip = '<bean:message bundle="third-feishu" key="thirdFeishuOmsInit.omsinit.finish.org"/>';
        			var errorUrl = '<c:url value="/third/feishu/third_feishu_person_mapping/list.jsp" />';
        			
    				dialog.confirm(message, function(value){
    					if(value == true) {
    						$.ajax({
    						   type: "POST",
    						   url: url,
    						   async:true,
    						   dataType: "json",
    						   timeout : 600000,
    						   success: function(data){
    								if(data.status=="1"){
    									if("1"==data.errors){
    										dialog.confirm('<bean:message bundle="third-feishu" key="other.init.error"/>',function(val){
    											window.progress.hide();
    											if(val){
    												self.location.href = errorUrl;
    											}else{
    												self.location.reload();
    											}
    										});
    									}else{
    										dialog.alert('<bean:message bundle="third-feishu" key="other.init.finish"/>',function(){
    											//window.progress.hide();
    											self.location.reload();
    										});
    									}
    								}else{
    									dialog.alert('<bean:message bundle="third-feishu" key="other.init.errormsg"/>',function(){
    										window.progress.hide();
    										self.location.reload();
    									});
    								}
    						   },
    						   error: function(data){
    							   //window.progress.hide();
    						   }
    						});
    						// 开启进度条
    						//window.progress = dialog.progress();
    						//window._progress();
    					}
    				});
    			}
                });
            
        </script>
    </template:replace>
</template:include>