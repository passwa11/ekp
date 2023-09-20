define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-style",
		"dojox/mobile/_ItemBase", "mui/util",
		"sys/mportal/mobile/OpenProxyMixin" ], function(declare, domConstruct,
		domStyle, ItemBase, util, OpenProxyMixin) {
	var item = declare("sys.mportal.ComplexUImgTextItemMixin", [ ItemBase,
			OpenProxyMixin ], {

		baseClass : "muiDripAnnoun",

		label : "",

		href : "",

		image : "",

		icon : "",

		buildRendering : function() {

			this.inherited(arguments);

			this.proxyClick(this.domNode, this.href, '_blank');

			if (!this.icon)
				this.icon = this.image;

			if (this.icon) {
				
				this.icon = util.formatUrl(this.icon);

				var imgNode = domConstruct.create('div', {
					className : 'muiDripImgbox'
				}, this.domNode);
				
				domStyle.set(imgNode, {
					'background-image' : 'url(' + this.icon + ')'
				});
			}

			domConstruct.create('p', {
				className : 'muiDripAnnounTxt muiFontSizeM muiFontColorInfo',
				innerHTML : this.label
			}, this.domNode);

		},

		_setLabelAttr : function(label) {
			if (label)
				this._set("label", label);
		}

	});
	return item;
});