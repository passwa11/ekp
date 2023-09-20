define("km/imeeting/mobile/resource/js/list/PlaceDateSelect",[ "dojo/_base/declare", "dojo/query", "dojo/dom-class",
		"mui/form/_SelectBase", "dojo/dom-construct",
		"mui/form/select/_StoreSelectMixin", "mui/dialog/Dialog",
		'dojo/parser', "dojo/_base/array", "dojo/_base/lang", "dojo/topic" ,"mui/util", "mui/i18n/i18n!sys-mobile",
		'dojo/date/locale'],
		function(declare, query, domClass, _SelectBase, domConstruct,
				_StoreSelectMixin, Dialog, parser, array, lang, topic, util, msg, locale) {
			var _field = declare("km.imeeting.PlaceDateSelect", [ _SelectBase,
					_StoreSelectMixin ], {

				value : '',

				text : '',

				type : 'select',

				CHECK_CHANGE : 'mui/form/checkbox/change',

				SELECT_CALLBACK : '/km/imeeting/navitem/selected',

				postCreate: function() {
					this.inherited(arguments);
					
					if(!this.value) {
						this.value = locale.format(new Date(), {
							selector: 'date',
							datePattern: 'yyyy-MM-dd'
						});
					}
				},
				
				startup : function() {
					this.url = util.formatUrl(this.url);
					this.inherited(arguments);
					this.valueField = this.name;
				},
				
				// 格式化数据
				formatValues : function(values) {
					values = values || [];
					var res = [];
					for(var i = 0; i < values.length; i++) {
						res.push({
							text: values[i].weekday + ' (' + values[i].value + ')',
							value: values[i].value
						});
					}
					
					this.values = res;
				},
				// 渲染模板
				renderListItem : function() {
					this.inherited(arguments);
				},

				closeDialog : function(srcObj, evt) {
					if (evt.name == (this.selectBoxPrefix + this.valueField)) {
						this.set('value', evt.value);
						this._closeDialog();
					}
				},

				_closeDialog : function() {
					topic.publish(this.SELECT_CALLBACK, this, {
						value: this.value,
						text: this.text
					});
					if(this.dialog){
						this.dialog.hide();
						this.dialog = null;
					}
				},

				_buildValue : function() {
					this.inherited(arguments);
					domClass.add(this.inputContent, 'muiSelectInputContnet');
				},

				buildRendering : function() {
					this.inherited(arguments);
					this.textNode = domConstruct.create('div', {
						className : 'muiFormSelectText'
					}, this.inputContent);
				},

				_setTextAttr : function(text) {
					this.textNode.innerHTML = text;
					this.text = text;
				},

				buildEdit : function() {
					this.inherited(arguments);
					this.selectNode = domConstruct.create('input', {
						name : this.name,
						className : 'muiFormSelectInput',
						value : this.value
					}, this.inputContent);
					this.bindEvent();
				},

				buildReadOnly : function() {
					this.selectNode = domConstruct.create('input', {
						name : this.name,
						className : 'muiFormSelectInput',
						value : this.value,
						readOnly : 'readOnly'
					}, this.inputContent);
				},

				buildHidden : function() {
					this.selectNode = domConstruct.create('input', {
						name : this.name,
						className : 'muiFormSelectInput',
						value : this.value
					}, this.inputContent);
				},

				viewValueSet : function(value) {
					if(value!=null && value!=''){
						this.set('text', this.getTextByValue(value));
					}
				},

				editValueSet : function(value) {
					this.set('text', this.getTextByValue(value));
					this.selectNode.value = value;
				},

				hiddenValueSet : function(value) {
					this.editValueSet(value);
				},

				readOnlyValueSet : function(value) {
					this.editValueSet(value);
				},

				bindEvent : function() {
					this.connect(this.domNode, 'click', function(evt){
						this.defer(function(){
							this._onClick(evt);
						},320);
					});
					if (!this.mul) {
						this.subscribe(this.CHECK_CHANGE, 'closeDialog');
					}
				},

				_onClick : function(evt) {
					if (this.dialog){
						this.dialog.hide();
						this.dialog = null;
						return;
					}
					
					this.containerNode =domConstruct.create('div', {
						className : 'muiCheckBoxPopWarp'
					}); 
					this.contentNode = domConstruct.create('ul', {
						className : 'muiRadioGroupPopList'
					},this.containerNode);
					this.renderListItem(this.contentNode);
					var buttons = [];
					if (this.mul) {
						buttons = [ {
							title : msg['mui.button.ok'],
							fn : lang.hitch(this, this._closeDialog)
						} ];
					}
					this.dialog = Dialog.element({
						element : this.containerNode,
						buttons : buttons,
						position:'top',
						scrollable:false,
						parseable:true,
						showClass:'muiFormSelect placeCateSelectDialog',
						callback : lang.hitch(this, function() {
							topic.publish(this.SELECT_CALLBACK, this, {
								value: this.value,
								text: this.text
							});
							this.dialog = null;
						})
					});
				}
			});
			return _field;
		});