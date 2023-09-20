define([ "dojo/_base/declare", "dojo/dom-construct", "mui/dialog/Dialog","mui/i18n/i18n!sys-mobile" ],
		function(declare, domConstruct, Dialog, Msg) {
	       
			/**
			* 构建Modal模态框，支持在弹窗中显示自定义内容，包括表单
			* @param contentHtml 弹窗描述内容(HTML字符串)
			* @param title 标题
			* @param buttons 模态框按钮JSON数组
			* @param dialogCallback 弹窗框关闭后的回调函数
			* @return
			*/
			return function(contentHtml, title, buttons, dialogCallback) {
				
				var modalNode = domConstruct.create('div', { className : 'muiModalDialogElement' });
                
				var isNeedDojoParse = false; // 是否需要dojo解析（字符串需要进行dojo解析）
				if(contentHtml){
					var contentNode = domConstruct.create('div', { className : 'muiModalDialogContent'}, modalNode);
					var contentDivNode = domConstruct.create('div', {}, contentNode);					
					if(typeof contentHtml === 'string'){
						contentDivNode.innerHTML = contentHtml;
						isNeedDojoParse = true;
					}else if(typeof contentHtml === 'object'){
						domConstruct.place(contentHtml, contentDivNode, "last");
					}
				}
				
				var defaultButtons = [ {
					title : Msg["mui.button.ok"], // 确定
					fn : function(dialog) {				
						dialog.hide();
					}
				} ];
				
				var options = {
					'title' : title?title:"",
					'showClass' : 'muiModalDialogShow',
					'element' : modalNode,
					'closeOnClickDomNode': false,
					'scrollable' : false,
					'scrollViewNode': contentDivNode,
					'parseable' : isNeedDojoParse, 
					'canClose' : false,
					'destroyAfterClose': false,
					'callback' : dialogCallback || null,
					'buttons' : buttons ? buttons : defaultButtons
				};
				
				return Dialog.element(options);
			};
			
		});