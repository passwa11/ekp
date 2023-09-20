/**
 * JS调用方式，使用方法:
 		require(['mui/commondialog/DialogSelector'],function(DialogSelector){
			DialogSelector.select({
				key : '____cateSelect',
				dataURL : '${dataURL}',
				modelName:'${modelName}',
				callback : function(evt){
					//TODO your logic
				}
			});
 */
define( [ "dojo/_base/declare","dijit/_WidgetBase", "mui/form/_CategoryBase", "mui/commondialog/DialogSelectMixin","mui/i18n/i18n!sys-mobile","dojo/dom-construct","dojo/dom-attr", "dojo/dom-class"],
		function(declare,WidgetBase, CategoryBase, DialogSelectMixin, Msg, domConstruct, domAttr, domClass) {
			var CommonDialog = declare("mui.commondialog.DialogSelector", [WidgetBase, CategoryBase , DialogSelectMixin], {
				buildRendering:function(){
					this.inherited(arguments);
					if(!this.key)
						this.key = '_cateSelect';
				},
				postCreate : function() {
					this.inherited(arguments);
					this.eventBind();
				},
				startup : function(){
					this.inherited(arguments);
					this.defer(function(){
						this._selectCate();
					}, 350);
				},
				afterSelectCate:function(evt){
					this.callback(evt);
				},
				
				returnDialog:function(srcObj , evt){
					this.inherited(arguments);
					if(srcObj.key == this.key){
						this.afterSelectCate(evt);
					}
				}
			});
			return {
				select : function(options){
					options = options || {};
					var commonDialog = new CommonDialog(options);
					commonDialog.startup();
				}
			}
	});
