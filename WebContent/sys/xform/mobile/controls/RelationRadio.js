define([ "dojo/_base/declare", "dojo/_base/array", "dojo/ready", "dojo/dom-construct", "dojo/dom-prop", "dojo/dom-style", 
         "sys/xform/mobile/controls/xformUtil", "dijit/registry", "dojo/request", "mui/util", "mui/dialog/Tip", 
         "dojo/query", "dojo/topic","mui/form/RadioGroup", "sys/xform/mobile/controls/RelationCommonBase"], function(declare, array, ready, domConstruct, domProp, domStyle, xUtil, 
        		 registry, request, util, Tip, query, topic, radioGroup, relationCommonBase) {

	var claz = declare("sys.xform.mobile.controls.RelationRadio", [radioGroup, relationCommonBase], {
		
		
		defvalue:null,
		
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
			this.inherited(arguments);
			this._buildValue();
		},
		
		buildEdit : function(){
			this.hiddenTextNode = domConstruct.create('input', {
				type:"hidden",
				name:this.textName
			}, this.domNode);
		},
		
		
		
		buildReadOnly: function() {
			this.inherited(arguments);
			this.hiddenTextNode = domConstruct.create('input', {
				type:"hidden",
				name:this.textName
			}, this.domNode);
		},
		
		buildHidden: function() {
			this.inherited(arguments);
			this.hiddenTextNode = domConstruct.create('input', {
				type:"hidden",
				name:this.textName
			}, this.domNode);
		},
		
		buildView: function() {
			if(this.valueNode){
				domConstruct.destroy(this.valueNode);
			}			
			this.valueNode = domConstruct.create("div", {
				'className' : 'muiRadioWrap_View '
			}, this.domNode);
		},
		
		postCreate:function(){
			this.inherited(arguments);
			if (this.edit) {
				this.subscribe("/mui/form/valueChanged","_execChange");
			}
		},
		
		startup: function(){
			this.inherited(arguments);
			if (this.edit) {
				this.queryData(true);
			}
			if(this.showStatus == "view"){
                // 设置默认值
                if(this.value && this.value != ''){
                    if(!(this.text && this.text != '')){
                        this.queryData(true);
                    }
                }
            }
		},
		
		addItems:function(values,texts,isInit){
			var backList = [];
			if (values!=null && texts!=null) {
				var val = '';
				isInit = isInit ? isInit : false;
				var notChecked = true;
				for ( var i = 0; i < values.length; i++) {
					//设置默认值 start
					if(isInit && notChecked && this.value == values[i]){
						val = values[i];
						backList.push({'text':texts[i],'value':values[i],'checked':true});
						notChecked = false;
					}else{
						backList.push({'text':texts[i],'value':values[i],'checked':false});	
					}
					//end
				}
				this.set('values', backList);
				// 只要是重新加载数据，都重新设置值
				this.set('value', val);
				//end
			}	
			domConstruct.empty(this.valueNode);
			this.generateList(this.values);
			this._working = false;
			topic.publish("/mui/list/resize", this);
		},
		
		_execChange:function(srcObj, arguContext){
			this.listenInputWgt(srcObj, arguContext,this.bindDom);
		},
		
		_setValueAttr : function(value) {
			var text = "";
			var valid = false;
			for (var j = 0; j < this.values.length; j++) {
				if (value == this.values[j].value) {
					text = this.values[j].text;
					valid = true;
					break;
				}
			}
			if(!valid){
				value = "";
			}
			this.text = text;
			this.inherited(arguments);
			this.showStatusSet(value);				
		},

		viewValueSet : function(value) {
			if(value!=null && value!=''){
				this.set('text', this.getTextByValue(value));
				this.valueNode.innerHTML = this.text;
			}
		},
		
		editValueSet : function(value) {
			this.inherited(arguments);
			this.hiddenNode.value = value;
			this.hiddenTextNode.value = this.text;
		},
		
		getTextByValue : function(value) {
			var text = '';
			if (value == undefined)
				return text;
			for (var j = 0; j < this.values.length; j++) {
				if (value == this.values[j].value) {
					text += this.values[j].text;
					break;
				}
			}
			return text;
		}

	});
	return claz;
});
