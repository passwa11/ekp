define(['dojo/_base/declare','dijit/registry','dojo/query','dojox/mobile/TransitionEvent','dojo/_base/array','dojo/parser',
        'dojo/dom-construct','dojo/date/locale','mui/i18n/i18n!sys-mobile','mui/util'], 
		function(declare,registry,query,TransitionEvent,array,parser,domCtr,locale,muiMsg,util ) {

	return declare('sys.attend.mobile.NavSwapScrollableViewMixin', [], {
		
		startup: function() {
			this.inherited(arguments);
			this.subscribe('/attend/statList/status', 'onStatListStatusChange');
		},
		
		onStatListStatusChange:function(widget,evt){
			if(this.id=='mNavSwapScrollView' && evt.statType==2){
				this._onStatListStatusChange(widget,evt);
			}else if(this.id=='navSwapScrollView' && evt.statType==1){
				this._onStatListStatusChange(widget,evt);
			}
		},
		
		_onStatListStatusChange:function(widget,evt){
			this.navBarIndex=evt.navIndex;
			if(this.refNavBar){
				var self = this;
				new TransitionEvent(document.body, {moveTo: this.getChildren()[this.navBarIndex].id}).dispatch();
			}
		},
		
		handleNavOnComplete: function(widget, items) {
			if(this.id=='mNavSwapScrollView' && widget.id=='_mNavItemBar'){
				this.setItemStoreUrl(widget);
				this._handleNavOnComplete(widget, items);
			}else if(this.id=='navSwapScrollView' && widget.id=='_navItemBar'){
				this.setItemStoreUrl(widget);
				this._handleNavOnComplete(widget, items);
			}
		},
		//重新设置navitemurl
		setItemStoreUrl : function(widget){
			var items = widget.getChildren();
			var statDateId = widget.id=='_navItemBar' ? 'statList_statDate':'mStatList_statDate';
			var statListDateObj = registry.byId(statDateId);
			var statDate = locale.parse(statListDateObj.get('value')+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
			var fdDept = registry.byNode(query('.muiStatCriterion .muiAddressForm .muiCategory')[0]);
			array.forEach(items, function(w, index) {
				var url = w.url;
				url = util.setUrlParameter(url,"fdDate",statDate.getTime());
				url = util.setUrlParameter(url,"fdDeptId",fdDept.get('value'));
				if(statDateId=='mStatList_statDate'){
					url = util.setUrlParameter(url,"fdMonth",statDate.getTime());
				}
				w.url = url;
			});
			
		},
		
		_handleNavOnComplete:function(widget, items){
			this.refNavBar = widget;
			var items = widget.getChildren();
			//添加选中值
			widget.selectedItem=items[this.navBarIndex];
			if(!items[0].moveTo){
				this.generateSwapList(widget.getChildren(), widget);
			}
		},
		
		//临时处理
		generateSwapList: function(items, widget) {
			var loadIndex = 0;
			// 修复数据源selected参数无效的问题
			if(widget && widget.selectedItem)
				loadIndex = array.indexOf(items, widget.selectedItem);
			//选中值必须为空,否则_storeNavBarMixin中事件发布后会跳转(/mui/nav/onComplete)
			widget.selectedItem=null;
			
			var size = items.length,count = 0;
			array.forEach(items, function(item, i) {
				if(item.url){
					var swap = this._createSwap();
					this.addChild(swap);
					var scroll = this._createScroll(item);
					swap.addChild(scroll);
					
					item.moveTo = swap.id; // 绑定view跳转
					var self = this;
					parser.parse(domCtr.create('div', {innerHTML: this.templateString}))
							.then(function(widgetList) {
								array.forEach(widgetList, function(w, index) {
									scroll.addChild(w, index + 1);
								});
								count++;
								if(count==size){
									//初始化完毕
									if (loadIndex == 0) {
										self.handleViewChanged(self.getChildren()[loadIndex]);
									} else {
										require(['dojox/mobile/TransitionEvent'], function(TransitionEvent) {
											new TransitionEvent(document.body, {moveTo: self.getChildren()[loadIndex].id}).dispatch();
										})
									}
									//调整列表高度
									self.resize();
								}
								
							});
				}
				
			}, this);
		}

	});
});