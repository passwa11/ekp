define([
    "dojo/_base/declare",
    "dojox/mobile/sniff",
	], function(declare, has) {
	
	return declare("km.review.mobile.resource.js.list.ReviewListRefreshMixin", null, {
		
		startup:function(){
			this.inherited(arguments);
			this.initEvent();
		},
		
		//不知道为什么现在ios执行back时无法刷新列表，所以先暂时做这个支持
		initEvent:function(){
			if(has('ios')){
				window.onload = function(){
					window.isPageHide = false; 
					window.addEventListener('pageshow', function () { 
						if (window.isPageHide) { 
							window.location.reload(); 
						} 
					}); 
					window.addEventListener('pagehide', function () { 
						window.isPageHide = true; 
					});
				}  
			}
		}
	});
});