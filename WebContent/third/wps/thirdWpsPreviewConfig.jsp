<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	
	<template:replace name="title">
		${lfn:message('third-wps:thirdWps.preview.online.config')}
	</template:replace>

	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span style="color: #35a1d0;">${lfn:message('third-wps:thirdWps.preview.online.config')}</span>
		</h2>
		<html:form action="/third/wps/thirdWpsConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.preview.enable')}</td>
						<td>
							<ui:switch property="value(thirdWpsPreviewEnabled)"
								onValueChange="autoChangeEvent();"
								enabledText=""
								disabledText="" 
								>
							</ui:switch>
						</td>
					</tr>
					<tr class="thirdWps" >
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.preview.server.os')}</td>
						<td>
							<xform:radio property="value(thirdWpsOS)" showStatus="edit" onValueChange="changeTip(this.value)">
							        <xform:simpleDataSource value="linux">${lfn:message('third-wps:thirdWps.preview.server.linux')}</xform:simpleDataSource>
							        <xform:simpleDataSource value="windows">${lfn:message('third-wps:thirdWps.preview.server.windows')}</xform:simpleDataSource>
							</xform:radio>
							<div>
								<font size="0.5" color="#C0C0C0">
								     不同环境下相同功能的接口实现方式不一致，故需要区分，请核对该服务部署的环境
								</font>
							</div>
						</td>
					</tr>
					<tr class="thirdWps" >
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.preview.server.ip')}</td>
						<td>
							<html:text property="value(thirdWpsSetRedUrl)" style="width:90%"/>
							<div>
								<font size="0.5" color="#C0C0C0">
									<span id="urlTip">
									</span>
								</font>
							</div>
							<div>
								<font size="1" color="red">
									<span>
										注意：在线预览如果需要外网访问，需要开通外网
									</span>
								</font>
							</div>
							<div id="window_preview">
								<html:text property="value(thirdWpsWinPreviewUrl)" style="width:90%"/>
								<div>
								<font size="0.5" color="#C0C0C0">
									<span>
										在线预览服务地址
									</span>
								</font>
							</div>
							</div>
						</td>
					</tr>
					<tr class="thirdWps" id="appid" >
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.preview.server.id')}</td>
						<td>
							<html:text property="value(thirdWpsSetRedAppId)" style="width:90%"/>
						</td>
					</tr>
					<tr class="thirdWps" id="appSecret">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.preview.server.key')}</td>
						<td>
							<html:text property="value(thirdWpsSetRedAppSecret)" style="width:90%"/>
						</td>
					</tr>
					
				</table>
			</center>
						
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.wps.model.ThirdWpsConfig" />
			<center style="margin-top: 10px;">
				<kmss:authShow roles="ROLE_THIRDWPS_SETTING">
					<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="configChange();"></ui:button>
					<ui:button text="测试套红" height="35" width="120" onclick="testSetRed();"></ui:button>
					<ui:button text="测试预览" height="35" width="120" onclick="testPreview();"></ui:button>
				</kmss:authShow>
			</center>
		</html:form>

		<script type="text/javascript">

		function configChange() {
			Com_Submit(document.sysAppConfigForm, 'update');
		}
		
		function autoChangeEvent() {
			var autoCategoryTag = "true" == $("input[name='value\\\(thirdWpsPreviewEnabled\\\)']").val();

			if (autoCategoryTag) {
				$('.thirdWps').css('display', 'table-row');
			}else{
				$('.thirdWps').hide();
			}
		}
		function changeTip(val)
		{
			if(val == "linux")
			{
				var tip="主要包括套红、清稿、OFD格式附件转换等应用功能的服务地址";
				$("#urlTip").html(tip);
				$("#window_preview").hide();
				$('#appid').hide();
				$('#appSecret').hide();
			}else
			{
				var tip="主要包括套红、清稿等应用功能的服务地址";
				$("#urlTip").html(tip);
				$("#window_preview").show();
				$('#appid').show();
				$('#appSecret').show();
			}
		}
		LUI.ready(function() {
			autoChangeEvent();
			var existOS = '${existOS}';
			
			//兼容历史，默认是windows
			if(existOS == '')
			{
				$('.lui-lbpm-radio')[1].querySelector('input').click();
			}
			
			changeTip(existOS);

		});
		
		function testSetRed(){
			seajs.use(["lui/dialog", "lui/jquery"], function(dialog, $) {
				var loading = dialog.loading();
				var thirdWpsOS=$("input[name='value\\\(thirdWpsOS\\\)']:checked").val();
				var url=$("input[name='value\\\(thirdWpsSetRedUrl\\\)']").val();
					
				var thirdWpsAppId=$("input[name='value\\\(thirdWpsSetRedAppId\\\)']").val();
				var thirdWpsAppSecret=$("input[name='value\\\(thirdWpsSetRedAppSecret\\\)']").val();
				$.ajax({    
			 	     type:"post",     
			 	     url:Com_Parameter.ContextPath+"third/wps/thirdWpsCloud.do",     
			 	     data:{method:"testSetRed",wpsUrl:url,appId:thirdWpsAppId,appSecret:thirdWpsAppSecret,wpsOs:thirdWpsOS},  
			 	     dataType:"json",
			 	     success:function(data){
			 	    	 if(data.flag==="ok"){
			 	    		loading.hide();
			 	    		dialog.alert("测试成功");
			 	    	 }else{
			 	    		loading.hide();
			 	    		dialog.alert("测试失败:"+data.errorMsg);
			 	    	 }
			 	     }
				});
			});
		}
		
		function testPreview(){
			
			seajs.use(["lui/dialog", "lui/jquery"], function(dialog, $) {
				var loading = dialog.loading();
				var thirdWpsOS=$("input[name='value\\\(thirdWpsOS\\\)']:checked").val();
				var url=$("input[name='value\\\(thirdWpsSetRedUrl\\\)']").val();
				if(thirdWpsOS==="windows")
					url=$("input[name='value\\\(thirdWpsWinPreviewUrl\\\)']").val();
					
				var thirdWpsAppId=$("input[name='value\\\(thirdWpsSetRedAppId\\\)']").val();
				var thirdWpsAppSecret=$("input[name='value\\\(thirdWpsSetRedAppSecret\\\)']").val();
				$.ajax({    
			 	     type:"post",     
			 	     url:Com_Parameter.ContextPath+"third/wps/thirdWpsCloud.do",     
			 	     data:{method:"testPreview",wpsUrl:url,appId:thirdWpsAppId,appSecret:thirdWpsAppSecret,wpsOs:thirdWpsOS},  
			 	     dataType:"json",
			 	     success:function(data){
			 	    	 if(data.flag==="ok"){
			 	    		loading.hide();
			 	    		dialog.alert("测试成功");
			 	    	 }else{
			 	    		loading.hide();
			 	    		dialog.alert("测试失败:"+data.errorMsg);
			 	    	 }
			 	     }
				});
			});
			
		}
		
		</script>
	</template:replace>
</template:include>
