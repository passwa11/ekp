<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	
	<template:replace name="title">
		${lfn:message('third-wps:thirdWpsSyn.title')}
	</template:replace>

	<template:replace name="content">

		<h2 align="center" style="margin: 10px 0">
			<span style="color: #35a1d0;">${lfn:message('third-wps:thirdWpsSyn.title')} </span>
		</h2>

		<html:form action="/third/wps/thirdWpsConfig.do?isCenter=true">

			<center>

				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWpsSyn.title')}</td>
						<td>
							<ui:switch property="value(thirdWpsCenterEnabled)"
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
							<html:text property="value(thirdWpsCenterUrl)" style="width:80%"/>
							<xform:checkbox property="value(urlIsolation)" onValueChange="isolationSwitch(this)" showStatus="edit">
								<xform:simpleDataSource value="true"><bean:message key="thirdWpsSyn.url.isolation" bundle="third-wps" /></xform:simpleDataSource>
							</xform:checkbox>
							<p>
									${lfn:message('third-wps:thirdWpsSyn.url.tips')}
							</p>
						</td>
					</tr>
					<tr id="intranetUrlTr" class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWpsSyn.intranet.url')}</td>
						<td>
							<html:text property="value(thirdWpsCenterIntranetUrl)" style="width:90%"/>
							<p>
								${lfn:message('third-wps:thirdWpsSyn.intranet.url.tips1')}
								<br>
								${lfn:message('third-wps:thirdWpsSyn.intranet.url.tips2')}
							</p>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.appId')}</td>
						<td>
							<html:text property="value(thirdWpsCenterAppId)" style="width:90%"/>
							<p>
								${lfn:message('third-wps:thirdWps.appId.desc')}
							</p>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.appSecret')}</td>
						<td>
							<html:text property="value(thirdWpsCenterAppSecret)" style="width:90%"/>
							<p>
								${lfn:message('third-wps:thirdWps.appSecret.desc')}
							</p>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.shortTokenTimeOut')}</td>
						<td>
							<html:text property="value(thirdWpsShortTokenTimeOut)" style="width:90%"/>(${lfn:message('third-wps:thirdWps.unit.min')})
							<p>
								${lfn:message('third-wps:thirdWps.shortTokenTimeOut.desc')}
							</p>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.longTokenTimeOut')}</td>
						<td>
							<html:text property="value(thirdWpsLongTokenTimeOut)" style="width:90%"/>(${lfn:message('third-wps:thirdWps.unit.min')})
							<p>
								${lfn:message('third-wps:thirdWps.longTokenTimeOut.desc')}
							</p>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.wpsCenterMode')}</td>
						<td>
							<xform:radio property="value(wpsCenterMode)" value="" showStatus="edit" required="true">
								<xform:simpleDataSource value="1">${lfn:message('third-wps:thirdWps.wpsCenterMode1')}</xform:simpleDataSource>
								<xform:simpleDataSource value="2">${lfn:message('third-wps:thirdWps.wpsCenterMode2')}</xform:simpleDataSource>
							</xform:radio>
							<p>
									${lfn:message('third-wps:thirdWps.wpsCenterMode.desc')}
							</p>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>${lfn:message('third-wps:thirdWps.authorizationExpirationTime')}</td>
						<td>
							<html:text property="value(thirdWpsExpiredTime)" style="width:90%" readonly="true"/>
							<p>
								${lfn:message('third-wps:thirdWps.authorizationExpirationTime.desc')}
							</p>
						</td>
					</tr>
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
						<ui:button text="${lfn:message('third-wps:thirdWps.button.test')}" height="35"
								width="120"
								onclick="testWpsMiddleCloud();"></ui:button>
				</kmss:authShow>
			</center>
		</html:form>

		<script type="text/javascript">
		function configChange() {
			var isOn = "true" == $(
					"input[name='value\\\(kmsCategoryEnabled\\\)']")
					.val();
			if (!checkIntranetUrl()) {
				return;
			}
			if (!$KMSSValidation().validate()) {
				return;
			}
			// 开启
			if (isOn) {
				seajs.use(['lui/dialog', 'lui/jquery'], function (dialog, $) {
					dialog.confirm('${ lfn:message("third-wps:kmsCategoryMain.config.refs.on.tip") }', function (ok) {
						if (!ok)
							return;
						else {
							Com_Submit(document.sysAppConfigForm, 'update');
						}
					});
				});
			} else {
				Com_Submit(document.sysAppConfigForm, 'update');
			}
		}

		function checkIntranetUrl() {
			var r = true;
			seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
				if ($("input[name='_value\\\(urlIsolation\\\)']").prop('checked')) {
					var intranetUrl =  $("input[name='value\\\(thirdWpsCenterIntranetUrl\\\)']").val();
					if (intranetUrl == null || intranetUrl.trim() == '') {
						dialog.alert("请填写服务器地址（内网）！");
						r =  false;
					}
				}
			});
			return r;
		}

		function isolationSwitch() {
			$('#intranetUrlTr').hide();
			if ($("input[name='_value\\\(urlIsolation\\\)']").prop('checked')) {
				$('#intranetUrlTr').show();
			}
		}
		
		function autoChange() {
			var autoCategoryTag = "true" == $(
					"input[name='value\\\(thirdWpsCenterEnabled\\\)']")
					.val();
			if (autoCategoryTag) {
				//$('#advance').css('display', 'table-row');
				$('.thirdWps').css('display', 'table-row');
				isolationSwitch();
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
		
		function testWpsMiddleCloud(){
			if (!checkIntranetUrl()) {
				return;
			}
			seajs.use(["lui/dialog", "lui/jquery"], function(dialog, $) {
				var loading = dialog.loading();
				var url=$("input[name='value\\\(thirdWpsCenterUrl\\\)']").val();
				if ($("input[name='_value\\\(urlIsolation\\\)']").prop('checked')) {
					url = $("input[name='value\\\(thirdWpsCenterIntranetUrl\\\)']").val();
				}
				var thirdWpsCenterAppId=$("input[name='value\\\(thirdWpsCenterAppId\\\)']").val();
				var thirdWpsAppSecret=$("input[name='value\\\(thirdWpsCenterAppSecret\\\)']").val();
				 $.ajax({    
			 	     type:"post",     
			 	     url:Com_Parameter.ContextPath+"third/wps/thirdWpsCloud.do",     
			 	     data:{method:"testWpsCenter",wpsCenterUrl:url,appCenterId:thirdWpsCenterAppId,appCenterSecret:thirdWpsAppSecret},  
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
