define(
		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"mui/util", "dojo/topic", "sys/mportal/mobile/OpenProxyMixin","mui/i18n/i18n!sys-mobile:mui.item.watch" ],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				util, topic, OpenProxyMixin,msg) {
			var item = declare(
					"sys.mportal.ComplexChosenTextItemMixin",
					[ ItemBase, OpenProxyMixin ],
					{

						baseClass : "muiPortalComplexLImgTextItem",

						label : "",

						href : "",

						image : "",

						count : "",

						created : "",

						creator : "",
						
						score :"",
						
						category :"",

						buildRendering : function() {

							this.inherited(arguments);

							var a = domConstruct.create('a', { href : 'javascript:;', className:'muiPortalComplexLImgTextItemLink'  }, this.domNode);

							this.proxyClick(a, this.href, '_blank');

							// 图标
							var icon = domConstruct.create('span', null, a);

							if (this.image)
								this.icon = this.image;
							
							if (!this.icon){
								this.icon = '/resource/style/default/attachment/default.png';
							}
							
							domStyle.set(icon, {
								'background-image' : 'url(' + util.formatUrl(this.icon) + ')'
							})

							var right = domConstruct.create('div', {}, a);

							domConstruct.create('h2', { className : 'muiPortalComplexLItemTitle muiFontSizeM muiFontColorInfo', innerHTML : [this.score] +this.label }, right);

							var footer = domConstruct.create('div', { className : 'muiPortalComplexLItemFooter muiFontSizeS muiFontColorMuted' }, right);

							if (this.category) {
								this.createdNode = domConstruct.create( "div", { className : "muiPortalComplexLItemCreator muiAuthor", innerHTML : this.category }, footer);
							}
							
							if (this.created) {
								this.createdNode = domConstruct.create( "div", { className : "muiPortalComplexLItemCreated", innerHTML : this.created }, footer);
							}

							if (this.count) {
								this.countNode = domConstruct.create("div",{ className:"muiPortalComplexLItemRead", innerHTML:"<span class='muiPortalComplexLItemReadNum'>"+this.count+"</span><span class='muiPortalComplexLItemReadViewText'>"+msg['mui.item.watch']+"</span>" }, footer);
							}
							
						},

						_setLabelAttr : function(label) {
							if (label)
								this._set("label", label);
						}

					});
			return item;
		});