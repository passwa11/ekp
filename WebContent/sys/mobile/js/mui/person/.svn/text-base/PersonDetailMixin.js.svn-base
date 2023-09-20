/**提供默认详细人员列表 **/
define([
    "dojo/_base/declare", 
    "dojo/_base/array",
    "dojo/text!./person_detail.jsp",
    'dojo/dom-construct',
    "dojox/mobile/TransitionEvent",
    'dojo/parser',
    'dojo/_base/lang',
    "mui/history/listener",
	"dojox/mobile/viewRegistry",
    'dojo/query'
    ],function(declare,array,detailTemplate,domConstruct,TransitionEvent,parser,lang,listener,viewRegistry,query){
	
	return declare("mui.person.PersonDetailMixin", null, {
		
		//详细页面属性
		detailTitle:'',
		detailUrl:'',
		detailTemplateString:detailTemplate,
		detailView:null,
		
		defaultDetailUrl:'/sys/organization/mobile/address.do?method=personDetailList',
		
		buildRendering : function(){
			this.inherited(arguments);
			if(this.personId && !this.detailUrl){
				this.detailUrl=this.defaultDetailUrl;
			}

		},
		
		forwardCallback : function() {
			
			if(!this.detailView){
				this.detailTemplateString = lang.replace(this.detailTemplateString,{
					title : this.detailTitle,
					url : this.detailUrl,
					personId : this.personId
				});
				var self = this;
				parser.parse(domConstruct.create('div',{ 
					innerHTML : this.detailTemplateString 
				},query('#content')[0], 'last')).then(function(widgetList) {
					if(widgetList.length > 0)
						self.detailView = widgetList[0];
					self.backView = viewRegistry.getEnclosingView(self.domNode);
					self.detailView.hide();//隐藏
					self.detailView.placeAt(self.detailView.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
					var opts = {
						transition : 'slide',
						moveTo : self.detailView.id
					};
					new TransitionEvent(this.personMoreNode || document.body ,  opts ).dispatch();
				});
			} else {
				var opts = {
					transition : 'slide',
					moveTo : this.detailView.id
				};
				new TransitionEvent(this.personMoreNode || document.body,  opts ).dispatch();
			}
			
		},
		
		backCallback:function(){
			var opts = {
					transition : 'slide',
					moveTo:this.backView.id,
					transitionDir:-1
				};
			var locationDialog = window['muiLocationDialog'];
			if(locationDialog){
				domStyle.set(locationDialog.domNode,'display','none');
			}
			new TransitionEvent(document.body,  opts ).dispatch();
			
		},
		
		openDeatailView : function(){
			
			var self = this;
			var listenerResult = listener.push({
				forwardCallback : function(){
					self.forwardCallback();
				},
		        backCallback : function(){
		        	
		        	self.backCallback();
		        }
			});;
			
		}

	});
	
});