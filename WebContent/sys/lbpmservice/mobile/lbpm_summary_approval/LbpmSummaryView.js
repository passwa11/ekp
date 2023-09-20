define([
    "dojo/_base/declare",
	"mui/list/StoreElementScrollableView",
	"dojo/dom-style",
	"dojo/query"
	], function(declare, StoreElementScrollableView,domStyle,query) {
	
	return declare("sys.lbpmservice.mobile.lbpmSummaryApproval.LbpmSummaryView", [StoreElementScrollableView], {
	      startup: function() {
	        this.inherited(arguments);
	      },
	      
	      showNoMore : function () {
	    	  this.upwarp.style.visibility = 'visible'; // 显示上拉加载区域
			  this.upwarp.style.display = 'block'; // 显示上拉加载区域
			  domStyle.set(this.upwarp,{
				  "margin-bottom":"6.7rem"
			  })
			  domStyle.set(query("#lbpmSummaryList")[0],{
				  "margin-bottom":"0"
			  })
			  this.optUp.showNoMore(this, this.upwarp); // 无更多数据
			  
	      }
	});
});
