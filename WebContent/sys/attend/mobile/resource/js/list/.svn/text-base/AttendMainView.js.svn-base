define(['dojo/_base/declare','dojox/mobile/View',"dojo/dom-construct","mui/util","dojox/mobile/viewRegistry",'dojo/date/locale',
        "dojo/topic","dojo/_base/lang","mui/i18n/i18n!sys-mobile","dojo/io-query","dojo/request","dojo/query","dijit/registry",
        'sys/attend/mobile/resource/js/list/LocationDialog',"sys/attend/map/mobile/resource/js/common/MapUtil"], 
		function(declare,View,domConstruct,util,viewRegistry,locale,topic,lang,muiMsg,ioq,request,query,registry,LocationDialog,MapUtil) {
	
	return declare('sys.attend.AttendDetailView', [View], {
		
		postMixInProperties : function() {
			this.inherited(arguments);
			util.loadCSS('/sys/attend/map/mobile/resource/css/location.css');
		},
		
		startup : function() {
			this.inherited(arguments);
			this._request();
		},
		
		_request : function() {
			var attendMainId = query('input[name="attendMainId"]')[0].value;
			var attendType = query('input[name="attendType"]')[0].value;
			var attendFdType = query('input[name="attendFdType"]')[0].value;
			var creatorId = query('input[name="creatorId"]')[0].value;
			var time = query('input[name="time"]')[0].value;
			if(undefined==attendType||"undefined"==attendType||""==attendType){
				attendType="1";
			}
			var options = {
					handleAs : 'json',
					method : 'POST',
					data : ioq.objectToQuery({
						fdAttendMainId : attendMainId || '',
						fdAttendType : attendType || '',
						fdAttendFdType:attendFdType||'',
						fdcreatorId:creatorId,
						fdtime:time
					})	
				};
			var url = util.formatUrl(this.url);
			request(url,options).then(lang.hitch(this,function(result){
				if(result && result.datas.length>0){
					var datas = this.formatData(result.datas);
					var coords = [];
					for(var idx in datas){
						var point = {};
						point.fdLng = datas[idx].fdLng;
						point.fdLat = datas[idx].fdLat;
						point.coordType = MapUtil.getCoordType(datas[idx].fdLatLng);
						coords.push(point);
					}
					this.openLocationDialog(coords,datas);
				} else {
					this.openLocationDialog();
				}
			}),lang.hitch(this,function(err){
				this.openLocationDialog();
			}));
		},
		
		openLocationDialog : function(coords,datas) {
			var self = this;
			this._loadScript(function(){
				self.openMapDialog(coords,datas);
			});
		},
		openMapDialog : function(coords,datas){
			var self = this;
			var cateNameDom = query(".attendMainView span.muiSignCateName")[0];
			var dateObj = registry.byId('mainDate');
			
			if(datas && datas.length > 0) {
				var data = datas[0];
				cateNameDom.innerHTML = data.fdCateName;
				dateObj.set('value', data.fdSignedDate);
			} else {
				cateNameDom.innerHTML = '';
				dateObj.set('value', '');
			}
			
			if(!window['muiLocationDialog']){
				var dialog = window['muiLocationDialog'] = new LocationDialog({
					showStatus : 'view',
					isShowList : true,
					isShowSearch:false,
				});
				dialog.startup();
				domConstruct.place(dialog.domNode,document.body,'last');
			}
			var evt = {
				showStatus : 'view',
				datas:coords
			};
			window['muiLocationDialog'].show(evt);
			topic.publish('/attend/location/info/datas', window['muiLocationDialog'], datas);
		},
		_loadScript:function(callback){
			if(window.BMap){
				if(callback)
            		callback();
				return;
			}
			var script = document.createElement('script');
	        script.type = 'text/javascript';
	        var key = dojoConfig.map.bMapKey || MapUtil.bMapKey;
	        script.src = "https://api.map.baidu.com/api?v=2.0&ak=" + key + "&s=1&callback=bInitCallback";
	        window.bInitCallback = function(){
	        	callback && callback();
	        };
	        document.body.appendChild(script);
		},
		formatData : function(datas){
			var data = [];
			for(var i = 0 ; i < datas.length;i++){
				var records = datas[i];
				var record = {};
				for(var idx in records){
					record[records[idx].col] = records[idx].value;
				}
				data.push(record);
			}
			return data;
		},
		
	});
});