define([ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_Contained",
		"dijit/_Container", "dojo/dom-construct", "mui/util", "dojo/dom-class",
		"sys/mportal/mobile/OpenProxyMixin" ], function(declare, WidgetBase,
		Contained, Container, domConstruct, util, domClass, OpenProxyMixin) {
	var menuItem = declare("sys.zone.mportal.PersonItem", [ WidgetBase,
			Container, Contained, OpenProxyMixin ], {

		buildRendering : function() {

			this.inherited(arguments);

			var contentNode = domConstruct.create('div', {
				className : 'muiPortalPersonModule'
			}, this.domNode, 'last');

			domConstruct.create('a', {
				className : 'muiPortalIcon mui ',
				href : 'javascript:;',
				innerHTML : "<img src='" + util.formatUrl(this.icon) + "'>"
			}, contentNode);

			this.proxyClick(this.domNode, this.href, '_blank');

			this.connect(this.domNode, 'click', this.click);
			domConstruct.create('div', {
				className : 'muiPortalPersonText textEllipsis muiFontColorInfo',
				innerHTML : this.text
			}, this.domNode);

		}
		
	});
	return menuItem;
});