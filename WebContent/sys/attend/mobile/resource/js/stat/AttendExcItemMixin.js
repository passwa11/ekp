define([
    "dojo/_base/declare","dojo/_base/lang","dojo/io-query","dojo/request",
    "dojo/dom-construct","mui/dialog/Tip","mui/i18n/i18n!sys-mobile",
    "dojo/dom-class","dojo/topic","dojox/mobile/viewRegistry",
	"dojo/dom-style","dojo/query",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin","mui/i18n/i18n!sys-attend"	
	], function(declare,lang,ioq,request, domConstruct,Tip,muiMsg,domClass ,topic,viewRegistry,
			domStyle,query,domAttr,ItemBase , util, _ListLinkItemMixin,Msg) {
	
	var item = declare("sys.attend.AttendExcItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		tag:"li",
		
		baseClass:"",
		
		href:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = this.containerNode= this.srcNodeRef || domConstruct.create(this.tag,{className:''});
			
			var contentNode = domConstruct.create('div',{className : 'content'},this.domNode);
			var headingNode = domConstruct.create('div',{className : 'heading'},contentNode);
			var imgBoxNode = domConstruct.create('span',{className : 'imgbox'}, headingNode);
			domConstruct.create('img',{src:this.icon},imgBoxNode);
			domConstruct.create('span',{className : 'name',innerHTML:this.docCreatorName}, headingNode);
			var fdAttendStatusTxt = this.fdAttendStatusTxt;
			if(this.fdWorkType==1){
				fdAttendStatusTxt = Msg['mui.fdWorkType.offwork'] + fdAttendStatusTxt;
			}else{
				fdAttendStatusTxt = Msg['mui.fdWorkType.onwork'] + fdAttendStatusTxt;
			}
			
			domConstruct.create('span',{className : 'muiAttendStatus',innerHTML:fdAttendStatusTxt}, headingNode);
			
			var dateNode = domConstruct.create('div',{className : 'muiDateBox'},contentNode);
			domConstruct.create('span',{className : '',innerHTML:Msg['mui.time'] + '：'}, dateNode);
			domConstruct.create('span',{className : '',innerHTML:this.docCreateTime}, dateNode);
			
			var summaryNode = domConstruct.create('div',{className : 'muiSummaryBox summary'},contentNode);
			domConstruct.create('span',{className : '',innerHTML:Msg['mui.reason'] + '：'}, summaryNode);
			if(this.fdDesc) {
				domConstruct.create('span',{className : '',innerHTML:this.fdDesc}, summaryNode);
			}
			
			/*
			var btnBarNode = domConstruct.create('div',{className : 'muiSignInBtnGroup'},this.domNode);
			var refuceNode = domConstruct.create('a',{href : 'javascript:,',innerHTML:'不通过'},btnBarNode);
			var okNode = domConstruct.create('a',{href : 'javascript:,',innerHTML:'通过'},btnBarNode);
			this.connect(refuceNode,'click',lang.hitch(this,function(){
				this.onUpdateStatus(0);
			}));
			this.connect(okNode,'click',lang.hitch(this,function(){
				this.onUpdateStatus(1);
			}));
			*/
			this.connect(contentNode,'click',lang.hitch(this,function(){
				var url = "/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=view&fdId=" + this.fdId;
				var curUrl = window.location.href;
				curUrl = util.setUrlParameterMap(curUrl,{
					navIndex:2,
					forward:'exclist'
				});
				url = util.setUrlParameter(url,'_referer',encodeURIComponent(curUrl));
				window.location.href=util.formatUrl(url);
			}));
		},
		
		onUpdateStatus:function(value){
			var url = "/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=updateStatus";
			var options = {
					handleAs : 'json',
					method : 'POST',
					headers: {'Accept': 'application/json'},
					data : ioq.objectToQuery({
						fdId : this.fdId,
						fdStatus:value
					})	
				};
				var self = this;
				var proc = Tip.processing();
				proc.show();
				request(util.formatUrl(url),options).then(lang.hitch(this,function(result){
					proc.hide();
					if (result['status'] === false) {
	            		fail(result);
	            		return;
	            	}
					Tip.success({
						text: muiMsg["mui.return.success"],
						callback:self.callback,
						cover: true
					});
					topic.publish('/attend/statView/refresh',self);
				},function(error){
					proc.hide();
					window.console.log("error:" + error);
				}));
		},
		
		callback:function(){
			var listDom = query('.muiSignInAbnormalList')[0];
			var scrollView = viewRegistry.getEnclosingScrollable(listDom);
			topic.publish('/mui/list/onReload',scrollView);
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