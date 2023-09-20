define([ "dojo/_base/declare", "dojo/dom-style", "dijit/_WidgetBase",
		"dijit/_Contained", "dijit/_Container", "dojo/dom-construct",
		"dojo/html", "mui/util", "./card/mixins/CardCreateMixin", 
		"./card/mixins/CardMoreMixin", "./CardMixin" 
		], function(declare, domStyle, WidgetBase, Contained,
		Container, domConstruct, html, util,
		CardCreateMixin, CardMoreMixin, CardMixin) {
	var Card = declare("sys.mportal.Card", [ WidgetBase, Container, Contained, CardCreateMixin,
			CardMoreMixin, CardMixin ], {

		baseClass : "muiPortalCard muiPortalContent",

		margin : true,

		config : {
			operations : {}
		},

		buildRendering : function() {

			this.inherited(arguments);

			// 没有下边距
			if (this.margin == false)
				domStyle.set(this.domNode, {
					'margin-bottom' : 0
				});

			// 当只配置标题不配置内容
			if (this.configs.length == 0) {

				this.buildHeader();
				return;
			}

			this.config = this.configs[0];

			var toolbar = this.config.operations.toolbar;

			if (toolbar != false) {
				this.buildHeader();
			}

			this.buildContent(this.config);

			if (toolbar)
				this.buildFooter();

		},

		buildFooter : function() {

			this.inherited(arguments);

		},

		buildHeader : function() {

			if (this.title) {

				this.buildHeaderNode();

				domConstruct.create('span', {
					className : "muiFontColorInfo",
					innerHTML : this.title
				}, this.headerNode);

			}

			this.inherited(arguments);

		}

	});

	return Card;
});