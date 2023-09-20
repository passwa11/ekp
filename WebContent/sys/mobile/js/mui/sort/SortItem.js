/**
 * 排序组件
 */
define([
	'dojo/_base/declare',
	'dojo/topic',
	'dijit/_WidgetBase',
	'dojo/dom-construct',
	'dojo/dom-class',
	'mui/ChannelMixin',
	'./HashMixin'
], function(declare, topic, _WidgetBase, domConstruct, domClass, ChannelMixin, HashMixin){
	
	return declare('mui.sort.SortItem', [ _WidgetBase, ChannelMixin, HashMixin ], {
		
		// 排序字段
		name: '',
		
		// 排序字段名
		subject: '',
		
		// 默认值 : up(降序)/down(升序),空字符串代表不排序
		value: '',
		
		buildRendering: function(){
			this.inherited(arguments);
			this.textNode = domConstruct.create('span',{
				innerHTML: this.subject,
				className: 'muiSortText muiFontSizeS muiFontColorMuted'
			},this.domNode);
			this.iconNode = domConstruct.create('span',{
				className: 'muiSortIcon'
			},this.domNode);
			this._handleClassName();
		},
		
		postCreate: function(){
			// 当前排序组件发生变化处理
			this.connect(this.domNode, 'click', 'submit');
			// 其它排序组件发生变化处理(目前只支持单排序，所以一旦监听到别的排序变化，本组件将调整为不排序)
			this.subscribe('/mui/property/filter', 'otherSubmit');
		},
		
		submit: function(){
			switch(this.value){
				case '':
					this.value = 'down';
					break;
				case 'down':
					this.value = 'up';
					break;
				case 'up':
					this.value = '';
					break;
			}
			this._handleClassName();
			topic.publish('/mui/property/filter', this, {
				orderby : {
					value: this.name,
					prefix: ''
				},
				ordertype: {
					value: this.value,
					prefix: ''
				}
			});
			this.inherited(arguments);
		},
		
		otherSubmit: function(obj, evt){
			if(!this.isSameChannel(obj.key)){
				return;
			}
			if(!evt){
				return;
			}
			if(evt.orderby 
					&& evt.orderby.value !== this.name){
				this.value = '';
				this._handleClassName();
			}
		},
		
		_handleClassName: function(){
			domClass.add(this.domNode, 'muiSort');
			domClass.toggle(this.domNode, 'down', this.value === 'down');
			domClass.toggle(this.domNode, 'up', this.value === 'up');
		}
		
	});
});
