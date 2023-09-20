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
    <center>
    	<div style="color:red;width:90%;" width="90%">
    	需要待办接收方在WebService服务中启用对应的接口（待办接收方系统中，集成管理->WebService服务->系统注册服务->待办服务务(针对ekpj)，V15版本才会有这个接口；如果V15版本中没找到这个接口，那么可以升级消息机制模块）
    	</div>
    	</center>
    	<br>
    	<br>
    	  <center>
		<div style="width:90%; text-align:left;">
		 待办清理：
		</div>
		</center>
         <table class="tb_normal" width="90%">
         	
        	<tr>
        			<td class="td_normal_title" width="15%" style="text-align: right;"">清理人员：</td>
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
		
		<br>
		<br>
		<br>
		<center>
		<div style="width:90%;    text-align:left;">
		 待办重推：
		</div>
		</center>
		
         <table class="tb_normal" width="90%">
         	
        	<tr>
        			<td class="td_normal_title" width="15%" style="text-align: right;"">重推人员：</td>
								<td>
									<div id="_xform_fdOrgAdminIds" _xform_type="text">
                        				<div class="inputselectsgl" style="width:51%;">
                        				<input type="hidden" style="width:100px" name="ekpUserId2" readonly="true" /> 
                        				<xform:text property="ekpUserName2" showStatus="edit" htmlElementProperties="readOnly='true'" style=" height: 24px; width: 98%;"  subject=""></xform:text>
                        				</div>
                       					<a href="#" onclick="Dialog_Address(false, 'ekpUserId2', 'ekpUserName2', null, ORG_TYPE_PERSON);">
										<bean:message key="dialog.selectOrg" /></a>
										<ui:button text="重推" onclick="resend();" style="vertical-align: top;margin-left: 60px;"></ui:button>	
							</div>
								</td>
        	</tr>
        </table>		       
        <script type="text/javascript">
        	Com_IncludeFile("dialog.js");
        	var listOption = {
                    contextPath: '${LUI_ContextPath}',
                    jPath: 'java_notify_que_err',
                    modelName: 'com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyQueErr',
                    templateName: '',
                    basePath: '/third/ekp/java/notify/third_ekp_java_notify_que_err/thirdEkpJavaNotifyQueErr.do',
                    canDelete: '${canDelete}',
                    mode: '',
                    templateService: '',
                    templateAlert: '${lfn:message("third-ekp-java-notify:treeModel.alert.templateAlert")}',
                    customOpts: {

                        ____fork__: 0
                    },
                    lang: {
                        noSelect: '${lfn:message("page.noSelect")}',
                        comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                    }

                };
            seajs.use(['lui/jquery','lui/dialog'],function($, dialog){
				 function cleaningNotify(){
					 var dismissTips = '是否确认清除该人员待办（对已处理但在接收系统没消失的待办进行处理）？';
					 
					var ekpUserId = $("input[name='ekpUserId']").val();
					if(ekpUserId == "" || ekpUserId == null){
						dismissTips = '是否对系统所有人的待办进行清理（对已处理但在接收系统没消失的待办进行处理）？该操作执行时间可能会很长，请在系统空闲的时间进行操作，并且最好对接收方系统数据库进行备份';
					}
					
                    dialog.confirm(dismissTips, function(ok) {
                        if(ok == true) {
                			var url ="${LUI_ContextPath}/third/ekp/java/notify/third_ekp_java_notify_que_err/thirdEkpJavaNotifyQueErr.do?method=clear&userId="+ekpUserId;       			
                			$.ajax({
								url : url,
								type : 'post',
								async : false,
								dataType : "json",
								success : function(data) {
									if(data.success){
										dialog.alert("清除待办成功，详细信息请查看日志文件！");
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
				
				
				 function resend(){
					 var dismissTips = '是否确认对该用户没有推送到接收系统的历史待办进行重推？';
					 
					var ekpUserId2 = $("input[name='ekpUserId2']").val();
					if(ekpUserId2 == "" || ekpUserId2 == null){
						dismissTips = '是否对系统所有人的历史待办进行重推？该操作执行时间可能会很长，请在系统空闲的时间进行操作，并且最好对接收方系统数据库进行备份';
					}
					
                    dialog.confirm(dismissTips, function(ok) {
                        if(ok == true) {
                			var url ="${LUI_ContextPath}/third/ekp/java/notify/third_ekp_java_notify_que_err/thirdEkpJavaNotifyQueErr.do?method=resendAll&userId="+ekpUserId2;       			
                			$.ajax({
								url : url,
								type : 'post',
								async : false,
								dataType : "json",
								success : function(data) {
									if(data.success){
										dialog.alert("重推待办成功，详细信息请查看日志文件！");
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
				
				window.cleaningNotify = cleaningNotify;
				window.resend = resend;
				
			});  
        </script>
    </template:replace>
</template:include>