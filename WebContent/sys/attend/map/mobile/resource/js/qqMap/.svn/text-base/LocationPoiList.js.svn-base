define([
    "dojo/_base/declare",
    "dojo/topic",
    'dojo/query',
    "dojox/mobile/EdgeToEdgeStoreList",
    'dojox/mobile/_StoreListMixin', 
    'mui/list/_ListNoDataMixin',
	"dojo/date",
	"mui/util",
	'dojo/_base/lang',
	"dojo/request",
	"dojo/dom-style",
	'dojo/dom-geometry',
	"./LocationListMixin"
	], function(declare,topic,query,EdgeToEdgeStoreList,_StoreListMixin,ListNoDataMixin,dateClz,util,lang,request,domStyle,domGeometry,LocationListMixin) {
	
	return declare("sys.attend.LocationPoiList", [EdgeToEdgeStoreList,_StoreListMixin,ListNoDataMixin,LocationListMixin], {
		
		// 支持URL
		url: '',
		
		busy: false,
		
		postMixInProperties:function(){
			this.subscribe("/location/poi/dataChange","handleDataChange");
		},
		buildRendering : function() {
			this.inherited(arguments);
		},
		
		startup : function() {
			if(this._started){ return; }
			this.inherited(arguments);
		},
		
		_formatDatas :function(widget,args){
			var newArray = [];
			var pois = args.pois;
			if(pois.length==0 && !args.location.title){
				return newArray;
			}
			var location = args.location;
			var showStatus = "view";
			if(widget.canSearch || widget.showStatus == 'edit'){
				showStatus = "edit";
			}
			location['defaultValue'] = true;
			location.showStatus = showStatus;
			var tmpTitle = location.title.split(' ');
			location.title = tmpTitle.length>0 ?tmpTitle[0]:location.title;
			if(pois.length>0){
				var result = [];
				for(var i = 0;i< pois.length;i++){
					var loc = pois[i];
					loc.showStatus = showStatus;
					if(loc.title==location.title && loc.address==location.address){
						continue;
					}
					result.push(loc);
				}
				if(location.location || location.point){
					newArray = [location].concat(result);
				}else{
					newArray = result;
				}
			}else{
				if(location.location || location.point){
					newArray = [location].concat(pois);
				}else{
					newArray = pois;
				}
			}
			
			return newArray;
		},
		handleDataChange:function(widget,args){
			var newArray = this._formatDatas(widget,args);
			this.generateList(newArray);
			this.totalSize=newArray.length;
			topic.publish('/mui/list/loaded', this,newArray);
			if(this.totalSize>0){
				var listDom = widget.listdom || query('.muiLocationListDom')[0];
				domStyle.set(listDom,'display','block');
				var winH = util.getScreenSize().h;
				var maxH = domStyle.get(query('.muiLocationListDom .mblScrollableView')[0],'height');
				var contentH = domStyle.get(query('.muiLocationPoiList')[0],'height');
				contentH = contentH>maxH ? maxH:contentH;
				if(this.totalSize >1){
					contentH = 120;
				}
				if(this.totalSize >2){
					contentH = 180;
				}
				domStyle.set(query('.muiLocationListDom .mblScrollableView')[0],'height',contentH+'px');
				//列表滚动位置
				domStyle.set(query('.muiLocationListDom .mblScrollableViewContainer')[0],'transform','translate3d(0px, 0px, 0px)');
				//定位图标位置
				var info = domGeometry.position(query('.muiLocationListDom')[0]);
				domStyle.set(query('.muiLocationGeoBtn .muiMapGeo')[0].parentNode,'top',(info.y-52)+'px');
			}else{
				domStyle.set(query('.muiLocationListDom')[0],'display','none');
			}
		}
		
	});
});