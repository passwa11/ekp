define("km/imeeting/mobile/resource/js/list/PlaceContent", [ "dojo/_base/declare", "dojo/dom-style", "dojo/dom-class", "dijit/_WidgetBase",
		"dijit/_Contained", "dijit/_Container", "mui/dialog/Tip", "dojo/_base/array", "./item/PlaceContentItem",
		"dojo/_base/lang", "dojo/query", "dojo/date", "dojo/date/locale"], 
	function(declare, domStyle, domClass, WidgetBase, Contained, Container, Tip, array, PlaceContentItem, 
			lang, query, date, locale) {
	var placeContnt = declare("km.imeeting.PlaceContent", [ WidgetBase, Container,
			Contained ], {
		//默认自适应

		baseClass : "",

		// 渲染模板
		itemRenderer : PlaceContentItem,
		
		placeSelected: "",
		
		startup : function() {
			if (this._started)
				return;
			this.inherited(arguments);
			this.subscribe('/km/imeeting/place/onComplete','generatePlaceTable');
		},
		buildRendering : function() {
			this.inherited(arguments);
		},
		generatePlaceTable: function(srcObj,evt) {
			//debugger;
			this.domNode.innerHTML = "";

			var now = locale.format(new Date(), {
				selector: 'date',
				datePattern: 'yyyy-MM-dd'
			});
			
			array.forEach(evt, function(item, index) {
				
				//判断是否为过期日期
				var lateDays = date.difference(
					new Date(now.replace(/-/g, '/')), 
					new Date(item.fdTime.replace(/-/g, '/'))
				);
				if(lateDays < 0) {
					item.late = true;
				}
				
				this.addChild(this.createListItem(item));
			}, this);
			this.subscribe('/km/imeeting/selected/change','drawPlaceTable');
			this.subscribe('/km/imeeting/placeitem/_selected','handlePlaceItemChanged');
		},
		drawPlaceTable: function(srcObj,evt) {
			//console.log(evt.placeId);
			//console.log(evt.start);
			//console.log(evt.finish);
			var start = evt.start;
			var finish = evt.finish;
			var placeId = evt.placeId;
			var allEle = query('[name ^="'+placeId+'"]',this.domNode);
			array.forEach(allEle, function(item) {
				if(!domClass.contains(item, "place_occupied")){
					domClass.replace(item,"place_free");
				}else{
					if(evt.isCancel && domClass.contains(item, "place_nobook")){
						domClass.remove(item,"place_nobook");
					}
				}
			});
			if(start && finish && start != finish){
				for(var i = start;i<= finish;i++){
					var ele = query('[name="'+placeId+'_'+i+':00'+'"]',this.domNode)[0];
					if(ele && !domClass.contains(ele, "place_occupied")){
						domClass.replace(ele,"place_selected");
					}else{
						if(ele && domClass.contains(ele, "place_nobook")){
							domClass.remove(ele, "place_nobook");
						}
					}
				}
			}else{
				var selected;
				if(start){
					selected = start;
				}else if(finish){
					selected = finish;
				}
				if(selected){
					var SelectedEle = query('[name="'+placeId+'_'+selected+':00'+'"]',this.domNode)[0];
					domClass.replace(SelectedEle,"place_selected");
					array.forEach(allEle, function(item) {
						if(domClass.contains(item, "place_nobook")){
							domClass.remove(item,"place_nobook");
						}
					});
				}
			}
		},
		
		handlePlaceItemChanged: function(srcObj,evt) {
			if(this.placeSelected == ""){
				this.placeSelected = srcObj.fdId;
			}else if(this.placeSelected != srcObj.fdId){
				//alert(this.placeSelected );
				//Tip.tip({icon:'mui mui-warn', text:'只能预约一个会议室！！',width:'200',height:'60'});
				//取消选择状态
				//var ele = query('[name="'+srcObj.fdId+'_'+evt.value+':00'+'"]',this.domNode)[0];
				//domClass.replace(ele,"place_free");
				//取消已已选择
				var ele = query('[name ^="'+this.placeSelected+'"]',this.domNode);
				array.forEach(ele, function(item) {
					if(!domClass.contains(item, "place_occupied")){
						domClass.replace(item,"place_free");
					}else{
						if(domClass.contains(item, "place_nobook")){
							domClass.remove(item,"place_nobook");
						}
					}
				});
				this.placeSelected = srcObj.fdId;
			}
		},
		
		// 格式化数据
		_createItemProperties : function(item) {
			return item;
		},
		createListItem : function(item) {
			var item = new this.itemRenderer(this
					._createItemProperties(item));
			return item;
		}
	});
	return placeContnt;
});
