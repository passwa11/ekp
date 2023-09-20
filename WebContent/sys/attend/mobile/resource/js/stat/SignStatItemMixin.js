define([
    "dojo/_base/declare","dojo/parser","dojo/query",
    "dojo/_base/lang","dojo/_base/array","dijit/registry",
    "dojo/dom-construct","dojox/mobile/TransitionEvent",
    "dojo/dom-class","dojo/date/locale","mui/i18n/i18n!sys-mobile",
	"dojo/dom-style","mui/history/listener",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util","dojo/dom-geometry",
   	"dojo/text!sys/attend/mobile/stat/signStatList.tmpl",
   	"dojo/text!sys/attend/mobile/stat/signTrailView.tmpl",
   	"mui/list/item/_ListLinkItemMixin","mui/i18n/i18n!sys-attend"
	], function(declare,parser,query,lang,array,registry, domConstruct,
			TransitionEvent,domClass ,locale,muiMsg,domStyle,listener,domAttr,ItemBase,
			util,domGeometry,signListTemplate,signTrailViewTemplate, _ListLinkItemMixin,Msg) {
	
	var item = declare("sys.attend.stat.SignStatItemMixin", [ItemBase], {
		
		tag:"li",
		
		baseClass:"muiSignStatListItem",
		
		href:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = domConstruct.create('li', {className : this.baseClass}, this.containerNode);
			this.buildHeadRender();
			this.buildContentRender();
		},
		
		buildHeadRender : function(){
			var title = this.fdName + "(" + this.fdTargetsCount + ")";
			this.headNode = domConstruct.create("div",{className:"muiStatHead",innerHTML:''},this.domNode);
			domConstruct.create("span",{className:"muiStatIcon",innerHTML:''},this.headNode);
			domConstruct.create("p",{className:"muiStatTitle",innerHTML:title},this.headNode);
			var className = this.fdStatus==1 ? "muiDoing" :(this.fdStatus==2 ? "muiFinish":"muiUnstart");
			this.statusSpan = domConstruct.create("span",{className:"muiStatStatusSpan"},this.headNode);
			domConstruct.create("i",{className:"muiStatStatus " + className,innerHTML:this.fdStatusTxt},this.statusSpan);
		},
		
		buildContentRender : function(){
			this.contentNode = domConstruct.create("div",{className:"muiStatContent",innerHTML:''},this.domNode);
			this.statNode = domConstruct.create("div",{className:"muiStatItem",innerHTML:''},this.contentNode);
			
			this.signedNode = domConstruct.create("div",{className:"muiSignBox"},this.statNode);
			domConstruct.create("span",{className:"muiCount",innerHTML:this.fdSignCount},this.signedNode);
			domConstruct.create("span",{className:"",innerHTML:Msg['mui.signed']},this.signedNode);
			
			
			this.unSignedNode = domConstruct.create("div",{className:"muiUnSignBox"},this.statNode);
			domConstruct.create("span",{className:"muiCount",innerHTML:this.fdUnSignCount},this.unSignedNode);
			domConstruct.create("span",{className:"",innerHTML:Msg['mui.unsign']},this.unSignedNode);
			
			this.signDetailNode = domConstruct.create("div",{className:"muiStatSignedDetail",innerHTML:Msg['mui.group.sign.trail']},this.headNode);
			this.detailNode = domConstruct.toDom('<i class="fontmuis muis-to-right"></i>');
			domConstruct.place(this.detailNode, this.signDetailNode);
			
			this.initEvent();
		},
		
		initEvent :function(){
			this.connect(this.signedNode,'click',lang.hitch(this,function(){
				this.onSignDetailClick(1);
			}));
			this.connect(this.unSignedNode,'click',lang.hitch(this,function(){
				this.onSignDetailClick(0);
			}));
			this.connect(this.signDetailNode,'click',lang.hitch(this,this.onSignTrailClick));
		},
		onSignDetailClick:function(value){
			var self = this;
				if(window._signStatListView){
					window._signStatListView.destroy();
				}
				var url = '/sys/attend/sys_attend_sign_stat/sysAttendSignStat.do?method=list&operType='+value + "&fdCategoryId="+this.fdId;
				var _signListTemplate = lang.replace(signListTemplate,{
					url:url,
					nowDate:'',
					navAttendSelected:value==1 ? 'muiNavitemSelected':'',
					navUnttendSelected:value==0 ? 'muiNavitemSelected':'',
					fdCateName:this.fdName,
					singedTxt : Msg['mui.signed'],
					unsignTxt : Msg['mui.unsign'],
				});
				//debugger;
				parser.parse(domConstruct.create('div',{ innerHTML:_signListTemplate,style:'display:none' },query('#content')[0] ,'last'))
					  .then(function(widgetList){
						  array.forEach(widgetList, function(widget, index) {
								if(index == 0){
									self.afterSignStatListParse(widget);
									window._signStatListView = widget;
								}
							});
						  	
							var opts = {
								transition : 'slide',
								moveTo : window._signStatListView.id
							};
							self.moveToSignStatListView(opts);
					  });
			
		},
		
		afterSignStatListParse : function(widget){
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
			this.backStatListView=widget.getShowingView();
			var self = this;
			listener.add({callback:function(){
				var opts = {
						transition : 'slide',
						moveTo:self.backStatListView.id,
						transitionDir:-1
					};
				self.showHeader();
				new TransitionEvent(document.body,opts ).dispatch();
			}});
		},
		moveToSignStatListView : function(opts){
			var bottomH = domGeometry.position(query('.muiEkpSubClockInFooter')[0]).h;
			this.hideHeader();
			new TransitionEvent(document.body ,  opts ).dispatch();
			var dateObj = registry.byId('sign_statDate');
			var now = locale.format(new Date(),{selector : 'date',datePattern : dojoConfig.Date_format });
			dateObj.set('value',now);
			var info = domGeometry.position(query('.signStatListView .muiSignRecordScrollableView')[0]);
			var p = util.getScreenSize();
			domStyle.set(query('.signStatListView .muiSignRecordScrollableView')[0],'height',p.h-info.y-bottomH + 'px');
		},
		
		onSignTrailClick : function(){
			var self = this;
			if(window._signTrailView){
				window._signTrailView.destroyRecursive();
			}
			
			var _signTrailViewTemplate = lang.replace(signTrailViewTemplate,{
				nowDate:'',
				userId:'',
				fdCategoryId:this.fdId,
				fdCateName:this.fdName
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
			this.backStatListView=widget.getShowingView();
			var self = this;
			listener.add({callback:function(){
				var opts = {
						transition : 'slide',
						moveTo:self.backStatListView.id,
						transitionDir:-1
					};
				self.showHeader();
				domStyle.set(window['muiSignTrailLocationDialog'].domNode,'display','none');
				new TransitionEvent(document.body,opts ).dispatch();
			}});
			
		},
		moveToSignTrailView : function(opts){
			this.hideHeader();
			var locationDialogNode = window['muiSignTrailLocationDialog'];
			if(locationDialogNode){
				domStyle.set(window['muiSignTrailLocationDialog'].domNode,'display','block');
			}
			new TransitionEvent(document.body ,  opts ).dispatch();
			var dateObj = registry.byId('signTrail_statDate');
			var now = locale.format(new Date(),{selector : 'date',datePattern : dojoConfig.Date_format });
			dateObj.set('value',now);
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
		},
		
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text){
				this._set("label", text);
			}
		}
	});
	return item;
});