seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	//导出
	window.exportAll = function(listMethod, eventType) {
		if($("input[name='List_Selected']").length <= 0){
			dialog.alert({"html" : listOption.lang.noRecord, "title" : listOption.lang.exportTitle });
			return;
		}
        var selected = [];
        $("input[name='List_Selected']:checked").each(function() {
            selected.push($(this).val());
        });
        var dialogConfirm = listOption.lang.exportConfirmSelecte;
        if(selected.length == 0) {
        	dialogConfirm = listOption.lang.exportConfirmAll;
        }
        dialog.confirm(dialogConfirm, function(ok) {
        	if(ok == true){
        		window.exp_load = dialog.loading();
				var URL = listOption.contextPath + '/sys/log/sys_log_export/sysLogExport.do?method=export';
				URL += "&listMethod=" + listMethod;
				URL += "&eventType=" + eventType;
				//条件
				var criteriaId = $(".criteria").attr('id');
				if(criteriaId && LUI(criteriaId) && LUI(criteriaId)._buildCriteriaSelectedValues){
					$.each(LUI(criteriaId)._buildCriteriaSelectedValues(), function() {
						for(var i in this.value){
							URL += "&q." + encodeURIComponent(this.key) + "=" + encodeURIComponent(this.value[i]);
						};
					});
				}
				//排序
				var listviewId = $(".listview div:first").attr("id");
				if(listviewId && LUI(listviewId) && LUI(listviewId).parent && LUI(listviewId).parent.sorts){
					$.each(LUI(listviewId).parent.sorts, function() {
						for(var i in this.value){
							URL += "&" + encodeURIComponent(this.key) + "=" + encodeURIComponent(this.value[i]);
						};
					});
				}
                var param = {
                        "List_Selected": selected
                	};
				$.ajax({
					url: URL,
                    data: $.param(param, true),
                    dataType: 'json',
                    type: 'POST',
					success: function(data){
						if(window.exp_load!=null){
							window.exp_load.hide(); 
						}
						dialog.result(data);
					},
					error: function(data){
						if(window.exp_load!=null){
							window.exp_load.hide(); 
						}
						dialog.result(data);
					}
				});
        	}
        });
	}
	window.downloadPage = function(){
		Com_OpenWindow(listOption.contextPath + '/sys/log/sys_log_export/index.jsp', '_blank');
	}
});