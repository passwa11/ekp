define(	["dojo/_base/declare", "dijit/_WidgetBase","dojox/mobile/Container" ,"dojo/dom-class",
				"dojo/dom-style", "dojox/mobile/Tooltip", "dojo/dom-construct","dojo/query",
				"dijit/registry", "dojo/_base/lang", "dojo/dom-attr","dojo/_base/array","mui/dialog/Dialog"], function(declare,
				WidgetBase, Container, domClass, domStyle, Tooltip, domConstruct,query,
				registry, lang, domAttr, array, Dialog) {

			return declare("sys.lbpmservice.AddressButtonGroup", [WidgetBase,Container], {
				
				tmpl : '<div data-dojo-type="mui/form/RadioGroup" ' + 
					'data-dojo-props="showStatus:\'edit\',name:\'_lbpm_address_button_radio\',mul:\'false\',store:{store},orient:\'vertical\'"></div>',
				
				buildRendering : function() {
					this.inherited(arguments);
					domClass.add(this.domNode,'muiAddressButtonGroup');
//					domStyle.set(this.domNode,{
//						'position' : 'absolute',
//						'height' : '100%',
//						'top' : '0',
//						'right' : '0'
//					});
				},
				
				startup : function() {
					if (this._started)
						return;
					this.inherited(arguments);
					this.store = [];
					this.childrenMap = {};
					array.forEach(this.getChildren(),function(child){
						var obj = {
							text : child.text,
							value : child.id
						}
						this.childrenMap[child.id] = child;
						this.store.push(obj);
					},this);
					this.dialogNode = domConstruct.toDom(lang.replace(this.tmpl,{store: JSON.stringify(this.store).replace(/\"/g,"\'") }));
					domClass.add(this.dialogNode,'newMui');
					this.connect(this.domNode,'click','_onClick');
				},

				_onClick : function(evt) {
					var self = this;
					if (this.dialog != null){
						return;
					}
					this.dialog = Dialog.element({
						canClose : false,
						showClass : 'muiDialogElementShow muiFormSelect',
						element : this.dialogNode,
						position:'bottom',
						'scrollable' : false,
						'parseable' : true,
						onDrawed:function(){
							self.attachClick();
						},
						callback : lang.hitch(this, function() {
							this.dialog = null;
						})
					});
				},
				
				attachClick : function(){
					var self = this;
					self._curTime = 0;
					query(".muiRadioItem",this.dialogNode).on("click",function(evt){
						evt.preventDefault();
						evt.stopPropagation();
						var curTime = new Date();
						if(curTime - self._curTime<500){
							return;
						}
						self._curTime = curTime;
						var srcDom = evt.target;
						var fieldObj;
						if(srcDom.className=='muiRadioItem'){
							fieldObj = srcDom;
						}else{
							fieldObj = query(srcDom).parents(".muiRadioItem")[0];
						}
						//先隐藏弹窗，避免弹窗滚动出问题（和地址本等冲突）
						self.dialog.hide();
						self.dialog = null;

						var widget = self.childrenMap[query("input",fieldObj).val()];
						if(widget && widget._selectCate){
							widget._selectCate();
						}

					});
				}
			});
		});