<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<style type="text/css">
		</style>
	</template:replace>
	<template:replace name="content"> 
		<table class="tb_normal" width="98%" style="margin-top: 10px;">
			<tr>
				<td colspan="4">
					<list:criteria id="criteria1">
						<list:cri-ref key="keyword" ref="criterion.sys.docSubject" title="${ lfn:message('hr-salary:hrSalaryPlanMain.fdName') }" />
					</list:criteria>
					<div style="height: 360px;overflow-y: auto;">
						<%--列表--%>
						<list:listview id="listview" style="">
							<ui:source type="AjaxJson" >
								{url:'/hr/salary/hr_salary_plan_main/hrSalaryPlanMainData.do?method=fdPlan'}
							</ui:source>
							<list:colTable isDefault="false" layout="sys.ui.listview.listtable" name="columntable" onRowClick="selectPlan('!{fdId}');">
								<list:col-radio></list:col-radio>
								<list:col-serial></list:col-serial>
								<list:col-html title="${ lfn:message('hr-salary:hrSalaryPlanMain.fdName') }">
									{$ <span data-id="{%row['fdId']%}" style="max-width: 300px;display: inline-block;">{%row['fdName']%}</span> $}
								</list:col-html>
								<list:col-html title="${ lfn:message('hr-salary:hrSalaryPlanMain.fdPackage') }">
									{$ <span data-id="{%row['fdId']%}_package" style="max-width: 300px;display: inline-block;">{%row['fdPackage.name']%}</span> $}
								</list:col-html>
								<list:col-html title="${ lfn:message('hr-salary:hrSalaryPlanMain.docCreator') }">
									{$ <span>{%row['docCreator.name']%}</span> $}
								</list:col-html>
								<list:col-html title="${ lfn:message('hr-salary:hrSalaryPlanMain.docCreateTime') }">
									{$ <span>{%row['docCreateTime']%}</span> $}
								</list:col-html>
							</list:colTable>
							<ui:event topic="list.loaded">
								var fdPlanId="${JsParam.fdPlanId}";
								if(fdPlanId){
									$('[name="List_Selected"][value="'+ fdPlanId +'"]').prop('checked','checked');
								}
							</ui:event>
						</list:listview>
						<list:paging></list:paging>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center;">
				  	<%--选择--%>
					<ui:button text="${lfn:message('button.select')}"   onclick="doSubmit();"/>
					<%--取消--%>
					<ui:button text="${lfn:message('button.cancel') }"  onclick="cancel();"/>
				</td>
			</tr>
		</table>
	
		<script type="text/javascript">
		 Com_IncludeFile("common.js", "${LUI_ContextPath}/hr/salary/resource/js/", 'js', true);
			seajs.use([
		 	      'lui/jquery',
		 	      'lui/dialog',
		 	      'lui/topic',
		 	      'lui/util/str'
		 	        ],function($,dialog,topic,str){
				window.selectPlan = function(fdId) {
					if($('[name="List_Selected"][value="'+ fdId +'"]').is(':checked')){
						$('[name="List_Selected"][value="'+ fdId +'"]').prop('checked', false);
					}else{
						$('[name="List_Selected"][value="'+ fdId +'"]').prop('checked',true);
					}
				}
				
				window.doSubmit = function() {
					var fdId = $('[name="List_Selected"]:checked').val();
					if(fdId) {
						var fdName = $('span[data-id="'+ fdId +'"]').text();
						var fdPackage = $('span[data-id="'+ fdId +'_package"]').text();
						if(fdName) {
							fdName = str.decodeHTML(fdName);
						}
						if (fdPackage) {
							fdPackage = str.decodeHTML(fdPackage);
						}
						$dialog.hide({fdId:fdId,fdName:fdName,fdPackage:fdPackage});
					} else {
						$dialog.hide(null);
					}
				};
				
				window.cancel = function() {
					$dialog.hide(null);
				}
			});
	</script>
	</template:replace>
</template:include>
