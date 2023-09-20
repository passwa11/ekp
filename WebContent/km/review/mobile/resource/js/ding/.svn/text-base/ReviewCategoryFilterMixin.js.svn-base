define(['dojo/_base/declare',"mui/util","dojo/dom-style","dojo/dom-construct"], function(declare, util,domStyle,domConstruct) {
	
		return declare('km.review.ding.ReviewCategoryFilterMixin', null, {
			
			buildRendering: function() {
		        this.inherited(arguments);
		        var txtNode = domConstruct.create("div",{className:"muiHeaderCategoryTxt",innerHTML:'分类'},this.domNode.parentNode);
		        this.connect(txtNode, "click", "openFilter")
			}
		
		});
});