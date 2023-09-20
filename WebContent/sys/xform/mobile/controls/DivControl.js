define([ "dojo/_base/declare", "dojo/dom-construct", "sys/xform/mobile/controls/xformUtil", "dojo/query", "mui/form/_FormBase", "dijit/_WidgetBase"],
	function(declare, domConstruct, xUtil, query, _FormBase,_WidgetBase) {

		// div控件不需继承_FormBase，以免被增加class，导致样式出现问题 by zhugr 2019-05-09
		var claz = declare("sys.xform.mobile.controls.DivControl", _WidgetBase, {
			showStatus : "view",

			postCreate: function() {
				this.inherited(arguments);
				// 和pc端的id保持一致
				if(this.domNode.id){
					var controlId = this.domNode.id;
					var resArray = /\((\w+)\)/.exec(controlId);
					var detailResArray = /\((\w+\.\d+.\w+)\)/.exec(controlId);
					if(resArray && resArray.length > 0){
						this.domNode.id = resArray[1];
					}else if(detailResArray && detailResArray.length > 0){
						this.domNode.id = detailResArray[1];
					}
				}
				//避免在moblie端，宽度太大，导致内容显示不全而无法输入内容 （#115294）
				$('div[id^="sys_xform_mobile_controls_DivControl"]').each(function(index,element) {
					$(element).css({"width":"100%"});
				});
			},

			_setRequiredAttr : function(value) {

			},

			_setReadOnlyAttr : function(value) {

			}

		});
		return claz;
	});