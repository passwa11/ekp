define( [ "dojo/_base/declare", "dijit/_WidgetBase",'dojo/topic',"dojo/dom-style","dojo/dom-construct"],
		function(declare,_WidgetBase ,topic,domStyle,domConstruct) {

	return declare("mui.nav.HeaderMoreItem", [ _WidgetBase], {

		
		baseClass : 'muiCateCancel',
		
		label:'关闭',
		
		postCreate : function() {
			this.subscribe("/mui/search/onfocus","_onfocus");
			this.subscribe("/mui/search/onblur", "_onblur");
		},
		_onfocus: function(srcobj) {
			domStyle.set(this.textNode,{'display':'none'});
		},
		_onblur: function(srcobj) {
			domStyle.set(this.textNode,{'display':'block'});
		},
		
		
		buildRendering : function() {
			this.inherited(arguments);
			this.textNode = domConstruct.create('div', {
				className : '',
				innerHTML :this.label
			}, this.domNode);
			
			this.connect(this.textNode,'click', function(){
				this.defer(function(){
					this._goBack();
				},350);
			});
		},
		
		_goBack : function(){
			topic.publish("/mui/category/cancel" , this);
		},
		startup:function(){
			this.inherited(arguments);
		}
	});
});
