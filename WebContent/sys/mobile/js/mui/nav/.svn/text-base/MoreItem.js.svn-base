define(
		'mui/nav/MoreItem',
		[ "dojo/_base/declare","dojox/mobile/TransitionEvent", "dojox/mobile/_ItemBase", "dojox/mobile/Badge",
				"dojo/dom-construct", "dojo/dom-style", "dojo/dom-class",
				"dojo/request", "dojo/topic", "mui/util" ],
		function(declare, TransitionEvent,ItemBase, Badge, domConstruct, domStyle, domClass,
				request, topic,util) {
			var MoreItem = declare(
					'mui.nav.MoreItem',
					ItemBase,
					{

						// 选中class
						_selClass : 'muiNavitemSelected',
						// 选中事件
						topicType : '/mui/navitem/_selected',

						tag : 'li',

						badge : 0,

						buildRendering : function() {
							this.domNode = this.containerNode = this.srcNodeRef
									|| domConstruct.create('li', {
										className : 'muiNavitem'
									});

							this.inherited(arguments);
							if (this.text) {													
								this.textNode = domConstruct.create('span', {
									className :!this.overflowHide ? 'muiNavitemSpan' : 'textEllipsis muiNavitemSpan'
								}, this.domNode);
								this.textNode.innerHTML = this.text;
								this.statNavCount();
							} 

							this
									.subscribe(this.topicType,
											'handleItemSelected');
							this.subscribe("/mui/list/onPull",'updateNavCount');
						},

						handleItemSelected : function(srcObj) {
							if(this.getParent() == srcObj.getParent() ){
								domClass.remove(this.domNode, this._selClass);
							}
						},
						updateNavCount:function(srcObj){
							if(this.url && srcObj.rel==this){
								this.statNavCount();
							}
						},
						statNavCount:function(){
							if (this.badge == 0 && this.url != '' && this.url != null) {
								var _self = this;
								if(this.isNavCount==true){
									var countUrl = util.formatUrl(_self.url + "&rowsize=1");
									request.post(countUrl, {
										handleAs : 'json'
									}).then(function(data) {
										if (data.page&&data.page.totalSize > 99) {
											_self.set('badge', "99+");
										}else if (data.page&&data.page.totalSize > 0) {
											_self.set('badge', data.page.totalSize);
										}else{
											if (_self.badgeObj && _self.domNode === _self.badgeObj.domNode.parentNode) {
												_self.domNode
														.removeChild(_self.badgeObj.domNode);
											}
										}
									}, function(data) {
	
									});
								}
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
								if (domClass.contains(target, 'muiNavitem'))
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
								url : this.url,
								text : this.text
							});
							domClass.add(this.domNode, this._selClass);
						},

						_onClick : function(e) {
							if(e){
								var target = e.target;
								this.beingSelected(target);
							}
							// 默认click事件
							this.defaultClickAction(e);
						},

						userClickAction : function() {
							if (this.moveTo) {
								return true;
							}
							return false; // 修复出现view跳转问题
						},
						
						
						makeTransition: function(/*Event*/e){
							if(this.back && history){
								history.back();	
								return;
							}	
							if (this.href && this.hrefTarget && this.hrefTarget != "_self") {
								win.global.open(this.href, this.hrefTarget || "_blank");
								this._onNewWindowOpened(e);
								return;
							}
							var opts = this.getTransOpts();
							var doTransition = 
								!!(opts.moveTo || opts.href || opts.url || opts.target || opts.scene);
							
							if(this.getParent() && this.getParent().key){
								opts.moveTo = opts.moveTo+"_" +this.getParent().key;
							}
							
							if(this._prepareForTransition(e, doTransition ? opts : null) === false){ return; }
							if(doTransition){
								this.setTransitionPos(e);
								new TransitionEvent(this.domNode, opts, e).dispatch();
							}
						},

						_setBadgeAttr : function(value) {
							if (!this.badgeObj) {
								this.badgeObj = new Badge();
								domStyle.set(this.badgeObj.domNode, {
									position : 'absolute',
									left : '100%',
									top : '0.2rem'
								});
							}
							this.badgeObj.setValue(value);
							if (value) {
								this.domNode.appendChild(this.badgeObj.domNode);
							} else {
								if (this.domNode === this.badgeObj.domNode.parentNode) {
									this.domNode
											.removeChild(this.badgeObj.domNode);
								}
							}
						}

					});

			return MoreItem;
		});