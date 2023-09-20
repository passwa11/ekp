define([
		"dojo/_base/declare",
		"dojo/dom-style",
		"dijit/_WidgetBase",
		"dijit/_Contained",
		"dijit/_Container",
		"dojo/dom-construct",
		"mui/util",
		"sys/mportal/mobile/OpenProxyMixin" ], function(
		declare,
		domStyle,
		WidgetBase,
		Contained,
		Container,
		domConstruct,
		util,
		OpenProxyMixin) {

	var menuItem = declare("sys.mportal.MenuItem", [
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
				
			var contentNode = domConstruct.create('a', null, this.domNode, 'last');

			var color = domConstruct.create('div', {
				className : 'muiPortalIcon'
			}, contentNode);
			
			var attr = '';
			if (this.iconType) {
				if (this.iconType == '1' || this.iconType == '2') {
					attr = {
						className : 'mui imgBox ' + this.icon.replace('selectImg ', '')
					}
				} else if(this.iconType == '4'){ //素材库
					var url = this.icon;
					if(url.indexOf("/") == 0){
						url = url.substring(1);
					}
					attr = {
						className : 'mui imgBox',
						style:'background: url('+dojoConfig.baseUrl+url+') center center / contain no-repeat;border-radius: 18px;'

					}
				} else if(this.iconType == '3'){ //文字
					attr = {
						className : 'mui ',
						innerHTML : this.icon
					}
				} else {
					attr = {
						className : 'mui ',
						innerHTML : this.icon
					}
				}
			} else {
				if (this.icon.indexOf('mui') >= 0) {
					attr = {
						className : 'mui ' + this.icon
					}
				} else {
					attr = {
						className : 'mui ',
						innerHTML : this.icon
					}
				}
			}
			var img = domConstruct.create('div', attr, color);
			
			if (this.click)
				this.connect(img, 'click', 'otherClick');
			else
				this.proxyClick(img, this.url, '_blank');
			
			domConstruct.create('span', {
				innerHTML : this.text,
				className: 'muiFontColorInfo muiFontSizeMS'
			}, contentNode);

		},

		otherClick : function(evt) {

			this.click(evt);

		}

	});
	return menuItem;
});