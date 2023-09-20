<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-evaluation" key="sysEvaluationWords.word.setting"/></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-evaluation" key="sysEvaluationWords.word.setting"/></span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				<table class="tb_normal" width=95%> 
					<tr >
						<td class="tr_normal_title" style="width: 15%">
							${ lfn:message('sys-evaluation:sysEvaluationNotes.switch')}
						</td>
						<td>
							<ui:switch property="value(fdEnable)" checkVal="1" unCheckVal="0"></ui:switch>
						</td>
					</tr> 

					<tr>
						<td class="tr_normal_title" style="width: 15%">
							${lfn:message('sys-evaluation:sysEvaluationConfig.setting.fdMaxTimesOneDay') }
						</td>
						<td><xform:text validators="number min(0) digits"
								property="value(fdMaxTimesOneDay)"></xform:text>
							${lfn:message('sys-evaluation:sysEvaluationConfig.setting.fdMaxTimesOneDay.time') }
						</td>
					</tr>
					<tr>
						<td class="tr_normal_title" style="width: 15%">
							${lfn:message('sys-evaluation:sysEvaluationConfig.setting.fdIntervalTime') }
						</td>
						<td><xform:text validators="number min(0) digits"
								property="value(fdIntervalTime)"></xform:text>
							${lfn:message('sys-evaluation:sysEvaluationConfig.setting.fdIntervalTime.minute') }
						</td>
					</tr>
				</table>
			</center>
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.evaluation.model.SysEvaluationNotesConfig" />
			<html:hidden property="method_GET" />
			<input type="hidden" name="autoclose" value="false" />
			<kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=update&modelName=com.landray.kmss.sys.evaluation.model.SysEvaluationNotesConfig" requestMethod="GET">
				<center style="margin:10px 0;">
					<!-- 保存 -->
					<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
					<script>
						$KMSSValidation();
					</script>
				</center>
			</kmss:auth>
		</html:form>
	</template:replace>
</template:include>
