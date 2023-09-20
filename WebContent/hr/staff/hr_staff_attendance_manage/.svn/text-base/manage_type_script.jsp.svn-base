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
		// 经历类型改变，跳到相应的页面
		topic.subscribe('criteria.spa.changed', function(evt) {
			var _href = document.location.href;
			if (evt['criterions'].length > 0) {
				for ( var i = 0; i < evt['criterions'].length; i++) {
					var _type = evt['criterions'][i];
					if (_type.key == "_type") {
						var experienceKey = _type.value[0];
						if(_href.indexOf("/" + experienceKey + "/") == -1) {
							location.href = '${LUI_ContextPath}/hr/staff/hr_staff_attendance_manage/'+experienceKey+'/index.jsp';
						}
					}
				}
			}
		});
	
		// 模板下载
		window.download = function() {
			$("#downloadTempletForm").submit();
		};
	
		// 导入
		window._modify = function() {
			dialog.iframe('/hr/staff/upload_files/common_upload_download.jsp?uploadActionUrl=${JsParam.uploadActionUrl}&downLoadUrl=${JsParam.downloadTempletUrl}', 
					'${JsParam.importTitle}', function(data) {
					topic.publish('list.refresh');
			}, {
				width : 680,
				height : 380
			});
		};

		// 新增
		window._add = function(url) {
			Com_OpenWindow(url);
		};
		
		// 编辑
		window._edit = function(url) {
			Com_OpenWindow(url);
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