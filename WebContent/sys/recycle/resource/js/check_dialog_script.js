
seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
	window.del_load = dialog.loading();
	
	var myVar, count = 0;
	window._getData = function() {
		// 防止死循环
		if (count++ > 10) {
			clearTimeout(myVar);
			dialog.failure($lang['dialogFailure']);
			return;
		}
		if (window.$dialog) {
			var modules = window.$dialog.content.params.data;
			clearTimeout(myVar);
			__check(modules);
		} else {
			myVar = setTimeout("window._getData()", 100);
		}
	}
	
	// 检查是否需要迁移
	window.__check = function(modules) {
		$.ajax({
			url: $var['checkUrl'],
			data: {'modules': modules.join(";")},
			dataType: 'json',
			success: function(result) {
				var _table = $("#_modules_table tbody").empty();
				if(result.resultMaps) {
					for(var i in result.resultMaps) {
						var tr = "<tr><td>" + i + "</td><td>" + result.resultMaps[i] + "</td></tr>";
						_table.append(tr);
					}
				}
				
				if(!result.isTransfer) {
					$("#__do").hide();
					$("#__msg").text($lang['checkNoTransfer']).show();
				}
			},
			complete: function() {
				if(window.del_load != null) {
					window.del_load.hide(); 
				}
			}
		});
	}
	
	// 执行迁移
	window._do = function() {
		dialog.confirm($lang['doConfirm'], function(value) {
			if(value == true) {
				window.del_load = dialog.loading();
				$.ajax({
					url: $var['doUrl'],
					dataType: 'json',
					success: function(result) {
						if(result.state){
							dialog.success($lang['dialogSuccess']);
							Com_CloseWindow();
						} else {
							dialog.failure($lang['dialogFailure']);
						}
					},
					complete: function() {
						if(window.del_load != null) {
							window.del_load.hide(); 
						}
					}
				});
			}
		});
	}
	
	$(function() {
		window._getData();
	});
});