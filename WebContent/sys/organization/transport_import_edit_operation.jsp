<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%-- 针对主数据关键字属性包含“全路径”时的信息提示 --%>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript">
seajs.use(['lui/jquery','lang!sys-organization'], function($,lang){
	function initNotice() {
		var isShowNotice = false;
		$.each($("select[name^='primaryKey']"), function(i, n){
			if($(n).children('option:selected').val() == "fdParentsName") {
				isShowNotice = true;
				return true;
			}
		});
		var noticeTip = lang['sysOrgElement.transport.view.notice'];
		if(isShowNotice) {
			if($("#fdParentsNameNotice").length < 1) {
				$("#transportTable>tbody").append('<tr id="fdParentsNameNotice"><td colspan=2><font color="red">'+noticeTip+'</font></td></tr>');
			}
		} else {
			$("#fdParentsNameNotice").remove();
		}
	}
	
	$(function(){
		// 初始化
		initNotice();
		
		// 判断是否有选中“全路径”
		$("select[name^='primaryKey']").change(function() {
			initNotice();
		});
		
	});
});


</script>