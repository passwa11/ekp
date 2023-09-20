define(	["dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class","dojox/mobile/TransitionEvent",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase","dojo/touch",
				"dojo/dom", "mui/util", "dojo/_base/lang","mui/device/adapter","mui/device/device",
				"dojo/request","dojo/query","dojo/topic","dojox/mobile/viewRegistry",
				"mui/dialog/Tip","dijit/registry","sys/attend/map/mobile/resource/js/common/MapUtil",
				"mui/dialog/Confirm","dojo/json","dojo/date/locale","dojo/Deferred","mui/coordtransform","sys/attend/mobile/resource/js/attachment/attachment","mui/i18n/i18n!sys-attend:*"],
		function(declare, domConstruct, domClass,TransitionEvent, domStyle, domAttr, ItemBase,touch,
				dom, util,lang,adapter,device, request,query,topic,viewRegistry, Tip,registry,MapUtil,Confirm,Json,locale,Deferred,coordtransform,attachmentUtil,Msg) {
			var item = declare("sys.attend.item.AttendItemMixin", [ItemBase], {

						tag : "li",
						
						// big things have small beginnings
						buildRendering : function() {
							//休息日标识
							this.isRestDay = query('#isRestDay')[0].value;
							// 此刻是否跨天打卡
							this.isCurrentAcross = query('#isAcrossDay')[0].value;
							if(this.fdType==1){
								//当前用户设备号
								this.fdDeviceIds = query('#fdDeviceIds')[0].value;
							}
							
							this.domNode = domConstruct.create('li', {className : ''}, this.containerNode);
							this.inherited(arguments);
							var now = new Date();
							now.setTime(this.nowTime);
							this.nowDateTime = now;
							var tempFdWorkDateEle =query('#fdWorkDate');
							if(tempFdWorkDateEle) {
								this.fdWorkDate = tempFdWorkDateEle[0].value;
							}
							this.buildInternalRender();
							
						},
						//刷新整个页面
						refreshPage:function(){
							//判断是否真正跨天
							var tmpFdWorkDate = parseInt(this.fdWorkDate);
							var workDate = new Date(tmpFdWorkDate)
							workDate.setHours(23);
							workDate.setMinutes(59);
							workDate.setSeconds(59);
							workDate.setMilliseconds(999);
							var now = this.nowDateTime;
							var isDateAcross = now.getTime()>workDate.getTime();
							if(this.isAcrossDay == 'true'){
								if(this.isCurrentAcross=='false' && isDateAcross){
									location.reload();
									return true;
								}
							}
							
							return false;
						},
						
						getDbTimeMillis : function() {
							var self =this;
							var url = "/sys/attend/sys_attend_main/sysAttendMain.do?method=getDbTimeMillis&forward=lui-source";
							request(util.formatUrl(url), {
								handleAs : 'json',
								method : 'post',
								data : ''
							}).then(function(result){
								if (result.nowTime) {
									self.nowDateTime.setTime(result.nowTime);
								}
							},function(e){
								window.console.log("获取数据失败,error:" + e);
							});
						},
						buildInternalRender : function() {
							if(query('.muiSignedRecords').length>0){
								domConstruct.destroy(query('.muiSignedRecords')[0]);
							}
							if(this.fdType==2){
								this.buildSignRender();
								return;
							}
							//休息日提示
							if(this.fdSigned){
								var restDayContentNode = query('.restDayContent');
								if(restDayContentNode.length>0){
									domStyle.set(restDayContentNode[0],'display','none');
								}
							}
							//考勤通用配置
							if(this.attendCfgJson){
								this.attendCfgJson = JSON.parse(this.attendCfgJson);
							}

							var fdWorkTxt = this.fdWorkType=='1'? Msg['mui.off'] :Msg['mui.on'];
							domConstruct.create("span",{className:"flowListPostPoint hasTxt",innerHTML:''},this.domNode);
							this.genTitleDom();
							
							var now = this.nowDateTime;
							var nowMins = this.getNowMins();//now.getHours() *60 + now.getMinutes();

							if(this.fdSigned && !this.isAttendStatusBuss()|| (this.fdSigned && this.isAttendedByBuss())){
								this.genSignContentDom();
							}else if(this.isRestDay=='true'){
								if(this.isAcrossDay == 'true' &&  0 <= nowMins && nowMins <= this._fdEndTime && this.isCurrentAcross == 'true') {
									this.fdIsAcross = true;
								}
								this.genSignDom();
							}else{
								if(this.isAcrossDay == 'true') {
									if(this.isCurrentAcross == 'true') {
										if(this.nextSignTime) {
											domConstruct.create("span",{className:"muiSignInLabel signInLabelDanger",innerHTML:Msg['mui.fdStatus.unSign']},this.head);
											this.genSignContentDom();
										} else {
											this.fdIsAcross = true;
											this.genSignDom();
										}
									} else {
										this.fdIsAcross = false;
										if(nowMins >= this._fdStartTime) {
											this.genSignOrMissedDom();
										} else {
											this.genDisableSignDom();
										}
									}
								} else {
									if(nowMins >= this._fdStartTime && nowMins <= this._fdEndTime){ //允许打卡区间
										this.genSignOrMissedDom();
									} else if (0 < nowMins && nowMins < this._fdStartTime) {
										this.genDisableSignDom();
									} else if (nowMins > this._fdEndTime) {
										domConstruct.create("span",{className:"muiSignInLabel signInLabelDanger",innerHTML:Msg['mui.fdStatus.unSign']},this.head);
										this.genSignContentDom();
									}
								}
							}
						},
						//判断是否出差/请假/外出
						isAttendStatusBuss : function(){
							if(this.fdSignedStatus=='4' || this.fdSignedStatus =='5' || this.fdSignedStatus =='6'){
								return true;
							}
							return false;
						},
						//判断出差/请假/外出是否已经打过卡
						isAttendedByBuss : function(){
							if(this.fdSignedStatus=='4' || this.fdSignedStatus =='5' || this.fdSignedStatus =='6'){
								if(this.fdSignedLocation || this.fdSignedWifi || this.fdAppName){
									return true;
								}
								return false;
							}
							return false;
						},
						//签到组打卡
						buildSignRender : function(){
							//var now = this.nowDateTime;
							var nowMins = this.getNowMins();//now.getHours() *60 + now.getMinutes();
							var records = eval(this.records);
							if(nowMins>=this._fdStartTime && nowMins<=this._fdEndTime){
								this.genSignDom();
								if(records.length>0){
									domConstruct.create("div",{className:"muiSignedRecordsBorder"},query('.muiSignInPanel')[0]);
									this.recordContentNode = domConstruct.create("div",{className:"muiSignedRecords"},query('.muiSignInPanel')[0]);
									this.recordUlNode = domConstruct.create("ul",{className:"muiSignedRecordList"},this.recordContentNode,'first');
								}else{
									domClass.add(query('.muiSignInPanel')[0],'muiSignNoRecordPanel');
								}
								for(var r in records){
									var record = records[r];
									var liNode = domConstruct.create("li",{className:""},this.recordUlNode);
									domConstruct.create("span",{className:"muiSignedTitlePoint"},liNode);
									var titleNode = domConstruct.create("div",{className:"muiSignedTitle"},liNode);
									
									var signedTime = locale.format(new Date(Number(record.fdSignedTime)),{selector : 'time',timePattern : 'HH:mm' });
									domConstruct.create("span",{className:"time",innerHTML:signedTime},titleNode);
									domConstruct.create("span",{className:"info",innerHTML:Msg['mui.sign']},titleNode);
									
									var locNode = domConstruct.create("div",{className:"muiSignedLoction"},liNode);
									var item = domConstruct.create("div",{className:"item",innerHTML:''},locNode);
									if(record.fdSignedLocation){
										var p = domConstruct.toDom("<p class='muiSignedAddress'><i class='mui fontmuis muis-position'></i>" + record.fdSignedLocation + "</p>");
										domConstruct.place(p, item);
									}
							   }
							}else{
								if(records.length>0){
									
								}else{
									this.buildSignNoData();
								}
							}
						},
						//是否排班休息日
						isTimeAreaRestDay : function(){
							if(this.fdType==1 && this.isRestDay=='true' && this.fdShiftType=='1'){
								return true;
							}
							return false;
						},
						
						genSignOrMissedDom : function() {
							var now = this.nowDateTime;
							var nowMins = this.getNowMins();//now.getHours() *60 + now.getMinutes();
							//排班制
							if(this.fdShiftType=='1'){
								if(this.nextSignTime){
									if(nowMins < this.nextSignTime){
										if(this.pSignTime){
											if(nowMins>this.pSignTime){
												this.genSignDom();
											}else{
												this.genDisableSignDom();
											}
										}else{
											this.genSignDom();
										}
									}else{
										this.genTitleMissDom();
										this.genSignContentDom();
									}
								}else{
									if(this.pSignTime){
										if(nowMins>this.pSignTime){
											this.genSignDom();
										}else{
											this.genDisableSignDom();
										}
									}else{
										this.genSignDom();
									}
								}
								return;
							}
							
							if(this.fdWorkNum == '1') {//第一班次
								if(this.fdWorkType=='0') {//上班
									if(this.nextSignTime){
										if(nowMins < this.nextSignTime){
											this.genSignDom();
										}else{
											this.genTitleMissDom();
											this.genSignContentDom();
										}
									}
								} else if(this.fdWorkType=='1'){//下班
									if(this.fdWorkTimeSize >= '2') {//两班制
										var endTime = this._fdEndTime1 || this._fdStartTime2 || this.nextSignTime;
										if(endTime) {
											if(nowMins <= endTime){
												this.genSignDom();
											}else{
												this.genTitleMissDom();
												this.genSignContentDom();
											}
										} else {
											this.genSignDom();
										}
									} else {
										this.genSignDom();
									}
								}
							} else if(this.fdWorkNum == '2'){//第二班次
								if(this.fdWorkType=='0') {//上班
									var startTime = this._fdStartTime2 || this._fdEndTime1;
									if(startTime) {
										if(nowMins < startTime) {
											this.genDisableSignDom();
										} else if (this.nextSignTime && nowMins < this.nextSignTime){
											this.genSignDom();
										} else {
											this.genTitleMissDom();
											this.genSignContentDom();
										}
									} else {
										if(this.nextSignTime){
											if(nowMins < this.nextSignTime){
												this.genSignDom();
											}else{
												this.genTitleMissDom();
												this.genSignContentDom();
											}
										}
									}
								} else if(this.fdWorkType=='1'){//下班
									this.genSignDom();
								}
							}
						},
						
						genTitleMissDom :function(){
							if(this.fdSigned && this.isAttendStatusBuss() && !this.isAttendedByBuss()){
								//出差/请假/外出未打卡场景
								domConstruct.empty(this.head);
								var info = this.fdWorkType=='1'? Msg['sysAttendMain.status.offMissTxt']: Msg['sysAttendMain.status.onMissTxt'];
								domConstruct.create("span",{className:"time",innerHTML:info},this.head);
								domConstruct.create("span",{className:"muiSignInLabel signInLabelPrimary",innerHTML:this.fdSignedStatusTxt},this.head);
								return;
							}
							domConstruct.create("span",{className:"muiSignInLabel signInLabelDanger",innerHTML:Msg['mui.fdStatus.unSign']},this.head);
						},
						
						genTitleDom:function(){
							this.head = domConstruct.create("div",{className:"muiSignInflowListHeading muiFontSizeSS muiFontColorMuted",innerHTML:''},this.domNode);

							if(this.fdSigned && !this.isAttendStatusBuss()|| (this.fdSigned && this.isAttendedByBuss())){
								if(this.fdSignedStatus=='0'){
									var info = this.fdWorkType=='1'? Msg['mui.fdWorkType.offwork']:Msg['mui.fdWorkType.onwork'];
									var time = "(" + this.signTime +(this.fdOverTimeType=="2"?"("+Msg['mui.second.day']+")":"")+ ")";
									domConstruct.create("span",{className:"time",innerHTML:(info+time)},this.head);
								}else{
									var time = this.fdSignedAcross == 'true' ? Msg['mui.second.day'] + '&nbsp;' + this.fdSignedTime : this.fdSignedTime;
									var timeSpan = domConstruct.create("span",{className:"time",innerHTML:time},this.head);
									var infoSpan = domConstruct.create("span",{className:"info",innerHTML:Msg['mui.sign']},this.head);
									domStyle.set(timeSpan,{
										'font-size': '1.4rem',
										'font-weight': 'bold',
								    	'color': '#2A304A',
								    	'position': 'relative',
								    	'bottom': '2px'
									});
									domStyle.set(infoSpan,{
										'font-size': '1.4rem',
										'font-weight': 'bold',
								    	'color': '#2A304A',
								    	'position': 'relative',
								    	'bottom': '2px'
									});
								}
								
								if(this.fdSignedStatus=='4' || this.fdSignedStatus=='5' || this.fdSignedStatus=='6') {
									domConstruct.create("span",{className:"muiSignInLabel signInLabelPrimary",innerHTML:this.fdSignedStatusTxt},this.head);
								} else if(this.fdSignedStatus=='1' && this.fdSignedOutside !='true' || this.fdState=='2'){
									domConstruct.create("span",{className:"muiSignInLabel signInLabelPrimary",innerHTML:Msg['mui.fdStatus.ok']},this.head);
								} else if(this.isRestDay!='true' && (this.fdSignedStatus=='0' ||  this.fdSignedStatus=='2' || this.fdSignedStatus=='3') && this.fdState!='2'){
									domConstruct.create("span",{className:"muiSignInLabel signInLabelDanger",innerHTML:this.fdSignedStatusTxt},this.head);
								} 
								
								if(parseInt(this.fdSignedStatus)>0 && this.fdSignedOutside=='true' && this.fdState !='2'){
									var outsideClass = this.fdOsdReviewType == '1' ? 'muiSignInLabel signInLabelDanger' : 'muiSignInLabel signInLabelWarning';
									domConstruct.create("span",{className:outsideClass,innerHTML:Msg['mui.outside']},this.head);
								}
							}else{
								var info = this.fdWorkType=='1'? Msg['mui.fdWorkType.offwork']:Msg['mui.fdWorkType.onwork'];
								var time = "(" + this.signTime +(this.fdOverTimeType=="2"?"("+Msg['mui.second.day']+")":"")+ ")";
								if(this.isTimeAreaRestDay()){
									time = "";
								}
								
								domConstruct.create("span",{className:"time",innerHTML:(info+time)},this.head);
							}
							
						},
						
						genSignDom:function(){
							if(dom.byId("__signing")){
								return;
							}
							
							domClass.add(this.domNode,"lastSignIn");
							//sign
							this.signNode = domConstruct.create("section",{className:"muiSignInBtnArea",innerHTML:''},this.domNode);
							this.signBtnNode = domConstruct.toDom('<button type="button" class="muiSignInBtn muiSignInBtnSign">');
							domConstruct.place(this.signBtnNode, this.signNode);
							this.currentTimeDom = domConstruct.create("span",{className:"currentTime",innerHTML:''},this.signBtnNode);
							var signBtnText = this.fdType==1 ? (this.fdWorkType=='1'? Msg['mui.offwork.sign']:Msg['mui.onwork.sign']):Msg['mui.sign'];
							this.signBtnTextNode = domConstruct.create("span",{className:"signName",innerHTML:signBtnText},this.signBtnNode);
							this.signTimeCounter();
							this.connect(this.signBtnNode, touch.press, 'onSignClick');
							//客户端限制打卡
							if(!this.isAllowSigning()){
								this.genLimitSignInfo();
							}else{
								this.genLoactionInfo();
							}
							
							domConstruct.destroy('__signing');
							var signingDom = domConstruct.toDom('<input type="hidden" id="__signing" value="true"/>');
							domConstruct.place(signingDom, this.domNode);

							setInterval(lang.hitch(this,this.signTimeCounter),1000);
							setInterval(lang.hitch(this,this.refreshPosition),30000);
							setInterval(lang.hitch(this,this.getDbTimeMillis),30000);
						},
						//客户端限制打卡 是否允许kk/钉钉打卡
						isAllowSigning : function(){
							if(this.fdType==1 && this.attendCfgJson && this.attendCfgJson.fdClientLimit==true){
								var clientType = device.getClientType();
								var isAllow = (this.attendCfgJson.fdClient=='kk' && clientType>=7 && clientType<=10) || (this.attendCfgJson.fdClient=='ding' && clientType==11);
								return isAllow;
							}
							return true;
						},
						//限制客户端类型打卡
						getClientLimitTip : function(){
							var clientTip = Msg['mui.clientlimit.tips.kk'];
							if(this.attendCfgJson.fdClient=='ding'){
								clientTip = Msg['mui.clientlimit.tips.ding'];
							}
							return clientTip;
						},
						genLimitSignInfo:function(){
							this.limitSignNode = domConstruct.create("p",{className:"muiSignInBtnInfo",innerHTML:this.getClientLimitTip()},this.signNode);
							domClass.add(this.signBtnNode,'muiSignInBtnDisabled');
						},
						
						genDisableSignDom : function() {
							if(dom.byId("__signing")){
								return;
							}
							domClass.add(this.domNode,"lastSignIn");
							var signNode = domConstruct.create("section",{className:"muiSignInBtnArea",innerHTML:''},this.domNode);
							var signBtnNode = domConstruct.toDom('<button type="button" class="muiSignInBtn muiSignInBtnSign muiSignInBtnDisabled">');
							domConstruct.place(signBtnNode, signNode);
							this.currentTimeDom = domConstruct.create("span",{className:"currentTime",innerHTML:''},signBtnNode);
							var signBtnTextNode = domConstruct.create("span",{className:"signName",innerHTML:Msg['mui.notReady']},signBtnNode);
							this.signTimeCounter();
							
							domConstruct.destroy('__signing');
							var signingDom = domConstruct.toDom('<input type="hidden" id="__signing" value="true"/>');
							domConstruct.place(signingDom, this.domNode);

							setInterval(lang.hitch(this,this.signTimeCounter),1000);
							setInterval(lang.hitch(this,this.refreshPosition),30000);
						},
						
						genLoactionInfo :function(){
							var self = this;
							this.signLocNode = domConstruct.create("p",{className:"muiSignInBtnInfo",innerHTML:''},this.signNode);
							this.signIconNode = domConstruct.create("i",{className:"mui fontmuis muis-position",innerHTML:''},this.signLocNode)
							
							this.locationShowNode = domConstruct.create("span",{className:"muiSignInLocation muiFontSizeSS",innerHTML:''},this.signLocNode);
							this.locationLoadingNode = domConstruct.create("div",{className:"muiIconLoading",innerHTML:''},this.signLocNode);
							domConstruct.place(domConstruct.toDom('<span></span><span></span><span></span>'), this.locationLoadingNode);
							
							this.locationValueNode = domConstruct.create('input',{name:'coord',type:'hidden'},this.signLocNode);
							
							this.limitNode = domConstruct.create('span',{className:'muiSignInLimit',innerHTML:''},this.signLocNode);
							
							this.resetBtnNode = domConstruct.create('a',{className:'refashSingIn muiFontColor',href:"javascript:void(0)",innerHTML:Msg['mui.relocation']},this.signNode);
							this.connect(this.resetBtnNode, touch.press, '_onResetLocationClick');
							adapter.mixinReady(function(){
								self.refreshPosition();
							});
							
						},
						
						refreshPosition : function(){
							var self = this;
							var promise = this.isWifiSign();
							promise.then(function(data){
								if(data.isWifi) {
									self.hideLocationLoading();
									self.locationShowNode.innerHTML = 'WIFI：' + data.wifiName;
									//#130087 WiFi打卡图标少去掉了个class导致图标问题
									domClass.remove(self.signIconNode, "fontmuis");
									domClass.remove(self.signIconNode, "muis-position");
									domClass.add(self.signIconNode, "mui-wifi");
									self._fdOutside = 0;
									self.signBtnTextNode.innerHTML= self.fdWorkType=='1'? Msg['mui.offwork.sign'] : Msg['mui.onwork.sign'];
									domClass.remove(self.limitNode,"unLimit");
									domClass.remove(self.signBtnNode,"muiSignInBtnOutside");
									domStyle.set(self.resetBtnNode,'display','none');
									self.limitNode.innerHTML = Msg['mui.zone.inside'];
									self.isWifiOk = true;
									self.wifiName = data.wifiName;
									self.wifiMacIp = data.wifiMacIp;
								} else {
									self.refreshLocation();
								}
							}).then(function(){
								adapter.getDeviceInfo(function(res){
									if(res && res.deviceId) {
										self.fdDeviceId = res.deviceId;
									}
								});
							});
						},
						
						hideLocationLoading : function(){
							domClass.add(this.locationLoadingNode,'hide');
						},
						showLocationLoading : function(){
							domClass.remove(this.locationLoadingNode,'hide');
						},
						
						isWifiSign : function(){
							var defer = new Deferred(),
								data = {
									isWifi : false,
								},
								fdWifiConfigs = eval(this.fdWifiConfigs),
								self = this;
							
							if(this.fdType==1 && fdWifiConfigs && fdWifiConfigs.length>0){
								adapter.getNetworkType(function(rs){
									if(rs && rs.networkType && rs.networkType.toLowerCase() == 'wifi'){
										adapter.getWifiInfo(function(result){
											if(result && result.macIp){
												var wifiName = '',
													wifiMacIp = '',
													isOk = false;
												for(var k=0; k<fdWifiConfigs.length; k++){
													if(self._isSameMacIp(result.macIp, fdWifiConfigs[k].fdMacIp)){
														isOk = true;
														wifiName = fdWifiConfigs[k].fdName;
														wifiMacIp = fdWifiConfigs[k].fdMacIp;
														break;
													}
												}
												if(isOk) {
													lang.mixin(data, {
														wifiName : wifiName,
														wifiMacIp : wifiMacIp,
														isWifi : true
													});
													defer.resolve(data);
												} else {//wifi不匹配
													defer.resolve(data);
												}
											} else {//获取不了mac地址
												defer.resolve(data);
											}
										});
									} else {//网络不是wifi
										defer.resolve(data);
									}
								});
							} else {//没有配置wifi打卡
								defer.resolve(data);
							}
							return defer.promise;
						},
						// mac地址只需判断前11位
						_isSameMacIp : function(macIp, _macIp) {
							if(macIp && _macIp) {
								return macIp.substring(0, macIp.length - 1).toLowerCase() == _macIp.substring(0, _macIp.length - 1).toLowerCase();
							}
							return false;
						},
						
						//转换为统一格式
						formatCoord : function(point,coordType){
							var prefix = "bd09:";
							if(coordType==5){
								prefix = "gcj02:";
							}
							var currentLatLng = prefix +(point.lat+"," + point.lng);
							return currentLatLng;
						},
						
						refreshLocation : function(){
							var self = this;
							adapter.getCurrentPosition(function(result){
								self.hideLocationLoading();
								var coordType = result.coordType;
								var address = result.address;
								var point = result.point;
								var pois = result.pois;
								if(pois && pois.length>0){
									//var title = pois[0].title ? pois[0].title:'';
									//address = title ?(title + ' ' + pois[0].address):pois[0].address;
									//point = pois[0].point;
								}
								var currentLatLng = self.formatCoord(point,coordType);
								self.locationValueNode.value=currentLatLng;
								self.locationName=address;
								self.fdAddress=address;
								var fdLocations = eval(self.fdLocations);
								//增加坐标
								//domConstruct.create('div',{className:'',innerHTML:'当前经纬度:'+currentLatLng,style:'position: absolute;bottom:2px;font-size: 6px;left: 2px;color:#999;'},query('#scrollView')[0]);
								if(self.fdType==1){
									if(self.fdCanMobile == 'false'){
										self.signBtnTextNode.innerHTML= Msg['mui.notAllow.sign'];
										self.locationShowNode.innerHTML = address;
										domClass.add(self.signBtnNode,'muiSignInBtnDisabled');
										domClass.remove(self.limitNode,"unLimit");
										domStyle.set(self.resetBtnNode,'display','none');
										domStyle.set(self.signLocNode,'display','none');
										
									}else{
										//限制打卡范围
										var isOk = false;
										var signInLimitText = Msg['mui.zone.outside'];
										var signInAddress = '';
										
										for(var i = 0; i< fdLocations.length;i++){
											var distance = MapUtil.getDistance(currentLatLng,fdLocations[i].coord);
											if(self.isRange(distance,fdLocations[i].distance)){
												isOk = true;
												self.locationName = fdLocations[i].address;
												signInLimitText = Msg['mui.zone.inside'];
												break;
											}
										}
										
										if(!isOk){
											//外勤
											self._fdOutside=1;
											self.locationShowNode.innerHTML=address;
											self.signBtnTextNode.innerHTML= Msg['mui.outside.sign'];
											domClass.add(self.signBtnNode,"muiSignInBtnOutside");
											domClass.add(self.limitNode,"unLimit");
										}else{
											//正常打卡
											self.locationShowNode.innerHTML=self.locationName;
											self._fdOutside=0;
											var signBtnText = self.fdType==1 ? (self.fdWorkType=='1'? Msg['mui.offwork.sign']:Msg['mui.onwork.sign']):Msg['mui.sign'];
											self.signBtnTextNode.innerHTML=signBtnText;
											domClass.remove(self.limitNode,"unLimit");
											domClass.remove(self.signBtnNode,"muiSignInBtnOutside");
											domClass.remove(self.signBtnNode,'muiSignInBtnOutsideDis');
											domStyle.set(self.resetBtnNode,'display','none');
										}
									}
									
									self.limitNode.innerHTML=signInLimitText;
								}
								
								if(self.fdType==2){
									if(fdLocations && fdLocations.length>0 && self.fdLimit) {
										var isOk = false;
										var signInLimitText = Msg['mui.zone.outside'];

										for(var i = 0; i< fdLocations.length;i++){
											var distance = MapUtil.getDistance(currentLatLng,fdLocations[i].coord);
											if(self.isRange(distance,fdLocations[i].distance)){
												isOk = true;
												self.locationName = fdLocations[i].address;
												signInLimitText = Msg['mui.zone.inside'];
												break;
											}
										}
										
										if(!isOk){
											self.isCustRange = false;
											domClass.add(self.limitNode,"unLimit");
											domClass.add(self.signBtnNode,'muiSignInBtnOutsideDis');
										} else {
											self.isCustRange = true;
											domClass.remove(self.limitNode,"unLimit");
											domClass.remove(self.signBtnNode,'muiSignInBtnOutsideDis');
										}
										self.limitNode.innerHTML=signInLimitText;
										self.locationShowNode.innerHTML=address;
									} else {
										self.isCustRange = true;
										self.locationShowNode.innerHTML=address;
									}
								}
								
							},function(){
								self.hideLocationLoading();
								self.locationShowNode.innerHTML= Msg['mui.location.fail'];
							});
						},
						
						isRange : function(distance,limit2){
							//兼容旧数据的范围打卡，目前只处理考勤打卡
							if(!!this.fdLimit) {
								var range = parseInt(this.fdLimit) > 200 ? this.fdLimit : 200;
								return distance <= range;
							} else if(this.fdType==1 && !!limit2){
								var range = limit2 > 200 ? limit2 : 200;
								return distance <= range;
							}else{
								return false;
							}
						},
						
						signTimeCounter :function(){
							//判断是否需要重新刷新页面
							var isRefresh = this.refreshPage();
							if(isRefresh){
								return;
							}
							var date = this.nowDateTime;
							date.setSeconds(date.getSeconds()+1);
							this.currentTimeDom.innerHTML=this.timeFormat(date.getHours())+':' + this.timeFormat(date.getMinutes())+":" + this.timeFormat(date.getSeconds());
						},
						timeFormat : function(v){
							return v >= 10 ? v : "0" + v;
						},
						
						genSignContentDom:function(){
							domClass.add(this.domNode,'muiSignedItem');
							var content = domConstruct.create("div",{className:"muiSignInflowListContent",innerHTML:''},this.domNode);
							var item = domConstruct.create("div",{className:"item",innerHTML:''},content);
							if(this.fdSignedLocation){
								var p = domConstruct.toDom("<p class='muiSignInBtnInfo'><i class='mui fontmuis muis-position'></i>" + this.fdSignedLocation + "</p>");
								domConstruct.place(p, item);
							} else if(this.fdSignedWifi){//wifi
								var p = domConstruct.toDom("<p class='muiSignInBtnInfo'><i class='mui mui-wifi'></i>" + this.fdSignedWifi + "</p>");
								domConstruct.place(p, item);
							}
							this.btnTools = domConstruct.create("div",{className:"muiSignInBtnGroup",innerHTML:''},item);
							
							if((this.fdSignedStatus=='4' || this.fdSignedStatus=='5' || this.fdSignedStatus=='6') && this.fdBusUrl) {//出差，请假，外出
								//var btn = domConstruct.toDom('<button type="button" class="muiSignInBtn muiSignInBtnPrimaryOutline">' + Msg['mui.bussiness.form'] + '<i class="mui mui-forward"></i></button>');
								//domConstruct.place(btn, this.btnTools);
								//this.connect(btn,'onclick','_onBusinessClick');
								this.genUpdateSignDom();
								return;
							}
							
							//休息日打卡不区分状态
							if(this.isRestDay !='true'){
								if(this.fdSignedStatus=='0' || this.fdSignedStatus=='2' || this.fdSignedStatus=='3' || 
										(this.fdSignedStatus=='1' && this.fdSignedOutside=='true' && this.fdOsdReviewType == '1')
										|| this.fdMainExcId && this.fdSigned || !this.fdSigned){
									
									var excBtnText =Msg['mui.submit.exc'];
									if(this.fdState=='1'){
										excBtnText = Msg['mui.exception.doing'] + '...';
									}else if(this.fdState=='2'){
										excBtnText = Msg['mui.exception.done'] + '...';
									}else if(this.fdState=='3'){
										excBtnText = Msg['mui.exception.reject'] + '...';
									}else if(!this.fdState){
										excBtnText = Msg['mui.submit.exc'];
									}
									var btn = domConstruct.toDom('<button type="button" class="muiSignInBtn muiSignInBtnPrimaryOutline">' + excBtnText + '<i class="fontmuis muis-to-right"></i></button>');
									domConstruct.place(btn, this.btnTools);
									this.connect(btn,'onclick','_onViewSignExcClick');

									if(this.fdState=='3') {
										var btn = domConstruct.toDom('<button type="button" class="muiSignInBtn muiSignInBtnPrimaryOutline">' + Msg['mui.submit.exc'] + '<i class="fontmuis muis-to-right"></i></button>');
										domConstruct.place(btn, this.btnTools);
										this.connect(btn, 'onclick', '_onSignExcClick');
									}
								}
							}
							//更新打卡
							this.genUpdateSignDom();
						},
						
						genUpdateSignDom : function(){
							if(!this.isLastSigned || this.fdSignedStatus=='1' && this.fdSignedOutside !='true' && this.fdWorkType=='0' || this.fdType!=1){
								return;
							}

							if(this.fdSigned && this.fdSignedStatus!='0' && (!this.fdState || this.fdState=='0' || this.fdState=='3')){
								if(this.fdSignedStatus=='2' && this.fdSignedOutside !='true'){//迟到且非外勤时,不允许更新
									return;
								}
								//时间过滤
								var isNeeded = this._isNeedUpdateByTime();
								if(!isNeeded) {
									return;
								}
								//位置范围过滤
								var self = this;
								if((self.fdSignedStatus!='1' || self.fdSignedOutside =='true') && self.fdOutside=='true' && self.fdCanMobile == 'true') {
									//原纪录不正常，并且允许外勤打卡时，不用判断是否在位置范围。规避同时调adapter.getCurrentPosition（打卡定位时）可能出错
									self._genUpdateSignDom();
									return;
								}
								this.isWifiSign().then(function(data){
									if(data.isWifi){
										self._genUpdateSignDom();
									} else {
										adapter.getCurrentPosition(function(result){
											var coordType = result.coordType;
											var address = result.address;
											var point = result.point;
											var pois = result.pois;
											if(pois && pois.length>0){
												//var title = pois[0].title ? pois[0].title:'';
												//address = title ?(title + ' ' + pois[0].address):pois[0].address;
												//point = pois[0].point;
											}
											point.coordType = coordType;
											if(!address){
												return;
											}
											//是否在打卡范围
											var fdLatLng = self.formatCoord(point,coordType);
											var fdLocations = eval(self.fdLocations);
											var isOk = false;
											for(var i = 0; i< fdLocations.length;i++){
												var distance = MapUtil.getDistance(fdLatLng,fdLocations[i].coord);
												if(self.isRange(distance,fdLocations[i].distance)){
													isOk = true;
													break;
												}
											}
											// 不在打卡范围内
											if(!isOk) {
												// 原纪录正常时，不论是否允许外勤打卡都不显示更新
												if(self.fdSignedStatus=='1' && self.fdSignedOutside !='true') {
													return;
												// 原纪录不正常，不允许外勤打卡时
												} else if (self.fdOutside=='false') {
													return;
												}
											}
											
											self._genUpdateSignDom();
										},function(err){
										});
									}
								});
							}
						},
						
						_isNeedUpdateByTime : function() {
							var now = this.getCurrentSignTime();
							var nowMins = this.getNowMins();//now.getHours() *60 + now.getMinutes();
							var isNeeded = false;
							//时间过滤
							if(this.isAcrossDay == 'true') {
								if(this.isCurrentAcross == 'true') {
									if(!this.nextSignTime) {
										this.fdIsAcross = true;
										isNeeded = true;
									}
								} else {
									this.fdIsAcross = false;
									if(nowMins >= this._fdStartTime) {
										var endTime = this.nextSignTime;
										if(this.fdWorkTimeSize >= '2' && this.fdWorkNum == '1' && this.fdWorkType == '1') {//两班制时第一班的下班卡
											endTime = this._fdEndTime1 || this._fdStartTime2 || this.nextSignTime;
										}
										if(endTime) {
											if(nowMins <= endTime) {
												isNeeded = true;
											}
										} else {
											isNeeded = true;
										}
									}
								}
							} else {
								if(nowMins>=this._fdStartTime && nowMins<=this._fdEndTime){
									var endTime = this.nextSignTime;
									if(this.fdWorkTimeSize >= '2' && this.fdWorkNum == '1' && this.fdWorkType == '1') {//两班制时第一班的下班卡
										endTime = this._fdEndTime1 || this._fdStartTime2 || this.nextSignTime;
									}
									if(endTime){
										if(nowMins <= endTime){
											isNeeded = true;
										}
									}else{
										isNeeded = true;
									}
								}
							}
							
							return isNeeded;
						},
						
						_genUpdateSignDom:function(){
							this.updateSignBtn = domConstruct.toDom('<button type="button" class="muiSignInBtn muiSignInBtnPrimaryOutline">'+Msg['mui.update.sign']+'<i class="fontmuis muis-to-right"></i></button>');
							domConstruct.place(this.updateSignBtn, this.btnTools);
							this.connect(this.updateSignBtn,'onclick','onUpdateSignClick');
						},
						//获取打卡界面当前显示时间
						getCurrentSignTime :function(){
							var currentTime = query('.muiSignInBtnArea .currentTime').text();
							if(currentTime){
								var now = new Date();
								var times = currentTime.split(":");
								now.setHours(times[0]);
								now.setMinutes(times[1]);
								now.setSeconds(times[2]);
								return now;
							}
							return new Date();
						},
						
						//判断是否早退或迟到
						_getCurFdStatus : function(){
							var fdStatus ="1";
							var now = this.getCurrentSignTime();
							var nowMins = this.getNowMins();//now.getHours() *60 + now.getMinutes();
							var fdLateTime = this.fdLateTime || 0;
							var fdLeftTime = this.fdLeftTime || 0;
							var _signTime = parseInt(this._signTime || 0);
							var fdIsAcross = !!this.fdIsAcross;
							var fdFlexTime = !!this.fdFlexTime ? parseInt(this.fdFlexTime, 10) : 0;
							var lastSignedTime = !!this.lastSignedTime ? parseInt(this.lastSignedTime, 10) : null;
							var workTimeMins = !!this.workTimeMins ? parseInt(this.workTimeMins, 10) : null;
							var fdOverTimeType = !!this.fdOverTimeType ? parseInt(this.fdOverTimeType, 10) : 1;
							//上一个打卡时间。只用于下班打卡时，相当于上班打卡的标准时间
							var _goWorkTimeMins = !!this.pSignTime ? parseInt(this.pSignTime, 10) : null;
							//判断是否早退或迟到
							if(this.fdIsFlex == 'true'){
								//是否弹性上下班
								if(this.fdWorkType=='0'){
									//上班，超过弹性时间，则认为是迟到
									if(nowMins > (_signTime + fdFlexTime)){
										fdStatus = '2';
									}
								} else {
									//已打卡的上班时间
									lastSignedTime = parseInt(lastSignedTime || 0);
									//提前多少分钟上班的（-30）
									var goWrokFlexMin = _goWorkTimeMins - lastSignedTime;
									//最大弹性时间为设置的。超过最大以设置最大的弹性分钟数为准
									if(goWrokFlexMin < fdFlexTime){
										//如果提前打卡的分钟数，在弹性时间范围内，则以多大的分钟数作为弹性分钟数
										if(goWrokFlexMin < 0){
											//比如设置30分钟，迟到1个小时，则没有弹性分钟数的概念
											if(goWrokFlexMin * -1  > fdFlexTime ){
												fdFlexTime =0;
											}else{
												fdFlexTime =goWrokFlexMin;
											}
										} else {
											fdFlexTime =goWrokFlexMin;
										}
									}
									//如果弹性时间小于0.则表示 上班迟到，则要下班标准时间 加上 迟到的时间，才不会早退
									if(fdFlexTime < 0 ){
										fdFlexTime = fdFlexTime * -1;
										if(nowMins < _signTime){
											//打卡时间是标准打卡时间之前，则是迟到
											fdStatus = "3";
										}else if(nowMins <  _signTime + fdFlexTime ){
											//打卡时间，小于弹性时间
											fdStatus = "3";
										}
									}else{
										//提前上班的弹性时间计算。
										if(nowMins < _signTime - fdFlexTime ){
											//打卡时间是标准打卡时间 减去弹性时间 之前。算迟到
											fdStatus = "3";
										}
									}
								}
							} else {
								//迟到时间的配置
								if(this.fdWorkType=='0'){
									fdLateTime = parseInt(this.fdLateTime || 0);
									if(nowMins >(_signTime + fdLateTime)){
										fdStatus = "2";
									}
								} else {
									fdLeftTime = parseInt(this.fdLeftTime || 0);
									if(nowMins < (_signTime - fdLeftTime)){
										fdStatus = "3";
									}
								}
							}
							
							//休息日打卡不区分状态
							if(this.isRestDay =='true'){
								fdStatus = "1";
							}
							
							return fdStatus;
						},
						
						onSignClick : function() {
							window.console.log('打卡事件触发，onSignClick...');
							var self =this;
							var url = "/sys/attend/sys_attend_main/sysAttendMain.do?method=getCateAlertTime&forward=lui-source&fdCategoryId="+this.categoryId;
							request(util.formatUrl(url), {
								handleAs : 'json',
								method : 'post',
							}).then(function(result){
								var flag= true;
								if (result.cateAlertTime) {
									var now = new Date();
									now.setTime(self.nowTime);
									var alertTime=new Date();
									alertTime.setTime(result.cateAlertTime);
									if(now<alertTime){
										Tip.tip({
											text : Msg['mui.change.category'],
											callback : self.callback
										});
										flag= false;
										return;
									}else{
										flag=true;
									}
								}
								if(flag){
									if(this.isWifiOk) {
										var proc = Tip.processing();
										proc.show();
										window.console.log('打卡事件触发，wifi打卡校验...');
										var promise = this.isWifiSign();
										promise.then(function(args){
											proc.hide();
											window.console.log('打卡事件触发，是否支持wifi打卡：'+args.isWifi);
											if(args.isWifi){
												self._onSignClick();
											} else {
												window.console.log('打卡事件触发，位置变更，重新刷新页面...');
												Tip.tip({
													text : Msg['mui.change.location'],
													callback : self.callback
												});
											}
										});
									} else {
										self._onSignClick();
									}
								}
							},function(e){
								window.console.log("获取数据失败,error:" + e);
								Tip.tip({
									text : Msg['mui.sign.fail'],
									callback : self.callback
								});
							});
						},
						
						_onSignClick:function(){
							window.console.log('打卡事件触发,进入安全性校验，_onSignClick...');
							if(domAttr.has(this.signBtnNode, "disabled")){
								window.console.log('打卡事件触发,重复触发，忽略处理！');
								return;
							}
							//客户端类型限制
							if(!this.isAllowSigning()){
								window.console.log('打卡事件触发,客户端类型限制，忽略处理！');
								Tip.fail({
									text:this.limitSignNode.textContent
								});
								return;
							}
							if(this.fdType==1 && this.fdCanMobile=='false'){
								window.console.log('打卡事件触发,不允许移动端操作，忽略处理！');
								Tip.fail({
									text:Msg['mui.notAllow.mobile']
								});
								return;
							}
							var fdStatus ="1";
							var now = this.getCurrentSignTime();
							var nowMins = this.getNowMins();//now.getHours() *60 + now.getMinutes();
							var fdLateTime = this.fdLateTime || 0;
							var fdLeftTime = this.fdLeftTime || 0;
							var _signTime = parseInt(this._signTime || 0);
							var isWifi = !!this.isWifiOk;//是否wifi打卡
							var fdWifiName = isWifi ? this.wifiName : '';
							var fdWifiMacIp = isWifi ? this.wifiMacIp : '';
							var fdIsAcross = !!this.fdIsAcross;
							var fdFlexTime = !!this.fdFlexTime ? parseInt(this.fdFlexTime, 10) : 0;
							var lastSignedTime = !!this.lastSignedTime ? parseInt(this.lastSignedTime, 10) : null;
							var workTimeMins = !!this.workTimeMins ? parseInt(this.workTimeMins, 10) : null;
							var fdOverTimeType=!!this.fdOverTimeType ? parseInt(this.fdOverTimeType, 10) : 1;


							var location = this.locationValueNode.value;
							if((!isWifi && !location) || (isWifi && !fdWifiName)){
								window.console.log('打卡事件触发,未定位，忽略处理！');
								Tip.fail({
									text: Msg['mui.relocation.please']
								});
								return;
							}
							
							if(this.fdType==2 && !this.isCustRange){
								window.console.log('打卡事件触发,范围外不许打卡，忽略处理！');
								Tip.fail({
									text:Msg['mui.signOutside.notAllow']
								});
								return;
							}
							//判断是否早退或迟到
							fdStatus = this._getCurFdStatus();
							//出差/请假/外出标识
							if(this.isAttendStatusBuss()){
								fdStatus = this.fdSignedStatus;
							}
							var fdLatLng = location;
							location = location.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
							var data = {
								fdId:this.fdId,
								fdStatus:fdStatus,
								fdCategoryId:this.categoryId,
								fdLocation:this.locationName,
								fdDesc:'',
								fdLng:location[1],
								fdLat :location[0],
								fdLatLng : fdLatLng,
								fdOutside :'0',
								fdWorkType :this.fdWorkType,
								fdWorkTimeId :this.fdWorkTimeId,
								fdLateTime : fdLateTime,
								fdLeftTime : fdLeftTime,
								fdType :this.fdType,
								signTime : _signTime,
								fdAddress:this.fdAddress,
								isRestDay : this.isRestDay,
								fdWifiName : fdWifiName,
								fdWifiMacIp : fdWifiMacIp,
								fdDeviceId : this.fdDeviceId || '',
								fdIsAcross : fdIsAcross,
								fdIsFlex : this.fdIsFlex,
								fdFlexTime : fdFlexTime,
								lastSignedTime : lastSignedTime,
								lastSignedStatus : this.lastSignedStatus,
								lastSignedState : this.lastSignedState,
								workTimeMins : workTimeMins,
								fdOverTimeType:fdOverTimeType,
								fdWorkDateLong: this.fdWorkDate
							};
							window.console.log('开始进入打卡阶段...');
							//早退则弹出提示
							if(fdStatus=='3'){
								if(this._fdOutside==1 || this.fdType==2){
									this.checkSecuritySubmit(data,lang.hitch(this,this._signSubmit));
									return;
								}
								var self = this;
								Confirm('<div style="line-height:2rem;">'+Msg['mui.sign.leftOrNot']+'</div>','',function(value){
									if(value==true){
										self.checkSecuritySubmit(data,lang.hitch(self,self._signSubmit));
									}
								});
								return;
							}else{
								this.checkSecuritySubmit(data,lang.hitch(this,this._signSubmit));
							}
						},
						
						isEnableSecurity : function(){
							if(this.fdType==1 && (this.fdSecurityMode=='camera' || this.fdSecurityMode=='face')){
								return true;
							}
							return false;
						},
						//判断是否启用设备数限制
						isEnableDeviceLimit : function(){
							if(this.fdType==1 && this.attendCfgJson && this.attendCfgJson.fdClientLimit==true && this.attendCfgJson.fdDeviceLimit==true){
								//只有kk,ding支持设备数限制
								var clientType = device.getClientType();
								var isSupport = (this.attendCfgJson.fdClient=='kk' && clientType>=7 && clientType<=10) || (this.attendCfgJson.fdClient=='ding' && clientType==11);
								return isSupport;
							}
							return false;
						},
						//安全验证(打卡,更新打卡入口)
						checkSecuritySubmit : function(data,callback){
							window.console.log('打卡进入安全验证阶段,checkSecuritySubmit,callback:'+callback);
							var self = this;
							//设备数限制校验
							if(!this.isEnableDeviceLimit()){
								this.doAttendSubmit(data,callback);
								return;
							}
							window.console.log('打卡进入安全验证阶段,获取设备信息...');
							adapter.getDeviceInfo(function(res){
								var fdDeviceId = res.deviceId;
								window.console.log('打卡进入安全验证阶段,获取设备信息：'+fdDeviceId);
								if(self.fdDeviceIds){
									var deviceIdsArr = self.fdDeviceIds.split(';');
									var fdDeviceCount = self.attendCfgJson.fdDeviceCount;
									if(deviceIdsArr.length>fdDeviceCount){
										var count = deviceIdsArr.length-fdDeviceCount;
										for(var i = 0;i < count;i++){
											deviceIdsArr.shift();
										}
									}
									var fdDeviceIds = deviceIdsArr.join(';');
									if(fdDeviceIds.indexOf(res.deviceId) > -1){
										self.doAttendSubmit(data,callback);
									}else if(deviceIdsArr.length<fdDeviceCount){
										self.doAttendSubmit(data,callback);
									}else{
										//身份验证
										self.validateUserOfDeviceExc(data,callback);
									}
								}else{
									self.doAttendSubmit(data,callback);
								}
							});
							
						},
						//设备异常处理
						validateUserOfDeviceExc : function(data,callback){
							window.console.log('打卡进入设备异常处理阶段,validateUserOfDeviceExc,callback:'+callback);
							var self = this;
							var fdDeviceExcMode = this.attendCfgJson.fdDeviceExcMode;
							var clientType = device.getClientType();
							if(clientType==11){
								alert(Msg['mui.device.id.error']);
								return ;
							}
							
							if(!this.validateSupportCameraOrFace(fdDeviceExcMode)){
								window.console.log('打卡进入设备异常处理阶段,设备不支持拍照或刷脸...');
								return;
							}
							//增加设备异常标识,用于后台保存异常记录
							data.deviceExcFlag = "true";
							//设备异常处理方式
							data.deviceExcMode = fdDeviceExcMode;
							//(考勤组开启安全验证后,设备异常时不再需要验证)
							if(this.isEnableSecurity()){
								data.deviceExcMode = this.fdSecurityMode;
								this.doAttendSubmit(data,callback);
								return;
							}
							//设备异常提示
							var tip = Msg['mui.device.excmode.tips'].replace('%mode%', (fdDeviceExcMode=='camera' ? Msg['mui.device.excmode.camera']:Msg['mui.device.excmode.faceid']));
							Confirm('<div style="line-height:2rem;">' + tip +'</div>','',function(value){
								if(value==true){
									//拍照验证
									window.console.log('打卡进入设备异常处理阶段,进入拍照或刷脸，fdDeviceExcMode：'+fdDeviceExcMode);
									if(fdDeviceExcMode=='camera'){
										self.proc = Tip.processing();
										self.proc.show();
										adapter.validateCamera(function(file){
											attachmentUtil.uploadFile(file,function(result){
												//附件提交
												if(!self.checkDeviceExcAttRules(file,data)){
													self.proc.hide();
													Tip.fail({text:Msg['mui.device.upload.picture.fail']});
													return;
												}
												callback && callback(data);
											},function(err){
												self.proc.hide();
												Tip.fail({text:Msg['mui.device.upload.picture.fail']});
											});
										},function(err){
											if(err && err!=-1){
												Tip.fail({text:Msg['mui.device.take.photo.fail']});
											}
											self.proc.hide();
										});
										return;
									}
									//刷脸验证
									if(fdDeviceExcMode=='face'){
										adapter.checkFace(function(result){
											callback && callback(data);
										},function(){
										});
										return;
									}
								}
							});
							
						},
						//校验是否支持拍照或刷脸
						validateSupportCameraOrFace : function(type){
							if(type=='camera'){
								var msg = {
									1 : Msg['mui.device.camera.type.error'],
									2 : Msg['mui.device.camera.version.error']
								}
								var ret = adapter.supportCamera();
								if(ret !=0){
									Tip.fail({text:msg[ret]});
									return false;
								}
								return true;
							}
							if(type=='face'){
								var msg = {
									1 : Msg['mui.device.face.type.error'],
									2 : Msg['mui.device.face.version.error']
								}
								var ret = adapter.supportFacePrint();
								if(ret !=0){
									Tip.fail({text:msg[ret]});
									return false;
								}
								return true;
							}
							return false;
						},
						
						//拍照或刷脸打卡入口
						doAttendSubmit : function(data,callback){
							window.console.log('打卡进入拍照或刷脸校验阶段,doAttendSubmit...');
							var self = this;
							if(!this.isEnableSecurity()){
								callback && callback(data);
								return;
							}
							//拍照验证
							if(this.fdSecurityMode=='camera'){
								if(!this.validateSupportCameraOrFace(this.fdSecurityMode)){
									window.console.log('打卡进入拍照或刷脸校验阶段,设备不支持拍照...');
									return;
								}
								//校验
								this.proc = Tip.processing();
								this.proc.show();
								window.console.log("开始拍照打卡..."+new Date());
								adapter.validateCamera(function(file){
									window.console.log("准备上传拍照打卡信息,获取上传token;file:" +file.name+";"+new Date());
									attachmentUtil.uploadFile(file,function(result){
										window.console.log("获取上传token完成,result status:" +result.status+";"+new Date());
										if(self._fdOutside==1){//外勤
											data.fileKey = file.filekey;
											data.fileName = file.name;
											data.imageFileOSPath = file.imageFileOSPath;
											self.securityOutside = true;//临时标识
											callback && callback(data);
											return ;
										}
										//附件提交
										if(!self.checkAttRules(file,data)){
											window.console.log("上传附件失败..."+";"+new Date());
											self.proc.hide();
											Tip.fail({text:Msg['mui.sign.upload.picture.fail']});
											return;
										}
										window.console.log("上传附件完成,准备提交打卡信息..."+";"+new Date());
										callback && callback(data);
									},function(err){
										window.console.log("获取上传token失败,err:" +err+";"+new Date());
										self.proc.hide();
										Tip.fail({text:Msg['mui.sign.upload.picture.fail']});
									});
								},function(err){
									window.console.log("调用拍照接口失败,err:" +err+";"+new Date());
									if(err && err!=-1){
										Tip.fail({text:Msg['mui.device.take.photo.fail']});
									}
									self.proc.hide();
								});
								return;
							}
							//刷脸验证
							if(this.fdSecurityMode=='face'){
								if(!this.validateSupportCameraOrFace(this.fdSecurityMode)){
									window.console.log('打卡进入拍照或刷脸校验阶段,设备不支持刷脸...');
									return;
								}
								//校验
								window.console.log("开始刷脸打卡..."+new Date());
								adapter.checkFace(function(result){
									callback && callback(data);
								},function(){
								});
								return;
							}
						},
						//设备异常检查提交附件
						checkDeviceExcAttRules:function(file,paramData){
							var fdId = attachmentUtil.registFile({'filekey':file.filekey,
								'name':file.name});
							if(!fdId){
								return false;
							}
							paramData['attachmentForms.attachment.fdModelName']='com.landray.kmss.sys.attend.model.SysAttendDeviceExc';
							paramData['attachmentForms.attachment.attachmentIds']=fdId;
							paramData['attachmentForms.attachment.fdKey']='deviceExe_attachment';
							paramData['attachmentForms.attachment.fdAttType']='pic';
							return true;
						},
						//打卡前检查提交附件
						checkAttRules:function(file,paramData){
							var fdId = attachmentUtil.registFile({'filekey':file.filekey,
								'name':file.name});
							if(!fdId){
								return false;
							}
							paramData['attachmentForms.attachment.fdModelName']='com.landray.kmss.sys.attend.model.SysAttendMain';
							paramData['attachmentForms.attachment.attachmentIds']=fdId;
							paramData['attachmentForms.attachment.fdKey']='attachment';
							paramData['attachmentForms.attachment.fdAttType']='pic';
							return true;
						},
						
						_signSubmit:function(data){
							window.console.log('准备进入打卡阶段,_signSubmit...');
							var self = this;
							//修复在下班打卡时间之前打开打卡页面，过了最迟下班打卡时间也还可以打第一班的下班卡，并且状态是正常#104286
							if(!this.validateSignOnEffectiveTimeRange()){
								window.open(util.formatUrl("/sys/attend/mobile/index.jsp?_u=" + new Date().getTime())+"&categoryId="+data.fdCategoryId, '_self');
								return ;
							}
							var url = "/sys/attend/sys_attend_main/sysAttendMain.do?method=save&forward=lui-source&pid="+dojoConfig.CurrentUserId;
							if(self._fdOutside==1 || self.fdType==2){
								var curUrl = window.location.href.replace(/#/g,"");
								curUrl = util.setUrlParameter(curUrl,'categoryId',data.fdCategoryId);
								var p ="method=add&fdCategoryId="+data.fdCategoryId + "&fdLocation=&fdLng=&fdLat=&fdWorkType="+data.fdWorkType+"&fdWorkTimeId="+data.fdWorkTimeId+'&fdDeviceId='+data.fdDeviceId+'&fdIsAcross='+data.fdIsAcross
								+ '&lastSignedTime=' + this.lastSignedTime + '&lastSignedStatus='+ this.lastSignedStatus + '&lastSignedState=' + this.lastSignedState
								+ "&fdLatLng=" + data.fdLatLng
								+"&_signTime="+ this._signTime
								+"&_fdWorkDate="+ parseFloat(this.fdWorkDate)
								+"&fdOverTimeType="+ data.fdOverTimeType
								+"&_referer="+encodeURIComponent(curUrl);
								if(this.securityOutside){
									//安全校验标识
									var fileParam = "&fileKey=" + data.fileKey + "&fileName=" + encodeURIComponent(data.fileName) + "&imageFileOSPath="+encodeURIComponent(data.imageFileOSPath);
									p = p+fileParam;
								}
								var href = util.formatUrl("/sys/attend/sys_attend_main/sysAttendMain.do?" + p);
								// console.log("url:"+href);
								window.open(href, '_self');
//								adapter.open(href,'_blank');
								return;
							}
							window.console.log('准备进入打卡...');
							domAttr.set(this.signBtnNode, "disabled", "disabled");
							if(!this.proc){
								this.proc = Tip.processing();
								this.proc.show();
							}
							request(util.formatUrl(url), {
								handleAs : 'json',
								method : 'post',
								data : data,
								preventCache:true
							}).then(function(result){
								self.proc.hide();
								window.console.log('打卡返回,result:'+JSON.stringify(result));
								if(result && result.status===true){
									Tip.success({
										text : Msg['mui.sign.success'],
										callback:self.callback
									});
									window.console.log('打卡成功...');
								}else{
									var errTxt = Msg['mui.sign.fail'];
									if(result.code=='01'){
										errTxt = Msg['mui.sign.device.fail'];
									}
									if(result.code=='02'){
										errTxt = Msg['mui.sign.duplicate.fail'];
									}
									Tip.fail({
										text : errTxt,
										callback:self.callback
									});
									window.console.log('打卡失败...'+errTxt);
								}
							},function(e){
								self.proc.hide();
								Tip.fail({
									text : Msg['mui.sign.fail'],
									callback:self.callback
								});
								domAttr.remove(self.signBtnNode, "disabled");
								window.console.log("打卡失败,error:" + e);
							});
						},
						callback:function(){
							var listDom = query('.muiSignInflowList')[0];
							var scrollView = viewRegistry.getEnclosingScrollable(listDom);
							topic.publish('/mui/list/onReload',scrollView);
						},
						//更新打卡入口
						onUpdateSignClick : function(){
							var self = this;
							//客户端类型限制
							if(!this.isAllowSigning()){
								Tip.fail({
									text:this.getClientLimitTip()
								});
								return;
							}
							if(domAttr.has(this.updateSignBtn, "disabled")){
								return;
							}
							// 判断此刻能否更新
							var isNeeded = this._isNeedUpdateByTime();
							if(!isNeeded) {
								Tip.fail({
									text : Msg['mui.no.update.now'],
									callback:self.callback
								});
								return;
							}
							var fdLateTime = this.fdLateTime || 0;
							var fdLeftTime = this.fdLeftTime || 0;
							var _signTime = parseInt(this._signTime || 0);
							var lastSignedTime = !!this.lastSignedTime ? parseInt(this.lastSignedTime, 10) : null;
							var workTimeMins = !!this.workTimeMins ? parseInt(this.workTimeMins, 10) : null;
							//判断是否早退或迟到
							var fdStatus = this._getCurFdStatus();
							//出差/请假/外出标识
							if(this.isAttendStatusBuss()){
								fdStatus = this.fdSignedStatus;
							}
							var data = {
									fdId : this.fdId,
									fdStatus : fdStatus,
									fdCategoryId:this.categoryId,
									fdOutside :'0',
									fdWorkType :this.fdWorkType,
									fdWorkTimeId :this.fdWorkTimeId,
									fdLateTime : fdLateTime,
									fdLeftTime : fdLeftTime,
									fdType : 1,
									signTime : _signTime,
									isRestDay : this.isRestDay,
									fdIsAcross : !!this.fdIsAcross,
									fdFlexTime : !!this.fdFlexTime ? parseInt(this.fdFlexTime, 10) : 0,
									fdIsFlex : this.fdIsFlex,
									fdDeviceId : this.fdDeviceId || '',
									lastSignedTime : lastSignedTime,
									lastSignedStatus : this.lastSignedStatus,
									lastSignedState : this.lastSignedState,
									workTimeMins : workTimeMins,
									fdOverTimeType:!!this.fdOverTimeType ? parseInt(this.fdOverTimeType, 10) : 1
									
							};
							//上班更新打卡时,增加迟到提醒
							if(this.fdWorkType=='0' && this.fdSignedStatus=='1' && fdStatus=='2'){
								Confirm('<div style="line-height:2rem;">'+Msg['mui.sign.update.left']+'</div>','',function(value){
									if(value==true){
										self.checkSecuritySubmit(data,lang.hitch(self,self._onUpdateSignClick));
									}
								});
							}else{
								this.checkSecuritySubmit(data,lang.hitch(this,this._onUpdateSignClick));
							}
						},
						_onUpdateSignClick : function(data){
							var url = "/sys/attend/sys_attend_main/sysAttendMain.do?method=update&forward=lui-source";
							var self = this;
							
							if(!this.proc){
								this.proc = Tip.processing();
								this.proc.show();
							}
							var promise = this.isWifiSign();
							promise.then(function(__args){
								adapter.getDeviceInfo(function(res){
								if(__args.isWifi){
									data.fdOutside='0';
									data.fdWorkDateLong = self.fdWorkDate;
									data.fdWifiName = __args.wifiName;
									data.fdWifiMacIp = __args.wifiMacIp;
									data.fdDeviceId = res.deviceId || '';
									
									domAttr.set(self.updateSignBtn, "disabled", "disabled");
									request(util.formatUrl(url), {
										handleAs : 'json',
										method : 'post',
										data : data
									}).then(function(updateWifiRes){
										self.proc.hide();
										if(updateWifiRes.status){
											Tip.success({
												text : Msg['mui.update.success'],
												callback:self.callback
											});
										}else{
											var errTxt = Msg['mui.update.fail'];
											if(updateWifiRes.code=='01'){
												errTxt = Msg['mui.sign.device.fail'];
											}
											Tip.fail({
												text : errTxt,
												callback:self.callback
											});
										}
									},function(error){
										self.proc.hide();
										Tip.fail({
											text : Msg['mui.update.fail'],
											callback:self.callback
										});
										domAttr.remove(self.updateSignBtn, "disabled");
										window.console.log("error:" + error);
									});
									
								} else {
									adapter.getCurrentPosition(function(result){
										var coordType = result.coordType;
										var address = result.address;
										var point = result.point;
										var pois = result.pois;
										if(pois && pois.length>0){
											//var title = pois[0].title ? pois[0].title:'';
											//address = title ?(title + ' ' + pois[0].address):pois[0].address;
											//point = pois[0].point;
										}
										var fdLatLng = self.formatCoord(point,coordType);
										var fdLocations = eval(self.fdLocations);
										var _fdOutside_flat = true;
										var _fdLocation=address;
										for(var i = 0; i< fdLocations.length;i++){
											var distance = MapUtil.getDistance(fdLatLng,fdLocations[i].coord);
											if(self.isRange(distance,fdLocations[i].distance)){
												_fdOutside_flat = false
												_fdLocation = fdLocations[i].address;
												break;
											}
										}
										
										var fdOutside = _fdOutside_flat?"1":"0";
										if(self.fdSignedStatus=='1' && self.fdSignedOutside !='true'){
											if(_fdOutside_flat){
												Tip.tip({
													text : Msg['mui.update.outsideZone'],
													callback:self.callback
												});
												return;
											}
										}
										data.fdWorkDateLong = self.fdWorkDate;
										data.fdLocation = _fdLocation;
										data.fdAddress = address;
										data.fdLng = point.lng;
										data.fdLat = point.lat;
										data.fdLatLng = fdLatLng;
										data.fdOutside = fdOutside;
										data.fdDeviceId = res.deviceId || '';
										
										domAttr.set(self.updateSignBtn, "disabled", "disabled");
										request(util.formatUrl(url), {
											handleAs : 'json',
											method : 'post',
											data : data
										}).then(function(updateRes){
											self.proc.hide();
											if(updateRes.status){
												Tip.success({
													text : Msg['mui.update.success'],
													callback:self.callback
												});
											}else{
												var errTxt = Msg['mui.update.fail'];
												if(updateRes.code=='01'){
													errTxt = Msg['mui.sign.device.fail'];
												}
												Tip.fail({
													text : errTxt,
													callback:self.callback
												});
											}
										},function(error){
											self.proc.hide();
											domAttr.remove(self.updateSignBtn, "disabled");
											window.console.log("error:" + error);
										});
									},function(){
										Tip.fail({
											text:Msg['mui.cantLocate']
										});
										self.proc.hide();
									});
								}
								});
							});

						},
						
						_onResetLocationClick : function(event){
							domConstruct.empty(this.locationShowNode);
							domConstruct.empty(this.locationValueNode);
							domConstruct.empty(this.limitNode);
							this.showLocationLoading();
							this.refreshPosition();
							
						},
						_onViewSignExcClick :function(){
							var fdAttendMainId = this.fdId || '';
							var curUrl = util.setUrlParameter(window.location.href.replace(/#/g,""),'categoryId', this.categoryId);
							var viewExcUrl = util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=view" + "&fdId="+ this.fdMainExcId +"&fdAttendMainId="
								+ fdAttendMainId + "&_referer="+encodeURIComponent(curUrl));
							if(this.fdMainExcId) {
								location.href = viewExcUrl;
								return;
							}
							//如果没有查看流程。则进行申请
							this._onSignExcClick();
						},
						_onSignExcClick :function(){
							var self = this;
							//异常单 不通过的情况，则也允许重新提交
							if(!this.fdState || this.fdState=='0' ||this.fdState=='3') {
								if(this.fdId) {
									if (this.fdBusSetting && this.fdBusSetting.length > 0) {
										this.buildExcMask(function(){
											self._openExcWin(self.fdId);
										});
									} else {
										this._openExcWin(self.fdId);
									}
								} else {
									// 补全记录
									var proc = Tip.processing();
									proc.show();
									request(util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=fixAttendMain"
											+"&categoryId=" + self.categoryId + "&fdWorkTimeId="+ self.fdWorkTimeId + "&fdWorkType=" + self.fdWorkType + "&fdSignTime=" + this._signTime+ "&fdOverTimeType=" + self.fdOverTimeType), {
										handleAs : 'json',
									}).then(function(data){
										proc.hide();
										if(data && data.fdAttendMainId) {
											self.fdId = data.fdAttendMainId;
											
											if (self.fdBusSetting && self.fdBusSetting.length > 0) {
												self.buildExcMask(function(){
													self._openExcWin(self.fdId);
												});
											} else {
												self._openExcWin(self.fdId);
											}
										} else {
											Tip.fail({
												text:Msg['mui.submit.exc.fail']
											});
										}
									},function(err){
										proc.hide();
										Tip.fail({
											text:Msg['mui.submit.exc.fail']
										});
									});
								}
							}
						},
						
						_openBusWin : function(url){
							location.href = url;
						},
						
						_openExcWin : function(mainId) {
							if(!mainId){
								return;
							}
							var curUrl = util.setUrlParameter(window.location.href.replace(/#/g,""),'categoryId', this.categoryId);
							var addExcUrl = util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=addExc&fdAttendMainId=" + mainId
									+ "&_referer="+encodeURIComponent(curUrl));
							
							var proc = Tip.processing();
							proc.show();
							request(util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=isAllowPatch&fdAttendMainId=" + mainId), {
								handleAs : 'json',
							}).then(function(data){
								proc.hide();
								 if(data) {
									  if(data.status == 0 && data.msg){
										  var text = '';
										  switch(data.msg) {
										  case 'notAllow' : text = Msg['sysAttendMainExc.error.notAllow'];break;
										  case 'overdue' : text = Msg['sysAttendMainExc.error.overdue'];break;
										  case 'overTimes': text = Msg['sysAttendMainExc.error.overdue'];break;
										  default : Msg['mui.submit.exc.fail'];
										  }
										  Tip.fail({
											  text:text
										  });
										  return;
									  }  else if(data.status == 3 && data.msg){
									  	//补卡超过次数按事假半天计算
										  var text = '';
										  switch(data.msg) {
											  case 'overTimes': text = Msg['sysAttendMainExc.error.overdue.overTimes'];break;
											  default : Msg['mui.submit.exc.fail'];
										  }
										  Tip.fail({
											  text:text
										  });
									  }
								 }
								 location.href = addExcUrl;
							},function(err){
								proc.hide();
								console.error(err);
								location.href = addExcUrl;
							});
						},
						
						buildExcMask : function(excFunc) {
							var self = this;
							if (this.excContainer) {
								this._showExcMask();
								return;
							}
							
							this.excContainer = domConstruct.create('div', {
								'className' : 'muiAttendExcMask'
							}, document.body, 'last');
							
							this.excContainerUl = domConstruct.create('div', {
								'className' : 'muiAttendExcUl'
							},  this.excContainer);
							
							var excPool = this._getExcPool(excFunc);
							if(!this.itemObjs) {
								this.itemObjs = [];
							}
							
							for(var i=0; i<excPool.length; i++) {
								var exc = excPool[i];
								var item = domConstruct.create("div", {
									'className' : 'muiAttendExc'
								}, this.excContainerUl);
								var citem  = domConstruct.create("span", {
									'className' : 'muiAttendExcItem'+  " muiAttendExcItemBgColor" + (i % 6),
								}, item);
								var attText  = domConstruct.create("span", {
									'className' : 'muiAttendExcItemText',
									'innerHTML' :  exc.text,
								},item);
								var itemIcon = domConstruct.create("i", {
									'className' : 'mui ' +  exc.icon
								}, citem);
								(function(exc){
									self.connect(item, "onclick", function(){
										exc.func && exc.func();
									});
								})(exc);
								
								this.itemObjs.push(item);
							}
							
							this._showExcMask();
							
						},
						
						_showExcMask : function() {
							domStyle.set(this.excContainer, {
								'display' : 'block',
								'opacity' : 1
							});
							if(!this.handleHide){
								this.handleHide = this.connect(this.excContainer, "onclick", function(){
									this._hideExcMask();
								});
							}
							this.defer( function() {
								for ( var i = 0; i < this.itemObjs.length; i++) {
									domStyle.set(this.itemObjs[i], {
										'-webkit-transform' : 'translate3d(0px, 0px, 0px)',
										'opacity' : 1
									});
								}
							},200);
						},
						
						_hideExcMask : function() {
							for (var i = 0; i < this.itemObjs.length; i++) {
								domStyle.set(this.itemObjs[i],{
										'-webkit-transform' : 'translate3d(0px, 500px, 0px)',
										'opacity' : 0
									});
							}
							
							this.defer(function() {
								domStyle.set(this.excContainer, {
									'opacity' : 0
								});
							}, 500);
							this.defer(function() {
								domStyle.set(this.excContainer, {
									'display' : 'none'
								});
							}, 700);
						},
						
						_getExcPool : function(excFunc) {
							var self = this;
							if(!this.excPool) {
								var pool = [];
								var getIcon = function(excType){
									switch(excType){
										case '4': return 'mui-airplane';break;
										case '5': return 'mui-dateTime';break;
										case '7': return 'mui-airplane';break;
										default : return 'mui-wrong';break;
									}
								};
								
								for(var k=0; k<this.fdBusSetting.length; k++) {
									var bus = this.fdBusSetting[k];
									var exc = {
										"text" : bus.fdBusName || '',
										"func" : (function(url) {
											return function() {
												self._openBusWin(url);
											}
										})(util.formatUrl(bus.fdReviewUrl)),
										"icon" : getIcon(bus.fdBusType),
									};
									pool.push(exc);
								}
								pool.push({
									"text" : Msg['mui.exception.form'],
									"func" :  excFunc || function() {
									},
									"icon" : 'mui-wrong',
								});
								
								this.excPool = pool;
							}
							return this.excPool;
						},
						
						_onBusinessClick : function(){
							if(this.fdBusUrl) {
								var url = util.formatUrl(this.fdBusUrl);
								location.href=url;
							}
						},
						
						buildSignNoData:function(text){
							if(query('.muiListNoDataArea').length>0){
								return;
							}
							var noDataImgUrl = util.formatUrl('/sys/attend/mobile/resource/image/nodata.png');
								noDataText = text || Msg['mui.no.sign'];
								
							var noDataHTML = '<div class="muiListNoDataArea">\
												<div class="muiListNoDataInnerArea">\
													<div class="muiListNoDataContainer muiListNoDataImg">\
														<img src="'+ noDataImgUrl +'">\
													</div>\
												</div>\
												<div class="muiListNoDataTxt">'+ noDataText +'</div>\
											</div>';
							var noDataNode = domConstruct.toDom(noDataHTML);
							domConstruct.place(noDataNode, this.domNode);
							var offsetHeight = query('#scrollView')[0].offsetHeight;
							offsetHeight = offsetHeight<=0 ? 189:offsetHeight;//如取不到高度则默认189
							var h = offsetHeight - query('.muiAttendNav')[0].offsetTop;
							if(!this.domNode.style['line-height'])
								domStyle.set(this.domNode, {
											'height' : h + 'px',
											'line-height' : h + 'px'
										});
						},
						
						buildSignNum:function(){
							domConstruct.create("span",{className:"flowListPostPoint hasTxt"},this.domNode);
						},
						
						startup : function() {
							if (this._started) {
								return;
							}
							this.inherited(arguments);
							if(this.fdType=='2'){
								domClass.add(query('.muiSignInPanel .muiSignInPanelBody')[0],'muiSignInPanelNoPadding');
							}else{
								domClass.remove(query('.muiSignInPanel .muiSignInPanelBody')[0],'muiSignInPanelNoPadding');
							}
						},
						
						_setFdBusSettingAttr : function(obj) {
							if(!obj) {
								this._set("fdBusSetting", []);
								return;
							}
							try{
								this._set("fdBusSetting", Json.parse(obj));
							} catch(e) {
								this._set("fdBusSetting", []);
							}
						},
						
						_setFdWifiConfigsAttr : function(obj) {
							if(!obj) {
								this._set("fdWifiConfigs", []);
								return;
							}
							try{
								this._set("fdWifiConfigs", Json.parse(obj));
							} catch(e) {
								this._set("fdWifiConfigs", []);
							}
						},

						_setLabelAttr : function(text) {
							if (text)
								this._set("label", text);
						},
						//验证当前打开时间是否在允许的时间范围内
						validateSignOnEffectiveTimeRange:function(){
							var now=this.getCurrentSignTime();//new Date();
							var nowMins =this.getNowMins();// now.getHours() *60 + now.getMinutes();
							if(this.fdType==2){
								if(!(nowMins>=this._fdStartTime && nowMins<=this._fdEndTime)){
									return false;
								}
							}
							if(this.fdSigned && !this.isAttendStatusBuss()|| (this.fdSigned && this.isAttendedByBuss())){
								
							}
							else if(this.isRestDay=='true'){
								if(this.isAcrossDay == 'true' &&  0 <= nowMins && nowMins <= this._fdEndTime && this.isCurrentAcross == 'true') {
									this.fdIsAcross = true;
								}
							}else{
								if(this.isAcrossDay == 'true') {
									if(this.isCurrentAcross == 'true') {
										if(this.nextSignTime) {
											if(this.nextOverTimeType!=2){
												return false;
											}
											if(nowMins >= this._fdStartTime) {
												return this.validateSignOrMissOnEffectiveTimeRange();
											}
										}
									}
									else {
										this.fdIsAcross = false;
										if(nowMins >= this._fdStartTime) {
											return this.validateSignOrMissOnEffectiveTimeRange();
										}
									}
								}
								else {
									if(nowMins >= this._fdStartTime && nowMins <= this._fdEndTime){ //允许打卡区间
										return this.validateSignOrMissOnEffectiveTimeRange();
									}
									else{
										return false;
									}
								}
							}
							return true;
						},
						validateSignOrMissOnEffectiveTimeRange:function(){
							var now=this.getCurrentSignTime();//new Date();
							var nowMins = this.getNowMins();//now.getHours() *60 + now.getMinutes();
							//排班制
							if(this.fdShiftType=='1'){
								if(this.nextSignTime){
									if(nowMins < this.nextSignTime){
										if(this.pSignTime){
											if(nowMins<=this.pSignTime){
												return false;
											}
										}
									}else{
										return false;
									}
								}
							}
							
							if(this.fdWorkNum == '1') {//第一班次
								if(this.fdWorkType=='0') {//上班
									if(this.nextSignTime){
										if(nowMins >= this.nextSignTime){
											return false;
										}
									}
								} else if(this.fdWorkType=='1'){//下班
									if(this.fdWorkTimeSize >= '2') {//两班制
										var endTime = this._fdEndTime1 || this._fdStartTime2 || this.nextSignTime;
										if(endTime) {
											if(nowMins > endTime){
												return false;
											}
										}
									} 
								}
							} else if(this.fdWorkNum == '2'){//第二班次
								if(this.fdWorkType=='0') {//上班
									var startTime = this._fdStartTime2 || this._fdEndTime1;
									if(startTime) {
										if(nowMins < startTime) {
											return false;
										} else if (this.nextSignTime && nowMins < this.nextSignTime){
											
										} else {
											return false;
										}
									} else {
										if(this.nextSignTime){
											if(nowMins >= this.nextSignTime){
												return false;
											}
										}
									}
								}
							}
							return true;
						},
						getNowMins:function(){
							var now=this.nowDateTime;
							var nowMins = now.getHours() *60 + now.getMinutes();
							if(this.fdType==1) {
								var tmpFdWorkDate = parseInt(this.fdWorkDate);
								var workDate = new Date(tmpFdWorkDate);
								var overTimeType = !!this.fdOverTimeType ? parseInt(this.fdOverTimeType, 10) : 1;
								//考勤组 计算跨天。
								//跨天排班
								if (overTimeType == 2) {
									nowMins = (24 * (now.getDate() - workDate.getDate()) + now.getHours()) * 60 + now.getMinutes();
								} else {
									if (this.isCurrentAcross || this.isCurrentAcross == "true") {
										//如果标准打卡时间在当日，但是当前属于跨天时。则需要把当前打卡时间加上一天
										nowMins = (24 * (now.getDate() - workDate.getDate()) + now.getHours()) * 60 + now.getMinutes();
									}
								}
							}
							return nowMins;
						}
					});
			return item;
		});
