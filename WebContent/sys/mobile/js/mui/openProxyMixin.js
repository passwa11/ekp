define([ "dojo/_base/declare", "mui/device/adapter", "mui/util",
		"dojo/_base/lang", "dojo/dom-class", "dojo/touch" ], function(declare,
		adapter, util, lang, domClass, touch) {

	return declare("mui.openProxyMixin", null, {

		_selClass : "mblListItemSelected",
		
		buildRendering : function(){
			this.inherited(arguments);
			domClass.add(this.domNode,'muiPortalClickItem');
		},
		
		proxyClick : function(node, href, target, contxt) {
			node.dojoClick = true;
			this.connect(node, 'click', lang.hitch(this, function() {
				this.set("selected", true);

				var _href = href;
				if (!contxt)
					_href = util.formatUrl(href,true)
					// 放定时器后面在ios下滑动情况下会无法打开

				adapter.open(_href, target);

				this.defer(function() {

					this.set("selected", false);

				}, 100);

			}));
		},

		_setSelectedAttr : function(selected) {

			this.inherited(arguments);

			domClass.toggle(this.domNode, this._selClass, selected);

		}

	});

});