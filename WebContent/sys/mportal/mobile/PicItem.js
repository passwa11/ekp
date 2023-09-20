define( [ "dojo/_base/declare", "mui/picslide/PicItem","sys/mportal/mobile/OpenProxyMixin"], function(declare,PicItem,OpenProxyMixin) {
	var PicItem = declare("mui.mportal.mobile.PicItem", [PicItem,OpenProxyMixin], {
		
		buildRendering : function() {
			this.inherited(arguments);
			if (this.href) {
				if(this.openByProxy){
					this.proxyClick(this.domNode, this.href, '_blank');
				}
			}
		},
		
		_onclick : function(e) {
			if(!this.openByProxy){
				this.inherited(arguments);
			}
		},
	});
	return PicItem;
});