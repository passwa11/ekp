define([ "dojo/_base/declare", "dojo/dom-construct", "mui/form/_FormBase",
		"dojo/dom-class", "dojo/_base/lang", "mui/util", "dojo/_base/window", "dojo/dom-style","mui/form/_PlaceholderMixin","dojo/dom-attr"  ], function(declare,
		domConstruct, _FormBase, domClass, lang, util, win, domStyle, _PlaceholderMixin, domAttr) {
	var _field = declare("mui.form._InputBase", [ _FormBase , _PlaceholderMixin ], {
		
		placeholder:'',
		
		opt : true,
		
		buildRendering : function() {
			this.inherited(arguments);
			this._buildValue();
		},

		_buildValue : function() {
			var setBuildName = 'build' + util.capitalize(this.showStatus);
			this[setBuildName] ? this[setBuildName]() : '';
			var setMethdName = this.showStatus + 'ValueSet';
			this.showStatusSet = this[setMethdName] ? this[setMethdName]
					: new Function();
		},

		_setValueAttr : function(value) {
			this.showStatusSet(value);
			this.inherited(arguments);
		},
		
		_onBlur:function(evt){
			var textNode = evt.target;
			evt.preventDefault();
			evt.stopPropagation();
			this.set("value", textNode.value);
			if(this.ClearIcon)
			   domStyle.set(this.ClearIcon,{'display':'none'});
		},
		
		_readOnlyAction:function(value){
			this.inherited(arguments);
			if(this.contentNode){
				if(value)
					domAttr.set(this.contentNode,"readonly","readonly");
				else
					domAttr.remove(this.contentNode,"readonly");
			}
		},
	
	});
	return _field;
});