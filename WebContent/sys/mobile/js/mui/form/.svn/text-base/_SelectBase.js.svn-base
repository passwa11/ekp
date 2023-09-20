define([ "dojo/_base/declare", "dojo/dom-construct", "mui/form/_FormBase",
		"dojo/dom-class", "mui/util","mui/form/_PlaceholderMixin" ,"mui/i18n/i18n!sys-mobile" ,"dojo/dom-attr" ], function(declare,
		domConstruct, _FormBase, domClass, util, _PlaceholderMixin,Msg, domAttr) {
	var _field = declare("mui.form._SelectBase", [ _FormBase , _PlaceholderMixin], {

		baseClass : "muiFormEleWrap popup",	
		
		optClass : 'muis-to-right',
		
		tipMsg :Msg['mui.form.please.select'],

		// 构建值区域
		_buildValue : function() {
			this.inherited(arguments);
			var setBuildName = 'build' + util.capitalize(this.showStatus);
			this[setBuildName] ? this[setBuildName]() : '';
			var setMethdName = this.showStatus + 'ValueSet';
			this.showStatusSet = this[setMethdName] ? this[setMethdName]
					: new Function();
		},

		buildRendering : function() {
			this.inherited(arguments);
			this.inputContent =domConstruct.create('div', {
				'className' : 'muiSelInput'
			},this.domNode);
			// 控件统一把输入的node设置为contentNode
			if(this.buildXFormStyle()){
				$(this.inputContent).attr("style",this.buildXFormStyle());
			}
			var xformStyle = this.buildXFormStyle();
			if(xformStyle){
				domAttr.set(this.inputContent,"style",xformStyle);
			}
			this.contentNode = this.inputContent;
			this._buildValue();
		},
		
		_readOnlyAction:function(value) {
			this.inherited(arguments);
		},

		_setValueAttr : function(value) {
			this.showStatusSet(value);
			this.inherited(arguments);
			if(this.domNode){
				if(!value && this.edit && this.showPleaseSelect){
					domClass.remove(this.domNode,"showTitle");
				}else{
					domClass.add(this.domNode,"showTitle");
				}
			}
		}
	});
	return _field;
});