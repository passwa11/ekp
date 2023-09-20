define([
		"dojo/_base/declare",
		"dojo/dom",
		"mui/rtf/_ImageGlobalResizeMixin",
		"mui/rtf/_TableResizeMixin",
		"mui/rtf/_VideoResizeMixin",
		"dojo/_base/array",
		"dojo/_base/lang",
		"dojo/dom-attr",
		"dojo/query",
		"dojo/on" ], function(
		declare,
		dom,
		_ImageGlobalResizeMixin,
		_TableResizeMixin,
		_VideoResizeMixin,
		array,
		lang,
		domAttr,
		query,
		on) {

	var claz = declare("mui.rtf.RtfResizeUtil", [
			_ImageGlobalResizeMixin,
			_TableResizeMixin,
			_VideoResizeMixin ], {

		constructor : function(options) {

			if (options) {

				this.name = options.name;
				this.channel = options.channel || 'default';
				this.formatContent(options.containerNode);
				this.restore(options.containerNode);

			}
		},

		restore : function(container) {

			array.forEach(query('a', container), lang.hitch(this,
					function(item) {

						var href = item.href;

						domAttr.set(item, 'href', 'javascript:;');

						on(item, 'click', function() {

							if (href
									&& (href.indexOf('http') == 0 || href
											.indexOf('/') == 0)) {

								window.open(href, '_self');

							} else {

								new Function(href)();

							}

						})

					}));

		},

		destroy : function() {

			this.emptySrcList();
		}
	});
	return claz;
});