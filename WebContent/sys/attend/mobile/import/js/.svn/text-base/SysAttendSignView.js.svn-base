define(['dojo/_base/declare','dijit/_WidgetBase','dijit/_TemplatedMixin','dojo/dom-attr',
		'dojo/text!./SysAttendSignView.tmpl','dojo/text!./SysAttendListView.tmpl','dojo/date/locale','dojo/date','dojo/parser',
		'dojo/_base/lang','dojo/ready','dojo/request','dojo/promise/all','mui/device/adapter','dojo/query',"dojo/_base/array",'dojox/mobile/TransitionEvent',"dojo/touch",
		'dojo/dom-class','dojo/dom-construct','dojo/dom-style','dojo/io-query','mui/util',
		'mui/i18n/i18n!sys-mobile','mui/i18n/i18n!sys-attend:mui','mui/dialog/Tip',"mui/coordtransform",'sys/attend/map/mobile/resource/js/common/MapUtil'], 
		function(declare, WidgetBase, _TemplatedMixin,domAttr, templateString, listTemplateString , locale, dojodate, parser,lang, 
				ready, request,all, adapter,query,array ,TransitionEvent,touch,domClass, domConstruct , domStyle,ioq,util,muiMsg,Msg,Tip,coordtransform,MapUtil) {

	/**
	 * 几个数字说明:
	 * 签到组：
	 * 	0 : 签到未开始
	 * 	1 : 签到进行中
	 * 	2 : 签到已结束
	 * 
	 * 签到记录：
	 *  0 : 未签到
	 *  1 : 已签到 
	 *  2 : 已签到，迟到
	 */
	return declare('sys.attend.import.SysAttendSignView', [WidgetBase, _TemplatedMixin], {

		templateString : templateString,
		
		outer : false,
		
		userId : null,
		
		canAttend : true,
		
		startup: function(){
			var self = this;
			this.inherited(arguments);	
			//签到组件通常不会出现在首屏,延缓加载
			ready(9999,lang.hitch(this,function(){
				//签到记录
				var urlmain = util.formatUrl('/resource/sys/attend/sysAttendAnym.do?method=listMain');
				var promise1 = request(urlmain,{
					handleAs : 'json',
					method : 'POST',
					data : ioq.objectToQuery({
						categoryId : this.categoryId,
						me : this.me,
						userId : this.userId,
						outer : this.outer,
					})
				});
				//签到组
				var urlcategory = util.formatUrl('/resource/sys/attend/sysAttendAnym.do?method=viewCategory');
				var promise2 = request(urlcategory,{
					handleAs : 'json',
					method : 'POST',
					data : ioq.objectToQuery({
						categoryId : this.categoryId
					})
				});
				all({datas : promise1, category : promise2}).then(lang.hitch(this, function(results){
					var category = results['category'];
					var datas = results['datas'];
					var _datas = this._formatDatas(datas.datas);
					
					if(category && category.fdId){
						category.canAttend = true;
						if(!category.isfdAttender){
							category.canAttend = false;
						}
						this.cateStatus = this.getCateStatus(category);
						//签到组信息
						this.renderTime(category);
						//签到按钮
						this.renderSignButton(category, _datas);
						//签到结果
						this.renderSignResult(category, _datas);
						//查看会议详情按钮
						this.renderViewDetailBtn(category);
					}else{
						self.renderNodata();
					}
				}),function(e){
					self.renderNodata();
					console.error(e);
				});
			}));
		},
		
		
		getCateStatus : function(category) {
			// 签到未开始或已结束
			var fdTime = category.fdTimes[0],
			//日期
			fdDate = locale.parse(fdTime,{
				selector : 'date',
				datePattern : dojoConfig.Date_format
			}),
			//开始日期时间
			startTime = locale.parse(fdTime + ' ' + category.fdStartTime,{
				selector : 'date',
				datePattern : dojoConfig.DateTime_format
			}),
			//结束日期时间
			endTime =  locale.parse(fdTime + ' ' + category.fdEndTime,{
				selector : 'date',
				datePattern : dojoConfig.DateTime_format
			});
			if(dojodate.compare(startTime,new Date()) > 0){//未开始
				return 0;
			} else if(dojodate.compare(new Date(),endTime) > 0 || category.fdStatus==2){//已结束
				return 2;
			} else {//进行中
				return 1;
			}
		},
		
		renderTime : function(category){
			this.domStartTime.innerHTML = category.fdStartTime;
			this.domEndTime.innerHTML = category.fdEndTime;
			this.domSignDate.innerHTML =  category.fdTimes[0];
			this.domSignTime.innerHTML = category.fdInTime;
			this.domCateTitle.innerHTML = category.fdName;
		},
		
		renderSignButton : function(category, datas) {
			if(datas && datas.length > 0){
				var data = datas[0];
				this.mainStatus = data._fdStatus;
				if(data._fdStatus == '0'){
					this.genSignDom(category, data);
				}else if(data._fdStatus == '1' || data._fdStatus == '2'){
					this.genSignContentDom();
				}
			} else {
				this.genSignDom(category, null);
			}
		},
		
		genSignDom : function(category, data) {
			if(this.cateStatus == 0){//未开始
				this.signContainer = domConstruct.create("div",{className:"mui_meetingSign_tip"}, this.domSignBody);
				domConstruct.create("div",{className:"imgbox"}, this.signContainer);
				domConstruct.create("div",{className:"txt",innerHTML: '签到未开始'}, this.signContainer);
				return;
			} else if(this.cateStatus == 2 && (!data || data._fdStatus == '0')){//已结束且没签到记录
				this.signContainer = domConstruct.create("div",{className:"mui_meetingSign_tip"}, this.domSignBody);
				domConstruct.create("div",{className:"imgbox"}, this.signContainer);
				domConstruct.create("div",{className:"txt",innerHTML: '签到已结束'}, this.signContainer);
				return;
			}
			
			// 签到按钮
			this.signContainer = domConstruct.create("section",{className:"sysAttendMainContainer mui_meetingSign_tip"}, this.domSignBody);
			this.signBtnNode = domConstruct.create("a",{className:"sysAttendBtn"}, this.signContainer);
			this.currentTimeDom = domConstruct.create("span",{className:"currentTime",innerHTML:''},this.signBtnNode);
			this.signBtnTextNode = domConstruct.create("span",{className:"signName",innerHTML:'签到'},this.signBtnNode);
			
			this.signTimeCounter();
			this.connect(this.signBtnNode, touch.press, lang.hitch(this,function(){
				this._onSignClick(category);
			}));
			
			// 签到按钮下方
			this.signLocNode = domConstruct.create("p",{className:"sysAttendTxt",innerHTML:''},this.signContainer);
			var signIconNode = domConstruct.create("i",{className:"mui mui-location",innerHTML:''},this.signLocNode);
			this.locationTextNode = domConstruct.create("span",{className:"muiSignInLocation",innerHTML:''},this.signLocNode);
			this.limitNode = domConstruct.create('span',{className:'muiSignInLimit',innerHTML:''},this.signLocNode);
			var resetBtnNode = domConstruct.create('a',{className:'refashSingIn',href:"javascript:void(0)",innerHTML:'重新定位'},this.signContainer);
			this.connect(resetBtnNode, touch.press, lang.hitch(this,function(){
				this._onResetLocationClick(category);
			}));
			
			var self = this;
			adapter.mixinReady(function(){
				self.refreshLocation(category);
			});

			setInterval(lang.hitch(this,function(){
				this.signTimeCounter();
			}),1000);
		},
		
		signTimeCounter : function() {
			var date = new Date();
			this.currentTimeDom.innerHTML=this.timeFormat(date.getHours())+':' + this.timeFormat(date.getMinutes())+":" + this.timeFormat(date.getSeconds());
		},
		timeFormat : function(v){
			return v >= 10 ? v : "0" + v;
		},
		
		refreshLocation : function (category) {
			var self = this;
			
			this.locationValue = '';
			this.locationTextNode.innerHTML = '';
			this.limitNode.innerHTML = '';
			domClass.remove(this.limitNode,"unLimit");
			this.isRange = false;
			
			adapter.getCurrentPosition(function(result){
				var coordType = result.coordType;
				var address = result.address;
				var point = result.point;
				var pois = result.pois;
				
				var currentLatLng = self.formatCoord(point,coordType);
				self.locationValue = currentLatLng;
				
				var fdLocations = eval(category.fdLocations);
				var fdLimit = category.fdLimit;
				
				if(fdLocations && fdLocations.length>0 && !!fdLimit){
					
					var isOk = false;
					var range = fdLimit;
					
					for(var i = 0; i< fdLocations.length;i++){
						var distance = MapUtil.getDistance(currentLatLng, fdLocations[i].coord);
						if(distance <= range){
							isOk = true;
							self.locationTextNode.innerHTML = fdLocations[i].address;
							break;
						}
					}
					
					if(!isOk){
						self.locationTextNode.innerHTML = address;
						self.limitNode.innerHTML = '不在打卡范围';
						domClass.add(self.limitNode,"unLimit");
						self.isRange = false;
					} else {
						self.limitNode.innerHTML = '已在打卡范围';
						domClass.remove(self.limitNode,"unLimit");
						self.isRange = true;
					}
				} else {
					self.locationTextNode.innerHTML = address;
					self.limitNode.innerHTML = '';
					domClass.remove(self.limitNode,"unLimit");
					self.isRange = true;
				}
			},function(){
				self.locationTextNode.innerHTML='地理定位失败';
			});
		},
		
		_onSignClick : function (category) {
			var signBtnNode = this.signBtnNode;
			var isRange = this.isRange;
			var locationValue = this.locationValue;
			var locationTextNode = this.locationTextNode;
			
			var fdLocation = locationTextNode.innerHTML || '';
			var fdLatLng = locationValue || '';
			
			if(domAttr.has(signBtnNode, "disabled")){
				return;
			}
			if(category.clientType == -1) {
				Tip.fail({
					text:'PC端不许签到'
				});
				return;
			}
			
			if(!this.outer && !category.canAttend){
				Tip.fail({
					text:'范围外人员不许签到'
				});
				return;
			}
			
			if(!isRange){
				Tip.fail({
					text:'请重新定位'
				});
				return;
			}
			
			var data = {
				userId : this.userId,
				fdLocation : fdLocation,
				fdLatLng : fdLatLng,
				categoryId : category.fdId,
			}
			
			var self = this;
			var url = "/resource/sys/attend/sysAttendAnym.do?method=updateByExt";
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
						text : '打卡成功',
						callback: function(){
							location.reload();
							//location.href = util.setUrlParameter(location.href);
						}
					});
				}else{
					Tip.fail({
						text : result.message,
						callback: function(){
							location.reload();
							//location.href = util.setUrlParameter(location.href);
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
		
		genSignContentDom : function() {
			var signContainer = domConstruct.create("div",{className:"mui_meetingSign_tip"}, this.domSignBody);
			var successDom = domConstruct.create("div",{className:"mui_meetingSign_success"}, signContainer);
			domConstruct.create("div",{className:"mui mui-pitchon"}, successDom);
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
		
		renderSignResult : function(category, datas) {
			if(datas && datas.length > 0){
				var data = datas[0];
				if(data && data._fdStatus != '0') {
					var resultList = domConstruct.create("ul",{className:"mui_meetingSign_info_list"}, this.domSignBody);
					var personName = this.outer ? data.fdOutPersonName : data['docCreator.fdName'];
					if(personName){
						var personNode = domConstruct.create("li",{className:"",innerHTML:personName}, resultList);
						domConstruct.create("li",{className:"mui mui-staff"}, personNode, 'first');
					}
					if(this.outer && data.fdOutPersonPhoneNum) {
						var phoneNode = domConstruct.create("li",{className:"",innerHTML:data.fdOutPersonPhoneNum}, resultList);
						domConstruct.create("li",{className:"mui mui-phone"}, phoneNode, 'first');
					}
					if(data.docCreateTime) {
						var timeNode = domConstruct.create("li",{className:"",innerHTML:data.docCreateTime}, resultList);
						domConstruct.create("li",{className:"mui mui-time-solid"}, timeNode, 'first');
					}
					if(data.fdLocation) {
						var locationNode = domConstruct.create("li",{className:"",innerHTML:data.fdLocation}, resultList);
						domConstruct.create("li",{className:"mui mui-dot"}, locationNode, 'first');
					}
				}
			} else {
				return;
			}
		},
		
		renderViewDetailBtn : function(category) {
			if(!this.outer && category.fdAppUrl && (this.mainStatus == '1'|| this.mainStatus =='2') && category.isUrlAccess) {
				var viewDetailNode = domConstruct.create("button",{className:"mui_meetingSign_btn",innerHTML:'查看会议详情'}, this.domSignBody);
				this.connect(viewDetailNode, touch.press, lang.hitch(this,function(){
					location.href = util.formatUrl(category.fdAppUrl);
				}));
			}
		},
		
		renderNodata : function() {
			domStyle.set(this.domNode,'display','none');
			domConstruct.create('div',{className : 'sysAttendViewNodata'},this.domNode,'before');
			domConstruct.create('div',{className : 'sysAttendViewNodataText', innerHTML: '无效签到'}, this.domNode,'before');
		}
	});
});