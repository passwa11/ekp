define(["dojo/_base/declare", "dijit/_WidgetBase","dojo/dom-construct","dojo/dom-style",
        "dojo/date/locale","dojo/touch","dojox/mobile/_css3",'dojox/mobile/SwapView','dojo/topic',
        'dojo/dom-attr','dojo/query','dojo/dom-class'], 
		function(declare,WidgetBase,domConstruct,domStyle,locale,touch,css3,SwapView,topic,domAttr,query,domClass){
	
	return declare("sys.attend.AttendSwapLocInfo",[WidgetBase],{
		
		map : null,
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/attend/location/swapinfo/datas",'renderLocationView');
			this.subscribe("/dojox/mobile/viewChanged",'doViewChanged');
		},
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = domConstruct.create('div');
		},
		
		renderLocationView :function(widget,evt){
			if(evt && evt.datas && evt.datas.length>0) {
				var __datas = evt.datas;
				var infoNode = domConstruct.create('section',{className:'muiSignInMapDetail trail'},this.domNode);
				for(var i=0; i<__datas.length; i++) {
					var __data = __datas[i];
					var swapview = new SwapView();
					domConstruct.place(swapview.domNode,infoNode,'last');
					swapview.fdLng = __data.fdLng;
					swapview.fdLat = __data.fdLat;
					swapview.fdOrder = i;
					swapview.startup();
					
					domConstruct.create('div',{className:'muiSignInMapDetailPost',innerHTML:__data.fdCategoryName},swapview.domNode);
					var titleNode = domConstruct.create('div',{className:'muiSignInMapDetailHeading',innerHTML:''},swapview.domNode);
					domConstruct.create('span',{className:'time',innerHTML:__data.fdSignedTime},titleNode);
					domConstruct.create('span',{className:'title',innerHTML:(__data.fdLocation || '')},titleNode);
					var statusClass = "signInLabelPrimary";
					if(__data.fdStatus!='1'){
						statusClass = "signInLabelDanger";  
					}
					//domConstruct.create('span',{className:'muiSignInLabel '+statusClass,innerHTML:__data.fdStatusTxt},titleNode);
					if(__data.fdDesc) {
						domConstruct.create('p',{className:'muiSignInMapDetailSummary',innerHTML:__data.fdDesc },
								swapview.domNode);
					}
					if(__data.picArr && __data.picArr.length > 0) {
						var imgbox = domConstruct.create('div',{className:'imgbox'},swapview.domNode);
						for(var j=0; j<__data.picArr.length; j++) {
							var imgNode =  domConstruct.create('span', null, imgbox);
							domConstruct.create('img', { src:__data.picArr[j] }, imgNode);
						}
					}
				}
				
				var dotsNode = this.dotsNode = domConstruct.create('section',{className:'muiSignInMapDetail-dotted'},infoNode);
				if(__datas.length > 1) {
					for(var k=0; k<__datas.length; k++){
						if(k == 0) {
							domConstruct.create('span',{className:'active', dotid: k},dotsNode);
						} else {
							domConstruct.create('span',{dotid: k},dotsNode);
						}
					}
				}
			}
			
		},
		
		doViewChanged : function(evt) {
			if(!evt)
				return;
			query('span[dotid]', this.dotsNode.domNode).forEach(function(node){
				var idx = domAttr.get(node,'dotid');
				if(idx == evt.fdOrder) {
					domClass.add(node, 'active');
				} else {
					domClass.remove(node, 'active');
				}
			});
			topic.publish('sys/attend/location/markerpanto',{ lng : evt['fdLng'], lat : evt['fdLat']});
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		}
		
		
	});
});