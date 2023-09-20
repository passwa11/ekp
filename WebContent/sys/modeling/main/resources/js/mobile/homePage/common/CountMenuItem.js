define([
		"dojo/_base/declare",
		"dojo/dom-style",
		"dijit/_WidgetBase",
		"dijit/_Contained",
		"dijit/_Container",
		"dojo/dom-construct",
		"mui/util",
		"sys/mportal/mobile/OpenProxyMixin"], function(
		declare,
		domStyle,
		WidgetBase,
		Contained,
		Container,
		domConstruct,
		util,
		OpenProxyMixin) {

	var menuItem = declare("sys.modeling.main.resources.js.mobile.homePage.common.CountMenuItem", [
			WidgetBase,
			Container,
			Contained,
			OpenProxyMixin ], {

		buildRendering : function() {
			this.domNode = domConstruct.create('li');
			
			this.inherited(arguments);
			if (this.width)
				domStyle.set(this.domNode, {
					width : this.width
				})
			var contentNode = domConstruct.create('div', {
				"className" : "mportalList-slide",
			}, this.domNode, 'last');

			domConstruct.create('p', {
				innerHTML : this.count,
				style:"color:"+this.numberColor
			}, contentNode);

			var spanStyle = "color:"+this.textColor+";white-space: nowrap;text-overflow: ellipsis;overflow:hidden;display: block;";
			domConstruct.create('span', {
				innerHTML : this.title,
				style: spanStyle
			}, contentNode);

			this.proxyClick(contentNode, this.url, '_self');
		},

		otherClick : function(evt) {

			this.click(evt);

		}

	});
	return menuItem;
});