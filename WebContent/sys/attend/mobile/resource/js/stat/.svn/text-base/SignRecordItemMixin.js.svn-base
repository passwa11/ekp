define([
    "dojo/_base/declare","dojo/_base/lang","dijit/registry",'dojo/parser',"dojo/query",
    "dojo/dom-construct","dojo/_base/array","dojox/mobile/TransitionEvent",
    "dojo/dom-class",'dojo/date/locale','mui/i18n/i18n!sys-mobile',"mui/i18n/i18n!sys-attend:mui",
	"dojo/dom-style","mui/history/listener",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/text!sys/attend/mobile/stat/signTrailView.tmpl",
	"dojo/date/locale"   	
	], function(declare,lang,registry,parser,query, domConstruct,array,TransitionEvent,domClass,
			locale,muiMsg,Msg, domStyle ,listener, domAttr , ItemBase , util, _ListLinkItemMixin,signTrailViewTemplate,locale) {
	
	var item = declare("sys.attend.SignRecordItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		tag:"li",
		
		baseClass:"SignRecordListItem muiListItem",
		
		href:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = this.containerNode= this.srcNodeRef
				|| domConstruct.create(this.tag,{className:this.baseClass});
			
			var imgContainer = domConstruct.create('div',{className : 'sysAttendListImgContainer'},this.domNode),
				contentContainer =  domConstruct.create('div',{className : 'sysAttendListContentContainer'},this.domNode),
				tagContainer = domConstruct.create('div',{className : 'sysAttendListTagContainer'},this.domNode);
			//头像
			domConstruct.create('img',{className : 'sysAttendListImg', src : this.docCreatorImg }, imgContainer);
			//人员+时间
			domConstruct.create('h2',{className : 'muiName', innerHTML : this.docCreatorName }, contentContainer);
			domConstruct.create('div',{className : 'muiDept', innerHTML : this.dept }, contentContainer);
			//次数
			var info = this.fdSignCount > 0 ? lang.replace(Msg['mui.sign.times'],[this.fdSignCount]):'';
			domConstruct.create('div',{className : '' , innerHTML :info }, tagContainer);
			if(this.fdSignCount>0){
				this.connect(this.domNode,'click',function(){
					this.onSignTrailClick();
				});
			}
		},
		
		onSignTrailClick : function(){
			var store = registry.byNode(this.domNode.parentNode);
			var fdCategoryId = util.getUrlParameter(store.url,'fdCategoryId');
			var fdCategoryName = query('#signStatListView .leftArea span').text();
			var self = this;
			if(window._signTrailView){
				window._signTrailView.destroyRecursive();
			}
			
			var _signTrailViewTemplate = lang.replace(signTrailViewTemplate,{
				nowDate:'',
				userId:this.docCreatorId,
				fdCategoryId:fdCategoryId,
				fdCateName:fdCategoryName
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
			this.backToMainView();
		},
		
		backToMainView : function(){
			var mainView=registry.byId('signStatListView');
			var self = this;
			listener.add({callback:function(){
				var opts = {
						transition : 'slide',
						moveTo:mainView.id,
						transitionDir:-1
					};
				domStyle.set(window['muiSignTrailLocationDialog'].domNode,'display','none');
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
			var fdDate = registry.byId('sign_statDate').value;
			dateObj.set('value',fdDate);
			this.defer(function(){
				domStyle.set(query('.muiHeader')[0],'display','none');
			},300);
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text){
				this._set("label", text);
			}
		},
		
		makeUrl:function(){
			if(!this.href){
				return '';
			}
			return this.inherited(arguments);
		}
	});
	return item;
});