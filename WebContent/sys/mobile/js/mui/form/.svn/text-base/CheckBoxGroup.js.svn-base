define(
		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/parser",
				"mui/form/_GroupBase" ,"mui/util","dojo/query","dojo/dom-class"],
		function(declare, domConstruct, parser, _GroupBase, util,query,domClass) {
			var _field = declare(
					"mui.form.CheckBoxGroup",
					[ _GroupBase ],
					{

						tmpl : '<input type="checkbox" data-dojo-type="mui/form/CheckBox" data-dojo-props="showStatus:\'!{showStatus}\',renderType:\'!{renderType}\',checked:!{checked},name:\'!{name}\',text:\'!{text}\',value:\'!{value}\',style:\'!{xformStyle}\'">',

						valueField : null,

						opt : false,

						name : null,

						value : '',

						text : '',

						values : [],
						// 是否只显示有值内容，view状态有效
						concentrate : false,
						// 默认 纵向排列
						alignment:'V',
						
						// 复选框的呈现样式类型( normal:标准 、block:块状 、table:表格)
						renderType: "normal",
						
						muiSingleRowViewClass : "muiSelInput",

						xformStyle: '',
						
						buildRendering : function() {
							this.inherited(arguments);
							if (!this.renderType) {
								this.renderType = "normal";
							}
							// 根据复选框呈现样式类型为组件DOM根节点添加类名标识（muiFormCheckBoxNormal、muiFormCheckBoxBlock、muiFormCheckBoxTable）
							domClass.add(this.domNode, 'muiFormCheckBox'+this.renderType.substring(0,1).toUpperCase()+this.renderType.substring(1));
							
							// 构建复选框选择项容器DOM
							var renderTypeClass = 'muiCheckBox'+this.renderType.substring(0,1).toUpperCase()+this.renderType.substring(1)+'Wrap';
							this.valueNode = domConstruct.create("div", {
								'className' : 'muiFormItem muiCheckBoxWrap '+renderTypeClass
							}, this.domNode);
							
							if (this.alignment == 'V'&& (this.orient=='vertical' || this.__orient == 'vertical')) {
								domClass.add(this.valueNode, 'blockCheck');
							}
						},

						_buildValue : function() {
							this.inherited(arguments);
						},

						isConcentrate : function(props) {
							return this.concentrate
									&& this.value_s.indexOf(props.value) < 0
									&& this.showStatus == 'view';
						},

						createListItem : function(props) {
							if (this.isConcentrate(props))
								return null;
							var tmpl = this.tmpl.replace('!{showStatus}',this.showStatus)
							        .replace('!{renderType}', this.renderType)
							        .replace('!{name}','_' + this.name.replace('.', '_') + '_single')
									.replace('!{value}',util.formatTextChar( util.formatText(props.value)))
									.replace('!{text}', util.formatTextChar(util.formatText(props.text.replace(/'/g,"&#039;"))))
									.replace('!{checked}',props.checked ? true : false)
									.replace('!{alignment}',this.alignment)
							var xformStyle = this.buildXFormStyle() || "";
							tmpl = tmpl.replace('!{xformStyle}',xformStyle);
							return domConstruct.toDom(tmpl);
						},
						
						completed :false,
						onComplete : function(items) {
							if(this.completed){
							  query('.muiFormOptionsField', this.valueNode).remove();
							}
							if(!this.completed)
								this.completed = true;
							this.inherited(arguments);
						},
						
						addChild : function(item) {
							this.inherited(arguments);
							domConstruct.place(item, this.valueNode, 'last');
						},

						generateList : function(items) {
							if (!this.value)
								this.value_s = [];
							else
								this.value_s = this.value.split(';');
							this.inherited(arguments);
							parser.parse(this.valueNode);
						},

						addValue : function(value) {
							if (this.value_s.indexOf(value) >= 0)
								return;
							this.value_s.push(value);
							this.set('value', this.value_s.join(';'));
						},
						
						_setValueAttr : function(value) {
							this.inherited(arguments);
							if(value!=null && value!='')
								this.value_s = value.split(";");
						},
						removeValue : function(value) {
							var index = this.value_s.indexOf(value);
							if (index < 0)
								return;
							this.value_s.splice(index, 1);
							this.set('value', this.value_s.join(';'));
						}
					});
			return _field;
		});