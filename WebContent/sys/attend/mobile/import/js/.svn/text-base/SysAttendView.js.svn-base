define(['dojo/_base/declare','dijit/_WidgetBase','dijit/_TemplatedMixin','dojo/dom-attr',
		'dojo/text!./SysAttendViewNew.tmpl','dojo/text!./SysAttendListView.tmpl','dojo/date/locale','dojo/date','dojo/parser',
		'dojo/_base/lang','dojo/ready','dojo/request','mui/device/adapter','dojo/query',"dojo/_base/array",'dojox/mobile/TransitionEvent',"dojo/touch",
		'dojo/dom-class','dojo/dom-construct','dojo/dom-style','dojo/io-query','mui/util',
		'mui/i18n/i18n!sys-mobile','mui/i18n/i18n!sys-attend:mui','mui/i18n/i18n!sys-attend:sysAttendCategory','mui/dialog/Tip',"mui/coordtransform",'sys/attend/map/mobile/resource/js/common/MapUtil'], 
		function(declare, WidgetBase, _TemplatedMixin,domAttr, templateString, listTemplateString , locale, dojodate, parser,lang, 
				ready, request, adapter,query,array ,TransitionEvent,touch,domClass, domConstruct , domStyle,ioq,util,muiMsg,Msg,CateMsg,Tip,coordtransform,MapUtil) {

	/**
	 * 几个数字说明:
	 * 	0 : 签到未开始
	 * 	1 : 签到进行中
	 * 	2 : 签到已结束
	 *  3 : 未签到
	 *  4 : 已签到 
	 */
	return declare('sys.attend.import.SysAttendView', [WidgetBase], {

		templateString : null,
		
		appId : null,
		
		fdModelName : null,
		
		canAttend : true,
		
		canFinishAttend : true,
		
		startup: function(){
			this.inherited(arguments);	
			//签到组件通常不会出现在首屏,延缓加载
			ready(9999,lang.hitch(this,function(){
				var urloptions ={
					handleAs : 'json',
					method : 'POST',
					data : ioq.objectToQuery({
						appId : this.appId
					})
				};
				
				var urlcategory = util.formatUrl('/sys/attend/sys_attend_category/sysAttendCategory.do?method=viewdata');
				var promise2 = request(urlcategory,urloptions);
				promise2.then(lang.hitch(this,function(categories){
					if(categories && categories.length>0){
						for(var i = 0 ;i < categories.length;i++){
							var category = categories[i];
							//多语言
							var _templateString = lang.replace(templateString,{sysAttendViewItem:category.fdId,
								topic11:CateMsg['sysAttendCategory.sysAttendview.topic11'],
								topic12:CateMsg['sysAttendCategory.sysAttendview.topic12'],
								topic13:CateMsg['sysAttendCategory.sysAttendview.topic13'],
								topic14:CateMsg['sysAttendCategory.sysAttendview.topic14'],
								topic15:CateMsg['sysAttendCategory.sysAttendview.topic15'],
								topic16:CateMsg['sysAttendCategory.sysAttendview.topic16'],
								topic17:CateMsg['sysAttendCategory.sysAttendview.topic17'],
								topic18:CateMsg['sysAttendCategory.sysAttendview.topic18'],
								topic19:CateMsg['sysAttendCategory.sysAttendview.topic19'],
								topic2:CateMsg['sysAttendCategory.sysAttendview.topic2']
								});
							var itemNode = domConstruct.toDom(_templateString);
							domConstruct.place(itemNode, this.domNode,'before');
							
							category.categoryStatus = category.fdStatus;
							category.canAttend = true;
							if(!category.isfdAttender){
								category.canAttend = false;
							}
							category.canFinishAttend = true;
							if(!category.isfdManager){
								category.canFinishAttend = false;
							}
							//签到时间
							this.renderTime(category);
							//查看二维码
							this.renderQRCode(category);
							//签到按钮
							this.renderSignButton(category);
							//结束按钮
							this.renderFinishAttendButton(category);
						}
						var urlstat = util.formatUrl('/sys/attend/sys_attend_category/sysAttendCategory.do?method=stat');
						var promise1 = request(urlstat,urloptions);
						promise1.then(lang.hitch(this,function(stats){
							if(stats && stats.length>0){
								for(var k = 0;k < stats.length;k++){
									stat = stats[k];
									//签到统计
									this.renderStat(stat);
									//签到记录列表
									if(stat.isStatSignReader){
										this.renderAttendList(stat);
									} else {
										domConstruct.destroy(query('#item_'+stat.fdId + ' .sysAttendListContainer')[0]);
									}
								}
							}
						}));
						
					}else{
						domStyle.set(this.domNode,'display','none');
						domConstruct.create('div',{className : 'sysAttendViewNodata'},this.domNode,'after');
					}
					
					
					
				}));
				
			}));
		},
		
		renderTime : function(category){
			var domStartTime = query('#item_'+category.fdId + ' .sysAttendStartTimeContainer .sysAttendTime')[0];
			var domEndTime = query('#item_'+category.fdId + ' .sysAttendEndTimeContainer .sysAttendTime')[0];
			var domAttendDate = query('#item_'+category.fdId + ' .sysAttendStatusContainer .sysAttendDate')[0];
			var domStatus = query('#item_'+category.fdId + ' .sysAttendStatusContainer .sysAttendStatus')[0];
			var sobj = { '0' : 'unStart' , '1' : 'doing' , '2' : 'finish' , '3' : 'unAttend' , '4' : 'hasAttend' };
			
			domStartTime.innerHTML = category.fdStartTime;
			domEndTime.innerHTML = category.fdEndTime;
			if(category.fdTimes && category.fdTimes[0] && category.fdTimes[0].indexOf("00:00") == -1){
				domAttendDate.innerHTML =  category.fdTimes[0]+" "+CateMsg['sysAttendCategoryRule.lateTime.text'];
			}
			domClass.add(domStatus,sobj[category.fdStatus]);
			domStatus.innerHTML = Msg['mui.attend.status.' + sobj[category.fdStatus]];
		},
		
		renderQRCode : function(category){
			var domQRCode = query('#item_'+category.fdId + ' .sysAttendQRcodeContainer')[0];
			if(category.isfdManager){
				if(category.fdQRCodeUrl){
					this.connect(domQRCode,touch.press,function(){
						var url = util.formatUrl('/sys/attend/mobile/import/view_qrcode.jsp?qrcodeurl=' + encodeURIComponent(category.fdQRCodeUrl));
						adapter.open(url,"_self");
					});
				}
			}else{
				domConstruct.destroy(domQRCode);
			}
		},
		
		renderSignButton : function(category) {
			
			var url = util.formatUrl('/sys/attend/sys_attend_main/sysAttendMain.do?method=list');
			var promise = request(url,{
				handleAs : 'json',
				method : 'POST',
				data : ioq.objectToQuery({
					fdCategoryId : category.fdId,
					me : 'true'
				})
			});
			
			promise.then(lang.hitch(this, function(result){
				var datas = this._formatDatas(result.datas);
				
				if(datas && datas.length > 0){
					var data = datas[0];
					if(data._fdStatus == '0'){
						this.genSignDom(category, data);
					}else if(data._fdStatus == '1' || data._fdStatus == '2'){
						this.genSignContentDom(category, data);
					}
				} else {
					this.genSignDom(category, null);
				}
			}));
		},
		
		genSignDom : function(category, data) {
			var signContainer = query('#item_'+category.fdId + ' .sysAttendMainContainer')[0];
			
			// 签到未开始或已结束
			var fdTime = category.fdTimes[0],
			fdDateTime = locale.parse(fdTime,{
				selector : 'date',
				datePattern : dojoConfig.DateTime_format
			}),
			fdDateTimeDate = locale.format(fdDateTime,{selector : 'date',datePattern : dojoConfig.Date_format }),
			startTime = locale.parse(fdDateTimeDate + ' ' + category.fdStartTime,{
				selector : 'date',
				datePattern : dojoConfig.DateTime_format
			}),
			endTime =  locale.parse(fdDateTimeDate + ' ' + category.fdEndTime,{
				selector : 'date',
				datePattern : dojoConfig.DateTime_format
			});
			if(dojodate.compare(startTime,new Date()) > 0){//未开始
				var signBtnNode = domConstruct.create("a",{id:'signBtnNode_'+ category.fdId, className:"sysAttendBtn disbale"}, signContainer);
				var signBtnTextNode = domConstruct.create("span",{id:'signBtnTextNode_'+ category.fdId, className:"signName",innerHTML:CateMsg['sysAttendCategory.sysAttendview.topic1']},signBtnNode);
				
				//var signLocNode = domConstruct.create("p",{id:'signLocNode_'+ category.fdId, className:"sysAttendTxt",innerHTML:'签到还未开始！'},signContainer);
				return;
			}else if(dojodate.compare(new Date(),endTime) > 0 || category.fdStatus==2){//已结束
				var signBtnNode = domConstruct.create("a",{id:'signBtnNode_'+ category.fdId, className:"sysAttendBtn disbale"}, signContainer);
				var signBtnTextNode = domConstruct.create("span",{id:'signBtnTextNode_'+ category.fdId, className:"signName",innerHTML:CateMsg['sysAttendCategory.sysAttendview.topic2']},signBtnNode);
				
				//var signLocNode = domConstruct.create("p",{id:'signLocNode_'+ category.fdId, className:"sysAttendTxt",innerHTML:'签到已结束！'},signContainer);
				category.categoryStatus = '2';
				return;
			}
			
			//是否仅扫码签到
			var classN="sysAttendBtn";
			if(category.fdPermState==1){
				classN+=" disbale";
			}
			
			// 签到按钮
			var signBtnNode = domConstruct.create("a",{id:'signBtnNode_'+ category.fdId, className:classN}, signContainer);
			var currentTimeDom = domConstruct.create("span",{id:'currentTimeDom_'+ category.fdId, className:"currentTime",innerHTML:''},signBtnNode);
			var signBtnTextNode = domConstruct.create("span",{id:'signBtnTextNode_'+ category.fdId, className:"signName",innerHTML:Msg['mui.qiandao.Title']},signBtnNode);
			
			this.signTimeCounter(category);
			this.connect(signBtnNode, touch.press, lang.hitch(this,function(){
				this._onSignClick(category);
			}));
			//仅扫码签到，友情提示
			if(category.fdPermState==1){
				var signLocNode = domConstruct.create("p",{id:'signLocNode_'+ category.fdId, className:"",innerHTML:''},signContainer);
				var locationTextNode = domConstruct.create("span",{id:'locationTextNode_'+ category.fdId, className:"muiSignInLocation",innerHTML:Msg['mui.PermState.Please']},signLocNode);
				
			}else{
				// 签到按钮下方
				var signLocNode = domConstruct.create("p",{id:'signLocNode_'+ category.fdId, className:"sysAttendTxt",innerHTML:''},signContainer);
				var signIconNode = domConstruct.create("i",{id:'signIconNode_'+ category.fdId, className:"mui mui-location",innerHTML:''},signLocNode);
				var locationTextNode = domConstruct.create("span",{id:'locationTextNode_'+ category.fdId, className:"muiSignInLocation",innerHTML:''},signLocNode);
				var locationValueNode = domConstruct.create('input',{id:'locationValueNode_'+ category.fdId, type:'hidden'},signLocNode);
				var limitNode = domConstruct.create('span',{id:'limitNode_'+ category.fdId, className:'muiSignInLimit',innerHTML:''},signLocNode);
				var isRangeNode = domConstruct.create('input',{id:'isRangeNode_'+ category.fdId, type:'hidden'},signLocNode);
				var signStatusNode = domConstruct.create('input',{id:'signStatusNode_'+ category.fdId, type:'hidden'},signLocNode);
				
				var resetBtnNode = domConstruct.create('a',{id:'resetBtnNode_'+ category.fdId, className:'refashSingIn',href:"javascript:void(0)",innerHTML:Msg['mui.dingwei.Title']},signContainer);
				this.connect(resetBtnNode, touch.press, lang.hitch(this,function(){
					this._onResetLocationClick(category);
				}));
				
				var self = this;
				adapter.mixinReady(function(){
					self.refreshLocation(category);
				});
			}
			//实时刷新时间
			setInterval(lang.hitch(this,function(){
				this.signTimeCounter(category);
			}),1000);
		},
		
		signTimeCounter : function(category) {
			var date = new Date();
			var currentTimeDom = query('#currentTimeDom_' + category.fdId)[0];
			currentTimeDom.innerHTML=this.timeFormat(date.getHours())+':' + this.timeFormat(date.getMinutes())+":" + this.timeFormat(date.getSeconds());
		},
		timeFormat : function(v){
			return v >= 10 ? v : "0" + v;
		},
		
		refreshLocation : function (category) {
			var self = this;
			
			var locationValueNode = query('#locationValueNode_'+ category.fdId)[0];
			var locationTextNode = query('#locationTextNode_'+ category.fdId)[0];
			var limitNode = query('#limitNode_'+ category.fdId)[0];
			var isRangeNode = query('#isRangeNode_'+ category.fdId)[0];
			
			locationValueNode.value = '';
			locationTextNode.innerHTML = '';
			limitNode.innerHTML = '';
			domClass.remove(limitNode,"unLimit");
			isRangeNode.value = false;
			
			adapter.getCurrentPosition(function(result){
				var coordType = result.coordType;
				var address = result.address;
				var point = result.point;
				var pois = result.pois;
				
				var currentLatLng = self.formatCoord(point,coordType);
				locationValueNode.value= currentLatLng;
				
				var fdLocations = eval(category.fdLocations);
				var fdLimit = category.fdLimit;
				
				if(fdLocations && fdLocations.length>0 && !!fdLimit){
					
					var isOk = false;
					var range = fdLimit;
					
					for(var i = 0; i< fdLocations.length;i++){
						var distance = MapUtil.getDistance(currentLatLng, fdLocations[i].coord);
						if(distance <= range){
							isOk = true;
							locationTextNode.innerHTML = fdLocations[i].address;
							break;
						}
					}
					
					if(!isOk){
						locationTextNode.innerHTML = address;
						limitNode.innerHTML = CateMsg['sysAttendCategory.sysAttendview.topic3'];
						domClass.add(limitNode,"unLimit");
						isRangeNode.value = false;
					} else {
						limitNode.innerHTML =CateMsg['sysAttendCategory.sysAttendview.topic4'];
						domClass.remove(limitNode,"unLimit");
						isRangeNode.value = true;
					}
				} else {
					locationTextNode.innerHTML = address;
					limitNode.innerHTML = '';
					domClass.remove(limitNode,"unLimit");
					isRangeNode.value = true;
				}
			},function(){
				locationTextNode.innerHTML=CateMsg['sysAttendCategory.sysAttendview.topic5'];
			});
		},
		
		_onSignClick : function (category) {
			var signBtnNode = query('#signBtnNode_'+ category.fdId)[0];
			var isRangeNode = query('#isRangeNode_'+ category.fdId)[0];
			var locationValueNode = query('#locationValueNode_'+ category.fdId)[0];
			var locationTextNode = query('#locationTextNode_'+ category.fdId)[0];
			
			var fdLocation = locationTextNode.innerHTML || '';
			var fdLatLng = locationValueNode.value || '';
			
			if(domAttr.has(signBtnNode, "disabled")){
				return;
			}
			
			if(!category.canAttend){
				Tip.fail({
					text:CateMsg['sysAttendCategory.sysAttendview.topic6']
				});
				return;
			}
			
			if(isRangeNode.value != 'true'){
				Tip.fail({
					text:CateMsg['sysAttendCategory.sysAttendview.topic7']
				});
				return;
			}
			
			var data = {
				fdLocation : fdLocation,
				fdLatLng : fdLatLng,
				categoryId : category.fdId,
			}
			
			var self = this;
			var url = "/sys/attend/sys_attend_main/sysAttendMain.do?method=updateByExt";
			domAttr.set(signBtnNode, "disabled", "disabled");
			var proc = Tip.processing();
			proc.show();
			request(util.formatUrl(url), {
				handleAs : 'json',
				method : 'post',
				data : data
			}).then(function(result){
				proc.hide();
				if(result.status==1){
					Tip.success({
						text : CateMsg['sysAttendCategory.sysAttendview.topic8'],
						callback: function(){
							location.href = util.setUrlParameter(location.href);
						}
					});
				}else{
					Tip.fail({
						text : result.message,
						callback: function(){
							location.href = util.setUrlParameter(location.href);
						}
					});
				}
				
			},function(e){
				proc.hide();
				domAttr.remove(self.signBtnNode, "disabled");
				window.console.log("error:" + e);
			});
			
			
		},
		
		formatCoord : function(point,coordType){
			var prefix = "bd09:";
			if(coordType==5){
				prefix = "gcj02:";
			}
			var currentLatLng = prefix +(point.lat+"," + point.lng);
			return currentLatLng;
		},
		
		_onResetLocationClick : function (category) {
			this.refreshLocation(category);
		},
		
		genSignContentDom : function(category, data) {
			var signContainer = query('#item_'+category.fdId + ' .sysAttendMainContainer')[0];
			
			var signBtnNode = domConstruct.create("div",{id:'signBtnNode_'+ category.fdId, className:"mui_meetingSign_success"}, signContainer);
			domConstruct.create("i",{id:'signBtnTextNode_'+ category.fdId, className:"mui mui-pitchon"},signBtnNode);
			
			var signedTimeText =CateMsg['sysAttendCategory.sysAttendview.topic9'] + data.docCreateTime;
			var signedTimeTextNode = domConstruct.create("p",{id:'signedTimeTextNode_'+ category.fdId, className:"sysAttendTxt",innerHTML:signedTimeText},signContainer);
			if(data.fdLocation) {
				var signedLocText = CateMsg['sysAttendCategory.sysAttendview.topic10'] + data.fdLocation;
				var signedLocTextNode = domConstruct.create("p",{id:'signedLocTextNode_'+ category.fdId, className:"sysAttendTxt",innerHTML:signedLocText},signContainer);
			}
			return;
		},
		
		renderFinishAttendButton : function(category){
			var domFinishAttend = query('#item_'+category.fdId + ' .sysAttendBtnContainer .sysAttendBtn')[0];
			// 是否有权限结束，签到组是否已结束
			if(category.canFinishAttend && category.categoryStatus != '2'){
				this.connect(domFinishAttend,'click',lang.hitch(this,function(){
					var url = util.formatUrl('/sys/attend/sys_attend_category/sysAttendCategory.do?method=updateStatus');
					url += '&fdId=' + category.fdId;
					var promise = request(url,{
						handleAs : 'json',
						headers: {'Accept': 'application/json'},
						method : 'GET'
					});
					promise.then(function(){
						Tip.success({
							text : CateMsg['sysAttendCategory.sysAttendview.topic2'],
							callback : function(){ location.reload(); }
						});
						
					});
				}));
			}else{
				domConstruct.destroy(domFinishAttend);
			}
		},
		
		_formatDatas : function(datas) {
			var dataed = [];
			for (var i = 0; i < datas.length; i++) {
				var datasi = datas[i];
				dataed[i] = {};
				for (var j = 0; j < datasi.length; j++) {
					dataed[i][datasi[j].col] = datasi[j].value;
				}
			}
			return dataed;
		},
		
		renderStat : function(stat){
			var domStatCount = query('#item_'+stat.fdId + ' .domStatCount')[0];
			var domStatDo = query('#item_'+stat.fdId + ' .domStatDo')[0];
			var domStatUndo = query('#item_'+stat.fdId + ' .domStatUndo')[0];
			var domStatNoraml = query('#item_'+stat.fdId + ' .domStatNoraml')[0];
			var domStatLate = query('#item_'+stat.fdId + ' .domStatLate')[0];
			
			domStatCount.innerHTML = stat.count;
			domStatDo.innerHTML = stat.attendcount;
			domStatUndo.innerHTML  = stat.unattendcount;
			domStatNoraml.innerHTML = stat.normalcount;
			domStatLate.innerHTML = stat.latecount;
		},
		
		renderAttendList : function(stat){
			var self = this;
			var domAttendList = query('#item_'+stat.fdId + ' .sysAttendListContainer')[0];
			this.connect(domAttendList,touch.press,lang.hitch(this,function(){
				/*if(window._sysAttendListView_){
					window._sysAttendListView_.destroy();
				}
					
				var _listTemplateString = lang.replace(listTemplateString,{
					url : '/sys/attend/sys_attend_main/sysAttendMain.do?method=list&operType=0&appId=' + this.appId +'&fdCategoryId='+stat.fdId,
					attendcount: stat.attendcount,
					unattendcount : stat.unattendcount
				});
				parser.parse(domConstruct.create('div',{ innerHTML:_listTemplateString },query('#content')[0] ,'last'))
					.then(function(widgetList) {
					array.forEach(widgetList, function(widget, index) {
						if(index == 0){
							self.afterAttendListParse(widget);
							window._sysAttendListView_ = widget;
						}
					});
					var opts = {
						transition : 'slide',
						moveTo : window._sysAttendListView_.id
					};
					new TransitionEvent(document.body ,  opts ).dispatch();
				});*/
				var url = util.formatUrl('/sys/attend/mobile/import/list_sign.jsp');
				url = util.setUrlParameterMap(url, {
					appId : this.appId,
					categoryId : stat.fdId,
				});
				adapter.open(url,"_self");
			}));
		},
		
		afterAttendListParse : function(widget){
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
			var backView=widget.getShowingView();
			this.connect(query('.sysAttendHeaderReturn',widget.domNode)[0],'click',lang.hitch(this,function(){
				var opts = {
					transition : 'slide',
					moveTo:backView.id,
					transitionDir:-1
				};
				new TransitionEvent(document.body,  opts ).dispatch();
			}));
		}

	});
});