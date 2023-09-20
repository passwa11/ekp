<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	
	<template:replace name="title">
		${lfn:message('third-wps:py.WpsJiChengPeiZhi')}
	</template:replace>

	<template:replace name="content">

		<h2 align="center" style="margin: 10px 0">

			<span style="color: #35a1d0;">
				${lfn:message('third-wps:py.WpsJiChengPeiZhi')} </span>

		</h2>

		<html:form action="/third/wps/thirdWpsConfig.do">

			<center>

				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:py.WpsJiChengPeiZhi')}</td>
						<td>
							<ui:switch property="value(thirdWpsEnabled)"
								onValueChange="autoChange();"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" 
								>
								
							</ui:switch>
						</td>
					</tr>
					
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWpsSyn.url')}</td>
						<td>
							<html:text property="value(thirdWpsUrl)" style="width:90%"/>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.appId')}</td>
						<td>
							<html:text property="value(thirdWpsAppId)" style="width:90%"/>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.appSecret')}</td>
						<td>
							<html:text property="value(thirdWpsAppSecret)" style="width:90%"/>
						</td>
					</tr>
					<%-- <tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWpsSyn.setRed.url')}</td>
						<td>
							<html:text property="value(thirdWpsSetRedUrl)" style="width:90%"/>
						</td>
					</tr> --%>
					<%-- <tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.setRed.appId')}</td>
						<td>
							<html:text property="value(thirdWpsSetRedAppId)" style="width:90%"/>
						</td>
					</tr> --%>
					<%-- <tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.setRed.appSecret')}</td>
						<td>
							<html:text property="value(thirdWpsSetRedAppSecret)" style="width:90%"/>
						</td>
					</tr> --%>
					
					<%-- <tr class="thirdWps" style="display: none">
						<td colspan="2" class="td_normal_title" width="15%" style="color: red">
							${lfn:message('third-wps:thirdWps.config.attention')}
						</td>
					</tr> --%>
				</table>

			</center>
						
			<html:hidden property="method_GET" />
			<%-- <html:hidden property="value(thirdWpsAppValue)" /> --%>

			<input type="hidden" name="modelName"
				value="com.landray.kmss.third.wps.model.ThirdWpsConfig" />
			<center style="margin-top: 10px;">
			
				<kmss:authShow roles="ROLE_THIRDWPS_SETTING">
						<ui:button text="${lfn:message('button.save')}" height="35"
								width="120"
								onclick="configChange();"></ui:button>
						<ui:button text="测试" height="35"
								width="120"
								onclick="testWpsCloud();"></ui:button>
				</kmss:authShow>
			</center>
		</html:form>

		<script type="text/javascript">
		function configChange() {
			var isOn = "true" == $(
					"input[name='value\\\(kmsCategoryEnabled\\\)']")
					.val();

			// 开启
			if (isOn) {
				seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
					dialog.confirm('${ lfn:message("third-wps:kmsCategoryMain.config.refs.on.tip") }', function(ok) {
						if(!ok)
							return;
						else{
							Com_Submit(document.sysAppConfigForm, 'update');
						}
					});
				});
			}else
				Com_Submit(document.sysAppConfigForm, 'update');

		}
		
		function autoChange() {
			var autoCategoryTag = "true" == $(
					"input[name='value\\\(thirdWpsEnabled\\\)']")
					.val();

			if (autoCategoryTag) {
				//$('#advance').css('display', 'table-row');
				$('.thirdWps').css('display', 'table-row');
			}else{
				//$('#advance').hide();
				$('.thirdWps').hide();
			}
		}
		
		LUI.ready(function() {
			autoChange();
			/* var thirdWpsAppValue= $("input[name='value\\\(thirdWpsAppValue\\\)']").val();
			if(thirdWpsAppValue!=""){
				var _fdCheckScope = document.getElementsByName("fdCheckScope");
				for(var i = 0; i < _fdCheckScope.length; i++){
					if(thirdWpsAppValue.indexOf(_fdCheckScope[i].value)>-1) 
						_fdCheckScope[i].checked = true;
				}
			} */
		});
		
		//全选
		function selectAll(obj) {
			var _fdCheckScope = document.getElementsByName("fdCheckScope");
			for(var i = 0; i < _fdCheckScope.length; i++){
				if(obj.checked) {
					_fdCheckScope[i].checked = true;
				} else {
					_fdCheckScope[i].checked = false;
				}
			}
		}
		
		//单个的选择
		function selectElement(element){
			var thirdWpsAppValue = "";
			
			var _fdCheckScope = document.getElementsByName("fdCheckScope");
			for (var j = 0; j < _fdCheckScope.length; j++){
				if(_fdCheckScope[j].checked){
					if(thirdWpsAppValue!="")
						thirdWpsAppValue=thirdWpsAppValue+";"+_fdCheckScope[j].value;
					else
						thirdWpsAppValue=_fdCheckScope[j].value;
				}
			}
			$("input[name='value\\\(thirdWpsAppValue\\\)']").val(thirdWpsAppValue);
		}
		
		function testWpsCloud(){
			seajs.use(["lui/dialog", "lui/jquery"], function(dialog, $) {
				var loading = dialog.loading();
				var url=$("input[name='value\\\(thirdWpsUrl\\\)']").val();
				var thirdWpsAppId=$("input[name='value\\\(thirdWpsAppId\\\)']").val();
				var thirdWpsAppSecret=$("input[name='value\\\(thirdWpsAppSecret\\\)']").val();
				$.ajax({    
			 	     type:"post",     
			 	     url:Com_Parameter.ContextPath+"third/wps/thirdWpsCloud.do",     
			 	     data:{method:"testWpsCloud",wpsUrl:url,appId:thirdWpsAppId,appSecret:thirdWpsAppSecret},  
			 	     dataType:"json",
			 	     success:function(data){
			 	    	 if(data.flag==="ok"){
			 	    		loading.hide();
			 	    		dialog.alert("测试成功");
			 	    	 }else{
			 	    		loading.hide();
			 	    		dialog.alert("测试失败:"+data.error);
			 	    	 }
			 	     }
				});
			});
		}
		
		</script>
	</template:replace>
</template:include>
