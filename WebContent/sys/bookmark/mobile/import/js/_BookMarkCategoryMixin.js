define([
    "dojo/_base/declare",
    "mui/form/_CategoryBase",
    "mui/simplecategory/SimpleCategoryMixin",
    "dojox/mobile/sniff",
	"mui/i18n/i18n!sys-bookmark:mui.sysBookmark"
],function(declare,_CategoryBase,SimpleCategoryMixin,has,Msg){
    return declare("sys.bookmark._BookMarkCategoryMixin",[_CategoryBase,SimpleCategoryMixin],{

    	buildRendering:function(){
			this.required = true;
    		this.modelName = "com.landray.kmss.sys.bookmark.model.SysBookmarkCategory";
    		this.key = "sysBookmarkCategory";
			this.inherited(arguments);
			this.domNode.dojoClick = !has('ios');
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.eventBind();
		},
		
		onClick : function(evt) {
			if (!this.isBooked){
				this.subject = Msg['mui.sysBookmark.select'];
				this._selectCate();
			}else{
				this.doMark(evt);
			}
			return false;
		},
		
		returnDialog: function(srcObj, evt) {
			var _self = this;
			if (evt) {
				if (srcObj.key == this.key) {
					this.curIds = evt.curIds
					this.curNames = evt.curNames
					this.doMark(evt,function(){
						_self.defer(function(){
							_self.closeDialog(srcObj)
							_self.curIds = "";
							_self.curNames = "";
						},300)
					});
				}
			}
	    }
    })
})