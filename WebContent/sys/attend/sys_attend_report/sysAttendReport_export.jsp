<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.dialog">
   <template:replace name="content">
   <br>
    <table class="tb_normal" width="95%" style="font-size: 12px;">
    	<tr>
	 		<td class="td_normal_title" width="150px">${lfn:message('sys-attend:sysAttendReport.fdExportShowCols') }</td>
	 		<td>
	 			<xform:radio property="fdExportShowCols" showStatus="edit" value="fdExportSelectCols" onValueChange="">
	 				<xform:simpleDataSource value="fdExportSelectCols">${lfn:message('sys-attend:sysAttendReport.fdExportShowCols.exportSelectCols') }</xform:simpleDataSource>
	 				<xform:simpleDataSource value="fdExportAllCols">${lfn:message('sys-attend:sysAttendReport.fdExportShowCols.exportAllCols') }</xform:simpleDataSource>
	 			</xform:radio>
	 		</td>
	 	</tr>
	 	<tr>
	 		<td class="td_normal_title" width="150px">${lfn:message('sys-attend:sysAttendReport.fdDateFormat') }</td>
	 		<td>
		 		<xform:radio property="fdDateFormat" showStatus="edit" onValueChange="" value="default">
		 				<xform:simpleDataSource value="default">${lfn:message('sys-attend:sysAttendReport.fdDateFormat.default') }</xform:simpleDataSource>
		 				<xform:simpleDataSource value="day">${lfn:message('sys-attend:sysAttendReport.fdDateFormat.day') }</xform:simpleDataSource>
		 				<xform:simpleDataSource value="hour">${lfn:message('sys-attend:sysAttendReport.fdDateFormat.hour') }</xform:simpleDataSource>
		 		</xform:radio>
	 		</td>
	 	</tr>
    </table>
    <div style="text-align: center;padding-top: 10px;">
   		<ui:button text="${lfn:message('sys-attend:sysAttendReport.export')}" onclick="exportExcel()"/>
   		<ui:button text="${lfn:message('button.close')}"  styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();"/> 
   	</div>
   	<script type="text/javascript">
		seajs.use(["lui/jquery","lui/dialog"],function($,dialog){
			window.exportExcel=function(){
				var fdExportShowCols=$("input:radio[name=fdExportShowCols]:checked").val();
				var fdDateFormat=$("input:radio[name=fdDateFormat]:checked").val();
				
				if(window.$dialog!=null){
					$dialog.hide({"fdExportShowCols":fdExportShowCols,"fdDateFormat":fdDateFormat});
				}else{
					window.close();
				}
			}
		})
	</script>
    </template:replace>
</template:include>