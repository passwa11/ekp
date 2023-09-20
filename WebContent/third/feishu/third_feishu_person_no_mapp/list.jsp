<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
    <template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("document.css", "style/"+Com_Parameter.Style+"/doc/");
			Com_IncludeFile("dialog.js|jquery.js");
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/ui/js/address/extend/simple/dialog.js"></script>
	</template:replace>
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdFeishuName" ref="criterion.sys.docSubject" title="${lfn:message('third-feishu:thirdFeishuPersonNoMapp.fdFeishuName')}" />
                <list:cri-auto modelName="com.landray.kmss.third.feishu.model.ThirdFeishuPersonNoMapp" property="fdFeishuMobileNo" />
                <list:cri-auto modelName="com.landray.kmss.third.feishu.model.ThirdFeishuPersonNoMapp" property="fdFeishuNo" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdFeishuPersonNoMapp.docAlterTime" text="${lfn:message('third-feishu:thirdFeishuPersonNoMapp.docAlterTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/feishu/third_feishu_person_no_mapp/thirdFeishuPersonNoMapp.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/feishu/third_feishu_person_no_mapp/thirdFeishuPersonNoMapp.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdFeishuName;fdEmployeeId;fdFeishuMobileNo;fdEmail;fdFeishuNo;operations" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'person_no_mapp',
                modelName: 'com.landray.kmss.third.feishu.model.ThirdFeishuPersonNoMapp',
                templateName: '',
                basePath: '/third/feishu/third_feishu_person_no_mapp/thirdFeishuPersonNoMapp.do',
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
                window.feishuDel = function(fdId,employeeId) {
    				var msg = '<bean:message bundle="third-feishu" key="thirdFeishuOmsInit.omsinit.person.del"/>';
    				var smsg = '<bean:message bundle="third-feishu" key="thirdFeishuOmsInit.omsinit.person.del.tip"/>';
    				var url = '<c:url value="/third/feishu/third_feishu_person_no_mapp/thirdFeishuPersonNoMapp.do?method=feishuDel&feishuId=" />'+employeeId+"&fdId="+fdId;
    				dialog.confirm(msg, function(value){
    					if(value == true) {
    						$.post(url, function(data){
    					    	if(data.status=="1"){
    					    		//if(type=="1"){
    					    			dialog.alert(smsg);
    						    		self.location.reload();
    					    		//}else{
    					    		//	$("#feishu"+fdId).attr("style","text-decoration: none;color: gray;font-size: 12px;");
    					    		//	$("#feishu"+fdId).removeAttr("onclick");
    					    		//	$("#ekp"+fdId).attr("style","color: gray;font-size: 12px;");
    					    		//	$("#ekp"+fdId).removeAttr("onclick");
    					    		//}
    					    	}
    					   }, "json");
    					}
    				});
    			}
    			window.ekpHandle = function(fdId,type) {
    				var url = '<c:url value="/third/feishu/third_feishu_person_no_mapp/thirdFeishuPersonNoMapp.do?method=ekpUpdate&fdId=" />'+fdId+"&fdEKPId="+$("input[name='fdEkpId"+fdId+"']").val();
    				 $.post(url, function(data){
    				    	if(data.status!="1"){
    				    		dialog.alert('<bean:message bundle="third-feishu" key="thirdFeishuOmsInit.omsinit.error"/>');
    				    		$("input[name='fdEkpId"+fdId+"']").val("");
    				    		$("input[name='fdEkpName"+fdId+"']").val("");
    				    	}else{
    				    		$("#feishu"+fdId).attr("style","text-decoration: none;color: gray;font-size: 12px;");
    			    			$("#feishu"+fdId).removeAttr("onclick");
    				    	}
    				   }, "json");
    			}
          });	
            
            </script>
    </template:replace>
</template:include>