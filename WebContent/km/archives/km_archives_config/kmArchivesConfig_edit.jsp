<%@page import="com.landray.kmss.km.archives.model.KmArchivesConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<% 
	KmArchivesConfig config_ = new KmArchivesConfig();
	pageContext.setAttribute("defaultNotifyType", config_.getFdDefaultRemind());
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig" /></template:replace>
	<template:replace name="head">
		<script>
			Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp", null, "js");
		</script>
		<script type="text/javascript">
			function submitConfig() {
				//默认授权方式 name="fdDefaultRange"
				var rangeValues = [];
				var arrs = document.getElementsByName('fdDefaultRange');
				for (var i = 0; i < arrs.length; i++) {
					if(arrs[i].checked)
						rangeValues.push(arrs[i].value);
				}
				document.getElementsByName('value(fdDefaultRange)')[0].value = rangeValues.join(';');
				Com_Submit(document.sysAppConfigForm, 'update');
			}
			seajs.use(['lui/jquery'],function($) {
				$(document).ready(function() {
					var range = $("[name='value(fdDefaultRange)']").val();
					if(range) {
						var ranges = range.split(';');
						for (var i = 0; i < ranges.length; i++) {
							$("[name='fdDefaultRange'][value='"+ranges[i]+"']").prop('checked',true);
						}
					}
					$("input[name^='__notify_type_'][type='checkbox']").click(function () { 
						checkNotifyType(); 
					});
				});
			});
			//通知方式
			function checkNotifyType() {//提示【通知方式不能为空】
				var fdNotifyType = $("input[name='value(fdDefaultRemind)']").val();
				var $ntype = $("#fdNotifyType");
				if(null == fdNotifyType || fdNotifyType==""){
					$ntype.show();
					return false;
				}else{
					$ntype.hide();
					return true;
				}
			}
			Com_Parameter.event["submit"].push(function() {
				var checkFlag = checkNotifyType();
				return checkFlag;
			});
		</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" >
			<div style="margin-top:25px">
			<p class="configtitle">
				<bean:message bundle="km-archives" key="table.kmArchivesConfig"/>
			</p>
			<center>
			<table class="tb_normal" width=90%>
			
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdSoonExpireDate" />
					</td><td colspan=3>
						<bean:message bundle="km-archives" key="kmArchivesConfig.advance" />
						<xform:text property="value(fdSoonExpireDate)" 
							subject="${lfn:message('km-archives:kmArchivesConfig.fdSoonExpireDate')}" 
							validators="digits min(0)" style="width:20px;"></xform:text>
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdSoonExpireDate.tip" />
					</td>
				</tr>
			   <tr>
			   <td class="td_normal_title" width=15%>
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdEarlyReturnDate"/>
					</td><td  colspan="3">
						<bean:message bundle="km-archives" key="kmArchivesConfig.advance" />
						<xform:text property="value(fdEarlyReturnDate)" 
							subject="${lfn:message('km-archives:kmArchivesConfig.fdEarlyReturnDate')}" 
							validators="digits min(0)" style="width:20px;"></xform:text>
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdEarlyReturnDate.tip" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdMaxRenewDate"/>
					</td><td  colspan="3">
						<xform:text property="value(fdMaxRenewDate)" 
							subject="${lfn:message('km-archives:kmArchivesConfig.fdMaxRenewDate')}" 
							validators="digits min(0)" style="width:120px;"></xform:text>
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdMaxRenewDate.tip" />
					</td>
				</tr>
			    <tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdDefaultRange" />
					</td><td colspan=3>
						<html:hidden property="value(fdDefaultRange)"/>
						<label><input type="checkbox" value="copy" name="fdDefaultRange"/> 
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdDefaultRange.copy" /></label>
						<label><input type="checkbox" value="download" name="fdDefaultRange"/> 
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdDefaultRange.download" /></label>
						<label><input type="checkbox" value="print" name="fdDefaultRange"/> 
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdDefaultRange.print" /></label>
					</td>
				</tr>
			    <tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-archives" key="kmArchivesConfig.fdDefaultRemind" />
					</td><td colspan=3>
						<kmss:editNotifyType property="value(fdDefaultRemind)" value="${defaultNotifyType }"></kmss:editNotifyType>
						<div id="fdNotifyType" style="background: #fff7c6;margin-top: 10px;line-height: 27px;border: 1px #e8e8e8 solid;padding-left: 8px;display: none;">
							<img src="${KMSS_Parameter_ContextPath}km/archives/resource/images/tip_bulb.png" style="position: relative;top: 3px">
							<bean:message bundle="km-archives" key="kmArchivesConfig.please.choose.notifyType" />
						</div>
					</td>		
				</tr>
			</table>
			<div style="margin-bottom: 10px;margin-top:25px">
				   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="submitConfig();" order="1" ></ui:button>
			</div>
			</center>
			</div>
			<html:hidden property="method_GET"/>
			<html:hidden property="modelName" value="com.landray.kmss.km.archives.model.KmArchivesConfig"/>
		</html:form>
		<script>$KMSSValidation(document.forms['sysAppConfigForm']);</script>
	</template:replace>
</template:include>
