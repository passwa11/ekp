define(
		'km/imeeting/mobile/resource/js/list/item/NavItem',
		[ "dojo/_base/declare", "dojox/mobile/_ItemBase", 
				"dojo/dom-construct", "dojo/dom-style", "dojo/dom-class",
				"dojo/request", "dojo/topic", "mui/util" ],
		function(declare, ItemBase, domConstruct, domStyle, domClass,
				request, topic,util) {
			var NavItem = declare(
					'km.imeeting.NavItem',
					ItemBase,
					{

						// 选中class
						_selClass : 'muiNavitemSelected',
						// 选中事件
						topicType : '/mui/navitem/_selected',
						
						tag : 'li',

						buildRendering : function() {
							this.domNode = this.containerNode = this.srcNodeRef
									|| domConstruct.create('li', {
										className : 'dateNavitem'
									});
							this.textNode = domConstruct.create('div', {
								className : 'item'
							},this.domNode);
							//this.domNode.dojoClick= true;
							this.inherited(arguments);
							if (this.weekday) {													
								this.timeNode = domConstruct.create('span', {
									className :'time'
								}, this.textNode);
								this.timeNode.innerHTML = this.weekday;
								this.dateNode = domConstruct.create('span', {
									className :'date'
								}, this.textNode);
								this.dateNode.innerHTML = this.value;
							} 

							this
									.subscribe(this.topicType,
											'handleItemSelected');
						},

						handleItemSelected : function(srcObj) {
							if(this.getParent() == srcObj.getParent() ){
								domClass.remove(this.domNode, this._selClass);
							}
						},

						startup : function() {
							if (this._started)
								return;
							this.connect(this.textNode, "onclick", '_onClick');
							this.inherited(arguments);
						},

						// 选中
						setSelected : function() {
							this.beingSelected(this.textNode);
						},

						beingSelected : function(target) {
							while (target) {
								if (domClass.contains(target, 'dateNavitem'))
									break;
								target = target.parentNode;
							}
							var left, width;
							if (!target.offsetParent)
								left = 0, width = 0;
							var style = domStyle.getComputedStyle(target), marginLeft = domStyle
									.toPixelValue(target, style.marginLeft), marginRight = domStyle
									.toPixelValue(target, style.marginRight);

							topic.publish(this.topicType, this, {
								width : width == 0 ? 0 : target.offsetWidth
										+ marginRight + marginLeft,
								left : left == 0 ? left : target.offsetLeft
										+ target.offsetParent.offsetLeft
										- marginLeft,
								target : this,
								value : this.value, 
								text : this.weekday
							});
							topic.publish("/km/imeeting/navitem/selected", this, {
								value : this.value, 
								text : this.weekday
							});
							domClass.add(this.domNode, this._selClass);
						},

						_onClick : function(e) {
							var target = e.target;
							this.beingSelected(target);
						},
					});

			return NavItem;
		});