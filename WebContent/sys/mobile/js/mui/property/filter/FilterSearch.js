define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/_base/array", "dojo/topic",
		"dojo/dom-style", "dojo/dom-class", "./FilterBase"], function(declare,
		domConstruct, array, topic, domStyle, domClass, FilterBase) {
	return declare("mui.property.FilterRadio", [ FilterBase ], {

		buildRendering: function() {
			this.inherited(arguments);
		    this.subscribe("/mui/property/filter/input/change", "setInputValue")
		},
		
		renderInput: function(value) {
			this.inputNode = domConstruct.create('input', {
				className: 'filterItemInput',
				value: value[0] || ''
			});
			
			domConstruct.place(this.inputNode, this.contentNode);
			
			this.connect(this.inputNode, 'oninput', 'onChange');
		},
		
		startup: function() {
			var self = this;
			this.getValue(function(value) {
				self.renderInput(value || []);
			});
		},
		
		onChange: function(e) {
			topic.publish(this.SET_EVENT, this, {
				name: this.name,
				value: [e.target.value]
			});
		},
		
		setInputValue: function(isReset){
			if (isReset) {
				this.inputNode.value="";
			}else{
				return
			}
		}

	});
});