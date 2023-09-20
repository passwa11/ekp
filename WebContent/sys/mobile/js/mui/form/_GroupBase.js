define([ "dojo/_base/declare", "mui/form/_FormBase",
		"mui/form/_StoreFormMixin", "dojo/dom-construct", 
		"dojo/topic", "mui/i18n/i18n!sys-mobile", "dojo/query" , "dijit/registry"], function(declare,
		_FormBase, _StoreFormMixin, domConstruct, topic, Msg,query,registry) {
	var _field = declare("mui.form._GroupBase", [ _FormBase, _StoreFormMixin ],	{

				name : null,
				
				baseClass : "muiFormEleWrap muiFormGroup",
				
				groupValueChange: "mui/group/valueChage",

				addValue : function(value) {

				},

				removeValue : function(value) {

				},

				/***************************************************************
				 * 构建隐藏域
				 **************************************************************/
				buildRendering : function() {
					this.inherited(arguments);
					if (this.edit) {
						// 设置containerNode，用于查找子组件
						this.containerNode = this.domNode;
						this.hiddenNode = domConstruct.create('input', {
							type : 'hidden',
							name : this.name
						}, this.domNode);
					}
				},
				
				_readOnlyAction:function(value){
					var wgts = query("*[widgetid]",this.domNode);
					wgts.forEach(function(tmpNode){
						var tmpWgt = registry.byNode(tmpNode);
						if(tmpWgt._readOnlyAction)
							tmpWgt._readOnlyAction(value);
					})
				},

				_setValueAttr : function(value) {
					this.inherited(arguments);
					if (this.edit){
						this.hiddenNode.value = value;
					}
					topic.publish(this.groupValueChange, this, value);
				},
				getText : function(){
					var text = [];
					if(this.value!=null && this.value!='') {
						var valArr = this.value.split(";");
						for (var i = 0; i < this.values.length; i++) {
							var option = this.values[i];
							for (j = 0; j < valArr.length; j++) {
								if (option.value == valArr[j]) {
									text.push(option.text);
									break;
								}
							}
						}
					}
					return text.join(";");
				}
			});
	return _field;

});