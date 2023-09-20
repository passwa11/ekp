define([ "dojo/_base/declare",
         "dojo/_base/lang",
         "dojo/dom-construct",
         "dojo/query",
         "dojo/request",
         "dojo/dom-style",
         "dojo/html",
         "dojo/dom",
         "dojox/mobile/viewRegistry",
         "mui/util",
         "mui/dialog/Dialog",
         "mui/tabbar/TabBarButton",
         "mui/i18n/i18n!km-review:button.feedback"
         ],
         function(declare, lang, domConstruct, query, request, domStyle, html, dom, viewRegistry, util, Dialog, TabBarButton, Msg){
			 Date.prototype.pattern=function(fmt) {
				 var o = {
					 "M+" : this.getMonth()+1, //月份
					 "d+" : this.getDate(), //日
					 "h+" : this.getHours(), //小时
					 "H+" : this.getHours(), //小时
					 "m+" : this.getMinutes(), //分
					 "s+" : this.getSeconds(), //秒
					 "q+" : Math.floor((this.getMonth()+3)/3), //季度
					 "S" : this.getMilliseconds() //毫秒
				 };
				 var week = {
					 "0" : "/u65e5",
					 "1" : "/u4e00",
					 "2" : "/u4e8c",
					 "3" : "/u4e09",
					 "4" : "/u56db",
					 "5" : "/u4e94",
					 "6" : "/u516d"
				 };
				 if(/(y+)/.test(fmt)){
					 fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
				 }
				 if(/(E+)/.test(fmt)){
					 fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
				 }
				 for(var k in o){
					 if(new RegExp("("+ k +")").test(fmt)){
						 fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
					 }
				 }
				 return fmt;
			 }
	var button = declare("sys.circulation.CirculationReplyButton", [TabBarButton], {
		
		icon1:"",
		
		modelName:"",
		
		modelId:"",
		
		fdKey: null,
		
		extOptUrl: "/km/review/mobile/km_review_feedback_info/feedback.jsp",



		_onClick: function(){
			var view = null;
			view = viewRegistry.getEnclosingView(this.domNode);
	        view = viewRegistry.getParentView(view) || view;
			
	        var _extView = viewRegistry.hash["feedbackView"]
	        if (_extView == null) {
	        	 var url = util.formatUrl(this.extOptUrl);
	             url = util.setUrlParameter(url, "docCreatorTime", new Date().pattern("yyyy-MM-dd hh:mm:ss"));
	             url = util.setUrlParameter(url, "docCreatorName", this.docCreatorName);
	             url = util.setUrlParameter(url, "docCreatorId", this.docCreatorId);
	             url = util.setUrlParameter(url, "modelName", this.modelName);
	             url = util.setUrlParameter(url, "fdMainId", this.modelId);

	             url = util.setUrlParameter(url, "fdBelongPersonId", this.fdBelongPersonId);
	             url = util.setUrlParameter(url, "sysCirculationMainId", this.sysCirculationMainId);
	             url = util.setUrlParameter(url, "fdRegular", this.fdRegular);
	             url = util.setUrlParameter(url, "required", this.required);
	             url = util.setUrlParameter(url, "nextPerson", this.nextPerson);
	             var _selfObj = this
	           	 url = "dojo/text!" + url;
	           	 this.viewNode = domConstruct.create('div');
	           	 domConstruct.place(this.viewNode,view.domNode.parentNode,'last');
	           	 require([url], function(tmplStr) {
					var dhs = new html._ContentSetter({
					  node: _selfObj.viewNode,
					  parseContent: true,
					  cleanContent: false
					})
					
					dhs.set(tmplStr)
					dhs.parseDeferred.then(function(results) {
						_selfObj.defer(function(){
							//初始化完数据后显示弹窗
					    	_selfObj.showCirculationDialog();
						},300);
					});
					dhs.tearDown();
	           	})
	        }else{
	        	this.showCirculationDialog();
	        }
		},
		showCirculationDialog:function(){
			this.operationView = dom.byId("feedbackView");
			this.dialogContentNode = domConstruct.create('div');
			domConstruct.place(dom.byId("feedbackContent"),this.dialogContentNode,'first');
			var buttons = [];
			this.dialog = Dialog.element({
				title : Msg['button.feedback.info'],
				canClose : false,
				element : this.dialogContentNode,
				buttons : buttons,
				position:'bottom',
				appendTo:this.operationView,
				'scrollable' : false,
				'parseable' : false,
				showClass : 'muiLbpmDialog',
				callback : lang.hitch(this, function(win,evt) {
					domStyle.set(this.operationView,{
						"display":"none",
						"position":"",
						"z-index":""
					})
					domStyle.set(query("body")[0],{
						"overflow-y":""
					})
					domConstruct.place(query("#feedbackContent",evt.element)[0],this.operationView,'first');
					this.lastDialog = this.dialog;
					this.dialog = null;
				}),
				onDrawed:lang.hitch(this, function(evt) {
					domStyle.set(this.operationView,{
						"display":"block",
						"position":"fixed",
						"bottom":"0",
						"z-index":"100"
					})
					domStyle.set(query(".mblScrollableViewContainer",this.operationView)[0] ,{
						"display":"none"
					})
					domStyle.set(query("body")[0],{
						"overflow-y":"hidden"
					})
					var contentHeight = document.documentElement.clientHeight*0.9;
					 if(evt.privateHeight){
						 contentHeight=evt.privateHeight
					 }
					 //减去头部高度
					 if(evt.divNode){
						contentHeight = contentHeight - evt.divNode.offsetHeight;
					 }
					 //减去按钮栏高度
					 if(evt.buttonsNode){
						contentHeight = contentHeight - evt.buttonsNode.offsetHeight;
					 }
					 domStyle.set(evt.contentNode, {
						   'height' : contentHeight + 'px',
						   "overflow-x":"hidden"
					 });
					 evt.scrollViewNode = evt.contentNode;
				})
			});
		},
		_destroyDialog:function(evt){
			if (this.dialog && this.dialog.callback)
				this.dialog.callback(window, this.dialog);
			this.lastDialog.hide();
			this.lastDialog = null;
		},
		postCreate: function() {
			this.inherited(arguments);
			this.subscribe("/km/review/km_review_feedback_info/feedback","_destroyDialog");
		},
		startup: function() {
			this.inherited(arguments);
		},
		buildRendering : function() {
			this.inherited(arguments);
			var self = this;
			var url = util.formatUrl('/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=getCirculationOpinionInfo');
			url = util.setUrlParameter(url, "fdModelId", this.modelId);
            url = util.setUrlParameter(url, "fdModelName", this.modelName);
			request.get(url, {handleAs:'json',sync:true}).then(function(json) {
				if(json){

					self.fdBelongPersonId = json.fdBelongPersonId;
					self.sysCirculationMainId = json.sysCirculationMainId;
					self.fdRegular = json.fdRegular;
					self.required = json.required;
					self.nextPerson = json.nextPerson;
					self.test = "json.nextPerson";
				}
			});
		}
	});
	return button;
});
