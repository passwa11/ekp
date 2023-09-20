/**
 * 腾讯地图接口入口
 */
define(['dojo/_base/lang','dojo/topic','dojo/dom-construct',
        'mui/util',
        'mui/coordtransform',
        '../common/BaseMap',
        "./LocationDialog",
        '../common/MapUtil',
        "mui/map",
        "mui/dialog/Tip"], 
		function(lang,topic,domConstruct, util,coordtransform,BaseMap,LocationDialog,MapUtil,map,Tip) {

	return lang.mixin({
		
		mapURL : 'https://map.qq.com/api/js?v=2.exp&key=I7RBZ-7WR3D-V6V4R-HQGPA-BJYVT-WIBLL&libraries=place,convertor',	
		
		init : function(widget){
			var self = this;
			if(!window['_$isMapJsLoaded$_']){
				window[widget.id + '_callback'] = function(){
					self._openLocationDialog(widget);
				};
				this.mapURL = util.setUrlParameter(this.mapURL,'callback',widget.id + '_callback');
				this.mapURL = util.setUrlParameter(this.mapURL,'key',widget.mapKey);
				this._loadScript(this.mapURL);
			}else{
				self._openLocationDialog(widget);
			}
		},
		
		_openLocationDialog : function(widget){
			if(window['muiLocationDialogIsUsing']){
				return;
			}
			window['muiLocationDialogIsUsing'] = true;
			if(!window['muiLocationDialog']){
				var dialog = window[widget.id+'_muiLocationDialog'] = new LocationDialog({
					showStatus : widget.showStatus,
					isShowPoi : widget.isShowPoi,
					isShowSearch : widget.isShowSearch,
					poiRadius:widget.radius,
					__location__ : widget,
					confirmLocation : function(evt){
						if(evt){
							var __evt = lang.mixin({},evt);
							__evt.location = __evt.point = {
								lng : __evt.location.lng,
								lat :__evt.location.lat
							};
							if(__evt.__location__.propertyProvince || __evt.__location__.propertyCity || __evt.__location__.propertyDistrict){
								map.getQMapLocation(
								          {
								            coordType: 5,
								            lat: __evt.location.lat,
								            lng: __evt.location.lng
								          },
								          function(result) {
								        	  __evt.province=result.addressComponent.province;
								        	  __evt.city=result.addressComponent.city;
								        	  __evt.district=result.addressComponent.district;
								        	  topic.publish('sys/attend/map/edit.finished',__evt,__evt.__location__);
								          },
								          function() {
								            Tip.fail({
								              text: "地图加载过程出错,请重试"
								            })
								          }
								        );
							}else{
								topic.publish('sys/attend/map/edit.finished',__evt,__evt.__location__);
							}
						}
						window['muiLocationDialogIsUsing'] = false;
					}
				});
				dialog.startup();
				domConstruct.place(dialog.domNode,document.body,'last');
			} else {
				window[widget.id+'_muiLocationDialog'].set('__location__',widget);
			}
			var evt = {
				showStatus : widget.showStatus	
			};
			if(widget.nameNode.value){
				widget.nameNode.blur();
				lang.mixin(evt,{ name : widget.value || widget.nameNode.value });
				if(widget.coordinateValue){
					var coordArr = widget.coordinateValue.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
					var coordType = MapUtil.getCoordType(widget.coordinateValue);
					lang.mixin(evt,{
						location : {
							lat : coordArr[0],
							lng : coordArr[1],
							coordType:coordType
						}
					});
				}
				if(widget.detailValue){
					lang.mixin(evt,{ detail : widget.detailValue  });
				}
			}
			window[widget.id+'_muiLocationDialog'].show(evt);
		}
		
	},BaseMap);

});