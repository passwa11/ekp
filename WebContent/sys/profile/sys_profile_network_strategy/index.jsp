<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
<script type="text/javascript">
	seajs.use(['theme!list']);
</script>

<!-- 排序 -->
<div class="lui_list_operation">
	<!-- 全选 -->
	<div class="lui_list_operation_order_btn">
		<list:selectall></list:selectall>
	</div>
	<!-- 分页 -->
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top" >
		</list:paging>
	</div>
	<div style="float:right">
		<div style="display: inline-block;vertical-align: middle;">
			<ui:toolbar count="5">
				<ui:button text="${lfn:message('button.add') }" onclick="addOrEdit();" order="1"></ui:button>
				<ui:button text="${lfn:message('button.deleteall') }" onclick="del();" order="2"></ui:button>
			</ui:toolbar>
		</div>
	</div>
</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>

<!-- 列表 -->
<list:listview>
	<ui:source type="AjaxJson">
		{url:'/sys/profile/sysProfileNetworkStrategy.do?method=list'}
	</ui:source>
	  <!-- 列表视图 -->	
	<list:colTable layout="sys.ui.listview.columntable" name="columntable">
		<list:col-checkbox></list:col-checkbox>
		<list:col-serial></list:col-serial> 
		<list:col-auto props="network;fdMark;docCreator;docCreateTime;operations"></list:col-auto>
	</list:colTable>
</list:listview> 
<list:paging></list:paging>

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
		// 增加 或 编辑
		window.addOrEdit = function(id) {
			var iframeUrl = "/sys/profile/sysProfileNetworkStrategy.do?method=add";
			var url = "${LUI_ContextPath}/sys/profile/sysProfileNetworkStrategy.do?method=save";
			if(id) {
				iframeUrl = "/sys/profile/sysProfileNetworkStrategy.do?method=edit&fdId=" + id;
				url = "${LUI_ContextPath}/sys/profile/sysProfileNetworkStrategy.do?method=update";
			}
			dialog.iframe(iframeUrl, '<bean:message bundle="sys-profile" key="sys.profile.org.passwordSecurityConfig.policyType.network"/>', function(data) {
				if (null != data && undefined != data) {
					if(id) {
						data.fdId = id;
					}
					$.post(url, data, function(result) {
						if(result.state) {
							topic.publish('list.refresh');
						} else {
							dialog.alert(result.msg);
						}
					}, "json");
				}
			}, {
				width : 900,
				height : 400
			});
		};

		// 删除
		window.del = function(id) {
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
			var url  = '<c:url value="/sys/profile/sysProfileNetworkStrategy.do?method=deleteall"/>';
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
