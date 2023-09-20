<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript">
	seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
		
		// 监听新建更新等成功后刷新
		topic.subscribe('successReloadPage', function() {
			topic.publish('list.refresh');
		});
		
		var getLeaveRules = function(callback) {
			var url = "${LUI_ContextPath}/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=getLeaveRules";
			$.ajax({
			   type: "GET",
			   url: url,
			   dataType: "json",
			   async:false,
			   success: function(data){
			     if(data && data.length>0){
			    	callback && callback(data);
			     } else {
		    	 	dialog.alert("${ lfn:message('sys-time:sysTimeLeaveAmount.atLeastOneAmount') }");
			     }
			   }
			});
		};
		
		//批量导入
		window.importFile = function(){
			getLeaveRules(function(){
				var uploadActionUrl = '${LUI_ContextPath}/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=fileUpload';
				var downloadTempletUrl =  '${LUI_ContextPath}/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=downloadTemplet';
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
			});
		};
		
		//新建
		window.addDoc = function(){
			getLeaveRules(function(){
				Com_OpenWindow('<c:url value="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do" />?method=add');
			});
		};
		
		//编辑
		window.editDoc = function(id){
			Com_OpenWindow('<c:url value="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do" />?method=edit&fdId=' + id,'_blank');
		};
		
		//删除
		window.deleteAll = function(id){
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
			dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
				if(value==true){
					window.del_load = dialog.loading();
					$.post('<c:url value="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=deleteall"/>',
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
				}
			});
		};
		//导出
		window.batchExport = function(){
 			var values = [];
			var url='<c:url value="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=batchExport"/>';
			openWindowWithPost(url,"selectedIds",values.toString());
		};
		
		window.openWindowWithPost= function(url,key,value){
		    var newWindow = window.open("post");  
		    console.log(value);
		    if (!newWindow)  
		        return false;  
		    var html = "";  
		    html += "<html><head></head><body><form id='formid' method='post' action='" + url + "'>";  
		    if (key && value) {  
		       html += "<input id='"+key+"' type='hidden' name='" + key + "' value='" +value+ "'/>";
		    }
		    html += "</form><script type='text/javascript'>document.getElementById('formid').submit();";  
		    html += "<\/script></body></html>".toString().replace(/^.+?\*|\\(?=\/)|\*.+?$/gi, "");   
		    newWindow.document.write(html);  
		    return newWindow; 
		};
	});
</script>