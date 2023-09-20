/**
 * 百度地图接口入口
 */
define(['dojo/_base/lang','dojo/topic','dojo/dom-construct','./LocationDialog','../common/MapUtil','mui/util',"mui/map","mui/dialog/Tip"], 
		function(lang,topic,domConstruct,LocationDialog,MapUtil,util,map,Tip) {

	return strategy = {
		
		mapURL : 'https://api.map.baidu.com/api?v=2.0&ak=' + MapUtil.bMapKey + '&s=1',
			
		init : function(widget){
			var self = this;
			if(!window['isBaiduMapJsLoaded']){
				window[widget.id + '_callback'] = function(){
					self._openLocationDialog(widget);
				};
				this.mapURL = util.setUrlParameter(this.mapURL,'callback',widget.id + '_callback');
				this.mapURL = util.setUrlParameter(this.mapURL,'ak',widget.mapKey);
				this._loadScript(this.mapURL);
			}else{
				self._openLocationDialog(widget);
			}
		},
		
		_loadScript : function(url, callback){
			if(window['isBaiduMapJsLoaded']){
				callback && callback();
				return;
			}
	        var head = document.getElementsByTagName('head')[0];
	        var script = document.createElement('script');
	        script.type = 'text/javascript';
	        script.src = url;
	        head.appendChild(script);
	        script.onload = script.onreadystatechange = function () {
	            if ((!this.readyState || this.readyState === "loaded" || this.readyState === "complete")) {
	                callback && callback();
	                window['isBaiduMapJsLoaded'] = true;
	                script.onload = script.onreadystatechange = null;
	            }
	        };
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
							//组装省份数据
							if(evt.__location__.propertyProvince || evt.__location__.propertyCity || evt.__location__.propertyDistrict){
								 map.getBMapLocation(
								          {
								            coordType: 3,
								            lat: evt.point.lat,
								            lng: evt.point.lng
								          },
								          function(result) {
								            evt.province=result.addressComponent.province;
								            evt.city=result.addressComponent.city;
								            evt.district=result.addressComponent.district;
								            topic.publish('sys/attend/map/edit.finished',evt,evt.__location__);
								          },
								          function() {
								        	  Tip.fail({
								                  text: "地图加载过程失败,请重试!"
								                });
								          }
								        );
							}else{
								topic.publish('sys/attend/map/edit.finished',evt,evt.__location__);
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
				lang.mixin(evt,{ value : widget.value || widget.nameNode.value });
				if(widget.coordinateValue){
					var coordValue = widget.coordinateValue.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
					var coordType = MapUtil.getCoordType(widget.coordinateValue);
					lang.mixin(evt,{
						location : {
							lat : coordValue[0],
							lng : coordValue[1],
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
		
	};

});