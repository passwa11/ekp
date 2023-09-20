define( [ "dojo/_base/declare", "dojox/mobile/_ItemBase","dojo/dom-style", "mui/util"], 
		function(declare,_ItemBase, domStyle, util) {

	return declare("mui.header.HeaderItem", [ _ItemBase ], {

		icon : null,

		baseClass : 'muiHeaderItem',
		
		label: null,
		
		referListId:null,
		
		buildRendering : function() {
			if(this.icon){
				this.baseClass = this.baseClass + " " + this.icon;
			}
			this.inherited(arguments);
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/mui/list/loaded","refreshLabel");
		},
		
		refreshLabel:function(evts){
			if(evts && evts.id==this.referListId && this.label){
				if(evts.totalSize)
					this.domNode.innerHTML = "<div class='muiHeaderItemLable muiFontSizeM muiFontColorInfo'>"+util.formatText(this.label) + "("+ evts.totalSize +")</div>";
			}
		},
		
		startup:function(){
			this.inherited(arguments);
			if (this.domNode.parentNode) {
				var h = this.domNode.parentNode.style.height;
				// 部分h带单位
				if(h > 0|| parseFloat(h)>0){
					var styleVar =  {
							'height':h,
							'line-height' : h
						};
					domStyle.set(this.domNode, styleVar);
				}
			}
		},
		
		_setLabelAttr:function(label){
			if(this.label){
				this.domNode.innerHTML = "<div class='muiHeaderItemLable muiFontSizeM muiFontColorInfo'>"+util.formatText(this.label)+"</div>";
			}
		}
		
	});
});
