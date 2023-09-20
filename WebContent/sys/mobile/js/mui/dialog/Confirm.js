define([ "dojo/_base/declare", "dojo/dom-construct", "mui/dialog/Dialog","mui/i18n/i18n!sys-mobile" ],
		function(declare, domConstruct, Dialog,Msg) {
			return function(html, title, callback, iconClose, dialogCallback, others) {
				var _title = title ? title : Msg["mui.dialog.tips"]; // 提示标题，默认显示“提示”
				var _html = html ? html : "";
				var contentNode = null;
				if(html!=""){
					contentNode = domConstruct.create('div', { className : 'muiConfirmDialogElement',innerHTML : '<div>' + _html + '</div>'});
				}else{
					contentNode = domConstruct.create('div', { className : 'muiConfirmDialogNoElement' });
				}	

				var _iconClose =  iconClose === true ? true : false;
				
				
				//其他设置
				others = others || {};
				
				var options = {
					'title' : _title,
					'showClass' : 'muiConfirmDialogShow',
					'element' : contentNode,
					'closeOnClickDomNode': false,
					'scrollable' : false,
					'parseable' : false, 
					'iconClose' : _iconClose,
					'callback' : dialogCallback || null,
					'buttons' : [ {
						title : others.cancelTitle || Msg["mui.search.cancel"],
						fn : function(dialog) {
							
							//取消按钮校验
							if(others.cancelValidator
									&& Object.prototype.toString.call(others.cancelValidator) == '[object Function]'
									&& !others.cancelValidator(dialog)) {
								return;
							}
							
							dialog.hide();
							if(callback)callback(false, dialog);
 
						}
					}, {
						title : others.checkTitle || Msg["mui.button.ok"],
						fn : function(dialog) {
							
							//确认按钮校验
							if(others.checkValidator 
									&& Object.prototype.toString.call(others.checkValidator) == '[object Function]'
									&& !others.checkValidator(dialog)) {
								return;
							}
							
							dialog.hide();
							if(callback)callback(true, dialog);
						}
					} ]
				};
				return Dialog.element(options);
			};
		});