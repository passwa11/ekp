define([ "dojo/_base/declare", "dojo/dom-construct", "mui/dialog/Dialog","mui/i18n/i18n!sys-mobile" ],
		function(declare, domConstruct, Dialog, Msg) {
	       
			/**
			* 构建Alert形式弹窗
			* @param contentHtml 弹窗描述内容(HTML字符串)
			* @param title 标题
			* @param callback 点击“确定”按钮的回调函数
			* @param icon 图标类型(可选项：success、fail、warn)
			* @param dialogCallback 弹窗框关闭后的回调函数
			* @return
			*/
			return function(contentHtml, title, callback, icon, dialogCallback) {
				var _title = "";
				if(title){
					_title = title;
				}
				var iconClass = "";
				if(icon){
					if(icon=="success"){
						_title = _title?_title:Msg["mui.alert.success"]; // 操作成功
						iconClass = "fontmuis muis-formpop-success";
					}else if(icon=="fail"){
						_title = _title?_title:Msg["mui.alert.failure"]; // 操作失败
						iconClass = "fontmuis muis-formpop-fail";
					}else if(icon=="warn"){
						_title = _title?_title:Msg["mui.alert.warning"]; // 操作提醒
						iconClass = "fontmuis muis-formpop-error";
					}
				}
				

				
				var alertNode = domConstruct.create('div', { className : 'muiAlertDialogElement' });
				if(iconClass){
					var iconNode = domConstruct.create('div', { className : 'muiAlertDialogIcon '+'muiAlertDialogIcon'+icon.substring(0,1).toUpperCase()+icon.substring(1) }, alertNode);
					var iconDivNode = domConstruct.create('div', {}, iconNode);
					domConstruct.create('i', { className : iconClass }, iconDivNode);
				}
				if(_title){
					var titleNode = domConstruct.create('div', { className : 'muiAlertDialogTitle', innerHTML:_title }, alertNode);
				}
				
				var _html = contentHtml ? contentHtml : "";
				if(_html!=""){
					domConstruct.create('div', { className : 'muiAlertDialogContent',innerHTML : '<div>' + _html + '</div>'}, alertNode);
				}
				
				
				var options = {
					'showClass' : 'muiAlertDialogShow',
					'element' : alertNode,
					'closeOnClickDomNode': false,
					'scrollable' : false,
					'parseable' : false, 
					'canClose' : false,
					'callback' : dialogCallback || null,
					'buttons' : [ {
						title : Msg["mui.button.ok"],
						fn : function(dialog) {				
							dialog.hide();
							if(callback)callback(dialog);
						}
					} ]
				};
				
				return Dialog.element(options);
			};
			
		});