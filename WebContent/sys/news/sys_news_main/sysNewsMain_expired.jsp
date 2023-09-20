<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">	
	<br><br>
	<center>
		<script  type="text/javascript">
				seajs.use([ 'lui/jquery','lui/parser','sys/news/resource/js/dateUtil','lui/dialog'],function($,parser,dateUtil,dialog) {
					//确认
					window.clickOK=function(){
						var field = document.getElementsByName("fdExpiredTime")[0];
						var nowDate = new Date();
						nowDate.setHours(0);
						nowDate.setMinutes(0);
						nowDate.setSeconds(0);
						nowDate.setMilliseconds(0);
						var expiredDate = dateUtil.parseDate(field.value);
						if(expiredDate && (expiredDate.getTime() <= nowDate.getTime())){
							dialog.alert('<bean:message bundle="sys-news" key="sysNewsMain.docOverdueTime.after" />');
							return;
						}
						var rtn = field.value;
						$dialog.hide(rtn);
					};
				});
		</script>
		<table class="tb_normal" width=95% >
		   <tr>
			    <td width="150px"><bean:message bundle="sys-news" key="sysNewsMain.docOverdueTime" /></td>
				<td>
					<xform:datetime property="fdExpiredTime" showStatus="edit" dateTimeType="date" style="width:80%"></xform:datetime>
					<br>
					<font color="red"><bean:message bundle="sys-news" key="sysNewsPublishMain.push.set" /></font>
				</td>
			</tr>
		</table>
		<br><br><br><br>
		<ui:button text="${lfn:message('button.ok') }" onclick="clickOK();"></ui:button>
		<ui:button style="padding-left:10px"  text="${lfn:message('button.cancel') }" styleClass="lui_toolbar_btn_gray" onclick="$dialog.hide('cancel');"></ui:button>
	</center>	
	</template:replace>
</template:include>
