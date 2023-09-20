define(['dojo/_base/declare','dojox/mobile/View',"dojo/dom-construct","mui/util","dojox/mobile/viewRegistry",'dojo/date/locale',
        "dojo/topic","dojo/_base/lang","mui/i18n/i18n!sys-mobile","dojo/io-query","dojo/request","dojo/query",
        'sys/attend/mobile/resource/js/stat/SignTrailLocationDialog',"sys/attend/map/mobile/resource/js/common/MapUtil"], 
		function(declare,View,domConstruct,util,viewRegistry,locale,topic,lang,muiMsg,ioq,request,query,SignTrailLocationDialog,MapUtil) {

	return declare('sys.attend.SignTrailView', [View], {

		startup : function() {
			this.inherited(arguments);
			util.loadCSS('/sys/attend/map/mobile/resource/css/location.css');
			if(!window['muiSignTrailDialogDateEventInit']){
				window['muiSignTrailDialogDateEventInit'] = true;
				topic.subscribe('/mui/form/datetime/change',lang.hitch(this,'__onSignTrailDateChange'));
			}
		},
		__onSignTrailDateChange : function(obj,evt){
			if(obj.id=='signTrail_statDate'){
				var statDate = locale.parse(obj.get('value')+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
				var userId = query('input[name="userId"]')[0].value;
				var fdCategoryId = query('input[name="fdCategoryId"]')[0].value;
				var mydoc = query('input[name="mydoc"]')[0].value;
				var options = {
						handleAs : 'json',
						method : 'POST',
						data : ioq.objectToQuery({
							fdDate : statDate.getTime(),
							fdCategoryId:fdCategoryId,
							docCreatorId:userId,
							mydoc : mydoc
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
						this.openSignTrailLocationDialog(coords,datas);
					}else{
						this.openSignTrailLocationDialog([]);
					}
				}),lang.hitch(this,function(err){
					this.openSignTrailLocationDialog([]);
				}));
			}
		},
		formatData:function(datas){
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
		
		openSignTrailLocationDialog : function(coords,datas){
			var self = this;
			this._loadScript(function(){
				self.openMapDialog(coords,datas);
			});
		},
		openMapDialog : function(coords,datas){
			var self = this;
			if(!window['muiSignTrailLocationDialog']){
				var dialog = window['muiSignTrailLocationDialog'] = new SignTrailLocationDialog({
					showStatus : 'view',
					isShowList : false,
					isShowSearch:false
				});
				dialog.startup();
				domConstruct.place(dialog.domNode,document.body,'last');
			}
			var evt = {
				showStatus : 'view',
				datas:coords
			};
			window['muiSignTrailLocationDialog'].show(evt);
			topic.publish('/signTrail/stat/person/datas',this,datas);
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
		}

	});
	
	
});