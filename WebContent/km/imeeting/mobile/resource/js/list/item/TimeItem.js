define(
		'km/imeeting/mobile/resource/js/list/item/TimeItem',
		[ "dojo/_base/declare", "dojox/mobile/_ItemBase", 
				"dojo/dom-construct", "dojo/dom-style", "dojo/dom-class",
				"dojo/request", "dojo/topic", "mui/util" ],
		function(declare, ItemBase, domConstruct, domStyle, domClass,
				request, topic,util) {
			var NavItem = declare(
					'km.imeeting.TimeItem',
					ItemBase,
					{

						tag : 'li',

						buildRendering : function() {
							this.domNode = this.containerNode = this.srcNodeRef
									|| domConstruct.create('li', {
										className : ''
									});
							this.inherited(arguments);
							if (this.value) {												
								this.textNode = domConstruct.create('span', {
									className : ''
								}, this.domNode);
								this.textNode.innerHTML = this.PlaceItem.js;
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