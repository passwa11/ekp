define([ "dojo/_base/declare", 
         "dojo/query", 
         "dojo/dom-class",
         "mui/form/_OptionsBase", 
         "dojo/dom-construct", 
         "dojo/topic",
		 "dojo/_base/lang", 
		 "dojo/dom-style", 
		 "mui/util", 
		 "mui/form/checkbox/render/CheckBoxNormal", 
		 "mui/form/checkbox/render/CheckBoxBlock",
		 "mui/form/checkbox/render/CheckBoxTable"
	   ], function(declare, query, domClass, _OptionsBase, domConstruct, topic, lang, domStyle, util, checkBoxNormal, checkBoxBlock, checkBoxTable) {
	
	var _field = declare("mui.form.CheckBox", [ _OptionsBase ], {

		valueField : null,

		// 与标准html属性重名会出问题
		// name : null,

		opt : false,

		type : 'checkbox',

		checked : false,

		mul : true,
		
		tag :'div',
		
		// 是否弹框
		pop:false,
		
		// 多选框可选呈现样式数组
		renderOptions: [ checkBoxNormal, checkBoxBlock, checkBoxTable ],
		
		// 呈现样式类型( normal:标准 、block:块状 、table:表格)
		renderType: "normal",

		CHECK_CHANGE : 'mui/form/checkbox/change',
		
		CHECK_SET: 'mui/form/checkbox/set',

		ITEMVALUE_CHANGE : 'mui/form/checkbox/valueChange',
		
		// 渲染选中状态事件名
		RENDER_CHECKED_STATUS: 'mui/form/checkbox/renderCheckedStatus',
		
		postCreate: function(){
			this.inherited(arguments);
			this.subscribe(this.CHECK_SET , "_setCheckedAttr");
		},

		_buildValue : function() {
		
			// 兼容历史展现在弹窗的列表CheckBox
			if(this.pop){
				this.renderType = "table";
			}
			// 根据呈现样式类型构建选项DOM
			for(var index in this.renderOptions){
				var render = this.renderOptions[index];
				this.checkboxNode = new render()._buildRender(this);
				if(this.checkboxNode){break;}
			}
			this.optionContainerNode = this.checkboxNode;
		
			domConstruct.place(this.domNode, this.checkboxNode, 'last');
			this.inherited(arguments);

		},

		_checkedChange : function(obj, evt) {
			if (!evt)
				return;
			if (evt.name == this.name && obj != this)
				this.set('checked', false);
		},

		_extendCheckAction:function(checked){
			this.inherited(arguments);
			// 发布事件渲染选中状态
			topic.publish(this.RENDER_CHECKED_STATUS, this, {
				"domNode": this.checkboxNode,
				"checked": this.checked,
				"checkedIcon": this.checkedIcon,
				"unCheckedIcon": this.unCheckedIcon
			});
		},
		
		_onClick : function(evt) {
			/* 连续点击不能超过500毫秒，防止快速双击
			（苹果IOS下，click事件未知原因会连续触发两次，下面的的 nowTime、clickTime的条件判断只是为了防止一次点击多次处理，是一种变通的方式来防止双击）*/
			var nowTime = new Date().getTime();
		    var clickTime = this.ctime;
		    if( clickTime != 'undefined' && (nowTime - clickTime < 500)){
		        return false;
		     } else {
		    	    
		    	 	this.set('checked', this.checked ? false : true);
		    	 
		    	 	topic.publish(this.ITEMVALUE_CHANGE, this, {
						"name" : this.name
					});
		    	 	
					topic.publish(this.CHECK_CHANGE, this, {
						"name" : this.name,
						"value" : this.value
					});
					
					// 重新渲染复选框选中状态
					topic.publish(this.RENDER_CHECKED_STATUS, this, {
						"domNode": this.checkboxNode,
						"checked": this.checked,
						"checkedIcon": this.checkedIcon,
						"unCheckedIcon": this.unCheckedIcon
					});
		    	
		    	 this.ctime = nowTime;
		    	 return true;
		     }
		},
		
		_readOnlyAction:function(value) {
			this.optionNode=this.checkboxNode;
			this.inherited(arguments);
		},

		buildEdit : function() {
			self._curTime=0;
			this._optionHandle =this.connect(this.checkboxNode, 'click', '_onClick');
			if (!this.mul) {
				this.subscribe(this.ITEMVALUE_CHANGE, '_checkedChange');
			}
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