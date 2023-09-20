<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
    
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1" expand="true">
                <list:cri-auto modelName="com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping" property="fdWelinkName" />
                <list:cri-auto modelName="com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping" property="fdEkpDept" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdWelinkDeptMapping.docAlterTime" text="${lfn:message('third-welink:thirdWelinkDeptMapping.docAlterTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="2">
                        <!-- 
						<kmss:auth requestURL="/third/welink/third_welink_dept_mapping/thirdWelinkDeptMapping.do?method=omsInit">
						<ui:button text="${ lfn:message('third-welink:thirdWelinkOmsInit.person.init') }"
							onclick="window.check();" order="1" >
						</ui:button>
						</kmss:auth>

						<kmss:auth requestURL="/third/welink/thirdWelink.do?method=getSyncStatus&orgType=dept">
                                <ui:button text="查询同步结果" onclick="getSyncStatus()" order="1" />
                            </kmss:auth>
                            -->
                            <kmss:auth requestURL="/third/welink/third_welink_dept_mapping/thirdWelinkDeptMapping.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/third/welink/third_welink_dept_mapping/thirdWelinkDeptMapping.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/welink/third_welink_dept_mapping/thirdWelinkDeptMapping.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/welink/third_welink_dept_mapping/thirdWelinkDeptMapping.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdWelinkId;fdWelinkName;fdEkpDept.name;docAlterTime" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'dept_mapping',
                modelName: 'com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping',
                templateName: '',
                basePath: '/third/welink/third_welink_dept_mapping/thirdWelinkDeptMapping.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-welink:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/welink/resource/js/", 'js', true);
            
            window.check = function() {
            	var message = '<bean:message bundle="third-welink" key="thirdWelinkOmsInit.org.init.tip"/>';
    			var url = '<c:url value="/third/welink/third_welink_dept_mapping/thirdWelinkDeptMapping.do?method=omsInit" />';
    			var inittip = '<bean:message bundle="third-welink" key="thirdWelinkOmsInit.omsinit.finish.org"/>';
    			var errorUrl = '<c:url value="/third/welink/third_welink_dept_mapping/thirdWelinkDeptMapping.do?method=list" />';
    			
				dialog.confirm(message, function(value){
					if(value == true) {
						$.ajax({
						   type: "POST",
						   url: url,
						   async:true,
						   dataType: "json",
						   success: function(data){
								if(data.status=="1"){
									if("1"==data.errors){
										dialog.confirm('<bean:message bundle="third-welink" key="other.init.error"/>',function(val){
											window.progress.hide();
											if(val){
												self.location.href = errorUrl;
											}else{
												self.location.reload();
											}
										});
									}else{
										dialog.alert('<bean:message bundle="third-welink" key="other.init.finish"/>',function(){
											window.progress.hide();
											self.location.reload();
										});
									}
								}else{
									dialog.alert('<bean:message bundle="third-welink" key="other.init.errormsg"/>',function(){
										window.progress.hide();
										self.location.reload();
									});
								}
						   },
						   error: function(data){
							   window.progress.hide();
						   }
						});
						// 开启进度条
						window.progress = dialog.progress();
						window._progress();
					}
				});
			}
            
            function getSyncStatus(){
				var url = '<c:url value="/third/welink/thirdWelink.do?method=getSyncStatus&orgType=dept" />';
				window.open(url,"_blank");
			}
            
        </script>
    </template:replace>
</template:include>