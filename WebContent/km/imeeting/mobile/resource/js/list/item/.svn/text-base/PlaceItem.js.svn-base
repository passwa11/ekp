define(
		'km/imeeting/mobile/resource/js/list/item/PlaceItem',
		[ "dojo/_base/declare", "dojox/mobile/_ItemBase", 
				"dojo/dom-construct", "dojo/dom-style", "dojo/dom-class",
				"dojo/request", "dojo/topic", "mui/util" ],
		function(declare, ItemBase, domConstruct, domStyle, domClass,
				request, topic,util) {
			var NavItem = declare(
					'km.imeeting.PlaceItem',
					ItemBase,
					{

						tag : 'li',

						buildRendering : function() {
							this.domNode = this.containerNode = this.srcNodeRef
									|| domConstruct.create('li', {
										className : ''
									});
							this.inherited(arguments);
							if (this.fdName) {												
								this.textNode = domConstruct.create('span', {
									className : 'textEllipsis'
								}, this.domNode);
								this.textNode.innerHTML = this.fdName;
							} 
						},
						startup : function() {
							if (this._started)
								return;
							this.inherited(arguments);
						}
					});

			return NavItem;
		});