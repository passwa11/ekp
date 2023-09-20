define([ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-construct",
		"mui/util", "dojo/dom-style" ], function(declare, WidgetBase,
		domConstruct, util, domStyle) {

	return declare("sys.mportal.iframe", [ WidgetBase ], {

		url : '',

		height : '',

		baseClass : 'muiPortalIframe',

		iframeResize : function(evt) {

			if (!evt || !evt.data)
				return;
			var args;
			if(typeof(evt.data) === "string") {
				args = JSON.parse(evt.data).args;
			} else {
				args = evt.data.args;
			}

			if (!args || args.length == 0)
				return;

			if (args[0].name != 'resize')
				return;

			var arg = args[0];

			if (this.id == arg.target) {

				domStyle.set(this.iframeNode, {
					height : arg.data.height + 'px'
				})

			}

		},

		buildRendering : function() {

			this.inherited(arguments);

			this.connect(window, 'message', 'iframeResize');

			this.url = util.setUrlParameter(this.url, 'LUIID', this.id)
			this.iframeNode = domConstruct.create('iframe', {
				src : util.formatUrl(this.url),
				width : '100%',
				scrolling : 'no'
			}, this.domNode);

			if (this.height)
				domStyle.set(this.iframeNode, {
					height : this.height + 'px'
				});

		}

	});
});