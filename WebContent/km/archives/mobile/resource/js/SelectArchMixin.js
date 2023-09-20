define(['dojo/_base/declare', 'dojo/_base/lang', 'dojo/_base/array', 
        'dojo/dom', 'dojo/dom-attr', 'dojo/dom-style', 'dojo/query', 
        'dijit/registry', 'mui/form/_ValidateMixin'], 
		function(declare, lang, array, dom, domAttr, domStyle, query, registry, _ValidateMixin){

	return declare('km.archives.mobile.js.SelectArchMixin', [_ValidateMixin], {
			
		validate: function(elements){
			
			var flag = true;
			
			if(query('.selectedArchRow').length < 1) {
				domStyle.set(dom.byId('selectArchTips'), 'display', 'block');
				flag = false;
			} else {
				domStyle.set(dom.byId('selectArchTips'), 'display', 'none');
			}
			
			return this.inherited(arguments) && flag;
			
		}
		
	});

});