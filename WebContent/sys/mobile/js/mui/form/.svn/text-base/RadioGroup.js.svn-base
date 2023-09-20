define( [ "dojo/_base/declare", "mui/form/_GroupBase", "dojo/dom-construct", "dojo/parser", "dojo/_base/array",
		"mui/util" ,"dojo/dom-class", "mui/dialog/Dialog", "dojo/_base/lang", "dojo/topic"],
		function(declare, _GroupBase, domConstruct, parser, array, util,domClass,Dialog,lang,topic) {
			var _field = declare("mui.form.RadioGroup", [ _GroupBase ], {

						tmpl : '<input type="radio" data-dojo-type="mui/form/Radio" data-dojo-props="checked:!{checked},showStatus:\'!{showStatus}\',renderType:\'!{renderType}\',name:\'!{name}\',text:\'!{text}\',value:\'!{value}\',style:\'!{xformStyle}\'">',

						valueField : null,

						opt : false,

						name : null,

						value : '',

						text : '',
						// 是否只显示有值内容，view状态有效
						concentrate : false,
						
						alignment:'V',
						
						// 单选按钮的呈现样式类型( normal:标准 、block:块状 、table:表格)
						renderType: "normal",

				        xformStyle: '',
						
						buildRendering : function() {
							this.inherited(arguments);
							if (!this.renderType) {
								this.renderType = "normal";
							}
							// 根据单选框呈现样式类型为组件DOM根节点添加类名标识（muiFormRadioNormal、muiFormRadioBlock、muiFormRadioTable）
							domClass.add(this.domNode, 'muiFormRadio'+this.renderType.substring(0,1).toUpperCase()+this.renderType.substring(1));
							
							// 构建单选框选择项容器DOM
							var renderTypeClass = 'muiRadioGroup'+this.renderType.substring(0,1).toUpperCase()+this.renderType.substring(1)+'Wrap';
							this.valueNode = domConstruct.create("div",{
								'className' : 'muiFormItem muiRadioGroupWrap '+renderTypeClass
							},this.domNode);

						    if(this.alignment=='V'){
						    	domClass.add(this.valueNode,' blockRadio');
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
							           .replace('!{name}', '_' + this.name + '_group')
									   .replace('!{value}', util.formatTextChar(util.formatText(props.value)))
									   .replace('!{text}',  util.formatTextChar(util.formatText(props.text.replace(/'/g,"&#039;"))))
									   .replace('!{checked}', props.checked ? true : false);
							var xformStyle = this.buildXFormStyle() || "";
							tmpl = tmpl.replace('!{xformStyle}',xformStyle);
							return domConstruct.toDom(tmpl);
						},

						addChild : function(item) {
							this.inherited(arguments);
							domConstruct.place(item, this.valueNode, 'last');
						},

						generateList : function(items) {
							this.value_s = this.value.split(';');
							this.inherited(arguments);
							return parser.parse(this.valueNode);
						},

						addValue : function(value) {
							this.set('value', value);
						}
					});
			return _field;
		});
