function config_softDelete_chgEnabled() {
	var lab_softDelete = $("#lab_softDelete");
	var tr_softDelete = $("#tr_softDelete");
	var isChecked = "true" == $("input[name='value\\\(softDeleteConfigEnable\\\)']").val();
	if (isChecked) {
		lab_softDelete.show();
		tr_softDelete.show();
	} else {
		lab_softDelete.hide();
		tr_softDelete.hide();
	}
}
LUI.ready(function(){
	config_softDelete_chgEnabled();
});

function _save() {
	$("input[name='addModules']").val("");
	$("input[name='delModules']").val("");
	
	// 判断功能是开启还是关闭
	var isEnable = $("[name='value(softDeleteConfigEnable)']").val();
	if(isEnable == "true") {
		// 开启状态，需要所有模块的状态
		// 获取所有已经选中的模块
		var enableModules = $("[name='_value(softDeleteConfigEnableModules)']:checked");
		var addModules = [], delModules = [], newModules = {};
		
		$.each(enableModules, function(i, n) {
			var val = $(n).val();
			newModules[val] = val;
			// 如果原始不包含，则为新增
			if(!oriEnableModules[val]) {
				addModules.push(val);
			}
		});
		
		for(var i in oriEnableModules) {
			if(!newModules[i]) {
				delModules.push(i);
			}
		}
		
		$("input[name='addModules']").val(addModules.join(";"));
		$("input[name='delModules']").val(delModules.join(";"));
		
		var msg = "";
		if(delModules.length != 0) {
			msg = $lang['delModulesInfo'];
			for(var i in delModules) {
				msg += models[delModules[i]] + ";"
			}
		} else if(addModules.length != 0) {
			msg = $lang['addModulesInfo'];
			for(var i in addModules) {
				msg += models[addModules[i]] + ";"
			}
		}
		
		if(delModules.length != 0) {
			var html = [];
			html.push('<div style="text-align:left;margin:10px">');
			html.push('	<div style="padding-bottom: 10px;">');
			html.push('   <span>'+$lang['confirmInfo1']+'</span>');
			html.push('	</div>');
			html.push('	<div style="color:red;">');
			html.push(msg);
			html.push('	</div>');
			html.push('	<div style="color:red;padding-top: 10px;">');
			html.push('   <span>'+$lang['confirmInfo4']+'</span>');
			html.push('	</div>');
			html.push('</div>');
			
			_closeRecycle(html.join(''), $lang['allRecover'], $lang['allDelete'], true);
		} else {
			var html = [];
			html.push('<div style="text-align:left;margin:10px">');
			html.push('	<div style="padding-bottom: 10px;">');
			html.push('   <span>'+$lang['confirmInfo2']+'</span>');
			html.push('	</div>');
			html.push('	<div style="color:red;">');
			html.push(msg);
			html.push('	</div>');
			html.push('	<div style="color:red;padding-top: 10px;">');
			html.push('   <span>'+$lang['confirmInfo4']+'</span>');
			html.push('	</div>');
			html.push('</div>');
			
			_closeRecycle(html.join(''), $lang['ok'], $lang['cancel'], false);
		}
	} else {
		// 关闭状态，需要提醒已删除数据是“还原”还是“永久删除”
		var html = [];
			html.push('<div style="text-align:left;margin:10px">');
			html.push('	<div style="padding-bottom: 10px;">');
			html.push('   <span>'+$lang['confirmInfo3']+'</span>');
			html.push('	</div>');
			html.push('	<div style="color:red;padding-top: 10px;">');
			html.push('   <span>'+$lang['confirmInfo1']+'</span>');
			html.push('	</div>');
			html.push('</div>');
		_closeRecycle(html.join(''), $lang['allRecover'], $lang['allDelete'], true);
	}
}

function _closeRecycle(html, okmsg, cancelmsg, isDel) {
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		var btns = [];
		// 确定
		btns.push({
			name : okmsg,
			value : true,
			focus : true,
			fn : function(value, dialogFr) {
				dialogFr.hide();
				if(isDel)
					$("input[name='allOpertType']").val("1");
				Com_Submit(document.softDeleteConfigForm, 'save');
			}
		});
		btns.push({
			name : cancelmsg,
			styleClass : "lui_toolbar_btn_gray",
			value : false,
			fn : function(value, dialogFr) {
				dialogFr.hide();
				if(isDel) {
					$("input[name='allOpertType']").val("2");
					Com_Submit(document.softDeleteConfigForm, 'save');
				}
			}
		});
		if(isDel) {
			btns.push({
				name : $lang['cancel'],
				styleClass : "lui_toolbar_btn_gray",
				value : false,
				fn : function(value, dialogFr) {
					dialogFr.hide();
				}
			});
		}
		
		dialog.build({
			config : {
				width : 400,
				height : 200,
				lock : true,
				title : $lang['operationTitle'],
				content : {
					type : "Html",
					html : html,
					buttons : btns
				}
			},
		}).show();
	});
}

function _check() {
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		var enableModules = [];
		$.each($("[name='_value(softDeleteConfigEnableModules)']:checked"), function(i, n) {
			enableModules.push($(n).val());
		});
		if(enableModules.length == 0) {
			dialog.alert($lang['checkNoTransfer']);
			return false;
		}
		dialog.iframe('/sys/recycle/config/check_dialog.jsp',
			$lang['checkTransfer'],
			function (value){
			},
			{width:600,height:400,params:{data:enableModules}}
		);
	});
}