define(["dojo/_base/declare", "mui/tabbar/TabBarButton", "dojo/dom-construct", "dojo/dom-style", "dojo/dom-class",
        "mui/dialog/Dialog", "mui/device/adapter","dojo/_base/lang","mui/util", "dojo/dom-attr","mui/i18n/i18n!sys-mobile" ],
		function(declare, TabBarButton, domConstruct,domStyle,domClass, Dialog, adapter,lang, util, domAttr,Msg) {
	
			return declare("mui.back.BackButton", [ TabBarButton ], {
				icon1 : "mui mui-back",
				
				//是否在编辑状态下 
				edit : false,
				
				
				
				editTip :Msg["mui.editor.exit"],
				
				buildRendering:function(){
					this.inherited(arguments);
					
					domClass.add(this.domNode,'muiBarFloatLeftButton');
					
					if(this.edit){
						domAttr.set(this.domNode, 'title', Msg["mui.button.exist"]);
						if(!this.label && !this.icon1){
							this.labelNode.innerHTML = Msg['mui.button.exist'];
						}
					}else{
						domAttr.set(this.domNode, 'title', Msg["mui.button.back"]);
						if(!this.label && !this.icon1){
							this.labelNode.innerHTML = Msg['mui.button.back'];
						}
					}
					
				},
				
				startup: function(){
					this.inherited(arguments);
					if(!this.icon1){
						domStyle.set(this.iconDivNode,{'width':'0px'});
					}
					//旧页面按钮写了行内样式，如果模块没改造，会导致按钮偏左
					if(domStyle.get(this.iconDivNode,"float") == 'left'){
						domStyle.set(this.iconDivNode,{'float':'none'});
					}
				},
				
				_onClick : function(evt) {
					this.defer( function() {
						if(this.edit){
							var contentNode = domConstruct.create('div', {
								className : 'muiBackDialogElement',
								innerHTML : '<div>' + util.formatText(this.editTip) +'<div>'
							});
							Dialog.element({
								'title' : Msg["mui.dialog.tips"],
								'showClass' : 'muiBackDialogShow',
								'element' : contentNode,
								'scrollable' : false,
								'parseable': false,
								'buttons' : [ {
									title : Msg["mui.search.cancel"],
									fn : function(dialog) {
										dialog.hide();
									}
								} ,{
									title : Msg["mui.button.ok"],
									fn : lang.hitch(this,function(dialog) {
										this.doBack();
										dialog.hide();
									})
								} ]
							});
						}else{
							this.doBack();
						}
					}, 450);// 延时处理原因：手机端延时300毫秒问题导致返回多次（iphone4发现问题）
			},
			doBack : function(refresh){
				var rtn = adapter.goBack();
				if(rtn == null){
					history.back();
				}
			}
			});
		});