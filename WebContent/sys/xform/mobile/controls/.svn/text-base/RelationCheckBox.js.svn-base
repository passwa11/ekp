define([ "dojo/_base/declare", "dojo/_base/array", "dojo/ready", "dojo/dom-construct", "dojo/dom-prop", "dojo/dom-style", 
         "sys/xform/mobile/controls/xformUtil", "dijit/registry", "dojo/request", "mui/util", "mui/dialog/Tip", 
         "dojo/query", "dojo/topic","mui/form/CheckBoxGroup", "sys/xform/mobile/controls/RelationCommonBase"], function(declare, array, ready, domConstruct, domProp, domStyle, xUtil, 
        		 registry, request, util, Tip, query, topic, checkBoxGroup, relationCommonBase) {

	var claz = declare("sys.xform.mobile.controls.RelationCheckBox", [checkBoxGroup, relationCommonBase], {
		
		
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
		
		postMixInProperties: function(){
			this.inherited(arguments);
			if(this.alignment == 'V'){
				this.__orient = 'vertical';
			}
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			this.values = [];
			if(this.value){
				var valueArray = this.value.split(";");
				var textArray = this.text.split(";");
				for(var i = 0;i < valueArray.length;i++){
					this.values.push({'value':valueArray[i],'text':textArray[i],'checked':true});
				}	
			}
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
				'className' : 'muiCheckboxWrap_View '
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
                        this.showStatusSet(this.value);
                    }
                }
            }
		},
		
		setCheckBox:function(value){
			if (value == undefined)
				return '';
			var values = value.split(';');
			for (var k = 0; k < values.length; k++) {
				for (var j = 0; j < this.values.length; j++) {
					if (values[k] == this.values[j].value) {
						this.values[j].checked = true;
						break;
					}
				}
			}
		},
		
		addItems:function(values,texts,isInit){
			var backList = [];
			if (values!=null && texts!=null) {
				var currentValues = '';
				isInit = isInit ? isInit : false;
				if(isInit == true){
					currentValues = this.value.split(";");
				}
				var val = "";
				for ( var i = 0; i < values.length; i++) {
					//设置默认值 start
					if(isInit == true && currentValues && currentValues.indexOf(values[i]) > -1 ){
						val += values[i] + ";";
						backList.push({'text':texts[i],'value':values[i],'checked':true});	
					}else{
						backList.push({'text':texts[i],'value':values[i],'checked':false});	
					}
					//end
				}
				this.set('values', backList);
				// 只要是重新加载数据，都重新设置值
				if(val.length > 0){
					// 去掉最后一个分号
					val = val.substring(0,val.length - 1);
				}
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
			this.text = this.getTextByValue(value);
			if(this.hiddenTextNode){
				this.hiddenTextNode.value = this.text;
			}
			this.inherited(arguments);
			this.showStatusSet(value);
		},
		
		viewValueSet : function(value) {
			if(value!=null && value!=''){
				this.set('text', this.getTextByValue(value));
				this.valueNode.innerHTML = this.text;
			}
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
		}

	});
	return claz;
});
