define(['dojo/_base/declare','dijit/_WidgetBase','dojo/text!sys/attend/mobile/stat/attendStatList.tmpl','dojo/text!sys/attend/mobile/stat/chartAttendList.tmpl',
        'dojo/text!sys/attend/mobile/stat/attendExcList.tmpl','dojo/text!sys/attend/mobile/stat/mChartAttendList.tmpl',
        'dojo/text!sys/attend/mobile/stat/mAttendStatList.tmpl','dojo/parser',"mui/history/listener",
		'dojo/_base/lang','dojo/ready','dojo/request','dojo/dom','dojo/query',"dojo/_base/array",'dojox/mobile/TransitionEvent',
		'dojo/dom-class','dojo/dom-construct','dojo/dom-style','dojo/io-query','mui/util','dijit/registry',"dojo/topic",
		'dojo/date','dojo/date/locale','mui/form/Address','mui/i18n/i18n!sys-mobile',"mui/i18n/i18n!sys-attend:mui","mui/dialog/Tip"], 
		function(declare, WidgetBase,statListTemplateString,listTemplateString,excListTemplateString,mChartListTmpl,
				mAttendStatListTmpl,parser,listener,lang, ready, request, dom,query,array ,TransitionEvent,domClass,
				domConstruct , domStyle,ioq,util,registry,topic,dateUtil,locale,Address,muiMsg,Msg,Tip) {

	return declare('sys.attend.mobile.AttendStatView', [WidgetBase], {
		
		startup: function(){
			this.inherited(arguments);	
			this.initEvent();
			this.initAddressForm();
			
			ready(9999,lang.hitch(this,function(){
				var dateObj = registry.byId('_statDate');
				dateObj.extType='date';
				var now = locale.format(new Date(),{selector : 'date',datePattern : dojoConfig.Date_format });
				dateObj.set('value',now);
				
				//异常处理列表
				this.renderAttendExcList();
				//工时排行榜
				this.renderWorkChartList();
				
			}));
			
		},
		
		initEvent:function(){
			this.connect(query('.muiStatType .muiDate')[0],'click',lang.hitch(this,this.onStatTypeClick));
			this.connect(query('.muiStatType .muiMonth')[0],'click',lang.hitch(this,this.onStatTypeClick));
			topic.subscribe('/mui/form/datetime/change',lang.hitch(this,'onStatDateChange'));
			this.subscribe('/attend/stat/exclist/show',lang.hitch(this,'onShowExcList'));
			this.subscribe('/attend/statView/refresh',lang.hitch(this,'refreshStatView'));
			this.connect(query('.muiStatView-infoboard-list .muiStatus')[0],'click',lang.hitch(this,this.onSignStatusClick));
			this.connect(query('.muiStatView-infoboard-list .muiLate')[0],'click',lang.hitch(this,this.onSignStatusClick));
			this.connect(query('.muiStatView-infoboard-list .muiLeft')[0],'click',lang.hitch(this,this.onSignStatusClick));
			this.connect(query('.muiStatView-infoboard-list .muiOutside')[0],'click',lang.hitch(this,this.onSignStatusClick));
			this.connect(query('.muiStatView-infoboard-list .muiMissed')[0],'click',lang.hitch(this,this.onSignStatusClick));
			this.connect(query('.muiStatView-infoboard-list .muiAbsent')[0],'click',lang.hitch(this,this.onSignStatusClick));
			this.connect(query('.muiStatView-infoboard-list .muiTrip')[0],'click',lang.hitch(this,this.onSignStatusClick));
			this.connect(query('.muiStatView-infoboard-list .muiOff')[0],'click',lang.hitch(this,this.onSignStatusClick));
			this.connect(query('.muiStatView-infoboard-list .muiOvertime')[0],'click',lang.hitch(this,this.onSignStatusClick));
			this.connect(query('.muiStatView-infoboard-list .muiOutgoing')[0],'click',lang.hitch(this,this.onSignStatusClick));
			
			this.connect(query('.muiStatCriterion .muiRightArea .right')[0],'click',lang.hitch(this,this.onSelectAddress));
			topic.subscribe("/mui/category/submit",lang.hitch(this,"returnDialog"));
			topic.subscribe("/mui/category/unselected",lang.hitch(this,"unselected"));
			
		},
		//取消部门选择事件
		unselected : function(widget,evt){
			if(widget.key!='_fdDeptId'){
				return;
			}
			this.address.set('value','');
			query('.muiStatCriterion .muiLeftArea').html(Msg['mui.default']);
			this.defer(function(){
				this.renderStatIndex();
				var statType = query('#statType')[0].value;
				if(statType=="1"){
					var dateObj = registry.byId('chartList_statDate');
					topic.publish('/mui/form/datetime/change',dateObj);
				}else{
					var dateObj = registry.byId('mChartList_statDate');
					topic.publish('/mui/form/datetime/change',dateObj);
				}
			},350);
		},
		returnDialog : function(widget,evt){
			if(widget.key!='_fdDeptId'){
				return;
			}
			query('.muiStatCriterion .muiLeftArea').html(evt.curNames);
			this.defer(function(){
				this.renderStatIndex();
				var statType = query('#statType')[0].value;
				if(statType=="1"){
					var dateObj = registry.byId('chartList_statDate');
					topic.publish('/mui/form/datetime/change',dateObj);
				}else{
					var dateObj = registry.byId('mChartList_statDate');
					topic.publish('/mui/form/datetime/change',dateObj);
				}
			},350);
		},
		onSelectAddress : function(){
			this.defer(function(){
				this.address._selectCate();
			},350);
		},
		initAddressForm : function(){
			var options = {
					type:'ORG_TYPE_ALLORG',
					idField:'_fdDeptId',nameField:'_fdDeptName',
			};
			if(this.isStatAllReader!='true'){
//				lang.mixin(options, {
//					templURL: '/sys/attend/mobile/resource/js/stat/address_sgl.jsp',
//					dataUrl:'/sys/attend/sys_attend_stat/sysAttendStat.do?method=addressList&parentId=!{parentId}&orgType=!{selType}'
//				});
			}
			var address = this.address = new Address(options);
			address.startup();
			domConstruct.place(address.domNode,query('.muiStatCriterion .muiAddressForm')[0],'last');
		},
		
		onStatTypeClick : function(){
			var target = event.currentTarget;
			domClass.add(target,'active');
			var dateObj = registry.byId('_statDate');
			var statDate = locale.parse(dateObj.get('value') + ' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
			var statDateTxt = '';
			if(domClass.contains(target,'muiDate')){
				domClass.remove(query('.muiStatType .muiMonth')[0],'active');
				query('#statType')[0].value="1";
				//domStyle.set(query('.muiStatView .mui-infoboard-progress')[0],'display','block');
				domStyle.set(query('.muiStatView .muiEkpSubStatisticsPopulation')[0],'display','block');
				dateObj.extType='date';
				statDateTxt = locale.format(statDate,{selector : 'date',datePattern : dojoConfig.Date_format });
			}else{
				domClass.remove(query('.muiStatType .muiDate')[0],'active');
				query('#statType')[0].value="2";
				//domStyle.set(query('.muiStatView .mui-infoboard-progress')[0],'display','none');
				domStyle.set(query('.muiStatView .muiEkpSubStatisticsPopulation')[0],'display','none');
				dateObj.extType='month';
				statDateTxt = locale.format(statDate,{selector : 'date',datePattern : Msg['mui.date.format.month'] });
			}
			query('#_statDate .muiSelInput',this.domNode)[0].innerHTML=statDateTxt;
			//触发事件
			this.renderStatIndex();
		},
		
		onSignStatusClick :function(){
			var liNode = event.currentTarget;
			var navIndex = 0;
			if(domClass.contains(liNode,'muiLeft')){
				navIndex = 1;
			}
			if(domClass.contains(liNode,'muiMissed')){
				navIndex = 2;
			}
			if(domClass.contains(liNode,'muiAbsent')){
				navIndex = 3;
			}
			if(domClass.contains(liNode,'muiOutside')){
				navIndex = 4;
			}
			if(domClass.contains(liNode,'muiStatus')){
				navIndex = 5;
			}
			if(domClass.contains(liNode,'muiOff')){
				navIndex = 6;
			}
			if(domClass.contains(liNode,'muiTrip')){
				navIndex = 7;
			}
			if(domClass.contains(liNode,'muiOvertime')){
				navIndex = 8;
			}
			if(domClass.contains(liNode,'muiOutgoing')){
				navIndex = 9;
			}
			//注:navIndex必须与nav.jsp顺序一致
			this.onClickStatList({navIndex:navIndex,statType:1});
		},
		
		renderStatIndex :function(){
			var url = util.formatUrl("/sys/attend/sys_attend_stat/sysAttendStat.do?method=stat");
			var dateObj = registry.byId('_statDate');
			var statType = query('#statType')[0].value;
			url = util.setUrlParameter(url,"statType",statType);
			var fdDeptId = query('.muiStatCriterion input[name="_fdDeptId"]')[0].value;
			var statDate = locale.parse(dateObj.get('value')+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
			if(!statDate){
				Tip.fail({text:'日期不允许为空'});
				return;
			}
			var options = {
				handleAs : 'json',
				method : 'POST',
				timeout:30000,
				data : ioq.objectToQuery({
					fdDate : statDate.getTime(),
					fdDeptId : fdDeptId
				})	
			};
			var options2 = {
					handleAs : 'json',
					method : 'POST',
					timeout:30000,
					data : ioq.objectToQuery({
						fdDate : statDate.getTime(),
						fdDeptId : fdDeptId,
						fdType:'totalCount'
					})	
				};
			var processing = Tip.processing();
			processing.show();
			var loading = true;
			//获取应打卡人数信息
			request(url,options2).then(lang.hitch(this,function(result){
				var totalCount = result.totalCount || 0;
				var signCount = result.signCount || 0;
				if(!loading){
					processing.hide(false);
				}
				loading = false;
				query('.mui-progress-circle-mask .head')[0].innerHTML=signCount+"/"+totalCount;
				query('.muiEkpSubStatisticsPopulation .head')[0].innerHTML=signCount+"/"+totalCount;
			}),function(err){
				if(!loading){
					processing.hide(false);
				}
				loading = false;
				console.log(err);
			});
			//具体维度统计信息
			request(url,options).then(lang.hitch(this,function(result){
				var totalCount = result.totalCount || 0;
				var signCount = result.signCount || 0;
				if(!loading){
					processing.hide(false);
				}
				loading = false;
				if(totalCount > 0){
					var progress = 100 * Math.round(100*signCount/totalCount)/100;
					//角度=360度*progress/100
					var deg=(360*parseInt(progress)/100);
					var leftNode = query('.muiStatView .progress-o-L')[0];
					var rightNode = query('.muiStatView .progress-o-R')[0];
					if(deg > 180){
						var _deg=deg-180;
						domStyle.set(leftNode,'transform','rotate('+_deg+'deg)');
						domStyle.set(leftNode,'-webkit-transform','rotate('+_deg+'deg)');
					}else{
						domStyle.set(leftNode,'transform','rotate(0deg)');
						domStyle.set(leftNode,'-webkit-transform','rotate(0deg)');
					}
					
					if(deg < 180){
						domStyle.set(rightNode,'transform','rotate('+deg+'deg)');
						domStyle.set(rightNode,'-webkit-transform','rotate('+deg+'deg)');
					}else{
						domStyle.set(rightNode,'transform','rotate(180deg)');
						domStyle.set(rightNode,'-webkit-transform','rotate(180deg)');
					}
				}else{
					var leftNode = query('.muiStatView .progress-o-L')[0];
					var rightNode = query('.muiStatView .progress-o-R')[0];
					domStyle.set(leftNode,'transform','rotate(0deg)');
					domStyle.set(leftNode,'-webkit-transform','rotate(0deg)');
					domStyle.set(rightNode,'transform','rotate(0deg)');
					domStyle.set(rightNode,'-webkit-transform','rotate(0deg)');
				}
								
				
				query('.muiStatView-infoboard-list .muiStatus .num')[0].innerHTML=result.fdStatusCount;
				query('.muiStatView-infoboard-list .muiLate .num')[0].innerHTML=result.fdLateCount;
				query('.muiStatView-infoboard-list .muiLeft .num')[0].innerHTML=result.fdLeftCount;
				query('.muiStatView-infoboard-list .muiOutside .num')[0].innerHTML=result.fdOutsideCount;
				query('.muiStatView-infoboard-list .muiMissed .num')[0].innerHTML=result.fdMissedCount;
				query('.muiStatView-infoboard-list .muiAbsent .num')[0].innerHTML=result.fdAbsentCount;
				query('.muiStatView-infoboard-list .muiTrip .num')[0].innerHTML=result.fdTripCount;
				query('.muiStatView-infoboard-list .muiOff .num')[0].innerHTML=result.fdOffCount;
				query('.muiStatView-infoboard-list .muiOvertime .num')[0].innerHTML=result.fdOverTimeCount;
				query('.muiStatView-infoboard-list .muiOutgoing .num')[0].innerHTML=result.fdOutgoingCount;
			}),function(err){
				if(!loading){
					processing.hide(false);
				}
				loading = false;
				console.log(err);
			});
		},
		renderAttendExcList :function(){
			this.connect(query('.muiAttendExcListContainer')[0],'click',lang.hitch(this,this.onClickExcList));
			this.renderAttendExcCount();
		},
		renderWorkChartList :function(){
			this.connect(query('.muiAttendWorkChartContainer')[0],'click',lang.hitch(this,this.onClickChartList));
		},
		
		renderAttendExcCount:function(){
			var url = util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=list&mydoc=approval");
			var options = {
				handleAs : 'json',
				method : 'POST',
				data : ioq.objectToQuery({
					rowsize : 1
				})	
			};
			request(url,options).then(lang.hitch(this,function(result){
				if(result.page && result.page.totalSize > 0){
					query('.muiAttendExcListContainer .excNum')[0].innerHTML=result.page.totalSize;
					domClass.add(query('.muiAttendExcListContainer .excNum')[0],'numActive');
				} else {
					domClass.remove(query('.muiAttendExcListContainer .excNum')[0],'numActive');
				}
			}));
		},
		
		onClickStatList :function(evt){
			var statType = query('#statType')[0].value;
			if(statType!='1'){
				evt.statType=2;
				this.onClickMonthStatList(evt);
				return;
			}
			var self = this;
			if(this._statListView){
				this._statListView.destroy();
			}
			statListTemplateString = lang.replace(statListTemplateString,{
				nowDate:'',
				title : Msg['mui.sign.detail']
			});
			//debugger;
			parser.parse(domConstruct.create('div',{ innerHTML:statListTemplateString,style:'display:none' },query('#content')[0] ,'last'))
				  .then(function(widgetList){
					  array.forEach(widgetList, function(widget, index) {
							if(index == 0){
								self.afterStatListParse(widget);
								self._statListView = widget;
							}
						});
					  	
						var opts = {
							transition : 'slide',
							moveTo : self._statListView.id
						};
						self.moveToStatListView(opts,evt);
				  });
		},
		
		moveToStatListView : function(opts,evt){
			var dateObj = registry.byId('_statDate');
			this.hideHeader();
			new TransitionEvent(document.body ,  opts ).dispatch();
			topic.publish('/attend/statList/status',this,evt)
			var statListDateObj = registry.byId('statList_statDate');
			statListDateObj.set('value',dateObj.get('value'));
			this.backToMainView();
		},
		backToMainView : function(){
			var mainView=registry.byId('statView');
			var self = this;
			listener.add({callback:function(){
				var opts = {
						transition : 'slide',
						moveTo:mainView.id,
						transitionDir:-1
					};
				self.showHeader();
				new TransitionEvent(document.body,  opts ).dispatch();
			}});
		},
		
		afterStatListParse : function(widget){
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
		},
		
		onStatDateChange : function(obj,evt){
			if(obj && obj.id=='_statDate'){
				this.renderStatIndex();
			}
		},
		
		refreshStatView : function(){
			var statType = query('#statType')[0].value;
			if(statType=='1'){
				this.renderStatIndex();
				this.renderAttendExcCount();
				return;
			}
		},
		
		onClickChartList : function(){
			var statType = query('#statType')[0].value;
			if(statType!='1'){
				this.onClickMonthChartList();
				return;
			}
			
			var self = this;
			var dateObj = registry.byId('_statDate');
			if(!this._chartListView){
				listTemplateString = lang.replace(listTemplateString,{
					nowDate:'',
					title: Msg['mui.totalhour.rank']
				});
				parser.parse(domConstruct.create('div',{ innerHTML:listTemplateString,style:'display:none' },query('#content')[0] ,'last'))
					  .then(function(widgetList){
						  array.forEach(widgetList, function(widget, index) {
								if(index == 0){
									self.afterChartListParse(widget);
									self._chartListView = widget;
								}
							});
							var opts = {
								transition : 'slide',
								moveTo : self._chartListView.id
							};
							self.hideHeader();
							new TransitionEvent(document.body ,  opts ).dispatch();
							
							var chartListDateObj = registry.byId('chartList_statDate');
							chartListDateObj.set('value',dateObj.get('value'));
					  });
			}else{
				var opts = {
					transition : 'slide',
					moveTo : this._chartListView.id
				};
				self.hideHeader();
				new TransitionEvent(document.body ,  opts ).dispatch();
				var chartListDateObj = registry.byId('chartList_statDate');
				chartListDateObj.set('value',dateObj.get('value'));
				this.backToMainView();
			}
		},
		
		afterChartListParse : function(widget){
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
			this.backToMainView();
		},
		
		onShowExcList :function(obj,evt){
			this.onClickExcList();
		},
		
		onClickExcList:function(){
			var self = this;
			if(!this._excListView){
				excListTemplateString = lang.replace(excListTemplateString,{
					count:query('.muiAttendExcListContainer .excNum')[0].innerHTML,
					title : Msg['mui.pending.exc']
				});
				parser.parse(domConstruct.create('div',{ innerHTML:excListTemplateString,style:'display:none' },query('#content')[0] ,'last'))
					  .then(function(widgetList){
						  array.forEach(widgetList, function(widget, index) {
								if(index == 0){
									self.afterExcListParse(widget);
									self._excListView = widget;
								}
							});
							var opts = {
								transition : 'slide',
								moveTo : self._excListView.id
							};
							self.hideHeader();
							new TransitionEvent(document.body ,  opts ).dispatch();
					  });
			}else{
				var opts = {
					transition : 'slide',
					moveTo : this._excListView.id
				};
				self.hideHeader();
				new TransitionEvent(document.body ,  opts ).dispatch();
				this.backToMainView();
			}
		},
		
		afterExcListParse : function(widget){
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
			this.backToMainView();
		},
		
		//月统计
		onClickMonthChartList : function(){
			var self = this;
			if(!this._monthChartListView){
				mChartListTmpl = lang.replace(mChartListTmpl,{
					nowDate:'',
					title : Msg['mui.totalhour.rank'],
				});
				parser.parse(domConstruct.create('div',{ innerHTML:mChartListTmpl,style:'display:none' },query('#content')[0] ,'last'))
					  .then(function(widgetList){
						  array.forEach(widgetList, function(widget, index) {
								if(index == 0){
									self.afterMonthChartListParse(widget);
									self._monthChartListView = widget;
								}
							});
						  self.moveToMonthChartListView();
					  });
			}else{
				this.moveToMonthChartListView();
			}
		},
		
		moveToMonthChartListView : function(){
			var opts = {
					transition : 'slide',
					moveTo : this._monthChartListView.id
				};
			var dateObj = registry.byId('_statDate');
			this.hideHeader();
			new TransitionEvent(document.body ,  opts ).dispatch();
			var mDateObj = registry.byId('mChartList_statDate');
			mDateObj.set('value',dateObj.get('value'));
			var statDateTxt = query('.muiSelInput',dateObj.domNode)[0].innerHTML;
			query('.muiSelInput',mDateObj.domNode)[0].innerHTML=statDateTxt;
			this.backToMainView();
		},
		
		afterMonthChartListParse : function(widget){
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
		},
		
		onClickMonthStatList :function(evt){
			var self = this;
			if(this._monthStatListView){
				this._monthStatListView.destroy();
			}
			mAttendStatListTmpl = lang.replace(mAttendStatListTmpl,{
				nowDate:'',
				title : Msg['mui.sign.detail'],
			});
			//debugger;
			parser.parse(domConstruct.create('div',{ innerHTML:mAttendStatListTmpl,style:'display:none' },query('#content')[0] ,'last'))
				  .then(function(widgetList){
					  array.forEach(widgetList, function(widget, index) {
							if(index == 0){
								self.afterMonthStatListParse(widget);
								self._monthStatListView = widget;
							}
						});
						self.moveToMonthStatListView(evt);
				  });
		},
		
		moveToMonthStatListView : function(evt){
			var opts = {
					transition : 'slide',
					moveTo : this._monthStatListView.id
				};
			var dateObj = registry.byId('_statDate');
			this.hideHeader();
			new TransitionEvent(document.body ,  opts ).dispatch();
			topic.publish('/attend/statList/status',this,evt)
			var statListDateObj = registry.byId('mStatList_statDate');
			statListDateObj.set('value',dateObj.get('value'));
			var statDateTxt = query('.muiSelInput',dateObj.domNode)[0].innerHTML;
			query('.muiSelInput',statListDateObj.domNode)[0].innerHTML=statDateTxt;
			this.backToMainView();
		},
		
		afterMonthStatListParse : function(widget){
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
		},
		
		showHeader:function(){
			//domStyle.set(query('.muiHeader')[0],'display','table');
			this.showIndexBottom();
		},
		hideHeader:function(){
			//domStyle.set(query('.muiHeader')[0],'display','none');
			this.hideIndexBottom();
		},
		showIndexBottom:function(){
			domStyle.set(query('.muiEkpSubClockInFooter')[0],'display','');
		},
		hideIndexBottom:function(){
			domStyle.set(query('.muiEkpSubClockInFooter')[0],'display','none');
		}
	});
});