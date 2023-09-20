define(
		[ "dojo/_base/declare", "dojo/query", "dojo/dom-class",
				"dojo/dom-construct", "mui/form/_StoreFormMixin",
				"dojo/_base/array", "dojo/_base/lang" ,"mui/util"],
		function(declare, query, domClass, domConstruct, _StoreFormMixin,
				array, lang, util) {
			var claz = declare(
					"mui.form.select._StoreSelectMixin",
					_StoreFormMixin,
					{

						selectBoxPrefix : '_select_box_',

						valueField : null,

						values : [],

						itemRenderer : '<input type="checkbox" data-dojo-type="mui/form/CheckBox" name="_select_box_!{valueField}" value="!{value}" data-dojo-props="tag:\'li\',mul:!{mul},text:\'!{text}\',checked:!{checked},pop:!{pop}">',

						mul : true,

						required : false,
						
						pop:true,

						generateList : function(items) {
							this.formatValues(items);
							this.set('value', this.value);
						},

						// 格式化数据
						formatValues : function(values) {
							this.values = values;
						},

						renderListItem : function(contentNode) {
							
							var values = this.value.split(';');
							array.forEach(this.values, lang.hitch(this,
									function(value, index) {
										value.selected = false;
										array.forEach(values, function(v) {
											if (v == value.value) {
												value.selected = true;
												return;
											}
										});
										var item = this.createListItem(value);
										contentNode.appendChild(item);
									}));
						},

						createListItem : function(props) {
							var itemRenderer = this.itemRenderer;
							var propsText = (typeof(props.text)!="undefined" && props.text!=null) ? util.formatTextChar(util.formatText(props.text.toString().replace(/'/g,"&#039;"))) : "";
							var propsValue = (typeof(props.value)!="undefined" && props.value!=null) ? util.formatTextChar(util.formatText(props.value.toString())) : "";
							itemRenderer = itemRenderer.replace('!{text}',propsText);
							itemRenderer = itemRenderer.replace('!{checked}', props.selected);
							itemRenderer = itemRenderer.replace('!{value}',propsValue);
							itemRenderer = itemRenderer.replace('!{mul}',this.mul);
							itemRenderer = itemRenderer.replace('!{valueField}',this.valueField);
							itemRenderer = itemRenderer.replace('!{pop}',this.pop);
							var item = domConstruct.toDom(itemRenderer);
							return item;
						},

						getSelectedValue : function() {
							var selecteds = query('[name="'
									+ this.selectBoxPrefix + this.valueField
									+ '"]:checked', this.contentNode);
							var ids = '';
							array.forEach(selecteds, function(item, index) {
								ids += index == 0 ? item.value : ';'
										+ item.value;
							});
							return ids;
						},

						getTextByValue : function(value) {
							var text = '';
							if (value == undefined)
								return text;
							var values = value.split(';');
							for (var k = 0; k < values.length; k++) {
								for (var j = 0; j < this.values.length; j++) {
									if (values[k] == this.values[j].value) {
										text += (k == 0 ? this.values[j].text
												: ';' + this.values[j].text);
										break;
									}
								}
							}
							return text;
						},
					});

			return claz;
		});