define(
		[ "dojo/_base/declare","dojo/_base/array", "dojo/dom-construct", "mui/form/_FormBase",
				"dojo/dom-class", "dojo/_base/lang", "mui/util",
				"mui/form/_GroupBase", "dojo/dom-style" ],
		function(declare, array, domConstruct, _FormBase, domClass, lang, util,
				_GroupBase,domStyle) {
			var _field = declare(
					"mui.form._OptionsBase",
					[ _FormBase ],
					{

						text : '',

						alignment : '',
						
						checkedIcon : 'active',
						
						unCheckedIcon : '',

						// 构建值区域
						_buildValue : function() {
							this.inherited(arguments);
							var setBuildName = 'build'
									+ util.capitalize(this.showStatus);
							this[setBuildName] ? this[setBuildName]() : '';
							var setMethdName = this.showStatus + 'ValueSet';
							this.showStatusSet = this[setMethdName] ? this[setMethdName]
									: new Function();
						},
						

						buildRendering : function() {
							if (!this.domNode)
								this.domNode = this.srcNodeRef
										|| this.ownerDocument
												.createElement("div");
							this._buildValue();
							this.fieldOptions =this.optionContainerNode;
						},

						postCreate : function(){
							this.inherited(arguments);
							this.subscribe("mui/group/valueChage" , "_changeChecked");
							domStyle.set(this.domNode, {
								opacity : '0'
							});
						},
						
						_changeChecked:function(pWgt,value){
							if(this.getParent() == pWgt  && pWgt instanceof _GroupBase && value!=null && value!=''){
								var thisValue = this._get("value");
								this._extendCheckAction(thisValue==value || array.indexOf(value.split(";"),thisValue)>-1);
							}
						},
						
						_extendCheckAction:function(checked) {
							this._set("checked", checked);
							this.domNode.checked = checked;
						},
						
						_setCheckedAttr : function(checked) {
							this._extendCheckAction(checked);
							var type = checked ? 'add' : 'remove';
							var parent = this.getParent();
							if (parent && parent instanceof _GroupBase)
								parent[type + 'Value'](this.value);
						},

						_setValueAttr : function(value) {
							this.inherited(arguments);
							this.showStatusSet(value);
						},
						
						_readOnlyAction:function(value) {
							if(value){
								if(this._optionHandle){
									this.disconnect(this._optionHandle);
								}
								this._optionHandle = null;
							}else{
								this._optionHandle = this.connect(this.optionNode, 'click', '_onClick');
							}
						},

						/*******************************************************
						 * scrollable嵌套点击事件重复执行临时解决方案
						 ******************************************************/
						holdTime : 250,

						lastTime : null,

						fireClick : function(evt) {
							var time = this.lastTime;
							this.lastTime = new Date().getTime()
							if (time && this.lastTime - time <= this.holdTime)
								return false;
							return true;
						}
					});
			return _field;
		});