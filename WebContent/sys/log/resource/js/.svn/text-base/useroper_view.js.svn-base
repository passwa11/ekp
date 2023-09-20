var option = window.viewOption;

seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	//审计
	window.audit = function(id) {
		var title = option.lang.audit;
		var url = option.contextPath + '/sys/log/sys_log_user_oper/sysLogUserOper.do?method=audit';
		dialog.iframe('/sys/log/sys_log_user_oper/import/iframe_audit.jsp?List_Selected='+id, title,
			function (value){
                // 回调方法
				if(value) {
					//后台刷新较慢，前端无法实时更新
					setTimeout(function(){
 	                	window.location.reload();
					}, 1000)
				}
			},
			{width:400,height:300,params:{url:url,data:"List_Selected="+id}}
		);
	};
});

seajs.use([ 'sys/log/resource/js/contentShow' ], function(contentShow) {
	contentShow.format();
});