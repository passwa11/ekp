define([ "dojo/_base/declare", "mui/device/adapter", "mui/util",
		"dojo/_base/lang", "dojo/dom-class"], function(declare,
		adapter, util, lang, domClass) {

	return declare("sys.mportal.OpenProxyMixin", null, {

		_selClass : "mblListItemSelected",
		
		buildRendering : function(){
			this.inherited(arguments);
			domClass.add(this.domNode,'muiPortalClickItem');
		},
		
		
		proxyClick : function(node, href, target, contxt) {
			node.dojoClick = true;
			this.connect(node, 'click', lang.hitch(this, function() {

				var _href = href;
				if (!contxt)
					_href = util.formatUrl(href,true)
					// 放定时器后面在ios下滑动情况下会无法打开
				adapter.open(_href, target);

			}));

		}

	});

});