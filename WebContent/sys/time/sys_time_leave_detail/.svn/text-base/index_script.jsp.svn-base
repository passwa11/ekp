<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript">
	seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
		
		// 监听新建更新等成功后刷新
		topic.subscribe('successReloadPage', function() {
			topic.publish('list.refresh');
		});
		
		//新建
		window.addDoc = function(){
			Com_OpenWindow('<c:url value="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do" />?method=add');
		};
		
		//批量导入
		window.importDoc = function() {
			var uploadActionUrl = '${LUI_ContextPath}/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=fileUpload';
			var downloadTempletUrl =  '${LUI_ContextPath}/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=downloadTemplet';
			var importTitle = "${lfn:message('sys-time:sysTimeLeaveAmount.import.batch')}";
			var url = '/sys/time/upload_files/common_upload_download.jsp';
			url = Com_SetUrlParameter(url, 'uploadActionUrl', uploadActionUrl);
			url = Com_SetUrlParameter(url, 'downLoadUrl', downloadTempletUrl);
			url = Com_SetUrlParameter(url, 'isRollBack', false);
			dialog.iframe(url, importTitle ,function(data) {
					topic.publish('list.refresh');
			}, {
				width : 680,
				height : 380
			});
		}
		
		//批量扣减
		window.deductAll = function(id) {
			var values = [];
			if(id) {
 				values.push(id);
	 		} else {
				$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
	 		}
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			window.del_load = dialog.loading();
			$.post('<c:url value="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=deductAll"/>',
					$.param({"List_Selected":values},true),function(data){
				if(window.del_load!=null)
					window.del_load.hide();
				if(data!=null && data.status==true){
					topic.publish("list.refresh");
					dialog.success('<bean:message key="return.optSuccess" />');
				}else{
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			},'json');
		};
		
		window.deduct = function(id){
			window.loading = dialog.loading();
			$.post('<c:url value="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=deduct"/>&fdId=' + id, 
					null,function(data){
				if(window.loading!=null)
					window.loading.hide();
				if(data) {
					if(data.status != 1) {
						var errMsg = "${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.fail') }";
						if(data.reason){
							errMsg += '。' + "${ lfn:message('sys-time:sysTimeLeaveDetail.reason') }" + '：' + data.reason;
						}
						dialog.failure(errMsg, null, function(){
							topic.publish("list.refresh");
						});
					} else {
						dialog.success('<bean:message key="return.optSuccess" />', null, function(){
							topic.publish("list.refresh");
						});
					}
				} else {
					dialog.failure('<bean:message key="return.optFailure" />', null, function(){
						topic.publish("list.refresh");
					});
				}
			},'json');
		};
		
		window.updateAttend = function(id){
			window.loading = dialog.loading();
			$.post('<c:url value="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=updateAttend"/>&fdId=' + id, 
					null,function(data){
				if(window.loading!=null)
					window.loading.hide();
				if(data) {
					dialog.success('<bean:message key="return.optSuccess" />', null, function(){
						topic.publish("list.refresh");
					});
				} else {
					dialog.failure('<bean:message key="return.optFailure" />', null, function(){
						topic.publish("list.refresh");
					});
				}
			},'json');
		};
		
		window.updateAttendAll = function(id){
			var values = [];
			if(id) {
 				values.push(id);
	 		} else {
				$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
	 		}
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			window.del_load = dialog.loading();
			$.post('<c:url value="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=updateAttendAll"/>',
					$.param({"List_Selected":values},true),function(data){
				if(window.del_load!=null)
					window.del_load.hide();
				if(data!=null && data.status==true){
					topic.publish("list.refresh");
					dialog.success('<bean:message key="return.optSuccess" />');
				}else{
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			},'json');
		};
	});
</script>