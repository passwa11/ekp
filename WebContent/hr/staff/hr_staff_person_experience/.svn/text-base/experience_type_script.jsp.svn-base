<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 模板下载表单 -->
<form id="downloadTempletForm" action="${HtmlParam.downloadTempletUrl}" method="post"></form>

<script type="text/javascript">
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		// 监听新建更新等成功后刷新
		topic.subscribe('successReloadPage', function() {
			setTimeout(function() {
				seajs.use(['lui/topic'], function(topic) {
					topic.publish('list.refresh');
				});
			}, 100);
		});
	
		// 模板下载
		window.download = function() {
			$("#downloadTempletForm").submit();
		};
	
		// 导入
		window._modify = function() {
			var type = "${JsParam.importType}";
			var _type = type.slice(0, 1).toUpperCase() + type.slice(1);
			var actionUrl = "${LUI_ContextPath}/hr/staff/hr_staff_person_experience/" + type + "/hrStaffPersonExperience" + _type + ".do?method=fileUpload";
			var downLoadUrl ="${JsParam.downloadTempletUrl}"; 
			dialog.iframe('/hr/staff/upload_files/common_upload_download.jsp?uploadActionUrl=' + actionUrl+'&downLoadUrl='+downLoadUrl, 
					'${JsParam.importTitle}', function(data) {
					topic.publish('list.refresh');
			}, {
				width : 680,
				height : 380
			});
		};
	
		// 删除
		window._delete = function(url, id) {
			var values = [];
				if(id) {
					values.push(id);
	 		} else {
				$("input[name='List_Selected']:checked").each(function() {
					values.push($(this).val());
				});
	 		}
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
				if(value == true) {
					window.del_load = dialog.loading();
					$.ajax({
						url : url,
						type : 'POST',
						data : $.param({"List_Selected" : values}, true),
						dataType : 'json',
						error : function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							dialog.result(data.responseJSON);
						},
						success: function(data) {
							if(window.del_load != null){
								window.del_load.hide(); 
								topic.publish("list.refresh");
							}
							dialog.result(data);
						}
				   });
				}
			});
		};
	});
</script>