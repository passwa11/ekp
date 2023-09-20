define([ "dojo/_base/declare", "dojo/query", "dojo/dom-class",
		"mui/form/_SelectBase", "dojo/dom-construct",
		"mui/form/select/_StoreSelectMixin", "mui/dialog/Dialog",
		'dojo/parser', "dojo/_base/array", "dojo/_base/lang", "dojo/topic" ,"mui/i18n/i18n!sys-mobile"],
		function(declare, query, domClass, _SelectBase, domConstruct,
				_StoreSelectMixin, Dialog, parser, array, lang, topic,msg) {
			var _field = declare("mui.form.Select", [ _SelectBase,
					_StoreSelectMixin ], {

				value : '',

				text : '',

				type : 'select',
				
				muiSingleRowViewClass : "muiSelInput",
				
				// 在明细表删除行操作中，需要更新索引的属性
				needToUpdateAttInDetail : ['name','valueField'],
				
				mul:true,

				CHECK_CHANGE : 'mui/form/checkbox/change',

				SELECT_CALLBACK : 'mui/form/select/callback',
				
				value_s:[],

				showPleaseSelect:false,
				
				opt : true,

				startup : function() {
					this.inherited(arguments);
					this.valueField = this.name;
				},

				// 渲染模板
				renderListItem : function() {
					this.inherited(arguments);
				},

				closeDialog : function(srcObj, evt) {
					if (!this.mul) {
						if (evt.name == (this.selectBoxPrefix + this.valueField)) {
							this.set('value', evt.value);
							this._closeDialog();
						}
					}else{
						if(srcObj.params.name== (this.selectBoxPrefix + this.valueField)){
							var type = srcObj.checked ? 'add' : 'remove';
							this[type+'Value'](srcObj.value);
						}
					}
				},
				
				_DoneClick : function() {
					this.set('value', this.value_s.join(';'));
					this._closeDialog();
				},
				
				_clearClick: function(){
					this.set('value', '');
					this.editValueSet('');
					this._closeDialog();					
				},

				_closeDialog : function() {
					topic.publish(this.SELECT_CALLBACK, this);
					if(this.dialog){
						this.dialog.hide();
						this.dialog = null;
					}
				},

				buildRendering : function() {
					this.inherited(arguments);
				},

				_setTextAttr : function(text) {
					this.inputContent.innerHTML = text;
					this.text = text;
				},

				buildEdit : function() {
					this.inherited(arguments);
					this.selectNode =domConstruct.create('input', {
						name : this.name,
						type : 'hidden',
						value : this.value
					},this.domNode);
					this.bindEvent();

				},

				buildReadOnly : function() {
					this.selectNode =domConstruct.create('input', {
						name : this.name,
						type : 'hidden',
						readOnly : 'readOnly',
						value : this.value
					},this.domNode);
				},

				buildHidden : function() {
					this.selectNode =domConstruct.create('input', {
						name : this.name,
						type : 'hidden',
						value : this.value
					},this.domNode);
				},

				viewValueSet : function(value) {
					if(value!=null && value!=''){
						this.set('text', this.getTextByValue(value));
					}
				},

				editValueSet : function(value) { 
					if(value||!this.showPleaseSelect){
						domClass.add(this.domNode,"showTitle");
					    this.set('text', this.getTextByValue(value));
					}else{
						this.set('text', '');
					}
					this.selectNode.value = value;
				},

				hiddenValueSet : function(value) {
					this.editValueSet(value);
				},

				readOnlyValueSet : function(value) {
					this.editValueSet(value);
				},
				
				addValue : function(value) {
					if (this.value_s.indexOf(value) >= 0)
						return;
					this.value_s.push(value);
				},

				removeValue : function(value) {
					var index = this.value_s.indexOf(value);
					if (index < 0)
						return;
					this.value_s.splice(index, 1);	
				},

				bindEvent : function() {
					this._clickHandle =this.connect(this.domNode, 'click', function(evt){
						this.defer(function(){
							this._onClick(evt);
						},320);
					});
					this.subscribe(this.CHECK_CHANGE, 'closeDialog');
				},
				
				_readOnlyAction:function(value) {
					this.inherited(arguments);
					if(value){
						if(this._clickHandle){
							this.disconnect(this._clickHandle);
						}
						this._clickHandle = null;
					}else{
						this._clickHandle = this.connect(this.domNode, 'click', function(evt){
							this.defer(function(){
								this._onClick(evt);
							},320);
						});
					}
				},
				
				resizeTop:function(evt) {
					var wdgts =evt.htmlWdgts,y=0;
					var value_s= this.get("value").split(";");
					for(var i=0;i<wdgts.length;i++){
						if(value_s[0]==wdgts[i].value&&wdgts[i].checked){
							if(wdgts[i].checkboxNode.offsetTop<evt.containerNode.clientHeight){
								break;
							}else if(this.contentNode.clientHeight-wdgts[i].checkboxNode.offsetTop<evt.containerNode.clientHeight){
							   y=this.contentNode.clientHeight-evt.containerNode.clientHeight;
							}
							else{
							   y =wdgts[i].checkboxNode.offsetTop-(evt.containerNode.clientHeight-wdgts[i].checkboxNode.clientHeight)/2;  
							}
							evt.contentNode.scrollTop = y;
						}
					}
				},

				_onClick : function(evt) {
					if (this.dialog)
						return;
					this.contentNode = domConstruct.create('div', {
						className : 'muiCheckBoxPopWarp'
					});
					var listNode = domConstruct.create('ul', {
						className : 'muiRadioGroupPopList'
					},this.contentNode);
					this.renderListItem(listNode);
					var buttons = [];
					if (this.mul) {
						buttons = [ {
							title : msg['mui.button.cancel'], // 取消
							fn : lang.hitch(this, this._closeDialog)
						},
						{
							title : msg['mui.button.ok'], // 确定
							fn :  lang.hitch(this, this._DoneClick)
						},
						{
							title : msg['mui.button.clear'], // 清空
							fn :  lang.hitch(this, this._clearClick)
						}];
					}
					
					this.dialog = Dialog.element({
						canClose : false,
						element : this.contentNode,
						buttons : buttons,
						position:'bottom',
						'scrollable' : false,
						'parseable' : true,
						showClass : 'muiFormSelect',
						callback : lang.hitch(this, function() {
						    topic.publish(this.SELECT_CALLBACK, this);
							this.dialog = null;
						}),
						onDrawed:lang.hitch(this, function(evt) {
							this.resizeTop(evt);
							if(this.mul){
								this.value_s=[];
								var values = this.value.split(";");
								for(var i=0;i<values.length;i++){
									if(values[i]!='')
									  this.value_s.push(values[i]);
								}
							}
						})
					});
				}
			});
			return _field;
		});