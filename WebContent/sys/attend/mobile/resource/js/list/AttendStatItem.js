define(["dojo/_base/declare", "dijit/_WidgetBase","dojo/query",
        "dojo/dom-construct","dojo/dom-style",'dojo/touch',
        'mui/util',"mui/history/listener",'mui/i18n/i18n!sys-mobile',
        "dojo/_base/array","dojox/mobile/TransitionEvent",
        'dojo/parser',"dijit/registry","dojo/_base/lang","dojo/date/locale",
        "dojo/text!sys/attend/mobile/stat/signTrailView.tmpl",
		"dojo/text!sys/attend/mobile/stat/signLogView.tmpl",
		"mui/i18n/i18n!sys-attend"],
		function(declare,WidgetBase,query,domConstruct,domStyle,touch,
				util,listener,muiMsg,array,TransitionEvent,parser,registry,lang,locale,signTrailViewTemplate,signLogViewTemplate,Msg){
	
		return declare("sys.attend.AttendCategorySelect",[WidgetBase],{
			baseClass :'',
			currentDate:new Date(),
			fdCategoryId:'',
			postCreate : function() {
				this.inherited(arguments);
				this.subscribe("/mui/list/loaded",'changeSignCount');
				this.subscribe("/mui/list/loaded",'changeTrailBtn');
				this.subscribe('/mui/calendar/valueChange','saveCurrentDate');
			},
			//选中日期改变时,缓存选中日期
			saveCurrentDate:function(widget,args){
				this.currentDate=args.currentDate;
			},

			buildRendering:function(){
				
				this.inherited(arguments);
				
				this.count = domConstruct.create('em',{
					className : 'muiEkpSubClockInScheduleCount',
					innerHTML : Msg['mui.sign.total'].replace('%count%','<font></font>')
				}, this.domNode);
				this.btn = domConstruct.create('span',{
					innerHTML : Msg['mui.view.trail'],
					style:'display:none'
				}, this.domNode);
				this.connect(this.btn, touch.press, 'onSignTrailClick');
				//记录
				this.log = domConstruct.create('em',{
					innerHTML : Msg['mui.view.signlog'],
					className:'mui-singin-log'
				}, this.domNode);
				this.connect(this.log, touch.press, 'onSignLogClick');

				this.logIcon = domConstruct.create('img',{
					src:util.formatUrl('/sys/attend/mobile/resource/image/log_icon.png'),
					className:'mui-singin-log-icon'
				}, this.domNode);
				this.connect(this.logIcon, touch.press, 'onSignLogClick');

			},
			
			changeSignCount :function(evt,datas){
				if(evt && evt.id == 'groupCalendarList'){
					var count = 0;
					for(var i = 0 ;i<datas.length;i++){
						if(datas[i].fdSignedStatus !=0){
							count++;
						}
					}
					var emNode = query('font', this.count)[0];
					if(datas && datas.length>0){
						domStyle.set(this.domNode, "display", "");
						emNode.innerHTML=count;
					}else{
						domStyle.set(this.domNode, "display", "none");
					}
				}
			},
			
			changeTrailBtn: function(evt,datas){
				if(evt && evt.id == 'groupCalendarList'){
					if(datas && datas.length>0) {
						var __data = datas[0];
						if(__data['fdType'] == '2'){
							domStyle.set(this.btn, "display", "");
							this.categoryId = __data.categoryId;
							this.categoryName = __data.categoryName;
							this.trailDate = __data.start;
							this.docCreatorId = __data.docCreatorId;
							domStyle.set(this.log, "display", "none");
							domStyle.set(this.logIcon, "display", "none");
						} else {
							domStyle.set(this.btn, "display", "none");
							domStyle.set(this.log, "display", "");
							domStyle.set(this.logIcon, "display", "");
						}
					} else {
						domStyle.set(this.btn, "display", "none");
						domStyle.set(this.log, "display", "");
						domStyle.set(this.logIcon, "display", "");
					}
				}
			},
			onSignLogClick:function() {
				var self = this;
				if(window._signLogView){
					window._signLogView.destroyRecursive();
				}
				let url ="/sys/attend/mobile/index_stat_sign_log.jsp?categoryId="+this.fdCategoryId+"&date="+util.formatDate(this.currentDate, 'yyyy-MM-dd');
				window.location.href=util.formatUrl(url);
				//
				// var _signLogViewTemplate = lang.replace(signLogViewTemplate,{
				// 	date: util.formatDate(this.currentDate, 'yyyy-MM-dd'),
				// 	title : Msg['mui.sign.detail']
				// });
				//
				// parser.parse(domConstruct.create('div',{ innerHTML:_signLogViewTemplate,style:'display:none' },query('#content')[0] ,'last'))
				// 	.then(function(widgetList){
				// 		array.forEach(widgetList, function(widget, index) {
				// 			if(index == 0){
				// 				self.afterSignTrailViewParse(widget);
				// 				window._signLogView = widget;
				// 			}
				// 		});
				//
				// 		var opts = {
				// 			transition : 'slide',
				// 			moveTo : window._signLogView.id
				// 		};
				// 		self.moveToSignTrailView(opts);
				// 	});
			},
			onSignTrailClick : function() {
				var self = this;
				if(window._signTrailView){
					window._signTrailView.destroyRecursive();
				}
				
				var _signTrailViewTemplate = lang.replace(signTrailViewTemplate,{
					nowDate:'',
					userId : this.docCreatorId,
					fdCategoryId : this.categoryId,
					fdCateName : this.categoryName,
					mydoc : 'true'
				});
				
				parser.parse(domConstruct.create('div',{ innerHTML:_signTrailViewTemplate,style:'display:none' },query('#content')[0] ,'last'))
				  .then(function(widgetList){
					  array.forEach(widgetList, function(widget, index) {
							if(index == 0){
								self.afterSignTrailViewParse(widget);
								window._signTrailView = widget;
							}
						});
					  	
						var opts = {
							transition : 'slide',
							moveTo : window._signTrailView.id
						};
						self.moveToSignTrailView(opts);
				  });
			},
			
			afterSignTrailViewParse : function(widget){
				widget.hide();//隐藏
				widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
				this.backView = widget.getShowingView();
				var self = this;
				listener.add({callback:function(){
					var opts = {
							transition : 'slide',
							moveTo:self.backView.id,
							transitionDir:-1
						};
					domStyle.set(window['muiSignTrailLocationDialog'].domNode,'display','none');
					domStyle.set(query('.muiHeader')[0],'display','table');
					new TransitionEvent(document.body,  opts ).dispatch();
				}});
			},
			
			moveToSignTrailView : function(opts){
				var locationDialogNode = window['muiSignTrailLocationDialog'];
				if(locationDialogNode){
					domStyle.set(locationDialogNode.domNode,'display','block');
				}
				new TransitionEvent(document.body ,  opts ).dispatch();
				var dateObj = registry.byId('signTrail_statDate');
				dateObj.set('value', this.trailDate.split(/\s+/g)[0]);
				this.defer(function(){
					domStyle.set(query('.muiHeader')[0],'display','none');
				},200);
			},
			
			startup:function(){
				if(this._started){ return; }
				this.inherited(arguments);
			}
		});
});