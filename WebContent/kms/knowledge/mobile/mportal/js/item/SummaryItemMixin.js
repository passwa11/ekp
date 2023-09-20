define(
		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"mui/util", "sys/mportal/mobile/OpenProxyMixin" ],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				util, OpenProxyMixin) {

			var item = declare(
					"sys.mportal.SummaryItemMixin",
					[ ItemBase, OpenProxyMixin ],
					{

						tag : "li",

						label : "",

						href : "",

						icon : "",

						summary : "",

						forward : "",

						buildRendering : function() {

							this.domNode = this.containerNode = this.srcNodeRef
									|| domConstruct.create(this.tag);

							this.inherited(arguments);

							var aNode = domConstruct.create('a', {
								href : 'javascript:;'
							}, this.domNode);

							if (this.forward)
								this.href = util.setUrlParameter(this.href,
										'forward', this.forward);

							this.proxyClick(aNode, this.href, '_blank');

							if (!this.icon)
								this.icon = "/resource/style/default/attachment/default.png";
							
							this.icon = util
										.formatUrl(this.icon);

							domConstruct.create('div', {
								className : 'muiDripImgbox',
								innerHTML : '<img src="' + this.icon + '" />'
							}, aNode);

							if (this.label)
								domConstruct.create('span', {
									className : 'muiDripListTitle muiFontSizeM muiFontColorInfo',
									innerHTML : this.label
								}, aNode);

							if (this.summary) {

								domConstruct.create('div', {
									className : 'muiDripTeachGrade muiFontSizeS muiFontColorMuted',
									innerHTML : this.summary
								}, aNode);

							}
						},

						_setLabelAttr : function(label) {
							if (label)
								this._set("label", label);
						}
					});
			return item;
		});
