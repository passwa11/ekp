define(
		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"mui/util", "dojo/topic", "sys/mportal/mobile/OpenProxyMixin","mui/i18n/i18n!sys-mobile:mui.item.views" ],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				util, topic, OpenProxyMixin,msg) {
			var item = declare(
					"sys.mportal.ComplexLImgTextItemMixin",
					[ ItemBase, OpenProxyMixin ],
					{

						baseClass : "muiPortalComplexLImgTextItem",

						label : "",

						href : "",

						image : "",

						count : "",

						created : "",

						creator : "",

						buildRendering : function() {

							this.inherited(arguments);

							// 构建a标签容器
							var a = domConstruct.create('a', { href:'javascript:;', className:'muiPortalComplexLImgTextItemLink' }, this.domNode);
							// 绑定a标签点击跳转链接
							this.proxyClick(a, this.href, '_blank');

							// 左侧图标
							var icon = domConstruct.create('span', null, a);
							if (this.image)
								this.icon = this.image;
							if (!this.icon){
								this.icon = '/resource/style/default/attachment/default.png';
							}
							domStyle.set(icon, {
								'background-image' : 'url(' + util.formatUrl(this.icon) + ')'
							});

							// 右侧容器
							var right = domConstruct.create('div', {}, a);

							//状态
							if(this.status){
								domConstruct.create('div', {
									className : 'muiImgStatus muiTitleLStatus',
									innerHTML : this.status
								}, right);
							}
							// 右侧上方标题
							domConstruct.create('h2', {
								className : 'muiPortalComplexLItemTitle muiFontSizeM muiFontColorInfo',
								innerHTML :  this.label
							}, right);

							// 组件底部右侧基本信息容器
							var footer = domConstruct.create('div', { className:'muiPortalComplexLItemFooter muiFontSizeS muiFontColorMuted' }, right);

							// 显示创建人姓名
							if (this.creator) {
								this.createdNode = domConstruct.create("div", { className:"muiPortalComplexLItemCreator muiAuthor", innerHTML:this.creator }, footer);
							}
							
							// 显示创建时间
							if (this.created) {
								this.createdNode = domConstruct.create("div",{ className:"muiPortalComplexLItemCreated", innerHTML: this.created }, footer);
							}
							
							// 显示阅读数量( “N” 观看 )
							if (this.count) {
								this.countNode = domConstruct.create("div",{ className:"muiPortalComplexLItemRead", innerHTML:"<span class='muiPortalComplexLItemReadNum'>"+this.count+"</span><span class='muiPortalComplexLItemReadViewText'>"+msg['mui.item.views']+"</span>" }, footer);
							}

						},

						_setLabelAttr : function(label) {
							if (label)
								this._set("label", label);
						}

					});
			return item;
		});