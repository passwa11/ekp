/**
 * 高德地图接口入口
 */
define(['dojo/_base/lang','dojo/topic','dojo/dom-construct','../common/BaseMap',"./LocationDialog","mui/device/device",
        "mui/device/adapter",'mui/coordtransform','../common/MapUtil','mui/util',"mui/map","mui/dialog/Tip"], 
		function(lang,topic,domConstruct,BaseMap,LocationDialog,device,adapter,coordtransform,MapUtil,util,map,Tip) {

	return lang.mixin({

		mapURL : 'https://webapi.amap.com/maps?v=1.4.15&key=2b0ff8afd4d82301c4cd3d277221e28b&plugin=AMap.AdvancedInfoWindow',
		
		init : function(widget){
			var self = this;
			if(!window['_$isMapJsLoaded$_']){
				window[widget.id + '_callback'] = function(){
					self._openLocationDialog(widget);
				};
				this.mapURL = util.setUrlParameter(this.mapURL,'callback',widget.id + '_callback');
				this.mapURL = util.setUrlParameter(this.mapURL,'key',widget.mapKeyPc);

				//配置安全密钥
				if (widget.mapKeyPcSecurityKey){
					// 兼容不需要安全密钥
					window._AMapSecurityConfig = {
						securityJsCode: widget.mapKeyPcSecurityKey,
					}
				}
				//地图组件key与web服务key不一致
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
			if(!window[widget.id +'_muiLocationDialog']){
				var dialog = window[widget.id +'_muiLocationDialog'] = new LocationDialog({
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
								lat : __evt.location.lat
							};

							if(__evt.__location__.propertyProvince || __evt.__location__.propertyCity || __evt.__location__.propertyDistrict){
								 map.getAMapLocation(
								          {
								            coordType: 5,
								            lat: __evt.location.lat,
								            lng: __evt.location.lng
								          },
								          function(result) {
								        	  __evt.province=result.addressComponent.province;
								        	  __evt.city=result.addressComponent.city || result.addressComponent.province;
								        	  __evt.district=result.addressComponent.district;
									          topic.publish('sys/attend/map/edit.finished',__evt,__evt.__location__);
								          },
								          function() {
								        	  Tip.fail({
								                  text: "地图加载过程失败,请重试!"
								                });
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
				window[widget.id +'_muiLocationDialog'].set('__location__',widget);
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
			window[widget.id +'_muiLocationDialog'].show(evt);
		}
		
	},BaseMap);

});