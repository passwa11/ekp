define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
	"dojo/_base/lang",
	"dojo/query",
    "dojox/mobile/_ItemBase",
   	"mui/util","mui/history/listener",
   	"mui/list/item/_ListLinkItemMixin",
	"dojo/date/locale",'dojo/parser',"dojo/_base/array","dojox/mobile/TransitionEvent",
	"dojo/text!sys/attend/mobile/stat/attendMainView.tmpl",'mui/i18n/i18n!sys-attend:sysAttendCategory'
	], function(declare, domConstruct,domClass , domStyle , domAttr , lang, query,ItemBase , util,listener,
			_ListLinkItemMixin,locale,parser,array,TransitionEvent,attendMainViewTmpl,Msg) {
	
	var item = declare("sys.attend.SysAttendItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		tag:"li",
		
		baseClass:"sysAttendListItem muiListItem muiAttendItem",
		
		href:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = this.containerNode= this.srcNodeRef
				|| domConstruct.create(this.tag,{className:this.baseClass});
			this.connect(this.domNode,'click',lang.hitch(this,this._onItemClick));
			
			var imgContainer = domConstruct.create('div',{className : 'sysAttendListImgContainer'},this.domNode),
				contentContainer =  domConstruct.create('div',{className : 'sysAttendListContentContainer'},this.domNode),
				tagContainer = domConstruct.create('div',{className : 'sysAttendListTagContainer'},this.domNode);
			//头像
			domConstruct.create('img',{className : 'sysAttendListImg', src : this.docCreatorImg }, imgContainer);
			//人员
			var name = '';
			if(this.fdOutPersonId && this.fdOutPersonName) {
				name = this.fdOutPersonName + ' (外部人员)';
			} else if(this.fdOutTarget==true) {
				name = this.docCreatorName +  ' (范围外)';
			} else {
				name = this.docCreatorName;
			}
			domConstruct.create('h2',{className : 'sysAttendListTitle', innerHTML : name }, contentContainer);
			//时间
			if(this._fdStatus == '1' || this._fdStatus == '2'){
				domConstruct.create('div',{className : 'sysAttendListTime', innerHTML : this.docCreateTime ?  this.docCreateTime : '' }, contentContainer);
			}
			//手机号
			if(this.fdOutPersonId && this.fdOutPersonPhoneNum) {
				domConstruct.create('div',{className : 'sysAttendListTime', innerHTML : this.fdOutPersonPhoneNum }, contentContainer);
			}
			//签到状态
			var className = 'sysAttendListStatus ' + (this._fdStatus > '0' && this._fdStatus == '1' ? 'attend' : 'unAttend');
			domConstruct.create('div',{className : className , innerHTML : this._fdStatus == '0' ? Msg['sysAttendCategory.sysAttendview.topic17'] : (this._fdStatus == '2' ? Msg['sysAttendCategory.sysAttendview.topic16']:Msg['sysAttendCategory.sysAttendview.topic15']) }, tagContainer);
			
			if(this._fdStatus == '0' && this.canPatch == 'true') {
				//补签按钮
				var patchBtn = domConstruct.create('div',{className : 'sysAttendListBtn' , innerHTML : Msg['sysAttendCategory.sysAttendview.topic22'] }, tagContainer, 'first');
				this.connect(patchBtn,'click',lang.hitch(this,this._onPatchBtnClick));
			} else if(this.fdIsPatch){
				//是否补签
				domConstruct.create('div',{className : 'sysAttendListStatus attend' ,style:'margin-left:5px', innerHTML : Msg['sysAttendCategory.sysAttendview.topic23'] }, tagContainer, 'first');
			}
			
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
		
		_onItemClick : function(){
			if(this._fdStatus == '0' || !this.fdLocation) {
				return;
			}
			
			var self = this;
			if(window.attendMainView){
				window.attendMainView.destroyRecursive();
			}
			
			var __attendMainViewTmpl = lang.replace(attendMainViewTmpl,{
				attendMainId : this.fdId
			});
			
			parser.parse(domConstruct.create('div',{ innerHTML:__attendMainViewTmpl,style:'display:none' },query('#content')[0] ,'last'))
			  .then(function(widgetList){
				  array.forEach(widgetList, function(widget, index) {
						if(index == 0){
							self.afterMainViewParse(widget);
							window.attendMainView = widget;
						}
					});
				  	
					var opts = {
						transition : 'slide',
						moveTo : window.attendMainView.id
					};
					
					var locationDialog = window['muiLocationDialog'];
					if(locationDialog){
						domStyle.set(locationDialog.domNode,'display','block');
					}
					//domStyle.set(query('.muiHeader')[0],'display','none');
					new TransitionEvent(document.body ,  opts ).dispatch();
			  });
		},
		
		afterMainViewParse : function(widget) {
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
				var locationDialog = window['muiLocationDialog'];
				if(locationDialog){
					domStyle.set(locationDialog.domNode,'display','none');
				}
				//domStyle.set(query('.muiHeader')[0],'display','table');
				new TransitionEvent(document.body,  opts ).dispatch();
			}});
		},
		
		_onPatchBtnClick : function() {
			var url = util.formatUrl('/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do?method=add'
					+ '&cateId=' + this.fdCategoryId + '&mainId=' + this.fdId);
			location.href = url;
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