<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<style>
	.profile_orgIO_content .title {
		font-size: 14px;
		color:#808080;
	}
	.profile_orgIO_content .title.title01 {
		margin-bottom: 20px;
	}
	.profile_orgIO_content .label01 {
		margin-left: 20px;
	}
	.profile_orgIO_btn_wrap {
		margin-top: 10px;
		margin-bottom: 10px;
	}
	.profile_orgIO_btn_wrap.btns {
		text-align: center;
	}
	.profile_orgIO_btn {
		display: inline-block;
		height: 30px;
		line-height: 30px;
		padding: 0 18px;
		margin-right: 5px;
		font-size: 14px;
	}
	.profile_orgIO_btn.btn_def {
		background: #37a8f5;
		color: #fff;
	}
	.profile_orgIO_btn.btn_def:hover{
		background:#008de2;
	}
	.profile_orgIO_btn.btn_gray {
		background: #f3f3f3;
		color: #a2a2a2;
	}
</style>

<div class="profile_orgIO_wrapper">
	<div class="profile_orgIO_content">
		<form name=custom_statistics_form action="${LUI_ContextPath}/sys/log/sys_login_info/sysLogLogin.do?method=exportCustomStatistics" method="post">
			<p class="title title01">
				<b><bean:message bundle="sys-log" key="sysLogOnline.statistics.type" />：</b>
				<label class="label01"><input type="radio" name="type" value="1"><bean:message bundle="sys-log" key="sysLogOnline.statistics.type1" /></label>
				<label class="label01"><input type="radio" name="type" value="2"><bean:message bundle="sys-log" key="sysLogOnline.statistics.type2" /></label>
			</p>
			<p class="title title01">
				<b><bean:message bundle="sys-log" key="sysLogOnline.statistics.day" />：</b>
				<label class="label01"><bean:message bundle="sys-log" key="sysLogOnline.statistics.day1" />
				<input type="text" name="day" size="3" maxlength="5" onkeyup="value=value.replace(/[^\d]/g,'')">
				<bean:message bundle="sys-log" key="sysLogOnline.statistics.day2" /><span id="info"></span></label>
			</p>
			<p class="title title01">
				<b><bean:message bundle="sys-organization" key="sysOrgPerson.fdLoginName" />：</b>
				<label class="label01"><input type="text" name="loginName"></label>
			</p>
			<div class="profile_orgIO_btn_wrap btns">
				<a href="javascript:void(0)" class="profile_orgIO_btn btn_def" onclick="_select();"><bean:message bundle="sys-log" key="sysLogOnline.statistics.select" /></a>
				<a href="javascript:void(0)" class="profile_orgIO_btn btn_def" onclick="_export();"><bean:message bundle="sys-log" key="sysLogOnline.statistics.export" /></a>
			</div>
		</form>
	</div>
</div>

<list:listview id="custom_statistics" channel="custom_statistics">
	<ui:source type="AjaxJson">
		{url:''}
	</ui:source>
	<list:colTable id="colTable" isDefault="true" layout="sys.ui.listview.columntable" channel="custom_statistics">
		<list:col-checkbox></list:col-checkbox>
		<list:col-serial></list:col-serial>
		<list:col-auto props=""></list:col-auto>
	</list:colTable>
</list:listview>
<br>
<!-- 分页 -->
	<list:paging channel="custom_statistics"/>

	<script type="text/javascript">
 	seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
 		LUI.ready(function(){
 			var colTable = LUI("colTable");
			$("input:radio").click(function() {
				var info;
				if(this.value == '1') {
					info = '<bean:message bundle="sys-log" key="sysLogOnline.statistics.type1.info"/>';
					colTable.columns[2].properties = "loginName,name,deptName";
				} else if(this.value == '2') {
					info = '<bean:message bundle="sys-log" key="sysLogOnline.statistics.type2.info"/>';
					colTable.columns[2].properties = "loginName,name,deptName,loginNum,loginTime";
				}
				$("#info").html(info);
			});
			$("input:radio[value=1]").click();
			_select();
 		});
 		
 		/**  统计数据    **/
 		window._select = function() {
 			var listView = LUI("custom_statistics");
 			var type = "&type=" + $("input[name=type]:checked").val();
 			var day = "&day=" + $("input[name=day]").val();
 			var loginName = "&loginName=" + $("input[name=loginName]").val();
 			
 			listView.sourceURL = '/sys/log/sys_login_info/sysLogLogin.do?method=listCustomStatistics' + type + day + loginName;
 			listView.source.setUrl(listView.sourceURL);
 			listView.source.get();
 		}
 		
 		/** 数据下载   **/
		window._export = function() {
 			// 提交表单进行下载
			document.custom_statistics_form.submit();
			
			// 显示加载中loading遮罩层
			window.export_load = dialog.loading();
			
			// ajax定时调用后台获取导出状态（用于判断导出是否结束）
			var url  = '<c:url value="/sys/log/sys_login_info/sysLogLogin.do?method=checkExportStatus"/>';
			$.ajaxSetup({ cache: false }); // 禁止jquery ajax缓存
			var intervalObj = window.setInterval(function(){
					$.ajax({
						url : url,
						type : 'POST',
						data : null,
						dataType : 'json',
						error : function(data) {
							if(window.export_load != null) {
								window.export_load.hide(); 
							}
							window.clearInterval(intervalObj);  // 清除定时器
							dialog.result(data.responseJSON);
						},
						success: function(data) {
							if(data.exportStatus=="1"){ // 导出状态   0：正在导出、1：导出结束
								window.clearInterval(intervalObj);  // 清除定时器 
								if(window.export_load != null){
									window.export_load.hide(); 
								}
							}
						}
				   });
			 }, 1000);
			
 		}
 		
	});
</script>
