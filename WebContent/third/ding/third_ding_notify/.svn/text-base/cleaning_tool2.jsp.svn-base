<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<style type="text/css">
		.tb_simple .inputsgl {
    height: 25px;
    border: 0px;
    border: 0px solid #dfdfdf; 
}
.tb_simple .inputselectsgl{
	    width: 30% !important;
}
</style>		
		
<template:include ref="config.profile.edit">
    <template:replace name="content">
         <table class="tb_normal" width="90%">
        	<tr>
        			<td class="td_normal_title" width="15%" style="text-align: right;"">钉钉待办清理人员：</td>
								<td>
									<div id="_xform_fdOrgAdminIds" _xform_type="text">
                        				<div class="inputselectsgl" style="width:51%;">
                        				<input type="hidden" style="width:100px" name="ekpUserId" readonly="true" /> 
                        				<xform:text property="ekpUserName" showStatus="edit" htmlElementProperties="readOnly='true'" style=" height: 24px; width: 98%;"  subject=""></xform:text>
                        				</div>
                       					<a href="#" onclick="Dialog_Address(false, 'ekpUserId', 'ekpUserName', null, ORG_TYPE_PERSON);">
										<bean:message key="dialog.selectOrg" /></a>
										<ui:button text="清除" onclick="cleaningNotify();" style="vertical-align: top;margin-left: 60px;"></ui:button>	
							</div>
								</td>
        	</tr>
        	<%-- <tr>
        			<td class="td_normal_title" width="15%" style="text-align: right;"">钉钉待办全部清除：</td>
								<td>
									<ui:button text="全部清除" onclick="cleaningAllNotify();" style="vertical-align: top;margin-left: 60px;"></ui:button>	
								</td>
        	</tr> --%>
		</table>			       
        <script type="text/javascript">
        	Com_IncludeFile("dialog.js");
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingNotify',
                templateName: '',
                basePath: '/third/ding/third_ding_notify/thirdDingNotify.do',
                canDelete: '${canDelete}',
                mode: '',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
            seajs.use(['lui/jquery','lui/dialog'],function($, dialog){
				 function cleaningNotify(){
					var ekpUserId = $("input[name='ekpUserId']").val();
					if(ekpUserId == "" || ekpUserId == null){
						dialog.alert("请选择人员！");
						return;
					}
					var dismissTips = '是否确认清除该人员待办？';
                    dialog.confirm(dismissTips, function(ok) {
                        if(ok == true) {
                			var url ="${LUI_ContextPath}/third/ding/third_ding_notify/thirdDingNotify.do?method=cleaningNotify&userId="+ekpUserId;       			
                			$.ajax({
								url : url,
								type : 'post',
								async : false,
								dataType : "json",
								success : function(data) {
									if(data.success){
										dialog.alert("清除该人员钉钉待办成功！");
										//location.reload();
									}else{
										dialog.alert(data.message);
									}
								} ,
								error : function(req) {
									
								}
						});
                 	 }
                	}); 
				}
				
				function cleaningAllNotify(){
					var dismissTips = '是否确认清除全部钉钉待办？';
                    dialog.confirm(dismissTips, function(ok) {
                        if(ok == true) {
                        	var del_load = dialog.loading();
                			var url ="${LUI_ContextPath}/third/ding/third_ding_notify/thirdDingNotify.do?method=cleaningAllNotify";         			
                			$.ajax({
								url : url,
								type : 'post',
								async : false,
								dataType : "json",
								success : function(data) {
									if(data.success){
										dialog.alert("操作成功！");
										location.reload();
									}else{
										dialog.alert(data.message);
									}
								} ,
								error : function(req) {
									
								}
						});
                 	 }
                	}); 
				}
				window.cleaningNotify = cleaningNotify;
				window.cleaningAllNotify = cleaningAllNotify;
				
			});  
        </script>
    </template:replace>
</template:include>