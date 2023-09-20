define([ "dojo/_base/declare", "dojo/dom-construct", "mui/dialog/Dialog","mui/i18n/i18n!sys-mobile" ],
		function(declare, domConstruct, Dialog, msg) {
			return function(html, title, callback, canClose) {
				var _title = title ? title : msg['mui.dialog.tips'];
				var _html = html ? html : "";
				var contentNode = domConstruct.create('div', {
					className : 'muiConfirmDialogElement',
					innerHTML : '<div>' + _html + '</div>'
				});
				var _canClose =  canClose === false ? false : true;
				var options = {
					'title' : title ? title : msg['mui.dialog.tips'],
					'showClass' : 'muiConfirmDialogShow',
					'element' : contentNode,
					'scrollable' : false,
					'parseable' : false,
					'canClose' : _canClose,
					'buttons' : [ {
						title : msg['mui.button.ok'],
						fn : function(dialog) {
							dialog.hide();
							//callback(true, dialog);
						}
					} ]
				};
				return Dialog.element(options);
			};
		});