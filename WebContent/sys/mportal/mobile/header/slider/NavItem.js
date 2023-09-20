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

						url : '/sys/mportal/sys_mportal_page/sysMportalPage.do?method=view&fdId=!{fdId}&fdName=!{fdName}',

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
							
							this.subscribe('/sys/mportal/navBarMore/changeData', '_onClick');

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
							
							// navBarMore传来的门户id
							var value = this.value;
							
							if(e.value){
								if(e.value != value)
									return;
								value = e.value;
							}
							
							if(typeof(e.value) != 'string')
								topic.publish('/sys/mportal/navBarMore/clickItem', this.tabIndex);
							
							if (value == this.getParent()['currentPageId'])
								return;
							if(this.type == 1 && !this.forceRefresh){
								
								topic.publish('/mui/mportal/header/logoChange', this.logo);
								
								var evt = lang.mixin({
									"pageId" : this.value
								},this);
								topic.publish('/sys/mportal/navItem/changed',evt,this);
								if(!e.back && history.pushState){
									var url = location.href;
									url = util.setUrlParameter(url,'fdId',this.value);
									url = util.setUrlParameter(url,'fdName',encodeURIComponent(this.text));
									history.pushState({id:this.value},'',url);
								}
								if(adapter.setTitle){
									adapter.setTitle(this.text);
								}else{
									document.title = this.text;
								}
							}else{
								window.open(util.formatUrl(util.urlResolver(
										this.url, {
											fdId : this.value,
											fdName : encodeURIComponent(this.text)
										})), '_self');
							}
							this.getParent()['currentPageId'] = this.value;
						}

					});

			return NavItem;
		});