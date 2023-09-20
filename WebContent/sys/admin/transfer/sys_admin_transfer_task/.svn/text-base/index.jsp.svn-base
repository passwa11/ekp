<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="title">
		${ lfn:message('sys-admin-transfer:table.sysAdminTransferTask') }
	</template:replace>
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<script type="text/javascript">
			Com_IncludeFile("data.js");
		</script>
		<link rel="stylesheet" href="<c:url value="/sys/admin/resource/images/dbcheck_select.css"/>?s_cache=${LUI_Cache}" />
		<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript" src="<c:url value="/sys/admin/resource/js/jquery.corner.js"/>?s_cache=${LUI_Cache}"></script>
		<style>
		.txttitle {
		    text-align: center;
		    font-size: 18px;
		    line-height: 30px;
		    color: #3e9ece;
		    font-weight: bold;
		}
		</style>
	</template:replace>
	<template:replace name="body">
		<br />
		<html:form action="/sys/admin/transfer/sys_admin_transfer_task/sysAdminTransferTask.do">
			<p class="txttitle">${ lfn:message('sys-admin-transfer:table.sysAdminTransferTask') }</p>
			<center>
			<div id="div_main" class="div_main">
			<table width="100%" class="tb_normal" cellspacing="1">
				<tr>
					<td class="rd_title">
						<label>
							<input type="radio" checked="checked"><bean:message key="sysAdminDbchecker.fdCheckType.1" bundle="sys-admin" />
						</label>
					</td>
				</tr>
			</table>
			<center>
			<table width="auto" class="tb_noborder" style="margin-top: 10px; background-color: transparent;"  cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
						<a href="javascript:void(0);" class="btn_submit_txt"
							onclick="window.check()"><bean:message bundle="sys-admin" key="sysAdminDbchecker.startCheck" /></a>
					</td>
				</tr>
			</table>
			</center>
			</div>
			</center>
			<html:hidden property="method_GET" />
			</html:form>

		<script type="text/javascript">
			seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				window.check = function() {
					dialog.confirm('<bean:message bundle="sys-admin" key="sysAdminDbchecker.startCheck.comfirm"/>', function(value){
						if(value == true) {
							Com_Submit(document.sysAdminTransferTaskForm, 'check');
							// 开启进度条
							window.progress = dialog.progress(false);
							window._progress();
						}
					});
				}
				
				window._progress = function () {
					var data = new KMSSData();
					data.UseCache = false;
					data.AddBeanData("sysAdminTransferScheduleTask");
					var rtn = data.GetHashMapArray()[0];
					
					if(window.progress) {
						if(rtn.msg == '') {
							window.progress.hide();
						}
						// 设置进度提示
						window.progress.setProgressText(rtn.msg);
						// 设置进度值
						var currentCount = rtn.current || 0;
						var allCount = rtn.totalCount || 0;
						if(allCount == 0) {
							window.progress.setProgress(0);
						} else {
							window.progress.setProgress(currentCount, allCount);
						}
					}
					// 如果总数量等于-1，表示操作还未执行
					// 如果当前执行的数量比总数量少，表示执行未结束
					if(rtn.checkState != 1) {
						setTimeout("window._progress()", 1000);
					}
				} 
			});
		</script>
	</template:replace>
</template:include>
