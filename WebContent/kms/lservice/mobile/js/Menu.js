define("kms/lservice/mobile/js/Menu", [
		"dojo/dom-construct",
		'dojo/_base/declare',
		"dojo/dom-class",
		"dojo/_base/lang",
		"dijit/_WidgetBase",
		"dojo/_base/array",
		"dojo/request",
		"mui/util" ], function(
		domConstruct,
		declare,
		domClass,
		lang,
		WidgetBase,
		array,
		request,
		util) {

	return declare('kms.lservice.mobile.js.Menu', [ WidgetBase ], {

		url : '',

		title : '',

		jsondatas : [],

		buildRendering : function() {

			this.inherited(arguments);

			domClass.add(this.domNode, 'navgationWrap');

			this.renderNav();

		},

		renderNav : function() {

			if (this.url) {

				this.renderNavGroup({
					url : this.url,
					title : this.title
				});

				return;

			}

			array.forEach(this.jsondatas, lang.hitch(this, function(group) {

				this.renderNavGroup(group);

			}));

		},

		renderNavGroup : function(group) {

			var bank = domConstruct.create('div', null, this.domNode);

			if (group.title)
				var title = domConstruct.create('h4', {
					innerHTML : group.title
				}, bank);

			var promise = request.get(util.formatUrl(group.url), {
				headers : {
					'Accept' : 'application/json'
				}
			});

			promise.response.then(lang.hitch(this, function(datas) {

				if (!datas)
					return;

				var items = eval(datas.data);

				if (!items)
					return;

				var group = domConstruct.create('div', {
					className : 'navgationList'
				}, bank);

				var ul;

				array.forEach(items, lang.hitch(this, function(item, index) {

					if (index % 4 == 0)
						ul = domConstruct.create('ul', null, group);

					this.renderNavItem(item, ul);

				}))

			}));

		},

		renderNavItem : function(item, ul) {

			var li = domConstruct.create('li', null, ul);

			var node = domConstruct.create('a', {
				href : 'javascript:;',
				innerHTML : '<span class="iconBox"></span><span class="txt">'
						+ item.text + '</span>'
			}, li);

			this.connect(node, 'click', function() {

				window.open(util.formatUrl(item.url), '_self');
			});

		}

	});

});