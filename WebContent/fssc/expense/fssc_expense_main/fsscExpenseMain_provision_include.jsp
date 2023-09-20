<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<kmss:ifModuleExist path="/fssc/provision">
<ui:content title="${ lfn:message('fssc-expense:py.YuTiXinXi') }" titleicon="lui-fm-icon-2" id="ytContent">
	<table class="tb_normal" width="100%" id="ytTable">
		<tr>
			<td  class="td_normal_title" width="10%" align="center">${lfn:message('page.serial') }</td>
			<td  class="td_normal_title" width="60%" align="center">${lfn:message('fssc-expense:py.MingCheng') }</td>
			<td  class="td_normal_title" width="30%" align="center">${lfn:message('fssc-expense:py.JinE') }</td>
		</tr>
	</table>
	<script>
		//默认隐藏
		LUI.ready(function(){
			setTimeout(function(){
				FSSC_ReloadProvisionInfo();
			},1000)
		})
	</script>
</ui:content>
</kmss:ifModuleExist>
