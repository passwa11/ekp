define(['dojo/_base/declare','dojox/mobile/View',"dojo/dom-construct","mui/util",
		"dojo/topic","dojo/_base/lang","dojo/query",
		"sys/attend/map/mobile/resource/js/common/MapUtil","./mapStrategy"],
	function(declare,View,domConstruct,util,topic,lang,query,MapUtil,mapStrategy) {
		//外勤打卡界面
		return declare('sys.attend.AttendMapView', [View], {
			strategy : 'bmap',//默认使用百度地图
			mapKey : MapUtil.bMapKey,
			mapKeyPc: null,
			mapKeyName:null,
			mapKeyPcSecurityKey:null,
			postMixInProperties : function(){
				var mapType = dojoConfig.map.mapType;
				if(mapType=='qmap'){
					this.strategy = 'qmap';
					this.mapKey = dojoConfig.map.qMapKey;
					this.mapKeyName = dojoConfig.map.qMapKeyName
				}else if(mapType=='bmap'){
					this.strategy = 'bmap';
					this.mapKey = dojoConfig.map.bMapKey;
				}else if(mapType=='amap'){
					this.strategy = 'amap';
					this.mapKey = dojoConfig.map.aMapKey;
					this.mapKeyPc = dojoConfig.map.aMapKeyPc;
					this.mapKeyPcSecurityKey =dojoConfig.map.aMapKeyPcSecurityKey;
				}
			},

			startup : function() {
				this.inherited(arguments);
				this.openAttendMapDialog();
			},
			openAttendMapDialog : function(){

				var _strategy = mapStrategy[this.strategy];
				var option = {
					id:"t_"+new Date().getTime(),
					mapKey:this.mapKey,
					mapKeyPc: this.mapKeyPc,
					mapKeyName: this.mapKeyName,
					fdLocations:this.fdLocations,
					fdType:this.fdType,
					fdLimit:this.fdLimit,
					mapKeyPcSecurityKey:this.mapKeyPcSecurityKey
				};
				if(_strategy && _strategy.init){
					_strategy.init(option);
				}
			}
		});
	});