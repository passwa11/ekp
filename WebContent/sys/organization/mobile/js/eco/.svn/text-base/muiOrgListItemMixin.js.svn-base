define([ "dojo/_base/declare", "dojox/mobile/_ItemBase",
         "dojo/dom-construct", "dojo/dom-construct", "./muiOrgEcoAddressMixin"], 
		function(declare, ItemBase, domConstruct, domConstruct, muiOrgEcoAddressMixin) {

	var item = declare("sys.org.eco.list.item.mixin", [ ItemBase, muiOrgEcoAddressMixin ], {

		buildRendering : function() {
			
			this.inherited(arguments);
			
			this.domNode = domConstruct.create('li');
			
			domConstruct.create('div', {
				className : 'muiOrgEcoPopulationListLabel',
				innerHTML : '<span><i class="fontmuis muis-user"></i></span>'
			}, this.domNode);
			
			var muiOrgEcoPopulationListInfo = domConstruct.create('div', {
				className : 'muiOrgEcoPopulationListInfo'
			}, this.domNode);
			
			domConstruct.create('p', {
				innerHTML : this.fdName,
			}, muiOrgEcoPopulationListInfo);
			
			domConstruct.create('span', {
				innerHTML : (this.fdPersonCount > 999? '999+' : this.fdPersonCount) + '<i>(人)</i>',
			}, muiOrgEcoPopulationListInfo);
			
			this.connect(this.domNode, 'click', 'onClick');
			
		},
		
		onClick : function() {
			this._selectCate();
		},

		_setLabelAttr : function() {
		}

	});

	return item;
});