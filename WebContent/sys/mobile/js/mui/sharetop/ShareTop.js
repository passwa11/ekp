define( [ "dojo/_base/declare", "dijit/_Contained", "dijit/_Container",
		"dijit/_WidgetBase", "dojo/_base/lang", "dojo/dom-class",
		"dojo/dom-construct", "dojo/dom-style",  "dojo/_base/array"], function(declare, Contained,
		Container, WidgetBase, lang, domClass, domConstruct, domStyle, array) {
	return declare("mui.top.Top", [ WidgetBase, Container, Contained ], {
		bottom : '60px',
		right : '5px',
		fdModelId : '',
		fdModelName : '',
		fdTemplateId : '',
		fdAllPath : '',

		buildRendering : function() {
			this.inherited(arguments);
			if (!this.containerNode)
				this.containerNode = this.domNode;
			domClass.replace(this.containerNode, "muiShareTop");
			domStyle.set(this.domNode, {
				bottom : !this.bottom ? "60px" : this.bottom,
				right : !this.right ? "60px" : this.right
			});
			domStyle.set(this.domNode, {
				display : 'block'
			});
		},

		startup : function() {
			if (this._started)
				return;
			this.inherited(arguments);
		},

		postCreate : function() {
			this.connect(this.domNode, "onclick", '_onClick');
			this.connectToggle();
		},

		_onClick : function(evt) {
			this.toCreate(evt);
		},

		// 以下mixin实现

		// 置顶实现
		toCreate : function(evt) {
			
		},
		// 显示隐藏切换
		connectToggle : function() {
		}

	});
});
