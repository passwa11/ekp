define([ "dojo/_base/declare", 
         "dojo/query", 
         "dojo/dom-class",
		 "mui/form/_OptionsBase", 
		 "dojo/dom-construct", 
		 "dojo/topic",
		 "dojo/_base/lang", 
		 "dojo/dom-style", 
		 "mui/util", 
		 "mui/form/_GroupBase",
		 "mui/form/radio/render/RadioNormal", 
		 "mui/form/radio/render/RadioBlock",
		 "mui/form/radio/render/RadioTable"
		], function(declare, query, domClass, _OptionsBase, domConstruct, topic, lang, domStyle, util, _GroupBase, radioNormal, radioBlock, radioTable) {
	var _field = declare("mui.form.Radio", [ _OptionsBase ], {

		valueField : null,

		// 与标准html属性重名会出问题
		// name : null,

		opt : false,

		edit : true,

		type : 'radio',

		checked : false,
		
		// 单选按钮可选呈现样式数组
		renderOptions: [ radioNormal, radioBlock, radioTable ],
		
		// 呈现样式类型( normal:标准 、block:块状 、table:表格)
		renderType: "normal",

		RADIO_CHANGE : 'mui/form/radio/change',
		
		// 渲染选中状态事件名
		RENDER_CHECKED_STATUS: 'mui/form/radio/renderCheckedStatus',		

		_buildValue : function() {

			// 根据呈现样式类型构建选项DOM
			for(var index in this.renderOptions){
				var render = this.renderOptions[index];
				this.radioNode = new render()._buildRender(this);
				if(this.radioNode){break;}
			}
			this.optionContainerNode = this.radioNode;
			
			domConstruct.place(this.domNode, this.radioNode, 'last');
			this.inherited(arguments);
			
		},
		
		_changeChecked:function(pWgt,value){
			if(this.getParent() == pWgt  && pWgt instanceof _GroupBase && value!=null && value!=''){
				var thisValue = this._get("value");
				this._extendCheckAction(thisValue == value);
			}
		},

		checkedChange : function(obj, evt) {
			if (!evt)
				return;
			if (evt.name == this.name && obj != this)
				this.set('checked', false);
		},

		_setCheckedAttr : function(checked) {
			this.inherited(arguments);
			if (this.checked) {
				topic.publish(this.RADIO_CHANGE, this, { name : this.name });
			}
			// 发布事件渲染选中状态
			topic.publish(this.RENDER_CHECKED_STATUS, this, {
				"domNode": this.radioNode,
				"checked": this.checked,
				"checkedIcon": this.checkedIcon,
				"unCheckedIcon": this.unCheckedIcon
			});			
		},
		
		_extendCheckAction:function(checked){
			this.inherited(arguments);
			// 发布事件渲染选中状态
			topic.publish(this.RENDER_CHECKED_STATUS, this, {
				"domNode": this.radioNode,
				"checked": this.checked,
				"checkedIcon": this.checkedIcon,
				"unCheckedIcon": this.unCheckedIcon
			});
		},
		
		_readOnlyAction:function(value) {
			this.optionNode=this.radioNode;
			this.inherited(arguments);
		},
		
		_onClick : function(evt) {
			if (!this.fireClick())
				return;
			this.set('checked', true);
		},

		buildEdit : function() {
			this._optionHandle=this.connect(this.radioNode, 'click', '_onClick');
			this.subscribe(this.RADIO_CHANGE, lang.hitch(this,this.checkedChange));
		},

		buildHidden : function() {
			domStyle.set(this.domNode, {
				display : 'none'
			});
		},

		buildReadOnly : function() {
			domStyle.set(this.domNode, {
				readOnly : 'readOnly'
			});
		},

		buildView : function() {
			this.buildReadOnly();
		},

		viewValueSet : function(value) {
			this.domNode.value = value;
		},

		editValueSet : function(value) {
			this.viewValueSet(value);
		},

		hiddenValueSet : function(value) {
			this.viewValueSet(value);
		},

		readOnlyValueSet : function(value) {
			this.viewValueSet(value);
		}
	});
	return _field;
});