define([
	"dojo/_base/declare",
	 "dojo/query",
	 "dijit/_WidgetBase",
	 "dojo/dom-style"
	], function(declare,query,_WidgetBase,domStyle) {
	return declare("kms.multidoc.view", [_WidgetBase],{
		 
		isShow : false,
		propagation : true,
		
		init: function(data){
			var self = this
			window.authorShow = function() {
				self.authorShow();
			}
			var all= query("body")[0];
			this.connect(all,'click','authorDis');
		},
		
		
		authorDis: function(){
			var popDom = query(".pop")[0];
			if(popDom){
				domStyle.set(popDom,"display","none");
		    	this.isShow = false
			}
		},
		
		authorShow: function(event){
			event = event || window.event;
			event.cancelBubble = true;
			if (event.preventDefault){
				event.preventDefault();
			}
			if (event.stopPropagation) {
				event.stopPropagation();
			}
			var popDom = query(".pop")[0];
			var display =  document.getElementById("authorNames").style.display;
		    if(this.isShow){
		    	document.getElementById("authorNames").style.display = 'none';
		    	domStyle.set(popDom,"display","none");
		    	this.isShow = false
		    }else{
		    	document.getElementById("authorNames").style.display = 'block';
		    	domStyle.set(popDom,"display","inline-block");
		    	this.isShow = true
		    }
		}
	})
	
});