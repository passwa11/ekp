define([ "dojo/_base/declare", "dojo/query", "dojo/dom-class",
		"dojo/dom-construct" , "mui/form/_FormBase"], function(declare, query,
		domClass, domConstruct,_FormBase) {
	var claz = declare("mui.form.Label", [ _FormBase ], {
 
		baseClass : "muiFormEleWrap showTitle",
		
		value : null,
		
		showStatus:'view',

		buildRendering : function() {
			this.inherited(arguments);
			if (this.value)
				this.labelNode = domConstruct.create('div', {
					className : 'muiSelLabel',
					innerHTML : this.value
				}, this.domNode);
		}
	});

	return claz;
})
