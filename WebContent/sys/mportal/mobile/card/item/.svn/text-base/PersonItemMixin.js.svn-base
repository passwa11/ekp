define([ "dojo/_base/declare", "dojo/dom-construct", "dojox/mobile/_ItemBase",
		"mui/util", "dojo/_base/lang", "dojo/touch",
		"sys/mportal/mobile/OpenProxyMixin" ], function(declare, domConstruct,
		ItemBase, util, lang, touch, OpenProxyMixin) {

	var item = declare("sys.mportal.PersonItemMixin", [ ItemBase,
			OpenProxyMixin ], {

		baseClass : "muiPortalPersonCardItem",

		label : "",

		fdName : "",

		fdPostNamesShortName : "",

		imgUrl : "",

		href : "",

		buildRendering : function() {

			this.inherited(arguments);

			var a = domConstruct.create('a', {
				href : 'javascript:;'
			}, this.domNode);

			this.proxyClick(a, this.href, '_blank');

			domConstruct.create('div', {
				className : 'muiPortalPersonCardAvatar',
				innerHTML : "<span style='background:url(" + util.formatUrl(this.imgUrl) + ") center center no-repeat;background-size: cover;'/>"
			}, a);

			domConstruct.create('div', {
				className : 'muiPortalPersonCardName muiFontSizeM muiFontColorInfo',
				innerHTML : this.fdName
			}, a);

			if (this.fdPostNamesShortName)
				domConstruct.create('div', {
					className : 'muiPortalPersonCardPostName muiFontSizeS muiFontColorMuted',
					innerHTML : this.fdPostNamesShortName
			}, a);

		},

		_setLabelAttr : function(label) {
			if (label)
				this._set("label", label);
		}
	});
	return item;
});
