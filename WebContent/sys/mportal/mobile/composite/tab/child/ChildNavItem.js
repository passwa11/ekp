define(
		[ "dojo/_base/declare", "dojo/_base/lang","dojox/mobile/_ItemBase", "dojo/dom-construct", "dojo/query",
				"dojo/dom-style", "dojo/dom-class", "dojo/topic", "mui/util","mui/device/adapter"],
		function(declare,lang, ItemBase, domConstruct, query, domStyle, domClass, topic,
				util,adapter) {
			var NavItem = declare(
					'mui.portal.NavItem',
					ItemBase,
					{
						forceRefresh : false,
						
						// 选中class
						_selClass : 'active',

						className : 'et-portal-tab muiFontSizeM',

						tag : 'li',
						
						value : '',

						text : '',
						
						pageUrl: "",
						
						pageUrlOpenType: "",						

						buildRendering : function() {

							this.inherited(arguments);
							if (this.text) {
								this.domNode = this.containerNode 
											 = this.srcNodeRef || domConstruct.create('a', {
											      className : this.className,
												  innerHTML : this.text
											   });

								this.connect(this.domNode, "onclick", '_onClick');
							}
							
							this.subscribe('/sys/mportal/composite/child/navBarMore/changeData', '_onClick');

						},

						// 选中
						setSelected : function() {
							domClass.remove(this.domNode, 'muiFontSizeM muiFontColorInfo'); // 移除常规中号字体以及默认颜色
							domClass.add(this.domNode, 'muiFontSizeXL muiFontColor');       // 选中加大号字体以及高亮颜色
							domClass.add(this.domNode, this._selClass);
						},
						
						setUnSelected : function(){
							domClass.remove(this.domNode, 'muiFontSizeXL muiFontColor'); // 移除加大号字体以及高亮颜色
							domClass.add(this.domNode, 'muiFontSizeM muiFontColorInfo'); // 选中常规中号字体以及默认颜色
							domClass.remove(this.domNode, this._selClass);
						},

						_onClick : function(e) {
							
							var value = this.value;
							
							var tabId = this.getParent().tabId;
							
							if(e.tabId && e.tabId != tabId){
								return;
							}
							
							if(e.value){
								if(e.value != value)
									return;
								value = e.value;
							}						
							if (value == this.getParent()['currentPageId'])
								return;
							var shouldUpdate = true;
							
							if(this.type == 1 && !this.forceRefresh){																
								var evt = lang.mixin({
									"tabId": tabId,
									"pageId" : this.value
								},this);
								topic.publish('/sys/mportal/composite/child/navItem/changed',evt,this);
							}else{
								if(this.type == 1){
									var evt = lang.mixin({
										"tabId": tabId,
										"pageId" : this.value,
										"forceRefresh" : this.forceRefresh
									},this);
									topic.publish('/sys/mportal/composite/child/navItem/changed',evt,this);
									return;
								}
								var url = util.formatUrl(this.pageUrl,true);
								if(this.pageUrlOpenType == 2){
									shouldUpdate = false;
									window.open(url, '_self');
								}else{
									var evt = lang.mixin({
										"pageId" : this.value,
										"url" : url,
										"useIframe": true,
										"tabId": tabId,
									},this);
									topic.publish('/sys/mportal/composite/child/navItem/changed',evt,this);	
								}
							}
							if(shouldUpdate){
								var href = location.href;
								href = util.setUrlParameter(href, "fdChildPageId", this.value);
								history.replaceState(null, null, href);
								if(typeof(e.value) != 'string')
									topic.publish('/sys/mportal/composite/child/navBarMore/clickItem', this.tabIndex, tabId);
								this.getParent()['currentPageId'] = this.value;
							}
							
						}

					});

			return NavItem;
		});