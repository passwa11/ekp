<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="com.landray.kmss.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">

	<template:replace name="title">
		${lfn:message("sys-mportal:sysMportal.msg.mportalTypeSet")}
	</template:replace>
	
	<template:replace name="head">
		<style>
			
		</style>
		<script>
		$(document).ready(function(){
			var sysMportalType = $("input[name='value(sysMportalType)']").val();
			if(sysMportalType == undefined || sysMportalType == ""){
				sysMportalType = 0;
				$("input[name='value(sysMportalType)']").val(sysMportalType);
				$("#sysMportalType0").click();
			}else{
				if(sysMportalType == "0"){
					$("#sysMportalType0").click();
				}else{
					$("#sysMportalType1").click();
				}
			}
		});
		</script>
	</template:replace>

	<template:replace name="content">

		<html:form action="/sys/mportal/sys_mportal_type_config/sysMportalTypeConfig.do">
			<center>
				<table width="98%" style="margin-top:50px;margin-bottom:50px;">		
					<tr>				
						<td align="center" width="50%" style="margin-top:15px;">
							<div style="width:240px;height:320px;">
								<img style="width:240px;height:320px;" alt="" src="${KMSS_Parameter_ContextPath}sys/mportal/resource/common_mportal@2x.png">
							</div>
							<input id="sysMportalType0" type="radio" name="sysMportalType" value="0">${lfn:message("sys-mportal:sysMportal.msg.common")}</input>							
						</td>
						<td  align="center" width="50%" >
							<div style="width:240px;height:320px;">
								<img style="width:240px;height:320px;" alt="" src="${KMSS_Parameter_ContextPath}sys/mportal/resource/composite_mportal@2x.png">
							</div>
							<input id="sysMportalType1" type="radio" name="sysMportalType" value="1">${lfn:message("sys-mportal:sysMportal.msg.composite")}</input>
						</td>
					</tr>			
				</table>
			</center>

			<html:hidden property="method_GET" />
			<html:hidden property="value(sysMportalType)"></html:hidden>
			<input type="hidden" name="modelName"
				value="com.landray.kmss.sys.mportal.model.SysMportalTypeConfig" />
			<input type="hidden" name="autoclose" value="false" />

			<center style="margin-top: 10px;">
				<ui:button text="${lfn:message('button.save')}" height="35"
					width="120"
					onclick="submit()"></ui:button>
			</center>
			
		</html:form>
		<script>
		Com_Parameter.event["submit"].push(function(){
			var val=$('input:radio[name="sysMportalType"]:checked').val();
			$("input[name='value(sysMportalType)']").val(val);
			return true;
		});
		
		window.submit = function(){
			Com_Submit.ajaxSubmit = function(form) {
	            var datas = $(form).serializeArray();
	            seajs.use(['lui/dialog', 'lui/util/env'],function(dialog, env) {
	                var dialog_loading = dialog.loading();
	                 $.ajax({
	                    url : env.fn.formatUrl(form.action),
	                    type : 'POST',
	                    dataType : 'json',
	                    data : $.param(datas),
	                    success : function(data,textStatus,xhr) {
	                            dialog_loading.hide();
	                            if(textStatus == "success"){  	                            	
	                            	var hash = window.top.location.hash;
	                            	var href = window.top.location.href;	                            	                        
	                            	if(!hash){
	                            		hash="#";                     		
	                            	}	                            	
	                            	window.top.location.hash = hash + "&LKSTreeDefaultNode=configNode";                       
	                            	window.top.location.reload();
	                            }else{
	                                dialog.alert(data);
	                            }                                                    
	                    },
	                    error : function(xhr,textStatus,errorThrown) {
	                            dialog_loading.hide();
	                            dialog.alert({
	                            	   html: xhr.responseText,
	                            	   iconType: "failure",
	                            	   title: "保存出错",
	                            	});                        
	                    }
	                });
	            });
			}
			Com_Submit(document.sysAppConfigForm, 'update');
		}
		</script>
	</template:replace>
</template:include>
